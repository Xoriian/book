/////////////////////////////////////// Projet Rummikub - Groupe 9 - Affichage de l'écran des scores ////////////////////////////////////////

#ifndef ECRAN_SCORE_H
#define ECRAN_SCORE_H

#include <SDL/SDL.h>
#include "../parametres_jeu.h"
#include "ecran_param.h"

void afficher_ecran_scores(SDL_Surface *ecran, DONNEES_FICHIER **tabDonnees, int numPartie);
void afficher_ecran_historique(SDL_Surface *ecran);

#endif
