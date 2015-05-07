package SistemFacturi;

public class MiniMarket extends Magazin {
	public final String Tip = "MiniMarket";
	
	public MiniMarket(String nume) {
		super(nume);
	}
	
	public double calculScutiriTaxe() {
		double totalCuTaxe = this.getTotalCuTaxe();
		
		// trebuie sa parcurg toate tarile si 
		// pentru fiecare sa calculez suma produselor comandate
		for(String tara : Gestiune.getInstance().taxe.keySet())
			if(this.getTotalTaraCuTaxe(tara) > totalCuTaxe * 50.d / 100.d)
				return 10.0d;
		
		return 0;
	}
	
	public String getTip() {
		return this.Tip;
	}
	
	public String toString() {
		return super.toString() + " (" + Tip + ")";
	}
}
