package fr.wargame.controleur.main;

import java.util.ArrayList;

import fr.wargame.controleur.gestionclic.*;
import fr.wargame.modele.actions.*;
import fr.wargame.modele.ia.*;
import fr.wargame.modele.plateaucase.*;
import fr.wargame.modele.unites.*;
import fr.wargame.vue.accueil.Home;
import fr.wargame.vue.ecranfin.*;
import fr.wargame.vue.fenetrejeu.*;

/**
 * Classe main regroupant les méthodes principales et le main
 */
public class Main {

	static boolean estBloque = false;
	static int tour = 0;
	public static ArrayList<ArrayList<Unite>> unites = new ArrayList<>();
	public static boolean[] estIA = {false, false};

	/**
	 * @param b Booléen représentant le fait que le menu soit encore actif (et que le jeu n'ait pas encore démarré)
	 */
	public static void setBloque(boolean b) { estBloque = b; }

	/**
	 * @return Renvoie le booléen représentant le fait que le menu soit encore actif (et que le jeu n'ait pas encore démarré)
	 */
	public static boolean getBloque() { return estBloque; }

	/**
	 * @param n Fixe le tour du jeu (en cas de récupération d'une partie par la sauvegarde)
	 */
	public static void setTour(int n) { tour = n; }

	/**
	 * @return l'entier représentant le tour de jeu
	 */
	public static int getTour() { return tour; }

	/**
	 * @param u Fixe les unités des deux joueurs (en cas de récupération d'une sauvegarde)
	 */
	public static void setUnites(ArrayList<ArrayList<Unite>> u) { unites = u; }

	/**
	 * @return Les unités des deux joueurs
	 */
	public static ArrayList<ArrayList<Unite>> getUnites() { return unites; }

	/**
	 * Fonction qui gère l'affichage du brouillard sur la carte du jeu
	 * @param fenJeu Pour l'affichage du brouillard dans la fenêtre de jeu
	 * @param Armee L'ensemble des unités du joueur actuel
	 * @param plateauJeu Le plateau du modèle pour lister les cases visibles (et donc à ne pas cacher)
	 */
	static void gestionBrouillard (FenetreJeu fenJeu, ArrayList<Unite> Armee, Case[][] plateauJeu) {

		ArrayList<ArrayList<Integer>> casesVisibles = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> coordTest = new ArrayList<Integer>();

		for (int i=0; i<20; i++) {
			for (int j=0; j<20; j++) 
				fenJeu.getPlateauG().effacerBrouillard(Conversion.modeleVersVue(new int[] {i,j}));
		}
		for (Unite unite : Armee) {
			CompteurDistance.debutlisterCasesVisibles(plateauJeu, casesVisibles, unite.getStatsActuelles().getDEP(), unite);
		}
		for (int i=0; i<20; i++) {
			for (int j=0; j<20; j++) {
				coordTest.add(0,i); coordTest.add(1,j);
				if (!(casesVisibles.contains(coordTest)))  
					fenJeu.getPlateauG().afficherBrouillard(Conversion.modeleVersVue(new int[] {i,j}));
				coordTest.clear();
			}
		}
	}

	/**
	 * Permet de gérer le chargement des attaques ultimes des différentes unités
	 * @param unite L'unité actuelle dont on doit changer la valeur de chargement de son attaque ultime
	 */
	static void changerUltime(Unite unite) {
		switch(unite.donnerTypeUnite()) {
			case "archer":
				unite.setUltime(unite.getUltime() + 35);
				break;
			case "mage":
				unite.setUltime(unite.getUltime() + 25);
				break;
			case "lancier":
				unite.setUltime(unite.getUltime() + 20);
				break;
			case "renard":
				unite.setUltime(unite.getUltime() + 15);
				break;
			case "dragon":
				unite.setUltime(unite.getUltime() + 10);
				break;
		}
	}

