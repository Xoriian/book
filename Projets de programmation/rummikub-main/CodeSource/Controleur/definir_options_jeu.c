/////////////////////////////////////// Projet Rummikub - Groupe 9 - Gestion des options de jeu ////////////////////////////////////////

#include "definir_options_jeu.h"

void choisir_nombre_joueurs(SDL_Surface *ecran, SDL_Rect posClic, SDL_Rect posTextePlusJoueurs, SDL_Rect posTexteMoinsJoueurs, int *nbJoueurs)
{
	char chaineNbJoueurs[20];
	SDL_Rect posNbJoueurs={875,720/3+11,18,18};
	
	if( (*nbJoueurs<NB_JOUEURS_MAX) && (est_dans_rect(posClic,posTextePlusJoueurs)) )
	{
		*nbJoueurs=*nbJoueurs+1;
		sprintf(chaineNbJoueurs,"%d",*nbJoueurs);
		SDL_FillRect(ecran,&posNbJoueurs,SDL_MapRGB(ecran->format,COULEUR_FOND_BARRE));
		afficher_texte(ecran,chaineNbJoueurs,875,720/3+2,28);
	}
	else if( (*nbJoueurs>1) && (est_dans_rect(posClic,posTexteMoinsJoueurs)) )
	{
		*nbJoueurs=*nbJoueurs-1;
		sprintf(chaineNbJoueurs,"%d",*nbJoueurs);
		SDL_FillRect(ecran,&posNbJoueurs,SDL_MapRGB(ecran->format,COULEUR_FOND_BARRE));
		afficher_texte(ecran,chaineNbJoueurs,875,720/3+2,28);
	}
	
	SDL_Flip(ecran);
}

void choisir_nombre_manches(SDL_Surface *ecran, SDL_Rect posClic, SDL_Rect posTextePlusManches, SDL_Rect posTexteMoinsManches, int *nbManches)
{
	char chaineNbManches[20];
	SDL_Rect posNbManches={875,720/3+55,18,18};
	
	if( (*nbManches<NB_MANCHES_MAX) && (est_dans_rect(posClic,posTextePlusManches)) )
	{
		*nbManches=*nbManches+1;
		sprintf(chaineNbManches,"%d",*nbManches);
		SDL_FillRect(ecran,&posNbManches,SDL_MapRGB(ecran->format,COULEUR_FOND_BARRE));
		afficher_texte(ecran,chaineNbManches,875,720/3+46,28);
	}
	else if( (*nbManches>1) && (est_dans_rect(posClic,posTexteMoinsManches)) )
	{
		*nbManches=*nbManches-1;
		sprintf(chaineNbManches,"%d",*nbManches);
		SDL_FillRect(ecran,&posNbManches,SDL_MapRGB(ecran->format,COULEUR_FOND_BARRE));		
		afficher_texte(ecran,chaineNbManches,875,720/3+46,28);
	}
	
	SDL_Flip(ecran);
}

void definir_options_jeu(SDL_Surface *ecran,int *nbJoueurs,int *nbManches)
{
	BOOLEEN clicRetour= FAUX;
	SDL_Rect posClic, posBoutonRetour={10,630,80,80};
	SDL_Rect posTextePlusJoueurs={990,720/3+12,16,16}, posTexteMoinsJoueurs={750,720/3+12,16,16};
	SDL_Rect posTextePlusManches={990,720/3+56,16,16}, posTexteMoinsManches={750,720/3+56,16,16};
	
	afficher_texte_choix_joueurs_manches(ecran,posTextePlusJoueurs,posTexteMoinsJoueurs,posTextePlusManches,posTexteMoinsManches,posBoutonRetour,*nbJoueurs,*nbManches);
	
	while(!clicRetour)
	{
		posClic=recuperer_coord_clic();
		
		choisir_nombre_joueurs(ecran,posClic,posTextePlusJoueurs,posTexteMoinsJoueurs,nbJoueurs);
		choisir_nombre_manches(ecran,posClic,posTextePlusManches,posTexteMoinsManches,nbManches);
		
		clicRetour=est_dans_rect(posClic,posBoutonRetour);
		
		SDL_Flip(ecran);
	}
}
