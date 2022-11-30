/////////////////////////////////////// Projet Rummikub - Groupe 9 - Contrôleur et main ////////////////////////////////////////

#include "controleur.h"

BOOLEEN est_dans_rect(SDL_Rect souris, SDL_Rect rectVoulu)
{
	return ( (souris.x>=rectVoulu.x) && (souris.x<=(rectVoulu.x+rectVoulu.w)) && (souris.y<=rectVoulu.y+rectVoulu.h) && (souris.y>=rectVoulu.y) );
}
  
SDL_Rect recuperer_coord_clic() 
{
	SDL_Rect posClic;
	SDL_Event event;
	BOOLEEN sortie = FAUX;
	
	while(!sortie)
	{
		SDL_WaitEvent(&event);
		switch(event.type)
		{
			case SDL_QUIT:
				exit(0);
		
			case SDL_MOUSEBUTTONUP:
				if(event.button.button == SDL_BUTTON_LEFT)
				{
					posClic.x = event.button.x;
					posClic.y = event.button.y;
					sortie = VRAI;
				}
			break;
		}
	}
	
	return posClic;
}

void attribuer_sprites_une_couleur(SDL_Surface *tab[53], char couleur, int multiplicateur)
{
	int i;
	char fichier[50];
	
	for(i=1;i<=13;i++)
	{
		sprintf(fichier,"../sprites/tuiles/tuile%d%c.bmp",i,couleur);
		tab[i+(13*multiplicateur)] = SDL_LoadBMP(fichier);
		
		SDL_SetColorKey(tab[i+(13*multiplicateur)], SDL_SRCCOLORKEY, SDL_MapRGB(tab[i+(13*multiplicateur)]->format, COULEUR_TRANSPARENCE));
	}
}

void attribuer_sprites(SDL_Surface *tab[53])
{
	tab[0] = SDL_LoadBMP("../sprites/tuiles/tuilejoker.bmp");
	SDL_SetColorKey(tab[0], SDL_SRCCOLORKEY, SDL_MapRGB(tab[0]->format, COULEUR_TRANSPARENCE));

	attribuer_sprites_une_couleur(tab,'b',0);
	attribuer_sprites_une_couleur(tab,'v',1);
	attribuer_sprites_une_couleur(tab,'r',2);
	attribuer_sprites_une_couleur(tab,'o',3);
}

SDL_Rect transforme_tuile_en_position_joueur(int position)
{
    SDL_Rect posIMG;
    
    posIMG.x = 350 + position%11 * 70;
    posIMG.y = 550 + position/11 * 85;
    
    return posIMG;
}

SDL_Rect transforme_tuile_en_position_plateau(int position)
{
    SDL_Rect posIMG;
    
    posIMG.x = 350 + position%13 * 70;
    posIMG.y = 15 + (position/13) * 85;
    
    return posIMG;
}

int transforme_clic_en_tuile_joueur(LISTE joueur, SDL_Surface* ecran, SDL_Rect posClic)
{
	SDL_Rect posIMG;
	int posTuile;

	posTuile = (posClic.x - 355) / 70 + (((posClic.y - 545) / 80)) * 11;
	posIMG = transforme_tuile_en_position_joueur(posTuile);
	afficher_tuile_select(ecran, posIMG);
	SDL_Flip(ecran);
	
	return posTuile;
}

int transforme_clic_en_tuile_plateau(LISTE_DE_LISTES plateau, SDL_Surface* ecran, SDL_Rect posClic)
{
	SDL_Rect posIMG;
	int posTuile;
	
	posTuile = (posClic.x - 355) / 70 + (((posClic.y - 10) / 80)) * 13;
	posIMG = transforme_tuile_en_position_plateau(posTuile);
	afficher_tuile_select(ecran, posIMG);
	SDL_Flip(ecran);
	
	return posTuile;
}

