package fr.wargame.modele.plateaucase;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Regroupe les différentes méthodes liées au fonctionnement du plateau.
 * Le plateau est constitué de cases. 
 * @see Case#Case() pour plus d'informations
 */
public class PlateauMod {
	public Case[][] plateauJeu = new Case[20][20];
	
	public PlateauMod(){
		this.plateauJeu = new Case[20][20];
		for(int i=0; i<this.plateauJeu.length; i++) {
			for(int j=0; j<this.plateauJeu[0].length; j++) {
				this.plateauJeu[i][j] = new Case();
			}
		}
		initialiserPlateau();
	}

	/**
	 * Méthode permettant de lire le fichier "plateau.txt" pour créer le plateau de jeu.
	 * Ce fichier texte regroupe les différents cases que comprend le plateau de jeu.
	 */
	public void initialiserPlateau() {
		String[][] plateauFich = new String[20][20];
		int i = 0, j = 0;

		try {
			BufferedReader tampon = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/plateau.txt")));
			String ligne;

			while ((ligne = tampon.readLine()) != null) {
				j = 0;
				for(String mot : ligne.split(" ")) {
					plateauFich[i][j] = mot;
					j++;
				}
				i++;
			}
			for(i=0;i<20;i++) {
				for(j=0;j<20;j++) {
					switch(plateauFich[i][j]) {
						case Case.FORET:
							this.plateauJeu[i][j].setTypeCase(Case.FORET);
							break;
						case Case.MER:
							this.plateauJeu[i][j].setTypeCase(Case.MER);
							break;
						case Case.MONTAGNE:
							this.plateauJeu[i][j].setTypeCase(Case.MONTAGNE);
							break;
						case Case.VILLAGE:
							this.plateauJeu[i][j].setTypeCase(Case.VILLAGE);
							break;
					}
				}
			}
			tampon.close();
		} catch(Exception e) {
			System.exit(0);
		}
	}
}
