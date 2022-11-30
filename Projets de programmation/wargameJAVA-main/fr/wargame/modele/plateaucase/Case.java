package fr.wargame.modele.plateaucase;

import java.lang.Math;

/**
 * Regroupe les différentes méthodes permettant de manipuler une case
 * On retrouve cinq differents types de cases :
 * Prairie (P)
 * Forêt (Fo)
 * Montagne (Mo)
 * Mer (Me)
 * Village (V)
 * On retrouve également un booléen indiquant si la case est occupée (si une unité se trouve dessus).
 * On retrouve enfin les coordonnées de la case (horizontale et verticale).
 * Les méthodes qui suivent servent à influencer les attaques en fonction de la case.
 * On y trouve une méthode pour les cases qui augmentent l'attaque et une méthode pour les cases qui diminuent l'attaque.
 */
public class Case {
	private String typeCase;
	private boolean estOccupee;
	public static final String PRAIRIE = "P";
	public static final String FORET = "Fo";
	public static final String MONTAGNE = "Mo";
	public static final String MER = "Me";
	public static final String VILLAGE = "V";
	
	public Case(){
		this.setTypeCase(PRAIRIE);
		this.setEstOccupee(false);
	}

	/**
	 * @return le type de la case souhaitée 
	 */
	public String getTypeCase() { return typeCase; }
	
	/**
	 * @param typeCase le type de la case à affecter
	 */
	public void setTypeCase(String typeCase) { this.typeCase = typeCase; }

	/**
	 * @return l'état de la case souhaitée (vrai si elle est occupée, faux sinon)
	 */
	public boolean getEstOccupee() { return estOccupee; }
	
	/**
	 * @param estOccupee état de la case à affecter
	 */
	public void setEstOccupee(boolean estOccupee) { this.estOccupee = estOccupee; }
	
	/**
	 * @param typeCase type de la case
	 * @return le coefficient de l'attaque sous forme de nombre décimal : 1.2 si l'effet se produit, 1.0 sinon
	 */
	public static double effetCaseAttaque(String typeCase) {		
		if(typeCase.equals(MER) && (Math.random()>=0.4) )
			return 1.2;
		return 1.0;
	}
	
	/**
	 * @param typeCase type de la case
	 * @return le coefficient de l'attaque sous forme de nombre décimal : inférieur à 1.0 si l'effet se produit, 1.0 sinon
	 */
	public static double effetCaseDefense(String typeCase) {
		if(typeCase.equals(FORET) && (Math.random()>=0.6) ) 
			return 0.9;
		else if(typeCase.equals(MONTAGNE) && (Math.random()>=0.4) ) 
			return 0.8;
		else if(typeCase.equals(VILLAGE)) 
			return 0.95;
		return 1.0;
	}
}
