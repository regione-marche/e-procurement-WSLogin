package it.maggioli.eldasoft.wslogin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlType;

import org.apache.ibatis.type.Alias;


@XmlType(propOrder = {
		"syscon",
		"syspwbou"		
})
@ApiModel(description="Dati usrSys associati all'utente")
@Alias("usrsysentry")
public class UsrSysEntry {

	@ApiModelProperty(value="ID syscon")
	private String syscon;
	@ApiModelProperty(value="Permessi dell'utente")  
	private String syspwbou;
	public String getSyscon() {
		return syscon;
	}
	public void setSyscon(String syscon) {
		this.syscon = syscon;
	}
	public String getSyspwbou() {
		return syspwbou;
	}
	public void setSyspwbou(String syspwbou) {
		this.syspwbou = syspwbou;
	}
}
