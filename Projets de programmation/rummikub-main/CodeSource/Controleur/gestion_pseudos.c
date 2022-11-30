/////////////////////////////////////// Projet Rummikub - Groupe 9 - Gestion des pseudos ////////////////////////////////////////

#include "gestion_pseudos.h"

void taper_pseudo_clavier(SDL_Surface* ecran, char* pseudoDemande)
{
	int curseur=0;
	BOOLEEN confirmerPseudo = FAUX;
	
	SDL_Event clavier;
	SDL_Rect posPseudo={575,210,500,20};
	
	SDL_EnableUNICODE(SDL_ENABLE);
	SDL_EnableKeyRepeat(0,0);
	
	do
	{
		SDL_WaitEvent(&clavier);

		if(clavier.type == SDL_QUIT)
			exit(0);
		
		if(clavier.type == SDL_KEYDOWN)
		{				
			if( (clavier.key.keysym.unicode == SDLK_BACKSPACE) && (curseur>0) ) //Supprimer la lettre à la position actuelle si on appuie sur Retour arrière
			{
				pseudoDemande[curseur-1]=' ';
				curseur-=1;
			}
			
			if( (!iscntrl(clavier.key.keysym.unicode)) && (curseur<(LONGUEUR_PSEUDO)) ) //Ajouter le symbole tapé à la position actuelle
			{
				pseudoDemande[curseur]=clavier.key.keysym.unicode;
				curseur+=1;
			}
			
			if( (clavier.key.keysym.unicode==SDLK_RETURN) && (!iscntrl(pseudoDemande[0])) && (pseudoDemande[0]!=' ') ) //Confirmer avec Entrée 
			{
				pseudoDemande[curseur] = '\0';
				confirmerPseudo = VRAI;
			}
			
			SDL_FillRect(ecran,&posPseudo,SDL_MapRGB(ecran->format,COULEUR_FOND_BARRE));
			afficher_texte(ecran,pseudoDemande,575,210,17);
			SDL_Flip(ecran);
		}

	} while(!confirmerPseudo);
	SDL_EnableUNICODE(SDL_DISABLE);
	
}

void enlever_espaces_inutiles_pseudo(char* pseudoDemande)
{
	int i=0;
	
	/* Enlever les espaces qui sont à la fin d'un pseudo (ex:"aaa       " devient "aaa") */

	while(pseudoDemande[i] != ' ' && pseudoDemande[i] != '\0')
		i++;
		
	pseudoDemande[i]='\0';
}

void recuperer_un_pseudo(SDL_Surface* ecran, char* pseudoDemande)
{
	afficher_instructions_demander_pseudo(ecran);
	taper_pseudo_clavier(ecran, pseudoDemande);
	enlever_espaces_inutiles_pseudo(pseudoDemande);
}


void entrer_pseudos_joueurs(SDL_Surface *ecran, JOUEUR joueurs[NB_JOUEURS_MAX], int nbJoueurs)
{
	for(int i=0;i<NB_JOUEURS_MAX;i++)
	{
		if((i+1)>nbJoueurs)
		{
			sprintf(joueurs[i].pseudo,"Ordi");
		}
		else
		{
			recuperer_un_pseudo(ecran,joueurs[i].pseudo);
		}
	}
}
