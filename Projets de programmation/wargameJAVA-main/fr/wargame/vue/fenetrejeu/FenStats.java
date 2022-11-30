package fr.wargame.vue.fenetrejeu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import fr.wargame.controleur.gestionclic.*;
import fr.wargame.modele.unites.*;

/**
 * Permet d'afficher les statistiques de l'unité sélectionnée
 * Autres éléments importants pour le déroulé du jeu (Ultime et passer le tour)
 */
public class FenStats extends JPanel implements ActionListener {
    protected JLayeredPane lp = new JLayeredPane();
    Clic clic;
    public JButton btnUlt,btnPasser;

    /**
     * DEFAULT_LAYER = Possibilité de mettre un fond personnalisé;
     * PALETTE_LAYER = Elements statiques;
     * MODAL_LAYER = Elements dynamiques;
     * POPUP_LAYER = Contour ultime sélectionné;
     */

    /**
     * @param clicFen clic joueur
     */
    FenStats(Clic clicFen) {
        lp.setBounds(0,0,250,720);
        this.add(lp);
        this.setBounds(0,0,250,720);
        this.setLayout(null);
        this.setBackground(new Color(60,80,100));
        this.afficherStatic();
        this.clic = clicFen;
    }

    /**
     * Affiche les icônes des stats PV,ATQ,DEFP,MAG,DEFM
     * Affiche le bouton pour passer le tour
     */
    public void afficherStatic() {
        int cmpt = 0;
        JLabel lab;
        String[] stat = {"pv","atq","defp","mag","defm"};
        String chemin;
        btnPasser = new JButton(new ImageIcon(getClass().getResource("/sprites/boutons/btnPasser.png")));

        for(String i : stat) {
            chemin = "/sprites/stats/" + i + ".png";
            lab = new JLabel(new ImageIcon(getClass().getResource(chemin)));
            lab.setBounds(80,250+64*cmpt,32,32);
            lp.add(lab,JLayeredPane.PALETTE_LAYER);
            cmpt++;
        }
        btnPasser.setBorderPainted(false); 
        btnPasser.setContentAreaFilled(false); 
        btnPasser.setOpaque(false);
        btnPasser.setDisabledIcon(new ImageIcon(getClass().getResource("/sprites/boutons/btnPasserDis.png")));
        btnPasser.setPressedIcon(new ImageIcon(getClass().getResource("/sprites/boutons/btnPasserPress.png")));
        btnPasser.setBounds(25,620,200,50);
        btnPasser.addActionListener(this);
        lp.add(btnPasser,JLayeredPane.PALETTE_LAYER);
    }

    /**
     * Affiche l'image de l'unité sélectionnée
     * Affiche le bouton d'ultime si disponible
     * Affiche les stats actuelles de l'unité
     * @param unite unité sélectionnée
     */
    public void afficherDyn(Unite unite) {
        //Pour l'affichage des chiffres
        int[] stat = {unite.getStatsActuelles().getPV(),
                    unite.getStatsActuelles().getATQ(),
                    unite.getStatsActuelles().getDEFP(),
                    unite.getStatsActuelles().getMAG(),
                    unite.getStatsActuelles().getDEFM()};
        JLabel[] num = new JLabel[2];
        String vie;
        int cmpt = 0;
        String[] tabNum;
        String chemin;
        JLabel imgUnit;

        //Affichage de l'unité sélectionnée
        chemin = "/sprites/unites/" + unite.donnerTypeUnite() + ".png";
        imgUnit = new JLabel(new ImageIcon(getClass().getResource(chemin)));
        imgUnit.setBounds(61,20,128,128);
        lp.add(imgUnit,JLayeredPane.MODAL_LAYER);

        //Affichage bouton ult si dispo
        if(unite.estUltimeDispo()) {
            btnUlt = new JButton(new ImageIcon(getClass().getResource("/sprites/boutons/btnUlt.png")));
            btnUlt.setBorderPainted(false); 
            btnUlt.setContentAreaFilled(false); 
            btnUlt.setOpaque(false);
            btnUlt.setDisabledIcon(new ImageIcon(getClass().getResource("/sprites/boutons/btnUlt.png")));
            btnUlt.setBounds(93,150,64,64);
            lp.add(btnUlt,JLayeredPane.MODAL_LAYER);
            btnUlt.addActionListener(this);
        }

        //Affichage des stats
        for(int i : stat) {
            vie = String.valueOf(i);
            if(vie.length() == 1)
                vie = "0" + vie;
            tabNum = vie.split("");

            for(int j=1; j>=0; j--) {
                chemin = "/sprites/chiffres/";
                chemin += tabNum[j] + ".png";
                num[j] = new JLabel(new ImageIcon(getClass().getResource(chemin)));
                num[j].setBounds(130+j*16,255+64*cmpt,12,20);
                lp.add(num[j],JLayeredPane.MODAL_LAYER);
            }
            cmpt++;
        }
        this.revalidate();
        this.repaint();
    }

    /**
     * Affiche un contour indiquant que l'ultime est sélectionné
     */
    public void afficherUltSelect() {
        JLabel lab = new JLabel(new ImageIcon(getClass().getResource("/sprites/boutons/btnUlt_S.png")));

        lab.setBounds(93,150,64,64);
        lp.add(lab,JLayeredPane.POPUP_LAYER);
        this.revalidate();
        this.repaint();
    }

    /**
     * Récupère tous les éléments sur le MODAL_LAYER (cf. liste des couches en haut du fichier)
     * Efface tous les éléments de cette couche
     */
    public void effacerDyn() {
        Component[] listElem = lp.getComponentsInLayer(JLayeredPane.MODAL_LAYER);
        
        for(Component i : listElem) {
            lp.remove(i);
        }
        effacerUltSelect();
        this.revalidate();
        this.repaint();
    }

    /**
     * Récupère tous les éléments sur le POPUP_LAYER (cf. liste des couches en haut du fichier)
     * Efface tous les éléments de cette couche
     */
    public void effacerUltSelect() {
        Component[] listElem = lp.getComponentsInLayer(JLayeredPane.POPUP_LAYER);
        
        for(Component i : listElem) 
            lp.remove(i);
        this.revalidate();
        this.repaint();
    }

    /**
     * Récupère la source de l'événement
     * Si ult -> on indique que le joueur veut/ne veut plus l'ultime
     * Autre -> passer le tour
     * @param event appuyer sur un bouton
     */
    public void actionPerformed (ActionEvent event) {
        if(event.getSource() == btnUlt) {
            if(clic.getVeutUlti()) {
                clic.setVeutUlti(false);
                effacerUltSelect();
            }
            else {
                clic.setVeutUlti(true);
                afficherUltSelect();
            }
        }
        else
            clic.setVeutPasser(true);
    }
}
