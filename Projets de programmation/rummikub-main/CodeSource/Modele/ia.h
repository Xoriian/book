/////////////////////////////////////// Projet Rummikub - Groupe 9 - Intelligence Artificielle ////////////////////////////////////////

#ifndef IA_H
#define IA_H

#include "gestion_listes.h"

void jouer_ia(int tmax, LISTE* joueur, LISTE_DE_LISTES* plateau, int* tab, MODE_DE_TRI modeTri);
BOOLEEN trier_tableau(int* tab, int tmax, BOOLEEN joker);
void rotation_tab(int* tab, int tmax, int tmin);
BOOLEEN ia(LISTE* main, LISTE_DE_LISTES* plateau, LISTE* pioche, BOOLEEN aFait30);

#endif 
