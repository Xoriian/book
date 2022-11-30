package fr.wargame.modele.unites;

public class Dragon extends UniteVolante {

	public Dragon() {
		super(35, 15, 10, 20, 15, 2, 2);
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
	 * Attaque physique.
	 * Attaque physique triplée durant l'attaque.
	 * Défense physique adverse à 0 durant attaque.
	 * Déplacement adverse -5.
	 */
	@Override
	public int attaquerUltime(Unite defenseur, boolean estSimulation) {
		int oldATQ = this.statsActuelles.atq;
		this.statsActuelles.atq = oldATQ * 3;
		this.statsActuelles.atq += defenseur.statsActuelles.defp;
		int vieRestante = super.lancerAttaque(defenseur, false, estSimulation);
		if(!estSimulation) {
			this.ultime = 0;
			defenseur.subirMalus(0, 0, 0, 0, 5);
		}
		this.statsActuelles.atq = oldATQ;
		return vieRestante;
	}

	/**
	 * Redéfinition afin de renvoyer le bon type.
	 */
	@Override
	public String donnerTypeUnite() {
		return "dragon";
	}
}
