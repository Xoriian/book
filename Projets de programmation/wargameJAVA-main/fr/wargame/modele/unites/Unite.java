package fr.wargame.modele.unites;

import java.io.Serializable;

import fr.wargame.modele.plateaucase.Case;

/**
 * Base de toute unité lui donnant ses statistiques et coordonnées.
 * Les méthodes d'attaque et de soin y sont également définies.
 * Cette classe implémente Serializable afin de permettre la sauvegarde dans un fichier.
 */
public class Unite implements Serializable {
	boolean estJouee = true;
	int ultime = 0;
	int[] coords = new int[]{0, 0};
	String typeCase = new String();
	Stats stats = new Stats();
	Stats statsActuelles = new Stats();
	private static final long serialVersionUID = 3L;

	Unite(int pv, int atq, int defp, int mag, int defm, int deplacement, int pornormale, int porultime) {
		this.stats.setPV(pv);
		this.stats.setATQ(atq);
		this.stats.setDEFP(defp);
		this.stats.setMAG(mag);
		this.stats.setDEFM(defm);
		this.stats.setDEP(deplacement);
		this.stats.setPOR(pornormale, porultime);
		this.statsActuelles.setPV(pv);
		this.statsActuelles.setATQ(atq);
		this.statsActuelles.setDEFP(defp);
		this.statsActuelles.setMAG(mag);
		this.statsActuelles.setDEFM(defm);
		this.statsActuelles.setDEP(deplacement);
		this.statsActuelles.setPOR(pornormale, porultime);
	}

	/**
	 * @param n l'entier représentant le chargement de l'ultime
	 */
	public void setUltime(int n) { this.ultime = n; }

	/**
	 * @param x l'entier représentant la coordonnée horizontale
	 * @param y l'entier représentant la coordonnée verticale
	 */
	public void setCoords(int x, int y) { this.coords[0] = x; this.coords[1] = y; }

	/**
	 * @param s la chaîne de caractères représentant le type de case sur laquelle est l'unité
	 */
	public void setTypeCase(String s) { this.typeCase = s; }

	/**
	 * @return l'entier représentant le chargement de l'ultime
	 */
	public int getUltime() { return this.ultime; }

	/**
	 * @return le tableau de deux entiers représentant les coordonnées
	 */
	public int[] getCoords() { return this.coords; }

	/**
	 * @return la chaîne de caractères représentant le type de case sur laquelle est l'unité
	 */
	public String getTypeCase() { return this.typeCase; }

	/**
	 * @return l'objet stats représentant les statitiques initiales
	 */
	public Stats getStats() { return this.stats; }

	/**
	 * @return l'objet stats représentant les statitiques actuelles
	 */
	public Stats getStatsActuelles() { return this.statsActuelles; }

	/**
	 * Méthode qui doit être redéfinie pour les unités avec armure.
	 * La valeur renvoyée vaut -1 pour indiquer que l'unité n'a pas d'armure et qu'elle n'en a jamais eu.
	 *
	 * @return -1
	 */
	public int getArmure() { return -1; }

	/**
	 * @return le booléen indiquant si l'unité a déjà été utilisée durant ce tour
	 */
	public boolean estDejaJouee() { return this.estJouee; }

	/**
	 * Renvoie un booleen qui indique si l'attaque ultime peut être utilisée.
	 * Si l'entier représentant le chargement de l'ultime est supérieur strictement à 99, l'ultime est débloqué.
	 *
	 * @return un booléen indiquant la disponibilité de l'ultime
	 */
	public boolean estUltimeDispo() {
		return (this.ultime > 99 ? true : false);
	}

	/**
	 * Doit être redéfinie par chaque classe d'unité pour choisir attaque physique [défaut] ou magique.
	 */
	public int attaquer(Unite defenseur, boolean estSimulation) {
		return this.lancerAttaque(defenseur, false, estSimulation);
	}

	/**
	 * Doit être redéfinie par chaque classe d'unité pour changer les effets de l'ultime.
	 */
	public int attaquerUltime(Unite defenseur, boolean estSimulation) {
		return this.attaquer(defenseur, estSimulation);
	}

	/**
	 * Attaque une unité adverse.
	 * Une simulation est possible, la cible ne perd pas de PV.
	 * Cela permet de tester une attaque et de prévoir les dégâts.
	 * Unité A attaque unité B.
	 * Calcule les dégâts en fonction du type d'attaque T = physique OU magique
	 * dégâts = Potentiel d'attaque T de A - potentiel de défense T de B
	 * Applique les effets de terrain s'il ne s'agit pas d'une simulation.
	 * Si les dégâts sont supérieurs à 0, appelle subirAttaque sur la cible et renvoie la valeur de retour.
	 * Sinon, renvoie les PV actuels de la cible.
	 * @param defenseur     l'objet unite qui subit l'attaque
	 * @param estMagique    un booléen, true s'il s'agit d'une attaque magique
	 * @param estSimulation un booléen, true s'il s'agit d'une simulation
	 * @return              un entier indiquant les PV restants de l'adversaire après l'attaque
	 */
	public int lancerAttaque(Unite defenseur, boolean estMagique, boolean estSimulation) {
		int degats = 0;
		if(estMagique)
			degats = this.statsActuelles.mag - defenseur.statsActuelles.defm;
		else
			degats = this.statsActuelles.atq - defenseur.statsActuelles.defp;
		if(!estSimulation)
			degats = (int) (degats * Case.effetCaseAttaque(this.typeCase) * Case.effetCaseDefense(defenseur.typeCase));
		return (degats > 0 ? defenseur.subirAttaque(degats, estSimulation) : defenseur.getStatsActuelles().pv);
	}

