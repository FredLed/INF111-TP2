
public class GrilleJeu {
	
	//Les tableaux d’indices du jeu
    private ClListe<InfoBloc> tabBlocLignes= new ClListe<InfoBloc>();
	private ClListe<InfoBloc> tabBlocColonnes= new ClListe<InfoBloc>();
	
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
		//this.tabBlocLignes = new ClListe[dessin.getLigne()];
		//prendre le nombre de colonnes dans le dessin
		//this.tabBlocColonnes = new ClListe[dessin.getColonne()];
		
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
	
	/**
	 * Retourne un tableau de tous les blocs sur une ligne
	 * @param ligne La ligne voulue
	 * @return Un tableau des tous les blocs sur une ligne
	 */
	public InfoBloc[] getInfoBlocLigne(int ligne){
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
	}
	
	/**
	 * Retourne un tableau de tous les blocs sur une colonne
	 * @param ligne La colonne voulue
	 * @return Un tableau des tous les blocs sur une colonne
	 */
	public InfoBloc[] getInfoBlocColonne(int colonne){
		//retourne le nombre de colonnes du dessin
		InfoBloc[] monTableauColonne = new InfoBloc[taille];
		
		return monTableauColonne;
	}
	
/**	Début
	Pour toutes les lignes i de la grille
	Initialiser la liste vide à la position i (t_liste)
//C’est une boucle dans une boucle oui oui
		Pour les colonnes j de 0 à maximum taille – 1						
On doit compter le nombre de cases qui constitue un bloc (et oui une autre boucle) en commençant à la position contenue dans j
S'il y a au moins une case coloriée, on crée un info_bloc qu’on insère dans la liste après la position courante et on déplace 
l’itérateur de colonne du nombre de cases + 1 pour passer au bloc suivant potentiel
Fin 
Fin
*/
	public void initialiserJeu(){
		
		//initialise  la liste vide à la position i pour toutes les lignes
		for (int i=0;i <= dessin_orig.getTaille();i++){
			//tabBlocLignes = i;
		}
		
		//créer un infobloc pour toutes cases coloriées pour toutes les colonnes
		for (int j=0; j <= dessin_orig.getTaille() - 1;j++){
			if (dessin_orig.estValide()){
				//InfoBloc.InfoBloc();
			}
		
		}
	}
	
	public void ajusterJeu(int i, int j){
		
		if(tabBlocLignes.estVide()){
			
		}else{
			for(int z =0;z<i;z++){
				try {
		
					tabBlocLignes.avancer();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}




