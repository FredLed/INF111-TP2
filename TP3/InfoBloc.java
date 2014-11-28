/**
 * Permet de faciliter l'algorithme de mise à jour des indices qui sont offert
 * dans la grille de jeu
 * 
 *  Un bloc est une ou plusieurs cases coloriées.  Nous retenons
 *  l'indice de début, le nombre de cases du bloc et le
*   nombre de cases qui restent à décourvrir.
*   
 * @author pbelisle
 * @version A14
 *
 */
public class InfoBloc {
	
	/*
	 * La stratégie est de décrémenter le nombre de cases restantes à coloriée
	 * jusqu'à ce qu'il n'y en ait plus.  C'est ainsi que nous saurons qu'un bloc est 
	 * terminé
	 */
	private int debut;
	private int nbCases;
	private int nbCasesRestantes;
	
 
	/**
	 * Constructeur qui crée un bloc à partir de l'indice founi de la longueur
	 * fournie
	 * 
	 */
	public InfoBloc(int debut, int nb){
		
		          //La position de départ du bloc
		         this.debut = debut;
		         
		         //Important de ne pas utiliser debut et fin qui sont les paramètres
		         this.nbCases = nb;
				
		         nbCasesRestantes = nb;
	}


	/**
	 * Retourne le nombre de cases du bloc
	 * @return Le nombre de cases du bloc
	 */
	public int getNbCases() {
		return nbCases;
	}


	/**
	 * Décrémente le nombre de cases restant à colorier
	 */
	public void colorieUneCase() {
		this.nbCasesRestantes--;
	}


	/**
	 * Accesseur du nombre de cases restant à colorier
	 * @return Le nombre de cases restant à colorier
	 */
	public int getNbCasesRestantes() {
		return nbCasesRestantes;
	}
	
	/**
	 * Retourne si l'indice est dans l'intervalle que le bloc couvre
	 * @param indice Doit être 
	 * @return Si l'indice est entre le début et la fin du bloc
	 */
	public boolean indiceEstDans(int indice){
		
		return indice>= debut && indice <= debut + nbCases - 1;
		
	}
	
	/**
	 * Sert essentiellement au déboggage
	 */
	public String toString(){
		return "debut : " + debut + " nb : " +
	                  nbCases + "restants : " + nbCasesRestantes;
	}
	
}
