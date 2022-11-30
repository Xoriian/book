/////////////////////////////////////// Projet Rummikub - Groupe 9 - Structures et listes ////////////////////////////////////////

#include "listes.h"
 
LISTE creer_liste_vide()
{
	LISTE liste;
	
	liste.deb_liste = NULL;
	liste.taille = 0;
	 
	return liste;
}

LISTE ajouter_element_liste(LISTE liste, TUILE tuile)
{
	ELEMENT* nouveau = malloc(sizeof(ELEMENT*));
	
	nouveau->tuile = tuile;
	nouveau->suivant = liste.deb_liste;
	
	liste.deb_liste = nouveau;
	liste.taille++;

	return liste;
}

BOOLEEN sont_egaux(TUILE tuile1, TUILE tuile2)
{
	if ((tuile1.numero == tuile2.numero) && (tuile1.couleur == tuile2.couleur))
		return VRAI;
	else 
		return FAUX;
}
