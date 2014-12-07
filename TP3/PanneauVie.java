


import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//� compl�ter
public class PanneauVie extends JPanel implements Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	GrilleJeu jeu;
	
	int nbVieInit;
	JLabel labelVie; 
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public PanneauVie(GrilleJeu jeu) {
		this.jeu = jeu;	
		this.jeu.addObserver(this);
		this.nbVieInit = jeu.getNbVies();
		this.labelVie = new JLabel();
		this.labelVie.setText(String.valueOf(nbVieInit));
		this.add(labelVie);
	}
}
