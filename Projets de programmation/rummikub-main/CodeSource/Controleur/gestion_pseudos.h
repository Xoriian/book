/////////////////////////////////////// Projet Rummikub - Groupe 9 - Gestion des pseudos ////////////////////////////////////////

#ifndef GESTION_PSEUDOS_H
#define GESTION_PSEUDOS_H

#include <ctype.h>
#include "../parametres_jeu.h"
#include "../Vue/ecran_param.h"

void taper_pseudo_clavier(SDL_Surface* ecran, char* pseudoDemande);
void enlever_espaces_inutiles_pseudo(char* pseudoDemande);
void recuperer_un_pseudo(SDL_Surface* fenetre, char *pseudoDemande);
void entrer_pseudos_joueurs(SDL_Surface *ecran,JOUEUR joueurs[NB_JOUEURS_MAX],int nbJoueurs);

#endif
