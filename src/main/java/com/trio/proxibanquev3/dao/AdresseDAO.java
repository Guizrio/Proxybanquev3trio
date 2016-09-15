/**
 * 
 */
package com.trio.proxibanquev3.dao;

import com.trio.proxibanquev3.domaine.Adresse;
import com.trio.proxibanquev3.exception.DAOException;
import com.trio.proxibanquev3.service.PersistanceService;

import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant d'utiliser un objet AdresseDAO en charge de l'ecriture et
 * <p>
 * de la lecture des Adresse dans la base de donnée.
 * 
 * @author Vincent Blameble
 *
 */
//@Named(value = "adresseDao")
//	@Stateless
//	@Model
//	@ManagedBean
	@Default
public class AdresseDAO implements IAdresseDAO {

	//private static Logger logger =Logger.getLogger(AdresseDAO.class);

	// private EntityManagerFactory emf =
	// Persistence.createEntityManagerFactory("proxibanquev3-pu");
//	private EntityManagerFactory emf = //EntityManagerFactorySingleton.Instance();
	private EntityManager em = null;
	private EntityTransaction tx = null;

	public AdresseDAO() {
	}

	/*
         * (non-Javadoc)
         *
         * @see com.trio.proxibanquev3.dao.IAdresseDAO#startContext()
         */
	@Override
	public void startContext() {
		if (em == null) {
			em = PersistanceService.createEntityManager();
			tx = em.getTransaction();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trio.proxibanquev3.dao.IAdresseDAO#closeContext()
	 */
	@Override
	public void closeContext() {
		if (em != null) {
			em.close();
			em = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trio.proxibanquev3.dao.IAdresseDAO#creerUneAdresse(com.trio.
	 * proxibanquev3.domaine.Adresse)
	 */
	@Override
	public void creerUneAdresse(Adresse adresse) throws DAOException {
		try {
			startContext();
			tx.begin();
			em.persist(adresse);
			tx.commit();
			closeContext();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new DAOException("probleme dans la couche DAO: sauvegarde de l'adresse impossible");
		} finally {
			closeContext();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trio.proxibanquev3.dao.IAdresseDAO#lireToutesLesAdresses()
	 */
	@Override
	public List<Adresse> lireToutesLesAdresses() throws DAOException {
		List<Adresse> adresses = new ArrayList<Adresse>();
		try {
			startContext();
			tx.begin();
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Adresse> criteriaRequete = criteriaBuilder.createQuery(Adresse.class);
			Root<Adresse> fromadresse = criteriaRequete.from(Adresse.class);
			criteriaRequete.select(fromadresse);
			TypedQuery<Adresse> requete = em.createQuery(criteriaRequete);
			adresses = requete.getResultList();
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new DAOException("probleme dans la couche DAO: récupération de la liste d'adresse impossible");
		} finally {
			closeContext();
		}
		return adresses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trio.proxibanquev3.dao.IAdresseDAO#lireUneAdresse(long)
	 */
	@Override
	public Adresse lireUneAdresse(long idAdresse) throws DAOException {
		Adresse adresse = null;
		try {
			startContext();
			tx.begin();

			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Adresse> criteriaRequete = criteriaBuilder.createQuery(Adresse.class);
			Root<Adresse> fromadresse = criteriaRequete.from(Adresse.class);
			criteriaRequete.select(fromadresse);
			criteriaRequete.where(criteriaBuilder.equal(fromadresse.get("idAdresse"), idAdresse));
			TypedQuery<Adresse> requete = em.createQuery(criteriaRequete);
			adresse = requete.getSingleResult();

			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new DAOException(
					"probleme dans la couche DAO: la lecture de l'adresse avec l'id " + idAdresse + " est impossible");
		} finally {
			closeContext();
		}
		return adresse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trio.proxibanquev3.dao.IAdresseDAO#mAJUneAdresse(com.trio.
	 * proxibanquev3.domaine.Adresse)
	 */
	@Override
	public void mAJUneAdresse(Adresse adresse) throws DAOException {
		try {
			startContext();
			tx.begin();
			adresse = em.merge(adresse);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new DAOException("probleme dans la couche DAO: modification de l'adresse impossible");
		} finally {
			closeContext();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trio.proxibanquev3.dao.IAdresseDAO#supprimerUneAdresse(com.trio.
	 * proxibanquev3.domaine.Adresse)
	 */
	@Override
	public void supprimerUneAdresse(Adresse adresse) throws DAOException {
		try {
			startContext();
			tx.begin();
			adresse = em.merge(adresse);
			em.remove(adresse);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new DAOException("probleme dans la couche DAO: suppression de l'adresse impossible");
		} finally {
			closeContext();
		}
	}

}
