package SistemFacturi;
import java.util.HashMap;
import java.util.Map.Entry;


public class MediumMarket extends Magazin {
	public final String Tip = "MediumMarket";
	
	public MediumMarket(String nume) {
		super(nume);
	}
	
	public double calculScutiriTaxe() {
		double totalCuTaxe = this.getTotalCuTaxe();
		HashMap<String, Double> sumaCategorii = new HashMap<>();
		
		// initializare categorii
		for(String categorie : Gestiune.getInstance().getCategoriiList()) {
			sumaCategorii.put(categorie, new Double(0));
		}
	
		for(Entry<String, Double> e : sumaCategorii.entrySet()) {
			for(Factura f : facturi) {
				double suma = f.getTotalCategorieCuTaxe(e.getKey());
				sumaCategorii.put(e.getKey(), suma + e.getValue()); 
			}
			
		}
		
		for(Entry<String, Double> e : sumaCategorii.entrySet()) 
			if(e.getValue() > totalCuTaxe * 50.d / 100.d)
				return 5.0d;
		
		return 0;
	}
	
	public String getTip() {
		return this.Tip;
	}
		
	
	public String toString() {
		return super.toString() + " (" + Tip + ")";
	}

}
