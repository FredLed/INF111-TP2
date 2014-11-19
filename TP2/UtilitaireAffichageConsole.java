import java.util.Arrays;
import java.util.Vector;

/**
 * Permet d'afficher le jeu ou le dessin en mode console.
 * 
 * Ce code est fourni dans le cadre d'un projet  int111 qui 
 * sera converti en GUI.  Le code est minimalement commenté
 * et n'est pas optimisé (répétition de code évitable).
 * 
 * @author pbelisle
 * @version A14
 *
 */
public class UtilitaireAffichageConsole {

	public static  final int DIMENSION_CASE = 4;


	/**
	 * Affiche la grille de jeu et les indicateurs de nombre de cases coloriées
	 * 
	 * @param grilleJeu
	 */
	public static void afficherGrilleJeu(GrilleDessin grille){

		/*
		 * STRATÉGIE : On récupère dans un tableau 
		 */
		int taille;
	
		//Sert à obtenir les données des différentes instances
		InfoBloc[] tabBlocLignes; 
		GrilleDessin dessin = grille.getDessin();
		taille = dessin.getTaille();
		
        //Procédure locale qui affiche les indices des colonnes
		afficherColonnes(grille, taille);
		
		//On affiche les espace d'alignement
		for(int j =0; j <taille;j++)
			System.out.print("   ");
		
		
		//Affichage de la même première ligne
		for(int k= 0; k < taille * DIMENSION_CASE;  k++)
			System.out.print("-");
		
		System.out.println();

		//On affiche toutes les indices sur les lignes
		for(int ligne =0; ligne < taille; ligne++){

			tabBlocLignes = grille.getInfoBlocLigne(ligne);
			
			for(int j =0; j <taille-tabBlocLignes.length;j++)
				System.out.print("   ");

			for(int j =0; j < tabBlocLignes.length;j++)
				
				
				//On met la valeur négatif si le nombre de cases restants est 0
				if(tabBlocLignes[j].getNbCasesRestantes() > 0)
			    	System.out.print(" " + tabBlocLignes[j].getNbCases() +  " ");
				else
					System.out.print(" " + -tabBlocLignes[j].getNbCases() +  " ");

			//Gestion d'alignement des colonnes
			for(int j = 0; j < taille; j++){
				System.out.print("|");
				String s = dessin.estColorie(ligne, j) ?"x":" ";

					System.out.print(" "+ s);
			}
			System.out.println();

			
			//On affiche les espace d'alignement
			for(int j =0; j <taille;j++)
				System.out.print("   ");
			
			
			//Affichage de la même première ligne
			for(int k= 0; k < taille * DIMENSION_CASE;  k++)
				System.out.print("-");


			System.out.print("|");


			System.out.println();

		}



	}

	private static void afficherColonnes(GrilleDessin grille, int taille){

		/*
		 * Termes :  Un bloc a pls cases et pls blocs par colonne
		 * 
		 * STRATÉGIE : On se crée un vecteur dont chaque case
		 *                         contiendra un tableau décrivant les blocs
		 *                         d'un colonne.  
		 * 
		 * 
		 *                          On trouve la colonne ayant le plus grand nombre de blocs
		 *                          et on affiche le nombre de blocs de toutes les colonnes
		 *                          ayant le même nombre de blocs.  On retire ce nombre du
		 *                          tableau.
		 *                          
		 *                          
		 *                          
		 */
		Vector<InfoBloc[]> tabColonnes = new Vector<InfoBloc[]>();

		
		//On affiche les espace d'alignement
		for(int j =0; j <taille;j++)
			System.out.print( "   ");
		
		//On remplit le tableau
		for(int i = 0; i < taille;i++){
			tabColonnes.add(grille.getInfoBlocColonne(i));
		}
		
		int max = taille;

		//Pour chaque lignes, il faut faire un enter après avoir afficher le nombre de
		//blocs sur une même ligne
		for(int ligne = 0; ligne < taille; ligne++){


			//On affiche les plus grands en premier
			for(int i = 0; i < taille; i++){

				InfoBloc[] info = tabColonnes.get(i);

				int nb = info.length;

				
				if (nb == max){
					if(info[0].getNbCasesRestantes() > 0)
				    	System.out.print(" " + info[0].getNbCases() +  " ");
					else
						System.out.print(" " + -info[0].getNbCases() +  " ");

					if(info.length > 1){
						InfoBloc[] tabTmp = Arrays.copyOfRange(info, 1, info.length);
						//On décale les éléments de 1 à gauche
						tabColonnes.set(i, tabTmp);
					}

					else{
						tabColonnes.set(i, null);
					}
					
				}

				else
					System.out.print("   ");			 
			}
			max--;
			System.out.println();
			//On affiche les espace d'alignement
			for(int j =0; j <taille;j++)
				System.out.print("   ");
			
		}
		
		System.out.println();
	}


}
