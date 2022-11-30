package fr.wargame.modele.actions;

import java.util.ArrayList;

import fr.wargame.modele.plateaucase.*;
import fr.wargame.modele.unites.Unite;

/**
 * Regroupe les méthodes permettant de gérer les cases visibles ou non.
 * <p>
 * Consiste en une récurrence qui dépend d'un entier représentant la vision de l'unité.
 * La récurrence teste si la case est visible puis l'ajoute à une liste.
 */

public class CompteurDistance {

	/**
	 * @param plateau 2D pour accéder aux cases
	 * @param casesVisibles pour ajouter la case si elle est visible
	 * @param nbPas la distance actuelle par rapport à la case initiale
	 * @param xCaseATester l'entier représentant la coordonnée horizontale de la case à tester
	 * @param yCaseATester l'entier représentant la coordonnée verticale de la case à tester
	 * @param vision champ de vision d'une unité
	 */
    public static void verifierCaseVisible(Case[][] plateau, ArrayList<ArrayList<Integer>> casesVisibles, int nbPas, int xCaseATester, int yCaseATester, int vision) {
		if ( (xCaseATester>=0) && (xCaseATester<plateau.length) && (yCaseATester>=0) && (yCaseATester<plateau[0].length) ) {
            if ( (plateau[xCaseATester][yCaseATester].getTypeCase().equals(Case.MONTAGNE)) || (plateau[xCaseATester][yCaseATester].getTypeCase().equals(Case.FORET)) ) 
                nbPas+=2;
            else 
                nbPas+=1;	
            
            ArrayList<Integer> coordVoisin = new ArrayList<Integer>();
            coordVoisin.add(0, xCaseATester); coordVoisin.add(1, yCaseATester);
            if (!(casesVisibles.contains(coordVoisin)))
                casesVisibles.add(coordVoisin);
            
            listerCasesVisibles(plateau, casesVisibles, vision, nbPas, xCaseATester, yCaseATester);
		}
	}
	
    /**
	 * @param plateau 2D pour accéder aux cases
	 * @param casesVisibles pour lister les cases visibles
	 * @param vision champ de vision d'une unité
	 * @param nbPas la distance actuelle par rapport à la case initiale
	 * @param xCaseATester l'entier représentant la coordonnée horizontale de la case à tester
	 * @param yCaseATester l'entier représentant la coordonnée verticale de la case à tester
     */
	public static void listerCasesVisibles(Case[][] plateau, ArrayList<ArrayList<Integer>> casesVisibles, int vision, int nbPas, int xCaseATester, int yCaseATester) {
		if (nbPas < vision) {
            verifierCaseVisible(plateau, casesVisibles, nbPas, xCaseATester, yCaseATester-1, vision);
			verifierCaseVisible(plateau, casesVisibles, nbPas, xCaseATester, yCaseATester-1, vision);
			verifierCaseVisible(plateau, casesVisibles, nbPas, xCaseATester, yCaseATester+1, vision);
			verifierCaseVisible(plateau, casesVisibles, nbPas, xCaseATester-1, yCaseATester, vision);
			verifierCaseVisible(plateau, casesVisibles, nbPas, xCaseATester+1, yCaseATester, vision);
			if (xCaseATester%2 == 0) {
				verifierCaseVisible(plateau, casesVisibles, nbPas, xCaseATester-1, yCaseATester-1, vision);	
				verifierCaseVisible(plateau, casesVisibles, nbPas, xCaseATester+1, yCaseATester-1, vision);
			}
			else {
				verifierCaseVisible(plateau, casesVisibles, nbPas, xCaseATester-1,yCaseATester+1, vision);
				verifierCaseVisible(plateau, casesVisibles, nbPas, xCaseATester+1, yCaseATester+1, vision);
			}
		}
	}

	/**
	 * @param plateau 2D pour accéder aux cases
	 * @param casesVisibles pour lister les cases visibles
	 * @param vision champ de vision d'une unité
	 * @param unite unité à considérer
	 */
    public static void debutlisterCasesVisibles(Case[][] plateau, ArrayList<ArrayList<Integer>> casesVisibles, int vision, Unite unite) {
        ArrayList<Integer> coordUnite = new ArrayList<Integer>();

        coordUnite.add(0, unite.getCoords()[0]); coordUnite.add(1, unite.getCoords()[1]);
        casesVisibles.add(coordUnite);
        listerCasesVisibles(plateau, casesVisibles, vision, 0, unite.getCoords()[0], unite.getCoords()[1]);
    }
}
