public class DemarrerJeuDessinCache {

	//Démarre l'application GUI du dessin chaché dans un procéeesus séparé
	public static void main(String[] args) {
		
		CadreDessinCache cadreJeu = new CadreDessinCache();
		Thread t = new Thread(cadreJeu);
		t.start();
	}
}
