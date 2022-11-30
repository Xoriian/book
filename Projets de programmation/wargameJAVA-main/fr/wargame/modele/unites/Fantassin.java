package fr.wargame.modele.unites;

/**
 * Groupe d'unités à pied
 */
public class Fantassin extends Unite {

	Fantassin(int pv, int atq, int defp, int mag, int defm, int pornormale, int porultime) {
		super(pv, atq, defp, mag, defm, 4, pornormale, porultime);
	}
}
