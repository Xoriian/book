/////////////////////////////////////// Projet Rummikub - Groupe 9 - Affichage de l'écran titre ////////////////////////////////////////

#include "ecran_titre.h"

void afficher_ecran_titre(SDL_Surface *ecran) 
{
	SDL_Surface *rummikub = NULL, *btnJouer = NULL, *btnReg = NULL, *btnScores = NULL;
	SDL_Rect posIMG;
	
	rummikub = SDL_LoadBMP("../sprites/rummikub.bmp");
	btnJouer = SDL_LoadBMP("../sprites/btnJouer.bmp");
	btnReg = SDL_LoadBMP("../sprites/btnReg.bmp");
	btnScores = SDL_LoadBMP("../sprites/btnScores.bmp");
	
	SDL_SetColorKey(rummikub,SDL_SRCCOLORKEY,SDL_MapRGB(rummikub->format,COULEUR_TRANSPARENCE));
	SDL_SetColorKey(btnJouer,SDL_SRCCOLORKEY,SDL_MapRGB(btnJouer->format,COULEUR_TRANSPARENCE));
	SDL_SetColorKey(btnReg,SDL_SRCCOLORKEY,SDL_MapRGB(btnReg->format,COULEUR_TRANSPARENCE));
	SDL_SetColorKey(btnScores,SDL_SRCCOLORKEY,SDL_MapRGB(btnScores->format,COULEUR_TRANSPARENCE));
	
	SDL_FillRect(ecran,NULL,SDL_MapRGB(ecran->format,COULEUR_FOND));
	
	posIMG.x = 165;
	posIMG.y = 140;
	SDL_BlitSurface(rummikub,NULL,ecran,&posIMG);
	
	posIMG.x = 480;
	posIMG.y = 400;
	SDL_BlitSurface(btnJouer,NULL,ecran,&posIMG);
	
	posIMG.x = 1190;
	posIMG.y = 630;
	SDL_BlitSurface(btnReg,NULL,ecran,&posIMG);
	
	posIMG.x = 10;
	posIMG.y = 630;
	SDL_BlitSurface(btnScores,NULL,ecran,&posIMG);
	
	SDL_Flip(ecran);
	
	SDL_FreeSurface(rummikub);
	SDL_FreeSurface(btnJouer);
	SDL_FreeSurface(btnReg);
	SDL_FreeSurface(btnScores);
}
