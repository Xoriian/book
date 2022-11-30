package fr.wargame.modele.sauvegarde;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import fr.wargame.controleur.main.*;
import fr.wargame.modele.unites.*;

/**
 * Gestion de la sauvegarde d'une partie dans un fichier.
 */
public class Sauvegarde {

	/**
	 * Enregistre la partie dans un fichier.
	 * Récupère le tour depuis le main et crée le nom du fichier dans le format :
	 * "sauv" + tour + "_" + date + ".dat"
	 * La date est du type : JJ-MM-AAAA-hh-mm-ss
	 * Écrit la ArrayList des unités dans le fichier.
	 * Arrête le programme en cas d'erreur.
	 */
	public static void enregistrer() {
		String date = new java.text.SimpleDateFormat("dd-MM-yyyy-HH-mm-ss").format(new java.util.Date());
		String nom = "sauvegardes/sauv" + Main.getTour() + "_" + date + ".dat";
		File fichier = new File(nom);
		try {
			FileOutputStream fo = new FileOutputStream(fichier);
			ObjectOutputStream oo = new ObjectOutputStream(fo);
			oo.writeObject(Main.getUnites());
			oo.close();
			fo.close();
		} catch (IOException e) {
			System.exit(0);
		}
	}

	/**
	 * Récupère une sauvegarde dans un fichier.
	 * Récupère la ArrayList dans le fichier spécifié.
	 * Appelle Main.setUnites pour remplir la arrayList avec les unités.
	 * Extrait le numéro de tour du nom du fichier.
	 * Appelle Main.setTour pour mettre à jour le numéro de tour.
	 * Si le fichier n'est pas trouvé, ne fait rien.
	 * Si le fichier est illisible, quitte le programme.
	 * @param fichier le file a lire
	 */
	@SuppressWarnings("unchecked")
	public static void charger(File fichier) {
		try {
			FileInputStream fi = new FileInputStream(fichier);
			ObjectInputStream oi = new ObjectInputStream(fi);
			Main.setUnites((ArrayList<ArrayList<Unite>>) oi.readObject());
			oi.close();
			fi.close();
			Main.setTour(Integer.parseInt(fichier.getName().split("_")[0].replaceAll("sauv", "")));
		}
		catch (FileNotFoundException e) {} 
		catch (IOException | ClassNotFoundException e) {
			System.exit(0);
		}
	}

	/**
	 * Supprime le fichier de l'ordinateur.
	 * @param fichier le file à supprimer
	 */
	public static void supprimer(File fichier) {
		fichier.delete();
	}

	/**
	 * Renvoie la liste des fichiers de sauvegarde.
	 * Recherche dans le dossier de sauvegarde les fichiers dont le nom commence par "sauv".
	 * @return le tableau de file trouvés
	 */
	public static File[] recupererFichiers() {
		File dossier = new File("sauvegardes");
		dossier.mkdir();
		return new File("sauvegardes/").listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File f, String nom) {
				return nom.startsWith("sauv");
			}
		});
	}
}
