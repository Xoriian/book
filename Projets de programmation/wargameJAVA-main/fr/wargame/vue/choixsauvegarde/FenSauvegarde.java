package fr.wargame.vue.choixsauvegarde;

import java.awt.event.*;
import java.io.File;
import javax.swing.*;

import fr.wargame.modele.sauvegarde.Sauvegarde;

/**
 * Fenêtre permettant d'enregister la partie et de quitter.
 * Il est également possible de supprimer un fichier de sauvegarde.
 */
public class FenSauvegarde extends JFrame implements ActionListener {

	JButton boutQuitter = new JButton("Quitter");
	JButton boutSauvegarder = new JButton("Sauv. + quitter");
	JSeparator separateur = new JSeparator();
	JButton boutSupprimer = new JButton("Supprimer fichier");
	JComboBox<File> comboFichiers = new JComboBox<File>();

	public FenSauvegarde() {
		setTitle("");
		setSize(350, 210);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setUndecorated(true);
		boutQuitter.setBounds(85, 10, 180, 30);
		boutQuitter.addActionListener(this);
		add(boutQuitter);
		boutSauvegarder.setBounds(85, 50, 180, 30);
		boutSauvegarder.addActionListener(this);
		add(boutSauvegarder);
		separateur.setBounds(0, 90, 350, 10);
		add(separateur);
		for(File f : Sauvegarde.recupererFichiers())
			comboFichiers.addItem(f);
		if(comboFichiers.getItemCount() == 0)
			boutSupprimer.setEnabled(false);
		comboFichiers.setBounds(10, 105, 330, 50);
		add(comboFichiers);
		boutSupprimer.setBounds(85, 170, 180, 30);
		boutSupprimer.addActionListener(this);
		add(boutSupprimer);
		validate();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Donne les actions aux boutons.
	 * Quitter pour sortir du programme.
	 * Sauvegarder pour enregistrer et sortir.
	 * Supprimer pour éliminer un fichier de sauvegarde.
	 * @param e l'événement qui s'est produit
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == boutQuitter) {
			dispose();
			System.exit(0);
		}
		if(source == boutSauvegarder) {
			dispose();
			Sauvegarde.enregistrer();
			System.exit(0);
		}
		if(source == boutSupprimer) {
			Sauvegarde.supprimer((File) comboFichiers.getSelectedItem());
			comboFichiers.removeAllItems();
			for(File f : Sauvegarde.recupererFichiers())
				comboFichiers.addItem(f);
			if(comboFichiers.getItemCount() == 0)
				boutSupprimer.setEnabled(false);
		}
	}
}
