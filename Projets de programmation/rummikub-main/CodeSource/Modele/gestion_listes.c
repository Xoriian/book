/////////////////////////////////////// Projet Rummikub - Groupe 9 - Gestion des listes ////////////////////////////////////////

#include "gestion_listes.h"

void trier_liste(LISTE* liste, MODE_DE_TRI modeTri) 
{
	LISTE nouveau = creer_liste_vide();
	ELEMENT* tmp = liste->deb_liste;

	nouveau.suivante = liste->suivante;
		
	while (tmp != NULL)
	{
		ajouter_liste_triee(&nouveau,tmp->tuile, modeTri);
		tmp=tmp->suivant;

	}
	vider_liste(liste->deb_liste);
	*liste = nouveau;
}

void ajouter_liste_triee_parcours(ELEMENT **elem, TUILE tuile, MODE_DE_TRI modeTri)
{
	ELEMENT *nouveau;
	if(*elem == NULL)
	{
		*elem = malloc(sizeof(ELEMENT*));
		(*elem)->tuile = tuile;
		(*elem)->suivant = NULL;
	}
	else if(
		( (modeTri == NOMBRES) && (tuile.numero <= (*elem)->tuile.numero))
		||
		( (modeTri == COULEURS) && ( (tuile.couleur * 100 + tuile.numero) <= ((*elem)->tuile.couleur * 100 + (*elem)->tuile.numero) )))
	{
		nouveau = malloc(sizeof(ELEMENT*));
		nouveau->tuile = (*elem)->tuile;
		nouveau->suivant = (*elem)->suivant;
		(*elem)->tuile = tuile;
		(*elem)->suivant = nouveau;
	}
	else
	{
		ajouter_liste_triee_parcours(&((*elem)->suivant), tuile, modeTri);
	}
}

void ajouter_liste_triee(LISTE *liste, TUILE tuile, MODE_DE_TRI modeTri)
{
	ajouter_liste_triee_parcours(&(liste->deb_liste), tuile, modeTri);
	liste->taille = liste->taille + 1;
}

void deplacer_de_liste1_vers_liste2(ELEMENT **elem_liste1, LISTE *liste2, int indice, MODE_DE_TRI modeTri)
{
	ELEMENT *tmp = NULL;
	if(indice > 1)
	{
		deplacer_de_liste1_vers_liste2(&((*elem_liste1)->suivant), liste2, indice-1, modeTri);
	}
	else
	{
		ajouter_liste_triee(liste2, (*elem_liste1)->tuile, modeTri);
		tmp = *elem_liste1;
		*elem_liste1 = (*elem_liste1)->suivant;
		free(tmp);
	}
}

void copier_liste(ELEMENT *origine, LISTE *copie, MODE_DE_TRI modeTri)
{
	if(origine != NULL)
	{
		copier_liste(origine->suivant, copie, modeTri);
		ajouter_liste_triee(copie, origine->tuile, modeTri);
	}
}

void copier_liste_de_listes(LISTE *origine, LISTE_DE_LISTES *copie, MODE_DE_TRI modeTri)
{
	LISTE *nouvelle_liste = NULL;
	if(origine != NULL)
	{
		copier_liste_de_listes(origine->suivante, copie, modeTri);
		nouvelle_liste = malloc(sizeof(LISTE*));
		nouvelle_liste->deb_liste = NULL;
		nouvelle_liste->taille = 0;
		nouvelle_liste->suivante = copie->prem_liste;
		copie->prem_liste = nouvelle_liste;
		copie->taille = copie->taille + 1;
		copier_liste(origine->deb_liste, copie->prem_liste, modeTri);
	}
}

void vider_liste(ELEMENT *elem)
{
	if(elem != NULL)
	{
		vider_liste(elem->suivant);
		free(elem);
	}
}

void vider_liste_de_listes(LISTE *liste)
{
	if(liste != NULL)
	{
		vider_liste_de_listes(liste->suivante);
		vider_liste(liste->deb_liste);
		free(liste);
	}
}

