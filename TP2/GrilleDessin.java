import java.io.Serializable;

/**
 * Une grille repr�sentant un  dessin cach� �
 * l'aide de case colori� ou non.
 * 
 * Si une case est colori�e, elle fait partie du dessin.
 * 
 * Cette grille est carr�e 
 * 
 * @author pbelisle
 * @version A14
 *
 */

public class GrilleDessin implements Serializable {

	
	
	private boolean [][] grille;
	private String nom;
	
	/**
	 * Accessur du nom du dessin
	 * @return Le nom du dessin
	 */
	public String getNom() {
		return nom;
	}
	
	public GrilleDessin getDessin(){
		return this;
	}
	public InfoBloc[] getInfoBlocLigne(int ligne){
		InfoBloc[] monTableauLigne= new InfoBloc[grille.length];
		int compteur =1;
		
		for(int i=0;i<grille.length;i++){
			monTableauLigne[i]= new InfoBloc(1,compteur,grille.length-compteur);
		}
		return monTableauLigne;
	}
	public InfoBloc[] getInfoBlocColonne(int colonne){
		InfoBloc[] monTableauLigne= new InfoBloc[grille.length];
		int compteur =1;
		
		for(int i=0;i<grille.length;i++){
			monTableauLigne[i]= new InfoBloc(1,compteur,grille.length-compteur);
		}
		return monTableauLigne;
	}

	/**
	 * Mutateur du nom du dessin
	 * @param nom Le nouveau nom du dessin
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Cr�ation d'une grille carr�e repr�sentant le
	 * dessin.  SI une intersection est mise �
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
	 * La position (i,j) sera retourn�e
	 * @param i La position en ligne
	 * @param j La position en colonne
	 * @return Si la case es colori� ou non
	 */
	boolean estColorie(int i, int j){
		return grille[i][j];
	}
	
	/**
	 * Met la case de la grille � la position (i,j) dans l'�tat
	 * demand�
	 * @param i La position en ligne
	 * @param j La position en colonne
	 * @param etat L'�tat de la case apr�s l'appel
	 */
	void colorieCase(int i, int j, boolean etat){
		grille[i][j] = etat;
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
	 * Retourne si le dessin est validie
	 * 
	 * Le dessin est valide si aucune ligne ou colonne n'est vide � l'exception 
	 * des contours.
	 * 
	 * @return Si le dessin respecte les r�gles de validit�
	 */
	
	public boolean estValide() {	
		boolean colonneValide = validerDessin(true);
		boolean ligneValide = validerDessin(false);
		return colonneValide && ligneValide;
	}
	
   /**
    * Valider dessin contient  la valeur que retourne la fonction 
    *
	* La valeur vrai (colonne) ou fausse (ligne)
	* 
	* @author Fr�d�ric Leduc, Martin Satakem Mbougho, Hai-Dang Ly, Kelvin Vargas-Garcia
    */
	private boolean validerDessin(boolean verifierSelonColonne) {
		
		// On consid�re le dessin valide jusqu'� preuve du contraire.
		boolean dessinValide = true; 
		
		// Pour savoir quand est-ce que le dessin d�bute.
		boolean dessinDebute = false;
		
		// Pour savoir si la colonne contient une case noire ou non.
		boolean contientCase = false;
		
		// Pour savoir si le dessin doit �tre consid�r� comme termin�.
		boolean dessinTermine = false;
		
		int i= 0;
		// On parcours le tableau en i et on arrete � la fin ou si le dessin 
		//  est invalide
		while (i < getTaille() && dessinValide)
		{
			contientCase= false;
			
			int j= 0;
			// On parcours le tableau en j et on arrete lorque contient case 
			// est invalide
			while (j < grille[i].length && !contientCase)
			{
				//on se sert de la variable verifierSelonColonne pour aller
				//chercher la donn�e selon la ligne ou la colonne
				if (VerifierCaseSelon(verifierSelonColonne, grille, i, j))
				{
					contientCase = true;
				}
				
				++j;
			}
			
			// Si on trouve une case dans la colonne ou la ligne et que le dessin 
			// n'�tait pas d�j� commencer, on le d�bute.
			if (contientCase && !dessinDebute) 
			{
				dessinDebute = true;
			}
			
			// Si le dessin est d�but� et on rencontre une colonne ou une ligne vide,
			// on consid�re que le dessin est termin�.
			if (dessinDebute && !contientCase) 
			{
				dessinTermine = true;
				dessinDebute = false;
			}
			
			// Si le dessin est consid�r� termin� et qu'on rencontre une ligne 
			// ou une colonne non-vide, c'est automatiquement un dessin invalide.
			if (dessinTermine && contientCase)
			{
				dessinValide = false;
			}
			
			++i;
		}
		
		return dessinValide;
	}
	
	//fonction retourne la donn�e selon les lignes ou les colonnes
	private boolean VerifierCaseSelon(boolean v�rifierSelonColonne, 
										boolean[][] grille, int i, int j) {
		return (v�rifierSelonColonne) ? grille[i][j]: grille[j][i];
	}
	
}