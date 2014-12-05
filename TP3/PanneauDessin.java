import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanneauDessin extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID =42L;
	private GrilleJeu jeu;
	/**
	 * Constructeur du panneau qui contient les boutons reprÃ©sentant la grille
	 * 
	 * @param dessin
	 */
	public PanneauDessin(GrilleJeu jeu) {

		this.jeu = jeu;

		creerGrille();
	}

	public void creerGrille() {
		if(CadreDessinCache.getMode() == CadreDessinCache.MODE_CREATION)
		{
			creerComposants(this.jeu.getDessinOrig());
		}
		else
		{
			creerComposants(this.jeu.getDessinMontre());
		}
			
	}

	/*
	 * CrÃ©e un tableau 2D de composants (GridLayout)
	 */
	public void creerComposants(GrilleDessin dessin) {
		
		//On récupère le dessin et sa taille
		int taille = dessin.getTaille();
		GridLayout gridLayout = new GridLayout(taille,taille);	
		this.setLayout(gridLayout);
		
		//Remplit le gridLayout de JButton Ã© l'aide de 2 boucles
		for(int i = 0; i < taille; i++)
		{
			for(int j = 0; j < taille; j++)
			{
				//on traduit le dessin original en composante 
				//en vérifiant chaque cases
				if(dessin.estColorie(i, j)){					
					this.add(new MonJButton(i,j,true));
				}
				
				else{
					this.add(new MonJButton(i,j,false));
				}
			}
		}
		
	}
	
	private class MonJButton extends JButton{

		//Ajout des attributs
		int ligne, colonne;
		boolean etat;
		
		//Constructeur
		public MonJButton(int ligne2,int colonne2,boolean etat2){
			this.ligne = ligne2;
			this.colonne = colonne2;
			this.etat = etat2;
			
			setBackground(etat2 ? Color.BLACK: Color.WHITE);
				
		this.addActionListener(new MonEcouteurBouton());
		
		}
		
		private class MonEcouteurBouton implements ActionListener{
			public void actionPerformed(ActionEvent arg0) {
				
				MonJButton source = (MonJButton)arg0.getSource();
				int ligneSrc = source.getLigne();
				int colonneSrc = source.getColonne();
				boolean etat = source.getEtat();
				
				//Si on est en mode crÃ©ation
				if(CadreDessinCache.getMode() == CadreDessinCache.MODE_CREATION)
				{			
					if(getBackground() == Color.BLACK)
					{
						setBackground(Color.WHITE);
						jeu.getDessinOrig().colorieCase(ligneSrc, colonneSrc, false);
					}
					else
						setBackground(Color.BLACK);
						jeu.getDessinOrig().colorieCase(ligneSrc, colonneSrc, true);
					
				}
				
				//Si on est en mode jeu
				else
				{
					//Si la case original Ã© ses coordonnées est colorié
					// et que la source est blanche
					if(jeu.getDessinOrig().estColorie(ligneSrc, colonneSrc) 
							&& source.getEtat() == false)
					{
						source.switchEtat(jeu.getDessinOrig().estColorie(ligneSrc, colonneSrc));
						jeu.getDessinMontre().colorieCase(ligneSrc, colonneSrc, true);	
					}
					//Si la case original est vide et la source est blanche
					else if(source.getEtat() == false)
						jeu.decrementeVie();
				}
				
			}
		}
		
		//Getters
		public int getLigne(){
			return this.ligne;
		}
		
		public int getColonne(){
			return this.colonne;
		}
		
		public boolean getEtat(){
			return this.etat;
		}
		//Setters
		public void switchEtat(boolean etat){
			
			this.setBackground(etat?Color.BLACK:Color.WHITE);
			
			if(etat == true)
			{
				etat = false;
			}
			else
			{
				etat = true;
			}

		}
		
	}
	
//End of class	
}
