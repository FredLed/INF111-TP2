import java.awt.Color;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * Cette classe représente les indices des colonnes du jeu de dessin caché
 * (Piccross) et est offert dans le cadre du tp3 A14 inf111 (voir énoncé)
 * 
 * @author pbelisle
 *
 */
public class PanneauIndicesHaut  extends JPanel{
	
	/**
	 * Constructeur qui reçoit la grille de jeu et affiche
	 * les indices sur les blocs de colonne
	 * 
	 * @param jeu Le jeu à conisidérer qui contient les grilles
	 */
	public PanneauIndicesHaut(GrilleJeu jeu){
		
		//C'est une bonne pratique d'initialiser les composants dans un SP 	
		creerEtiqIndices(jeu);
	}
	
	   /**
     * Procédure locale qui crée les composants et les ajoute au panneau(this)
     * 
     * @param jeu (voir constructeur)
     */
	private void creerEtiqIndices(GrilleJeu jeu){
		
		/*
		 * STRATÉGIE : Nous utilisons de JLabel comme composant dans un 
		 *                          GridLayout de la taille de dessin.
		 *                          
		 *                          Nous utilisons la collection Vector pour construire un tableau
		 *                          et obtenir toutes les InfoBlocs de toutes les colonnes.
		 *                          
		 *                          Ensuite, dans une boucle, nous affichons à chaque tour 
		 *                          l'indice du bloc appartenant au tableau de blocs ayant le
		 *                           plus grand nombre de blocs.   On commence par afficher
		 *                           les indices de clolonnes ceux qui un nombre de blocs ==
		 *                           max et on cédeément max à chaque tour.
		 *                           
		 *                           Cela permet d'aligner les
		 *                           indices en hauteur sur le bord du panneau.
		 *                           
		 *                           ex:    3                  2   
		 *                                    1    2     2    1
		 *                                    2    1     1    4 
		 */
		removeAll();
		
		//Évite pls appels aux accesseurs
		int taille = jeu.getDessinMontre().getTaille();
		
		setLayout(new GridLayout(taille, taille));
	
		Vector<InfoBloc[] > tabColonnes = new Vector<InfoBloc[]>();

		JLabel etiq;
		
		//On affiche les espaces d'alignement
		for(int j =0; j <taille;j++)
			System.out.print( "   ");
		
		//On remplit le vector avec tous les tableaux d'IfoBlocs de toutes les colonnes
		for(int i = 0; i < taille;i++){
			tabColonnes.add(jeu.getInfoBlocColonne(i));
		}
		
		
		//Il n'y a pas plus de blocs possibles que la taille c'est sûr
		int max = taille;

		//Toutes les lignes
		for(int i = 0; i < taille; i++){

			//Toutes les colonnes
			for(int j = 0; j < taille; j++){

				//Récupérer l'infobloc de cette colonne
				InfoBloc [] info= tabColonnes.get(j);
				
				//évite pls utilisations de l'attribut .length
				int nb =  info.length;

				//On affiche le bloc juste s'il y en a 
				if (nb == max){

					//Création du composant avec le nombre de cases du bloc
					etiq = new JLabel(String.valueOf(info[0].getNbCases()));

					//En rouge si le bloc a été complété
					if(info[0].getNbCasesRestantes() <= 0)
						etiq.setForeground(Color.RED);

					//On met le composant dans le panneau
					add(etiq);
					
					//On enlève le bloc du tableau à l'aide de la classe Arrays
					if(nb > 1){

						InfoBloc[] tabTmp = Arrays.copyOfRange(info, 1, nb);

						//On décale les éléments de 1 à gauche
						tabColonnes.set(j, tabTmp);
					}
					
				}

				else{
					add(new JLabel(" "));
				}

			}
			
			max--;

		}
	}
	

}
