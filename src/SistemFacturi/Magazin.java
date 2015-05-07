package SistemFacturi;
import java.util.Vector;


public abstract class Magazin implements IMagazin {
	public final String Tip = "Market";
	public String nume;
	public Vector<Factura> facturi;
		
	public Magazin(String nume) {
		this.nume = nume;
		this.facturi = new Vector<>();
	}
	
	public double getTotalFaraTaxe() {
		double sum = 0;
		for(Factura f : facturi) {
			sum += f.getTotalFaraTaxe();
		}
		
		return sum;
	}

	public double getTotalCuTaxe() {
		double sum = 0;
		for(Factura f : facturi) {
			sum += f.getTotalCuTaxe();
		}
		
		return sum;	
	}

	public double getTotalCuTaxeScutite() {
		return this.getTotalCuTaxe() - this.getTotalCuTaxe() * this.calculScutiriTaxe() / 100;
	}

	public double getTotalTaraFaraTaxe(String tara) {
		double sum = 0;
		for(Factura f : facturi) {
			sum += f.getTotalTaraFaraTaxe(tara);
		}
		
		return sum;
	}

	public double getTotalTaraCuTaxe(String tara) {
		double sum = 0;
		for(Factura f : facturi) {
			sum += f.getTotalTaraCuTaxe(tara);
		}
		
		return sum;
	}

	public double getTotalTaraCuTaxeScutite(String tara) {
		return this.getTotalTaraCuTaxe(tara) - this.getTotalTaraCuTaxe(tara) * this.calculScutiriTaxe() / 100.d;
	}
	
	public double getTotalCategorieFaraTaxe(String categorie) {
		double sum = 0;
		for(Factura f : facturi) {
			sum += f.getTotalCategorieFaraTaxe(categorie);
		}
		
		return sum;
	}

	public double getTotalCategorieCuTaxe(String categorie) {
		double sum = 0;
		for(Factura f : facturi) {
			sum += f.getTotalCategorieCuTaxe(categorie);
		}
		
		return sum;
	}

	public double getTotalCategorieCuTaxeScutite(String categorie) {
		return this.getTotalCategorieCuTaxeScutite(categorie) - this.getTotalCategorieCuTaxeScutite(categorie) * this.calculScutiriTaxe() / 100;
	}
	
	public String getTipMagazin() {
		return Tip;
	}
	
	public String toString() {
		return this.nume + " - Nr. Facturi: " + this.facturi.size();
	}
	
	@Override
	public int compareTo(IMagazin o) {
		return (int)(this.getTotalFaraTaxe() - o.getTotalFaraTaxe());
	}
	
	public abstract String getTip();
}
