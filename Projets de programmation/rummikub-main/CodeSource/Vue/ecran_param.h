/////////////////////////////////////// Projet Rummikub - Groupe 9 - Affichage de l'écran des paramètres ////////////////////////////////////////

#ifndef ECRAN_PARAM_H
#define ECRAN_PARAM_H

#include "SDL/SDL.h"
#include "SDL/SDL_ttf.h"
#include "../Controleur/controleur.h"
#include "../parametres_jeu.h"


void afficher_texte(SDL_Surface* ecran, char* texteAAfficher, int xpos,int ypos,int taille);
void afficher_texte_choix_joueurs_manches(SDL_Surface *ecran, SDL_Rect posTextePlusJoueurs, SDL_Rect posTexteMoinsJoueurs, SDL_Rect posTextePlusManches, SDL_Rect posTexteMoinsManches, SDL_Rect posBoutonRetour, int nbJoueurs, int nbManches);
void afficher_instructions_demander_pseudo(SDL_Surface *ecran);

#endif
