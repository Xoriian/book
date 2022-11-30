package fr.wargame.modele.unites;

import java.io.Serializable;

/**
 * Regroupe toutes les statistiques d'une unité dans un objet.
 * Il s'agit des grandeurs suivantes :
 * = points de vie
 * = potentiel d'attaque physique
 * = potentiel de défense physique
 * = potentiel d'attaque magique
 * = potentiel de défense magique
 * = potentiel de déplacement
 * = portée maximale d'attaque normale et d'ultime
 * Cette classe implémente Serializable afin de permettre la sauvegarde dans un fichier.
 */
public class Stats implements Serializable {
	int pv = 0;
	int atq = 0;
	int defp = 0;
	int mag = 0;
	int defm = 0;
	int deplacement = 0;
	int portee[] = new int[] {1, 1};
	private static final long serialVersionUID = 1L;

	/**
	 * @param n l'entier représentant les points de vie
	 */
	void setPV(int n) { this.pv = n; }

	/**
	 * @param n l'entier représentant l'attaque physique
	 */
	void setATQ(int n) { this.atq = n; }

	/**
	 * @param n l'entier représentant la défense physique
	 */
	void setDEFP(int n) { this.defp = n; }

	/**
	 * @param n l'entier représentant l'attaque magique
	 */
	void setMAG(int n) { this.mag = n; }

	/**
	 * @param n l'entier représentant la défense magique
	 */
	void setDEFM(int n) { this.defm = n; }

	/**
	 * @param n l'entier représentant le déplacement maximal
	 */
	void setDEP(int n) { this.deplacement = n; }

	/**
	 * @param n l'entier représentant la portée d'attaque maximale
	 * @param m l'entier représentant la portée d'ultime maximale
	 */
	void setPOR(int n, int m) { this.portee[0] = n; this.portee[1] = m; }

	/**
	 * @return l'entier représentant les points de vie
	 */
	public int getPV() { return this.pv; }

	/**
	 * @return l'entier représentant l'attaque physique
	 */
	public int getATQ() { return this.atq; }

	/**
	 * @return l'entier représentant la défense physique
	 */
	public int getDEFP() { return this.defp; }

	/**
	 * @return l'entier représentant l'attaque magique
	 */
	public int getMAG() { return this.mag; }

	/**
	 * @return l'entier représentant la défense magique
	 */
	public int getDEFM() { return this.defm; }

	/**
	 * @return l'entier représentant le déplacement
	 */
	public int getDEP() { return this.deplacement; }

	/**
	 * @return le tableau de deux entiers représentant les portees maximales (normale et ultime)
	 */
	public int[] getPOR() { return this.portee; }
}
