/////////////////////////////////////// Projet Rummikub - Groupe 9 - Traitement fichier score ////////////////////////////////////////

#ifndef TRAITEMENT_FICHIER_SCORE_H
#define TRAITEMENT_FICHIER_SCORE_H

#include <stdio.h>
#include <stdlib.h>
#include "../Controleur/controleur.h"
#include "../parametres_jeu.h"


//Fonctions pour calculer les scores d'une manche
void calculer_totaux_tuiles_joueurs(JOUEUR* joueurs);
int trouver_joueur_avec_score_min(JOUEUR *joueurs);
void affecter_scores_a_une_manche(JOUEUR* joueurs, LISTE pioche);


//Fonctions pour affecter les scores dans le tableau des scores
void remplir_manche_dans_tabDonnees(JOUEUR *joueurs, DONNEES_FICHIER **tabDonnees, int mancheActuelle);
void decaler_scores_donnees(DONNEES_FICHIER **tabDonnees);
void trier_score_decroissant(DONNEES_FICHIER **tabDonnees);


//Fonctions pour manipuler le fichier scores.dat
void enregistrer_donnees_fichier(DONNEES_FICHIER **tabDonnees);
void lire_donnees_fichier(DONNEES_FICHIER **tabDonnees);


#endif
