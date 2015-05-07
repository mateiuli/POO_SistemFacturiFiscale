package SistemFacturi;

public class Produs {
	private String denumire = null;
	private String categorie = null;
	private String taraOrigine = null;
	private double pret;
	
	public Produs() {
		
	}
	
	public Produs(String nume, String categorie, String tara) {
		this.denumire = nume;
		this.categorie = categorie;
		this.taraOrigine = tara;
	}
	
	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}
	
	public String getDenumire() {
		return this.denumire;
	}
	
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	
	public String getCategorie() {
		return this.categorie;
	}
	
	public void setTaraOrigine(String tara) {
		this.taraOrigine = tara;
	}
	
	public String getTaraOrigine() {
		return this.taraOrigine;
	}
	
	public void setPret(double pret) {
		this.pret = pret;
	}
	
	public double getPret() {
		return this.pret;
	}
	
	public String toString() {
		return new String(denumire + ": [" + categorie + "] " + taraOrigine + " - " + pret);		
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Produs))
			return false;
		
		Produs p = (Produs) o;
		return 	getDenumire().equals(p.getDenumire()) &&
				getCategorie().equals(p.getCategorie()) &&
				getTaraOrigine().equals(p.getTaraOrigine()) &&
				getPret() == p.getPret();
	}
	
	public boolean isLike(Produs p) {
		boolean ret = false;
		boolean e = false;
		
		if(p.getDenumire() != null && getDenumire() != null)
			ret = getDenumire().toLowerCase().contains(p.getDenumire().toLowerCase());
		else {
			ret = true;
			e = true;
		}
		
		if(p.getCategorie() != null && getCategorie() != null)
			ret = ret && getCategorie().toLowerCase().contains(p.getCategorie().toLowerCase());
		else {
			if(e)
				ret = true;
		}			
		
		if(p.getTaraOrigine() != null && getTaraOrigine() != null)
			ret = ret && getTaraOrigine().toLowerCase().contains(p.getTaraOrigine().toLowerCase());
		
		return ret;
	}
}
            