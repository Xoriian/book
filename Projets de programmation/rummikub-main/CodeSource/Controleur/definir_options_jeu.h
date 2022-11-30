/////////////////////////////////////// Projet Rummikub - Groupe 9 - Gestion des options de jeu ////////////////////////////////////////

#ifndef DEFINIR_OPTIONS_JEU_H
#define DEFINIR_OPTIONS_JEU_H

#include "../Vue/ecran_param.h"

void choisir_nombre_joueurs(SDL_Surface *ecran, SDL_Rect posClic, SDL_Rect posTextePlusJoueurs, SDL_Rect posTexteMoinsJoueurs, int *nbJoueurs);
void choisir_nombre_manches(SDL_Surface *ecran, SDL_Rect posClic, SDL_Rect posTextePlusManches, SDL_Rect posTexteMoinsManches, int *nbManches);
void definir_options_jeu(SDL_Surface *ecran,int *nbJoueurs,int *nbManches);

#endif
