package fr.wargame.vue.selectionfenetre;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.*;

import fr.wargame.controleur.main.Main;
import fr.wargame.modele.unites.*;
import fr.wargame.vue.accueil.Home;

/**
 * Classe qui affiche la fenêtre de sélection des unités 
 */
public class SelectionUniteFenetre extends JFrame{

	JButton ajouterUniteButton = new JButton("Confirmer la composition");
	JPanel conteneur  = (JPanel) this.getContentPane();
	JLabel labelPV = new JLabel("Point de vie de l'unite : ");
	JLabel labelATQ = new JLabel("Attaque physique de l'unité : ");
	JLabel labelDEF = new JLabel("Defense physique de l'unité : ");
	JLabel labelATQSpe = new JLabel("Attaque magique de l'unité : ");
	JLabel labelDEFSpe = new JLabel("Defense magique de l'unité : ");
	JLabel labelPormin = new JLabel("Portee de l'attaque normale de l'unité : ");
	JLabel labelPormax = new JLabel("Portee de l'attaque ultime de l'unité : ");
	JLabel labelDep = new JLabel("Deplacement de l'unité : ");
	JLabel PVMage = new JLabel("25");
	JLabel MageATQ = new JLabel("0");
	JLabel MageDEF = new JLabel("5");
	JLabel MageATQSpe = new JLabel("20");
	JLabel MageDEFSpe = new JLabel("15");
	JLabel MagePornor = new JLabel("3");
	JLabel MagePorult = new JLabel("2");
	JLabel MageDep = new JLabel("4");
	JLabel PVDragon = new JLabel("35");
	JLabel DragonATQ = new JLabel("15");
	JLabel DragonDEF = new JLabel("10");
	JLabel DragonATQSpe = new JLabel("20");
	JLabel DragonDEFSpe = new JLabel("15");
	JLabel DragonPornor = new JLabel("2");
	JLabel DragonPorult = new JLabel("2");
	JLabel DragonDep = new JLabel("5");
	JLabel PVRenard = new JLabel("35");
	JLabel RenardATQ = new JLabel("0");
	JLabel RenardDEF = new JLabel("10");
	JLabel RenardATQSpe = new JLabel("15");
	JLabel RenardDEFSpe = new JLabel("5");
	JLabel RenardPornor = new JLabel("1");
	JLabel RenardPorult = new JLabel("3");
	JLabel RenardDep = new JLabel("6");
	JLabel PVLancier = new JLabel("40");
	JLabel LancierATQ = new JLabel("15");
	JLabel LancierDEF = new JLabel("20");
	JLabel LancierATQSpe = new JLabel("0");
	JLabel LancierDEFSpe = new JLabel("10");
	JLabel LancierPornor = new JLabel("1");
	JLabel LancierPorult = new JLabel("1");
	JLabel LancierDep = new JLabel("3");
	JLabel PVArcher = new JLabel("30");
	JLabel ArcherATQ = new JLabel("15");
	JLabel ArcherDEF = new JLabel("10");
	JLabel ArcherATQSpe = new JLabel("15");
	JLabel ArcherDEFSpe = new JLabel("10");
	JLabel ArcherPornor = new JLabel("3");
	JLabel ArcherPorult = new JLabel("4");
	JLabel ArcherDep = new JLabel("4");
	int nbrUnitesJun;
	int nbrUnitesDeux;
	JLabel separator = new JLabel("___________________________________________________________________________________________________________________________________________________________");
	JLabel Sseparator = new JLabel("__________________________________________________________________________________________________________________________________________________________");
	JLabel Tseparator = new JLabel("___________________________________________________________________________________________________________________________________________________________");
	JLabel Fseparator = new JLabel("___________________________________________________________________________________________________________________________________________________________");
	JLabel Fiseparator = new JLabel("___________________________________________________________________________________________________________________________________________________________");
	JLabel Siseparator = new JLabel("___________________________________________________________________________________________________________________________________________________________");
	JLabel Seseparator = new JLabel("___________________________________________________________________________________________________________________________________________________________");
	JLabel Heseparator = new JLabel("___________________________________________________________________________________________________________________________________________________________");
	JLabel Neseparator = new JLabel("___________________________________________________________________________________________________________________________________________________________");
	JLabel nbrUniteLabel = new JLabel("Nombre d'unités : ");
	static SpinnerModel model = new SpinnerNumberModel(0, 0, 15, 1); 
	static SpinnerModel modelun = new SpinnerNumberModel(0, 0, 15, 1); 
	static SpinnerModel modeldeux = new SpinnerNumberModel(0, 0, 15, 1); 
	static SpinnerModel modeltrois = new SpinnerNumberModel(0, 0, 15, 1); 
	static SpinnerModel modelquatre = new SpinnerNumberModel(0, 0, 15, 1); 
	public static JSpinner nbrMage = new JSpinner(model);
	public static JSpinner nbrDragon = new JSpinner(modelun);
	public static JSpinner nbrRenard = new JSpinner(modeldeux);
	public static JSpinner nbrLancier = new JSpinner(modeltrois);
	public static JSpinner nbrArcher = new JSpinner(modelquatre);
	Boolean joueurUn=false;
	Boolean joueurDeux=false;
	JTextArea infos = new JTextArea();
	public static int nbrRenardJoueurDeux;
	public static int nbrLancierJoueurDeux;
	public static int nbrDragonJoueurDeux;
	public static int nbrMageJoueurDeux;
	public static int nbrArcherJoueurDeux;
	public static int nbrRenardJoueurUn;
	public static int nbrLancierJoueurUn;
	public static int nbrDragonJoueurUn;
	public static int nbrMageJoueurUn;
	public static int nbrArcherJoueurUn;
	Random random = new Random();

