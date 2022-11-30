package fr.wargame.vue.fenetrejeu;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.sound.sampled.*;
import javax.swing.*;

import fr.wargame.controleur.gestionclic.*;
import fr.wargame.vue.choixsauvegarde.*;

/**
 * Fenêtre contenant le plateau graphique et la fenêtre de stats
 */
public class FenetreJeu extends JFrame {
	private Plateau plateauG;
	private FenStats fenStats;
	public Clic clic = new Clic();
	private Clip musique;

	/**
	 * @param estTuto Savoir si c'est le tutoriel
	 */
	public FenetreJeu(boolean estTuto) {
		super("Spirits");

		// Crédits musique : Darren Korb - Hades - Original Soundtrack - 02 The House of Hades

		try {
			AudioInputStream flux =  AudioSystem.getAudioInputStream(this.getClass().getResource("/audio/musique.wav")); 
			musique = AudioSystem.getClip();
			musique.open(flux);
		} 
		catch (Exception error) {}

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent event) {
				if (!estTuto) {
					try {
						musique.loop(Clip.LOOP_CONTINUOUSLY);
					} catch (Exception error) {}
				}
			}
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					musique.stop();
				} catch (Exception error) {}
				dispose();
				if(!estTuto)
					new FenSauvegarde();
			}
		});
		this.plateauG = new Plateau(clic);
		this.fenStats = new FenStats(clic);
		this.add(plateauG.sc);
		this.add(fenStats);
		this.setSize(1295,755);
		this.setLayout(null);
		this.setVisible(true);
	}

	/**
	 * @return le plateau graphique
	 */
	public Plateau getPlateauG()  { return this.plateauG; }

	/**
	 * @return la fenêtre de stats
	 */
	public FenStats getFenStats()  { return this.fenStats; }
}
