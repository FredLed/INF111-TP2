import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * Classe qui permet de récupérer et de sauvegarder les différentes 
 * GrilleDessin
 * 
 * @author pbelisle
 * @version A14
 *
 */
public class UtilitaireFichier {


	//Constantes

	//Sert à clarifier le code sur les options possibles
	public static final int OUVRE = 0;
	public static final int SAUVE = 1;


	//Traduction des messages de JOptionPane
	public final static int MSG_OUI_CANCEL = 
			JOptionPane.OK_CANCEL_OPTION;

	public final static int MSG_OUI_NON_CANCEL =
			JOptionPane.YES_NO_CANCEL_OPTION;

	//Seule extension valide
	public static final String NOM_EXTENSION = "dsn";


	//Nom par défaut
	public static final String NOM_FIC_DEFAULT = "aucun dessin";

	/**
	 * Méthode static qui permet de sauvegarder dans un fichier le dessin
	 * reçu en paramètre.  L'utilisateur doit donner un nom au dessin avec
	 * l'extension prévue
	 * 
	 * @param dessin
	 */
	public static boolean sauvegarderGrilleDessin(final GrilleDessin dessin){

		/*
		 * STRATÉGIE : On sauve le dessin d'un seul coup dans un fichier 
		 *                       binaire à l'aide de FileOutputStream et 
		 *                       ObjectOutputStream.
		 *                       
		 *                       Le nom de fichier est saisit par la fonction locale 
		 *                       nomFichierValide.
		 */
		//valeur de retour
		boolean sauvegarde = false;

		//Récupère le nom de fichier
		String nomFic = nomFichierValide(dessin.getNom(), 
				SAUVE, 
				NOM_EXTENSION);

		//Si null, l'utilisateur a annulé
		if(nomFic != null){

			//Les instances nécessaires
			FileOutputStream fic;
			ObjectOutputStream tampon = null;

			try {

				//Crée le fichier du nom voulu
				fic = new FileOutputStream(nomFic);

				//Ouverture du tampon d'écriture
				tampon = new ObjectOutputStream(fic);

				//On tente d'écrire
				tampon.writeObject(dessin);

				//écriture effectuée, on peut fermer
				tampon.close();	

				sauvegarde =  true;

			} catch (FileNotFoundException e) {
				e.printStackTrace();
	
			//Problème lors de l'accès au disque
			} catch (IOException e) {
				e.printStackTrace();
			}

		}	

		return sauvegarde;
	}

	/**
	 * NOM FICHIER VALIDE (fonction locale)
	 * Permet à l'utilisateur de sélectionner un nom de fichier pour
	 * ouvrir un dessin antérieurement sauvegardé.
	 * 
	 * @param nomFic Le nom du fichier par défaut offert
	 * @param action OUVRE ou SAUVE
	 * @param extension UNe chaîne contenant l'extension acceptée
	 * @return Un nom de fichier valide ou null
	 */
	private static String nomFichierValide(String nomFicDefaut, 
			int action, 
			String extension){

		/*
		 *STRATEGIE : On utilise le FileChooser de Java qui permet de 
		 *                      sélectionner un nom de fichier parmi un filtre donné.  
		 *                      Le filtre utilisé est le nom de fichier concaténer avec
		 *                      l'extension fournie.
		 */
		
		//valeur de retour
		String nomFic = null;
		
		//Création du sélectionneur de fichier
		JFileChooser fc = new JFileChooser(".");

		//On filtre seulement les fichiers avec l'extension
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Dessin",
				extension);
		
		fc.addChoosableFileFilter(filter);

		//On laissera la fenêtre de dialogue tant que ce ne sera pas valide                
		boolean valide = false;

		//Tant que ce n'est pas valide (avec la bonne extension) ou 
		//que l'utilisateur annule
		while(!valide){

			//On affiche la fenêtre de dialogue et on attend la réponse
			int returnVal;

			//En ouverture
			if(action == OUVRE)
				returnVal = fc.showOpenDialog(null);

			//Ou en sauvegarde. 
			else{

				fc.setSelectedFile(new File(nomFicDefaut+ "." + extension));

				returnVal = fc.showSaveDialog(null);						   
			}


			//Si l'utilisateur a choisi un fichier
			if (returnVal == JFileChooser.APPROVE_OPTION) {

				//On obtient le chemin complet du fichier choisi et
				//on le concatène avec l'extension pour le valider. 
				nomFic = fc.getSelectedFile().getAbsolutePath();

				valide = extensionValide(nomFic,"."+extension);
				

				//On change le répertoire courant par celui du fichier qui vient 
				//d'être lu
				System.setProperty("user.dir",fc.getSelectedFile().getParent());							
			}

			//Si pas de fichier sélectionné, on arête
			else{
				valide = true;
			}
		}		

		return nomFic;

	}	

	/**
	 * Permet de vérifier que l'extension du nom de fichier est valide
	 * @param nomFic Le nom du fichier contenant l'extension
	 * @param  extension L'extension qui doit terminer le nom de fichier
	 */
	public static boolean extensionValide(String nomFic, String extension){

		/*
		 * STRATÉGIE : On utilise JOptionPane pour afficher un message 
		 *                       d'invalidité et la méthode booléenne endsWith de la
		 *                       classe String.
		 */
		
		//valeur de retour
		boolean valide = false;

		if(!(nomFic.endsWith(extension)))
			JOptionPane.showMessageDialog(null,"Extension invalide");

		else{
			
			valide = true;
		}
		return valide; 
	}				


	/**
	 * Permet d'obtenir un dessin sauvegardé sur disque 
	 * 
	 * @return La grille contenu dans le fichier chooisi (si valide)
	 */
	public static GrilleDessin recupererDessin(){

		/*
		 *STRATEGIE : On utilise le FileChooser de Java qui permet de 
		 *                      sélectionner un nom de fichier parmi un filtre donné.  
		 *                      Le filtre utilisé est le nom de fichier concaténer avec
		 *                      l'extension fournie
		 *                      
		 */
		
		//Le dessin à retourner
		GrilleDessin dessin = null;


		String nomFic = nomFichierValide(NOM_FIC_DEFAULT, 
				OUVRE, 
				NOM_EXTENSION);

		if(nomFic != null){

			// ouverture du fichier contenant le dessin
			FileInputStream fic;

			try {
				fic = new FileInputStream(nomFic);

				// ouverture du tampon logique
				ObjectInputStream tampon = null;		
				try {
					tampon = new ObjectInputStream(fic);

					//lecture de la bd
					dessin = (GrilleDessin) tampon.readObject();
					tampon.close();

				} 
				//vérification des problèmes lors de la lecture
				catch (ClassNotFoundException e) {
					e.printStackTrace();
					System.out.println("bogue");
				}

				//erreur d'écriture
				catch (IOException e) {
					e.printStackTrace();
				}

			}//si le fichier n'existe pas, nomFIc est déjà à null
			catch (FileNotFoundException e) {

			}

		}

		return dessin;
	}

}