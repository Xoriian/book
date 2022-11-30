/////////////////////////////////////// Projet Rummikub - Groupe 9 - Affichage du plateau de jeu ////////////////////////////////////////

#ifndef PLATEAU_JEU_H
#define PLATEAU_JEU_H

#include <unistd.h>
#include <SDL/SDL.h>
#include "ecran_param.h"

void afficher_plateau_jeu(SDL_Surface *ecran, int numJ, JOUEUR *joueurs, DONNEES_FICHIER **tabDonnees);
void afficher_tuiles_joueur(JOUEUR joueur, SDL_Surface *ecran, SDL_Surface *tabSprites[]);
void afficher_tuiles_plateau(LISTE_DE_LISTES plateau, SDL_Surface *ecran, SDL_Surface *tabSprites[]);
void afficher_tuile_select(SDL_Surface *ecran, SDL_Rect posIMG);
void afficher_premier_joueur(SDL_Surface *ecran, TUILE tab[], SDL_Surface *tabSprites[], JOUEUR *joueurs, DONNEES_FICHIER **tabDonnees, int numJ);
void afficher_jeu(SDL_Surface *ecran, LISTE_DE_LISTES plateau, SDL_Surface *tabSprites[], int numJ, JOUEUR *joueurs, DONNEES_FICHIER **tabDonnees);
void afficher_cadre_joueur_joue(SDL_Surface *ecran, int numJ, JOUEUR *joueurs, DONNEES_FICHIER **tabDonnees);

#endif