int determiner_premier_joueur(LISTE pioche, SDL_Surface *ecran, SDL_Surface *tabSprites[], JOUEUR *joueurs, DONNEES_FICHIER **tabDonnees, int numJ)
{
	int i, j, indice=0;
	TUILE tab[NB_JOUEURS_MAX];
	ELEMENT* tmp = pioche.deb_liste;
	
	for (i=0; i < NB_JOUEURS_MAX; i++)
	{
		indice = (random() % pioche.taille);
		
		for(j=0 ; j<indice ; j++)
		{
			tmp = tmp->suivant;
		}
		
		tab[i] = tmp->tuile;
		tmp = pioche.deb_liste;
	}
	
	afficher_premier_joueur(ecran, tab, tabSprites, joueurs, tabDonnees, numJ);
	
	indice = 0;
	for(i=1; i<NB_JOUEURS_MAX; i++)
	{
		if (tab[i].numero > tab[indice].numero)
			indice = i;
	}
	
	if(indice)
		indice--;
	else
		indice = 3;
	
	return indice;
}
	
void jouer_humain(LISTE *pioche, LISTE_DE_LISTES *plateau, SDL_Surface *ecran, SDL_Surface *tabSprites[], int numJ, JOUEUR *joueurs, DONNEES_FICHIER **tabDonnees)
{
	int posTuile, posPlateau, numSerie=1, premSerie = 1, test=0;
	SDL_Rect posClic, posBtnValider = {1140,640,140,80}, posPioche = {1140,543,60,90}, posBtnRecom = {1200,543,80,80};
	SDL_Event event;
	BOOLEEN aJoue = FAUX;
	MODE_DE_TRI modeTri = COULEURS;
	LISTE copiejoueur;
	LISTE* tmpl = plateau->prem_liste;
	LISTE* listeJouee = plateau->prem_liste;
	LISTE_DE_LISTES copieplateau;
	
	copieplateau.prem_liste = NULL;
	copieplateau.taille = 0;
	copiejoueur = creer_liste_vide();
	copier_liste_de_listes(plateau->prem_liste, &copieplateau, modeTri);
	copier_liste(joueurs[numJ].main.deb_liste, &copiejoueur, modeTri);
	aJoue = FAUX;
	
	while(tmpl != NULL)
	{
		tmpl = tmpl->suivante;
		premSerie++;
	}
		
	do
	{
		do
		{
			afficher_jeu(ecran, *plateau, tabSprites, numJ, joueurs, tabDonnees);
			SDL_PollEvent(&event);
			posClic = recuperer_coord_clic();

			if(est_dans_rect(posClic, posBtnValider))
			{
				if(aJoue && est_plateau_valide(plateau, joueurs[numJ].aFait30, premSerie)) // Si le coup est valide...
				{
					vider_liste_de_listes(copieplateau.prem_liste);
					vider_liste(copiejoueur.deb_liste);
					joueurs[numJ].aFait30 = VRAI;
					return;
				}
				else // Sinon on recommence le tour
				{
					vider_liste_de_listes(plateau->prem_liste);
					*plateau = copieplateau;
					vider_liste(joueurs[numJ].main.deb_liste);
					joueurs[numJ].main = copiejoueur;
					copieplateau.prem_liste = NULL;
					copieplateau.taille = 0;
					copiejoueur = creer_liste_vide();
					copier_liste_de_listes(plateau->prem_liste, &copieplateau, modeTri);
					copier_liste(joueurs[numJ].main.deb_liste, &copiejoueur, modeTri);
					aJoue = FAUX;
					test = -1;
				}
			}	
			else if(est_dans_rect(posClic, posPioche))
			{
				vider_liste_de_listes(plateau->prem_liste);
				*plateau = copieplateau;
				vider_liste(joueurs[numJ].main.deb_liste);
				joueurs[numJ].main = copiejoueur;
				piocher(pioche, &joueurs[numJ].main);
				return;
			}
			else if(est_dans_rect(posClic, posBtnRecom))
			{
				vider_liste_de_listes(plateau->prem_liste);
				*plateau = copieplateau;
				vider_liste(joueurs[numJ].main.deb_liste);
				joueurs[numJ].main = copiejoueur;
				copieplateau.prem_liste = NULL;
				copieplateau.taille = 0;
				copiejoueur = creer_liste_vide();
				copier_liste_de_listes(plateau->prem_liste, &copieplateau, modeTri);
				copier_liste(joueurs[numJ].main.deb_liste, &copiejoueur, modeTri);
				aJoue = FAUX;
				test = -1;
			}
			else if((posClic.x > 355 && posClic.x < 1130) && posClic.y > 545) // Si on clique dans la main
			{
				posTuile = transforme_clic_en_tuile_joueur(joueurs[numJ].main, ecran, posClic) +1;
				if (posTuile <= joueurs[numJ].main.taille)
					test = 1;
				else
					test = -1;
			}
			else if(joueurs[numJ].aFait30 && posClic.x > 355 && posClic.y < 523) // Si on clique sur le plateau et que le joueur a déjà posé
			{	
				posTuile = transforme_clic_en_tuile_plateau(*plateau, ecran, posClic) +1;
				listeJouee = plateau->prem_liste;
				
				while(listeJouee != NULL && posTuile > listeJouee->taille)
				{
					posTuile -= listeJouee->taille+1;
					listeJouee = listeJouee->suivante;
				}
				
				if (listeJouee != NULL && posTuile != 0)
					test = 2;
				else
					test = -1;
			}
			else
				test = -1;
			
			if (test != -1) // Si un choix de tuile valide a été fait avant
			{
				do
				{
					posClic = recuperer_coord_clic();
				} while (posClic.x <= 355 || posClic.y >= 523); // Position du plateau
				
				posPlateau = transforme_clic_en_tuile_plateau(*plateau, ecran, posClic);
				
				tmpl = plateau->prem_liste;
				numSerie = 1;
		
				while(tmpl != NULL && posPlateau > tmpl->taille)
				{
					posPlateau -= tmpl->taille+1;
					tmpl = tmpl->suivante;
					numSerie++;
				}
					
				if(joueurs[numJ].aFait30 || numSerie >= premSerie) // On joue si le joueur a déjà posé ou si il n'a pas posé et qu'il ouvre une nouvelle série
				{
					if (test == 1)
						jouer(&joueurs[numJ].main, plateau, modeTri, posTuile, numSerie);
					else
						jouer(listeJouee, plateau, modeTri, posTuile, numSerie);
				}
			}
			
			afficher_jeu(ecran, *plateau, tabSprites, numJ, joueurs, tabDonnees);
			
			if(!aJoue && joueurs[numJ].main.taille != copiejoueur.taille)
				aJoue = VRAI;
				
		}while(test == -1);
	}while(1);
}

