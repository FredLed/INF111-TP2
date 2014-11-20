
public class InfoBloc {
	private int debut;
	private int nb;
	private int reste;
	
	public InfoBloc(int debut, int nb, int reste){
		this. debut = debut;
		this.nb = nb;
		this.reste = reste;
	}
	
	public int getNbCasesRestantes() {
		return reste;
	}
	
	public void setNbCasesRestantes(int reste) {
		this.reste = reste;
	}
	
	public int getNbCases(){
		return nb;
	}
	
	public int getDebut(){
		return debut;
	}
}
