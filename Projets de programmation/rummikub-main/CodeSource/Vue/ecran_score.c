/////////////////////////////////////// Projet Rummikub - Groupe 9 - Affichage de l'écran des scores ////////////////////////////////////////

#include "ecran_score.h"

void afficher_ecran_scores(SDL_Surface *ecran, DONNEES_FICHIER **tabDonnees, int numPartie)
{
	SDL_Surface *titreSection = NULL, *btnRetour = NULL, *fondScores = NULL;
	SDL_Surface *med[NB_JOUEURS_MAX];
	SDL_Rect posIMG;
	char texte[30];
	int i;
	
	titreSection = SDL_LoadBMP("../sprites/titreSection.bmp"); btnRetour = SDL_LoadBMP("../sprites/btnRetour.bmp"); fondScores = SDL_LoadBMP("../sprites/fondScores.bmp");
	med[0] = SDL_LoadBMP("../sprites/med1.bmp"); med[1] = SDL_LoadBMP("../sprites/med2.bmp"); med[2] = SDL_LoadBMP("../sprites/med3.bmp"); med[3] = SDL_LoadBMP("../sprites/med4.bmp");
	
	SDL_SetColorKey(titreSection,SDL_SRCCOLORKEY,SDL_MapRGB(titreSection->format,COULEUR_TRANSPARENCE));
	SDL_SetColorKey(btnRetour,SDL_SRCCOLORKEY,SDL_MapRGB(btnRetour->format,COULEUR_TRANSPARENCE));
	SDL_SetColorKey(fondScores,SDL_SRCCOLORKEY,SDL_MapRGB(fondScores->format,COULEUR_TRANSPARENCE));
	
	for(i=0; i<NB_JOUEURS_MAX; i++)
		SDL_SetColorKey(med[i],SDL_SRCCOLORKEY,SDL_MapRGB(med[i]->format,COULEUR_TRANSPARENCE));
	
	SDL_FillRect(ecran,NULL,SDL_MapRGB(ecran->format,COULEUR_FOND));
	
	posIMG.x = 1280/2 - titreSection->w/2;
	posIMG.y = 0;
	SDL_BlitSurface(titreSection,NULL,ecran,&posIMG);
	afficher_texte(ecran,"Scores",1280/2-titreSection->w/5,titreSection->h/3,45);
	
	posIMG.x = 160;
	posIMG.y = titreSection->h;
	SDL_BlitSurface(fondScores,NULL,ecran,&posIMG);
	
	posIMG.x = 10;
	posIMG.y = 630;
	SDL_BlitSurface(btnRetour,NULL,ecran,&posIMG);
	
	posIMG.x = 400;
	posIMG.y = 220;
	
	for(i=0; i<NB_JOUEURS_MAX; i++)
	{
		SDL_BlitSurface(med[i],NULL,ecran,&posIMG);
		sprintf(texte,"%d",tabDonnees[numPartie][i].scoreFinal);
		afficher_texte(ecran,tabDonnees[numPartie][i].pseudo,550,235+i*95,35);
		afficher_texte(ecran,texte,750,235+i*95,35);
		posIMG.y += med[i]->h + 15;
	}
	
	SDL_Flip(ecran);
	
	SDL_FreeSurface(titreSection); SDL_FreeSurface(btnRetour); SDL_FreeSurface(fondScores);
	
	for(i=0; i<NB_JOUEURS_MAX; i++)
		SDL_FreeSurface(med[i]);
}

void afficher_ecran_historique(SDL_Surface *ecran)
{
	SDL_Surface *titreSection = NULL, *btnRetour = NULL, *btnFond = NULL;
	SDL_Rect posIMG;
	int i,j;
	char texte[5];
	
	titreSection = SDL_LoadBMP("../sprites/titreSection.bmp"); btnRetour = SDL_LoadBMP("../sprites/btnRetour.bmp"); btnFond = SDL_LoadBMP("../sprites/btnFond.bmp");

	SDL_SetColorKey(titreSection,SDL_SRCCOLORKEY,SDL_MapRGB(titreSection->format,COULEUR_TRANSPARENCE));
	SDL_SetColorKey(btnRetour,SDL_SRCCOLORKEY,SDL_MapRGB(btnRetour->format,COULEUR_TRANSPARENCE));
	SDL_SetColorKey(btnFond,SDL_SRCCOLORKEY,SDL_MapRGB(btnFond->format,COULEUR_TRANSPARENCE));
	
	SDL_FillRect(ecran,NULL,SDL_MapRGB(ecran->format,COULEUR_FOND));
	
	posIMG.x = 1280/2 - titreSection->w/2;
	posIMG.y = 0;
	SDL_BlitSurface(titreSection,NULL,ecran,&posIMG);
	afficher_texte(ecran,"Historique",1280/2-90,titreSection->h/3,45);
	
	posIMG.x = 10;
	posIMG.y = 630;
	SDL_BlitSurface(btnRetour,NULL,ecran,&posIMG);
	
	for(i=0;i<2;i++)
	{
		for(j=0;j<5;j++)
		{
			posIMG.x = 280 + 160*j;
			posIMG.y = 240 + 160*i;
			SDL_BlitSurface(btnFond,NULL,ecran,&posIMG);
			sprintf(texte,"%d",(i*5)+(j+1));
			
			if(i == 1 && j == 4) //Pour bien centrer le 10
				afficher_texte(ecran,texte,305+j*160,255+i*160,40);
			else
				afficher_texte(ecran,texte,310+j*160,255+i*160,40);
		}
	}
	
	SDL_Flip(ecran);
	
	SDL_FreeSurface(titreSection); SDL_FreeSurface(btnRetour); SDL_FreeSurface(btnFond);
}
