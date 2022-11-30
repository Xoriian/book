/////////////////////////////////////// Projet Rummikub - Groupe 9 - Traitement fichier score ////////////////////////////////////////

#include "traitement_fichier_score.h"


void calculer_totaux_tuiles_joueurs(JOUEUR joueurs[NB_JOUEURS_MAX])
{	
	ELEMENT* tmp = NULL;
	int numJoueur;
	
	for(numJoueur=0; numJoueur<NB_JOUEURS_MAX; numJoueur++)
	{
		tmp=joueurs[numJoueur].main.deb_liste;
		
		while( (tmp!=NULL) )
		{
			joueurs[numJoueur].score = joueurs[numJoueur].score + tmp->tuile.numero;
			tmp = tmp->suivant;
		}
	}
}


int trouver_joueur_avec_score_min(JOUEUR joueurs[NB_JOUEURS_MAX])
{
	int posMin=0, numJoueur;
	
	for(numJoueur=0; numJoueur<NB_JOUEURS_MAX; numJoueur++)
	{
		if(joueurs[numJoueur].score < joueurs[posMin].score)
			posMin=numJoueur;
	}
	
	return posMin;
}


void affecter_scores_a_une_manche(JOUEUR joueurs[NB_JOUEURS_MAX], LISTE pioche)
{
	int totalDesPerdants=0, posMin=0, numJoueur;
	
	calculer_totaux_tuiles_joueurs(joueurs);
	posMin=trouver_joueur_avec_score_min(joueurs);
	
	if(pioche.taille == 0)
	{
		for(numJoueur=0; numJoueur<NB_JOUEURS_MAX; numJoueur++)
		{
			//On ajoute le score du perdant à un total et on le rend négatif
			if(numJoueur!=posMin)
			{
				joueurs[numJoueur].score-=joueurs[posMin].score;
				totalDesPerdants+=joueurs[numJoueur].score;
				joueurs[numJoueur].score= -(joueurs[numJoueur].score);
			}
		}
		joueurs[posMin].score=totalDesPerdants;
	}
	else
	{
		for(numJoueur=0; numJoueur<NB_JOUEURS_MAX; numJoueur++)
		{
			if(numJoueur != posMin)
			{
				totalDesPerdants+=joueurs[numJoueur].score;
				joueurs[numJoueur].score= -(joueurs[numJoueur].score);
			}
			
		}
		joueurs[posMin].score=totalDesPerdants;
	}
	
}


void remplir_manche_dans_tabDonnees(JOUEUR *joueurs, DONNEES_FICHIER **tabDonnees, int mancheActuelle)
{
	int numJoueur;
	
	for(numJoueur=0; numJoueur<NB_JOUEURS_MAX; numJoueur++)
	{
		tabDonnees[0][numJoueur].scoreFinal += joueurs[numJoueur].score;
	}
	
}


void decaler_scores_donnees(DONNEES_FICHIER **tabDonnees)
{
	int numPartie, numJoueur;
	
	for(numPartie=NB_PARTIES_MAX-1;numPartie>0;numPartie--)
	{
		for(numJoueur=0;numJoueur<NB_JOUEURS_MAX;numJoueur++)
		{
			tabDonnees[numPartie][numJoueur].scoreFinal=tabDonnees[numPartie-1][numJoueur].scoreFinal;
			strcpy(tabDonnees[numPartie][numJoueur].pseudo,tabDonnees[numPartie-1][numJoueur].pseudo);
		}
	}
}


void trier_score_decroissant(DONNEES_FICHIER **tabDonnees)
{
	int i,j,tmpScore=0;
	char *tmpPseudo;
	
	tmpPseudo=(char*)malloc(sizeof(char)*(LONGUEUR_PSEUDO+1));
		
	for(i=0; i<NB_JOUEURS_MAX-1; i++)
	{
		for(j=0; j<NB_JOUEURS_MAX-i-1; j++)
		{
			if ( tabDonnees[0][j].scoreFinal < tabDonnees[0][j+1].scoreFinal )
			{
				tmpScore=tabDonnees[0][j].scoreFinal;
				tabDonnees[0][j].scoreFinal=tabDonnees[0][j+1].scoreFinal;
				tabDonnees[0][j+1].scoreFinal=tmpScore;
				
				
				strcpy(tmpPseudo,tabDonnees[0][j].pseudo);
				strcpy(tabDonnees[0][j].pseudo,tabDonnees[0][j+1].pseudo);
				strcpy(tabDonnees[0][j+1].pseudo,tmpPseudo);
			}
		}
	}
	
	free(tmpPseudo);
}


void enregistrer_donnees_fichier(DONNEES_FICHIER **tabDonnees)
{
	FILE *fichierScores = NULL;
	int i,j;
	
	fichierScores=fopen("../scores.dat","wb");
	
	
	for(i=0;i<NB_PARTIES_MAX;i++)
	{
		for(j=0;j<NB_JOUEURS_MAX;j++)
		{
			fwrite(tabDonnees[i][j].pseudo,sizeof(char)*(LONGUEUR_PSEUDO+1),1,fichierScores);
			fwrite(&tabDonnees[i][j].scoreFinal,sizeof(int),1,fichierScores);
		}
	}
	
	fclose(fichierScores);
}


void lire_donnees_fichier(DONNEES_FICHIER **tabDonnees)
{
	FILE *fichierScores = NULL;
	int i,j;
	
	fichierScores=fopen("../scores.dat","rb");
	
	if(fichierScores!=NULL)
	{
		for(i=0;i<NB_PARTIES_MAX;i++)
		{
			for(j=0;j<NB_JOUEURS_MAX;j++)
			{
				fread(tabDonnees[i][j].pseudo,sizeof(char)*(LONGUEUR_PSEUDO+1),1,fichierScores);
				fread(&tabDonnees[i][j].scoreFinal,sizeof(int),1,fichierScores);
			}
		}
	
		fclose(fichierScores);
	}
}