void ecran_titre_choix(int *choix)
{
    SDL_Rect posClic, posBtnJouer = {480,400,320,160}, posBtnReg = {1190,630,80,80}, posBtnScores = {10,630,80,80};
    
    while(!*choix)
    {
        posClic = recuperer_coord_clic();
      
        if(est_dans_rect(posClic,posBtnJouer))
            *choix = 1;
        else if(est_dans_rect(posClic,posBtnReg))
            *choix = 2;
        else if(est_dans_rect(posClic,posBtnScores))
	    *choix = 3;
    }
}

void ecran_historique_choix(int *choixScores)
{
	SDL_Rect posClic, posBtnRetour = {10,630,80,80}, posBtnPartie = {0,0,80,80};
	int i,j;
	
	while(!*choixScores)
	{
		posClic = recuperer_coord_clic();
		
		if(est_dans_rect(posClic,posBtnRetour))
			*choixScores = 30;
		else
		{
			for(i=0;i<2;i++)
			{
				for(j=0;j<5;j++)
				{
					posBtnPartie.x = 280 + j*160;
					posBtnPartie.y = 240 + i*160;
					if(est_dans_rect(posClic,posBtnPartie))
						*choixScores = (i*5)+(j+1);
				}
			}
		}
	}
}



int main(void)
{
	SDL_Surface *ecran = NULL;
	SDL_Surface *tabSprites[53];
	SDL_Rect clic, posBtnRetour = {10,630,80,80};
	int numJoueur, nbTuilesMain, choix = 0, choixScores = 0, i, j, nbJoueurs=1,nbManches=1,mancheActuelle=0;
	
	DONNEES_FICHIER **tabDonnees=NULL;
	LISTE pioche = creer_liste_vide();
	LISTE_DE_LISTES plateau;
	JOUEUR joueurs[NB_JOUEURS_MAX];
	
	srandom(time(NULL));
	
	// Récupération des données enregistrées dans score.dat :
	
	tabDonnees=(DONNEES_FICHIER**)malloc(sizeof(DONNEES_FICHIER*)*NB_PARTIES_MAX);
	for(i=0;i<NB_PARTIES_MAX;i++)
	{
		tabDonnees[i]=(DONNEES_FICHIER*)malloc(sizeof(DONNEES_FICHIER)*NB_JOUEURS_MAX);
		for(j=0;j<NB_JOUEURS_MAX;j++)
		{
			tabDonnees[i][j].scoreFinal=0;
			tabDonnees[i][j].pseudo = (char*)malloc(sizeof(char)*(LONGUEUR_PSEUDO));
			strcpy(tabDonnees[i][j].pseudo,"          ");
		}
	}
	lire_donnees_fichier(tabDonnees);
	
	// Initialisation de la SDL :
	
	SDL_Init(SDL_INIT_VIDEO);
	ecran = SDL_SetVideoMode(1280,720,32,SDL_HWSURFACE|SDL_DOUBLEBUF);
	
	if(ecran == NULL)
	{
		fprintf(stderr,"Impossible de charger le mode vidéo : %s\n",SDL_GetError());
		exit(EXIT_FAILURE);
	}
	SDL_WM_SetCaption("Rummikub",NULL);
	TTF_Init();
	if(SDL_Init(SDL_INIT_VIDEO) < 0)
	{
		fprintf(stderr,"Erreur d'initialisation : %s\n",SDL_GetError());
		exit(EXIT_FAILURE);
	}	
	
	// Initialisation des variables joueurs :
	
	for(i = 0; i < NB_JOUEURS_MAX; i++)
	{
		joueurs[i].aFait30 = FAUX;
		joueurs[i].main = creer_liste_vide();
		joueurs[i].pseudo = (char*)malloc(sizeof(char)*(LONGUEUR_PSEUDO+1));
		strcpy(joueurs[i].pseudo,"          ");
		joueurs[i].score = 0;
	}
	
	attribuer_sprites(tabSprites);
	
	// Gestion de l'écran titre :
	
	while(choix != 1)
	{
		afficher_ecran_titre(ecran);
		ecran_titre_choix(&choix);
		
		switch(choix)
		{
			case 1: // Démarrage du jeu
				entrer_pseudos_joueurs(ecran,joueurs,nbJoueurs);
				break;
			
			case 2: // Ecran des paramètres
				definir_options_jeu(ecran,&nbJoueurs,&nbManches);
				choix = 0;
				break;
				
			case 3: // Ecran des scores précédents
				while(choixScores != 30)
				{
					choixScores = 0;
					afficher_ecran_historique(ecran);
					ecran_historique_choix(&choixScores);
					
					if(choixScores > 0 && choixScores <= 10)
					{
						afficher_ecran_scores(ecran,tabDonnees,choixScores-1);
						do
						{
							clic = recuperer_coord_clic();
						} while(!est_dans_rect(clic,posBtnRetour));
					}
				}
				choixScores = 0;
				choix = 0;
				break;
		}
	}
		
	// Initialisation des ordinateurs et des joueurs :
	
	for(i=0;i<NB_JOUEURS_MAX;i++)
	{
		if(i<nbJoueurs)
			joueurs[i].estOrdi = FAUX;
		else
			joueurs[i].estOrdi = VRAI;
	}
	
	// Remise à 0 des données de la première partie (celle que l'on va enregistrer) à 0 et mise des pseudos modifiés
	
	decaler_scores_donnees(tabDonnees);
	for(numJoueur=0;numJoueur<NB_JOUEURS_MAX;numJoueur++)
	{
		tabDonnees[0][numJoueur].scoreFinal=0;
		strcpy(tabDonnees[0][numJoueur].pseudo,joueurs[numJoueur].pseudo);
	}	
	
	// Début du jeu en lui-même :
	
	for(mancheActuelle=0; mancheActuelle<nbManches; mancheActuelle++)
	{
		numJoueur = 0;
		plateau.prem_liste = NULL;
		plateau.taille = 0;

		initialiser_pioche(&pioche);
		
		numJoueur = determiner_premier_joueur(pioche, ecran, tabSprites, joueurs, tabDonnees, numJoueur);
		
		for(int i = 0; i < NB_JOUEURS_MAX; i++)
		{
			joueurs[i].aFait30 = FAUX;
			joueurs[i].main = creer_liste_vide();
			joueurs[i].score = 0;
			for(int j = 0; j < 14; j++)	
			{
				piocher(&pioche, &joueurs[i].main);
			}	
		}
		
		// Boucle principale du jeu :
		
		while( (pioche.taille) && (joueurs[numJoueur].main.taille) )
		{
			numJoueur = (numJoueur+1)%NB_JOUEURS_MAX;
			
			if(joueurs[numJoueur].estOrdi)
			{
				nbTuilesMain = joueurs[numJoueur].main.taille;
				joueurs[numJoueur].aFait30 = ia(&(joueurs[numJoueur].main), &plateau, &pioche, joueurs[numJoueur].aFait30);
				
				if(joueurs[numJoueur].main.taille == nbTuilesMain)
					piocher(&pioche, &(joueurs[numJoueur].main));
			}
			else
				jouer_humain(&pioche, &plateau, ecran, tabSprites, numJoueur, joueurs, tabDonnees);				
		}
		numJoueur = (numJoueur+1)%NB_JOUEURS_MAX;
		
		// On enregistre les scores de la manche qui vient de se terminer :
		
		affecter_scores_a_une_manche(joueurs,pioche);		
		remplir_manche_dans_tabDonnees(joueurs,tabDonnees,mancheActuelle);
		
		// On réinitialise les listes à 0 :
		
		vider_liste(pioche.deb_liste);
		pioche.taille = 0;
		vider_liste_de_listes(plateau.prem_liste);
		plateau.taille = 0;
		for(i = 0; i < NB_JOUEURS_MAX; i++)
		{
			vider_liste(joueurs[i].main.deb_liste);
			joueurs[i].main.taille = 0;
		}
	}
	
	// Partie finie, on trie les scores totaux :
	
	trier_score_decroissant(tabDonnees);
	enregistrer_donnees_fichier(tabDonnees);
	afficher_ecran_scores(ecran,tabDonnees,0);

	do
	{
		clic = recuperer_coord_clic();
	}while(!est_dans_rect(clic,posBtnRetour));
	
	// On libère toute la mémoire restante :
	
	TTF_Quit();
	SDL_FreeSurface(ecran);
	SDL_Quit();
	for(i=0;i<NB_PARTIES_MAX;i++)
		free(tabDonnees[i]);
	free(tabDonnees);
	for(i = 0; i < 53; i++)
		SDL_FreeSurface(tabSprites[i]);
	
	return 0;
}
