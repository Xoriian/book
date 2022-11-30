package fr.wargame.modele.unites;

public class Mage extends Fantassin {

	public Mage() {
		super(25, 0, 5, 20, 15, 3, 2);		
	}

	/**
	 * Redéfinition afin de lancer une attaque magique.
	 */
	@Override
	public int attaquer(Unite defenseur, boolean estSimulation) {
		return super.lancerAttaque(defenseur, true, estSimulation);
	}

	/**
	 * Redéfinition afin d'appliquer des bonus/malus.
	 * Attaque magique.
	 * Attaque magique doublée durant l'attaque.
	 * Déplacement adverse -3.
	 */
	@Override
	public int attaquerUltime(Unite defenseur, boolean estSimulation) {
		int oldMAG = this.statsActuelles.mag;
		this.statsActuelles.mag = oldMAG * 2;
		if(!estSimulation) {
			this.ultime = 0;
			defenseur.subirMalus(0, 0, 0, 0, 3);
		}
		int vieRestante = this.attaquer(defenseur, estSimulation);
		this.statsActuelles.mag = oldMAG;
		return vieRestante;
	}

	/**
	 * Redéfinition afin de renvoyer le bon type.
	 */
	@Override
	public String donnerTypeUnite() {
		return "mage";
	}
}