void initialiser_pioche(LISTE *pioche)
{
	int i, j;
	TUILE tuile;
	tuile.numero = 30;
	tuile.couleur = 3;
	tuile.estJoker = VRAI;
	
	*pioche = ajouter_element_liste(*pioche, tuile);
	*pioche = ajouter_element_liste(*pioche, tuile);
	
	tuile.estJoker = FAUX;
	
	for(i = 3; i >= 0; i--)
	{
		for(j = 13; j >= 1; j--)
		{
			tuile.numero = j;
			tuile.couleur = i;
			*pioche = ajouter_element_liste(*pioche, tuile);
			*pioche = ajouter_element_liste(*pioche, tuile);
		}
	}
}

void piocher(LISTE *pioche, LISTE *joueur)
{
	int indice;

	indice = (random() % pioche->taille) + 1;
	deplacer_de_liste1_vers_liste2(&(pioche->deb_liste), joueur, indice, COULEURS);
	pioche->taille = pioche->taille - 1;
}

void jouer(LISTE *joueur, LISTE_DE_LISTES *plateau, MODE_DE_TRI modeTri, int indice_tuile_jouee, int numero_serie_agrandie)
{
	int i;
	LISTE *nouvelle_liste = NULL;
	LISTE **liste_tmp = NULL;
	
	if(numero_serie_agrandie > plateau->taille)
	{
		nouvelle_liste = malloc(sizeof(LISTE*));
		nouvelle_liste->deb_liste = NULL;
		nouvelle_liste->taille = 0;
		nouvelle_liste->suivante = NULL;
		liste_tmp = &(plateau->prem_liste);
		while(*liste_tmp != NULL)
		{
				liste_tmp = &((*liste_tmp)->suivante);
		}
		*liste_tmp = nouvelle_liste;
		deplacer_de_liste1_vers_liste2(&(joueur->deb_liste), *liste_tmp, indice_tuile_jouee, modeTri);
		plateau->taille = plateau->taille + 1;
	}
	else
	{
		liste_tmp = &(plateau->prem_liste);
		for(i = 1; i < numero_serie_agrandie; i++)
		{
			liste_tmp = &((*liste_tmp)->suivante);
		}
		deplacer_de_liste1_vers_liste2(&(joueur->deb_liste), *liste_tmp, indice_tuile_jouee, modeTri);
	}
	joueur->taille = joueur->taille - 1;
}

BOOLEEN est_serie_valide(LISTE* serie)
{
	ELEMENT* tmp1;
	ELEMENT* tmp2; 
	int couleurs[4] = {0};

	if(serie->taille == 0)
		return VRAI;	
	else if(serie->taille < 3)
		return FAUX;
	
	gerer_joker(serie);
	
	tmp1 = serie->deb_liste;
	tmp2 = serie->deb_liste->suivant;

	if ((tmp1->tuile.numero == tmp2->tuile.numero && tmp1->tuile.couleur != tmp2->tuile.couleur)) // Si la série est un brelan/carré
	{	
		if(serie->taille > 4)
			return FAUX;
			
		while(tmp2 != NULL)
		{
			if (!((tmp1->tuile.numero == tmp2->tuile.numero && tmp1->tuile.couleur != tmp2->tuile.couleur && couleurs[tmp2->tuile.couleur] == 0)))
				return FAUX;
				
			couleurs[tmp1->tuile.couleur] = 1; // On enregistre les couleurs déjà vues
			tmp1 = tmp2;
			tmp2 = tmp2->suivant;
			
		}
		return VRAI;
	}
	else if ((tmp1->tuile.numero+1 == tmp2->tuile.numero && tmp1->tuile.couleur == tmp2->tuile.couleur)) // Si la série est une suite
	{
		if(serie->taille > 13)
			return FAUX;
			
		while(tmp2 != NULL)
		{
			if (!((tmp1->tuile.numero+1 == tmp2->tuile.numero && tmp1->tuile.couleur == tmp2->tuile.couleur)))
				return FAUX;
			tmp1 = tmp2;
			tmp2 = tmp2->suivant;
			
		}
		return VRAI;
	}
	
	return FAUX;
}

