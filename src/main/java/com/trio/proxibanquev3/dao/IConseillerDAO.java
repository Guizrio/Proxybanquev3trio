package com.trio.proxibanquev3.dao;

import com.trio.proxibanquev3.domaine.Conseiller;
import com.trio.proxibanquev3.exception.DAOException;

import javax.enterprise.inject.Model;
import java.util.List;

public interface IConseillerDAO {

	/**
	 * permet d'initialiser l'entity manager et la transaction utiliser dans le
	 * <p>
	 * DAO
	 * 
	 */
	void startContext();

	/**
	 * ferme l'entity manager et passe la variable a null
	 */
	void closeContext();

	/**
	 * Sauvegarde un conseiller initialement non present dans la base de donnée
	 * 
	 * @param conseiller
	 *            conseiller a sauvegarder
	 * @throws DAOException
	 *             renvoie une DAOexception avec un message
	 */
	void creerUnConseiller(Conseiller conseiller) throws DAOException;

	/**
	 * renvoie la liste de tous les conseillers enregistrés en base de donnée
	 * 
	 * @return une liste de conseillers
	 * @throws DAOException
	 *             renvoie une DAOexception avec un message
	 */
	List<Conseiller> lireToutesLesConseillers() throws DAOException;

	/**
	 * renvoie un objet Conseiller creer a partir de l'enregistrement en base de
	 * <p>
	 * donnée repertorié par l'id idConseiller
	 * 
	 * @param idConseiller
	 *            id de Conseiller a lire
	 * @return un objet Conseiller
	 * @throws DAOException
	 *             renvoie une DAOexception avec un message
	 */
	Conseiller lireUnConseiller(long idConseiller) throws DAOException;

	/**
	 * mettre a jour l'enregistrement d'un conseiller en base de donnée
	 * 
	 * @param conseiller
	 *            objet Conseiller a mettre a jour dans la base
	 * @throws DAOException
	 *             renvoie une DAOexception avec un message
	 */
	void mAJUnConseiller(Conseiller conseiller) throws DAOException;

	/**
	 * Supprimer l'enregistrement representant un objet Conseiller
	 * 
	 * @param adresse
	 *            objet Conseiller a supprimer de la base de donnée;
	 * @throws DAOException
	 *             renvoie une DAOexception avec un message
	 */
	void supprimerUnConseiller(Conseiller conseiller) throws DAOException;

	/**
	 * renvoie un objet boolean. Si un conseiller est trouvé en base avec le
	 * <p>
	 * login , une comparaison des password est effectué. Si les passwords sont
	 * <p>
	 * identique alors le boolean renvoyé est egale a true. En cas d erreur ou
	 * <p>
	 * d'incohérence le boolean est renvoyé a false
	 * 
	 * 
	 * @param login
	 *            login a verifié
	 * @param password
	 *            password a verifié
	 * @return un boolean
	 * @throws DAOException
	 *             renvoie une DAOexception avec un message
	 */
	boolean authentification(String login, String password) throws DAOException;

	/**
	 * renvoie un objet Conseiller creer a partir de l'enregistrement en base de
	 * <p>
	 * donnée repertorié par le login login
	 * 
	 * @param login
	 *            login de Conseiller a lire
	 * @return un objet Conseiller
	 * @throws DAOException
	 *             renvoie une DAOexception avec un message
	 */
	Conseiller lireUnConseiller(String login) throws DAOException;

}