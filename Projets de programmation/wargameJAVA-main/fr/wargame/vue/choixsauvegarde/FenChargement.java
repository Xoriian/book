package fr.wargame.vue.choixsauvegarde;

import java.awt.event.*;
import java.io.File;
import javax.swing.*;

import fr.wargame.controleur.main.*;
import fr.wargame.modele.sauvegarde.Sauvegarde;
import fr.wargame.vue.accueil.Home;

/**
 * Fenêtre permettant de charger une partie enregistrée.
 */
public class FenChargement extends JFrame implements ActionListener {

	JButton boutRetour = new JButton("Retour");
	JSeparator separateur = new JSeparator();
	JButton boutCharger = new JButton("Charger partie");
	JComboBox<File> comboFichiers = new JComboBox<File>();

	public FenChargement() {
		setTitle("");
		setSize(350, 175);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setUndecorated(true);
		boutRetour.setBounds(85, 10, 180, 30);
		boutRetour.addActionListener(this);
		add(boutRetour);
		separateur.setBounds(0, 50, 350, 10);
		add(separateur);
		for(File f : Sauvegarde.recupererFichiers())
			comboFichiers.addItem(f);
		if(comboFichiers.getItemCount() == 0) 
			boutCharger.setEnabled(false);
		comboFichiers.setBounds(10, 65, 330, 50);
		add(comboFichiers);
		boutCharger.setBounds(85, 130, 180, 30);
		boutCharger.addActionListener(this);
		add(boutCharger);
		validate();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Donne les actions aux boutons.
	 * Retour pour revenir au menu principal.
	 * Charger pour continuer une partie commencée.
	 * @param e l'événement qui s'est produit
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == boutRetour) {
			dispose();
			Home fenetreAccueil = new Home();
			fenetreAccueil.setVisible(true);
		}
		if(source == boutCharger) {
			Sauvegarde.charger((File) comboFichiers.getSelectedItem());
			dispose();
			Main.setBloque(false);
		}
	}
}
