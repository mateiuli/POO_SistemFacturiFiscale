package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JEditorPane;

import SistemFacturi.Factura;
import SistemFacturi.Gestiune;
import SistemFacturi.Magazin;

import javax.swing.JScrollPane;

import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JSeparator;

public class StatisticiFrame extends JFrame {

	private JPanel contentPane;
	private JEditorPane magazinMaxPane;
	private JScrollPane scrollPane;
	private JLabel lblMagazinulCuCele_1;
	private JScrollPane scrollPane_1;
	private JEditorPane tariMagazinMaxPane;
	private JEditorPane magazinMaxCategoriePane;
	private JEditorPane facturaMaximaPane;

	/**
	 * Create the frame.
	 */
	public StatisticiFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(StatisticiFrame.class.getResource("/GUI/dollar2_icon.png")));
		setResizable(false);
		setTitle("Statistici");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 851, 557);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMagazinulCuCele = new JLabel("Magazinul cu cele mai mari vanzari");
		lblMagazinulCuCele.setFont(new Font("Dialog", Font.BOLD, 16));
		lblMagazinulCuCele.setBounds(10, 11, 276, 30);
		contentPane.add(lblMagazinulCuCele);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 52, 376, 167);
		contentPane.add(scrollPane);
		
		magazinMaxPane = new JEditorPane();
		scrollPane.setViewportView(magazinMaxPane);
		magazinMaxPane.setEditable(false);
		
		lblMagazinulCuCele_1 = new JLabel("Magazinul cu cele mai mari vanzari pe fiecare tara");
		lblMagazinulCuCele_1.setFont(new Font("Dialog", Font.BOLD, 16));
		lblMagazinulCuCele_1.setBounds(10, 241, 376, 30);
		contentPane.add(lblMagazinulCuCele_1);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 285, 376, 179);
		contentPane.add(scrollPane_1);
		
		tariMagazinMaxPane = new JEditorPane();
		scrollPane_1.setViewportView(tariMagazinMaxPane);
		tariMagazinMaxPane.setText((String) null);
		tariMagazinMaxPane.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tariMagazinMaxPane.setEditable(false);
		
		JLabel lblMagazinulCuCele_2 = new JLabel("Magazinul cu cele mai mari vanzari pe fiecare categorie");
		lblMagazinulCuCele_2.setFont(new Font("Dialog", Font.BOLD, 16));
		lblMagazinulCuCele_2.setBounds(411, 11, 424, 30);
		contentPane.add(lblMagazinulCuCele_2);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(411, 52, 424, 167);
		contentPane.add(scrollPane_2);
		
		magazinMaxCategoriePane = new JEditorPane();
		magazinMaxCategoriePane.setText((String) null);
		magazinMaxCategoriePane.setFont(new Font("Tahoma", Font.PLAIN, 13));
		magazinMaxCategoriePane.setEditable(false);
		scrollPane_2.setViewportView(magazinMaxCategoriePane);
		
		JLabel lblFacturaMaxima = new JLabel("Factura maxima");
		lblFacturaMaxima.setFont(new Font("Dialog", Font.BOLD, 16));
		lblFacturaMaxima.setBounds(411, 241, 376, 30);
		contentPane.add(lblFacturaMaxima);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(411, 285, 424, 179);
		contentPane.add(scrollPane_3);
		
		facturaMaximaPane = new JEditorPane();
		facturaMaximaPane.setText((String) null);
		facturaMaximaPane.setFont(new Font("Tahoma", Font.PLAIN, 13));
		facturaMaximaPane.setEditable(false);
		scrollPane_3.setViewportView(facturaMaximaPane);
		
		// do some dirty job
		showMagazinCeleMaiMariVanzari();
		showMagazinTariVanzariMaxime();
		showMagazinCategorieVanzariMaxime();
		showFacturaMaxima();
	}
	
	public void showMagazinCeleMaiMariVanzari() {
		// caut magazinul cu cele mai mari vanzari
		Magazin magazinMax = Gestiune.getInstance().magazine.get(0);
		for(Magazin m : Gestiune.getInstance().magazine) {
			if(m.getTotalCuTaxe() > magazinMax.getTotalCuTaxe())
				magazinMax = m;
		}
		
		magazinMaxPane.setFont(new Font("Tahoma", Font.PLAIN, 12));	
		magazinMaxPane.setText(getInfoMagazin(magazinMax));	
	}
	
	public void showMagazinTariVanzariMaxime() {
		ArrayList<String> tari = (ArrayList<String>) Gestiune.getInstance().getTariList();
		StringBuffer sb = new StringBuffer();
		
		for(String tara : tari) {
			// fac maximul getTotalTaraCuTaxe(tara) dintre toata magazinele
			Magazin max = Gestiune.getInstance().magazine.get(0);
			
			for(Magazin m : Gestiune.getInstance().magazine) {
				if(m.getTotalTaraCuTaxe(tara) > max.getTotalTaraCuTaxe(tara))
					max = m;				
			}
			
			sb.append(tara);
			sb.append("\n------------------------------\n");
			sb.append(getInfoMagazin(max));
			sb.append("------------------------------\n");
			
		}		
		
		tariMagazinMaxPane.setFont(new Font("Tahoma", Font.PLAIN, 12));	
		tariMagazinMaxPane.setText(sb.toString());
	}
	
	public void showMagazinCategorieVanzariMaxime() {
		ArrayList<String> categorii = (ArrayList<String>) Gestiune.getInstance().getCategoriiList();
		StringBuffer sb = new StringBuffer();
		
		for(String categorie : categorii) {
			// fac maximul getTotalCategorieCuTaxe(categorie) dintre toata magazinele
			Magazin max = Gestiune.getInstance().magazine.get(0);
			
			for(Magazin m : Gestiune.getInstance().magazine) {
				if(m.getTotalCategorieCuTaxe(categorie) > max.getTotalCategorieCuTaxe(categorie))
					max = m;				
			}

			sb.append(categorie);
			sb.append("\n------------------------------\n");
			sb.append(getInfoMagazin(max));
			sb.append("------------------------------\n");
			
		}		
		
		magazinMaxCategoriePane.setFont(new Font("Tahoma", Font.PLAIN, 12));
		magazinMaxCategoriePane.setText(sb.toString());
	}
	
	public void showFacturaMaxima() {
		DecimalFormat fmt = new DecimalFormat();
		fmt.setMaximumFractionDigits(4);
		
		ArrayList<String> categorii = (ArrayList<String>) Gestiune.getInstance().getCategoriiList();
		StringBuffer sb = new StringBuffer();
		
		Factura facturaMax = new Factura();
		for(Magazin m : Gestiune.getInstance().magazine) {
			for(Factura f : m.facturi) {
				if(f.getTotalFaraTaxe() > facturaMax.getTotalFaraTaxe())
					facturaMax = f;
			}
		}	
		
		sb.append(facturaMax.denumire);
		sb.append("\n------------------------------\n");
		// Totalul
		sb.append("Total fara taxe: " + fmt.format(facturaMax.getTotalFaraTaxe()) + "\n");
		sb.append("Total cu taxe: " + fmt.format(facturaMax.getTotalCuTaxe()) + "\n");
		sb.append("------------------------------\n");
		
		for(String tara : Gestiune.getInstance().taxe.keySet()) {
			sb.append(tara + " ");				
			if(facturaMax.getTotalTaraCuTaxe(tara) == 0) {
				sb.append("0");
				continue;
			}
			
			sb.append(fmt.format(facturaMax.getTotalTaraFaraTaxe(tara)) + " " + fmt.format(facturaMax.getTotalTaraCuTaxe(tara)) + "\n");
		}
				
		facturaMaximaPane.setFont(new Font("Tahoma", Font.PLAIN, 12));
		facturaMaximaPane.setText(sb.toString());
	}
	
	public String getInfoMagazin(Magazin m) {
		StringBuffer sb = new StringBuffer();
		sb.append("Nume magazin: " + m.nume + "\n");
		sb.append("Tip magazin: " + m.getTip() + "\n");
		sb.append("Nr. facturi: " + m.facturi.size() + "\n");
		sb.append("------------------------------\n");
		sb.append("Total fara taxe: " + m.getTotalFaraTaxe() + "\n");
		sb.append("Total cu taxe: " + m.getTotalCuTaxe() + "\n");
		sb.append("Total cu taxe scutite: " + m.getTotalCuTaxeScutite() + "\n");
		return sb.toString();
	}
}
