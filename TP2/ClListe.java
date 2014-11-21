public class ClListe<T> {
	
	private class Noeud {
		
		// On a décidé de faire une liste doublement chaînée pour facilité
		// la réalisation de la fonction InsererAvant.
		public Noeud(T valeur, Noeud precedent, Noeud suivant) {
			this.precedent = precedent;
			this.suivant = suivant;
			this.valeur = valeur;
		}
		
		private Noeud suivant;
		private Noeud precedent;
		private T valeur;
	}
	
	public ClListe() {
		nbElements = 0;
		tete = null;
		fin = null;
		positionCourante = null;
	}
	
	public ClListe(ClListe<T> liste) {
		try {
			if (liste != null) {
				this.tete = liste.tete;
				this.fin = liste.fin;
				this.nbElements = liste.nbElements;
				this.positionCourante = liste.positionCourante;
			}
		}
		catch (Exception e) { }
	}
	
	public boolean estVide() {
		return nbElements == 0;
	}
	
	public void insererAvant(T valeur) {
		// Dans tous les cas, on met tout le temps la position courante à la 
		// nouvelle donnée entrée.
		if (estVide()) {
			// Si la liste est vide, on met la tete, la position courante et la fin
			// à la donnée qu'on ajoute car c'est la seule de la liste pour le moment.
			fin = positionCourante = tete = new Noeud(valeur, null, null);
		}
		else if (tete == fin) {
			// Si la tete et la fin sont équivalente, cela veut dire qu'il y a
			// pour le moment une seule donnée et qu'on veut en ajouter une deuxième.
			// Donc on laisse la fin à sa position et on recule la tête et la position courante.
			tete = positionCourante = fin.precedent = new Noeud(valeur, null, fin);
		}
		else if (positionCourante == tete) {
			// Si la position courante est à la tête (si on se rend ici, cela veut
			// dire qu'on a minimum 3 données dans la liste grâce au deux tests
			// ci-haut), on ajoute avant la tête et on déplace la tête à la 
			// nouvelle donnée.
			positionCourante.precedent = new Noeud(valeur, null, positionCourante);
			tete= positionCourante.precedent;
			
			// On place la posistion courant à la nouvelle donnée.
			positionCourante = tete;
		}
		else if (positionCourante != null) {
			// Si on se rend ici, on fait juste ajouté la nouvelle donnée avant
			// la position courante.
			Noeud nouvelleEntree= new Noeud(valeur, positionCourante.precedent, positionCourante);
			positionCourante.precedent.suivant = nouvelleEntree;
			positionCourante.precedent = nouvelleEntree;
			
			// On place la position courante à la nouvelle donnée.
			positionCourante = nouvelleEntree;
		}
		
		++nbElements;
	}
	
	public void insererApres(T valeur) {
		// Dans tous les cas, on met tout le temps la position courante à la 
		// nouvelle donnée entrée. 
		if (estVide()) {
			// Si la liste est vide, on met la tete, la position courante et la fin
			// à la donnée qu'on ajoute car c'est la seule de la liste pour le moment.
			fin = positionCourante = tete = new Noeud(valeur, null, null);
		}
		else if (tete == fin) {
			// Si la tete et la fin sont équivalente, cela veut dire qu'il y a
			// pour le moment une seule donnée et qu'on veut en ajouter une deuxième.
			// Donc on laisse la tête à sa position et on avance la fin et la position courante.
			positionCourante = tete.suivant = fin = new Noeud(valeur, tete, null);
		}
		else if (positionCourante == fin) {
			// Si on veut ajouter après la fin de la liste, on déplace la fin.
			fin = positionCourante.suivant = new Noeud(valeur, positionCourante, null);
			
			// On place la position courante à la nouvelle donnée.
			positionCourante = fin;
		}
		else if (positionCourante != null) {
			// Si on se rend ici, on fait juste ajouté la nouvelle donnée après
			// la position courante.
			Noeud nouvelleEntree = new Noeud(valeur, positionCourante, positionCourante.suivant);
			positionCourante.suivant.precedent = nouvelleEntree;
			positionCourante.suivant = nouvelleEntree;
			
			// On place la position courante à la nouvelle donnée.
			positionCourante = nouvelleEntree;
		}
		
		++nbElements;
	}
	
	/**
	 * Permet de supprimer la donnée à la position courante. Par la suite,
	 * on déplace la position courante à la donnée suivante celle ciblé par la
	 * suppression.
	 * @return si la suppression à eu lieu ou non
	 */
	public boolean supprimer() {
		//on regarde si la liste est vide ou si la position courante est null.
		if (estVide() || positionCourante == null) {
			return false;
		}
		else {
			if (positionCourante == tete)
			{
				//si la position est au début, on met la tete au prochain noeud 
				//et on met null dans le precedent.
				tete = positionCourante.suivant;
				tete.precedent = null;
				positionCourante = tete;
			}
			else if (positionCourante == fin) {
			    //Si la position est à la fin, on met la valeur du noeud 
				//précédent à la position courante et on met suivant à null
				fin = positionCourante.precedent;
				fin.suivant = null;
				positionCourante = fin;
			}
			else {
				//la position courante n'est ni au début ni à la fin, alors
				//réajuste les valeur des noeud suivant et précédent.Pour sortir
				// le noeud courant de la liste
				positionCourante.precedent.suivant = positionCourante.suivant;
				positionCourante.suivant.precedent = positionCourante.precedent;
				positionCourante = positionCourante.suivant;
			}
			
			--nbElements;
			return true;
		}
	}
	
	/**
	 *Permet de retourner le nombre d'élément dans la liste.
	 * @return Le nombre d'élements
	 */
	public int getNbElements() {
		return nbElements;
	}
	
	/**
	 *Permet de retourner la valeur de l'élément courant dans la liste.
	 * @return La valeur de l'élément
	 */
	public T getElement() {
		return positionCourante.valeur;
	}
	
	public void setElement(T valeur) {
		positionCourante.valeur = valeur;
	}
	
	/**
	 *Permet de mettre la position courante au suivant.
	 * 
	 */
	public void avancer() throws Exception {
		if (estVide()) 
			throw new Exception("Impossible d'avancer car la liste est vide.");
		else if (positionCourante == fin) 
			throw new Exception("Impossible d'avancer plus loin que la fin.");
		else if (positionCourante != null)
			positionCourante = positionCourante.suivant;
		else
			throw new Exception("La position courante est nulle.");
	}
	
	/**
	 *Permet de mettre la position courante au précédent.
	 * 
	 */
	public void reculer() throws Exception {
		if (estVide())
			throw new Exception("Impossible de reculer car la liste est vide.");
		else if (positionCourante == tete)
			throw new Exception("Impossible de reculer plus loin que le début.");
		else if (positionCourante != null)
			positionCourante = positionCourante.suivant;
		else
			throw new Exception("La position courante est nulle.");
	}
	
	/*
	 * Permet de mettre la position courant au début du noeud.
	 * 
	 * */
	public void setPositionCouranteDebut() throws Exception {
		if (!estVide()) 
			positionCourante = tete;
		else
			throw new Exception("La liste est vide.");
	}
	
	/*
	 * Permet de mettre la position courant à la fin du noeud.
	 * 
	 * */
	public void setPositionCouranteFin() throws Exception {
		if (!estVide())
			positionCourante = fin;
		else 
			throw new Exception("La liste est vide.");
	}
	
	private Noeud tete;
	private Noeud positionCourante;
	private Noeud fin;
	private int nbElements;
}
