import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Observable;

/**
 * Une grille représentant un  dessin caché à
 * l'aide de case(s) coloriée(s) ou non.
 * 
 * Si une case est coloriée, elle fait partie du dessin.
 * 
 * Cette grille est carrée.
 * de 
 *  
 *  Des lignes et des colonnes peuvent être vides seulement si elles font 
 *  partie du contour.  Autrement dit, il n'y a pas de ligne(s) ou de colonne(s)
 *  vide(s) en plein milieu du dessin.  Aucune validation n'est effectuée mais 
 *  la méthode estValide permet d'obtenir si une grille est valide ou non.
 * 
 * Cette validation a été instauré pour des raisons académiques.  Le jeu peut
 * fonctionner sans cette contrainte.
 * 
 * @author pbelisle
 * @version A14
 *
 */
public class GrilleDessin extends Observable implements Serializable{

	//Tableau 2D qui contiendra les booléens
	//qui informent que la case est coloriée ou non
	private boolean [][] grille;
	
	//Id du dessin
	private String nom;
	
	/**
	 * Accessur du nom du dessin
	 * @return Le nom du dessin
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Mutateur du nom du dessin
	 * @param nom Le nouveau nom du dessin
	 */
	public void setNom(String nom) {
		this.nom = nom;
        setChanged();
        notifyObservers();
	}

	/**
	 * Création d'une grille carrée représentant le
	 * dessin.  Si une intersection est mise à
	 * true, c'est que la case est coloriée.
	 * 
	 * @param taille La taille de la grille
	 */
	public GrilleDessin(int taille, String nom){

		//On crée simplement la grille
		grille = new boolean[taille][taille];
		this.nom = nom;
	}
	
	/**
	 * Le contenu de la grille à la position (i,j) sera retournée
	 * @param i La position en ligne
	 * @param j La position en colonne
	 * @return Si la case es colorié ou non
	 */
	boolean estColorie(int i, int j){
		
		return grille[i][j];
	}
	
	/**
	 * Met la case de la grille à la position (i,j) dans l'état
	 * demandé.
	 * @param i La position en ligne
	 * @param j La position en colonne
	 * @param etat L'état de la case après l'appel
	 */
	void colorieCase(int i, int j, boolean etat){
		
		grille[i][j] = etat;
        setChanged();
        notifyObservers(new int[] {i, j});
	}
	
	/**
	 * Accesseur de la taille au moment de la création
	 * 
	 * @return La taille de la grille carrée
	 */
	int getTaille(){
		return grille.length;
	}
	
	/**
	 * Retourne s'il y  au moins une case coloriée sur une ligne 
	 * @param ligne L ligne à considérer dans la grille
	 */
	private boolean  aUneCaseColorieEnLigne(int ligne){
		
		/*
		 * Stratégie : On triche en utilisant un for qui s'arrêtera potentiellement
		 *                    avant la fin.  On ne respecte pas la règle d'un seul return
		 *                    pour accélérer l'exécution d'un tour de boucle
		 */
		
		//On parcourt toutes les colones à la recher d'une case à true
		for(int j = 0; j < grille.length; j++)
			
			//Il faut lire (grille[ligne][j] == true)
			if(grille[ligne][j])
				return true;
		
		return false;
	}
	
	/**
	 * Retourne s'il y  au moins une case coloriée sur la colonne 
	 *
	 */
	private boolean  aUneCaseColorieEnColonne(int colonne){
		
		/*
	     * Stratégie : On triche en utilisant un for qui s'arrêtera potentiellement
		 *                    avant la fin.  On ne respecte pas la règle d'un seul return
		 *                    pour accélérer l'exécution d'un tour de boucle
   	     */
		for(int i = 0; i < grille.length; i++)
			if(grille[i][colonne])
				return true;
		
		return false;
	}
	
	
	/**
	 * Retourne si le dessin est valide en ligne
	
	 * @return Si le dessin respecte les règles de validité en ligne
	 */
	private boolean dessinEstValideEnLigne() {
		
		/*
		 * Stratégie : Nous cherchons la première ligne qui contient au moins 
		 *                  une case coloriée(true) en partant du début (0) à l'aide de 
		 *                  la fonction locale.  On fait la 
		 *                  même chose en partant de la fin (deux indices).  
		 *                  Entre ces deux indices, il est interdit d'avoir une ligne
		 *                  avec aucune case coloriée.
		 *                     
		 * 
		 */
		//valeur de retour
		boolean valide = true;
		
		//initialisation des itérateurs
		int debut = 0;
		int fin = grille.length - 1;

		//On cherche la première ligne non vide en partant du début
		while(debut < grille.length && !aUneCaseColorieEnLigne(debut))
			debut++;

		//Une grille vide n'est pas un dessin valide
		if(debut == grille.length)
			valide = false;
		
		else{
			
			//On cherche la première ligne non vide en partant de la fin
			while(fin >= 0 && !aUneCaseColorieEnLigne(fin))
				fin --;

			//On regarde entre le début et la fin (non inclus)
			int i = debut;       
			while(i<fin  && aUneCaseColorieEnLigne(i))
					i++;
		}
				
		return valide;
	}
    
	
	/**
	 * Retourne si le dessin est valide en colonne
	 * 
	 * @return Si le dessin respecte les règles de validité en colonne
	 */
	private boolean dessinEstValideEnColonne() {
		
		/*
		 * Stratégie : Nous cherchons la première ligne qui contient au moins 
		 *                  une case coloriée(true) en partant du début (0) à l'aide de 
		 *                  la fonction locale. 
		 *                  On fait la même chose en partant de la fin (deux indices).  
		 *                  Entre ces deux indices, il est interdit d'avoir une colonne
		 *                  avec aucune case coloriée.
		 */
		//valeur de retour
		boolean valide = true;
		
		//initialisation des itérateurs
		int debut = 0;
		int fin = grille.length - 1;

		//On cherche la première colonne non vide en partant du début
		while(debut < grille.length && !aUneCaseColorieEnColonne(debut))
			debut++;

		//Une grille vide n'est pas un dessin valide
		if(debut == grille.length)
			valide = false;
		
		else{
			
			//On cherche la première colonne non vide en partant de la fin
			while(fin >= 0 && !aUneCaseColorieEnColonne(fin))
				fin --;

			//On regarde entre le début et la fin (non inclus)
			int i = debut;       
			while(i<fin  && aUneCaseColorieEnColonne(i))
					i++;
		}
				
		return valide;
	}
	/**
	 * Retourne si le dessin est validie
	 * 
	 * Le dessin est valide si aucune ligne ou colonne n'est vide à l'exception 
	 * des contours.
	 * 
	 * @return Si le dessin respecte les règles de validité
	 */
	
	public boolean estValide() {
		
		/*
		 * Stratégie : On utilise les fonctions locales qui doivent retourner true 
		 *                  toutes les deux pour dire qu'un dessin est valide
		 */
		return dessinEstValideEnLigne() && dessinEstValideEnColonne();
	}
		
	
	
}
