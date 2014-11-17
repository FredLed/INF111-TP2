import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

/**
 * Une classe contenant les sous-programmes pour afficher
 * un GUI pour sélectionner le fichier .dsn et le lire.
 * 
 * Si ce n'est pas la bonne extension, un message d'erreur
 * s'affiche et l'utilisateur doit choisir un autre fichier.
 * 
 *
 * @version novembre 2014
 *
 */

public class UtilitaireFichier {
	
	//demander le fichier par GUI
	public static void JFileChooser(JPanel component) {
		
		//créer un nouveau objet du file chooser
		final JFileChooser fc = new JFileChooser();
		boolean bonfichier = false;
		
		while (bonfichier==false) {
			//répondre quand on clique sur la souris
			int returnVal = fc.showOpenDialog(component);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
					
				if (accept(file)) {			
					lire(file,component);
					bonfichier = true;
				} 
				else {
					JOptionPane.showMessageDialog(component,"Le fichier est invalide.",
											"Erreur",JOptionPane.ERROR_MESSAGE);
				}
			
			}
			else {
				bonfichier = true;
			}	
		}
	}	
		
	
	//vérifie le fichier pour voir si c'est bien une extension .dsn
	public static boolean accept(File f){
		boolean estbon = false;
		//filtre pour l'extension .dsn
		FileNameExtensionFilter filter = new FileNameExtensionFilter("fichier dsn","dsn");
		//retourne true si ça correspond à l'extension .dsn
	    if(filter.accept(f)){
	    	estbon = true;
	    }
	    return estbon;
	}
	
	//afficher le message d'erreur si ce n'est pas la bonne extension
	public static void message(JFrame frame){
		JOptionPane.showMessageDialog(frame,"Le fichier est invalide.","Erreur",
													JOptionPane.ERROR_MESSAGE);
	}	
	
	//désérialiser et lire le fichier .dsn
	public static void lire(File file,JPanel panel) {
		
		//ouvrir une connection au fichier sélectionné
		try {
			
			FileInputStream fileIn = new FileInputStream(file);
			
			//désérialiser le fichier
			ObjectInputStream in = new ObjectInputStream(fileIn);
			
			//lire le fichier
			GrilleDessin lecture = (GrilleDessin) in.readObject();
			
			//fermer
			in.close();
			
			//affichier ce qui a été lu
			System.out.println("Information: " + lecture);
			JOptionPane.showMessageDialog(panel,lecture,"Erreur",
													JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e) { }
		
	}
}
