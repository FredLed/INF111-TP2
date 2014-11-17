


public class TestListe {

	public static void main(String[] args) {
		ClListe<Double> liste = new ClListe<Double>();
		liste.InsererApres(2.123);
		liste.InsererApres(123.542);
		liste.InsererApres(1233123.124124);
		
		
		liste.InsererAvant(1231231.123123);
		liste.InsererAvant(123123.123123);
		
		try {
			liste.setPositionCouranteDebut();
			liste.InsererAvant(1.1);
			
			liste.setPositionCouranteFin();
			liste.InsererApres(2.2);
			
			liste.avancer();
		}
		catch (Exception e) { 
			System.out.println(e.getMessage());
		}
		
		try {
			liste.Supprimer();
			liste.setPositionCouranteDebut();
			liste.Supprimer();
			liste.setPositionCouranteFin();
			liste.Supprimer();
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

}
