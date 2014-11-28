


import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

//� compl�ter
public class PanneauVie extends JPanel {
	
	GrilleJeu jeu;
	
	int nbVieInit;
	
	public PanneauVie(GrilleJeu jeu) {
		this.jeu = jeu;	
		this.nbVieInit = jeu.getNbVies();
	}
}
