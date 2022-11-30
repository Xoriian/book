package fr.wargame.controleur.gestionclic;

/**
 * Regroupe les méthodes utilisées pour récupérer les informations données par un clic de l'utilisateur
 */
public class Clic {
    int[] coordsM = new int[2];
    boolean aClique, veutUlti, veutPasser;

    public Clic () {
        this.coordsM[0] = -2; this.coordsM[1] = -2;
        this.aClique = false;
        this.veutUlti = false;
        this.veutPasser = false;
    }

    /**
     * Actualise les coordonnées du clic, et signale au main que l'utilisateur a fait un clic
     * @param coordsM Les coordonnées du point de vue du modèle
     */
    public void setCoordsM (int[] coordsM) {
        this.coordsM[0] = coordsM[0]; 
        this.coordsM[1] = coordsM[1];
        this.aClique = true;
    }

    /**
     * @return Les coordonnées du modèle enregistrées
     */
    public int[] getCoordsM() {
        return coordsM;
    }

    /**
     * Enregistre le clic sur le bouton d'attaque Ultime
     * @param veutUlti booléen représantant ce clic
     */
    public void setVeutUlti(boolean veutUlti) {
        this.veutUlti = veutUlti;
    }

    /**
     * Enregistre le clic sur le bouton de fin de tour
     * @param veutPasser booléen représentant ce clic
     */
    public void setVeutPasser(boolean veutPasser) {
        this.veutPasser = veutPasser;
        if(veutPasser == true)
            this.aClique = true;
        else
            this.aClique = false;
    }

    /**
     * @return la valeur du booléen représentant le bouton d'attaque ultime
     */
    public boolean getVeutUlti() {
        return this.veutUlti;
    }

    /**
     * @return la valeur du booléen représentant le bouton de fin de tour
     */
    public boolean getVeutPasser() {
        return this.veutPasser;
    }

    /**
     * @return la valeur du booléen représentant le fait que l'utilisateur ait fait un clic ou non
     */
    public boolean getAClique() {
        return this.aClique;
    }

    /**
     * Méthode permettant de dire que le clic précédent a bien été récupéré et que le main est en attente d'un nouveau clic
     */
    public void setFinClic() {
        this.aClique = false;
    }
}
