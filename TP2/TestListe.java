


public class TestListe {

	public static void main(String[] args) {
		ClListe<Double> liste = new ClListe<Double>();
		liste.insererApres(2.123);
		liste.insererApres(123.542);
		liste.insererApres(1233123.124124);
		
		
		liste.insererAvant(1231231.123123);
		liste.insererAvant(123123.123123);
		
		try {
			liste.setPositionCouranteDebut();
			liste.insererAvant(1.1);
			
			liste.setPositionCouranteFin();
			liste.insererApres(2.2);
			
			liste.avancer();
		}
		catch (Exception e) { 
			System.out.println(e.getMessage());
		}
		
		try {
			liste.supprimer();
			liste.setPositionCouranteDebut();
			liste.supprimer();
			liste.setPositionCouranteFin();
			liste.supprimer();
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

}
