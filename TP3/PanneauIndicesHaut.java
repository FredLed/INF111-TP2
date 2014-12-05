import java.awt.Color;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * Cette classe repr�sente les indices des colonnes du jeu de dessin cach�
 * (Piccross) et est offert dans le cadre du tp3 A14 inf111 (voir �nonc�)
 * 
 * @author pbelisle
 *
 */
public class PanneauIndicesHaut  extends JPanel{
	
	/**
	 * Constructeur qui re�oit la grille de jeu et affiche
	 * les indices sur les blocs de colonne
	 * 
	 * @param jeu Le jeu � conisid�rer qui contient les grilles
	 */
	public PanneauIndicesHaut(GrilleJeu jeu){
		
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
		 *                          GridLayout de la taille de dessin.
		 *                          
		 *                          Nous utilisons la collection Vector pour construire un tableau
		 *                          et obtenir toutes les InfoBlocs de toutes les colonnes.
		 *                          
		 *                          Ensuite, dans une boucle, nous affichons � chaque tour 
		 *                          l'indice du bloc appartenant au tableau de blocs ayant le
		 *                           plus grand nombre de blocs.   On commence par afficher
		 *                           les indices de clolonnes ceux qui un nombre de blocs ==
		 *                           max et on c�de�ment max � chaque tour.
		 *                           
		 *                           Cela permet d'aligner les
		 *                           indices en hauteur sur le bord du panneau.
		 *                           
		 *                           ex:    3                  2   
		 *                                    1    2     2    1
		 *                                    2    1     1    4 
		 */
		removeAll();
		
		//�vite pls appels aux accesseurs
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
		
		
		//Il n'y a pas plus de blocs possibles que la taille c'est s�r
		int max = taille;

		//Toutes les lignes
		for(int i = 0; i < taille; i++){

			//Toutes les colonnes
			for(int j = 0; j < taille; j++){

				//R�cup�rer l'infobloc de cette colonne
				InfoBloc [] info= tabColonnes.get(j);
				
				//�vite pls utilisations de l'attribut .length
				int nb =  info.length;

				//On affiche le bloc juste s'il y en a 
				if (nb == max){

					//Cr�ation du composant avec le nombre de cases du bloc
					etiq = new JLabel(String.valueOf(info[0].getNbCases()));

					//En rouge si le bloc a �t� compl�t�
					if(info[0].getNbCasesRestantes() <= 0)
						etiq.setForeground(Color.RED);

					//On met le composant dans le panneau
					add(etiq);
					
					//On enl�ve le bloc du tableau � l'aide de la classe Arrays
					if(nb > 1){

						InfoBloc[] tabTmp = Arrays.copyOfRange(info, 1, nb);

						//On d�cale les �l�ments de 1 � gauche
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
