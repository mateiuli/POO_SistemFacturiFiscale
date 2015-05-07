package GUI;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ProduseCellRenderer extends DefaultTableCellRenderer {
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

	    // cells are by default rendered as a JLabel.
	    JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

	    // get the status for the current row.
	    ProdusTableModel tableModel = (ProdusTableModel) table.getModel();
	    // System.out.println(col);
	    if (col == ProdusTableModel.PRET_COLUMN) {
	    	l.setBackground(Color.RED);
	    } else {
	    	l.setForeground(Color.BLACK);
	    }

	    return l;
	}
}
