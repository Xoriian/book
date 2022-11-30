/////////////////////////////////////// Projet Rummikub - Groupe 9 - Affichage de l'écran des paramètres ////////////////////////////////////////

#include "ecran_param.h"

void afficher_texte(SDL_Surface* ecran, char* texteAAfficher, int xpos,int ypos,int taille)
{
	TTF_Font *police=NULL;
	SDL_Surface *surfaceTexte=NULL;
	SDL_Rect posTexte;
	SDL_Color coulTxt={COULEUR_TEXTE};
	
	police=TTF_OpenFont("../polices/NerkoOne-Regular.ttf",taille);
	
	surfaceTexte=TTF_RenderUTF8_Blended(police,texteAAfficher,coulTxt);
	
	posTexte.x=xpos;posTexte.y=ypos;
	
	SDL_BlitSurface(surfaceTexte,NULL,ecran,&posTexte);
	
	SDL_FreeSurface(surfaceTexte);
	TTF_CloseFont(police);
}

void afficher_texte_choix_joueurs_manches(SDL_Surface *ecran, SDL_Rect posTextePlusJoueurs, SDL_Rect posTexteMoinsJoueurs, SDL_Rect posTextePlusManches, SDL_Rect posTexteMoinsManches, SDL_Rect posBoutonRetour, int nbJoueurs, int nbManches)
{
	SDL_Surface *texteMoins=NULL, *textePlus=NULL, *boutonRetour=NULL, *barParam=NULL, *titreSection=NULL;
	char chaineNbJoueurs[20],chaineNbManches[20];
	SDL_Rect posIMG;
	
	/* Charger les images et valeurs nécessaires aux paramètres */
	
	sprintf(chaineNbJoueurs,"%d",nbJoueurs);
	sprintf(chaineNbManches,"%d",nbManches);
	
	textePlus = SDL_LoadBMP("../sprites/plus.bmp");
	SDL_SetColorKey(textePlus,SDL_SRCCOLORKEY,SDL_MapRGB(textePlus->format,COULEUR_TRANSPARENCE)); //Ajouter de la transparence sur l'image
	texteMoins = SDL_LoadBMP("../sprites/moins.bmp");
	SDL_SetColorKey(texteMoins,SDL_SRCCOLORKEY,SDL_MapRGB(texteMoins->format,COULEUR_TRANSPARENCE));
	boutonRetour = SDL_LoadBMP("../sprites/btnRetour.bmp");
	SDL_SetColorKey(boutonRetour,SDL_SRCCOLORKEY,SDL_MapRGB(boutonRetour->format,COULEUR_TRANSPARENCE));
	barParam = SDL_LoadBMP("../sprites/barParamBG.bmp");
	SDL_SetColorKey(barParam,SDL_SRCCOLORKEY,SDL_MapRGB(barParam->format,COULEUR_TRANSPARENCE));
	titreSection = SDL_LoadBMP("../sprites/titreSection.bmp");
	SDL_SetColorKey(titreSection,SDL_SRCCOLORKEY,SDL_MapRGB(titreSection->format,COULEUR_TRANSPARENCE));
	
	/* Afficher les paramètres */
	
	SDL_FillRect(ecran,NULL,SDL_MapRGB(ecran->format,COULEUR_FOND));
	
	posIMG.x = 1280/2 - titreSection->w/2;
	posIMG.y = 0;
	SDL_BlitSurface(titreSection,NULL,ecran,&posIMG);
	afficher_texte(ecran,"Paramètres",1280/2-titreSection->w/3,titreSection->h/3,45);
	
	posIMG.x = 0;
	posIMG.y = 720/3;
	SDL_BlitSurface(barParam,NULL,ecran,&posIMG);
	posIMG.y += barParam->h+4;
	SDL_BlitSurface(barParam,NULL,ecran,&posIMG);
	
	afficher_texte(ecran,"Nombre de joueurs",200,720/3+2,28);
	SDL_BlitSurface(texteMoins,NULL,ecran,&posTexteMoinsJoueurs);
	afficher_texte(ecran,chaineNbJoueurs,875,720/3+2,28);
	SDL_BlitSurface(textePlus,NULL,ecran,&posTextePlusJoueurs);
	
	afficher_texte(ecran,"Nombre de manches",200,posIMG.y+2,28);
	SDL_BlitSurface(texteMoins,NULL,ecran,&posTexteMoinsManches);
	afficher_texte(ecran,chaineNbManches,875,posIMG.y+2,28);
	SDL_BlitSurface(textePlus,NULL,ecran,&posTextePlusManches);
	
	SDL_BlitSurface(boutonRetour,NULL,ecran,&posBoutonRetour);
	
	SDL_Flip(ecran);
	
	SDL_FreeSurface(textePlus);
	SDL_FreeSurface(texteMoins);
	SDL_FreeSurface(boutonRetour);
	SDL_FreeSurface(barParam);
	SDL_FreeSurface(titreSection);
}

void afficher_instructions_demander_pseudo(SDL_Surface *ecran)
{
	SDL_Surface *barParam=NULL;
	char texteDemandePseudo[60];
	SDL_Rect posIMG;

	barParam = SDL_LoadBMP("../sprites/barParamBG.bmp");
	SDL_SetColorKey(barParam,SDL_SRCCOLORKEY,SDL_MapRGB(barParam->format,COULEUR_TRANSPARENCE));
	
	SDL_FillRect(ecran,NULL,SDL_MapRGB(ecran->format,COULEUR_FOND));
	
	posIMG.x = 0; posIMG.y = 150;
	SDL_BlitSurface(barParam,NULL,ecran,&posIMG);
	posIMG.y = 200;
	SDL_BlitSurface(barParam,NULL,ecran,&posIMG);
	posIMG.y = 500;
	SDL_BlitSurface(barParam,NULL,ecran,&posIMG);
	
	sprintf(texteDemandePseudo,"Veuillez entrer votre pseudo (%d caractères maximum)",LONGUEUR_PSEUDO);
	
	SDL_BlitSurface(barParam,NULL,ecran,&posIMG);
	afficher_texte(ecran,texteDemandePseudo,350,150,28);
	afficher_texte(ecran,"Confirmez avec Entrée",500,500,28);
	
	SDL_Flip(ecran);
	
	SDL_FreeSurface(barParam);
}
