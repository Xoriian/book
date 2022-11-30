package fr.wargame.vue.ecranfin;

import javax.swing.*;

import fr.wargame.vue.fenetrejeu.FenetreJeu;

/**
 * Affiche qui a gagné la partie
 */
public class EcranFin extends JFrame {
    JLabel txt;

    public EcranFin(Boolean estIA, int numJ, FenetreJeu fenJeu) {
        super("Spirits");
        fenJeu.setDefaultCloseOperation(EXIT_ON_CLOSE);

        if(estIA)
            txt = new JLabel("L'ordinateur " + numJ + " a gagné !", SwingConstants.CENTER);
        else
            txt = new JLabel("Le joueur " + numJ + " a gagné !", SwingConstants.CENTER);
        add(txt);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300,100);
        setResizable(false);
        setVisible(true);
    }
}