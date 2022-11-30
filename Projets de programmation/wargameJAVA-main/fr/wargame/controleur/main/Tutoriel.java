package fr.wargame.controleur.main;

import java.util.ArrayList;
import javax.swing.*;

import fr.wargame.controleur.gestionclic.*;
import fr.wargame.modele.actions.*;
import fr.wargame.modele.plateaucase.PlateauMod;
import fr.wargame.modele.unites.*;
import fr.wargame.vue.fenetrejeu.FenetreJeu;

/**
 * Cette classe implémente une unique méthode représentant le tutoriel
 * Elle reprend différents points du main principal
 */
public class Tutoriel {

    /**
     * Méthode implémentant le tutoriel
     */
    public static void tutoriel () {
        ArrayList<ArrayList<Unite>> unites = new ArrayList<>();
        PlateauMod plateauMod = new PlateauMod();
        FenetreJeu fenJeu = new FenetreJeu(true);
        Unite unite;

        ArrayList<ArrayList<Integer>> casesDispos = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> indicesEnnemisAttaquables = new ArrayList<Integer>();

        fenJeu.getPlateauG().afficherPlateau(plateauMod.plateauJeu);

        unites.add(new ArrayList<Unite>());
        unites.add(new ArrayList<Unite>());

        unites.get(0).add(new Archer()); unites.get(0).get(0).setCoords(5, 4); 
        unites.get(0).add(new Lancier()); unites.get(0).get(1).setCoords(5, 1); 

        unites.get(1).add(new Mage()); unites.get(1).get(0).setCoords(0, 2);
        unites.get(1).add(new Renard()); unites.get(1).get(1).setCoords(0, 3);
        unites.get(1).add(new Dragon()); unites.get(1).get(2).setCoords(1, 3); 
        
        for(int i=0; i<unites.get(0).size(); i++) {
            unites.get(0).get(i).setTypeCase(plateauMod.plateauJeu[unites.get(0).get(i).getCoords()[0]][unites.get(0).get(i).getCoords()[1]].getTypeCase());
            plateauMod.plateauJeu[unites.get(0).get(i).getCoords()[0]][unites.get(0).get(i).getCoords()[1]].setEstOccupee(true);
            fenJeu.getPlateauG().afficherUnite(unites.get(0).get(i),Conversion.modeleVersVue(unites.get(0).get(i).getCoords()), 0);
        }
        for(int i=0; i<unites.get(1).size(); i++) {
            unites.get(1).get(i).changerEstJouee();
            unites.get(1).get(i).setTypeCase(plateauMod.plateauJeu[unites.get(1).get(i).getCoords()[0]][unites.get(1).get(i).getCoords()[1]].getTypeCase());
            plateauMod.plateauJeu[unites.get(1).get(i).getCoords()[0]][unites.get(1).get(i).getCoords()[1]].setEstOccupee(true);
            fenJeu.getPlateauG().afficherUnite(unites.get(1).get(i),Conversion.modeleVersVue(unites.get(1).get(i).getCoords()), 1);
        }

        unite = unites.get(1).get(0);

        JOptionPane.showMessageDialog(fenJeu, 
        "Bienvenue dans Spirits ! Ce tutoriel va vous apprendre les bases du jeu.",
        "Tutoriel",JOptionPane.INFORMATION_MESSAGE);

        fenJeu.getPlateauG().afficherHexaSelect(Conversion.modeleVersVue(unite.getCoords()), "S");
        fenJeu.getFenStats().afficherDyn(unite);
        Deplacement.listerCasesPossibles(plateauMod.plateauJeu, casesDispos, unite, 0, unite.getCoords()[0], unite.getCoords()[1]);

        for(int i=0; i<casesDispos.size(); i++) 
            fenJeu.getPlateauG().afficherHexaSelect(Conversion.modeleVersVue(new int[]{casesDispos.get(i).get(0),casesDispos.get(i).get(1)}), "D");

        JOptionPane.showMessageDialog(fenJeu, 
        "Vous allez commencer par déplacer le mage. \nDans la partie gauche, vous avez accès à ses statistiques : PV, Attaque Physique, Défense Physique, Attaque Magique, Défense Magique. \nLes cases possibles pour déplacer l'unité sont affichées, nous allons maintenant la déplacer.",
        "Tutoriel",JOptionPane.INFORMATION_MESSAGE);

        fenJeu.getPlateauG().effacerHexaSelect();
        fenJeu.getPlateauG().effacerUnite(Conversion.modeleVersVue(unite.getCoords()));
        Deplacement.deplacerUnite(plateauMod.plateauJeu, casesDispos, unite, 2, 2);
        fenJeu.getPlateauG().afficherUnite(unite, Conversion.modeleVersVue(unite.getCoords()), 1);
        Recherchecibles.listerEnnemisAttaquables(plateauMod.plateauJeu, unites.get(0), indicesEnnemisAttaquables, unite.getStatsActuelles().getPOR()[0], 0, unite.getCoords()[0], unite.getCoords()[1]);

        for(int i=0; i<indicesEnnemisAttaquables.size(); i++) {
            fenJeu.getPlateauG().afficherHexaSelect(Conversion.modeleVersVue(
            new int[]{unites.get(0).get(indicesEnnemisAttaquables.get(i)).getCoords()[0], 
            unites.get(0).get(indicesEnnemisAttaquables.get(i)).getCoords()[1]}), "A");
            fenJeu.getPlateauG().afficherDegatsUnite(Conversion.modeleVersVue(unites.get(0).get(indicesEnnemisAttaquables.get(i)).getCoords()),
            unites.get(0).get(indicesEnnemisAttaquables.get(i)).getStatsActuelles().getPV() 
            - unite.attaquer(unites.get(0).get(indicesEnnemisAttaquables.get(i)), true));
        }

        JOptionPane.showMessageDialog(fenJeu, 
        "Vous avez déplacé votre unité, maintenant c'est l'heure de l'attaque !",
        "Tutoriel",JOptionPane.INFORMATION_MESSAGE);

        JOptionPane.showMessageDialog(fenJeu, 
        "Les unités à portée sont indiquées ainsi que les dégâts maximum qui peuvent être infligés. \nAttention aux effets des cases qui peuvent altérer les dégâts et les déplacements.",
        "Tutoriel",JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Tutoriel.class.getResource("/sprites/tutoriel/effets_terrain.png")));

