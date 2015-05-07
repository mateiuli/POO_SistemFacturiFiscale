package GUI;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import SistemFacturi.Produs;

public class ProdusTableModel extends AbstractTableModel {
	public static final int NO_COLUMN 			= 0;
	public static final int DENUMIRE_COLUMN 	= 1;
	public static final int CATEGORIE_COLUMN 	= 2;
	public static final int TARA_COLUMN 		= 3;
	public static final int PRET_COLUMN 		= 4;
	
	private String[] colNames = {"Nr #", "Denumire", "Categorie", "Tara origine", "Pret"};
	private List<Produs> produse;
	
	// produsele cu pretul 0
	private boolean afiseazaProdusePretZero;
	
	public ProdusTableModel(List<Produs> produse) {
		this.afiseazaProdusePretZero = false;
		this.produse = produse;
	}
	
	public void setAfiseazaProdusePretZero(boolean flag) {
		this.afiseazaProdusePretZero = flag;
	}
	
	public int getColumnCount() {
		return colNames.length;
	}

	public int getRowCount() {
		return produse.size();
	}
	
	public String getColumnName(int columnIndex) {
        return colNames[columnIndex];
    }
	
	public int getRowBySearch(String denumire, String categorie, String tara) {
		Produs produs = new Produs(denumire, categorie, tara);
		
		if(denumire == null && categorie == null && tara == null)
			return -2;
		
		int i = 0;
		for(Produs p : produse) {
			if(p.isLike(produs))
				return i;
			i++;
		}
		
		return -1;
		
		// return produse.indexOf(p);
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		Produs produs = produse.get(rowIndex);
		
		if(produs.getPret() == 0)
			if(!afiseazaProdusePretZero)
				return null;
		
		switch(columnIndex) {
		case NO_COLUMN:
			return rowIndex + 1;

		case DENUMIRE_COLUMN:
			return produs.getDenumire();

		case CATEGORIE_COLUMN:
			return produs.getCategorie();

		case TARA_COLUMN:
			return produs.getTaraOrigine();

		case PRET_COLUMN:
			return produs.getPret();
		}
		
		return null;
	}
	
	public Class<?> getColumnClass(int columnIndex) {
		if (produse.isEmpty())
			return Object.class;
			 
		return getValueAt(0, columnIndex).getClass();
	}
	
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Produs produs = produse.get(rowIndex);
        if (columnIndex == NO_COLUMN)
        	return;      
        
        if(columnIndex == DENUMIRE_COLUMN) {
        	produs.setDenumire((String) value);
        }
        
        if(columnIndex == CATEGORIE_COLUMN) {
        	produs.setCategorie((String) value);
        }
        
        if(columnIndex == TARA_COLUMN) {
        	produs.setTaraOrigine((String) value);
        }
        
        if(columnIndex == PRET_COLUMN) {
        	produs.setPret((Double) value);
        }
    }

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == NO_COLUMN)
			return false;
		
		return true;
	}
	
	public void removeRow(int rowIndex) {
		this.produse.remove(rowIndex);
	}
}
