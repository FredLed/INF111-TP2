import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Représente la fenétre qui contient le panneau principal et la barre de menu
 * d'un jeu de dessin caché (voir énoncé inf111 A14)
 * 
 * S'occupe également de la gestion du mode du jeu 
 * MODE_JOUE ou MODE_CREATION
 * 
 * @author pbelisle
 *
 */
public class CadreDessinCache extends Observable implements Runnable {
	
    //Les modes possibles de l'application
	public static final int MODE_JOUE = 0;
	public static final int MODE_CREATION = 1;

	//On compose é l'aide d'un JFrame 
	private JFrame cadreJeu;
	private  PanneauPrincipal panneauPrincipal;
	
	//Accessible par tous en tout temps avec getMode
	private static int mode;
	
	//La barre de menu dépend du mode et a besoi du panneauPrincipal
	private BarreMenu barre ;
	
    /**
     * Constructeur par défaut de l'application qui
     * configure le titre seulement.   La méthode run()
     * s'occupe de créer le panneau principal et la barre de menu
     */
	public CadreDessinCache(){		
		cadreJeu = new JFrame("Jeu de dessin caché");
	}

	
	
	@Override
	public void run() {
		
		//Mode par défaut (arbitraire)
		mode = MODE_JOUE;
		
		//Le panneau contenant la grille de jeu
		panneauPrincipal = new PanneauPrincipal();
	
		//L'instance de la barre dem enu 
		barre = new BarreMenu(panneauPrincipal);

		this.addObserver(barre);
		//configuration de base (plein écran, fermeture sur X)
		cadreJeu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cadreJeu.setExtendedState(JFrame.MAXIMIZED_BOTH);

		//Le panneau principal est mis au centre 
		cadreJeu.getContentPane().add(panneauPrincipal, BorderLayout.CENTER);
		panneauPrincipal.setBackground(Color.black);
		
		//le panneau de mode en haut
		cadreJeu.getContentPane().add(new PanneauMode(),
				BorderLayout.PAGE_START);
	
		cadreJeu.setJMenuBar(barre);
		cadreJeu.setVisible(true);
		
	}

	/**
	 * Accesseur du panneau principal
	 * @return Le panneauprincipal du cadre
	 */
	public PanneauPrincipal getPanneauPrincipal() {
		return panneauPrincipal;
	}

	
	/*
	 * Accesseur de l'instance static de classe
	 * Un seul pour toute l'application
	 */
	public static int getMode(){
		return mode;		
	}
	
	/**
	 * Panneau qui retient le mode choisi par l'utilisateur.  Il contient
	 * deux composants qui permet de sélectionner le mode.
	 * 
	 * @author pbelisle
	 *
	 */
	private  class PanneauMode extends JPanel{

	    JRadioButton btnJeu = new JRadioButton("Mode Jeu");
	    JRadioButton btnCreation = new JRadioButton("Mode Création");

		ButtonGroup group = new ButtonGroup();
		
		//évite la répétition de code
		private void setMode(int nouveauMode) {
			if (mode != nouveauMode) {
				mode = nouveauMode;
				if(nouveauMode == MODE_CREATION)
				{
					barre.barreCreation();
					//barre.removeAll();
					barre.repaint();
				}
				else
				{
					// On demande à l'utilisateur s'il veut saauvegarder // TODO
					//okAnnuler("Voulez-vous enregistrer avant de quitter le mode céation?");
					barre.barreJeu();
					//barre.removeAll();
					barre.repaint();
				}
				
				setChanged();
				notifyObservers();
			}
		}
		
		
		/**
		 * Constructeur 
		 * @param cadreDessin
		 */
		
		public PanneauMode(){
		
            //Les boutons sont dans un groupe pour étre mutuellement exclusifs
			group.add(btnJeu);
			group.add(btnCreation);


			//mode par défaut
			btnJeu.setSelected(mode == MODE_JOUE);
			
			add(btnJeu);
			add(btnCreation);
			

			btnJeu.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					//utilisation de la procédure locale
					setMode(MODE_JOUE);
				}
			});

			btnCreation.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					//utilisation de la procédure locale
					setMode(MODE_CREATION);
				}
			});			
		}
	}
}
