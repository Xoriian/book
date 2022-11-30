package fr.wargame.modele.unites;

/**
 * Groupe d'unités volantes.
 */
public class UniteVolante extends Unite {

	UniteVolante(int pv, int atq, int defp, int mag, int defm, int pornormale, int porultime) {
		super(pv, atq, defp, mag, defm, 5, pornormale, porultime);
	}
}
