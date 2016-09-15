package com.trio.proxibanquev3.dao;

import com.trio.proxibanquev3.domaine.Conseiller;
import com.trio.proxibanquev3.exception.DAOException;
import com.trio.proxibanquev3.service.PersistanceService;

import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 ** Classe permettant d'utiliser un objet ConseillerDAO en charge de l'ecriture
 * et
 * <p>
 * de la lecture des Conseillers dans la base de donnée.
 * <p>
 * 
 * @author Vincent Blameble
 *
 */
//@Stateless
//@Model
//	@ManagedBean
	@Default
public class ConseillerDAO implements IConseillerDAO {
	// private EntityManagerFactory emf =
	// Persistence.createEntityManagerFactory("proxibanquev3-pu");
//	private EntityManagerFactory emf = EntityManagerFactorySingleton.Instance();
	private EntityManager em = null;
	private EntityTransaction tx = null;

	public ConseillerDAO() {
	}

	/*
         * (non-Javadoc)
         *
         * @see com.trio.proxibanquev3.dao.IConseillerDAO#startContext()
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
	 * @see com.trio.proxibanquev3.dao.IConseillerDAO#closeContext()
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
	 * @see
	 * com.trio.proxibanquev3.dao.IConseillerDAO#creerUnConseiller(com.trio.
	 * proxibanquev3.domaine.Conseiller)
	 */
	@Override
	public void creerUnConseiller(Conseiller conseiller) throws DAOException {
		try {
			startContext();
			tx.begin();
			em.persist(conseiller);
			tx.commit();
			closeContext();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new DAOException("probleme dans la couche DAO: sauvegarde du conseiller impossible");
		} finally {
			closeContext();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trio.proxibanquev3.dao.IConseillerDAO#lireToutesLesConseillers()
	 */
	@Override
	public List<Conseiller> lireToutesLesConseillers() throws DAOException {
		List<Conseiller> conseillers = new ArrayList<Conseiller>();
		try {
			startContext();
			tx.begin();
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Conseiller> criteriaRequete = criteriaBuilder.createQuery(Conseiller.class);
			Root<Conseiller> fromConseiller = criteriaRequete.from(Conseiller.class);
			criteriaRequete.select(fromConseiller);
			TypedQuery<Conseiller> requete = em.createQuery(criteriaRequete);
			conseillers = requete.getResultList();
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new DAOException("probleme dans la couche DAO: récupération de la liste de conseillers impossible");
		} finally {
			closeContext();
		}
		return conseillers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trio.proxibanquev3.dao.IConseillerDAO#lireUnConseiller(long)
	 */
	@Override
	public Conseiller lireUnConseiller(long idConseiller) throws DAOException {
		Conseiller conseiller = null;

		try {
			startContext();
			tx.begin();

			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Conseiller> criteriaRequete = criteriaBuilder.createQuery(Conseiller.class);
			Root<Conseiller> fromConseiller = criteriaRequete.from(Conseiller.class);
			criteriaRequete.select(fromConseiller);
			criteriaRequete.where(criteriaBuilder.equal(fromConseiller.get("idPersonne"), idConseiller));
			TypedQuery<Conseiller> requete = em.createQuery(criteriaRequete);
			conseiller = requete.getSingleResult();

			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new DAOException("probleme dans la couche DAO: la lecture du conseiller avec l'id " + idConseiller
					+ " est impossible");
		} finally {
			closeContext();
		}
		return conseiller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trio.proxibanquev3.dao.IConseillerDAO#lireUnConseiller(long)
	 */
	@Override
	public Conseiller lireUnConseiller(String login) throws DAOException {
		Conseiller conseiller = null;

		try {
			startContext();
			tx.begin();

			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Conseiller> criteriaRequete = criteriaBuilder.createQuery(Conseiller.class);
			Root<Conseiller> fromConseiller = criteriaRequete.from(Conseiller.class);
			criteriaRequete.select(fromConseiller);
			criteriaRequete.where(criteriaBuilder.equal(fromConseiller.get("login"), login));
			TypedQuery<Conseiller> requete = em.createQuery(criteriaRequete);
			conseiller = requete.getSingleResult();

			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new DAOException(
					"probleme dans la couche DAO: la lecture du conseiller avec le login " + login + " est impossible");
		} finally {
			closeContext();
		}
		return conseiller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trio.proxibanquev3.dao.IConseillerDAO#mAJUnConseiller(com.trio.
	 * proxibanquev3.domaine.Conseiller)
	 */
	@Override
	public void mAJUnConseiller(Conseiller conseiller) throws DAOException {
		try {
			startContext();
			tx.begin();
			conseiller = em.merge(conseiller);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new DAOException("probleme dans la couche DAO: modification du conseiller impossible");
		} finally {
			closeContext();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.trio.proxibanquev3.dao.IConseillerDAO#supprimerUnConseiller(com.trio.
	 * proxibanquev3.domaine.Conseiller)
	 */
	@Override
	public void supprimerUnConseiller(Conseiller conseiller) throws DAOException {
		try {
			startContext();
			tx.begin();
			conseiller = em.merge(conseiller);
			em.remove(conseiller);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new DAOException("probleme dans la couche DAO: suppression du conseiller impossible");
		} finally {
			closeContext();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.trio.proxibanquev3.dao.IConseillerDAO#authentification(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public boolean authentification(String login, String password) throws DAOException {
		Conseiller conseiller = null;
		String mdp = null;

		try {
			startContext();
			tx.begin();

			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Conseiller> criteriaRequete = criteriaBuilder.createQuery(Conseiller.class);
			Root<Conseiller> fromConseiller = criteriaRequete.from(Conseiller.class);
			criteriaRequete.select(fromConseiller);
			criteriaRequete.where(criteriaBuilder.equal(fromConseiller.get("login"), login));
			TypedQuery<Conseiller> requete = em.createQuery(criteriaRequete);
			conseiller = requete.getSingleResult();
			tx.commit();
		}catch (NoResultException e) {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw e;// new DAOException(
					// "probleme dans la couche DAO: la lecture du password lié
					// au login " + login + " est impossible");
		} finally {
			closeContext();
			if (conseiller != null) {
				mdp = conseiller.getPassword();
			}
		}
		if (mdp == null) {
			return false;
		} else if (password.equalsIgnoreCase(mdp)) {
			return true;
		} else {
			return false;
		}

	}

}
