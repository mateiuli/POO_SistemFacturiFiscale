package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FileChooserUI;

import SistemFacturi.Gestiune;
import SistemFacturi.User;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JTextField;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JProgressBar;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileFilter;
import java.awt.SystemColor;

import javax.swing.Box;
import javax.swing.border.LineBorder;

import java.awt.Component;

import javax.swing.JSeparator;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private Gestiune gestiune = null;
	private User user;
	private JTextField taxeFilePath;
	private JTextField produseFilePath;
	private JTextField facturiFilePath;
	private JTextField rezultateFilePath;
	private JProgressBar loadingProgress;
	private JButton btnBrowseProduse;
	private JButton btnBrowseFacturi;
	private JButton btnBrowseRezultate;
	private JButton btnBrowseTaxe;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private JButton btnProduse;
	private JButton btnStatistici;
	/**
	 * Create the frame.
	 */
	public MainFrame(User user) {
		setResizable(false);
		this.user = user;
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/GUI/dollar2_icon.png")));
		setTitle("Sistem de Facturi Fiscale");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 533, 476);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblInfoLabel = new JLabel("Esti autentificat ca " + user);
		lblInfoLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblInfoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblInfoLabel.setBounds(192, 3, 325, 16);
		contentPane.add(lblInfoLabel);
		
		JLabel lblIncarcareDate = new JLabel("Incarcare fisiere cu date");
		lblIncarcareDate.setFont(new Font("Dialog", Font.BOLD, 16));
		lblIncarcareDate.setBounds(31, 30, 224, 30);
		contentPane.add(lblIncarcareDate);
		
		JLabel lblFisierTaxe = new JLabel("Fisier taxe");
		lblFisierTaxe.setFont(new Font("Dialog", Font.BOLD, 12));
		lblFisierTaxe.setBounds(31, 67, 70, 15);
		contentPane.add(lblFisierTaxe);
		
		taxeFilePath = new JTextField();
		taxeFilePath.setText("taxe.txt");
		taxeFilePath.setColumns(10);
		taxeFilePath.setBackground(Color.WHITE);
		taxeFilePath.setBounds(31, 83, 200, 30);
		contentPane.add(taxeFilePath);
		
		JLabel lblFisierProduse = new JLabel("Fisier produse");
		lblFisierProduse.setFont(new Font("Dialog", Font.BOLD, 12));
		lblFisierProduse.setBounds(31, 124, 94, 15);
		contentPane.add(lblFisierProduse);
		
		produseFilePath = new JTextField();
		produseFilePath.setText("produse.txt");
		produseFilePath.setColumns(10);
		produseFilePath.setBackground(Color.WHITE);
		produseFilePath.setBounds(31, 145, 200, 30);
		contentPane.add(produseFilePath);
		
		JLabel lblFisierFacturi = new JLabel("Fisier facturi");
		lblFisierFacturi.setFont(new Font("Dialog", Font.BOLD, 12));
		lblFisierFacturi.setBounds(31, 186, 94, 15);
		contentPane.add(lblFisierFacturi);
		
		facturiFilePath = new JTextField();
		facturiFilePath.setText("facturi.txt");
		facturiFilePath.setColumns(10);
		facturiFilePath.setBackground(Color.WHITE);
		facturiFilePath.setBounds(31, 202, 200, 30);
		contentPane.add(facturiFilePath);
		
		JLabel lblFisierRezultate = new JLabel("Fisier rezultate");
		lblFisierRezultate.setForeground(Color.RED);
		lblFisierRezultate.setFont(new Font("Dialog", Font.BOLD, 12));
		lblFisierRezultate.setBounds(31, 243, 94, 15);
		contentPane.add(lblFisierRezultate);
		
		rezultateFilePath = new JTextField();
		rezultateFilePath.setText("out.txt");
		rezultateFilePath.setColumns(10);
		rezultateFilePath.setBackground(Color.WHITE);
		rezultateFilePath.setBounds(31, 259, 200, 30);
		contentPane.add(rezultateFilePath);
		
		btnBrowseTaxe = new JButton("Browse...");
		btnBrowseTaxe.setBounds(241, 83, 87, 30);
		contentPane.add(btnBrowseTaxe);
		
		btnBrowseProduse = new JButton("Browse...");
		btnBrowseProduse.setBounds(241, 145, 87, 30);
		contentPane.add(btnBrowseProduse);
		
		btnBrowseFacturi = new JButton("Browse...");
		btnBrowseFacturi.setBounds(241, 202, 87, 30);
		contentPane.add(btnBrowseFacturi);
		
		btnBrowseRezultate = new JButton("Browse...");
		btnBrowseRezultate.setBounds(241, 259, 87, 30);
		contentPane.add(btnBrowseRezultate);
		
		JLabel lblNewLabel_1 = new JLabel((String) null);
		lblNewLabel_1.setIcon(new ImageIcon(MainFrame.class.getResource("/GUI/database_icon.png")));
		lblNewLabel_1.setBounds(371, 111, 128, 147);
		contentPane.add(lblNewLabel_1);
				
		// ca sa nu scriu de 4 ori acelasi lucru, fac un for
		JButton browseButtons[] = {btnBrowseTaxe, btnBrowseProduse, btnBrowseFacturi};
		JTextField fields[] = {taxeFilePath, produseFilePath, facturiFilePath};
		
		// Deschidere fisier
		for(int i = 0; i < 3; i++) {
			JButton button = browseButtons[i];
			final JTextField textField = fields[i];
			
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser fc = new JFileChooser();
					fc.setDialogTitle("Incarcare fisiere cu date");
					fc.setDialogType(JFileChooser.OPEN_DIALOG);
					fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
					fc.setFileFilter(new FileNameExtensionFilter("Doar fisiere text", "txt"));
					fc.showOpenDialog(fc);
					
					if(fc.getSelectedFile() != null) {
						textField.setText(fc.getSelectedFile().getAbsolutePath());
					}
				}
			});
		}
		
		// salvare fisier
		btnBrowseRezultate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogType(JFileChooser.SAVE_DIALOG);
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.setDialogTitle("Specifica unde va fi salvat fisierul rezultatele");				
				//fc.setFileFilter(new FileNameExtensionFilter("Doar fisiere text", "txt"));
				fc.showOpenDialog(fc);
				
				if(fc.getSelectedFile() != null) {
					rezultateFilePath.setText(fc.getSelectedFile().getAbsolutePath());
				}
			}
		});
		
		loadingProgress = new JProgressBar();
		loadingProgress.setStringPainted(true);
		loadingProgress.setToolTipText("Progres incarcare fisier");
		loadingProgress.setBounds(213, 313, 286, 30);
		contentPane.add(loadingProgress);
		
		JButton btnIncarca = new JButton("Incarca");
		btnIncarca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadingProgress.setValue(0);
				initializeGestiune();
				JOptionPane.showMessageDialog(null, "Datele au fost incarcate cu success!", "Success!", JOptionPane.INFORMATION_MESSAGE);
				// activeaza butoanele pentru gestiune
				activateGestiuneButtons();				
			}
		});
		
		btnIncarca.setBackground(new Color(240, 240, 240));
		btnIncarca.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnIncarca.setForeground(Color.RED);
		btnIncarca.setBounds(31, 313, 169, 30);
		contentPane.add(btnIncarca);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 300, 527, 2);
		contentPane.add(separator);
		
		separator_1 = new JSeparator();
		separator_1.setBounds(0, 28, 527, 2);
		contentPane.add(separator_1);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon(MainFrame.class.getResource("/GUI/main_back.jpg")));
		lblNewLabel.setBounds(0, 3, 527, 351);
		contentPane.add(lblNewLabel);
		
		separator_2 = new JSeparator();
		separator_2.setBounds(0, 354, 527, 2);
		contentPane.add(separator_2);
		
		btnProduse = new JButton("Produse");
		btnProduse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProduseFrame pf = new ProduseFrame();
				pf.setVisible(true);
			}
		});
		btnProduse.setForeground(SystemColor.textHighlight);
		btnProduse.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnProduse.setBackground(SystemColor.menu);
		btnProduse.setBounds(31, 398, 224, 30);
		contentPane.add(btnProduse);
		
		JLabel lblGestionare = new JLabel("Gestionare");
		lblGestionare.setFont(new Font("Dialog", Font.BOLD, 16));
		lblGestionare.setBounds(31, 362, 224, 30);
		contentPane.add(lblGestionare);
		
		btnStatistici = new JButton("Statistici");
		btnStatistici.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StatisticiFrame sf = new StatisticiFrame();
				sf.setVisible(true);
			}
		});
		btnStatistici.setForeground(SystemColor.textHighlight);
		btnStatistici.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnStatistici.setBackground(SystemColor.menu);
		btnStatistici.setBounds(281, 398, 224, 30);
		contentPane.add(btnStatistici);		
		deactivateGestiuneButtons();
	}
	
	public void initializeGestiune() {
		this.gestiune = Gestiune.getInstance();
		this.gestiune.setTaxeFileName(this.taxeFilePath.getText().trim());
		this.gestiune.setProduseFileName(this.produseFilePath.getText().trim());
		this.gestiune.setFacturiFileName(this.facturiFilePath.getText().trim());
		this.gestiune.setOutFileName(this.rezultateFilePath.getText().trim());
		this.gestiune.setProgressBar(this.loadingProgress);
		
		this.gestiune.readData();
		this.gestiune.writeOut();
	}
	
	public void activateGestiuneButtons() {
		this.btnProduse.setEnabled(true);
		this.btnStatistici.setEnabled(true);
	}
	
	public void deactivateGestiuneButtons() {
		this.btnProduse.setEnabled(false);
		this.btnStatistici.setEnabled(false);
	}
}
