/**
 * 
 */
package com.trio.proxibanquev3.service;

import com.trio.proxibanquev3.dao.IConseillerDAO;
import com.trio.proxibanquev3.exception.DAOException;
import org.apache.log4j.Logger;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 * Classe permettant d'utiliser un objet AdresseService utilise lors des
 * <p>
 * opérations d'identification du use l'application.
 * 
 * @author Vincent Blameble
 *
 */
//@Named(value = "authService")
	@Model
//@ApplicationScoped
public class AuthService implements IAuthService {

	private final static Logger logger = Logger.getLogger(AuthService.class);
	
	@Inject
	IConseillerDAO conseillerDao; //=new ConseillerDAO();


	public AuthService() {
	}

	/* (non-Javadoc)
         * @see com.trio.proxibanquev3.service.IAuthService#authentification(java.lang.String, java.lang.String)
         */
	@Override
	public boolean authentification(String login,String password) throws DAOException{
		logger.info("On demande a authentifier un utilisateur comme conseiller pour une session");
		return conseillerDao.authentification(login,password);
	}

}