        fenJeu.getPlateauG().effacerUnite(Conversion.modeleVersVue(unites.get(0).get(1).getCoords()));
        fenJeu.getPlateauG().effacerUnite(Conversion.modeleVersVue(unite.getCoords()));
        unite.attaquer(unites.get(0).get(1), false);
        fenJeu.getPlateauG().afficherUnite(unites.get(0).get(1), Conversion.modeleVersVue(unites.get(0).get(1).getCoords()), 0);
        unites.get(1).get(0).changerEstJouee();
        fenJeu.getPlateauG().afficherUnite(unite, Conversion.modeleVersVue(unite.getCoords()), 1);
        fenJeu.getPlateauG().effacerHexaSelect();
        fenJeu.getPlateauG().effacerDegatsUnite(Conversion.modeleVersVue(unites.get(0).get(1).getCoords()));
        unite = unites.get(1).get(2);   
        unite.setUltime(100);
        fenJeu.getFenStats().effacerDyn();
        fenJeu.getFenStats().afficherDyn(unite);
        fenJeu.getPlateauG().afficherHexaSelect(Conversion.modeleVersVue(unite.getCoords()), "S");

        casesDispos.clear();
        Deplacement.listerCasesPossibles(plateauMod.plateauJeu, casesDispos, unite, 0, unite.getCoords()[0], unite.getCoords()[1]);
        for(int i=0; i<casesDispos.size(); i++)
            fenJeu.getPlateauG().afficherHexaSelect(Conversion.modeleVersVue(new int[]{casesDispos.get(i).get(0),casesDispos.get(i).get(1)}), "D");

        JOptionPane.showMessageDialog(fenJeu, 
        "Passons maintenant au dragon. \nSon attaque ultime est disponible, il suffit de cliquer sur le bouton dans la fenêtre des statistiques avant de le déplacer pour l'activer.",
        "Tutoriel",JOptionPane.INFORMATION_MESSAGE);

        fenJeu.getFenStats().afficherUltSelect();

