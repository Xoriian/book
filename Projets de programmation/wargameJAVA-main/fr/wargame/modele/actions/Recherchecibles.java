package fr.wargame.modele.actions;

import java.util.ArrayList;

import fr.wargame.modele.plateaucase.*;
import fr.wargame.modele.unites.*;

/**
 * Regroupe les méthodes permettant à une unité de savoir si elle peut attaquer un ou des ennemis.
 * Consiste en une récurrence qui dépend d'un entier représentant la portée de l'unité.
 * La récurrence teste si la case est celle d'un ennemi puis l'ajoute à une liste.
 */
public class Recherchecibles {
	
	/**
	 * @param plateau 2D pour accéder aux cases
	 * @param unitesEnnemies pour accéder aux coordonnées des ennemis
	 * @param indicesEnnemisAttaquables ajoute l'indice de la liste unitesEnnemies si l'ennemi est attaquable
	 * @param porteeAttaque le nombre de cases maximum auquel une attaque peut avoir lieu
	 * @param nbPas la distance actuelle par rapport à la case initiale
	 * @param xCaseATester l'entier représentant la coordonnée horizontale de la case à tester
	 * @param yCaseATester l'entier représentant la coordonnée verticale de la case à tester
	 */
	public static void verifierCaseEnnemi(Case[][] plateau, ArrayList<Unite> unitesEnnemies, ArrayList<Integer> indicesEnnemisAttaquables, int porteeAttaque, int nbPas, int xCaseATester, int yCaseATester) {
		if ( (xCaseATester>=0) && (xCaseATester<plateau.length) && (yCaseATester>=0) && (yCaseATester<plateau[0].length) ) {
			if ( (plateau[xCaseATester][yCaseATester].getTypeCase().equals(Case.MONTAGNE)) || (plateau[xCaseATester][yCaseATester].getTypeCase().equals(Case.FORET))) 
				nbPas+=2;
			else
				nbPas+=1;
			int[] coordsCaseATester = new int[2];
			coordsCaseATester[0]=xCaseATester; coordsCaseATester[1]=yCaseATester;
			for(int i=0; i<unitesEnnemies.size(); i++) {
				if ( (unitesEnnemies.get(i).getCoords()[0] == coordsCaseATester[0]) && (unitesEnnemies.get(i).getCoords()[1] == coordsCaseATester[1]) ) {
					if ( !(indicesEnnemisAttaquables.contains(i)) )
						indicesEnnemisAttaquables.add(i);
				}
			}
			listerEnnemisAttaquables(plateau, unitesEnnemies, indicesEnnemisAttaquables, porteeAttaque, nbPas, xCaseATester, yCaseATester);
		}
	}
	
	/**
	 * @param plateau 2D pour accéder aux cases
	 * @param unitesEnnemies pour accéder aux coordonnées des ennemis
	 * @param indicesEnnemisAttaquables liste les ennemis attaquables
	 * @param porteeAttaque le nombre de cases maximum auquel une attaque peut avoir lieu
	 * @param nbPas la distance actuelle par rapport à la case initiale
	 * @param xCaseATester l'entier représentant la coordonnée horizontale de la case à tester
	 * @param yCaseATester l'entier représentant la coordonnée verticale de la case à tester
	 */
	public static void listerEnnemisAttaquables(Case[][] plateau, ArrayList<Unite> unitesEnnemies, ArrayList<Integer> indicesEnnemisAttaquables, int porteeAttaque, int nbPas, int xCaseATester, int yCaseATester) {
		if (nbPas<porteeAttaque) {
			verifierCaseEnnemi(plateau, unitesEnnemies, indicesEnnemisAttaquables, porteeAttaque, nbPas, xCaseATester, yCaseATester-1);
			verifierCaseEnnemi(plateau, unitesEnnemies, indicesEnnemisAttaquables, porteeAttaque, nbPas, xCaseATester, yCaseATester+1);
			verifierCaseEnnemi(plateau, unitesEnnemies, indicesEnnemisAttaquables, porteeAttaque, nbPas, xCaseATester-1, yCaseATester);
			verifierCaseEnnemi(plateau, unitesEnnemies, indicesEnnemisAttaquables, porteeAttaque, nbPas, xCaseATester+1, yCaseATester);
			if (xCaseATester%2 == 0) {
				verifierCaseEnnemi(plateau, unitesEnnemies, indicesEnnemisAttaquables, porteeAttaque, nbPas, xCaseATester-1, yCaseATester-1);	
				verifierCaseEnnemi(plateau, unitesEnnemies, indicesEnnemisAttaquables, porteeAttaque, nbPas, xCaseATester+1, yCaseATester-1);
			}
			else {
				verifierCaseEnnemi(plateau, unitesEnnemies, indicesEnnemisAttaquables, porteeAttaque, nbPas, xCaseATester-1,yCaseATester+1);
				verifierCaseEnnemi(plateau, unitesEnnemies, indicesEnnemisAttaquables, porteeAttaque, nbPas, xCaseATester+1, yCaseATester+1);
			}
		}
	}
}
