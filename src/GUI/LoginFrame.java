package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Canvas;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.UIManager;

import SistemFacturi.User;
import SistemFacturi.UserManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField userField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginFrame.class.getResource("/GUI/dollar2_icon.png")));
		//setIconImage(Toolkit.getDefaultToolkit().getImage(LoginFrame.class.getResource("/GUI/login_back.jpg")));
		setFont(new Font("DejaVu Sans", Font.PLAIN, 12));
		setTitle("Sistem de Facturi Fiscale");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 288);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewconecLabel = new JLabel("Autentificare utilizatori");
		lblNewconecLabel.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		lblNewconecLabel.setBounds(43, 25, 224, 30);
		contentPane.add(lblNewconecLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Utilizator");
		lblNewLabel_1.setFont(new Font("DejaVu Sans", Font.BOLD, 12));
		lblNewLabel_1.setBounds(43, 74, 70, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel labelNewLabel_2 = new JLabel("Parola");
		labelNewLabel_2.setFont(new Font("DejaVu Sans", Font.BOLD, 12));
		labelNewLabel_2.setBounds(43, 132, 70, 15);
		contentPane.add(labelNewLabel_2);
		
		passwordField = new JPasswordField();
		passwordField.setBackground(Color.WHITE);
		passwordField.setBounds(43, 147, 200, 30);
		contentPane.add(passwordField);
		
		userField = new JTextField();
		userField.setBackground(Color.WHITE);
		userField.setBounds(43, 90, 200, 30);
		contentPane.add(userField);
		userField.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		// Butonul Login a fost apasat
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// verific ca cele doua campuri sa fie completate
				if(userField.getText().trim().isEmpty() || passwordField.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Completeaza cele doua campuri!", "A aparut o problema", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				User user = new User(userField.getText().trim(), passwordField.getText().trim());
				
				// Verific daca userul exista
				UserManager um = UserManager.getInstance();
				
				if(!um.userExists(user)) {
					JOptionPane.showMessageDialog(null, "Username/Parola invalida", "A aparut o problema", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// User-ul exista si toata lumea este fericita :)
				// Mai putin administrator-ul
				setVisible(false);
				new MainFrame(user).setVisible(true);
			}
		});
		btnLogin.setBounds(43, 198, 120, 30);
		contentPane.add(btnLogin);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon(LoginFrame.class.getResource("/GUI/login_back.jpg")));
		lblNewLabel.setBounds(0, 0, 448, 258);
		contentPane.add(lblNewLabel);
		
	
	}
}
