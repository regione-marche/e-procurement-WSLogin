package it.maggioli.eldasoft.wslogin.dao;

import it.maggioli.eldasoft.wslogin.vo.AccountResult;
import it.maggioli.eldasoft.wslogin.vo.UsrSysEntry;

import org.apache.ibatis.annotations.Param;

/**
 * DAO Interface per l'estrazione delle informazioni relative al login
 * nell'applicazione.
 * 
 * @author Stefano.Sabbadin
 */
public interface AccountMapper {

	/**
	 * Restituisce i dati dell'account relativi alle credenziali passate
	 * 
	 * @param username
	 *            username
	 * @param password
	 *            password
	 * @return dati relativi all'account
	 */
	public AccountResult getAccount(@Param("username") String username);

	/**
	 * Restituisce una clientKey se clientId esiste
	 * 
	 * @param clientId
	 *            clientId
	 * @return clientKey relativa all'account
	 */
	public String getClientKey(@Param("clientId") String clientId);

	/**
	 * Restituisce la chiave di cifratura per il JWT Token salvato nella w_config 
	 * 
	 * @param codapp
	 *            codapp
	 * @param chiave
	 *            chiave
	 * @return Jwt key
	 */
		
	public String getCipherKey(@Param("codapp") String codapp, @Param("chiave") String chiave);

	
	/**
	 * Restituisce le info associate all'utente nella usersys 
	 * 
	 * @param username
	 *            username
	 * @return UsrSysEntry usrSys
	 */
	
	public UsrSysEntry getUserSysInfo(@Param("username") String username);
}