	/**
	 * Applique les dégâts subis lors d'une attaque.
	 * Retire les PV de soi-même d'un nombre de points valant les dégâts.
	 * Si les PV sont négatifs, ils sont passés à 0.
	 * La baisse n'est pas effective s'il s'agit d'une simulation.
	 * Cela permet de tester une attaque et de prévoir les dégâts.
	 * @param x l'entier indiquant les dégâts infligés
	 * @param estSimulation un booléen, true s'il s'agit d'une simulation
	 * @return              un entier indiquant les PV restants de l'adversaire après l'attaque
	 */
	public int subirAttaque(int degats, boolean estSimulation) {
		int temp = this.statsActuelles.pv;
		temp -= degats;
		if(temp < 1)
			temp = 0;
		if(estSimulation)
			return temp;
		else {
			this.statsActuelles.pv = temp;
			return this.statsActuelles.pv;
		}
	}

	/**
	 * Applique les malus (baisses de statistique) infligés.
	 * Pour chaque statistique, teste si elle est supérieure au malus reçu.
	 * Si oui, soustrait ce nombre.
	 * Si non, passe la statistique à 0.
	 * Cette fonction modifie statsActuelles car les malus sont temporaires.
	 * @param atq un entier représentant la baisse d'attaque physique
	 * @param defp un entier représentant la baisse de défense physique
	 * @param mag un entier représentant la baisse d'attaque magique
	 * @param defm un entier représentant la baisse de défense magique
	 * @param deplacement un entier représentant la baisse de déplacement
	 */
	public void subirMalus(int atq, int defp, int mag, int defm, int deplacement) {
		this.statsActuelles.atq = (this.statsActuelles.atq >= atq ? this.statsActuelles.atq - atq : 0);
		this.statsActuelles.defp = (this.statsActuelles.defp >= defp ? this.statsActuelles.defp - defp : 0);
		this.statsActuelles.mag = (this.statsActuelles.mag >= mag ? this.statsActuelles.mag - mag : 0);
		this.statsActuelles.defm = (this.statsActuelles.defm >= defm ? this.statsActuelles.defm - defm : 0);
		this.statsActuelles.deplacement = (this.statsActuelles.deplacement >= deplacement ? this.statsActuelles.deplacement - deplacement : 0);
	}

	/**
	 * Retire progressivement les malus (+2 points par tour).
	 * Teste si les statistiques actuelles sont égales aux statistiques initiales.
	 * Si oui, ne fait rien.
	 * Si non, pour chaque statistique, ajoute 2 points si cela ne dépasse pas la valeur initiale.
	 * Dans le cas contraire, ajoute 1 point.
	 */
	public void retirerMalus() {
		if(!this.stats.equals(this.statsActuelles)) {
			if(this.statsActuelles.atq < this.stats.atq) {
				if(this.statsActuelles.atq < this.stats.atq - 1) { this.statsActuelles.atq += 2; }
				else { this.statsActuelles.atq ++; }
			}
			if(this.statsActuelles.defp < this.stats.defp) {
				if(this.statsActuelles.defp < this.stats.defp - 1) { this.statsActuelles.defp += 2; }
				else { this.statsActuelles.defp ++; }
			}
			if(this.statsActuelles.mag < this.stats.mag) {
				if(this.statsActuelles.mag < this.stats.mag - 1) { this.statsActuelles.mag += 2; }
				else { this.statsActuelles.mag ++; }
			}
			if(this.statsActuelles.defm < this.stats.defm) {
				if(this.statsActuelles.defm < this.stats.defm - 1) { this.statsActuelles.defm += 2; }
				else { this.statsActuelles.defm ++; }
			}
			if(this.statsActuelles.deplacement < this.stats.deplacement) {
				if(this.statsActuelles.deplacement < this.stats.deplacement - 1) { this.statsActuelles.deplacement += 2; }
				else { this.statsActuelles.deplacement ++; }
			}
		}
	}

	/**
	 * Soigne l'unité de 30% dans un village et 10% ailleurs.
	 * Le nombre de PV ne peut jamais dépasser la valeur maximale.
	 * typeCase permet de savoir si l'unité est dans un village.
	 */
	public void soigner() {
		double bonus = (this.typeCase.equals("V") ? 0.3 : 0.1);
		this.statsActuelles.pv += (int) (this.stats.pv * bonus);
		if(this.statsActuelles.pv > this.stats.pv) {
			this.statsActuelles.pv = this.stats.pv;
		}
	}

	/**
	 * Inverse la valeur du booléen estJouee.
	 */
	public void changerEstJouee() { this.estJouee = (estJouee ? false : true); }

	/**
	 * Doit être redéfinie pour renvoyer le bon type.
	 * @return "MissingNo."
	 */
	public String donnerTypeUnite() {
		return "MissingNo.";
	}
}

