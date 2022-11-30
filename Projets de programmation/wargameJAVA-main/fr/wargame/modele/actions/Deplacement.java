package fr.wargame.modele.actions;

import java.util.ArrayList;

import fr.wargame.modele.plateaucase.*;
import fr.wargame.modele.unites.*;

/**
 * Regroupe les méthodes permettant à une unité de se déplacer.
 * Consiste en une récurrence qui dépend d'un entier représentant la capacité de déplacement.
 * La récurrence teste si la case est valide puis l'ajoute à une liste.
 */
public class Deplacement {

	/**
	 * @param plateau pour avoir les informations sur les cases souhaitées
	 * @param unite pour avoir accès à la classe de l'unité
	 * @param xCaseATester l'entier représentant la coordonnée horizontale de la case à tester
	 * @param yCaseATester l'entier représentant la coordonnée verticale de la case à tester
	 * @return vrai si la case est accessible, sinon faux 
	 */
	public static boolean verifierCaseAccessible(Case[][] plateau, Unite unite, int xCaseATester, int yCaseATester) {
		if (plateau[xCaseATester][yCaseATester].getEstOccupee()) {
			return false;
		}
		else if ( (!(unite instanceof UniteVolante)) && (plateau[xCaseATester][yCaseATester].getTypeCase().equals(Case.MER)) ) {
			return false;
		}
		else if ( (unite instanceof UniteMontee) && (plateau[xCaseATester][yCaseATester].getTypeCase().equals(Case.FORET)) ) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param plateau 2D pour accéder aux cases
	 * @param casesDispos pour ajouter la case à la liste
	 * @param unite l'unité à considérer
	 * @param nbPas la distance actuelle par rapport à la case initiale
	 * @param xCaseATester l'entier représentant la coordonnée horizontale de la case à tester
	 * @param yCaseATester l'entier représentant la coordonnée verticale de la case à tester
	*/
	public static void verifierCaseLibre(Case[][] plateau, ArrayList<ArrayList<Integer>> casesDispos, Unite unite, int nbPas, int xCaseATester, int yCaseATester) {
		if ( (xCaseATester>=0) && (xCaseATester<plateau.length) && (yCaseATester>=0) && (yCaseATester<plateau[0].length) ) {
			if ( verifierCaseAccessible(plateau, unite, xCaseATester, yCaseATester) ) {
				if ( (plateau[xCaseATester][yCaseATester].getTypeCase().equals(Case.MONTAGNE)) || (!(unite instanceof UniteMontee)) && (plateau[xCaseATester][yCaseATester].getTypeCase().equals(Case.FORET)) ) 
					nbPas+=2;
				else 
					nbPas+=1;
			
				ArrayList<Integer> coordVoisin = new ArrayList<Integer>();
				coordVoisin.add(0, xCaseATester); coordVoisin.add(1, yCaseATester);
				if (!(casesDispos.contains(coordVoisin))) {
					casesDispos.add(coordVoisin);
				}	
				listerCasesPossibles(plateau, casesDispos, unite, nbPas, xCaseATester, yCaseATester);
			}
		}
	}
	
	/**
	 * @param plateau 2D pour accéder aux cases
	 * @param casesDispos pour lister les cases disponibles
	 * @param unite l'unité à considérer
	 * @param nbPas la distance actuelle par rapport à la case initiale
	 * @param xCaseATester l'entier représentant la coordonnée horizontale de la case à tester
	 * @param yCaseATester l'entier représentant la coordonnée verticale de la case à tester
	*/
	public static void listerCasesPossibles(Case[][] plateau, ArrayList<ArrayList<Integer>> casesDispos, Unite unite, int nbPas, int xCaseATester, int yCaseATester) {
		if (nbPas<unite.getStatsActuelles().getDEP()) {
			verifierCaseLibre(plateau, casesDispos, unite, nbPas, xCaseATester, yCaseATester-1);
			verifierCaseLibre(plateau, casesDispos, unite, nbPas, xCaseATester, yCaseATester+1);
			verifierCaseLibre(plateau, casesDispos, unite, nbPas, xCaseATester-1, yCaseATester);
			verifierCaseLibre(plateau, casesDispos, unite, nbPas, xCaseATester+1, yCaseATester);
			if (xCaseATester%2 == 0) {
				verifierCaseLibre(plateau, casesDispos, unite, nbPas, xCaseATester-1, yCaseATester-1);	
				verifierCaseLibre(plateau, casesDispos, unite, nbPas, xCaseATester+1, yCaseATester-1);
			}
			else {
				verifierCaseLibre(plateau, casesDispos, unite, nbPas, xCaseATester-1,yCaseATester+1);
				verifierCaseLibre(plateau, casesDispos, unite, nbPas, xCaseATester+1, yCaseATester+1);
			}
		}
	}
	
	/**
	 * @param plateau 2D pour changer les états des cases
	 * @param casesDispos pour vérifier que la destination est bien atteignable
	 * @param uniteADeplacer l'unité à déplacer
	 * @param xDest l'entier représentant la coordonnée de destination horizontale
	 * @param yDest l'entier représentant la coordonnée de destination verticale
	 * @return vrai si le déplacement s'est bien produit, sinon faux
	*/
	public static boolean deplacerUnite(Case[][] plateau, ArrayList<ArrayList<Integer>> casesDispos, Unite uniteADeplacer, int xDest, int yDest) {
		ArrayList<Integer> coordsDest = new ArrayList<Integer>();
		coordsDest.add(0,xDest); coordsDest.add(1,yDest);
		if (casesDispos.contains(coordsDest)) {
			plateau[uniteADeplacer.getCoords()[0]][uniteADeplacer.getCoords()[1]].setEstOccupee(false);
			uniteADeplacer.setCoords(xDest, yDest);
			uniteADeplacer.setTypeCase(plateau[uniteADeplacer.getCoords()[0]][uniteADeplacer.getCoords()[1]].getTypeCase());
			plateau[uniteADeplacer.getCoords()[0]][uniteADeplacer.getCoords()[1]].setEstOccupee(true);	
			
			return true;
		}
		return false;
	}
}
