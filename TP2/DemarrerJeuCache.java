import java.awt.Color;
import javax.swing.*;

public class DemarrerJeuCache extends JFrame {

	/**
	 * Programme principal 
	 * 
	 * @author Hai-Dang Ly, Frédéric Leduc, Kelvin Vargas-Garcia, 
	 * 				Martin Satakem Mbougho
	 * @version novembre 2014
	 */
	
	public static void main(String[] args) {
		JFrame j = new JFrame();
		JPanel p = new JPanel();
		j.setContentPane(p);
		j.setSize(500, 500); 
		p.setBackground(Color.red);
		j.setVisible(true);
		
		UtilitaireFichier.JFileChooser(p);
	}
}
