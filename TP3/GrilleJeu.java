import java.awt.Rectangle;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * Cette classe est la représentation 
 * en mémoire de la GrilleDessin du jeu de dessin caché (Picross)
 * 
 * Elle calcule et retient la position et  le nombre de chaque bloc coloriées en 
 * ligne et en colonne de la GrilleDessin reçu au constructeur et en permet
 * aussi l'ajustement.
 * 
 * @author pbelisle
 * @version A14
 *
 */
public class GrilleJeu extends Observable {


	/**
	 * Stratégies : On utilise un Vector comme une file pour retenir 
	 *                     le nombre de blocs coloriés  du dessin pour chaque ligne 
	 *                     et chaque colonne.
	 *                      
	 *                     Un tableau de listes pour les lignes et un pour les colonnes.
	 *                        
	 *                     Chaque liste peut être de taille différente (voir règles du jeu)
	 *                      et contient des InfoBloc
	 *                      
	 *                     On présume que le dessin  fourni au constructeur est 
	 *                     valide alors aucune validation n'est effectuée
	 */
	private Vector<InfoBloc> [] tabBlocLignes;
	private Vector<InfoBloc> [] tabBlocColonnes;

	//La grille qui contient le dessin
	private GrilleDessin dessinOrig;

	//grille vide qui est montrée et ajustée durant le jeu
	private GrilleDessin dessinMontre;

	//Évite pls appels à l'accesseur
	private int taille;

	private int nbVies;

	/**
	 * Crée une représentation mémoire du dessin reçu
	 * tel qu'elle sera présentée à l'utilisateur du jeu.
	 *  
	 * @param dessin Le dessin à considérer
	 */
	public GrilleJeu(GrilleDessin dessin){


		//Évite pls appels à l'accesseur
		taille = dessin.getTaille();

		//le dessin montré est vide au départ
		dessinMontre = new GrilleDessin(taille, dessin.getNom());

		//retient le dessin reçu
		this.dessinOrig = dessin;


		nbVies = taille/2;

		initialiserJeu();
	}

	/**
	 * Parcours le dessin pour créer les différentes listes
	 * avec le nombre de cases successives coloriées
	 */
	public void initialiserJeu(){

		//Tableaux de listes de blocs
		tabBlocLignes = new Vector [taille];
		tabBlocColonnes = new Vector  [taille];

		//On compte les lignes d'abord
		compterLignes();


		//Ensuite les colonnes
		compterColonnes();
		
		setChanged();
		notifyObservers();
	}


