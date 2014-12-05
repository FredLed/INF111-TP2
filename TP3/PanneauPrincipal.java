import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

/**
 * Panneau qui remplace le contentPane du cadreJeu.  Il contient les 
 * composants pour prï¿½senter la grille de jeu, les indices de gauche et du
 * haut et le nombre de vies
 *  
 *  	
 * Au moment de la crï¿½ation de ce  panneau, le dessin n'a pas ï¿½tï¿½
 * rï¿½cupï¿½rï¿½ encore.  C'est lors de setDessin que les diffï¿½rents panneaux
 * sont crï¿½ï¿½s.
 *
 *   
 * @author pbelisle
 *
 */
public class PanneauPrincipal  extends JPanel {

	
	private GrilleJeu jeu;

	private PanneauDessin panneauDessin;
	
	private PanneauVie panneauVie;

	public PanneauPrincipal(){
	

	}

	/**
	 * Retourne le dessin original ou null si le jeu n'a pas ï¿½tï¿½ initialisï¿½e
	 * 
	 * @return Le dessin original ou null
	 */
	public GrilleDessin getDessin() {
		if(jeu != null)
			return  jeu.getDessinOrig();
		return null;
	
	}

	public void  initialiseJeu(GrilleDessin dessin){
		
		//Important de ne pas mettre dans initialiserComposants
		//juste une fois dans le constructeur
		jeu = new GrilleJeu(dessin);
		
	
		initialiserComposants();
	}

	private void initialiserComposants() {

		//On s'assure que le panneau est vide.
		this.removeAll();

		//Le panneau de jeu
		this.add(new PanneauDessin(jeu),
			BorderLayout.EAST);
		
		//Le panneau de vie ï¿½ droite
		PanneauVie panneauVie = new PanneauVie(jeu);
		this.add(panneauVie,
				BorderLayout.EAST);
		
		panneauVie.setBackground(Color.yellow);
		panneauVie.setSize(100, 50);
		
		//On actualise le GUI
		validate();
		repaint();
	}
	
}