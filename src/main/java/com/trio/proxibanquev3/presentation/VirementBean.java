package com.trio.proxibanquev3.presentation;

import com.trio.proxibanquev3.domaine.Client;
import com.trio.proxibanquev3.domaine.CompteBancaire;
import com.trio.proxibanquev3.exception.DAOException;
import com.trio.proxibanquev3.exception.ServiceException;
import com.trio.proxibanquev3.service.ClientService;
import com.trio.proxibanquev3.service.CompteBancaireService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Managed Bean concernant les virements
 * 
 * @author Trio
 *
 */
@ManagedBean(name = "virementbean")
@Named
@ConversationScoped
public class VirementBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Client> listeDesClients = new ArrayList<Client>();
	private List<CompteBancaire> listeDesComptes = new ArrayList<CompteBancaire>();
	private String client1;
	private String compte1;
	private Map<String, String> clients1;
	private Map<String, String> comptes1;

	private String client2;
	private String compte2;
	private Map<String, String> clients2;
	private Map<String, String> comptes2;

	private Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();

	private String sommeADebiter;

	@Inject
	CompteBancaireService cptService;// = new CompteBancaireService();

	@Inject
	ClientService clientService; // = new ClientService();


	public VirementBean() {
	}

	/**
	 * @return the sommeADebiter
	 */
	public String getSommeADebiter() {
		return sommeADebiter;
	}

	/**
	 * @param sommeADebiter
	 *            the sommeADebiter to set
	 */
	public void setSommeADebiter(String sommeADebiter) {
		this.sommeADebiter = sommeADebiter;
	}

	/**
	 * @return the client1
	 */
	public String getClient1() {
		return client1;
	}

	/**
	 * @param client1
	 *            the client1 to set
	 */
	public void setClient1(String client1) {
		this.client1 = client1;
	}

	/**
	 * @return the compte1
	 */
	public String getCompte1() {
		return compte1;
	}

	/**
	 * @param compte1
	 *            the compte1 to set
	 */
	public void setCompte1(String compte1) {
		this.compte1 = compte1;
	}

	/**
	 * @return the clients1
	 */
	public Map<String, String> getClients1() {
		return clients1;
	}

	/**
	 * @param clients1
	 *            the clients1 to set
	 */
	public void setClients1(Map<String, String> clients1) {
		this.clients1 = clients1;
	}

	/**
	 * @return the comptes1
	 */
	public Map<String, String> getComptes1() {
		return comptes1;
	}

	/**
	 * @param comptes1
	 *            the comptes1 to set
	 */
	public void setComptes1(Map<String, String> comptes1) {
		this.comptes1 = comptes1;
	}

	/**
	 * @return the client2
	 */
	public String getClient2() {
		return client2;
	}

	/**
	 * @param client2
	 *            the client2 to set
	 */
	public void setClient2(String client2) {
		this.client2 = client2;
	}

	/**
	 * @return the compte2
	 */
	public String getCompte2() {
		return compte2;
	}

	/**
	 * @param compte2
	 *            the compte2 to set
	 */
	public void setCompte2(String compte2) {
		this.compte2 = compte2;
	}

	/**
	 * @return the clients2
	 */
	public Map<String, String> getClients2() {
		return clients2;
	}

	/**
	 * @param clients2
	 *            the clients2 to set
	 */
	public void setClients2(Map<String, String> clients2) {
		this.clients2 = clients2;
	}

	/**
	 * @return the comptes2
	 */
	public Map<String, String> getComptes2() {
		return comptes2;
	}

	/**
	 * @param comptes2
	 *            the comptes2 to set
	 */
	public void setComptes2(Map<String, String> comptes2) {
		this.comptes2 = comptes2;
	}

	public Map<String, String> getClients() {
		return clients1;
	}

	public void setClients(Map<String, String> clients) {
		this.clients1 = clients;
	}

	public Map<String, String> getComptes() {
		return comptes1;
	}

	public void setComptes(Map<String, String> comptes) {
		this.comptes1 = comptes;
	}

	public void setData(Map<String, Map<String, String>> data) {
		this.data = data;
	}



	/**
	 * Méthode init en post construct qui permet de charger la liste des
	 * clients, ainsi que la liste de leurs comptes bancaires associés, et de
	 * les mettre dans une map.
	 */
	@PostConstruct
	public void init() {
		try {
			listeDesClients = clientService.lireToutesLesClients();
			clients1 = new HashMap<String, String>();
			int tailleListeDesClients = listeDesClients.size();

			for (int i = 0; i < tailleListeDesClients; i++) {
				String prenom = listeDesClients.get(i).getPrenom();
				String nom = listeDesClients.get(i).getNom();
				long id = listeDesClients.get(i).getIdPersonne();
				String prenomEtNom = prenom + " " + nom + ", id : " + String.valueOf(id);
				clients1.put(prenomEtNom, prenomEtNom);
				Map<String, String> map = new HashMap<String, String>();
				listeDesComptes = listeDesClients.get(i).getComptes();
				int tailleListeDesComptes = listeDesComptes.size();
				for (int j = 0; j < tailleListeDesComptes; j++) {
					long numCompte = listeDesComptes.get(j).getNumCompte();
					String numeroDeCompte = String.valueOf(numCompte);
					double sld = listeDesComptes.get(j).getSolde();
					String solde = String.valueOf(sld);
					long idCompte = listeDesComptes.get(j).getIdCompte();
					String idCompteStr = String.valueOf(idCompte);
					String numEtSolde = numeroDeCompte + " solde de : " + solde + " € , id =" + idCompteStr;
					map.put(numEtSolde, numEtSolde);
				}
				data.put(prenomEtNom, map);

			}

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @return the listeDesClients
	 */
	public List<Client> getListeDesClients() {
		return listeDesClients;
	}

	/**
	 * @param listeDesClients
	 *            the listeDesClients to set
	 */
	public void setListeDesClients(List<Client> listeDesClients) {
		this.listeDesClients = listeDesClients;
	}

	/**
	 * @return the listeDesComptes
	 */
	public List<CompteBancaire> getListeDesComptes() {
		return listeDesComptes;
	}

	/**
	 * @param listeDesComptes
	 *            the listeDesComptes to set
	 */
	public void setListeDesComptes(List<CompteBancaire> listeDesComptes) {
		this.listeDesComptes = listeDesComptes;
	}

	/**
	 * @return the client1
	 */
	public String getClient() {
		return client1;
	}

	/**
	 * @param client1
	 *            the client1 to set
	 */
	public void setClient(String client) {
		this.client1 = client;
	}

	/**
	 * @return the compte1
	 */
	public String getCompte() {
		return compte1;
	}

	/**
	 * @param compte1
	 *            the compte1 to set
	 */
	public void setCompte(String compte) {
		this.compte1 = compte;
	}

	public Map<String, Map<String, String>> getData() {
		return data;
	}

	/**
	 * Méthode qui permet de charger en mémoire les comptes bancaires associés
	 * au client à débiter
	 */
	public void chargementDesComptesAssociesClient1() {

		if (client1 != null && !client1.equals(""))
			comptes1 = data.get(client1);
		else
			comptes1 = new HashMap<String, String>();
	}

	/**
	 * Méthode qui permet de charger en mémoire les comptes bancaires associés
	 * au client à créditer
	 */
	public void chargementDesComptesAssociesClient2() {

		if (client2 != null && !client2.equals(""))
			comptes2 = data.get(client2);
		else
			comptes2 = new HashMap<String, String>();
	}

	/**
	 * Méthode qui permet, lors du clic sur le bouton, de valider le virement,
	 * en prenant en compte les deux comptes sélectionnés et le montant du
	 * virement.
	 * 
	 * @return un String qui redirige vers la page suivante
	 */
	public String validationVirement() {
		LoginBean loginBean = (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("loginBean");
		FacesMessage msg = null;
		String resultat;
		Long idCompte1;
		String resultat2;
		Long idCompte2;
		double montant;
		CompteBancaire compteDebite = null;
		CompteBancaire compteCredite = null;
		if (compte1 != null && client1 != null && compte2 != null && client2 != null && sommeADebiter != null) {
			resultat = compte1.substring(compte1.lastIndexOf("=") + 1, compte1.length());
			idCompte1 = Long.parseLong(resultat);
			resultat = compte2.substring(compte2.lastIndexOf("=") + 1, compte2.length());
			idCompte2 = Long.parseLong(resultat);
			montant = Double.parseDouble(sommeADebiter);
			try {
				compteDebite = cptService.lireUnCompteBancaire(idCompte1);
				compteCredite = cptService.lireUnCompteBancaire(idCompte2);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				cptService.virement(compteDebite, compteCredite, montant);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String redirectVrai = loginBean.getNavigateBean().redirectToMenuConseiller();

			return redirectVrai;

		} else
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalide", "comptes et/ou montant non selectionné.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		String redirect = loginBean.getNavigateBean()
				.redirectToError("Erreur lors de la tentative de virement. La procédure est annulée !");
		return redirect;

	}

}
