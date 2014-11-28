import java.awt.Rectangle;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * Cette classe est la repr�sentation 
 * en m�moire de la GrilleDessin du jeu de dessin cach� (Picross)
 * 
 * Elle calcule et retient la position et  le nombre de chaque bloc colori�es en 
 * ligne et en colonne de la GrilleDessin re�u au constructeur et en permet
 * aussi l'ajustement.
 * 
 * @author pbelisle
 * @version A14
 *
 */
public class GrilleJeu {
	
	
	/**
	 * Strat�gies : On utilise un Vector comme une file pour retenir 
	 *                     le nombre de blocs colori�s  du dessin pour chaque ligne 
	 *                     et chaque colonne.
	 *                      
	 *                     Un tableau de listes pour les lignes et un pour les colonnes.
	 *                        
	 *                     Chaque liste peut �tre de taille diff�rente (voir r�gles du jeu)
	 *                      et contient des InfoBloc
	 *                      
	 *                     On pr�sume que le dessin  fourni au constructeur est 
	 *                     valide alors aucune validation n'est effectu�e
	 */
	private Vector<InfoBloc> [] tabBlocLignes;
	private Vector<InfoBloc> [] tabBlocColonnes;
	
	//La grille qui contient le dessin
	private GrilleDessin dessinOrig;
	
	//grille vide qui est montr�e et ajust�e durant le jeu
	private GrilleDessin dessinMontre;
	
	//�vite pls appels � l'accesseur
	private int taille;
	
	private int nbVies;
	
	/**
	 * Cr�e une repr�sentation m�moire du dessin re�u
	 * tel qu'elle sera pr�sent�e � l'utilisateur du jeu.
	 *  
	 * @param dessin Le dessin � consid�rer
	 */
	public GrilleJeu(GrilleDessin dessin){
	
		
		//�vite pls appels � l'accesseur
		 taille = dessin.getTaille();
			
		//le dessin montr� est vide au d�part
		dessinMontre = new GrilleDessin(taille, dessin.getNom());
		
		//retient le dessin re�u
		this.dessinOrig = dessin;
		
		//arbitraire
		nbVies = taille/2;
		
		initialiserJeu();
	}
	
	/**
	 * Parcours le dessin pour cr�er les diff�rentes listes
	 * avec le nombre de cases successives colori�es
	 */
	public void initialiserJeu(){
		
		//Tableaux de listes de blocs
		tabBlocLignes = new Vector [taille];
		tabBlocColonnes = new Vector  [taille];
		
		//On compte les lignes d'abord
		compterLignes();
	       
		
		//Ensuite les colonnes
		compterColonnes();
		
	}

	
	/**
	 *Fonction priv�e pour compter le nombre de cases cons�cutives qui sont
	 *colori�es sur une ligne
	 */
	private int nbCasesColorieesConsecutivesEnLigne(GrilleDessin dessin, 
			int i, int j){
		
		/*
		 * STRAT�GIE : La valeur de i reste inchang�e et j varie de sa position
		 *                       jusqu'� la taille ou si un bloc est non colori�. 
		 *                       Finalement retourne le nombre de blocs cons�cutifs
		 *                       qui peut �tre 0.
		 */
		
		//Sera vrai lorsque le nombre de cases est compt�
		boolean compte = false;
		
		//valeur de retour
		int nb = 0;
		
		//Deux conditions d'arr�t
		while(j < dessin.getTaille() && !compte){
			
			if(dessin.estColorie(i, j))
				nb++;
			else
				compte = true;
			j++;
		}
		
		return nb;
	}
	
	/**
	 *Fonction priv�e pour compter le nombre de cases cons�cutives qui sont
	 *colori�es sur une colonne.  
	 */
	private int nbCasesColorieesConsecutivesEnColonne(
			GrilleDessin dessin, 
			int i, int j){
		
		/*
		 * STRAT�GIE : la valeur de i reste inchang�e et j varie de sa position
		 *                       jusqu'� la taille ou si un bloc est non colori�.
		 *                       Finalement retourne le nombre de blocs cons�cutifs
		 *                       qui peut �tre 0.
		 */
		
		//Sera vrai lorsque le nombre de cases est compt�
		boolean compte = false;
		
		//valeur de retour
		int nb = 0;
		
		//Deux conditions d'arr�t
		while(i < dessin.getTaille() && !compte){
			
			if(dessin.estColorie(i, j))
				nb++;
			else
				compte = true;
			i++;
		}
		
		return nb;
	}
	
	/**
	 * Cr�e et rempli les listes pour les lignes 
	 */
	private void compterLignes(){
		
		/**
		 * Strat�gie : un SP compte combien de cases cons�cutives sur 
		 *                  une ligne sur la ligne fournie � partir de la colonne fournie.
		 *                    
		 *                    On met ce nombre dans un bloc avec son indice de
		 *                    d�but et on l'ajoute � la iste correspondant � la ligne
		 *                    dans le vecteur de lignes.
		 *                    
		 *                    
		 */
		//Les it�rateurs
		int i;
		int j;
		
		//Pour toutes les lignes
		for( i = 0; i < tabBlocLignes.length; i++)
		{ 
			
			j = 0;
			
			//cr�ation de la liste vide
			tabBlocLignes[i] = new Vector<InfoBloc>();
			
			//On commence � 0 et on arr�te � la derni�re colonne
			while(j < tabBlocColonnes.length){
				
				//On compte les cases colori�es � partir de j.
				int nb = nbCasesColorieesConsecutivesEnLigne(dessinOrig,i, j) ;
				
				//Il doit y avoir un bloc
				//S'il y a au moins une case colori�e, on ins�re le bloc dans la liste
				if(nb!= 0){
						
					tabBlocLignes[i].add( new InfoBloc(j, nb));
					
				}
				//On d�place j du nombre de cases + 1 
				//pour passer au bloc suivant en passant la case non 
				//colori�e qui les s�pare
				j+=nb + 1;
			}		 
		}		
	}
	
