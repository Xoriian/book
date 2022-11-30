/////////////////////////////////////// Projet Rummikub - Groupe 9 - Affichage du plateau de jeu ////////////////////////////////////////

#include "plateau_jeu.h"

void afficher_plateau_jeu(SDL_Surface *ecran, int numJ, JOUEUR *joueurs, DONNEES_FICHIER **tabDonnees) 
{
	int i;
	SDL_Surface *cadreJ[NB_JOUEURS_MAX];
	SDL_Surface *mainJ, *plateau;
	SDL_Surface *btnValider, *btnRecom, *pioche;
	SDL_Rect posIMG;
	char texte[20];
	
	cadreJ[0] = SDL_LoadBMP("../sprites/cadreJ1_p.bmp"); cadreJ[1] = SDL_LoadBMP("../sprites/cadreJ2_p.bmp"); 
	cadreJ[2] = SDL_LoadBMP("../sprites/cadreJ3_p.bmp"); cadreJ[3] = SDL_LoadBMP("../sprites/cadreJ4_p.bmp");
	mainJ = SDL_LoadBMP("../sprites/mainJ.bmp"); plateau = SDL_LoadBMP("../sprites/plateau.bmp");
	btnValider = SDL_LoadBMP("../sprites/btnValider.bmp"); btnRecom = SDL_LoadBMP("../sprites/btnRecommencer.bmp"); pioche = SDL_LoadBMP("../sprites/tuiles/pioche.bmp");
		
	for(i=0; i<NB_JOUEURS_MAX; i++)
		SDL_SetColorKey(cadreJ[i],SDL_SRCCOLORKEY,SDL_MapRGB(cadreJ[i]->format,COULEUR_TRANSPARENCE));
		
	SDL_SetColorKey(btnValider,SDL_SRCCOLORKEY,SDL_MapRGB(btnValider->format,COULEUR_TRANSPARENCE));
	SDL_SetColorKey(btnRecom,SDL_SRCCOLORKEY,SDL_MapRGB(btnValider->format,COULEUR_TRANSPARENCE));
	SDL_SetColorKey(pioche,SDL_SRCCOLORKEY,SDL_MapRGB(pioche->format,COULEUR_TRANSPARENCE));
	
	SDL_FillRect(ecran,NULL,SDL_MapRGB(ecran->format,COULEUR_FOND));
	
	// Afficher données cadres
	
	posIMG.x = 5;
	posIMG.y = 0;
	
	for(i=0; i<NB_JOUEURS_MAX; i++)
	{
		SDL_BlitSurface(cadreJ[i],NULL,ecran,&posIMG);
		afficher_texte(ecran,joueurs[i].pseudo,75,7+i*cadreJ[i]->h,15);
		sprintf(texte,"%d",tabDonnees[0][i].scoreFinal);
		afficher_texte(ecran,texte,85,30+i*cadreJ[i]->h,15);
		sprintf(texte,"%d",joueurs[i].main.taille);
		afficher_texte(ecran,texte,77,50+i*cadreJ[i]->h,15);
		posIMG.y += cadreJ[i]->h;
	}
	
	// Affichage du reste
	
	posIMG.x = 1280 - plateau->w;
	posIMG.y = 0;
	SDL_BlitSurface(plateau,NULL,ecran,&posIMG);
	
	posIMG.y += plateau->h;
	SDL_BlitSurface(mainJ,NULL,ecran,&posIMG);
	
	posIMG.x = 1280 - btnValider->w;
	posIMG.y = 720 - btnValider->h;
	SDL_BlitSurface(btnValider,NULL,ecran,&posIMG);
	
	posIMG.y -= (mainJ->h - btnValider->h)/2 + pioche->h/2;
	SDL_BlitSurface(pioche,NULL,ecran,&posIMG);
	
	posIMG.x += pioche->w;
	SDL_BlitSurface(btnRecom,NULL,ecran,&posIMG);
	
	for(i=0; i<NB_JOUEURS_MAX; i++)
		SDL_FreeSurface(cadreJ[i]); 
		
	SDL_FreeSurface(mainJ); SDL_FreeSurface(plateau);
	SDL_FreeSurface(btnValider); SDL_FreeSurface(btnRecom); SDL_FreeSurface(pioche); 
}

void afficher_tuiles_joueur(JOUEUR joueur, SDL_Surface *ecran, SDL_Surface *tabSprites[])
{
	int i=0;
	SDL_Rect posIMG;
	ELEMENT* tmp = joueur.main.deb_liste;
	
	while(tmp != NULL)
	{
		posIMG = transforme_tuile_en_position_joueur(i);
		if(tmp->tuile.estJoker)
			SDL_BlitSurface(tabSprites[0], NULL, ecran, &posIMG);
		else
			SDL_BlitSurface(tabSprites[(tmp->tuile.couleur * 13 + tmp->tuile.numero)], NULL, ecran, &posIMG);
		tmp = tmp->suivant;
		i++;
	}	
}

