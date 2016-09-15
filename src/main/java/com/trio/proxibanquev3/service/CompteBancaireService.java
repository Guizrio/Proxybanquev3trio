package com.trio.proxibanquev3.service;

import com.trio.proxibanquev3.dao.ICompteBancaireDAO;
import com.trio.proxibanquev3.domaine.Client;
import com.trio.proxibanquev3.domaine.CompteBancaire;
import com.trio.proxibanquev3.exception.DAOException;
import com.trio.proxibanquev3.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

/**
 * Classe permettant d'utiliser un objet CompteBancaireService en charge des
 * <p>
 * opérations en lien avec les comptes bancaires.
 * 
 * @author Vincent Blameble
 *
 */
//@Named(value = "compteBanquaireService")
	@Model
//@ApplicationScoped
public class CompteBancaireService implements ICompteBancaireService {

	private final static Logger logger = Logger.getLogger(CompteBancaireService.class);


	public CompteBancaireService() {
	}

	//	@Named(value = "compteBancaireDao")
	@Inject
	ICompteBancaireDAO compteBancaireDao; //= new CompteBancaireDAO();




	private boolean debite(CompteBancaire compteDebite, double montant) throws ServiceException {
		logger.info("Une opération de débit débute.");
		boolean goodWork = true;
		try {
			compteDebite.setSolde(compteDebite.getSolde()-montant);
			compteBancaireDao.mAJUnCompteBancaire(compteDebite);
		} catch (DAOException e) {

			logger.error("L'opération de débit a échouée. Cause : Couche DAO. Tentative de rollback maison", e);
			goodWork = false;
			compteDebite.setSolde(compteDebite.getSolde()+montant);
			throw new ServiceException(
					"probleme dans la couche service au niveau du debit du compte: " + e.getMessage());

		} catch (Exception e) {

			logger.error("L'opération de débit a échouée. Cause : Inconnue. Tentative de rollback maison", e);
			goodWork = false;
			throw new ServiceException("probleme dans la couche service au niveau du debit du compte");
		}

		logger.warn("Le mec qui a codé cette fonction est une b... La valeur suivante est toujours vrai.");
		return goodWork;

	}

	private boolean credite(CompteBancaire compteCredite, double montant) throws ServiceException {

		logger.info("Une opération de crédit débute.");

		boolean goodWork = true;
		try {
			compteCredite.setSolde(compteCredite.getSolde()+montant);
			compteBancaireDao.mAJUnCompteBancaire(compteCredite);
		} catch (DAOException e) {

			logger.error("L'opération de crédit a échouée. Cause : Couche DAO. Tentative de rollback maison", e);
			goodWork = false;
			compteCredite.setSolde(compteCredite.getSolde()-montant);
			throw new ServiceException(
					"probleme dans la couche service au niveau du credit du compte: " + e.getMessage());
		} catch (Exception e) {

			logger.error("L'opération de crédit a échouée. Cause : Inconnue. Tentative de rollback maison", e);
			goodWork = false;
			throw new ServiceException("probleme dans la couche service au niveau du credit du compte");
		}

		logger.warn("Le mec qui a codé cette fonction est une b... La valeur suivante est toujours vrai.");
		return goodWork;

	}