BOOLEEN est_plateau_valide(LISTE_DE_LISTES* plateau, BOOLEEN aFait30, int serie1)
{
	LISTE* serie = plateau->prem_liste;
	ELEMENT* element;
	int i, cpt = 0;
	
	while(serie != NULL)
	{
		if(est_serie_valide(serie))
		{
			serie = serie->suivante;
		}
		else
			return FAUX;
	}
	
	if (!aFait30)
	{
		serie = plateau->prem_liste;
		
		for(i=1;i<serie1;i++)
		{
			serie = serie->suivante;
		}
		
		while (serie != NULL)
		{
			element = serie->deb_liste;
			
			while (element != NULL)
			{
				cpt+=element->tuile.numero;
				element = element->suivant;
			}
			
			serie=serie->suivante;
		}
	}
	
	if ( (aFait30) || (!aFait30 && cpt >= 30) )
		return VRAI;
		
	return FAUX;
}

void gerer_joker(LISTE* serie)
{
	ELEMENT* tmp = serie->deb_liste;
	ELEMENT* joker[2] = {NULL,NULL};
	
	int tabTuile[2][serie->taille]; // tableau pour stocker le numéro et la couleur des tuiles
	
	int i, j, k, cptJoker = -1;
	
	for(i = 0; i < 4; i++) // On remplit les couleurs des tuiles à 0
	{
		tabTuile[1][i] = 0;
	}
	
	for(i=0 ; i<serie->taille ; i++)
	{
		if(i > 3) // Pas de couleur représentée par un nombre > 3
		{
			tabTuile[1][i] = 0;
		}

		if(tmp == NULL)
		{
			tabTuile[0][i] = 0;
		}
		else if(!(tmp->tuile.estJoker))
		{
			tabTuile[0][i] = tmp->tuile.numero;
			tabTuile[1][tmp->tuile.couleur] = 1;
		}	
		else 
		{
			cptJoker++;
			joker[cptJoker] = tmp;
			i--;
		}
		
		if(tmp != NULL)
			tmp = tmp->suivant;
	}
		
	if(cptJoker != -1) // S'il y a un joker
	{	
		if (serie->taille == 3 && cptJoker == 1)
		{	
			joker[0]->tuile.numero = tabTuile[0][0];
			joker[1]->tuile.numero = tabTuile[0][0];
			joker[0]->tuile.couleur = (tabTuile[1][0]+1)%4;
			joker[1]->tuile.couleur = (tabTuile[1][0]+2)%4;	
		}
		else
		{
			if(tabTuile[0][0] == tabTuile[0][1])
			{
				for(i = cptJoker; i > -1 ; i--)
				{
					joker[i]->tuile.numero = tabTuile[0][0];
					j = 0;
					
					while (tabTuile[1][j] == 1)
					{
						j++;
					}
					
					joker[i]->tuile.couleur = j;
					tabTuile[1][joker[i]->tuile.couleur] = 1;
				}
				
			trier_liste(serie,NOMBRES);
			}	
			else
			{
				tmp = serie->deb_liste;
				k=0;
				
				for(i = cptJoker; i > -1 ; i--)
				{
					j=0;
					
					while (tabTuile[1][j] == 0)
					{
						j++;
					}
					joker[i]->tuile.couleur = j;
					
					j=0;
					
					while(tabTuile[0][k] +1 == tabTuile[0][k+1])
					{
						k++;
						tmp = tmp->suivant;
					}
						
					if(tabTuile[0][k] != 13)
						joker[i]->tuile.numero = tabTuile[0][k]+1;
					else
						joker[i]->tuile.numero = tabTuile[0][0]-1;
						
					k++;
				}
				trier_liste(serie, COULEURS);
			}
		}
	}
}
