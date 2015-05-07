package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputSearchPanel extends JPanel {
	
	private JTextField denumire;
	private JTextField categorie;
	private JTextField taraOrigine;
	
	public InputSearchPanel() {
		super();
		denumire = new JTextField();
		categorie = new JTextField();
		taraOrigine = new JTextField();
		setPreferredSize(new Dimension(150, 185));
		denumire.setPreferredSize(new Dimension(150, 30));
		categorie.setPreferredSize(new Dimension(150, 30));
		taraOrigine.setPreferredSize(new Dimension(150, 30));
		
		setLayout(new FlowLayout());
		JLabel j = new JLabel("Denumire");
		j.setPreferredSize(new Dimension(150, 20));
		add(j);
		add(denumire);
		
		j = new JLabel("Categorie");
		j.setPreferredSize(new Dimension(150, 20));
		add(j);
	    add(categorie);
	    
	    j = new JLabel("Tara origine");
		j.setPreferredSize(new Dimension(150, 20));
		add(j);
	    add(taraOrigine);
	    add(Box.createHorizontalStrut(15)); // a spacer
	}
	
	public String getDenumire() {
		String d =  this.denumire.getText().trim();
		return d.isEmpty() ? null : d;
	}
	
	public String getCategorie() {
		String d =  this.categorie.getText().trim();
		return d.isEmpty() ? null : d;
	}
	
	public String getTaraOrigine() {
		String d =  this.taraOrigine.getText().trim();
		return d.isEmpty() ? null : d;
	}
}
