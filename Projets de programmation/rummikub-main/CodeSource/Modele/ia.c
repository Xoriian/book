/////////////////////////////////////// Projet Rummikub - Groupe 9 - Intelligence Artificielle ////////////////////////////////////////

#include "ia.h"

void jouer_ia(int tmax, LISTE* joueur, LISTE_DE_LISTES* plateau, int* tab, MODE_DE_TRI modeTri)
{	
	int i;
	
	for(i = tmax-1; i >= 0 ; i--)
	{
		while(tab[i] != 0 && i>= 0)
		{
			if(i == tmax-1 || tab[i+1] == 0) // Nouvelle série
			{				
				if (tab[i] == -1)
					tab[i] = joueur->taille;
					
				jouer(joueur, plateau, modeTri, tab[i], plateau->taille+1);
			}
			
			else
			{
				// Présence d'un Joker en fin de main
				if (tab[i] == -1)
						tab[i] = joueur->taille;
						
				jouer(joueur, plateau, modeTri, tab[i], plateau->taille);
			}
			
			i--;
		}
	}
	return;
}

BOOLEEN trier_tableau(int* tab, int tmax, BOOLEEN joker)
{
	int i,j;
	
	for(i=0; i<tmax-2 ; i++)
	{
		j=0;
		while(tab[i] > 0)
		{
			j++;
			i++;
		}
		
		if(j == 2)
		{
			if(joker)
			{
				tab[i] = -1;
				rotation_tab(tab, tmax,i+1);
				joker = FAUX;
			}
			else
			{
				tab[i-1] = 0;
				tab[i-2] = 0;
			}
		}
	}
	return joker;
}

BOOLEEN ia(LISTE* joueur, LISTE_DE_LISTES* plateau, LISTE* pioche, BOOLEEN aFait30)
{
	int premSerie = 1, i, j=0, tmax = (joueur->taille + joueur->taille / 3);
	BOOLEEN estNouvSerie = VRAI, joker = FAUX;
	MODE_DE_TRI modeTri = COULEURS;
	int* tab = malloc(tmax * sizeof(int));
	
	ELEMENT* tmp;
	LISTE* parcours = plateau->prem_liste;
	LISTE_DE_LISTES copieplateau;
	LISTE copiejoueur;

	copieplateau.prem_liste = NULL;
	copieplateau.taille = 0;
	copiejoueur = creer_liste_vide();
	copier_liste_de_listes(plateau->prem_liste, &copieplateau, modeTri);
	copier_liste(joueur->deb_liste, &copiejoueur, modeTri);
	
	for(i = 0; i < tmax ; i++)
	{
		tab[i] = 0;
	}
	
	while(parcours != NULL)
	{
		parcours = parcours->suivante;
		premSerie++;
	}

	////////////////// Jeu par couleur (suite) :
	
	modeTri = COULEURS;
	trier_liste(joueur, modeTri);
	
	tmp = joueur->deb_liste;
		
	for (i=1; i < joueur->taille ; i++)
	{	
		if((tmp->tuile.numero+1 == tmp->suivant->tuile.numero) && (tmp->tuile.couleur == tmp->suivant->tuile.couleur)) //tuile i et i+1 de même couleur mais de numéro différent
		{
			if (estNouvSerie)
			{
				tab[j] = i;
				tab[j+1] = i+1;
				j += 2; estNouvSerie = FAUX;
			}
			else
			{
				tab[j] = i+1;
				j++;
			}
		}
		else if (!(sont_egaux(tmp->tuile,tmp->suivant->tuile)) && !estNouvSerie)
		{
			estNouvSerie = VRAI;
			j++;
		}
			
		tmp = tmp->suivant;
	}
	
	////////////////// Tri et jeu :
	
	joker = trier_tableau(tab,tmax,joker);
	jouer_ia(tmax,joueur,plateau,tab,modeTri);
	
	////////////////// Si un Joker est dans la main de l'IA :
	
	tmp=joueur->deb_liste;
	while(tmp->suivant != NULL)
	{
		tmp = tmp->suivant;
	}
	
	if(tmp->tuile.numero == 30)
		joker = VRAI;
		
	////////////////// Par nombre (brelan / carré) :
	
	for(i = 0; i < tmax ; i++)
	{
		tab[i] = 0;
	}
	
	modeTri = NOMBRES;
	trier_liste(joueur, modeTri);
	
	tmp=joueur->deb_liste;
	estNouvSerie = VRAI;
	
	j=0;
	for (i=1; i < joueur->taille ; i++)
	{	
		if((tmp->tuile.numero == tmp->suivant->tuile.numero) && (tmp->tuile.couleur != tmp->suivant->tuile.couleur)) //tuile i et i+1 de même numéro mais de couleur différente
		{
			if (estNouvSerie)
			{
				tab[j] = i;
				tab[j+1] = i+1;
				j += 2; estNouvSerie = FAUX;
			}
			else
			{
				tab[j] = i+1;
				j++;
			}
		}
		else if (!(sont_egaux(tmp->tuile,tmp->suivant->tuile)) && !estNouvSerie)
		{
			estNouvSerie = VRAI;
			j++;
		}
			
		tmp = tmp->suivant;
	}

	////////////////// Tri et jeu :
	
	joker = trier_tableau(tab,tmax,joker);
	jouer_ia(tmax,joueur,plateau,tab,modeTri);
	
	///////////////// Validation du coup :
	
	if(!est_plateau_valide(plateau,aFait30,premSerie))
	{
		vider_liste_de_listes(plateau->prem_liste);
		*plateau = copieplateau;
		vider_liste(joueur->deb_liste);
		*joueur = copiejoueur;
	}
	else
	{
		vider_liste_de_listes(copieplateau.prem_liste);
		vider_liste(copiejoueur.deb_liste);
		aFait30 = VRAI;
	}

	free(tab);
	return aFait30;
}


void rotation_tab(int* tab, int tmax, int tmin)
{
	int i;

	for(i=tmax-1; i>tmin; i--)
	{
		tab[i] = tab[i-1];
	}
	
	tab[tmin] = 0;
	
	return;
}