	public SelectionUniteFenetre() {
		setTitle("Selection des unites");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1110,600);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
		infos.setEditable(false);
		ImageIcon iconeMage = new ImageIcon(getClass().getResource("/sprites/unites/mage.png"));
		JLabel imageMage = new JLabel(iconeMage);
		imageMage.setBounds(190,0, 200, 190);
		conteneur.add(imageMage);

		ImageIcon iconeDragon = new ImageIcon(getClass().getResource("/sprites/unites/dragon.png"));
		JLabel imageDragon = new JLabel(iconeDragon);
		imageDragon.setBounds(370,0, 200, 190);
		conteneur.add(imageDragon);

		ImageIcon iconeRenard = new ImageIcon(getClass().getResource("/sprites/unites/renard.png"));
		JLabel imageRenard = new JLabel(iconeRenard);
		imageRenard.setBounds(570,0, 200, 150);
		conteneur.add(imageRenard);

		ImageIcon iconeLancier = new ImageIcon(getClass().getResource("/sprites/unites/lancier.png"));
		JLabel imageLancier = new JLabel(iconeLancier);
		imageLancier.setBounds(740,0, 200, 170);
		conteneur.add(imageLancier);

		ImageIcon iconeArcher = new ImageIcon(getClass().getResource("/sprites/unites/archer.png"));
		JLabel imageArcher = new JLabel(iconeArcher);
		imageArcher.setBounds(900,0, 200, 170);
		conteneur.add(imageArcher);

