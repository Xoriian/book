/////////////////////////////////////// Projet Rummikub - Groupe 9 - Contrôleur et main ////////////////////////////////////////

#ifndef CONTROLEUR_H
#define CONTROLEUR_H

#include <time.h>

// Modèle
#include "../Modele/listes.h"
#include "../Modele/gestion_listes.h"
#include "../Modele/ia.h"
#include "../Modele/traitement_fichier_score.h"

// Contrôleur
#include "definir_options_jeu.h"
#include "gestion_pseudos.h"

// Vue
#include "../Vue/plateau_jeu.h"
#include "../Vue/ecran_titre.h"
#include "../Vue/ecran_score.h"

// Autres
#include "../parametres_jeu.h"

// Prototypes des fonctions

BOOLEEN est_dans_rect(SDL_Rect souris, SDL_Rect rectVoulu);
SDL_Rect recuperer_coord_clic();

void attribuer_sprites_une_couleur(SDL_Surface *tab[53], char couleur, int multiplicateur);
void attribuer_sprites(SDL_Surface *tab[53]);
SDL_Rect transforme_tuile_en_position_joueur(int position);
SDL_Rect transforme_tuile_en_position_plateau(int position);
int transforme_clic_en_tuile_joueur(LISTE joueur, SDL_Surface* ecran, SDL_Rect posClic);
int transforme_clic_en_tuile_plateau(LISTE_DE_LISTES plateau, SDL_Surface* ecran, SDL_Rect posClic);

int determiner_premier_joueur(LISTE pioche, SDL_Surface *ecran, SDL_Surface *tabSprites[], JOUEUR *joueurs, DONNEES_FICHIER **tabDonnees, int numJ);
void jouer_humain(LISTE *pioche, LISTE_DE_LISTES *plateau, SDL_Surface *ecran, SDL_Surface *tabSprites[], int numJ, JOUEUR *joueurs, DONNEES_FICHIER **tabDonnees);

void ecran_titre_choix(int *choix);
void ecran_historique_choix(int *choixScores);

#endif

