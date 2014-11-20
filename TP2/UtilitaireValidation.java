import java.util.Scanner;

/**
 * <code}UtileValidation<code><p>
 * Classe qui permet de lire et valider des valeurs
 * en provenance de la console 
 * 
 * @author <a href = "mailto:Pierre.belisle@etsmtl.ca"> Pierre Bélisle</a>
 * @version Aout 2005
 */
public class UtilitaireValidation {
	
    //sert à retenir si l'utilisateur a annulé
	private static boolean utilisateurAnnule = false;
	
	//pour la lecture au clavier
	private static Scanner clavier= new Scanner(System.in);
	
	
	
	/*******************************
	 * LIRE INT
	 *******************************/
	/**
	 * Affiche le message reçu et attend l'entrée d'un
	 * nombre entier entre les deux bornes (min et max).
	 * L'utilisateur annule s'il fait seulement <entrée>
	 * Si l'utilisateur annule, la fonction retourne 0
	 * et la fonction utilisateurAnnule() retournera true.
	 * 
	 * @param msg Le message de sollicitation
	 * @param min limite inférieure acceptée
	 * @param max limite supérieure acceptée
	 * @return un entier entre min et max
	 */
	public static int lireInt(String msg,int min, int max){
				
		String valeurLue = null;
		boolean valide = false;
		int valInt = 0;
		utilisateurAnnule = false;
				
		while(!valide && !utilisateurAnnule){			
		    
		        //sollicitation de l'utilisateur
			    System.out.print(msg + " (<entrée> pour annuler) : ");
			    
		        //on lit toute la ligne		        
			    valeurLue = clavier.nextLine();
			    
			    //si l'utilisateur annule, c'est valide
			    if (valeurLue.equals("")){
			    	utilisateurAnnule = true; 
			    }//if
			    		    
			    //sinon on tente de convertir en entier
			    else 
		
					try{
						//si on réussit la conversion sans exception
						valInt = Integer.parseInt(valeurLue);
						
						//on évalue les bornes
						if(valInt < min || valInt > max)
						     System.out.println("la valeur entrée doit être " +
						     		            "entre "+min + " et" + max);						

				        //si on vient ici c'est que tout est beau
				        else
							valide = true;
					}//try
					
				    //s'il y a une exception lors de la conversion 
				    //on avise et valide reste faux
					catch (Exception e){
					     System.out.println("la valeur entrée doit être " +
			     		            "entre "+min + " et" + max);
					}//catch
		}//while
		
		return valInt;
	}
	

	/*******************************
	 * LIRE DOUBLE
	 *******************************/
	/**
	 * Affiche le message reçu et attend l'entrée d'un
	 * nombre réel entre les deux bornes (min et max).
	 * Si l'utilisateur annule, la fonction retourne 0
	 * et la fonction utilisateurAnnule() retournera true.
	 * 
	 * @param msg Le message de sollicitation
	 * @param min limite inférieure acceptée
	 * @param max limite supérieure acceptée
	 * @return un réel entre min et max
	 */
	public static double lireDouble(String msg,double min, double max){
				
		String valeurLue = null;
		boolean valide = false;
		double valDouble = 0;
		utilisateurAnnule = false;

						
		while(!valide && !utilisateurAnnule){			

		        //sollicitation de l'utilisateur
			    System.out.print(msg + " (<entrée> pour annuler) : ");
			    
		        //on lit toute la ligne		        
			    valeurLue = clavier.nextLine();
			    
			    //si l'utilisateur annule, c'est valide
			    if (valeurLue.equals("")){
			    	utilisateurAnnule = true; 
			    }
			    		    
			    //sinon on tente de convertir en réel
			    else 
		
					try{
						//si on réussit la conversion sans exception
						valDouble = Double.parseDouble(valeurLue);
						
						//on évalue les bornes
						if(valDouble < min || valDouble > max)
						     System.out.println("la valeur entrée doit être " +
						     		            "entre "+min + " et" + max);
						
				        //si on vient ici c'est que tout est beau
				        else
							valide = true;
					}//try
					
				    //s'il y a une exception lors de la conversion 
				    //on avise et valide reste faux
					catch (Exception e){
					     System.out.println("la valeur entrée doit être " +
			     		            "entre "+min + " et" + max);
					}//catch
		}//while
		
		return valDouble;
	}

	/*******************************
	 * LIRE STRING
	 *******************************/
	/**
	 * Affiche le message reçu et attend l'entrée d'une
	 * chaîne.  L'utilisateur annule en entrant une chaîne vide.
	 * Si l'utilisateur annule, la fonction retourne null
	 * et la fonction utilisateurAnnule() retournera true.
	 * 
	 * 
	 * @param msg Le message de sollicitation
	 * @return la chaîne lue
	 */
	public static String lireString(String msg){
				
		String valeurLue = null;
		utilisateurAnnule = false;
		
        //sollicitation de l'utilisateur
	    System.out.print(msg + " (<entrée> pour annuler) : ");
			    
        //on lit toute la ligne		        
	    valeurLue = clavier.nextLine();
    		    
		//si l'utilisateur annule, c'est valide
		//et on retourne null
		if (valeurLue.equals("")){
		  	utilisateurAnnule = true;
		  	valeurLue = null;
		}
	    
		
		return valeurLue;		
    }
	

	/*******************************
	 * UTILISATEUR ANNULE
	 *******************************/
	/**
	 * Retourne si l'utilisateur a annulé lors 
	 * de la dernière lecture au clavier
	 */
	public static boolean utilisateurAnnule() {
		return utilisateurAnnule;
	}

}