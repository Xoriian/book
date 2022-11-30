package fr.wargame.modele.unites;

/**
 * Groupe d'unités possédant une armure.
 * L'armure double la défense physique lorsqu'elle est portée.
 * Elle perd 1 point de durabilité à chaque attaque reçue (hors simulation).
 * Se soigner sur un village redonne 1 point.
 * Elle est représentée par deux entiers, la valeur maximale et la valeur actuelle.
 */
public class UniteEnArmure extends Unite {
	int armure = 0;
	int armureMAX = 0;

	UniteEnArmure(int pv, int atq, int defp, int mag, int defm, int pornormale, int porultime, int armure) {
		super(pv, atq, defp * 2, mag, defm, 3, pornormale, porultime);
		this.armureMAX = armure;
		this.armure = armure;
	}

	/**
	 * @return l'entier représentant la durabilité actuelle de l'armure
	 */
	@Override
	public int getArmure() { return this.armure; }

	/**
	 * Redéfinition afin de gérer l'armure.
	 * Lors d'une attaque non simulée, l'armure perd 1 point de durabilité.
	 * Si elle passe à 0, la défense physique est divisée par 2.
	 * Appel à super.subirAttaque pour gérer la perte de PV.
	 */
	@Override
	public int subirAttaque(int degats, boolean estSimulation) {
		if(!estSimulation && this.armure > 0) {
			this.armure -= 1;
			if(this.armure < 1) {
				this.stats.defp = (int)(this.stats.defp / 2);
				this.statsActuelles.defp = (int)(this.statsActuelles.defp / 2);
			}
		}
		return super.subirAttaque(degats, estSimulation);
	}

	/**
	 * Redéfinition afin de gérer l'armure.
	 * Appel à super.soigner pour gérer le regain de PV.
	 * L'armure est réparée de 1 point dans un village.
	 */
	@Override
	public void soigner() {
		super.soigner();
		if(this.armure > 0 && this.armure < this.armureMAX && this.typeCase.equals("V")) {
			this.armure += 1;
		}
	}
}
