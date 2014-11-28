/**
 * Permet de faciliter l'algorithme de mise � jour des indices qui sont offert
 * dans la grille de jeu
 * 
 *  Un bloc est une ou plusieurs cases colori�es.  Nous retenons
 *  l'indice de d�but, le nombre de cases du bloc et le
*   nombre de cases qui restent � d�courvrir.
*   
 * @author pbelisle
 * @version A14
 *
 */
public class InfoBloc {
	
	/*
	 * La strat�gie est de d�cr�menter le nombre de cases restantes � colori�e
	 * jusqu'� ce qu'il n'y en ait plus.  C'est ainsi que nous saurons qu'un bloc est 
	 * termin�
	 */
	private int debut;
	private int nbCases;
	private int nbCasesRestantes;
	
 
	/**
	 * Constructeur qui cr�e un bloc � partir de l'indice founi de la longueur
	 * fournie
	 * 
	 */
	public InfoBloc(int debut, int nb){
		
		          //La position de d�part du bloc
		         this.debut = debut;
		         
		         //Important de ne pas utiliser debut et fin qui sont les param�tres
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
	 * D�cr�mente le nombre de cases restant � colorier
	 */
	public void colorieUneCase() {
		this.nbCasesRestantes--;
	}


	/**
	 * Accesseur du nombre de cases restant � colorier
	 * @return Le nombre de cases restant � colorier
	 */
	public int getNbCasesRestantes() {
		return nbCasesRestantes;
	}
	
	/**
	 * Retourne si l'indice est dans l'intervalle que le bloc couvre
	 * @param indice Doit �tre 
	 * @return Si l'indice est entre le d�but et la fin du bloc
	 */
	public boolean indiceEstDans(int indice){
		
		return indice>= debut && indice <= debut + nbCases - 1;
		
	}
	
	/**
	 * Sert essentiellement au d�boggage
	 */
	public String toString(){
		return "debut : " + debut + " nb : " +
	                  nbCases + "restants : " + nbCasesRestantes;
	}
	
}
