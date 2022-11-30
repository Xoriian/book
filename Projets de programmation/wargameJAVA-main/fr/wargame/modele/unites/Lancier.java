package fr.wargame.modele.unites;

public class Lancier extends UniteEnArmure {

	public Lancier() {
		super(40, 15, 20, 0, 10, 1, 1, 3);
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
	 * Attaque physique.
	 * Attaque physique triplée durant l'attaque.
	 * Défense physique et déplacement de soi-même à 0.
	 * Défense physique et déplacement adverses à 0.
	 */
	@Override
	public int attaquerUltime(Unite defenseur, boolean estSimulation) {
		int oldATQ = this.statsActuelles.atq;
		this.statsActuelles.atq = oldATQ * 3;
		int vieRestante = this.attaquer(defenseur, estSimulation);
		if(!estSimulation) {
			this.ultime = 0;
			this.subirMalus(0, this.statsActuelles.defp, 0, 0, this.statsActuelles.deplacement);
			defenseur.subirMalus(0, defenseur.statsActuelles.defp, 0, 0, defenseur.statsActuelles.deplacement);
		}
		this.statsActuelles.atq = oldATQ;
		return vieRestante;
	}

	/**
	 * Redéfinition afin de renvoyer le bon type.
	 */
	@Override
	public String donnerTypeUnite() {
		return "lancier";
	}
}
