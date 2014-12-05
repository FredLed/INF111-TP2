import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * Cette classe repr�sente les indices des lignes du jeu de dessin cach�
 * (Piccross) et est offert dans le cadre du tp3 A14 inf111 (voir �nonc�)
 * 
 * @author pbelisle
 *
 */
public class PanneauIndicesGauche extends JPanel{
	
	
	/**
	 * Constructeur qui re�oit la grille de jeu et affiche
	 * les indices sur les blocs de ligne
	 * 
	 * @param jeu Le jeu � conisid�rer qui contient les grilles
	 */
    public PanneauIndicesGauche(GrilleJeu jeu){
	
    	
    	//C'est une bonne pratique d'initialiser les composants dans un SP 	
    	creerEtiqIndices(jeu);
    	
    }

    /**
     * Proc�dure locale qui cr�e les composants et les ajoute au panneau(this)
     * 
     * @param jeu (voir constructeur)
     */
private void creerEtiqIndices(GrilleJeu jeu){
		
	/*
	 * STRAT�GIE : Nous utilisons de JLabel comme composant dans un 
	 *                          GridLayout du double de la taille de dessin en largeur.
	 *                          Cela permet d'espace et d'aligner les indices dans le 
	 *                          panneau.
	 */
		removeAll();
		
		//�vite pls appels aux accesseurs
		int taille = jeu.getDessinMontre().getTaille();
		
		//Le double de la grille en largeur
		setLayout(new GridLayout(taille, taille*2));
	
		//Sert � cr�er les composants qui servent � montrer les indices
		JLabel etiq;
		
		//Il faut r�cup�rer le tableau d'InfoBloc en provenance du jeu
		InfoBloc[] tabBlocLignes;
	
		//La boucle pour chaque ligne
		for(int i = 0; i < taille; i++){
			
				//R�cup�rer l'infobloc de cette ligne
				tabBlocLignes = jeu.getInfoBlocLigne(i);
			
				// Pour aligner les composants(Devrait �tre un SP si utilis� aileurs)
				for(int j =0; j <taille- tabBlocLignes.length - 1;j++){
					add(new JLabel (" "));
					add(new JLabel (" "));
				}
				
				//La boucle pour les colonnes
				for(int j =0; j <tabBlocLignes.length;j++){
					
					//Cr� le composant avec le nombre case du bloc 
					etiq = new JLabel(String.valueOf(tabBlocLignes[j].getNbCases()));			

					//Si le bloc est compl�t�, nbCasesRestance d'InfoBloc == 0
					//On le change de couleur
					if(tabBlocLignes[j].getNbCasesRestantes() <= 0)
						etiq.setForeground(Color.RED);

					//Ajout au panneau avec un espace
					add(etiq);
					add(new JLabel (" "));
					
					//mise � jour de l'�cran
					validate();
				}
					
			}			
	}
}
