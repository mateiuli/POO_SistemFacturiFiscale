package SistemFacturi;

public class MagazinFactory {
	public Magazin createMarket(String nume, String tipMagazin) {
		if(tipMagazin.equals("MiniMarket"))
			return new MiniMarket(nume);
		
		if(tipMagazin.equals("MediumMarket"))
			return new MediumMarket(nume);
		
		if(tipMagazin.equals("HyperMarket"))
			return new HyperMarket(nume);
		
		return null;
	}
}
