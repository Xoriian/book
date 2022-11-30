package fr.wargame.vue.fenetrejeu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import fr.wargame.controleur.gestionclic.*;
import fr.wargame.modele.plateaucase.Case;
import fr.wargame.modele.unites.*;

/**
 * Permet d'afficher le plateau de jeu et tous les éléments qui s'afficheront dessus
 */
public class Plateau extends JPanel {
    protected JLayeredPane lp = new JLayeredPane();
    protected JScrollPane sc = new JScrollPane(this,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    int[] clicCoords = new int[2];
    public Clic clic;

    public static Integer DMG_LAYER = Integer.valueOf(500);
    /**
     * DEFAULT_LAYER = Hexagones - Integer.valueOf(0);
     * PALETTE_LAYER = Hexagones sélectionnés - Integer.valueOf(100);
     * MODAL_LAYER = Unites - Integer.valueOf(200);
     * POPUP_LAYER = Vie/Armure/Coche - Integer.valueOf(300);
     * DRAG_LAYER = Brouillard - Integer.valueOf(400);
     * DMG_LAYER = Dégâts - Integer.valueOf(500);
     */

    /**
     * @param clicFen clic joueur
     */
    Plateau(Clic clicFen) {
        lp.setBounds(0,0,2624,1952);
        this.add(lp);
        this.setBounds(0,0,2624,1952);
        this.setPreferredSize(new Dimension(2624,1952));
        this.setLayout(null);
        this.setBackground(new Color(66,155,66));
        this.sc.setBounds(250,0,1030,720);
        this.sc.getVerticalScrollBar().setUnitIncrement(16);
        this.sc.getHorizontalScrollBar().setUnitIncrement(16);
        this.clic = clicFen;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                clicCoords[0] = event.getY(); clicCoords[1] = event.getX();
                clic.setCoordsM(Conversion.vueVersModele(clicCoords));
            }
        });
    }

    /**
     * Affiche toutes les cases du plateau
     * @param plateauM plateau modèle
     */
    public void afficherPlateau(Case[][] plateauM) {
        for(int i=0; i<20; i++) {
            for(int j=0; j<20; j++) 
                afficherHexa(i*128,j*96,plateauM[j][i].getTypeCase());
        }
    }

    /**
     * Recherche le chemin du sprite selon le type de la case
     * Affiche la case
     * @param x coord x graphique
     * @param y coord y graphique
     * @param typeCase type de la case à afficher
     */
    public void afficherHexa(int x, int y, String typeCase) {
        String chemin = "/sprites/hexa/hexa_" + typeCase + ".png";
        JLabel lab = new JLabel(new ImageIcon(getClass().getResource(chemin)));
        int decal = 0;

        if((y/96)%2 == 1)
            decal = 64;
        lab.setBounds(x+decal,y,128,128);
        lp.add(lab,JLayeredPane.DEFAULT_LAYER);
    }

    /**
     * Recherche le sprite du contour selon le type (Sélectionnée, Déplacement possible, Attaquable)
     * Affiche le contour
     * @param coordsV Coordonnées graphiques (tableau de int)
     * @param type Type de contour (Sélectionnée, Déplacement possible, Attaquable)
     */
    public void afficherHexaSelect(int[] coordsV, String type) {
        JLabel lab = new JLabel(new ImageIcon(getClass().getResource("/sprites/hexa/hexa_" + type + ".png")));
        lab.setBounds(coordsV[0],coordsV[1],128,128);
        lp.add(lab,JLayeredPane.PALETTE_LAYER);
        this.revalidate();
        this.repaint();
    }
    
    /**
     * Recherche le chemin du sprite de l'icône de l'unité selon le type de l'unité
     * Affiche l'icône de l'unité
     * Affiche la vie de l'unité
     * Affiche la coche si l'unité est jouable
     * @param unite Unité à afficher
     * @param coordsV Coordonnées graphiques (tableau de int)
     * @param joueur Numéro du joueur auquel appartient l'unité
     */
    public void afficherUnite(Unite unite, int[] coordsV, int joueur) {
        String chemin = "/sprites/unites/" + unite.donnerTypeUnite() + "_icon_J" + String.valueOf(joueur) + ".png";
        JLabel lab = new JLabel(new ImageIcon(getClass().getResource(chemin)));

        lab.setBounds(coordsV[0],coordsV[1],128,128);
        lp.add(lab,JLayeredPane.MODAL_LAYER);
        afficherVieUnite(unite,coordsV);
        if(unite.getArmure() > 0)
            afficherArmureUnite(unite,coordsV);
        if(!unite.estDejaJouee())
            afficherCoche(coordsV);
    }

    /**
     * Récupère la vie de l'unité et la transforme en String
     * Sépare la vie en 2 chiffres dans un tableau
     * Rajoute un 0 au début si un seul chiffre pour la vie
     * Pour les 2 chiffres, recherche le sprite associé au chiffre
     * Affiche le chiffre avec un décalage pour le 2e
     * Affiche le sprite coeur
     * @param unite unité dont on doit afficher la vie
     * @param coordsV coords graphiques de l'unité
     */
    public void afficherVieUnite(Unite unite, int[] coordsV) {
        JLabel coeur = new JLabel(new ImageIcon(getClass().getResource("/sprites/stats/pv.png")));
        JLabel[] num = new JLabel[2];
        String vie = String.valueOf(unite.getStatsActuelles().getPV());
        String chemin;
        String[] tabNum;

        if(vie.length() == 1)
            vie = "0" + vie;
        tabNum = vie.split("");
        for(int i=1; i>=0; i--) {
            chemin = "/sprites/chiffres/";
            chemin += tabNum[i] + ".png";
            num[i] = new JLabel(new ImageIcon(getClass().getResource(chemin)));
            num[i].setBounds(coordsV[0]+98+i*16,coordsV[1]+76,12,20);
            lp.add(num[i],JLayeredPane.POPUP_LAYER);
        }
        coeur.setBounds(coordsV[0]+96,coordsV[1]+96,32,32);
        lp.add(coeur,JLayeredPane.POPUP_LAYER);
        this.revalidate();
        this.repaint();
    }

    /**
     * Récupère la valeur de l'armure de l'unité et la transforme en String
     * Sépare cette valeur en 2 chiffres dans un tableau
     * Rajoute un 0 au début si un seul chiffre
     * Pour les 2 chiffres, recherche le sprite associé au chiffre
     * Affiche le chiffre avec un décalage pour le 2e
     * Affiche le sprite armure
     * @param unite unité dont on doit afficher l'armure
     * @param coordsV coords graphiques de l'unité
     */
    public void afficherArmureUnite(Unite unite, int[] coordsV) {
        JLabel lab = new JLabel(new ImageIcon(getClass().getResource("/sprites/stats/arm.png")));
        JLabel[] num = new JLabel[2];
        String arm = String.valueOf(unite.getArmure());
        String chemin;
        String[] tabNum;

        if(arm.length() == 1)
            arm = "0" + arm;
        tabNum = arm.split("");
        for(int i=1; i>=0; i--) {
            chemin = "/sprites/chiffres/";
            chemin += tabNum[i] + ".png";
            num[i] = new JLabel(new ImageIcon(getClass().getResource(chemin)));
            num[i].setBounds(coordsV[0]+2+i*16,coordsV[1]+76,12,20);
            lp.add(num[i],JLayeredPane.POPUP_LAYER);
        }
        lab.setBounds(coordsV[0],coordsV[1]+96,32,32);
        lp.add(lab,JLayeredPane.POPUP_LAYER);
        this.revalidate();
        this.repaint();
    }

    /**
     * Affiche une coche
     * @param coordsV coords graphiques de l'unité
     */
    public void afficherCoche(int[] coordsV) {
        JLabel coche = new JLabel(new ImageIcon(getClass().getResource("/sprites/coche.png")));

        coche.setBounds(coordsV[0],coordsV[1],32,32);
        lp.add(coche,JLayeredPane.POPUP_LAYER);
        this.revalidate();
        this.repaint();
    }

    /**
     * Récupère la valeur des dégâts et la transforme en String
     * Sépare cette valeur en 2 chiffres dans un tableau
     * Rajoute un 0 au début si un seul chiffre
     * Pour les 2 chiffres, recherche le sprite associé au chiffre
     * Affiche le chiffre avec un décalage pour le 2e
     * @param coordsV coords graphiques de l'unité attaquée
     * @param degats dégâts qui seront infligés à l'unité
     */
    public void afficherDegatsUnite(int[] coordsV, int degats) {
        JLabel[] num = new JLabel[2];
        String deg = String.valueOf(degats);
        String chemin;
        String[] tabNum;

        if(deg.length() == 1)
            deg = "0" + deg;
        tabNum = deg.split("");
        for(int i=1; i>=0; i--) {
            chemin = "/sprites/chiffres/";
            chemin += tabNum[i] + "D.png";
            num[i] = new JLabel(new ImageIcon(getClass().getResource(chemin)));
            num[i].setBounds(coordsV[0]+98+i*16,coordsV[1],12,20);
            lp.add(num[i],Plateau.DMG_LAYER);
        }
        this.revalidate();
        this.repaint();
    }

    /**
     * Affiche le brouillard sur une case
     * @param coordsV coords graphiques de la case
     */
    public void afficherBrouillard(int[] coordsV) {
        JLabel lab = new JLabel(new ImageIcon(getClass().getResource("/sprites/hexa/brouillard.png")));

        lab.setBounds(coordsV[0],coordsV[1],128,128);
        lp.add(lab,JLayeredPane.DRAG_LAYER);
        this.revalidate();
        this.repaint();
    }

    /**
     * Récupère tous les éléments sur le PALETTE_LAYER (cf. liste des couches en haut du fichier)
     * Retire tous les éléments de cette couche
     */
    public void effacerHexaSelect() {
        Component[] listElem = lp.getComponentsInLayer(JLayeredPane.PALETTE_LAYER);

        for(Component i : listElem)
            lp.remove(i);
        this.revalidate();
        this.repaint();
    }
    
    /**
     * Récupère tous les éléments sur le MODAL_LAYER (cf. liste des couches en haut du fichier)
     * Efface l'unité qui correspont aux coordonnées données
     * Efface la vie de l'unité
     * Efface l'armure de l'unité
     * Efface la coche le l'unité
     * @param coordsV coords graphiques de l'unité à effacer
     */
    public void effacerUnite(int[] coordsV) {
        Component[] listElem = lp.getComponentsInLayer(JLayeredPane.MODAL_LAYER);

        for(int i=0;i<listElem.length;i++) {
            if((listElem[i].getX() == coordsV[0]) && (listElem[i].getY() == coordsV[1]))
                lp.remove(listElem[i]);
        }
        effacerVieUnite(coordsV);
        effacerArmureUnite(coordsV);
        effacerCoche(coordsV);
    }

    /**
     * Récupère tous les éléments sur le POPUP_LAYER (cf. liste des couches en haut du fichier)
     * Efface les éléments situés aux coordonnées des chiffres et du coeur
     * @param coordsV coords graphiques de l'unité
     */
    public void effacerVieUnite(int[] coordsV) {
        Component[] listElem = lp.getComponentsInLayer(JLayeredPane.POPUP_LAYER);

        for(int i=0;i<listElem.length;i++) {
            if((((listElem[i].getX() == coordsV[0]+98) || (listElem[i].getX() == coordsV[0]+114)) && (listElem[i].getY() == coordsV[1]+76))
            || ((listElem[i].getX() == coordsV[0]+96) && (listElem[i].getY() == coordsV[1]+96)))
                lp.remove(listElem[i]);
        }
        this.revalidate();
        this.repaint();
    }

    /**
     * Récupère tous les éléments sur le POPUP_LAYER (cf. liste des couches en haut du fichier)
     * Efface les éléments situés aux coordonnées des chiffres et de l'armure
     * @param coordsV coords graphiques de l'unité
     */
    public void effacerArmureUnite(int[] coordsV) {
        Component[] listElem = lp.getComponentsInLayer(JLayeredPane.POPUP_LAYER);

        for(int i=0;i<listElem.length;i++) {
            if((((listElem[i].getX() == coordsV[0]+2) || (listElem[i].getX() == coordsV[0]+18)) && (listElem[i].getY() == coordsV[1]+76))
            || ((listElem[i].getX() == coordsV[0]) && (listElem[i].getY() == coordsV[1]+96)))
                lp.remove(listElem[i]);
        }
        this.revalidate();
        this.repaint();
    }

    /**
     * Récupère tous les éléments sur le POPUP_LAYER (cf. liste des couches en haut du fichier)
     * Efface la coche
     * @param coordsV coords graphiques de l'unité
     */
    public void effacerCoche(int[] coordsV) {
        Component[] listElem = lp.getComponentsInLayer(JLayeredPane.POPUP_LAYER);

        for(int i=0;i<listElem.length;i++) {
            if(listElem[i].getX() == coordsV[0] && listElem[i].getY() == coordsV[1])
                lp.remove(listElem[i]);
        }
        this.revalidate();
        this.repaint();
    }

    /**
     * Récupère tous les éléments sur le DMG_LAYER (cf. liste des couches en haut du fichier)
     * Efface les dégâts
     * @param coordsV coords graphiques de l'unité
     */
    public void effacerDegatsUnite(int[] coordsV) {
        Component[] listElem = lp.getComponentsInLayer(Plateau.DMG_LAYER);

        for(int i=0;i<listElem.length;i++) {
            if(((listElem[i].getX() == coordsV[0]+98) || (listElem[i].getX() == coordsV[0]+114)) && (listElem[i].getY() == coordsV[1]))
                lp.remove(listElem[i]);
        }
        this.revalidate();
        this.repaint();
    }

    /**
     * Récupère tous les éléments sur le DRAG_LAYER (cf. liste des couches en haut du fichier)
     * Efface le brouillard d'une case
     * @param coordsV coords graphiques de la case
     */
    public void effacerBrouillard(int[] coordsV) {
        Component[] listElem = lp.getComponentsInLayer(JLayeredPane.DRAG_LAYER);

        for(int i=0;i<listElem.length;i++) {
            if((listElem[i].getX() == coordsV[0]) && (listElem[i].getY() == coordsV[1]))
                lp.remove(listElem[i]);
        }
        this.revalidate();
        this.repaint();
    }
}