        JOptionPane.showMessageDialog(fenJeu, 
        "Les attaques normales et ultimes diffèrent selon les unités. Faites aussi attention à la portée de chaque attaque ! \nMaintenant déplaçons le dragon et attaquons le lancier !", 
        "Tutoriel",JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Tutoriel.class.getResource("/sprites/tutoriel/effets_attaques.png"))); 

        fenJeu.getPlateauG().effacerHexaSelect();
        unite.changerEstJouee();
        fenJeu.getPlateauG().effacerUnite(Conversion.modeleVersVue(unite.getCoords()));
        Deplacement.deplacerUnite(plateauMod.plateauJeu, casesDispos, unite, 4, 0);
        fenJeu.getPlateauG().afficherUnite(unite, Conversion.modeleVersVue(unite.getCoords()), 1);
        unite.attaquerUltime(unites.get(0).get(1), false);
        fenJeu.getPlateauG().effacerUnite(Conversion.modeleVersVue(unites.get(0).get(1).getCoords()));

        JOptionPane.showMessageDialog(fenJeu, 
        "Pour finir votre tour plus rapidement, vous pouvez cliquer sur le bouton 'fin' avant de déplacer une unité. \nToutes les unités restantes resteront sur place et se soigneront automatiquement."
        ,"Tutoriel",JOptionPane.INFORMATION_MESSAGE);

        fenJeu.getFenStats().effacerDyn();
        unites.get(1).get(1).changerEstJouee();
        fenJeu.getPlateauG().effacerUnite(Conversion.modeleVersVue(unites.get(1).get(1).getCoords()));
        fenJeu.getPlateauG().afficherUnite(unites.get(1).get(1), Conversion.modeleVersVue(unites.get(1).get(1).getCoords()), 1);
        fenJeu.getPlateauG().effacerUnite(Conversion.modeleVersVue(unites.get(0).get(0).getCoords()));
        Deplacement.deplacerUnite(plateauMod.plateauJeu, casesDispos, unites.get(0).get(0), 2, 3);
        unites.get(0).get(0).attaquer(unites.get(1).get(1), false);
        fenJeu.getPlateauG().afficherUnite(unites.get(0).get(0), Conversion.modeleVersVue(unites.get(0).get(0).getCoords()), 0);
        fenJeu.getPlateauG().effacerUnite(Conversion.modeleVersVue(unites.get(1).get(1).getCoords()));
        fenJeu.getPlateauG().afficherUnite(unites.get(1).get(1), Conversion.modeleVersVue(unites.get(1).get(1).getCoords()), 1);

        for(Unite u : unites.get(1)) {
            u.changerEstJouee();
            fenJeu.getPlateauG().effacerUnite(Conversion.modeleVersVue(u.getCoords()));
            fenJeu.getPlateauG().afficherUnite(u, Conversion.modeleVersVue(u.getCoords()), 1);
        }

        JOptionPane.showMessageDialog(fenJeu, 
        "Votre adversaire vient de jouer, il ne nous reste plus qu'à voir comment soigner ses unités."
        ,"Tutoriel",JOptionPane.INFORMATION_MESSAGE);

        unite = unites.get(1).get(1);
        fenJeu.getFenStats().afficherDyn(unite);
        fenJeu.getPlateauG().effacerUnite(Conversion.modeleVersVue(unite.getCoords()));
        fenJeu.getPlateauG().afficherUnite(unite, Conversion.modeleVersVue(unite.getCoords()), 1);
        fenJeu.getPlateauG().afficherHexaSelect(Conversion.modeleVersVue(unite.getCoords()), "S");
        fenJeu.getFenStats().afficherDyn(unite);

        JOptionPane.showMessageDialog(fenJeu, 
        "Votre renard a perdu de la vie, il vous faut vous reposer pour le soigner. Pour cela, il vous faut sélectionner l'unité mais ni la déplacer ni attaquer avec."
        ,"Tutoriel",JOptionPane.INFORMATION_MESSAGE);

        fenJeu.getPlateauG().effacerUnite(Conversion.modeleVersVue(unite.getCoords()));
        unite.soigner();
        fenJeu.getPlateauG().afficherUnite(unite, Conversion.modeleVersVue(unite.getCoords()), 1);

        JOptionPane.showMessageDialog(fenJeu, 
        "Vous regagnez 10% de vie en vous reposant sur toutes les cases, sauf sur les villages où vous regagnez 30% de vos PV max."
        ,"Tutoriel",JOptionPane.INFORMATION_MESSAGE);

        fenJeu.getPlateauG().effacerHexaSelect();
        Main.gestionBrouillard(fenJeu, unites.get(1), plateauMod.plateauJeu);

        JOptionPane.showMessageDialog(fenJeu, "Pour finir, vous voyez les nuages en bas à droite ? \nIls cachent les cases que vous ne pouvez pas atteindre en déplaçant vos unités. Faites attention à cela en jeu !"
        ,"Tutoriel",JOptionPane.INFORMATION_MESSAGE);

        JOptionPane.showMessageDialog(fenJeu, "Ceci termine notre tutoriel ! \nVous avez toutes les bases pour jouer, amusez-vous bien !"
        ,"Tutoriel",JOptionPane.INFORMATION_MESSAGE);

        fenJeu.dispose();
    }
}
