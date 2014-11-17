
public class InfoBloc {
	private int debut;
	private int nb;
	private int reste;
	
	public InfoBloc(int debut, int nb, int reste){
		this. debut = debut;
		this.nb = nb;
		this.reste = reste;
	}
	public int getNbCasesRestantes(){
		return reste;
	}
	public int getNbCases(){
		return nb;
	}
	
}
