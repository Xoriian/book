package fr.wargame.modele.unites;

/**
 * Groupe d'unités possédant une monture.
 */
public class UniteMontee extends Unite {

	UniteMontee(int pv, int atq, int defp, int mag, int defm, int pornormale, int porultime) {
		super(pv, atq, defp, mag, defm, 6, pornormale, porultime);
	}
}
