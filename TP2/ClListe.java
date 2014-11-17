public class ClListe<T> {
	
	private class Noeud {
		
		// On a d�cid� de faire une liste doublement cha�n�e pour facilit�
		// la r�alisation de la fonction InsererAvant.
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
	
	public boolean estVide() {
		return nbElements == 0;
	}
	
	public void InsererAvant(T valeur) {
		// Dans tous les cas, on met tout le temps la position courante � la 
		// nouvelle donn�e entr�e.
		if (estVide()) {
			// Si la liste est vide, on met la tete, la position courante et la fin
			// � la donn�e qu'on ajoute car c'est la seule de la liste pour le moment.
			fin = positionCourante = tete = new Noeud(valeur, null, null);
		}
		else if (tete == fin) {
			// Si la tete et la fin sont �quivalente, cela veut dire qu'il y a
			// pour le moment une seule donn�e et qu'on veut en ajouter une deuxi�me.
			// Donc on laisse la fin � sa position et on recule la t�te et la position courante.
			tete = positionCourante = fin.precedent = new Noeud(valeur, null, fin);
		}
		else if (positionCourante == tete) {
			// Si la position courante est � la t�te (si on se rend ici, cela veut
			// dire qu'on a minimum 3 donn�es dans la liste gr�ce au deux tests
			// ci-haut), on ajoute avant la t�te et on d�place la t�te � la 
			// nouvelle donn�e.
			positionCourante.precedent = new Noeud(valeur, null, positionCourante);
			tete= positionCourante.precedent;
			
			// On place la posistion courant � la nouvelle donn�e.
			positionCourante = tete;
		}
		else if (positionCourante != null) {
			// Si on se rend ici, on fait juste ajout� la nouvelle donn�e avant
			// la position courante.
			Noeud nouvelleEntree= new Noeud(valeur, positionCourante.precedent, positionCourante);
			positionCourante.precedent.suivant = nouvelleEntree;
			positionCourante.precedent = nouvelleEntree;
			
			// On place la position courante � la nouvelle donn�e.
			positionCourante = nouvelleEntree;
		}
		
		++nbElements;
	}
	
	public void InsererApres(T valeur) {
		// Dans tous les cas, on met tout le temps la position courante � la 
		// nouvelle donn�e entr�e. 
		if (estVide()) {
			// Si la liste est vide, on met la tete, la position courante et la fin
			// � la donn�e qu'on ajoute car c'est la seule de la liste pour le moment.
			fin = positionCourante = tete = new Noeud(valeur, null, null);
		}
		else if (tete == fin) {
			// Si la tete et la fin sont �quivalente, cela veut dire qu'il y a
			// pour le moment une seule donn�e et qu'on veut en ajouter une deuxi�me.
			// Donc on laisse la t�te � sa position et on avance la fin et la position courante.
			positionCourante = tete.suivant = fin = new Noeud(valeur, tete, null);
		}
		else if (positionCourante == fin) {
			// Si on veut ajouter apr�s la fin de la liste, on d�place la fin.
			fin = positionCourante.suivant = new Noeud(valeur, positionCourante, null);
			
			// On place la position courante � la nouvelle donn�e.
			positionCourante = fin;
		}
		else if (positionCourante != null) {
			// Si on se rend ici, on fait juste ajout� la nouvelle donn�e apr�s
			// la position courante.
			Noeud nouvelleEntree = new Noeud(valeur, positionCourante, positionCourante.suivant);
			positionCourante.suivant.precedent = nouvelleEntree;
			positionCourante.suivant = nouvelleEntree;
			
			// On place la position courante � la nouvelle donn�e.
			positionCourante = nouvelleEntree;
		}
		
		++nbElements;
	}
	
	/**
	 * Permet de supprimer la donn�e � la position courante. Par la suite,
	 * on d�place la position courante � la donn�e suivante celle cibl� par la
	 * suppression.
	 * @return si la suppression � eu lieu ou non
	 */
	public boolean Supprimer() {
		//on regarde si la liste est vide ou si la position courante est null.
		if (estVide() || positionCourante == null) {
			return false;
		}
		else {
			if (positionCourante == tete)
			{
				//si la position est au d�but, on met la tete au prochain noeud 
				//et on met null dans le precedent.
				tete = positionCourante.suivant;
				tete.precedent = null;
				positionCourante = tete;
			}
			else if (positionCourante == fin) {
			    //Si la position est � la fin, on met la valeur du noeud 
				//pr�c�dent � la position courante et on met suivant � null
				fin = positionCourante.precedent;
				fin.suivant = null;
				positionCourante = fin;
			}
			else {
				//la position courante n'est ni au d�but ni � la fin, alors
				//r�ajuste les valeur des noeud suivant et pr�c�dent.Pour sortir
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
	 *Permet de retourner le nombre d'�l�ment dans la liste.
	 * @return Le nombre d'�lements
	 */
	public int getNbElements() {
		return nbElements;
	}
	
	/**
	 *Permet de retourner la valeur de l'�l�ment courant dans la liste.
	 * @return La valeur de l'�l�ment
	 */
	public T getElement() {
		return positionCourante.valeur;
	}
	
	/**
	 *Permet de mettre la position courante aux suivant.
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
	 *Permet de mettre la position courante aux pr�c�dent.
	 * 
	 */
	public void reculer() throws Exception {
		if (estVide())
			throw new Exception("Impossible de reculer car la liste est vide.");
		else if (positionCourante == tete)
			throw new Exception("Impossible de reculer plus loin que le d�but.");
		else if (positionCourante != null)
			positionCourante = positionCourante.suivant;
		else
			throw new Exception("La position courante est nulle.");
	}
	
	/*
	 * Permet de mettre la position courant au d�but du noeud.
	 * 
	 * */
	public void setPositionCouranteDebut() throws Exception {
		if (!estVide()) 
			positionCourante = tete;
		else
			throw new Exception("La liste est vide.");
	}
	
	/*
	 * Permet de mettre la position courant � la fin du noeud.
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
