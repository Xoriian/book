package fr.wargame.vue.accueil;

import java.awt.event.*;
import javax.swing.*;

import fr.wargame.controleur.main.*;
import fr.wargame.vue.choixsauvegarde.FenChargement;
import fr.wargame.vue.selectionfenetre.SelectionUniteFenetre;

/**
 * Classe représentant la fenêtre d'accueil
 */
public class Home extends JFrame{	

	JRadioButton newGameRadioButton;
	JRadioButton loadGameRadioButton;
    JButton joueurVSjoueurButton = new JButton("Joueur VS Joueur");
    JButton joueurVSmachineButton = new JButton("Joueur VS Machine");
    JButton machineVSmachineButton = new JButton("Machine VS Machine");
    JButton tutoButton = new JButton("Tutoriel");
    static public int boutonClique;
    
	public Home() {
	    super( "Spirits - Accueil" );
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(400, 550);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	    
	    JPanel contentPane  = (JPanel) this.getContentPane();
	    this.setLayout(null);
	    ImageIcon icone = new ImageIcon(getClass().getResource("/sprites/logo.png"));
        JLabel image = new JLabel(icone);
        image.setBounds(75,0, 250, 100);
        contentPane.add(image);
	    ButtonGroup newORloadGame = new ButtonGroup();
	    newGameRadioButton = new JRadioButton("Nouvelle partie");
	    newGameRadioButton.setSelected(true);
	    newGameRadioButton.setBounds(70,100, 250, 50);
	    contentPane.add(newGameRadioButton);
	    newORloadGame.add(newGameRadioButton);
	    loadGameRadioButton = new JRadioButton("Charger une partie");
	    loadGameRadioButton.setBounds(70,130, 250, 50);
	    contentPane.add(loadGameRadioButton);
	    newORloadGame.add(loadGameRadioButton);
	    joueurVSjoueurButton.setBounds(70, 200, 250, 50);
	    joueurVSjoueurButton.addActionListener(this::joueurVSjoueurButtonListener);
	    contentPane.add(joueurVSjoueurButton);
	    joueurVSmachineButton.setBounds(70, 270, 250, 50);
	    joueurVSmachineButton.addActionListener(this::joueurVSmachineButtonListener);
	    contentPane.add(joueurVSmachineButton);
	    machineVSmachineButton.setBounds(70,340,250,50);
	    machineVSmachineButton.addActionListener(this::machineVSmachineButtonListener);
	    contentPane.add(machineVSmachineButton);
	    tutoButton.setBounds(70,410,250,50);
	    tutoButton.addActionListener(this::tutoButtonListener);
	    contentPane.add(tutoButton);
	}	
	
	/**
	 * Ferme la fenêtre d'accueil et ouvre la fenêtre de sélection des unités ou ouvre la fenêtre de chargement (JvJ)
	 * @param event 
	 */
	private void joueurVSjoueurButtonListener (ActionEvent event) {
		boutonClique=1;
		dispose();
		if (newGameRadioButton.isSelected()) {
			SelectionUniteFenetre fenetreSelectionTroupes = new SelectionUniteFenetre();
			fenetreSelectionTroupes.setVisible(true);  
		}
		else if(loadGameRadioButton.isSelected())
			new FenChargement();
	}

	/**
	 * Ferme la fenêtre d'accueil et ouvre la fenêtre de sélection des unités ou ouvre la fenêtre de chargement (JvM)
	 * @param event 
	 */
	private void joueurVSmachineButtonListener (ActionEvent event) {
		boutonClique=2;
		dispose();
		if (newGameRadioButton.isSelected()) {
			SelectionUniteFenetre fenetreSelectionTroupes = new SelectionUniteFenetre();
			fenetreSelectionTroupes.setVisible(true);  	
		}
		else if(loadGameRadioButton.isSelected()) {
			Main.estIA[0] = true; 
			new FenChargement();
		}
	}

	/**
	 * Ferme la fenêtre d'accueil et ouvre la fenêtre de sélection des unités ou ouvre la fenêtre de chargement (MvM)
	 * @param event 
	 */
	private void machineVSmachineButtonListener (ActionEvent event) {
		boutonClique=3;
		dispose();
		if (newGameRadioButton.isSelected()){
			SelectionUniteFenetre fenetreSelectionTroupes = new SelectionUniteFenetre();
			fenetreSelectionTroupes.setVisible(true);
		}
		else if(loadGameRadioButton.isSelected()){
			Main.estIA[0] = true; 
			Main.estIA[1] = true; 
			new FenChargement();
		}
	}

	/**
	 * Cache la fenêtre d'accueil et ouvre le tutoriel
	 * @param event 
	 */
	private void tutoButtonListener (ActionEvent event) {
		this.setVisible(false);
		Tutoriel.tutoriel();
		this.setVisible(true);
	}
}
