package SistemFacturi;

public interface IMagazin extends Comparable<IMagazin>{
	public double getTotalFaraTaxe();
	public double getTotalCuTaxe();
	public double getTotalCuTaxeScutite();
	public double getTotalTaraFaraTaxe(String tara);
	public double getTotalTaraCuTaxe(String tara);
	public double getTotalTaraCuTaxeScutite(String tara);
	public double calculScutiriTaxe();	
}
