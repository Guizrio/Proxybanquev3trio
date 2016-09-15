/**
 * 
 */
package com.trio.proxibanquev3.service;

import com.trio.proxibanquev3.dao.IConseillerDAO;
import com.trio.proxibanquev3.domaine.Conseiller;
import com.trio.proxibanquev3.exception.DAOException;
import org.apache.log4j.Logger;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

/**
 * Classe permettant d'utiliser un objet ConseillerService en charge des
 * <p>
 * opérations en lien avec les conseillers.
 * 
 * @author Vincent Blameble
 *
 */
//@Named(value = "conseillerService")
	@Model
//@ApplicationScoped
public class ConseillerService implements IConseillerService {

	private final static Logger logger = Logger.getLogger(ConseillerService.class);


	public ConseillerService() {
	}

	@Inject
	IConseillerDAO conseillerDao; // = new ConseillerDAO();

	
	
	/* (non-Javadoc)
	 * @see com.trio.proxibanquev3.service.IConseillerService#creerUnConseiller(com.trio.proxibanquev3.domaine.Conseiller)
	 */
	@Override
	public void creerUnConseiller(Conseiller conseiller) throws DAOException {
		conseillerDao.creerUnConseiller(conseiller);
		logger.info("Un conseiller a été créé. (peut avoir échoué)");
	}

	/* (non-Javadoc)
	 * @see com.trio.proxibanquev3.service.IConseillerService#lireToutesLesConseillers()
	 */
	@Override
	public List<Conseiller> lireToutesLesConseillers() throws DAOException {
		logger.info("demande de lecture des conseillers.");
		return conseillerDao.lireToutesLesConseillers();
	}

	/* (non-Javadoc)
	 * @see com.trio.proxibanquev3.service.IConseillerService#lireUnConseiller(long)
	 */
	@Override
	public Conseiller lireUnConseiller(long idConseiller) throws DAOException {
		logger.info("demande de lecture d'un conseiller par son identifiant.");
		return conseillerDao.lireUnConseiller(idConseiller);
	}
	
	/* (non-Javadoc)
	 * @see com.trio.proxibanquev3.service.IConseillerService#lireUnConseiller(java.lang.String)
	 */
	@Override
	public Conseiller lireUnConseiller(String login) throws DAOException {
		logger.info("Demande de lecture d'un conseiller par son login");
		return conseillerDao.lireUnConseiller(login);
	}

	/* (non-Javadoc)
	 * @see com.trio.proxibanquev3.service.IConseillerService#MAJUnConseiller(com.trio.proxibanquev3.domaine.Conseiller)
	 */
	@Override
	public void MAJUnConseiller(Conseiller conseiller) throws DAOException {

		conseillerDao.mAJUnConseiller(conseiller);
		logger.info("Un conseiller vient d'etre mis à jour (peut avoir échoué)");
	}

	/* (non-Javadoc)
	 * @see com.trio.proxibanquev3.service.IConseillerService#SupprimerUnConseiller(com.trio.proxibanquev3.domaine.Conseiller)
	 */
	@Override
	public void SupprimerUnConseiller(Conseiller conseiller) throws DAOException {
		conseillerDao.supprimerUnConseiller(conseiller);
		logger.info("Un conseiller vient d'etre supprimé (peut avoir échoué)");
	}

}
