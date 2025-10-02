package gui;

import domain.Admin;
import domain.Bidaiaria;
import domain.Driver;
import domain.User;

import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEmail;
	private JPasswordField passwordField;
	private JLabel lblEmail;
	private JLabel lblPasahitza;
	private JButton btnAtzera;
	private JButton btnSaioaHasi;
	private JLabel lblMezua;
	
	private User user;


	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("SaioaHasi"));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblMezua = new JLabel(); 
		lblMezua.setBounds(76, 231, 269, 19);
		lblMezua.setForeground(Color.red);
		contentPane.add(lblMezua);
		
		lblEmail = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Email"));
		lblEmail.setBounds(63, 36, 144, 13);
		contentPane.add(lblEmail);
		lblPasahitza = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PassWord"));
		lblPasahitza.setBounds(63, 116, 157, 13);
		contentPane.add(lblPasahitza);
		txtEmail = new JTextField();
		txtEmail.setBounds(63, 72, 310, 19);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		btnAtzera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnAtzera.setBounds(76, 186, 131, 21);
		contentPane.add(btnAtzera);
		btnSaioaHasi = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SaioaHasi"));
		btnSaioaHasi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				if(facade.saioaHasi(txtEmail.getText(),new String(passwordField.getPassword()))) {
					user = facade.erabiltzaileaBilatu(txtEmail.getText());
					if(user instanceof Driver) {
						JFrame a = new GidariGUI((Driver)user);
						a.setVisible(true);
						setVisible(false);
					} else {
						JFrame a = new BidaiariGUI((Bidaiaria)user);
						a.setVisible(true);
						setVisible(false);
					}
				} else { // GEHITU
					if(facade.saioaHasiAdmin(txtEmail.getText(),new String(passwordField.getPassword()))) {
						Admin admin = facade.erabiltzaileaBilatuAdmin(txtEmail.getText());
						JFrame a = new ErreklamazioaKudeatuAdminGUI(admin);
						a.setVisible(true);
						setVisible(false);
						//
					} else {
						lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.UserNoExist"));
					}
				}
			}
		});;
		btnSaioaHasi.setBounds(222, 186, 123, 21);
		contentPane.add(btnSaioaHasi);
		passwordField = new JPasswordField();
		passwordField.setBounds(63, 147, 310, 19);
		contentPane.add(passwordField);
	}

}
