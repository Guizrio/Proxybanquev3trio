/**
 * 
 */
package com.trio.proxibanquev3.service;

import com.trio.proxibanquev3.dao.IClientDAO;
import com.trio.proxibanquev3.domaine.Client;
import com.trio.proxibanquev3.domaine.Conseiller;
import com.trio.proxibanquev3.exception.DAOException;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

/**
 * Classe permettant d'utiliser un objet ClientService en charge des
 * <p>
 * opérations en lien avec les clients.
 * 
 * @author Vincent Blameble
 *
 */
//@Named("clientService")
	@Model
//@ApplicationScoped
public class ClientService implements IClientService {

	private final static Logger logger = Logger.getLogger(ClientService.class);

	@Inject
//	@Named(value = "clientDao")
	IClientDAO clientDao; // = new ClientDAO();

	public ClientService() {
		logger.debug("debut construction Client Service");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.trio.proxibanquev3.service.IClientService#creerUnClient(com.trio.
	 * proxibanquev3.domaine.Client)
	 */
	@Override
	public void creerUnClient(Client client) throws DAOException {
		logger.info("On demande a créer un client");
		clientDao.creerUnClient(client);
		logger.info("On a créé un client (peut avoir échoué)");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trio.proxibanquev3.service.IClientService#lireToutesLesClients()
	 */
	@Override
	public List<Client> lireToutesLesClients() throws DAOException {
		logger.info("On demande a lire tous les clients");
		return clientDao.lireToutesLesClients();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trio.proxibanquev3.service.IClientService#
	 * lireToutesLesClientsByidConseiller(long)
	 */
	@Override
	public List<Client> lireToutesLesClientsByidConseiller(long idConseiller) throws DAOException {
		logger.info("On demande à lire tous les clients qui sont rattachés à un conseiller particulier ciblé par son identifiant");
		return clientDao.lireToutesLesClientsByidConseiller(idConseiller);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trio.proxibanquev3.service.IClientService#
	 * lireToutesLesClientsByidConseiller(long)
	 */
	@Override
	public List<Client> lireToutesLesClientsByidConseiller(Conseiller conseiller) throws DAOException {
		logger.info("On demande à lire tous les clients qui sont rattachés à un conseiller particulier ciblé par lui même");
		return clientDao.lireToutesLesClientsByidConseiller(conseiller);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trio.proxibanquev3.service.IClientService#lireUnClient(long)
	 */
	@Override
	public Client lireUnClient(long idClient) throws DAOException {
		logger.info("On demande à lire un client par son identifiant");
		return clientDao.lireUnClient(idClient);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trio.proxibanquev3.service.IClientService#mAJUnClient(com.trio.
	 * proxibanquev3.domaine.Client)
	 */
	@Override
	public void mAJUnClient(Client client) throws DAOException {
		logger.info("On demande à mettre à jour un client");
		clientDao.mAJUnClient(client);
		logger.info("On a fini la mise à jour du client (peut avoir échoué)");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.trio.proxibanquev3.service.IClientService#supprimerUnClient(com.trio.
	 * proxibanquev3.domaine.Client)
	 */
	@Override
	public void supprimerUnClient(Client client) throws DAOException {
		logger.info("On demande a supprimer un client");
		clientDao.supprimerUnClient(client);
		logger.info("On a fini la suppresion du client (peut avoir échoué)");
	}

	@PostConstruct
	public void logapresconstruction() {
		logger.debug("service construit client");
	}

}
