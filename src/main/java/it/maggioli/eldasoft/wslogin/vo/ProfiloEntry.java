package it.maggioli.eldasoft.wslogin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

/**
 * Dati Profilo.
 *
 * @author Stefano.Sabbadin
 */
@XmlType(propOrder = {
		"codice",
		"nome",
		"descrizione"
})
@ApiModel(description="Profilo associato all'utente")
@Alias("profiloentry")
public class ProfiloEntry implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -4433185026855332865L;

	@XmlElement(name = "code")
	@ApiModelProperty(name = "code", value="Codice profilo")
	private String codice;
	@XmlElement(name = "name")
	@ApiModelProperty(name = "name", value="Nome profilo")  
	private String nome;
	@XmlElement(name = "description")
	@ApiModelProperty(name = "description", value="Descrizione profilo")  
	private String descrizione;

	public void setCodice(String codice) {
		this.codice = StringUtils.stripToNull(codice);
	}
	public String getCodice() {
		return codice;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = StringUtils.stripToNull(descrizione);
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setNome(String nome) {
		this.nome = StringUtils.stripToNull(nome);
	}
	public String getNome() {
		return nome;
	}

}
