package it.maggioli.eldasoft.wslogin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

/**
 * Dati Stazione appaltante associata all'utente.
 *
 * @author Stefano.Sabbadin
 */
@XmlType(propOrder = {
		"codice",
		"denominazione",
		"codiceFiscale",
		"idAmministrazione"
})
@ApiModel(description="Stazione appaltante associata all'utente")
@Alias("saentry")
public class SAEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -4433185026855332865L;

	@ApiModelProperty(value="Codice stazione appaltante")
	private String codice;
	@ApiModelProperty(value="Descrizione stazione appaltante")  
	private String denominazione;
	@ApiModelProperty(value="Codice fiscale stazione appaltante")  
	private String codiceFiscale;
	@ApiModelProperty(value="Id amministrazione stazione appaltante")  
	private int idAmministrazione;
	
	public void setCodice(String codice) {
		this.codice = StringUtils.stripToNull(codice);
	}
	public String getCodice() {
		return codice;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = StringUtils.stripToNull(denominazione);
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = StringUtils.stripToNull(codiceFiscale);
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setIdAmministrazione(int idAmministrazione) {
		this.idAmministrazione = idAmministrazione;
	}
	public int getIdAmministrazione() {
		return idAmministrazione;
	}
	

}
