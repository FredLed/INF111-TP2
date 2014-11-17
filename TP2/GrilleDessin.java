import java.io.Serializable;

/**
 * Une grille représentant un  dessin caché à
 * l'aide de case colorié ou non.
 * 
 * Si une case est coloriée, elle fait partie du dessin.
 * 
 * Cette grille est carrée 
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
	 * Création d'une grille carrée représentant le
	 * dessin.  SI une intersection est mise à
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
	 * La position (i,j) sera retournée
	 * @param i La position en ligne
	 * @param j La position en colonne
	 * @return Si la case es colorié ou non
	 */
	boolean estColorie(int i, int j){
		return grille[i][j];
	}
	
	/**
	 * Met la case de la grille à la position (i,j) dans l'état
	 * demandé
	 * @param i La position en ligne
	 * @param j La position en colonne
	 * @param etat L'état de la case après l'appel
	 */
	void colorieCase(int i, int j, boolean etat){
		grille[i][j] = etat;
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
	 * Retourne si le dessin est validie
	 * 
	 * Le dessin est valide si aucune ligne ou colonne n'est vide à l'exception 
	 * des contours.
	 * 
	 * @return Si le dessin respecte les règles de validité
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
	* @author Frédéric Leduc, Martin Satakem Mbougho, Hai-Dang Ly, Kelvin Vargas-Garcia
    */
	private boolean validerDessin(boolean verifierSelonColonne) {
		
		// On considère le dessin valide jusqu'à preuve du contraire.
		boolean dessinValide = true; 
		
		// Pour savoir quand est-ce que le dessin débute.
		boolean dessinDebute = false;
		
		// Pour savoir si la colonne contient une case noire ou non.
		boolean contientCase = false;
		
		// Pour savoir si le dessin doit être considéré comme terminé.
		boolean dessinTermine = false;
		
		int i= 0;
		// On parcours le tableau en i et on arrete à la fin ou si le dessin 
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
				//chercher la donnée selon la ligne ou la colonne
				if (VerifierCaseSelon(verifierSelonColonne, grille, i, j))
				{
					contientCase = true;
				}
				
				++j;
			}
			
			// Si on trouve une case dans la colonne ou la ligne et que le dessin 
			// n'était pas déjà commencer, on le débute.
			if (contientCase && !dessinDebute) 
			{
				dessinDebute = true;
			}
			
			// Si le dessin est débuté et on rencontre une colonne ou une ligne vide,
			// on considère que le dessin est terminé.
			if (dessinDebute && !contientCase) 
			{
				dessinTermine = true;
				dessinDebute = false;
			}
			
			// Si le dessin est considéré terminé et qu'on rencontre une ligne 
			// ou une colonne non-vide, c'est automatiquement un dessin invalide.
			if (dessinTermine && contientCase)
			{
				dessinValide = false;
			}
			
			++i;
		}
		
		return dessinValide;
	}
	
	//fonction retourne la donnée selon les lignes ou les colonnes
	private boolean VerifierCaseSelon(boolean vérifierSelonColonne, 
										boolean[][] grille, int i, int j) {
		return (vérifierSelonColonne) ? grille[i][j]: grille[j][i];
	}
	
}
