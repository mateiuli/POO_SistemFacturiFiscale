package SistemFacturi;
import java.util.Vector;


public class Factura implements Comparable<Factura> {
	public String denumire;
	public Vector<ProdusComandat> produse;
	
	public Factura() {
		this.produse = new Vector<>();
	}
	
	public Factura(String denumire) {
		this();
		this.denumire = denumire;
	}
	
	public double getTotalFaraTaxe() {
		double sum = 0;
		for(ProdusComandat pc : produse) {
			Produs p = pc.getProdus();
			sum += p.getPret() * pc.getCantitate();
		}
		
		return sum;
	}
	
	public double getTotalCuTaxe() {
		return this.getTotalFaraTaxe() + this.getTaxe();
//		double sum = 0;
//		for(ProdusComandat pc : produse) {
//			Produs p = pc.getProdus();
//			sum += (p.getPret() + p.getPret() * pc.getTaxa() / 100.d)* pc.getCantitate();
//		}
//		
//		return sum;
	}
	
	public double getTaxe() {
		double sum = 0;
		
		for(ProdusComandat pc : produse) {
			Produs p = pc.getProdus();
			sum += pc.getTaxa() * p.getPret() * pc.getCantitate() / 100.d;
		}
		
		return sum;
	}
		
	public double getTotalTaraFaraTaxe(String tara) {
		double sum = 0;
		for(ProdusComandat pc : produse) {
			Produs p = pc.getProdus();
			
			if(p.getTaraOrigine().compareTo(tara) != 0)
				continue;
			
			sum += p.getPret() * pc.getCantitate();
		}
		
		return sum;
	}
	
	public double getTotalTaraCuTaxe(String tara) {
		return this.getTotalTaraFaraTaxe(tara) + this.getTaxeTara(tara);
	}
	
	public double getTaxeTara(String tara) {
		double sum = 0;
		
		for(ProdusComandat pc : produse) {
			Produs p = pc.getProdus();
			
			if(p.getTaraOrigine().compareTo(tara) != 0)
				continue;
			
			sum += pc.getTaxa() * p.getPret() * pc.getCantitate() / 100.d;
		}
		
		return sum;	
	}
	
	public double getTotalCategorieFaraTaxe(String categorie) {
		double sum = 0;
		for(ProdusComandat pc : produse) {
			Produs p = pc.getProdus();
			
			if(p.getCategorie().compareTo(categorie) != 0)
				continue;
			
			sum += p.getPret() * pc.getCantitate();
		}
		
		return sum;
	}
	
	public double getTotalCategorieCuTaxe(String categorie) {
		return this.getTotalCategorieFaraTaxe(categorie) + this.getTaxeCategorie(categorie);
	}
	
	public double getTaxeCategorie(String categorie) {
		double sum = 0;
		
		for(ProdusComandat pc : produse) {
			Produs p = pc.getProdus();
			
			if(p.getCategorie().compareTo(categorie) != 0)
				continue;
			
			sum += pc.getTaxa() * p.getPret() * pc.getCantitate() / 100.d;
		}
		
		return sum;	
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer("Factura: " + this.denumire + "\n");
		for(ProdusComandat pc : produse)
			sb.append(pc.toString() + "\n");
		
		return sb.toString();
	}

	public int compareTo(Factura o) {
		return (int) (this.getTotalCuTaxe() - o.getTotalCuTaxe());
	}
}
