package fr.wargame.controleur.gestionclic;

/**
 * Classe regroupant les méthodes permettant de convertir les coordonnées de la vue en coordonnées du modèle et inversement
 */
public class Conversion {

    /**
     * Méthode de conversion du modèle vers la vue
     * @param coordsM Les coordonnées du modèle à convertir
     * @return Les coordonnées de la vue
     */
    public static int[] modeleVersVue(int[] coordsM) {
        int[] coordsV = new int[2];
        int decal = 0;

        if(coordsM[0]%2 == 1)
            decal = 64;
        coordsV[0] = coordsM[1]*128 + decal;
        coordsV[1] = coordsM[0]*96;

        return coordsV;
    }

    /**
     * Méthode de conversion de la vue vers le modèle.
     * Le code utilise l'algorithme présenté sur ce site : https://www.gamedev.net/articles/programming/general-and-gameplay-programming/coordinates-in-hexagon-based-tile-maps-r1800/
     * Dimensions des hexagones : r = 64 (donc 2r = 128) ; h = 32 (donc h+r = 96) ; m = h/r = 0.5
     * @param coordsV Coordonnées de la vue à convertir
     * @return Coordonnées du modèle
     */
    public static int[] vueVersModele(int[] coordsV) {
        int[] coordsM = new int[2]; // Les coordonnées dans le tableau 2D (équivalent de la map divisée en carrés)
        int[] coordsSection = new int[2]; // Les coordonnées du point dans le carré (une fois la map divisée)

        coordsM[0] = coordsV[0] / 96;
        coordsM[1] = coordsV[1] / 128; 
        coordsSection[0] = coordsV[0] % 96; 
        coordsSection[1] = coordsV[1] % 128; 

        if (coordsM[0] % 2 == 0) {
             // Défaut : Zone centrale
            if (coordsSection[0] < (32 - coordsSection[1] / 2)) { // Diagonale gauche
                coordsM[0] -= 1; coordsM[1] -= 1;
            }
            else if (coordsSection[0] < (- 32 + coordsSection[1] / 2)) // Diagonale droite
                coordsM[0] -= 1;
        }
        else {
            // Défaut : Zone centrale droite
            if (coordsSection[1] > 64) {
                if (coordsSection[0] < (64 - coordsSection[1] / 2)) // Zone en haut
                    coordsM[0] -= 1;
            }
            else {
                if (coordsSection[0] < (coordsSection[1] / 2)) // Zone en haut
                    coordsM[0] -= 1;
                else // Zone centrale gauche
                    coordsM[1] -= 1; 
            }
        }

        return coordsM;
    }
}
