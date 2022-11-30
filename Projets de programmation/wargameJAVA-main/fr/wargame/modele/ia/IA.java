package fr.wargame.modele.ia;

import java.util.ArrayList;

import fr.wargame.modele.actions.*;
import fr.wargame.modele.plateaucase.*;
import fr.wargame.modele.unites.*;

/**
 * Regroupe l'ensembles des méthodes utilisées pour l'IA.
 */
public class IA {

    /**
     * Cette fonction regroupes les différentes actions que peut faire une unité contrôler par l'IA
     * @param unite L'unité qui joue
     * @param plateauJeu Récupère le plateau de jeu pour utiliser les fonctions de déplacement
     * @param unitesEnnemies La liste contenant les unités adverses (pour l'attaque)
     * @param indiceArmee L'indice du joueur auquel appartient l'unité (pour les déplacements)
     * @return Un entier qui renvoie l'indice de l'unité adverse si elle est mise K.O., -1 sinon
     */
    static public int actionIA (Unite unite, Case[][] plateauJeu, ArrayList<Unite> unitesEnnemies, int indiceArmee) {
        int degats = 0, cible = 0, recherche = 0, caseCible = 0, vie = 0;
        ArrayList<ArrayList<Integer>> casesDispos = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> indicesEnnemisAttaquables = new ArrayList<Integer>();
        ArrayList<Integer> coordsActuelles = new ArrayList<Integer>();

        coordsActuelles.add(0, unite.getCoords()[0]); coordsActuelles.add(1, unite.getCoords()[1]);
        casesDispos.add(coordsActuelles);
        Deplacement.listerCasesPossibles(plateauJeu, casesDispos, unite, 0, unite.getCoords()[0], unite.getCoords()[1]);
        boucle : for(int i=0; i<casesDispos.size(); i++) {
            if (unite.estUltimeDispo())
                Recherchecibles.listerEnnemisAttaquables(plateauJeu, unitesEnnemies, indicesEnnemisAttaquables, unite.getStatsActuelles().getPOR()[1], 0, casesDispos.get(i).get(0), casesDispos.get(i).get(1));
            else 
                Recherchecibles.listerEnnemisAttaquables(plateauJeu, unitesEnnemies, indicesEnnemisAttaquables, unite.getStatsActuelles().getPOR()[0], 0, casesDispos.get(i).get(0), casesDispos.get(i).get(1));
            if(unite.estUltimeDispo()) {
                for(int j : indicesEnnemisAttaquables) {
                    if (unite.attaquerUltime(unitesEnnemies.get(j), true) == 0) {
                        degats = -1;
                        cible = j;
                        caseCible = i;
                        break boucle;
                    }
                    else if (unitesEnnemies.get(j).getStatsActuelles().getPV() - unite.attaquerUltime(unitesEnnemies.get(j), true) > degats) {
                        degats = unitesEnnemies.get(j).getStatsActuelles().getPV() - unite.attaquerUltime(unitesEnnemies.get(j), true);
                        cible = j;
                        caseCible = i;
                    }
                }
            }
            else {
                for(int j : indicesEnnemisAttaquables) {
                    if (unite.attaquer(unitesEnnemies.get(j), true) == 0) {
                        degats = -1;
                        cible = j;
                        caseCible = i;
                        break boucle;
                    }
                    else if (unitesEnnemies.get(j).getStatsActuelles().getPV() - unite.attaquer(unitesEnnemies.get(j), true) > degats) {
                        degats = unitesEnnemies.get(j).getStatsActuelles().getPV() - unite.attaquer(unitesEnnemies.get(j), true);
                        cible = j;
                        caseCible = i;
                    }
                }
            }
        }
        if(degats != 0) {
            Deplacement.deplacerUnite(plateauJeu, casesDispos, unite, casesDispos.get(caseCible).get(0), casesDispos.get(caseCible).get(1));
            if(unite.estUltimeDispo())
                vie = unite.attaquerUltime(unitesEnnemies.get(cible), false);
            else
                vie = unite.attaquer(unitesEnnemies.get(cible), false);
            
            if (vie == 0)
                return cible;
            else 
                return -1;
        }
        
        else {
            if(unite.getStatsActuelles().getPV() < 0.9 * unite.getStats().getPV()) {
                recherche = rechercherVillage(plateauJeu,casesDispos,unite);
                if (recherche == -1)
                    unite.soigner();
                else
                    Deplacement.deplacerUnite(plateauJeu, casesDispos, unite, casesDispos.get(recherche).get(0), casesDispos.get(recherche).get(1));
            }
            else
                deplacerIA(plateauJeu, casesDispos, unite, indiceArmee);
        }
        return -1;
    }

    /**
     * Méthode de recherche d'un village accessible par l'unité
     * @param plateauJeu Récupère le plateau pour tester les types des cases disponibles
     * @param casesDispos Récupère la liste des cases disponibles
     * @param unite Récupère l'unité actuelle pour avoir ses coordonnées
     * @return L'indice d'une case de type "village", -1 si aucune n'est accessible
     */
    static int rechercherVillage (Case[][] plateauJeu, ArrayList<ArrayList<Integer>> casesDispos, Unite unite) {
        if(plateauJeu[unite.getCoords()[0]][unite.getCoords()[1]].getTypeCase() == Case.VILLAGE)
            return -1;
        else {
            for(int i=0; i<casesDispos.size(); i++) {
                if(plateauJeu[casesDispos.get(i).get(0)][casesDispos.get(i).get(1)].getTypeCase() == Case.VILLAGE) 
                    return i;
            }
        }
        return -1;
    }

    /**
     * Méthode qui permet de déplacer une unité de l'IA en fonction du joueur à laquelle elle appartient
     * @param plateauJeu Récupère les cases pour le déplacement
     * @param casesDispos Récupère la liste des cases accessibles par l'unité
     * @param unite Récupère l'unité actuelle
     * @param indiceArmee Récupère l'indice du joueur pour définir la zone vers laquelle l'unité doit se déplacer
     */
    static void deplacerIA (Case[][] plateauJeu, ArrayList<ArrayList<Integer>> casesDispos, Unite unite, int indiceArmee) {
        int objectif, effectif = 0;

        if ((indiceArmee == 1 && Math.abs(unite.getCoords()[0] - plateauJeu[0].length) >= 4) 
            || (indiceArmee == 0 && Math.abs(unite.getCoords()[0]) < 4))
            objectif = plateauJeu[0].length;
        else    
            objectif = 0;

        for(int i=1; i<casesDispos.size(); i++) {
            if (plateauJeu[casesDispos.get(i).get(0)][casesDispos.get(i).get(1)].getTypeCase() == Case.MONTAGNE
                && Math.abs(casesDispos.get(i).get(0) - objectif) < Math.abs(unite.getCoords()[0] - objectif)) {

                Deplacement.deplacerUnite(plateauJeu, casesDispos, unite, casesDispos.get(i).get(0), casesDispos.get(i).get(1));
                return;
            }
            else if (Math.abs(casesDispos.get(i).get(0) - objectif) < Math.abs(casesDispos.get(effectif).get(0) - objectif))
                effectif = i;
        }
        Deplacement.deplacerUnite(plateauJeu, casesDispos, unite, casesDispos.get(effectif).get(0), casesDispos.get(effectif).get(1));
    }
}
