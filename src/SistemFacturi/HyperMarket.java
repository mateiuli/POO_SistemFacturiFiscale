package SistemFacturi;

public class HyperMarket extends Magazin {
	public final String Tip = "HyperMarket";
	
	public HyperMarket(String nume) {
		super(nume);
	}
	
	public double calculScutiriTaxe() {
		double totalCuTaxe = this.getTotalCuTaxe();
		for(Factura f : facturi)
			if(f.getTotalCuTaxe() > totalCuTaxe * 10.d / 100.d)
				return 1.0d;
		
		return 0;
	}
	
	public String getTip() {
		return this.Tip;
	}
	
	public String toString() {
		return super.toString() + " (" + Tip + ")";
	}
}
