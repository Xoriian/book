package fr.wargame.modele.unites;

/**
 * Unité attaquant avec un arc.
 */
public class Archer extends Fantassin {

	public Archer() {
		super(30, 15, 10, 15, 10, 3, 4);
	}
	
	/**
	 * Redéfinition afin de lancer une attaque physique.
	 */
	@Override
	public int attaquer(Unite defenseur, boolean estSimulation) {
		return super.lancerAttaque(defenseur, false, estSimulation);
	}

	/**
	 * Redéfinition afin d'appliquer des bonus/malus.
	 * Attaque magique.
	 * Attaque magique doublée durant l'attaque.
	 */
	@Override
	public int attaquerUltime(Unite defenseur, boolean estSimulation) {
		int oldMAG = this.statsActuelles.mag;
		this.statsActuelles.mag = oldMAG * 2;
		if(!estSimulation) {
			this.ultime = 0;
		}
		int vieRestante = super.lancerAttaque(defenseur, true, estSimulation);
		this.statsActuelles.mag = oldMAG;
		return vieRestante;
	}

	/**
	 * Redéfinition afin de renvoyer le bon type.
	 */
	@Override
	public String donnerTypeUnite() {
		return "archer";
	}
}
