import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Classe qui contient le code pour les menus de la barres de menu.
 * 
 * menu fichier *ouvrir *sauvegarder sous *quitter
 * 
 * menu nouveau 5, 10, 15 //les tailles permises dans l'�nonc� du travail
 * 
 * 
 * Le constructeur doit recevoir la r�f�rence vers le panneau principal qui
 * contient la grille de jeu.
 * 
 * Le menu est un observateur du cadre pour le panneau de changement de mode
 * 
 * @author pbelisle
 * 
 */
public class BarreMenu extends JMenuBar implements Observer {

	/*test*/
	PanneauDessin panneuDessin;
	
	/**
	 * STRAT�GIE : On cr�e les �couteurs en sous classes locales ou en classe
	 * anonyme.
	 */

	// lisibilit� du code
	public static final int MIN_TAILLE = 5;
	public static final int MED_TAILLE = 10;
	public static final int MAX_TAILLE = 15;

	// Attributs ici (JMenu et JMenuItem et �couteurs)
	boolean sauvegardeEffectuee = true;
	JMenu menuFichier = new JMenu("Fichier");
	JMenuItem menuItemOuvrir = new JMenuItem("Ouvrir");
	JMenuItem menuItemSauvegarder = new JMenuItem("Sauvegarder sous");
	JMenuItem menuItemQuitter = new JMenuItem("Quitter");
	
	JMenu menuNouveau = new JMenu("Nouveau");
	JMenuItem menuItemDebutant = new JMenuItem(String.valueOf(MIN_TAILLE));
	JMenuItem menuItemInter = new JMenuItem(String.valueOf(MED_TAILLE));
	JMenuItem menuItemExpert = new JMenuItem(String.valueOf(MAX_TAILLE));

	/**
	 * Cr�e une barre de menu
	 * 
	 * @param panneau
	 *            L'instance du panneau qui remplace contentPane
	 */
	public BarreMenu(final PanneauPrincipal panneauPrincipal) {
		// à �crire
		boolean sauvegardeEffectuee = true;
		
	    this.menuFichier.add(this.menuNouveau);
	    this.menuFichier.add(this.menuItemOuvrir);
	    this.menuFichier.add(this.menuItemSauvegarder);
	    this.menuFichier.add(this.menuItemQuitter);
	    
	    this.menuNouveau.add(this.menuItemDebutant);
	    this.menuNouveau.add(this.menuItemInter);
	    this.menuNouveau.add(this.menuItemExpert);
	    
	    //�couteur pour l'enregistrement
	    this.menuFichier.add(this.menuItemSauvegarder);
	    
	    this.menuItemSauvegarder.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	    	  
	        String nom = panneauPrincipal.getDessin().getNom();
	        
	        int reponse = JOptionPane.showConfirmDialog(panneauPrincipal.getParent(), 
	          "Voulez-vous modifier le nom du dessin?");
	        if (reponse == 0)
	        {
	          nom = JOptionPane.showInputDialog(panneauPrincipal.getParent(), 
	            "Entrez un nom pour votre dessin svp.", nom);
	          if (nom != null) {
	        	  panneauPrincipal.getDessin().setNom(nom);
	          }
	        }
	         BarreMenu.this.sauvegardeEffectuee = UtilitaireFichier.sauvegarderGrilleDessin(panneauPrincipal.getDessin());
	      }
	    });
	    
	    //�couteur sur les menus   
	    this.menuItemOuvrir.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	    	//Si le joueur clique sur Fichier/Ouvrir,
	    	//on r�cup�re le dessin ouvert, on le met dans le layout
	    	//on l'affiche � l'�cran
	        GrilleDessin dessinTmp = UtilitaireFichier.recupererDessin();
	        if (dessinTmp != null)
	        {
	        	panneauPrincipal.initialiseJeu(dessinTmp);
	        	GrilleJeu grilleJeu = new GrilleJeu(dessinTmp);
	        	panneuDessin = new PanneauDessin(grilleJeu); 
	        	panneauPrincipal.add(panneuDessin,BorderLayout.CENTER);
	        	
	        }
	      }
	    });
	    
	  //Action a faire lorsque choisi de cr�er un grille de taille 5
	  ActionListener ecouteurMenuNouveau = new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	    	  JMenuItem menuItem = (JMenuItem)e.getSource();
	    	  int choix = Integer.parseInt(menuItem.getText());
	    	  
	    	  GrilleDessin grille = new GrilleDessin(choix,"NomDefaut");
	    	  GrilleJeu grilleJeu = new GrilleJeu(grille);
	    	  panneauPrincipal.initialiseJeu(grille);
	    	  PanneauDessin panneuDessin = new PanneauDessin(grilleJeu); 
	    	  panneauPrincipal.add(panneuDessin,BorderLayout.CENTER);
	    	  
	      }
	    };

	    this.menuItemDebutant.addActionListener(ecouteurMenuNouveau);
	    this.menuItemInter.addActionListener(ecouteurMenuNouveau);
	    this.menuItemExpert.addActionListener(ecouteurMenuNouveau);
	    
	    //Action a faire lorsque le bouton quitter est cliquer
	    ActionListener ecouteurQuitter = new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	    	  BarreMenu.this.quitter();
	      }
	    };
	    
	    //Ajout de l'�couteur sur le menu Quitter
	    this.menuItemQuitter.addActionListener(ecouteurQuitter);
	    
	    this.add(menuFichier);
	    this.add(menuNouveau);
	    this.barreJeu();
	}
	
	public void barreJeu()
	{
		this.menuNouveau.setVisible(false);
		this.menuItemSauvegarder.setVisible(false);
	}
	public void barreCreation()
	{
		this.menuNouveau.setVisible(true);
		this.menuItemSauvegarder.setVisible(true);
	}
	
	//Lorsque qu'on clique sur Quitter, on affiche un message d'au revoir
	//Et le syst�me se ferme.
	  private void quitter()
	  {
	    if (!this.sauvegardeEffectuee)
	    {
	      int reponse = JOptionPane.showConfirmDialog(this.getParent(), 
	        "Etes-vous certain de vouloir quitter sans sauvegarde ?");
	      if (reponse == 0)
	      {
	        JOptionPane.showMessageDialog(this.getParent(), 
	          "Merci et au plaisir de vour revoir");
	        System.exit(0);
	      }
	    }
	    else
	    {
	      JOptionPane.showMessageDialog(this.getParent(), 
	        "Merci et au plaisir de vour revoir");
	      System.exit(0);
	    }
	  }

	@Override
	public void update(Observable observateur, Object arg1) {
		// TODO Auto-generated method stub
		
		CadreDessinCache monDessinCache = (CadreDessinCache)observateur;
		int modeCourant = CadreDessinCache.getMode();
		
		if(modeCourant==0){
			
			barreCreation();
		}else{
			
			barreJeu();
		}
		 GrilleDessin dessin = UtilitaireFichier.recupererDessin();
		 PanneauPrincipal monPanneau = monDessinCache.getPanneauPrincipal();
	}
}
