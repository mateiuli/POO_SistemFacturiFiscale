package SistemFacturi;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JProgressBar;


public class Gestiune {
	public ArrayList<Produs> produse;
	public ArrayList<Magazin> magazine;
	public TreeMap<String, HashMap<String, Double>> taxe;
	private String taxeFilename;
	private String produseFilename;
	private String facturiFilename;
	private String outFilename;
	private JProgressBar progressBar;
	private static Gestiune instance = null;
	private List<String> tariList = null;
	private List<String> categoriiList = null;
	
	private Gestiune() {
		this.taxe = new TreeMap<>();
		this.produse = new ArrayList<>();
		this.magazine = new ArrayList<>();		
	}
	
	public void readData() {
		this.readTaxe();
		progressBar.setValue(25);
		this.readProduse();
		progressBar.setValue(50);
		this.readFacturi();
		progressBar.setValue(75);
	}
	
	public void setTaxeFileName(String file) {
		this.taxeFilename = file;
	}
	
	public void setProduseFileName(String file) {
		this.produseFilename = file;
	}
	
	public void setFacturiFileName(String file) {
		this.facturiFilename = file;
	}
	
	public void setOutFileName(String file) {
		this.outFilename = file;
	}
	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}
	
	/**
	 * Sorteaza datele inainte de a fi scrise in fisier
	 */
	public void sortData() {
		// sortez magazinele
		Collections.sort(magazine);
		
		// sortez facturile in magazine
		for(Magazin m : magazine) {
			Collections.sort(m.facturi);
		}
	}
	
	/**
	 * Scrie datele in fisierul out.txt
	 * @throws FileNotFoundException 
	 */
	public void writeOut() {
		// sorteaza datele
		this.sortData();
		
		try {
			PrintWriter out = new PrintWriter(outFilename);
			String[] tipuriMagazin = {"MiniMarket", "MediumMarket", "HyperMarket"};
			boolean firstTip = true;
			
			for(String tip : tipuriMagazin) {
				if(!firstTip) {
					out.println();
					out.println();
				}
				
				out.println(tip);
				
				boolean firstMag = true;
				for(Magazin m : magazine) {				
					if(m.getTip().equals(tip)) {
						if(!firstMag) {
							out.println();
							out.println();
						}
						
						writeMagazin(out, m);
						firstMag = false;
					}					
				}
				
				firstTip = false;
			}
			
			out.close();
		}
		catch(IOException e) {
			System.err.println("Nu pot scrie in fisierul out.txt");
		}
		
		progressBar.setValue(100);
 	}
	
	private void writeMagazin(PrintWriter out, Magazin magazin) {	
		DecimalFormat fmt = new DecimalFormat();
		fmt.setMaximumFractionDigits(4);
		
		out.format("%s", magazin.nume);		
		out.println();
		out.println();
		// Totalul
		out.print("Total ");
		out.format("%s %s %s", 	
				fmt.format(magazin.getTotalFaraTaxe()),
				fmt.format(magazin.getTotalCuTaxe()),
				fmt.format(magazin.getTotalCuTaxeScutite()));
		out.println();
		out.println();
		
		// Tara
		out.println("Tara");
		boolean firstT = true;
		for(String tara : Gestiune.getInstance().taxe.keySet()) {
			if(!firstT)
				out.println();
			
			if(magazin.getTotalTaraCuTaxe(tara) == 0) {
				out.format("%s 0", tara);
				firstT = false;
				continue;
			}
			
			out.format("%s %s %s %s", tara,
				fmt.format(magazin.getTotalTaraFaraTaxe(tara)),
				fmt.format(magazin.getTotalTaraCuTaxe(tara)),
				fmt.format(magazin.getTotalTaraCuTaxeScutite(tara)));
			
			firstT = false;
		}
		
		// Facturile
		for(Factura f : magazin.facturi) {
			out.println();
			out.println();
		
			out.format("%s", f.denumire);	
			out.println();
			out.println();
		
			// Totalul
			out.print("Total ");
			out.format("%s %s", 	
					fmt.format(f.getTotalFaraTaxe()),
					fmt.format(f.getTotalCuTaxe()));
			out.println();
			out.println();
			
			// Tara
			out.print("Tara");
			out.println();
			
			boolean firstTara = true;
			for(String tara : Gestiune.getInstance().taxe.keySet()) {
				if(!firstTara)
					out.println();
				
				out.format("%s ", tara);				
				if(f.getTotalTaraCuTaxe(tara) == 0) {
					out.print(0);
					firstTara = false;
					continue;
				}
				
				out.format("%s %s",
					fmt.format(f.getTotalTaraFaraTaxe(tara)),
					fmt.format(f.getTotalTaraCuTaxe(tara)));
				firstTara = false;
			}
		}		
	}
	
	/**
	 * Parseaza fisierul taxe.txt
	 */
	private void readTaxe() {
		try {
			RandomAccessFile raf = new RandomAccessFile(taxeFilename, "r");
			
			String line = null;
			boolean firstLine = true;
			
			// array temporar cu numele tarilor
			Vector<String> tari = new Vector<>();
			
			while((line = raf.readLine()) != null) {
				Scanner s = new Scanner(line);
				
				// citesc lista cu tarile
				if(firstLine) {
					// ignor primul token
					s.next();
					
					// citesc toate tarile
					while(s.hasNext()) {
						String tara = s.next().trim();
						tari.add(tara);
						taxe.put(tara, new HashMap<String, Double>());
					}
					
					firstLine = false;
					continue;
				}
				
				// citesc categoria
				String categorie = s.next().trim();
				
				// citesc pentru fiecare tara taxa corespunzatoare 
				// pentru fiecare categorie
				for(int i = 0; i < tari.size(); i++) {
					double val = s.nextDouble();
					taxe.get(tari.get(i)).put(categorie, val);
				}
				
				s.close();
			}
			
			raf.close();
		} catch (IOException e) {
			System.err.println("Nu pot accesa fisierul taxe.txt");
		}
	}
	
	/**
	 * Parseaza fisierul produse.txt
	 */
	private void readProduse() {
		try {
			RandomAccessFile raf = new RandomAccessFile(produseFilename, "r");
			
			String line = null;
			boolean firstLine = true;
			
			// array temporar cu numele tarilor
			Vector<String> tari = new Vector<>();
			
			while((line = raf.readLine()) != null) {
				Scanner s = new Scanner(line);
				
				// citesc lista cu tarile
				if(firstLine) {
					// ignor primele 2 token-uri
					s.next();
					s.next();
					
					// citesc toate tarile
					while(s.hasNext()) {
						String tara = s.next().trim();
						tari.add(tara);
					}
					
					firstLine = false;
					continue;
				}
				
				// citesc numele produsului
				String denumire = s.next().trim();
				
				// citesc categoria
				String categorie = s.next().trim();
				
				// citesc pentru fiecare tara taxa corespunzatoare 
				// pentru fiecare p
				for(int i = 0; i < tari.size(); i++) {
					double val = s.nextDouble();
					
					Produs produs = new Produs();
					produs.setDenumire(denumire);
					produs.setCategorie(categorie);
					produs.setPret(val);
					produs.setTaraOrigine(tari.get(i));
					this.produse.add(produs);
				}
				
				s.close();
			}
			
			raf.close();
		} catch (IOException e) {
			System.err.println("Nu pot accesa fisierul produse.txt");
		}
	}
	
	/**
	 * Parseaza fisierul facturi.txt
	 */
	private void readFacturi() {
		// fabrica de magazina :D
		MagazinFactory magazinFactory = new MagazinFactory();
		
		try {
			RandomAccessFile raf = new RandomAccessFile(facturiFilename, "r");
			String line = null;
						
			while((line = raf.readLine()) != null) {
				line = line.trim();
				if(line.isEmpty())
					continue;
				
				// antetul unui magazin
				if(line.startsWith("Magazin:")) {
					Scanner scan = new Scanner(line);
					scan.useDelimiter(":");
					// ignor cuvantul "Magazin"
					scan.next();
					
					// Creez un magazin nou
					String tipMagazin = scan.next().trim();
					String numeMagazin = scan.next().trim();
					magazine.add(magazinFactory.createMarket(numeMagazin, tipMagazin));
					scan.close();
				}
				
				// antent factura scan.hasNext("Factura(\\d+)")
				if(line.startsWith("Factura")) {
					Factura f = readFactura(raf, line);
				
					// adaug factura la ultimul magazin
					if(this.magazine.size() == 0)
						throw new Exception("Format invalid facturi.txt");
					
					magazine.get(magazine.size() - 1).facturi.add(f);
					
				}			
			}
						
			raf.close();
		}
		catch(IOException e) {
			System.err.println("Nu pot accesa fisierul produse.txt");
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Citeste o factura din fisier, de la pozitia
	 * curenta retinuta in 'raf'
	 * @param raf
	 * @param denumire
	 * @return
	 * @throws IOException
	 */
	private Factura readFactura(RandomAccessFile raf, String denumire) throws IOException {
		// sar peste antetul tabelului
		// "DenumireProdus Tara Cantitate"
		String line = raf.readLine().trim();
		Factura f = new Factura(denumire);
		
		// citesc produsele de pe factura
		while((line = raf.readLine()) != null) {
			line = line.trim();
			if(line.isEmpty())
				break;
			
			Scanner s = new Scanner(line);
			// Produsul comandat
			String denumireProdus = s.next();
			String taraOrigine = s.next();
			int cantitateProdus = s.nextInt();
			s.close();
			
			// caut produsul citit in lista de produse
			Produs p = cautaProdus(denumireProdus, taraOrigine);
			
			// igonra daca produsul de pe factura nu exista
			if(p == null)
				continue;
			
			// caut taxa asociata acestui produs
			ProdusComandat pc = new ProdusComandat(p, cantitateProdus, getTaxaProdus(p));
			f.produse.add(pc);
		}
		
		return f;
	}
	
	/**
	 * Cauta un produs pe baza denumirii si a tarii
	 * @param denumire
	 * @param tara
	 * @return
	 */
	private Produs cautaProdus(String denumire, String tara) {
		for(Produs p : produse)
			if(p.getDenumire().equals(denumire) && p.getTaraOrigine().equals(tara))
				return p;
		
		return null;
	}
	
	/**
	 * Returneaza taxa asociata categoriei
	 * din care face parte produsul
	 * @param produs
	 * @return
	 */
	private Double getTaxaProdus(Produs produs) {
		return taxe.get(produs.getTaraOrigine()).get(produs.getCategorie());
	}
	
	/**
	 * Returenaza lista cu tari citite din fisierul taxe.txt
	 * @return
	 */
	public List<String> getTariList() {
		if(taxe.isEmpty())
			return null;
		
		if(tariList != null)
			return tariList;
		
		tariList = new ArrayList<>();
		tariList.addAll(taxe.keySet());
		return tariList;
	}
	
	/**
	 * Returenaza lista cu categorii
	 * @return
	 */
	public List<String> getCategoriiList() {
		if(taxe.isEmpty())
			return null;
		
		if(categoriiList != null)
			return categoriiList;
		
		categoriiList = new ArrayList<>();
		categoriiList.addAll(taxe.get(taxe.firstKey()).keySet());
		return categoriiList;
	}
	
	/**
	 * Suprascrie fisierul cu produsele, salvand in el
	 * noile produse dupa modificari
	 */
	public void writeProduse() {
		DecimalFormat fmt = new DecimalFormat();
		
		try {
			PrintWriter pw = new PrintWriter(produseFilename);
			// scriu headerul fisierului
			pw.print("Produs Categorie");
			for(String tara : getTariList()) 
				pw.print(" " + tara);
			pw.println();
			
			// Produsele in ordinea intrarii, unice
			ArrayList<String> produseUnice = new ArrayList<>();
			HashMap<String, String> categorii = new HashMap<>();
			ArrayList<String> tariUnice = (ArrayList<String>) getTariList();
			// tarile sortate
			Collections.sort(tariUnice);
			
			for(Produs p : produse) {
				if(!produseUnice.contains(p.getDenumire()))
					produseUnice.add(p.getDenumire());
				
				if(!categorii.containsKey(p.getDenumire()))
					categorii.put(p.getDenumire(), p.getCategorie());
			}
						
			HashMap<String, HashMap<String, Double>> pretTara = new HashMap<String, HashMap<String,Double>>();
			
			for(Produs p : produse) {
				if(!pretTara.containsKey(p.getDenumire()))
					pretTara.put(p.getDenumire(), new HashMap<String, Double>());
				
				pretTara.get(p.getDenumire()).put(p.getTaraOrigine(), p.getPret());
			}
						
			Iterator<String> numeProdus = produseUnice.iterator();
					
			while(numeProdus.hasNext()) {
				String nume = numeProdus.next();
				String categorie = categorii.get(nume);
				
				pw.print(nume + " " + categorie);
				
				for(String tara : tariUnice) {
					pw.print(" " + fmt.format(getPretProdusCategorieTara(nume, categorie, tara)));
				}
					
				pw.println();
			}
					
			pw.close();
		}
		catch(IOException e) {
			System.err.println("Nu pot scrie in " + produseFilename + "\n" + e.getMessage());
		}
	}
	
	public double getPretProdusCategorieTara(String nume, String categorie, String tara) {
		for(Produs p : produse) {
			if(p.getDenumire().equals(nume) && p.getCategorie().equals(categorie) && p.getTaraOrigine().equals(tara))
				return p.getPret();
		}
		
		return 0;
	}
	
	public static Gestiune getInstance() {
		if(instance == null)
			instance = new Gestiune();
		
		return instance;
	}

	public static String prettyDouble(double d) {
		if(d == (int)d)
			return String.format("%d", (int)d);
		
		return String.format("%s", d);
	}
}
