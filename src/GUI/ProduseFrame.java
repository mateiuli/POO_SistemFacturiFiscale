package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import SistemFacturi.Gestiune;
import SistemFacturi.Produs;

import javax.swing.table.TableModel;

import java.awt.Dialog.ModalExclusionType;

import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ComboBoxModel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class ProduseFrame extends JFrame {

	private JPanel contentPane;
	private JTable produseTable;
	private ProdusTableModel tableModel;
	private JScrollPane scrollPane;
	private JMenuItem mntmStergere;
	private JMenuItem mntmStergere_1;
	private JTextField fieldNumeProdus;
	private JLabel lblTaraOrigine;
	private JTextField fieldPret;
	private JLabel label;
	private JButton btnNewButton;
	private JComboBox fieldTaraOrigine;
	private JLabel lblCategorie;
	private JComboBox fieldCategorieProdus;
	private JMenu mnFiltre;
	private JCheckBoxMenuItem checkboxToateProdusele;
	private JMenu mnFisier;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmIesire;
	private boolean dataChanged = false;
	/**
	 * Create the frame.
	 */
	public ProduseFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(!dataChanged) {
					dispose();
					return;
				}
				
				int option = JOptionPane.showConfirmDialog(null, "Doriti sa salvati modificarile?", "Salvati modoficarile?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(option == JOptionPane.YES_OPTION) {
					// salveaza produsele in fisier
					Gestiune.getInstance().writeProduse();
				}
				
				dispose();
			}
		});
		setResizable(false);
		setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
		setTitle("Gestiune Produse");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProduseFrame.class.getResource("/GUI/dollar2_icon.png")));
		setBounds(100, 100, 650, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
			
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 523, 24);
		contentPane.add(menuBar);
		
		mnFisier = new JMenu("Fisier");
		mnFisier.setIcon(new ImageIcon(ProduseFrame.class.getResource("/GUI/file_icon.png")));
		menuBar.add(mnFisier);
		
		mntmNewMenuItem = new JMenuItem("Salvare");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// salveaza produsele in fisier
				Gestiune.getInstance().writeProduse();
				JOptionPane.showMessageDialog(null, "Modificarile au fost salvate in fisier.", "Success!", JOptionPane.INFORMATION_MESSAGE);
				dataChanged = false;
			}
		});
		mntmNewMenuItem.setIcon(new ImageIcon(ProduseFrame.class.getResource("/GUI/save_icon.png")));
		mnFisier.add(mntmNewMenuItem);
		
		mntmIesire = new JMenuItem("Iesire");
		mntmIesire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		mnFisier.add(mntmIesire);
		
		JMenu mnProduse = new JMenu("Produse");
		mnProduse.setIcon(new ImageIcon(ProduseFrame.class.getResource("/GUI/product_icon.png")));
		menuBar.add(mnProduse);
		 	
		tableModel = new ProdusTableModel(Gestiune.getInstance().produse);
		produseTable = new JTable(tableModel);
		produseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		produseTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
		produseTable.setFillsViewportHeight(true);
		produseTable.setBounds(0, 26, 500, 200);
		//tableModel.setColumnIdentifiers(new String[] {"Nr #", "Denumire", "Categorie", "Tara origine", "Pret"});
		//produseTable.setDefaultRenderer(String.class, new ProduseCellRenderer());
		produseTable.getColumn("Nr #").setPreferredWidth(15);
		produseTable.setAutoCreateRowSorter(true);
		
		// coloane JComboBox
		TableColumn colCategorie = produseTable.getColumnModel().getColumn(2);
		TableColumn colTaraOrigine = produseTable.getColumnModel().getColumn(3);
		
		JComboBox comboCat = new JComboBox(new CategoriiComboBoxModel(Gestiune.getInstance().getCategoriiList()));
		JComboBox comboTara = new JComboBox(new TariComboBoxModel(Gestiune.getInstance().getTariList()));
		
		colCategorie.setCellEditor(new DefaultCellEditor(comboCat));
		colTaraOrigine.setCellEditor(new DefaultCellEditor(comboTara));
		
		produseTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			        public void valueChanged(ListSelectionEvent evt) {
			        	dataChanged = true;
			        }
			    }
			);
		
		scrollPane = new JScrollPane(produseTable);
		scrollPane.setViewportBorder(null);
		scrollPane.setSize(645, 250);
		scrollPane.setLocation(0, 23);
		contentPane.add(scrollPane);
		
		mntmStergere = new JMenuItem("Cauta produs");
		mntmStergere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				produseTable.clearSelection();
				InputSearchPanel searchPanel = new InputSearchPanel();
				JOptionPane.showMessageDialog(null, searchPanel, "Cautare", JOptionPane.PLAIN_MESSAGE);
				
				// cauta un produs asemanator
				int row = tableModel.getRowBySearch(searchPanel.getDenumire(),
													searchPanel.getCategorie(),
													searchPanel.getTaraOrigine());
				if(row < 0) {
					if(row == -1)
						JOptionPane.showMessageDialog(null, "Niciun produs nu corespunde criteriilor!", "Info", JOptionPane.INFORMATION_MESSAGE);
					
					return;
				}
				
				produseTable.setRowSelectionInterval(row, row);			
			}
		});
		mntmStergere.setIcon(new ImageIcon(ProduseFrame.class.getResource("/GUI/edit_icon.png")));
		mnProduse.add(mntmStergere);
		
		mntmStergere_1 = new JMenuItem("Sterge");
		mntmStergere_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(produseTable.getSelectionModel().getValueIsAdjusting())
					return;
				
				int selectedRow = produseTable.getSelectionModel().getMinSelectionIndex();
				if(selectedRow == -1)
					return;
				
				dataChanged = true;
				// sterg elementul din model
				tableModel.removeRow(selectedRow);
				// fac update la tabel
				tableModel.fireTableDataChanged();
			}
		});
		
		mntmStergere_1.setIcon(new ImageIcon(ProduseFrame.class.getResource("/GUI/file_delete.png")));
		mnProduse.add(mntmStergere_1);
		
		mnFiltre = new JMenu("Filtre");
		mnFiltre.setIcon(new ImageIcon(ProduseFrame.class.getResource("/GUI/ICN_Filter.png")));
		menuBar.add(mnFiltre);
		
		checkboxToateProdusele = new JCheckBoxMenuItem("Afiseaza toate produsele");
		checkboxToateProdusele.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				// se afiseaza toate produsele sau doar cele cu pretul != 0
				tableModel.setAfiseazaProdusePretZero(checkboxToateProdusele.isSelected());				
				// refresh la tabel
				tableModel.fireTableDataChanged();
			}
		});
		mnFiltre.add(checkboxToateProdusele);
		
		JLabel lblAdaugareProdusNou = new JLabel("Adaugare produs nou");
		lblAdaugareProdusNou.setFont(new Font("Dialog", Font.BOLD, 16));
		lblAdaugareProdusNou.setBounds(15, 285, 224, 30);
		contentPane.add(lblAdaugareProdusNou);
		
		JLabel lblNumeProdus = new JLabel("Nume produs");
		lblNumeProdus.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNumeProdus.setBounds(15, 322, 121, 15);
		contentPane.add(lblNumeProdus);
		
		fieldNumeProdus = new JTextField();
		fieldNumeProdus.setColumns(10);
		fieldNumeProdus.setBackground(Color.WHITE);
		fieldNumeProdus.setBounds(15, 338, 230, 30);
		contentPane.add(fieldNumeProdus);
		
		lblTaraOrigine = new JLabel("Tara origine");
		lblTaraOrigine.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTaraOrigine.setBounds(15, 379, 121, 15);
		contentPane.add(lblTaraOrigine);
		
		fieldTaraOrigine = new JComboBox(new TariComboBoxModel(Gestiune.getInstance().getTariList()));
		fieldTaraOrigine.setSelectedIndex(0);
		fieldTaraOrigine.setBackground(Color.WHITE);
		fieldTaraOrigine.setBounds(15, 395, 230, 30);
		contentPane.add(fieldTaraOrigine);
		
		JLabel lblPret = new JLabel("Pret");
		lblPret.setFont(new Font("Dialog", Font.BOLD, 12));
		lblPret.setBounds(250, 378, 121, 15);
		contentPane.add(lblPret);
		
		fieldPret = new JTextField();
		fieldPret.setColumns(10);
		fieldPret.setBackground(Color.WHITE);
		fieldPret.setBounds(255, 395, 230, 30);
		contentPane.add(fieldPret);
		
		btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Adaugare produs
				adaugaProdus();
				
				// Fac refresh la tabel
				tableModel.fireTableDataChanged();
			}
		});
		btnNewButton.setIcon(new ImageIcon(ProduseFrame.class.getResource("/GUI/add_produs2.png")));
		btnNewButton.setBounds(509, 338, 106, 87);
		contentPane.add(btnNewButton);
		
		lblCategorie = new JLabel("Categorie");
		lblCategorie.setFont(new Font("Dialog", Font.BOLD, 12));
		lblCategorie.setBounds(255, 322, 121, 15);
		contentPane.add(lblCategorie);
		
		fieldCategorieProdus = new JComboBox(new CategoriiComboBoxModel(Gestiune.getInstance().getCategoriiList()));
		fieldCategorieProdus.setSelectedIndex(0);
		fieldCategorieProdus.setBackground(Color.WHITE);
		fieldCategorieProdus.setBounds(255, 338, 230, 30);
		contentPane.add(fieldCategorieProdus);
		
		label = new JLabel(new ImageIcon(ProduseFrame.class.getResource("/GUI/main_back.jpg")));
		label.setBounds(0, 275, 645, 163);
		contentPane.add(label);	
	}
	
	public void adaugaProdus() {
		String denumire = fieldNumeProdus.getText().trim();
		String categorie = ((String)fieldCategorieProdus.getSelectedItem()).trim();
		String taraOrigine = ((String)fieldTaraOrigine.getSelectedItem()).trim();
		String pretText = fieldPret.getText().trim();
		
		if(denumire.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Seteaza numele produsului", "Eroare!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(categorie.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Seteaza categoria produsului!", "Eroare!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(taraOrigine.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Selecteaza tara de origine!", "Eroare!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(pretText.isEmpty()) {			
			JOptionPane.showMessageDialog(null, "Seteaza pretul produsului!", "Eroare!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Double pret = null;
		try {
			pret = new Double(pretText);
		}
		catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Pretul e invalid!", "Eroare!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// creez noul produs
		Produs produs = new Produs();
		produs.setDenumire(denumire);
		produs.setCategorie(categorie);
		produs.setTaraOrigine(taraOrigine);
		produs.setPret(pret);
				
		// verific daca produsul exista deja
		if(Gestiune.getInstance().produse.contains(produs)) {
			JOptionPane.showMessageDialog(null, "Produsul exista deja!", "Eroare!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// adaug noul produs
		Gestiune.getInstance().produse.add(produs);
		
		// Afisez un mesaj de confirmare
		JOptionPane.showMessageDialog(null, "Produsul a fost adaugat cu succes!", "Success!", JOptionPane.INFORMATION_MESSAGE);
		
		// golesc campurile pentru adaugare altui produs
		fieldNumeProdus.setText("");
		fieldCategorieProdus.setSelectedIndex(0);
		fieldTaraOrigine.setSelectedIndex(0);
		fieldPret.setText("");
	}
	
	
}
