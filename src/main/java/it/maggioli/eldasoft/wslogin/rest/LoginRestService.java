package it.maggioli.eldasoft.wslogin.rest;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.maggioli.eldasoft.wslogin.bl.AccountManager;
import it.maggioli.eldasoft.wslogin.vo.AccountResult;
import it.maggioli.eldasoft.wslogin.vo.LoginResult;
import it.maggioli.eldasoft.wslogin.vo.UsrSysEntry;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Path("/Account")
@Component
@Api(value = "/Account")
public class LoginRestService {

	/**
	 * Servizio REST esposto al path "/Account".
	 */

	/** Logger di classe. */
	protected Logger logger = LoggerFactory.getLogger(LoginRestService.class);

	/**
	 * Wrapper della business logic a cui viene demandata la gestione
	 */
	private AccountManager accountManager;

	/**
	 * @param loginManager
	 *            loginManager da settare internamente alla classe.
	 */
	@Required
	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	@POST
	@Path("/LoginPubblica")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(nickname = "AccountRestService.loginPubblica", value = "Verifica le credenziali per l'accesso al servizio di pubblicazione", notes = "Ritorna l'esito della verifica delle credenziali", response = LoginResult.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Operazione effettuata con successo"),
			@ApiResponse(code = 400, message = "Controlli falliti sui parametri in input (si veda l'attributo error della risposta)"),
			@ApiResponse(code = 404, message = "L'utente richiesto non è stato trovato (si veda l'attributo error della risposta)"),
			@ApiResponse(code = 500, message = "Errori durante l'esecuzione dell'operazione (si veda l'attributo error della risposta)") })
	public Response loginPubblica(
			@ApiParam(value = "Username utente", required = true) @FormParam("username") String username,
			@ApiParam(value = "Password utente", required = true) @FormParam("password") String password,
			@ApiParam(value = "Client/Application key", required = true) @FormParam("clientKey") String clientKey,
			@ApiParam(value = "Client/Application Id", required = true) @FormParam("clientId") String clientId)
			throws ParseException {

		if (logger.isDebugEnabled()) {
			logger.debug("loginPubblica(" + username + "," + password
					+ "): inizio metodo");
		}
		LoginResult risultato = userLogin(username, password);

		HttpStatus status = HttpStatus.OK;
		if (risultato.getError() != null) {
			if (AccountResult.ERROR_NOT_FOUND.equals(risultato.getError())) {
				status = HttpStatus.NOT_FOUND;
			} else if (AccountResult.ERROR_USERNAME_PASSWORD_EMPTY
					.equals(risultato.getError())) {
				status = HttpStatus.BAD_REQUEST;
			} else {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			logger.debug("loginPubblica: fine metodo [http status "
					+ status.value() + "]");
			return Response.status(status.value()).entity(risultato).build();
		}

		risultato = applicationLogin(clientId, clientKey);
		
		if (risultato.getError() != null) {
			if (AccountResult.ERROR_NOT_FOUND.equals(risultato.getError())) {
				status = HttpStatus.NOT_FOUND;
			} else if (AccountResult.ERROR_CLIENTID_CLIENTKEY_EMPTY
					.equals(risultato.getError())) {
				status = HttpStatus.BAD_REQUEST;
			} else {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			logger.debug("loginPubblica: fine metodo [http status "
					+ status.value() + "]");
			return Response.status(status.value()).entity(risultato).build();
		}
		try {
			String jwtKey = accountManager.getJWTKey();
			risultato.setToken(getToken(username, clientId, jwtKey));
			logger.debug("loginPubblica: fine metodo [http status "
					+ status.value() + "]");
			return Response.status(status.value()).entity(risultato).build();
		} catch (Exception e) {
			logger.error("Errore in generazione JWT TOKEN",e);
			risultato.setError(e.getMessage());
			risultato.setEsito(false);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			return Response.status(status.value()).entity(risultato).build();
		}

	}

	private LoginResult userLogin(String user, String pwd){
		LoginResult risultato = null;
		String username = StringUtils.stripToNull(user);
		String password = StringUtils.stripToNull(pwd);
		if (username == null || password == null) {
			risultato = new LoginResult();
			risultato.setError(LoginResult.ERROR_USERNAME_PASSWORD_EMPTY);
		} else {
			username = username.toUpperCase();
			risultato = accountManager.getLoginPubblica(username, password);
		}
		return risultato;
	}
	
	private LoginResult applicationLogin(String clientId, String clientKey){
		LoginResult risultato = null;
		String username = StringUtils.stripToNull(clientId);
		String password = StringUtils.stripToNull(clientKey);
		if (username == null || password == null) {
			risultato = new LoginResult();
			risultato.setError(LoginResult.ERROR_CLIENTID_CLIENTKEY_EMPTY);
		} else {
			risultato = accountManager.getLoginApplicazione(username, password);
		}
		return risultato;
	}
	
	private static final long ttlMillis = 3600000;

	public String getToken(String username, String clientId, String jwtKey) {

		String issuer = null;
		try {
			issuer = InetAddress.getLocalHost().getHostAddress()
					+ "/Account/LoginPubblica";
		} catch (UnknownHostException e) {
			logger.debug("loginPubblica: metodo creazione token. Impossibile trovare HostName");
			issuer = "UnknownHost";
		}
		UsrSysEntry sysEntry = accountManager.getUsrSysEntry(username);
		long nowMillis = System.currentTimeMillis();
		JwtBuilder builder = Jwts.builder().setIssuedAt(new Date(nowMillis))
				.setSubject(username).setIssuer(issuer).setAudience(clientId)
				.signWith(SignatureAlgorithm.HS256, jwtKey)
				.setExpiration(getExpDate(nowMillis, ttlMillis))
				.claim("syscon", sysEntry.getSyscon())
				.claim("modificaArchivioStazioneAppaltante", !sysEntry.getSyspwbou().contains("ou214"));
		return builder.compact();
	}

	private static Date getExpDate(long nowMillis, long ttlMillis) {
		return new Date(nowMillis + ttlMillis);
	}

	
	
}