		labelPV.setBounds(50,170,200,24);
		conteneur.add(labelPV);
		separator.setBounds(50,180,1100,24);
		conteneur.add(separator);
		labelATQ.setBounds(50,200,200,24);
		conteneur.add(labelATQ);
		Tseparator.setBounds(50,210,1100,24);
		conteneur.add(Tseparator);
		labelDEF.setBounds(50,230,200,24);
		conteneur.add(labelDEF);
		Fseparator.setBounds(50,240,1100,24);
		conteneur.add(Fseparator);
		labelATQSpe.setBounds(50,260,200,24);
		conteneur.add(labelATQSpe);
		Fiseparator.setBounds(50,270,1100,24);
		conteneur.add(Fiseparator);
		labelDEFSpe.setBounds(50,290,200,24);
		conteneur.add(labelDEFSpe);
		Siseparator.setBounds(50,300,1100,24);
		conteneur.add(Siseparator);
		labelDep.setBounds(50,320,200,24);
		conteneur.add(labelDep);
		Seseparator.setBounds(50,330,1100,24);
		conteneur.add(Seseparator);
		labelPormin.setBounds(50,350,200,24);
		conteneur.add(labelPormin);
		Heseparator.setBounds(50,360,1100,24);
		conteneur.add(Heseparator);
		labelPormax.setBounds(50,380,200,24);
		conteneur.add(labelPormax);
		Neseparator.setBounds(50,390,1100,24);
		nbrUniteLabel.setBounds(50,410,200,24);
		nbrMage.setBounds(279,410,55,25);
		nbrDragon.setBounds(447,410,55,25);
		nbrRenard.setBounds(655,410,55,25);
		nbrArcher.setBounds(994,410,55,25);
		nbrLancier.setBounds(830,410,55,25);  
		PVMage.setBounds(280,170,200,24);
		conteneur.add(PVMage);
		MageATQ.setBounds(280,200,200,24);
		conteneur.add(MageATQ);
		MageDEF.setBounds(280,230,200,24);
		conteneur.add(MageDEF);
		MageATQSpe.setBounds(280,260,200,24);
		conteneur.add(MageATQSpe);
		MageDEFSpe.setBounds(280,290,200,24);
		conteneur.add(MageDEFSpe);
		MageDep.setBounds(280,320,200,24);
		conteneur.add(MageDep);
		MagePornor.setBounds(280,350,200,24);
		conteneur.add(MagePornor);
		MagePorult.setBounds(280,380,200,24);
		conteneur.add(MagePorult);
		PVDragon.setBounds(450,170,200,24);
		conteneur.add(PVDragon);
		DragonATQ.setBounds(450,200,200,24);
		conteneur.add(DragonATQ);
		DragonDEF.setBounds(450,230,200,24);
		conteneur.add(DragonDEF);
		DragonATQSpe.setBounds(450,260,200,24);
		conteneur.add(DragonATQSpe);
		DragonDEFSpe.setBounds(450,290,200,24);
		conteneur.add(DragonDEFSpe);
		DragonDep.setBounds(450,320,200,24);
		conteneur.add(DragonDep);
		DragonPornor.setBounds(450,350,200,24);
		conteneur.add(DragonPornor);
		DragonPorult.setBounds(450,380,200,24);
		conteneur.add(DragonPorult);
		PVRenard.setBounds(655,170,200,24);
		conteneur.add(PVRenard);
		RenardATQ.setBounds(655,200,200,24);
		conteneur.add(RenardATQ);
		RenardDEF.setBounds(655,230,200,24);
		conteneur.add(RenardDEF);
		RenardATQSpe.setBounds(655,260,200,24);
		conteneur.add(RenardATQSpe);
		RenardDEFSpe.setBounds(655,290,200,24);
		conteneur.add(RenardDEFSpe);
		RenardDep.setBounds(655,320,200,24);
		conteneur.add(RenardDep);
		RenardPornor.setBounds(655,350,200,24);
		conteneur.add(RenardPornor);
		RenardPorult.setBounds(655,380,200,24);
		conteneur.add(RenardPorult);
		PVLancier.setBounds(830,170,200,24);
		conteneur.add(PVLancier);
		LancierATQ.setBounds(830,200,200,24);
		conteneur.add(LancierATQ);
		LancierDEF.setBounds(830,230,200,24);
		conteneur.add(LancierDEF);
		LancierATQSpe.setBounds(830,260,200,24);
		conteneur.add(LancierATQSpe);
		LancierDEFSpe.setBounds(830,290,200,24);
		conteneur.add(LancierDEFSpe);
		LancierDep.setBounds(830,320,200,24);
		conteneur.add(LancierDep);
		LancierPornor.setBounds(830,350,200,24);
		conteneur.add(LancierPornor);
		LancierPorult.setBounds(830,380,200,24);
		conteneur.add(LancierPorult);
		PVArcher.setBounds(990,170,200,24);
		conteneur.add(PVArcher);
		ArcherATQ.setBounds(990,200,200,24);
		conteneur.add(ArcherATQ);
		ArcherDEF.setBounds(990,230,200,24);
		conteneur.add(ArcherDEF);
		ArcherATQSpe.setBounds(990,260,200,24);
		conteneur.add(ArcherATQSpe);
		ArcherDEFSpe.setBounds(990,290,200,24);
		conteneur.add(ArcherDEFSpe);
		ArcherDep.setBounds(990,320,200,24);
		conteneur.add(ArcherDep);
		ArcherPornor.setBounds(990,350,200,24);
		conteneur.add(ArcherPornor);
		ArcherPorult.setBounds(990,380,200,24);
		conteneur.add(ArcherPorult);
		infos.setBounds(50,500,500,40);
		if(Home.boutonClique == 3)
			ajouterUniteButton = new JButton("Confirmer le mode IA");
		if(Home.boutonClique != 3) {
			conteneur.add(infos);
			conteneur.add(nbrMage); conteneur.add(nbrDragon); conteneur.add(nbrLancier); conteneur.add(nbrRenard); conteneur.add(nbrArcher);
			conteneur.add(nbrUniteLabel); conteneur.add(Neseparator);
		}
		infos.setBackground(Color.lightGray);
		infos.setForeground(Color.BLACK);
		ajouterUniteButton.setBounds(800,490,250,50);
		ajouterUniteButton.addActionListener(this::ajouterUnitButtonListener);
		conteneur.add(ajouterUniteButton);
	}

	/**
	 * Ajoute les unités dans la liste
	 * @param joueur pour indiquer par quel joueur on commence l'initalisation
	 */
	public static void initialiserUnite(int joueur) {
		int i;
		joueur = joueur%2;

		for(i=0; i<nbrRenardJoueurUn; i++) {
			Renard renard = new Renard();
			Main.unites.get(joueur).add((Unite) renard);
		}
		for(i=0; i<nbrLancierJoueurUn; i++) {
			Lancier lancier = new Lancier();
			Main.unites.get(joueur).add((Unite) lancier);
		}
		for(i=0; i<nbrMageJoueurUn; i++) {
			Mage mage = new Mage();
			Main.unites.get(joueur).add((Unite) mage);
		}
		for(i=0; i<nbrDragonJoueurUn; i++) {
			Dragon dragon = new Dragon();
			Main.unites.get(joueur).add((Unite) dragon);
		}
		for(i=0; i<nbrArcherJoueurUn; i++) {
			Archer archer = new Archer();
			Main.unites.get(joueur).add((Unite) archer);
		}

		if(joueur == 1) {
			for(i=0; i<Main.unites.get(joueur).size(); i++)
				Main.unites.get(joueur).get(i).setCoords(0,i);
		}
		else {
			for(i=0; i<Main.unites.get(0).size(); i++)
				Main.unites.get(0).get(i).setCoords(19,19-i);
		}
	}

	/**
	 * Méthode permettant de vérifier le nombre d'unités ajoutées (et génération automatique pour l'ordinateur)
	 * @param event
	 */
	private void ajouterUnitButtonListener (ActionEvent event) {
		int unites = (int) nbrRenard.getValue()+ (int) nbrLancier.getValue() +  (int) nbrMage.getValue() + (int) nbrDragon.getValue() + (int) nbrArcher.getValue();

		// joueur VS joueur
		if(Home.boutonClique == 1) {
			if(!joueurUn && !joueurDeux) {
				if(unites != 15)
					JOptionPane.showMessageDialog(null, "Le nombre d'unités doit être égal à 15");
				else {
					joueurUn = true;
					nbrRenardJoueurUn = (int) nbrRenard.getValue();
					nbrArcherJoueurUn = (int) nbrArcher.getValue();
					nbrLancierJoueurUn=(int)nbrLancier.getValue();
					nbrDragonJoueurUn=(int)nbrDragon.getValue();
					nbrMageJoueurUn=(int)nbrMage.getValue();
					infos.append("JOUEUR 1: "+nbrMageJoueurUn+" mages, "+nbrDragonJoueurUn+" dragons, "+nbrRenardJoueurUn+" renards, "+nbrLancierJoueurUn+" lanciers,"+nbrArcherJoueurUn+" archers");
					infos.append("\n");
					SelectionUniteFenetre.initialiserUnite(1);
				}
			}
			else if(joueurUn && !joueurDeux) {
				if(unites != 15)
					JOptionPane.showMessageDialog(null, "Le nombre d'unités doit être égal à 15");
				else {
					joueurDeux = true;
					nbrLancierJoueurDeux=(int)nbrLancier.getValue();
					nbrDragonJoueurDeux = (int)nbrDragon.getValue();
					nbrArcherJoueurDeux = (int) nbrArcher.getValue();
					nbrMageJoueurDeux=(int)nbrMage.getValue();
					nbrRenardJoueurDeux=(int) nbrRenard.getValue();
					infos.append("JOUEUR 2: "+nbrMageJoueurDeux+" mages, "+nbrDragonJoueurDeux+" dragons, "+nbrRenardJoueurDeux+" renards, "+nbrLancierJoueurDeux+" lanciers, "+nbrArcherJoueurDeux +" Archers");
					SelectionUniteFenetre.initialiserUnite(2);
				}

			}
			if(joueurUn && joueurDeux) {
				Main.setBloque(false);
				dispose();
			}	
		}

		// joueur VS Machine
		if(Home.boutonClique == 2) {
			if(!joueurUn && !joueurDeux) {
				if(unites != 15)
					JOptionPane.showMessageDialog(null, "Le nombre d'unités doit être égal à 15");
				else {
					joueurUn = true;
					nbrRenardJoueurUn = (int) nbrRenard.getValue();
					nbrArcherJoueurUn = (int) nbrArcher.getValue();
					nbrLancierJoueurUn=(int)nbrLancier.getValue();
					nbrDragonJoueurUn=(int)nbrDragon.getValue();
					nbrMageJoueurUn=(int)nbrMage.getValue();
					infos.append("JOUEUR 1: "+nbrMageJoueurUn+" mages, "+nbrDragonJoueurUn+" dragons, "+nbrRenardJoueurUn+" renards, "+nbrLancierJoueurUn+" lanciers,"+nbrArcherJoueurUn+" archers");
					infos.append("\n");
					SelectionUniteFenetre.initialiserUnite(1);
				}
				if(joueurUn && !joueurDeux) {
					joueurDeux=true;
					nbrMageJoueurDeux = (int)(Math.random() * (15 + 1));
					nbrDragonJoueurDeux = (int)(Math.random() * ((15-nbrMageJoueurDeux) + 1));
					nbrLancierJoueurDeux = (int)(Math.random() * ((15-(nbrMageJoueurDeux+nbrDragonJoueurDeux)) + 1));
					nbrRenardJoueurDeux = (int)(Math.random() * ((15-(nbrMageJoueurDeux+nbrDragonJoueurDeux+nbrLancierJoueurDeux)) + 1));
					nbrArcherJoueurDeux = (15-(nbrMageJoueurDeux+nbrDragonJoueurDeux+nbrLancierJoueurDeux+nbrRenardJoueurDeux));
					SelectionUniteFenetre.initialiserUnite(2);
				}
				if(joueurUn && joueurDeux) {
					Main.estIA[0] = true;
					Main.setBloque(false);
					dispose();
				}
			}
		}

		//ia
		if(Home.boutonClique ==3) {
			if(!joueurUn && !joueurDeux) {
				joueurUn = true;
				nbrMageJoueurUn = (int)(Math.random() * (15 + 1));
				nbrDragonJoueurUn = (int)(Math.random() * ((15-nbrMageJoueurUn) + 1));
				nbrLancierJoueurUn = (int)(Math.random() * ((15-(nbrMageJoueurUn+nbrDragonJoueurUn)) + 1));
				nbrRenardJoueurUn = (int)(Math.random() * ((15-(nbrMageJoueurUn+nbrDragonJoueurUn+nbrLancierJoueurUn)) + 1));
				nbrArcherJoueurUn = (15-(nbrMageJoueurUn+nbrDragonJoueurUn+nbrLancierJoueurUn+nbrRenardJoueurUn));
				SelectionUniteFenetre.initialiserUnite(1);
			}
			if(joueurUn && !joueurDeux) {
				joueurDeux=true;	
				nbrMageJoueurDeux = (int)(Math.random() * (15 + 1));
				nbrDragonJoueurDeux = (int)(Math.random() * ((15-nbrMageJoueurDeux) + 1));
				nbrLancierJoueurDeux = (int)(Math.random() * ((15-(nbrMageJoueurDeux+nbrDragonJoueurDeux)) + 1));
				nbrRenardJoueurDeux = (int)(Math.random() * ((15-(nbrMageJoueurDeux+nbrDragonJoueurDeux+nbrLancierJoueurDeux)) + 1));
				nbrArcherJoueurDeux = (15-(nbrMageJoueurDeux+nbrDragonJoueurDeux+nbrLancierJoueurDeux+nbrRenardJoueurDeux));
				SelectionUniteFenetre.initialiserUnite(2);
			}	
			if(joueurUn && joueurDeux) {
				Main.estIA[1] = true;
				Main.estIA[0] = true;
				Main.setBloque(false);
				dispose();
			}
		}
	}
}
