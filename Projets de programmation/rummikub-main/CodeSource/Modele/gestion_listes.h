/////////////////////////////////////// Projet Rummikub - Groupe 9 - Gestion des listes ////////////////////////////////////////

#ifndef GESTION_LISTES_H
#define GESTION_LISTES_H

#include "listes.h"

// Manipulation de listes

void trier_liste(LISTE* liste, MODE_DE_TRI modeTri);
void ajouter_liste_triee_parcours(ELEMENT **elem, TUILE tuile, MODE_DE_TRI modeTri);
void ajouter_liste_triee(LISTE *liste, TUILE tuile, MODE_DE_TRI);
void deplacer_de_liste1_vers_liste2(ELEMENT **elem_liste1, LISTE *liste2, int indice, MODE_DE_TRI modeTri);
void copier_liste(ELEMENT *origine, LISTE *copie, MODE_DE_TRI modeTri);
void copier_liste_de_listes(LISTE *origine, LISTE_DE_LISTES *copie, MODE_DE_TRI modeTri);
void vider_liste(ELEMENT *elem);
void vider_liste_de_listes(LISTE *liste);

// Fonctions de jeu

void initialiser_pioche(LISTE *pioche);
void piocher(LISTE *pioche, LISTE *joueur);
void jouer(LISTE *joueur, LISTE_DE_LISTES *plateau, MODE_DE_TRI modeTri, int indice_tuile_jouee, int numero_serie_agrandie);

// Vérification du plateau

BOOLEEN est_serie_valide(LISTE* serie);
BOOLEEN est_plateau_valide(LISTE_DE_LISTES* plateau, BOOLEEN tour1, int serie1);
void gerer_joker(LISTE* serie);

#endif
