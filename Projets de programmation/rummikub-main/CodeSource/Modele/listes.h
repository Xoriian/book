/////////////////////////////////////// Projet Rummikub - Groupe 9 - Structures et listes ////////////////////////////////////////

#ifndef LISTES_H
#define LISTES_H
 
#include <stdlib.h>

// Types énumérés utiles :

typedef enum {BLEU, VERT, ROUGE, ORANGE} COULEUR;
typedef enum {FAUX, VRAI} BOOLEEN;
typedef enum {COULEURS, NOMBRES} MODE_DE_TRI;

// Position donnée par une abscisse et une ordonnée :

typedef struct {
	int x;
	int y; 
} POSITION;

// Structure d'une tuile :

typedef struct {
	int numero;
	COULEUR couleur;
	BOOLEEN estJoker;
} TUILE;

// Structure d'un élément d'une liste de tuiles :

typedef struct ELEMENT ELEMENT;
struct ELEMENT {
	TUILE tuile;
	ELEMENT* suivant;
};

// Structure de liste :

typedef struct LISTE_{
	ELEMENT* deb_liste;
	int taille;
	struct LISTE_* suivante;
} LISTE;

// Structure de liste de listes :

typedef struct {
	LISTE* prem_liste;
	int taille;
} LISTE_DE_LISTES;

// Structure d'un joueur :

typedef struct {
	char* pseudo;
	int score;
	BOOLEEN estOrdi;
	BOOLEEN aFait30;
	LISTE main;
} JOUEUR;

// Structure des données à lire et enregistrer

typedef struct{
	char *pseudo;
	int scoreFinal;
}DONNEES_FICHIER;

///////////////////////////////////////////////////

// Prototype de fonctions :

LISTE creer_liste_vide();
LISTE ajouter_element_liste (LISTE liste, TUILE tuile);
BOOLEEN sont_egaux(TUILE tuile1, TUILE tuile2);


#endif
