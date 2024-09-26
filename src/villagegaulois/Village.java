package villagegaulois;

import Exceptions_personnalisees.VillageSansChefException;
import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	Marche marche;

	public Village(String nom, int nbVillageoisMaximum,int nbEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche=new Marche(nbEtal);
	}

	//class interne marche
	private static class Marche{
		//attributs
		private Etal[] etals;

		//constructeur
		public Marche(int nbEtal) {
			etals=new Etal[nbEtal];
			for(int i=0;i<nbEtal;i++) {
				etals[i]=new Etal();
			}
		}
		
		//m�thodes
		private void utiliserEtal(int indiceEtal,Gaulois vendeur,String produit,int nbProduit) {
			if (etals[indiceEtal].isEtalOccupe()) {
				System.out.println("L'�tal "+indiceEtal+" est d�j� occup�");	
			}else {
				etals[indiceEtal].occuperEtal(vendeur,produit,nbProduit);
			}
		}
		
		private int trouverEtalLibre() {
			for(int i=0;i<etals.length;i++) {
				if(!(etals[i].isEtalOccupe())) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			Etal[] etalAvcProduit;
			int j=0;
			for(int i=0;i<etals.length;i++) {
				if(etals[i].contientProduit(produit)) {
				  j++;
				}
			}
			etalAvcProduit=new Etal[j];
			j=0;
			for(int i=0;i<etals.length;i++) {
				if(etals[i].contientProduit(produit)) {
				  etalAvcProduit[j]=etals[i];
				  j++;
				}
			}
			return etalAvcProduit;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0;i<etals.length;i++) {
				if(etals[i].getVendeur().getNom()==gaulois.getNom()) {
					return etals[i];
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			int nbEtalVide=0;
			StringBuilder afficheMarche=new StringBuilder();
			for(int i=0;i<etals.length;i++) {
				if(etals[i].isEtalOccupe()) {
					afficheMarche.append(etals[i].afficherEtal());
				}else {
					nbEtalVide++;
				}
			}
			afficheMarche.append("Il reste "+nbEtalVide+" �tals non tulis�s dans le march�.\n");
			return afficheMarche.toString();
		}
				
	}
	//fin classe interne
	
	
	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}
	
	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		StringBuilder chaine = new StringBuilder();
		if(chef==null) {
			throw new VillageSansChefException("Le village n'a pas de chef: impossible d'afficher ses habitants.\n");
		}
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur,String produit,int nbProduit) {
		StringBuilder install=new StringBuilder();
		install.append(vendeur.getNom()+" cherche un endroit pour vendre "+nbProduit+" "+produit+".\n");
		int indice=marche.trouverEtalLibre();
		if(indice==-1) {
			install.append("Il n'y a pas '�tal libre\n");
		}else {
			marche.utiliserEtal(indice, vendeur, produit, nbProduit);
			install.append("Le vendeur "+vendeur.getNom()+" vend des "+produit+" � l'�tal n�"+(indice+1)+".\n");
			
		}
		return install.toString();	
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder vendeur=new StringBuilder();
		Etal[] etalsprod=marche.trouverEtals(produit);
		if(etalsprod.length==0) {
			vendeur.append("Il n'y a pas de vendeur qui propose des "+produit+ " au march�.\n");
		}else if(etalsprod.length==1) {
			vendeur.append("Seul le vendeur "+etalsprod[0].getVendeur().getNom()+" vend des "+produit+" au march�.\n");
		}else {
			vendeur.append("Les vendeurs qui proposent des "+produit+" sont:\n");
			for(int i=0;i<etalsprod.length;i++) {
				vendeur.append("-" +etalsprod[i].getVendeur().getNom()+"\n");
			}
		}
		return vendeur.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		return rechercherEtal(vendeur).libererEtal();
	}
	
	public String afficherMarche() {
		StringBuilder affMarche=new StringBuilder();
		affMarche.append("Le march� du village "+this.nom+" poss�de plsuieurs �tals:\n");
		affMarche.append(marche.afficherMarche());
		return affMarche.toString();
	}
}