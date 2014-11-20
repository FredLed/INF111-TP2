
public class GrilleJeu {
	
	//Les tableaux d’indices du jeu
    private ClListe<InfoBloc>[] tabBlocLignes;
	private ClListe<InfoBloc>[] tabBlocColonnes;
	
	private GrilleDessin dessin_orig;
	private GrilleDessin dessin_cache;
	private int nbVies;

	//mettre taille en private pour éviter pls appels à l'accesseur
	private int taille;

	//constructeur
	public GrilleJeu(GrilleDessin dessin){
		//prendre la taille du dessin
		this.taille = dessin.getTaille();
		
		//prendre le nombre de lignes dans le dessin
		this.tabBlocColonnes = new ClListe[this.taille];
		
		//prendre le nombre de colonnes dans le dessin
		this.tabBlocLignes = new ClListe[this.taille];
		
		this.initialiserJeu();
		
		//si la taille est 5X5
		if(taille == 5)
			//on donne 3 vies
			this.nbVies = 3;
		
		//si la taille est 10X10
		else if(taille == 10)
			//on donne 4 vies
			this.nbVies = 4;
		
		//si la taille est 15X15
		else if(taille == 15)
			//on donne 5 vies
			this.nbVies = 5;
		
	}
	
	// ANCIENNE VERSION

	/**
	 * Retourne un tableau de tous les blocs sur une ligne
	 * @param ligne La ligne voulue
	 * @return Un tableau des tous les blocs sur une ligne
	 */
	
	

	// Voir getInfoBlocs() dans GrilleDessin.java
	/*public InfoBloc[] getInfoBlocLigne(int ligne){ 
		//retourne le nombre de lignes du dessin
		InfoBloc[] monTableauLigne = new InfoBloc[tabBlocLignes.getNbElements()];
		for(int i =0;i<monTableauLigne.length;i++){
			int debut;
			int NbCases;
			int NbRestant;
			
			for(int j=0;j<monTableauLigne.length;j++){
				
			}
			
		}
		return monTableauLigne;
	}*/
	
	/**
	 * Retourne un tableau de tous les blocs sur une colonne
	 * @param ligne La colonne voulue
	 * @return Un tableau des tous les blocs sur une colonne
	 */
	/* public InfoBloc[] getInfoBlocColonne(int colonne){
		//retourne le nombre de colonnes du dessin
		InfoBloc[] monTableauColonne = new InfoBloc[taille];
		
		return monTableauColonne;
	} */
	
	
	// Nouvelle version
	public void initialiserJeu() {
		for (int i= 0; i < this.taille; ++i) {
			tabBlocLignes[i] = dessin_orig.getInfoBlocs(i, false);
			tabBlocColonnes[i] = dessin_orig.getInfoBlocs(i, true);
		}
	}
	
	public boolean listeBlocsEstVide(ClListe<InfoBloc> listeBlocs) {
		// On considère que la liste est vide jusqu'à preuve du contraire.
		boolean estVide= true;
		if (listeBlocs != null) {
			if (!listeBlocs.estVide()) {
				try {
					listeBlocs.setPositionCouranteDebut();
			
					for (int i= 0; i < listeBlocs.getNbElements(); ++i) {
						if (listeBlocs.getElement().getNbCasesRestantes() != 0) 
							estVide= false;
						
						listeBlocs.avancer();
					}
				}
				catch(Exception e) { }
			}
		}
		
		return estVide;
	}
	
	public boolean jeuEstSolutionne() {
		// On considère que les listes sont vides jusqu'à preuve du contraire.
		boolean lignesVide = true;
		boolean colonnesVide= true;
		
		for (ClListe<InfoBloc> listeLigne : tabBlocLignes) {
			if (!listeBlocsEstVide(listeLigne)) {
				lignesVide= false;
			}
		}
		
		for (ClListe<InfoBloc> listeColonne : tabBlocColonnes) {
			if (!listeBlocsEstVide(listeColonne)) {
				colonnesVide= false;
			}
		}
		
		return lignesVide && colonnesVide;
	}
	
	// Nouvelle version
	public void ajusterJeu(int i, int j) {
		try {
			tabBlocLignes[i].setPositionCouranteDebut();
			tabBlocColonnes[j].setPositionCouranteDebut();
			InfoBloc blocCourant;
			
			while ((blocCourant= tabBlocLignes[i].getElement()).getDebut() != j) {
				
				if (j > blocCourant.getDebut() && j < blocCourant.getDebut() + blocCourant.getNbCases()) {
					blocCourant.setNbCasesRestantes(blocCourant.getNbCasesRestantes()-1);
					tabBlocLignes[i].setElement(blocCourant);
				}
				
				tabBlocLignes[i].avancer();
			}
			
			while ((blocCourant= tabBlocColonnes[j].getElement()).getDebut() != i) {
				
				if (i > blocCourant.getDebut() && i < blocCourant.getDebut() + blocCourant.getNbCases()) {
					blocCourant.setNbCasesRestantes(blocCourant.getNbCasesRestantes()-1);
					tabBlocLignes[j].setElement(blocCourant);
				}
				
				tabBlocLignes[j].avancer();
			}
		}
		catch (Exception e) { }
		
	}
	
	/*public void ajusterJeu(int i, int j){
		
		if(tabBlocLignes.estVide()){
			//Mettre une message
		}else{
			try {
				tabBlocLignes.setPositionCouranteDebut();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for(int z =0;z<i;z++){
				try {
					tabBlocLignes.avancer();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}*/
	
	
	// PROGRAMME PRINCIPALE
	public static void main(String[] args) {
		GrilleDessin dessin;
		
		// Lecture du fichier
		
	}
}




