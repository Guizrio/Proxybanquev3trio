package com.trio.proxibanquev3.service;

import com.trio.proxibanquev3.dao.IAdresseDAO;
import com.trio.proxibanquev3.domaine.Adresse;
import com.trio.proxibanquev3.exception.DAOException;
import org.apache.log4j.Logger;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

/**
 * Classe permettant d'utiliser un objet AdresseService en charge des
 * <p>
 * opérations en lien avec les adresses.
 * 
 * @author Vincent Blameble
 *
 */
//@Named(value = "adresseService")
	@Model
//@ApplicationScoped
public class AdresseService implements IAdresseService {

	private final static Logger logger = Logger.getLogger(AdresseService.class);


//	@Named(value = "adresseDao")
	@Inject
	IAdresseDAO adresseDao;// = new AdresseDAO();


	public AdresseService() {
	}

	/* (non-Javadoc)
         * @see com.trio.proxibanquev3.service.IAdresseService#creerUneAdresse(com.trio.proxibanquev3.domaine.Adresse)
         */
	@Override
	public void creerUneAdresse(Adresse adresse) throws DAOException {
		logger.info("On demande a créer une adresse");
		adresseDao.creerUneAdresse(adresse);
		logger.info("On a fini de créer une adresse (peut avoir échoué)");
	}

	/* (non-Javadoc)
	 * @see com.trio.proxibanquev3.service.IAdresseService#lireToutesLesAdresses()
	 */
	@Override
	public List<Adresse> lireToutesLesAdresses() throws DAOException {
		logger.info("On demande a lire toutes les adresses");
		return adresseDao.lireToutesLesAdresses();
	}

	/* (non-Javadoc)
	 * @see com.trio.proxibanquev3.service.IAdresseService#lireUneAdresse(long)
	 */
	@Override
	public Adresse lireUneAdresse(long idAdresse) throws DAOException {
		logger.info("On demande a lire une adresse ciblée par son identifiant");
		return adresseDao.lireUneAdresse(idAdresse);
	}

	/* (non-Javadoc)
	 * @see com.trio.proxibanquev3.service.IAdresseService#mAJUneAdresse(com.trio.proxibanquev3.domaine.Adresse)
	 */
	@Override
	public void mAJUneAdresse(Adresse adresse) throws DAOException {
		logger.info("On demande a mettre à jour une adresse");
		adresseDao.mAJUneAdresse(adresse);
		logger.info("On a fini de mettre à jour une adresse (peut avoir échoué)");
	}

	/* (non-Javadoc)
	 * @see com.trio.proxibanquev3.service.IAdresseService#supprimerUneAdresse(com.trio.proxibanquev3.domaine.Adresse)
	 */
	@Override
	public void supprimerUneAdresse(Adresse adresse) throws DAOException {
		logger.info("On demande a supprimer une adresse");
		adresseDao.supprimerUneAdresse(adresse);
		logger.info("On a fini de supprimer une adresse (peut avoir échoué)");
	}

}
