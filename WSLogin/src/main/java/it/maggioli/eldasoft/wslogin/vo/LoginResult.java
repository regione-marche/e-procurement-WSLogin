package it.maggioli.eldasoft.wslogin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Risultato accesso servizio pubblicazione
 *
 * @author Mirco.Franzoni
 */
@XmlRootElement
@XmlType(propOrder = {
		"esito",
		"token",
		"error"
})
@ApiModel(description="Contenitore per il risultato dell'operazione di accesso al servizio di pubblicazione")
public class LoginResult implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	/** Codice di errore nel caso di record non trovato in base all'id dato in input. */
	public static final String ERROR_NOT_FOUND = "not-found";
	/** Codice di errore in fase di controllo username e password */
	public static final String ERROR_USERNAME_PASSWORD_EMPTY = "empty-username-password";
	/** Codice di errore in fase di controllo clientId e clientKey */
	public static final String ERROR_CLIENTID_CLIENTKEY_EMPTY = "empty-clientId-clientKey";
	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";

	@ApiModelProperty(value="Risultato operazione di accesso al servizio di pubblicazione")
	private boolean esito;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Eventuale messaggio di errore in caso di operazione fallita")
	private String error;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Se le credenzaili sono corrette questo token è valorizzato")
	private String token;

	public void setError(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	public boolean isEsito() {
		return esito;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

}
