package fr.wargame.modele.unites;

public class Renard extends UniteMontee {

	public Renard() {
		super(35, 0, 10, 15, 5, 1, 3);			
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
	 * Pas de dégâts.
	 * Attaque, défense physique, magique adverses à 0.
	 */
	@Override
	public int attaquerUltime(Unite defenseur, boolean estSimulation) {
		if(!estSimulation) {
			this.ultime = 0;
			defenseur.subirMalus(defenseur.statsActuelles.atq, defenseur.statsActuelles.defp, defenseur.statsActuelles.mag, defenseur.statsActuelles.defm, 0);
		}
		return defenseur.getStatsActuelles().getPV();
	}

	/**
	 * Redéfinition afin de renvoyer le bon type.
	 */
	@Override
	public String donnerTypeUnite() {
		return "renard";
	}
}