	/**
	 * Cr�e et remplit les listes pour les colonnes
	 */
	private void compterColonnes(){
		
		//it�rateur
		int i;
		int j;
		
		//On commence � 0 et on arr�te � la derni�re ligne
		for( j = 0; j < tabBlocColonnes.length; j++)
		{ 
			
				
			//cr�ation de la liste vide
			tabBlocColonnes[j] = new Vector<InfoBloc>();
			
			i = 0;
			
			while(i < tabBlocLignes.length){
				
				//Premier bloc en colonne
				int nb = nbCasesColorieesConsecutivesEnColonne(dessinOrig,i,j);
				
				//S'il y a au moins une case colori�e, on ins�re le bloc dans la liste
				if(nb!= 0){				
					tabBlocColonnes[j].add( new InfoBloc(i, nb));
					
				}
				
				//On d�place i du nombre de cases + 1 
				//pour passer au bloc suivant en passant la case non 
				//colori�e qui les s�pare
				i+=nb+1;
			}		 
		}		
	}

	/*
	 * Fonction priv�e :  Parcourt la liste dans le tableau de lignes
	 *                             � la recherche de l'infobloc qui se trouve �
	 *                             la colonne fournie en param�tre
	 */
	private void ajusterLignes(int ligne,int colonne){
		
		/*
		 * STRAT�GIE : On pr�sume que la case fait partie n�cessairement 
		 *                          d'un bloc.  SI ce n'est pas le cas, �a plantera avec
		 *                          ArrayOutOfBoundException
		 */
		
		//it�rateur
		int k = 0;
		
		//est mis � vrai lorsque le bloc est trouv�
		boolean trouve = false;
		
		//pour obtenir ceux en provenance des listes
		InfoBloc info;
	    
		//On utilise while parce qu'on ne connait pas le nombre de fois
		//qu'on va it�rer
		while(!trouve){
			
			//On obtient un bloc de la liste
			info = tabBlocLignes[ligne].get(k);
			
			//faut lire : if((info.indiceEstDans(colonne) == true)
			if(info.indiceEstDans(colonne)){
				
				//Un bloc restant de moins
				info.colorieUneCase();	
				
				//on sort de la boucle
				trouve = true;
			}
			k++;
		}	   
	}
	
	/*
	 * Fonction priv�e :  Parcourt la liste dans le tableau de colonnes
	 *                             � la recherche de l'infobloc qui se trouve �
	 *                             la ligne fournie en param�tre
	 */
	private void ajusterColonnes(int ligne, int colonne){
		
		/*
		 * STRAT�GIE : On pr�sume que la case fait partie n�cessairement 
		 *                          d'un bloc.  SI ce n'est pas le cas, �a plantera avec
		 *                          ArrayOutOfBoundException
		 */
		
		//it�rateur
		int k;
		
		//pour obtenir ceux en provenance des listes
		InfoBloc info;
		
		//est mis � vrai lorsque le bloc est trouv�
		boolean trouve = false;
		
		
		//Version do-while de la m�me boucle que dans ajusterLignes
	    
		k = 0;
		do{
			
			info = tabBlocColonnes[colonne].get(k);
			
			//On retient la valeur de retour de la fonction
			trouve =  info.indiceEstDans(ligne);
			
			k++;
			
	    //Termine si trouve == true
		}while(!trouve);
		
		//Une case restante de moins dans ce bloc
		info.colorieUneCase();	
	}
	
	public int getNbVies(){
		return nbVies;
	}
	public void decrementeVie(){
		nbVies--;
	}
	
	/**
	 * Retourne un tableau des tous les nobre de blocs sur une ligne
	 * @param ligne La ligne voulue
	 * @return Un tableau des tous les blocs sur une ligne
	 */
	public InfoBloc[] getInfoBlocLigne(int ligne){
		
		/**
		 * STRAT�GIE : On utilise toArray version qui re�oit un tableau car l'autre 
		 *                          ne semble pas fonctionner.
		 */
		 InfoBloc[] tab = new InfoBloc[tabBlocLignes[ligne].size()];
		return tabBlocLignes[ligne].toArray(tab);
	}
	
	

	/**
	 * Retourne un tableau des tous les nobre de blocs sur une ligne
	 * @param ligne La ligne voulue
	 * @return Un tableau des tous les blocs sur une ligne
	 */
	public InfoBloc[] getInfoBlocColonne(int colonne){
		
		/**
		 * STRAT�GIE : On utilise toArray version qui re�oit un tableau car l'autre 
		 *                          ne semble pas fonctionner.
		 */
		 InfoBloc[] tab = new InfoBloc[tabBlocColonnes[colonne].size()];
		return tabBlocColonnes[colonne].toArray(tab);
	}
	
    /**
     * Accesseur de la grille contenant le dessin original
     * 
     * @return Le dessin original
     */
	public GrilleDessin getDessinOrig(){
		return dessinOrig;
	}
	
	/**
     * Accesseur de la grille contenant le dessin montr�
     * 
     * @return Le dessin montr�
     */
	public GrilleDessin  getDessinMontre(){
		return dessinMontre;
	}
}
