package SistemFacturi;

public class ProdusComandat {
	private Produs produs;
	private Double taxa;
	private int cantitate;
	
	public ProdusComandat() {
		
	}
	
	public ProdusComandat(Produs produs) {
		this.produs = produs;
	}
	
	public ProdusComandat(Produs produs, int cantitate) {
		this.produs = produs;
		this.cantitate = cantitate;
	}
	
	public ProdusComandat(Produs produs, int cantitate, Double taxa) {
		this.produs = produs;
		this.cantitate = cantitate;
		this.taxa = taxa;
	}
	
	public void setProdus(Produs produs) {
		this.produs = produs;
	}
	
	public Produs getProdus() {
		return this.produs;
	}
	
	public void setTaxa(Double taxa) {
		this.taxa = taxa;
	}
	
	public Double getTaxa() {
		return this.taxa;
	}
	
	public void setCantitate(int cantitate) {
		this.cantitate = cantitate;
	}
	
	public int getCantitate() {
		return this.cantitate;
	}
	
	public String toString() {
		return produs.toString() + " | Cantitate: " + cantitate + " | Taxa: " + taxa;
	}
}