	/**
	 * Le main du jeu
	 * @param args N'est pas utilisé
	 */
	public static void main(String[] args) {
		unites.add(new ArrayList<Unite>());
		unites.add(new ArrayList<Unite>());
		setBloque(true);
		Home fenetreAccueil = new Home();
		fenetreAccueil.setVisible(true);

		while(estBloque==true) {System.out.print("");} // Obligatoire pour ne pas bloquer le while

		FenetreJeu fenJeu = new FenetreJeu(false);
		Clic clic = fenJeu.clic;
		Unite uniteActuelle;
		int compteurAJouer = 0, indiceKO = 0;
		boolean estKO = false, neBougePas = false;
		PlateauMod plateauMod = new PlateauMod();
		ArrayList<ArrayList<Integer>> casesDispos = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> indicesEnnemisAttaquables = new ArrayList<Integer>();

		fenJeu.getPlateauG().afficherPlateau(plateauMod.plateauJeu);

		// ----------- Initialisation des unités et du plateau ------------------

		for(int i=0; i<unites.get(0).size(); i++) {
			unites.get(0).get(i).setTypeCase(plateauMod.plateauJeu[unites.get(0).get(i).getCoords()[0]][unites.get(0).get(i).getCoords()[1]].getTypeCase());
			plateauMod.plateauJeu[unites.get(0).get(i).getCoords()[0]][unites.get(0).get(i).getCoords()[1]].setEstOccupee(true);
			fenJeu.getPlateauG().afficherUnite(unites.get(0).get(i),Conversion.modeleVersVue(unites.get(0).get(i).getCoords()), 0);
		}
		for(int i=0; i<unites.get(1).size(); i++) {
			unites.get(1).get(i).setTypeCase(plateauMod.plateauJeu[unites.get(1).get(i).getCoords()[0]][unites.get(1).get(i).getCoords()[1]].getTypeCase());
			plateauMod.plateauJeu[unites.get(1).get(i).getCoords()[0]][unites.get(1).get(i).getCoords()[1]].setEstOccupee(true);
			fenJeu.getPlateauG().afficherUnite(unites.get(1).get(i),Conversion.modeleVersVue(unites.get(1).get(i).getCoords()), 1);
		}

		// ----------- Début du jeu ------------------ 

		if(tour > 0) {  // Le tour a été récupéré d'une sauvegarde
			if(!estIA[tour%2]) {
				for (Unite unite : unites.get(tour%2))
					unite.changerEstJouee();
			}
			tour--;
		} 

		jeu : while(unites.get(0).size() > 0 && unites.get(1).size() > 0) {
			tour++;

			// En cas de jeu par l'IA
			if(estIA[tour%2]) {
				for (Unite unite : unites.get(tour%2)) {
					changerUltime(unite);
					fenJeu.getPlateauG().effacerUnite(Conversion.modeleVersVue(unite.getCoords()));
					if((indiceKO = IA.actionIA(unite, plateauMod.plateauJeu, unites.get(1 - tour%2), tour%2)) != -1) {
						fenJeu.getPlateauG().effacerUnite(Conversion.modeleVersVue(unites.get(1 - tour % 2).get(indiceKO).getCoords()));
						unites.get(1 - tour%2).remove(indiceKO);
					}
					fenJeu.getPlateauG().afficherUnite(unite, Conversion.modeleVersVue(unite.getCoords()), tour%2);

					if(unites.get(1 - tour%2).size() == 0)
						break jeu;
				}
				for(Unite unite : unites.get(1 - tour%2)) {
					fenJeu.getPlateauG().effacerUnite(Conversion.modeleVersVue(unite.getCoords()));
					fenJeu.getPlateauG().afficherUnite(unite, Conversion.modeleVersVue(unite.getCoords()), 1- tour%2);
				}
			}
			else { // En cas de jeu par un joueur humain
				compteurAJouer = 0;
				for (Unite unite : unites.get(tour%2)) {
					changerUltime(unite);
					unite.changerEstJouee();
					compteurAJouer++;
					fenJeu.getPlateauG().effacerUnite(Conversion.modeleVersVue(unite.getCoords()));
					fenJeu.getPlateauG().afficherUnite(unite, Conversion.modeleVersVue(unite.getCoords()), tour%2);
					unite.retirerMalus();
				}
				gestionBrouillard(fenJeu, unites.get(tour%2), plateauMod.plateauJeu);
				while (compteurAJouer > 0) {
					fenJeu.getFenStats().btnPasser.setEnabled(true);
					if(unites.get(1 - tour%2).size() == 0)
						break jeu;

					// 1 - Sélection de l'unité à utiliser
					selection : while(true) {
						System.out.print(""); // Obligatoire pour ne pas bloquer le while

						if (clic.getAClique()) {
							clic.setFinClic();
							if(clic.getVeutPasser())
							{	
								for (Unite unite : unites.get(tour%2)) {
									if (!unite.estDejaJouee()) {
										unite.soigner();
										unite.changerEstJouee();
										fenJeu.getPlateauG().effacerUnite(Conversion.modeleVersVue(unite.getCoords()));
										fenJeu.getPlateauG().afficherUnite(unite, Conversion.modeleVersVue(unite.getCoords()), tour%2);
										compteurAJouer = 0;
									}
								}
								clic.setVeutPasser(false);
								continue jeu;
							}
							for (Unite unite : unites.get(tour%2)) {
								if (! unite.estDejaJouee()
									&& unite.getCoords()[0] == clic.getCoordsM()[0] 
									&& unite.getCoords()[1] == clic.getCoordsM()[1]) {

									fenJeu.getPlateauG().effacerHexaSelect();
									fenJeu.getFenStats().effacerDyn();
									fenJeu.getPlateauG().afficherHexaSelect(Conversion.modeleVersVue(unite.getCoords()), "S");
									fenJeu.getFenStats().afficherDyn(unite);
									uniteActuelle = unite;

									break selection;
								}
							}
						}
					}
					fenJeu.getFenStats().btnPasser.setEnabled(false);
					fenJeu.getFenStats().effacerDyn();
					fenJeu.getFenStats().afficherDyn(uniteActuelle);
					Deplacement.listerCasesPossibles(plateauMod.plateauJeu, casesDispos, uniteActuelle, 0, uniteActuelle.getCoords()[0], uniteActuelle.getCoords()[1]);

					if(casesDispos.size() != 0) {
						for(int i=0; i<casesDispos.size(); i++) {
							fenJeu.getPlateauG().afficherHexaSelect(Conversion.modeleVersVue(new int[]{casesDispos.get(i).get(0),casesDispos.get(i).get(1)}), "D");
						}

						// 2 - Sélection de la destination
						deplacement : while(true) {
							System.out.print("");

							if (clic.getAClique()) {
								clic.setFinClic();
								if(uniteActuelle.getCoords()[0] == clic.getCoordsM()[0] && uniteActuelle.getCoords()[1] == clic.getCoordsM()[1]) {
									fenJeu.getPlateauG().effacerHexaSelect();
									neBougePas = true;

									break deplacement;
								}
								for(int i=0; i<casesDispos.size(); i++) {
									if (casesDispos.get(i).get(0) == clic.getCoordsM()[0] && casesDispos.get(i).get(1) == clic.getCoordsM()[1]) {
										fenJeu.getPlateauG().effacerHexaSelect();
										fenJeu.getPlateauG().effacerUnite(Conversion.modeleVersVue(uniteActuelle.getCoords()));
										Deplacement.deplacerUnite(plateauMod.plateauJeu, casesDispos, uniteActuelle, clic.getCoordsM()[0], clic.getCoordsM()[1]);
										fenJeu.getPlateauG().afficherUnite(uniteActuelle, Conversion.modeleVersVue(uniteActuelle.getCoords()), tour%2);
										neBougePas = false;
										gestionBrouillard(fenJeu, unites.get(tour%2), plateauMod.plateauJeu);

										break deplacement;
									}
								}
							}
						}
					}
					try {
						fenJeu.getFenStats().btnUlt.setEnabled(false);
					} catch(Exception e) {}

					if(clic.getVeutUlti()) 
						Recherchecibles.listerEnnemisAttaquables(plateauMod.plateauJeu, unites.get(1 - tour%2), indicesEnnemisAttaquables, uniteActuelle.getStatsActuelles().getPOR()[1], 0, uniteActuelle.getCoords()[0],uniteActuelle.getCoords()[1]);
					else
						Recherchecibles.listerEnnemisAttaquables(plateauMod.plateauJeu, unites.get(1 - tour%2), indicesEnnemisAttaquables, uniteActuelle.getStatsActuelles().getPOR()[0], 0, uniteActuelle.getCoords()[0],uniteActuelle.getCoords()[1]);

					if(indicesEnnemisAttaquables.size() > 0) {
						for(int i=0; i<indicesEnnemisAttaquables.size(); i++) {
							fenJeu.getPlateauG().afficherHexaSelect(Conversion.modeleVersVue(new int[]
							{unites.get(1 - tour%2).get(indicesEnnemisAttaquables.get(i)).getCoords()[0], 
							unites.get(1 - tour%2).get(indicesEnnemisAttaquables.get(i)).getCoords()[1]}), "A");
							if(clic.getVeutUlti()) {
								fenJeu.getPlateauG().afficherDegatsUnite(Conversion.modeleVersVue(unites.get(1 - tour%2).get(indicesEnnemisAttaquables.get(i)).getCoords()),
									unites.get(1 - tour%2).get(indicesEnnemisAttaquables.get(i)).getStatsActuelles().getPV() 
									- uniteActuelle.attaquerUltime(unites.get(1 - tour%2).get(indicesEnnemisAttaquables.get(i)), true));
							}
							else {
								fenJeu.getPlateauG().afficherDegatsUnite(Conversion.modeleVersVue(unites.get(1 - tour%2).get(indicesEnnemisAttaquables.get(i)).getCoords()),
									unites.get(1 - tour%2).get(indicesEnnemisAttaquables.get(i)).getStatsActuelles().getPV() 
									- uniteActuelle.attaquer(unites.get(1 - tour%2).get(indicesEnnemisAttaquables.get(i)), true));
							}
						}

						// 3 - Sélection de l'unité à attaquer
						attaque : while(true) {
							System.out.print("");

							if (clic.getAClique()) {
								clic.setFinClic();
								if(uniteActuelle.getCoords()[0] == clic.getCoordsM()[0] && uniteActuelle.getCoords()[1] == clic.getCoordsM()[1]) {
									fenJeu.getPlateauG().effacerHexaSelect();
									if(neBougePas)
										uniteActuelle.soigner();
									for(int j=0; j<indicesEnnemisAttaquables.size(); j++)
										fenJeu.getPlateauG().effacerDegatsUnite(Conversion.modeleVersVue(unites.get(1 - tour % 2).get(indicesEnnemisAttaquables.get(j)).getCoords()));
									
									break attaque;
								}
								for(int i=0; i<indicesEnnemisAttaquables.size(); i++) {
									if (unites.get(1 - tour%2).get(indicesEnnemisAttaquables.get(i)).getCoords()[0] == clic.getCoordsM()[0]
										&& unites.get(1 - tour%2).get(indicesEnnemisAttaquables.get(i)).getCoords()[1] == clic.getCoordsM()[1]) {
										
										for(int j=0; j<indicesEnnemisAttaquables.size(); j++)
											fenJeu.getPlateauG().effacerDegatsUnite(Conversion.modeleVersVue(unites.get(1 - tour % 2).get(indicesEnnemisAttaquables.get(j)).getCoords()));
										fenJeu.getPlateauG().effacerHexaSelect();

										if(clic.getVeutUlti())
											estKO = (uniteActuelle.attaquerUltime(unites.get(1 - tour%2).get(indicesEnnemisAttaquables.get(i)), false) == 0 ? true : false);
										else
											estKO = (uniteActuelle.attaquer(unites.get(1 - tour%2).get(indicesEnnemisAttaquables.get(i)), false) == 0 ? true : false);

										fenJeu.getPlateauG().effacerUnite(Conversion.modeleVersVue(unites.get(1 - tour % 2).get(indicesEnnemisAttaquables.get(i)).getCoords()));

										if(estKO) {
											plateauMod.plateauJeu[unites.get(1 - tour%2).get(indicesEnnemisAttaquables.get(i)).getCoords()[0]]
											[unites.get(1 - tour%2).get(indicesEnnemisAttaquables.get(i)).getCoords()[1]].setEstOccupee(false);
											unites.get(1 - tour%2).remove((int) indicesEnnemisAttaquables.get(i));
										}
										else 
											fenJeu.getPlateauG().afficherUnite(unites.get(1 - tour % 2).get(indicesEnnemisAttaquables.get(i)),
												Conversion.modeleVersVue(unites.get(1 - tour % 2).get(indicesEnnemisAttaquables.get(i)).getCoords()), 1 - tour%2);

										break attaque;
									}
								}
							}
						}
					}
					else if(neBougePas)
						uniteActuelle.soigner();

					uniteActuelle.changerEstJouee();
					clic.setVeutUlti(false);
					fenJeu.getPlateauG().effacerUnite(Conversion.modeleVersVue(uniteActuelle.getCoords()));
					fenJeu.getPlateauG().afficherUnite(uniteActuelle, Conversion.modeleVersVue(uniteActuelle.getCoords()), tour%2);

					casesDispos.clear();
					indicesEnnemisAttaquables.clear();
					compteurAJouer--;
				}
			}
		}
		new EcranFin(estIA[tour%2],tour%2, fenJeu);
	}
}
