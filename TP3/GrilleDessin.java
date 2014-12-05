import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Observable;

/**
 * Une grille repr�sentant un  dessin cach� �
 * l'aide de case(s) colori�e(s) ou non.
 * 
 * Si une case est colori�e, elle fait partie du dessin.
 * 
 * Cette grille est carr�e.
 * de 
 *  
 *  Des lignes et des colonnes peuvent �tre vides seulement si elles font 
 *  partie du contour.  Autrement dit, il n'y a pas de ligne(s) ou de colonne(s)
 *  vide(s) en plein milieu du dessin.  Aucune validation n'est effectu�e mais 
 *  la m�thode estValide permet d'obtenir si une grille est valide ou non.
 * 
 * Cette validation a �t� instaur� pour des raisons acad�miques.  Le jeu peut
 * fonctionner sans cette contrainte.
 * 
 * @author pbelisle
 * @version A14
 *
 */
public class GrilleDessin extends Observable implements Serializable{

	//Tableau 2D qui contiendra les bool�ens
	//qui informent que la case est colori�e ou non
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
	 * Cr�ation d'une grille carr�e repr�sentant le
	 * dessin.  Si une intersection est mise �
	 * true, c'est que la case est colori�e.
	 * 
	 * @param taille La taille de la grille
	 */
	public GrilleDessin(int taille, String nom){

		//On cr�e simplement la grille
		grille = new boolean[taille][taille];
		this.nom = nom;
	}
	
	/**
	 * Le contenu de la grille � la position (i,j) sera retourn�e
	 * @param i La position en ligne
	 * @param j La position en colonne
	 * @return Si la case es colori� ou non
	 */
	boolean estColorie(int i, int j){
		
		return grille[i][j];
	}
	
	/**
	 * Met la case de la grille � la position (i,j) dans l'�tat
	 * demand�.
	 * @param i La position en ligne
	 * @param j La position en colonne
	 * @param etat L'�tat de la case apr�s l'appel
	 */
	void colorieCase(int i, int j, boolean etat){
		
		grille[i][j] = etat;
        setChanged();
        notifyObservers(new int[] {i, j});
	}
	
	/**
	 * Accesseur de la taille au moment de la cr�ation
	 * 
	 * @return La taille de la grille carr�e
	 */
	int getTaille(){
		return grille.length;
	}
	
	/**
	 * Retourne s'il y  au moins une case colori�e sur une ligne 
	 * @param ligne L ligne � consid�rer dans la grille
	 */
	private boolean  aUneCaseColorieEnLigne(int ligne){
		
		/*
		 * Strat�gie : On triche en utilisant un for qui s'arr�tera potentiellement
		 *                    avant la fin.  On ne respecte pas la r�gle d'un seul return
		 *                    pour acc�l�rer l'ex�cution d'un tour de boucle
		 */
		
		//On parcourt toutes les colones � la recher d'une case � true
		for(int j = 0; j < grille.length; j++)
			
			//Il faut lire (grille[ligne][j] == true)
			if(grille[ligne][j])
				return true;
		
		return false;
	}
	
	/**
	 * Retourne s'il y  au moins une case colori�e sur la colonne 
	 *
	 */
	private boolean  aUneCaseColorieEnColonne(int colonne){
		
		/*
	     * Strat�gie : On triche en utilisant un for qui s'arr�tera potentiellement
		 *                    avant la fin.  On ne respecte pas la r�gle d'un seul return
		 *                    pour acc�l�rer l'ex�cution d'un tour de boucle
   	     */
		for(int i = 0; i < grille.length; i++)
			if(grille[i][colonne])
				return true;
		
		return false;
	}
	
	
	/**
	 * Retourne si le dessin est valide en ligne
	
	 * @return Si le dessin respecte les r�gles de validit� en ligne
	 */
	private boolean dessinEstValideEnLigne() {
		
		/*
		 * Strat�gie : Nous cherchons la premi�re ligne qui contient au moins 
		 *                  une case colori�e(true) en partant du d�but (0) � l'aide de 
		 *                  la fonction locale.  On fait la 
		 *                  m�me chose en partant de la fin (deux indices).  
		 *                  Entre ces deux indices, il est interdit d'avoir une ligne
		 *                  avec aucune case colori�e.
		 *                     
		 * 
		 */
		//valeur de retour
		boolean valide = true;
		
		//initialisation des it�rateurs
		int debut = 0;
		int fin = grille.length - 1;

		//On cherche la premi�re ligne non vide en partant du d�but
		while(debut < grille.length && !aUneCaseColorieEnLigne(debut))
			debut++;

		//Une grille vide n'est pas un dessin valide
		if(debut == grille.length)
			valide = false;
		
		else{
			
			//On cherche la premi�re ligne non vide en partant de la fin
			while(fin >= 0 && !aUneCaseColorieEnLigne(fin))
				fin --;

			//On regarde entre le d�but et la fin (non inclus)
			int i = debut;       
			while(i<fin  && aUneCaseColorieEnLigne(i))
					i++;
		}
				
		return valide;
	}
    
	
	/**
	 * Retourne si le dessin est valide en colonne
	 * 
	 * @return Si le dessin respecte les r�gles de validit� en colonne
	 */
	private boolean dessinEstValideEnColonne() {
		
		/*
		 * Strat�gie : Nous cherchons la premi�re ligne qui contient au moins 
		 *                  une case colori�e(true) en partant du d�but (0) � l'aide de 
		 *                  la fonction locale. 
		 *                  On fait la m�me chose en partant de la fin (deux indices).  
		 *                  Entre ces deux indices, il est interdit d'avoir une colonne
		 *                  avec aucune case colori�e.
		 */
		//valeur de retour
		boolean valide = true;
		
		//initialisation des it�rateurs
		int debut = 0;
		int fin = grille.length - 1;

		//On cherche la premi�re colonne non vide en partant du d�but
		while(debut < grille.length && !aUneCaseColorieEnColonne(debut))
			debut++;

		//Une grille vide n'est pas un dessin valide
		if(debut == grille.length)
			valide = false;
		
		else{
			
			//On cherche la premi�re colonne non vide en partant de la fin
			while(fin >= 0 && !aUneCaseColorieEnColonne(fin))
				fin --;

			//On regarde entre le d�but et la fin (non inclus)
			int i = debut;       
			while(i<fin  && aUneCaseColorieEnColonne(i))
					i++;
		}
				
		return valide;
	}
	/**
	 * Retourne si le dessin est validie
	 * 
	 * Le dessin est valide si aucune ligne ou colonne n'est vide � l'exception 
	 * des contours.
	 * 
	 * @return Si le dessin respecte les r�gles de validit�
	 */
	
	public boolean estValide() {
		
		/*
		 * Strat�gie : On utilise les fonctions locales qui doivent retourner true 
		 *                  toutes les deux pour dire qu'un dessin est valide
		 */
		return dessinEstValideEnLigne() && dessinEstValideEnColonne();
	}
		
	
	
}