	/* (non-Javadoc)
	 * @see com.trio.proxibanquev3.service.ICompteBancaireService#virement(com.trio.proxibanquev3.domaine.CompteBancaire, com.trio.proxibanquev3.domaine.CompteBancaire, double)
	 */
	@Override
	public void virement(CompteBancaire compteDebite, CompteBancaire compteCredite, double montant)
			throws ServiceException {

		logger.info("Une opération de virement a été demandé. Début de virement.");

		logger.warn("Du aux remarques qui vont probablement suivre, la ligne suivante me fait bien marrer !");
		if (debite(compteDebite, montant)) {
			try {
				credite(compteCredite, montant);
			} catch (ServiceException e) {
				try {

					logger.warn("Tentative de remettre de l'argent sur le compte suite au rollback");
					credite(compteDebite, montant);
					logger.info("Fin de la tentative de remettre de l'argent sur le compte suite au rollback");
					throw new ServiceException("virement interrompu: " + e.getMessage());

				} catch (ServiceException e2) {

					logger.error("La fin du monde : de l'argent perdu à tout jamais : débit inrecréditable");
					throw new ServiceException(
							"probleme de virement, debit sans credit, reversement impossible pour cause de: "
									+ e2.getMessage());
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.trio.proxibanquev3.service.ICompteBancaireService#creerUnCompteBancaire(com.trio.proxibanquev3.domaine.CompteBancaire)
	 */
	@Override
	public void creerUnCompteBancaire(CompteBancaire compteBancaire) throws DAOException {
		compteBancaireDao.creerUnCompteBancaire(compteBancaire);
		logger.info("Un compte viens d'etre créé (peut avoir échoué)");
	}

	/* (non-Javadoc)
	 * @see com.trio.proxibanquev3.service.ICompteBancaireService#lireToutesLesCompteBancaires()
	 */
	@Override
	public List<CompteBancaire> lireToutesLesCompteBancaires() throws DAOException {
		logger.info("Quelqu'un demande la liste de tous les comptes banquaires.");
		return compteBancaireDao.lireToutesLesCompteBancaires();
	}

	/* (non-Javadoc)
	 * @see com.trio.proxibanquev3.service.ICompteBancaireService#lireUnCompteBancaire(long)
	 */
	@Override
	public CompteBancaire lireUnCompteBancaire(long idCompteBancaire) throws DAOException {
		logger.info("Quelqu'un demande à lire un compte banquaire par son identifiant");
		return compteBancaireDao.lireUnCompteBancaire(idCompteBancaire);
	}

	/* (non-Javadoc)
	 * @see com.trio.proxibanquev3.service.ICompteBancaireService#mAJUnCompteBancaire(com.trio.proxibanquev3.domaine.CompteBancaire)
	 */
	@Override
	public void mAJUnCompteBancaire(CompteBancaire compteBancaire) throws DAOException {
		logger.info("quelqu'un demande à mettre à jour un compte banquaire");
		compteBancaireDao.mAJUnCompteBancaire(compteBancaire);
		logger.info("Quelqu'un a mis à jour un compte banquaire (peut avoir échoué)");
	}

	/* (non-Javadoc)
	 * @see com.trio.proxibanquev3.service.ICompteBancaireService#supprimerUnCompteBancaire(com.trio.proxibanquev3.domaine.CompteBancaire)
	 */
	@Override
	public void supprimerUnCompteBancaire(CompteBancaire compteBancaire) throws DAOException {

		compteBancaireDao.supprimerUnCompteBancaire(compteBancaire);
		logger.info("Quelqu'un a supprimé un compte banquaire (peut avoir échoué)");
	}

	/* (non-Javadoc)
	 * @see com.trio.proxibanquev3.service.ICompteBancaireService#lireToutesLesCompteBancairesByClient(long)
	 */
	@Override
	public List<CompteBancaire> lireToutesLesCompteBancairesByClient(long idClient) throws DAOException {
		logger.info("quelqu'un demande la liste de tous les comptes banquaire d'un client ciblé par son identifiant");
		return compteBancaireDao.lireToutesLesCompteBancairesByClient( idClient);
	}

	/* (non-Javadoc)
	 * @see com.trio.proxibanquev3.service.ICompteBancaireService#lireToutesLesCompteBancairesByClient(com.trio.proxibanquev3.domaine.Client)
	 */
	@Override
	public List<CompteBancaire> lireToutesLesCompteBancairesByClient(Client client) throws DAOException {
		logger.info("quelqu'un demande la liste de tous les comptes banquaires d'un client ciblé par lui-même");
		return compteBancaireDao.lireToutesLesCompteBancairesByClient(client);
	}

}
