package it.maggioli.eldasoft.wslogin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.eldasoft.utils.sicurezza.FactoryCriptazioneByte;
import it.eldasoft.utils.sicurezza.ICriptazioneByte;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Dettaglio dell'account.
 *
 * @author Stefano.Sabbadin
 */
@XmlRootElement
@XmlType(propOrder = {
		"id",
		"username",
		"nome",
		"email",
		"codiceFiscale",
		"abilitato",
		"ruolo",
		"amministratore",
		"profili",
		"stazioniAppaltanti",
		"token",
		"error"
})
@Alias("accountresult")
@ApiModel(description="Contenitore per i dati dell'utente loggato")
public class AccountResult implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	/** Codice di errore nel caso di record non trovato in base all'id dato in input. */
	public static final String ERROR_NOT_FOUND = "Errore durante la fase di login. Verificare le credenziali di accesso al Servizio di pubblicazione";
	/** Codice di errore in fase di controllo username e password */
	public static final String ERROR_USERNAME_PASSWORD_EMPTY = "Errore durante la fase di login. Verificare le credenziali di accesso al Servizio di pubblicazione";
	/** Codice di errore in fase di controllo clientId e clientKey */
	public static final String ERROR_CLIENTID_CLIENTKEY_EMPTY = "Errore durante la fase di login. Verificare le credenziali di accesso al Servizio di pubblicazione";
	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";

	@XmlElement(name = "id")
	@ApiModelProperty(value="Id account utente")  
	private int id;
	@XmlElement(name = "username")
	@ApiModelProperty(value="Username")
	private String username;
	@XmlElement(name = "name")
	@ApiModelProperty(name = "name", value="Nome utente")  
	private String nome;
	@XmlElement(name = "email")
	@ApiModelProperty(value="Email utente")  
	private String email;
	@XmlElement(name = "fiscal_code")
	@ApiModelProperty(name = "fiscal_code", value="Codice fiscale utente")  
	private String codiceFiscale;
	@XmlElement(name = "role")
	@ApiModelProperty(name = "role", value="Ruolo utente 'A' = amministratore, 'U' = utente")  
	private String ruolo;
	@XmlElement(name = "profiles")
	@ApiModelProperty(name = "profiles", value="Profili associati all'utente")
	private List<ProfiloEntry> profili = new ArrayList<ProfiloEntry>();
	@XmlElement(name = "procurement_entities")
	@ApiModelProperty(name = "procurement_entities", value="Profili associati all'utente")
	private List<SAEntry> stazioniAppaltanti = new ArrayList<SAEntry>();
	@XmlElement(name = "token")
	@ApiModelProperty(name = "token", value="Token")  
	private String token = "";
	
	private String sysdisab;
	private String syspwbou;
	private String password;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Eventuale messaggio di errore in caso di operazione fallita")
	private String error;

	/**
	 * @return Ritorna error.
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error
	 *        error da settare internamente alla classe.
	 */
	public void setError(String error) {
		this.error = error;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setUsername(String username) {
		this.username = StringUtils.stripToNull(username);
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = StringUtils.stripToNull(password);
	}

	public Boolean isCorrectPassword(String password) {
		if (this.password != null) {
			String passwordDecifrata = "";
			try {
				ICriptazioneByte decriptatorePsw = FactoryCriptazioneByte.getInstance("LEG",
						this.password.getBytes(),
		            ICriptazioneByte.FORMATO_DATO_CIFRATO);
				passwordDecifrata = new String(decriptatorePsw.getDatoNonCifrato());
			} catch(Exception ex) {
				;
			}
			return passwordDecifrata.equals(password);
		} else {
			return false;
		}
	}

	public void setNome(String nome) {
		this.nome = StringUtils.stripToNull(nome);
	}

	public String getNome() {
		return nome;
	}

	public void setEmail(String email) {
		this.email = StringUtils.stripToNull(email);
	}

	public String getEmail() {
		return email;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = StringUtils.stripToNull(codiceFiscale);
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	@XmlElement(name = "enable")
	@ApiModelProperty(name = "enable", value="Utente abilitato")  
	public boolean isAbilitato() {
		if (this.sysdisab != null) {
			return this.sysdisab.equals("0");
		} else {
			return false;
		}
	}

	public void setProfili(List<ProfiloEntry> profili) {
		this.profili = profili;
	}

	public List<ProfiloEntry> getProfili() {
		return profili;
	}

	public void setStazioniAppaltanti(List<SAEntry> stazioniAppaltanti) {
		this.stazioniAppaltanti = stazioniAppaltanti;
	}

	public List<SAEntry> getStazioniAppaltanti() {
		return stazioniAppaltanti;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = StringUtils.stripToNull(ruolo);
	}

	public String getRuolo() {
		return ruolo;
	}

	@XmlElement(name = "administrator")
	@ApiModelProperty(name = "administrator", value="Utente amministratore parametri di sistema")  
	public boolean isAmministratore() {
		if (this.syspwbou != null) {
			return this.syspwbou.contains("ou89");
		} else {
			return false;			
		}
	}

	public void setSysdisab(String sysdisab) {
		this.sysdisab = StringUtils.stripToNull(sysdisab);
	}

	public void setSyspwbou(String syspwbou) {
		this.syspwbou = StringUtils.stripToNull(syspwbou);
	}

}

