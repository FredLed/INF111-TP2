import java.io.File;

import javax.swing.*;


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
	public GrilleJeu(GrilleDessin dessin) {
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
	
	public int getTaille() {
		return this.taille;
	}
	
	public void perdreVie() {
		--this.nbVies;
	}
	
	public boolean joueurMort() {
		return this.nbVies <= 0;
	}
	
	public void initialiserJeu() {
		for (int i= 0; i < this.taille; ++i) {
			tabBlocLignes[i] = new ClListe<InfoBloc>(dessin_orig.getInfoBlocs(i, false));
			tabBlocColonnes[i] = new ClListe<InfoBloc>(dessin_orig.getInfoBlocs(i, true));
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

	// PROGRAMME PRINCIPALE
	public static void main(String[] args) {
		GrilleDessin dessin=null;
		// Lecture du fichier
		JFrame j = new JFrame();
		JPanel p = new JPanel();
		
		j.setContentPane(p);
		File i = UtilitaireFichier.JFileChooser(p);
		i=UtilitaireFichier.accept(i);
		dessin = UtilitaireFichier.lire(i, p);
		
		GrilleJeu jeu = new GrilleJeu(dessin);
		
		if(dessin != null) {
			int noLigne = 0;
		//	dessin_orig=dessin.getDessin();
			if(dessin.estValide()) {
				do {
					UtilitaireAffichageConsole.afficherGrilleJeu(dessin);
					
					noLigne = UtilitaireValidation.lireInt("Veuillez entrer le numéro de la ligne : " ,
													1, dessin.getTaille());
					
					if (noLigne != 0) {
						int noColonne = UtilitaireValidation.lireInt("Veuillez entrer le numéro de la colonne : ",
														1, dessin.getTaille());
						
						if (noColonne != 0) {
							if (dessin.estColorie(noLigne, noColonne)) {
								jeu.ajusterJeu(noLigne, noColonne);
							}
							else {
								jeu.perdreVie();
							}
						}
					}
				} while (noLigne != 0 || jeu.joueurMort() || jeu.jeuEstSolutionne());		
			}
		}
		else {
				System.out.println("Le dessin n'est pas valide.");
		}
	}
}