void afficher_tuiles_plateau(LISTE_DE_LISTES plateau, SDL_Surface *ecran, SDL_Surface *tabSprites[])
{
	int i=0;
	SDL_Rect posIMG;
	LISTE* tmpl = plateau.prem_liste;
	ELEMENT* tmpe;

	while(tmpl != NULL)
	{
		tmpe = tmpl->deb_liste;
		
		while(tmpe != NULL)
		{
			posIMG = transforme_tuile_en_position_plateau(i);
			if(tmpe->tuile.estJoker)
				SDL_BlitSurface(tabSprites[0], NULL, ecran, &posIMG);
			else
				SDL_BlitSurface(tabSprites[(tmpe->tuile.couleur * 13 + tmpe->tuile.numero)], NULL, ecran, &posIMG);
			tmpe = tmpe->suivant;
			i++;
		}
		tmpl = tmpl->suivante;
		i++;
	}
}

void afficher_tuile_select(SDL_Surface *ecran, SDL_Rect posIMG) 
{
    SDL_Surface *tuileSelect = NULL;
    
    tuileSelect = SDL_LoadBMP("../sprites/tuiles/tuileSelect.bmp");
    SDL_SetColorKey(tuileSelect,SDL_SRCCOLORKEY,SDL_MapRGB(tuileSelect->format,COULEUR_TRANSPARENCE));
    SDL_BlitSurface(tuileSelect,NULL,ecran,&posIMG);
    
    SDL_FreeSurface(tuileSelect);
}

void afficher_premier_joueur(SDL_Surface *ecran, TUILE tab[], SDL_Surface *tabSprites[], JOUEUR *joueurs, DONNEES_FICHIER **tabDonnees, int numJ)
{
	SDL_Rect posTuile = {200,0};
	int i;
	
	afficher_plateau_jeu(ecran, numJ, joueurs, tabDonnees);
	
	for(i=0 ; i<NB_JOUEURS_MAX ; i++)
	{
		if(tab[i].estJoker)
			SDL_BlitSurface(tabSprites[0], NULL, ecran, &posTuile);
		else
			SDL_BlitSurface(tabSprites[(tab[i].couleur * 13 + tab[i].numero)], NULL, ecran, &posTuile);
		
		posTuile.y += 80;
	}
	
	SDL_Flip(ecran);
	sleep(3);
	
	afficher_plateau_jeu(ecran, numJ, joueurs, tabDonnees);
	SDL_Flip(ecran);
}

void afficher_jeu(SDL_Surface *ecran, LISTE_DE_LISTES plateau, SDL_Surface *tabSprites[], int numJ, JOUEUR *joueurs, DONNEES_FICHIER **tabDonnees)
{
	afficher_plateau_jeu(ecran, numJ, joueurs, tabDonnees);
	afficher_tuiles_joueur(joueurs[numJ], ecran, tabSprites);
	afficher_tuiles_plateau(plateau, ecran, tabSprites);
	afficher_cadre_joueur_joue(ecran,numJ, joueurs,tabDonnees);

	SDL_Flip(ecran); 
}

void afficher_cadre_joueur_joue(SDL_Surface *ecran, int numJ, JOUEUR *joueurs, DONNEES_FICHIER **tabDonnees)
{
	SDL_Surface *cadreJG = NULL;
	SDL_Rect posIMG = {5,555,320,160};
	
	char texte[35];
	sprintf(texte,"../sprites/cadreJ%d_G.bmp",numJ+1);
	
	cadreJG = SDL_LoadBMP(texte);
	SDL_SetColorKey(cadreJG,SDL_SRCCOLORKEY,SDL_MapRGB(cadreJG->format,COULEUR_TRANSPARENCE));
	
	SDL_FillRect(ecran,&posIMG,SDL_MapRGB(ecran->format,COULEUR_FOND));
	SDL_BlitSurface(cadreJG,NULL,ecran,&posIMG);
	
	afficher_texte(ecran,joueurs[numJ].pseudo,150,570,28);
	
	sprintf(texte,"%d",tabDonnees[0][numJ].scoreFinal);
	afficher_texte(ecran,texte,180,614,30);
	
	sprintf(texte,"%d",joueurs[numJ].main.taille);
	afficher_texte(ecran,texte,155,660,25);
	
	SDL_FreeSurface(cadreJG);
}

