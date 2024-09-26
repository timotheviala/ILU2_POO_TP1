package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Etal etal=new Etal();
		Gaulois gaulois=new Gaulois("Asterix",3);
		etal.occuperEtal(gaulois, "fleurs",7);
		etal.acheterProduit(1, gaulois);
		Village village=new Village("Le village",4,4);
		village.ajouterHabitant(gaulois);
		village.afficherVillageois();
		System.out.println("Fin du test");
	}

}