	/**
	 *Fonction privée pour compter le nombre de cases consécutives qui sont
	 *coloriées sur une ligne
	 */
	private int nbCasesColorieesConsecutivesEnLigne(GrilleDessin dessin, 
			int i, int j){

		/*
		 * STRATÉGIE : La valeur de i reste inchangée et j varie de sa position
		 *                       jusqu'à la taille ou si un bloc est non colorié. 
		 *                       Finalement retourne le nombre de blocs consécutifs
		 *                       qui peut être 0.
		 */

		//Sera vrai lorsque le nombre de cases est compté
		boolean compte = false;

		//valeur de retour
		int nb = 0;

		//Deux conditions d'arrêt
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
	 *Fonction privée pour compter le nombre de cases consécutives qui sont
	 *coloriées sur une colonne.  
	 */
	private int nbCasesColorieesConsecutivesEnColonne(
			GrilleDessin dessin, 
			int i, int j){

		/*
		 * STRATÉGIE : la valeur de i reste inchangée et j varie de sa position
		 *                       jusqu'à la taille ou si un bloc est non colorié.
		 *                       Finalement retourne le nombre de blocs consécutifs
		 *                       qui peut être 0.
		 */

		//Sera vrai lorsque le nombre de cases est compté
		boolean compte = false;

		//valeur de retour
		int nb = 0;

		//Deux conditions d'arrêt
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
	 * Crée et rempli les listes pour les lignes 
	 */
	private void compterLignes(){

		/**
		 * Stratégie : un SP compte combien de cases consécutives sur 
		 *                  une ligne sur la ligne fournie à partir de la colonne fournie.
		 *                    
		 *                    On met ce nombre dans un bloc avec son indice de
		 *                    début et on l'ajoute à la iste correspondant à la ligne
		 *                    dans le vecteur de lignes.
		 *                    
		 *                    
		 */
		//Les itérateurs
		int i;
		int j;

		//Pour toutes les lignes
		for( i = 0; i < tabBlocLignes.length; i++)
		{ 

			j = 0;

			//création de la liste vide
			tabBlocLignes[i] = new Vector<InfoBloc>();

			//On commence à 0 et on arrête à la dernière colonne
			while(j < tabBlocColonnes.length){

				//On compte les cases coloriées à partir de j.
				int nb = nbCasesColorieesConsecutivesEnLigne(dessinOrig,i, j) ;

				//Il doit y avoir un bloc
				//S'il y a au moins une case coloriée, on insère le bloc dans la liste
				if(nb!= 0){

					tabBlocLignes[i].add( new InfoBloc(j, nb));

				}
				//On déplace j du nombre de cases + 1 
				//pour passer au bloc suivant en passant la case non 
				//coloriée qui les sépare
				j+=nb + 1;
			}		 
		}		
	}

	/**
	 * Crée et remplit les listes pour les colonnes
	 */
	private void compterColonnes(){

		//itérateur
		int i;
		int j;

		//On commence à 0 et on arrête à la dernière ligne
		for( j = 0; j < tabBlocColonnes.length; j++)
		{ 


			//création de la liste vide
			tabBlocColonnes[j] = new Vector<InfoBloc>();

			i = 0;

			while(i < tabBlocLignes.length){

				//Premier bloc en colonne
				int nb = nbCasesColorieesConsecutivesEnColonne(dessinOrig,i,j);

				//S'il y a au moins une case coloriée, on insère le bloc dans la liste
				if(nb!= 0){				
					tabBlocColonnes[j].add( new InfoBloc(i, nb));

				}

				//On déplace i du nombre de cases + 1 
				//pour passer au bloc suivant en passant la case non 
				//coloriée qui les sépare
				i+=nb+1;
			}		 
		}		
	}

	/*
	 * Fonction privée :  Parcourt la liste dans le tableau de lignes
	 *                             à la recherche de l'infobloc qui se trouve à
	 *                             la colonne fournie en paramètre
	 */
	private void ajusterLignes(int ligne,int colonne){

		/*
		 * STRATÉGIE : On présume que la case fait partie nécessairement 
		 *                          d'un bloc.  SI ce n'est pas le cas, ça plantera avec
		 *                          ArrayOutOfBoundException
		 */

		//itérateur
		int k = 0;

		//est mis à vrai lorsque le bloc est trouvé
		boolean trouve = false;

		//pour obtenir ceux en provenance des listes
		InfoBloc info;

		//On utilise while parce qu'on ne connait pas le nombre de fois
		//qu'on va itérer
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
		setChanged();
		notifyObservers();
	}

	/*
	 * Fonction privée :  Parcourt la liste dans le tableau de colonnes
	 *                             à la recherche de l'infobloc qui se trouve à
	 *                             la ligne fournie en paramètre
	 */
	private void ajusterColonnes(int ligne, int colonne){

		/*
		 * STRATÉGIE : On présume que la case fait partie nécessairement 
		 *                          d'un bloc.  SI ce n'est pas le cas, ça plantera avec
		 *                          ArrayOutOfBoundException
		 */

		//itérateur
		int k;

		//pour obtenir ceux en provenance des listes
		InfoBloc info;

		//est mis à vrai lorsque le bloc est trouvé
		boolean trouve = false;


		//Version do-while de la même boucle que dans ajusterLignes

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
		setChanged();
		notifyObservers();
	}

	public int getNbVies(){
		return nbVies;
	}
	public void decrementeVie(){
		nbVies--;
		setChanged();
		notifyObservers();
	}

	/**
	 * Retourne un tableau des tous les nobre de blocs sur une ligne
	 * @param ligne La ligne voulue
	 * @return Un tableau des tous les blocs sur une ligne
	 */
	public InfoBloc[] getInfoBlocLigne(int ligne){

		/**
		 * STRATÉGIE : On utilise toArray version qui reçoit un tableau car l'autre 
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
		 * STRATÉGIE : On utilise toArray version qui reçoit un tableau car l'autre 
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
	 * Accesseur de la grille contenant le dessin montré
	 * 
	 * @return Le dessin montré
	 */
	public GrilleDessin  getDessinMontre(){
		return dessinMontre;
	}


	/*
	 * Fonction booléenne locale qui parcourt un Vector d'InfoBloc
	 * et retourne si le nombre de cases restantes est de 0.
	 */
	private boolean listeEstSolutionne( Vector<InfoBloc>  listeBlocs)
	{
		/*
		 * STRATÉGIE : On met le résultat de la comparaison dans une variable
		 *                          booléenne et la boucle se termine si elle devient faux.
		 */
		//Mis à vrai au départ
		boolean solutionne = true;

		//sert à récupérer l'élément de la liste
		InfoBloc infoBloc;

		//itérateur
		int i;

		//évite pls appels à l'accesseur dans la boucle
		int nb_elements =listeBlocs.size();


		//Si la liste est vide, on retourne true
		//sinon, on doit parcourir la liste
		if(!listeBlocs.isEmpty())
		{
			i = 0;

			//On sort si le nombre de cases restantes est != 0
			//ou que i == nb_elements
			while(solutionne && i < nb_elements)
			{

				infoBloc = listeBlocs.get(i);

				//Solutionne sera FALSE si  infoBloc.getNbCasesRestantes != 0
				solutionne  = infoBloc.getNbCasesRestantes() == 0;

				i++;
			}

		}

		return solutionne;

	}

	/*
	 * Fonction privée qui vérifie que tous les vecteurs d'un tableau
	 * ne contient que des Infolocs avec des cases restantes à 0.
	 * 
	 * Essentiellement pour éviter la répétition de code pour les lignes et les colonnes
	 */
	private boolean tableauBlocsEstToutsolutionne(Vector<InfoBloc>[] tabBloc){

		/*
		 * STRATÉGIE : On utilise la fonction locale qui parcours un tableau d'InfoBloc
		 *                          et qui retourne si le nombre de cases restantes est  à 0 
		 */


		//Valeur de retour
		boolean solutionne = true;


		//itérateur du tableau
		int i = 0;

		//On commence solutionnEnLigne les lignes(En SP si réutilisé)
		while(solutionne && i < taille)
		{
			solutionne = listeEstSolutionne(tabBloc[i]);
			i++;
		}

		return solutionne;
	}



	//Fonction boolenne qui retourne TRUE si tous les blocs sont
	//coloriés et que le jeu est solutionne et FALSE autrement.
	public boolean estSolutionne()
	{
		/*
		 *STRATÉGIE : On parcourt les tableaux d'indices et il faut 
		             que les cases restantes de tous les blocs soient à 0 en ligne et 
		             en colonne
		 *
		 *            Si on prend l'exemple des lignes, on met un boléen à TRUE avant 
		 *            de commencer et tant qu'il est vrai et qu'on a pas fini de parcourir
		 *             la liste, on reste dans la boucle.  Cela est fait dans un SP
		 *
		 *           Même chose en colonne, on utilise la même fonciton booléenne.
		 *
		 *          Le jeu est solutionne si solutionne_en_ligne && 
		 *          solutionne_en_colonne
		 *
		 */
		boolean solutionneEnCol = false;
		boolean solutionneEnLigne= tableauBlocsEstToutsolutionne(tabBlocLignes);

		//On ne veut pas parcourir les colonnes pour rien
		if(solutionneEnLigne)
		{
			solutionneEnCol =   tableauBlocsEstToutsolutionne(tabBlocColonnes);	
		}

		return solutionneEnLigne && solutionneEnCol;

	}
	
	private class EcouteurDessinOrig implements Observer {
		
		@Override
		public void update(Observable arg0, Object arg1) {
			//Procédure locale qui crée les tableaux d’indices
			//à partir du dessin original du jeu
			initialiserJeu();
			
			//La grille de jeu est observable
			setChanged();
			notifyObservers();
		}
	}
	
	private class EcouteurDessinMontre implements Observer{
		
		@Override
		public void update(Observable arg0, Object arg1) {
			if (arg1 != null) {
				ajusterLignes((int)((Object[])arg1)[0], (int)((Object[])arg1)[1]);
				setChanged();
				notifyObservers();
			} 
		}
	}

}
