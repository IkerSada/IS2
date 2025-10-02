package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Bidaiaria;


public class BidaiariGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public BidaiariGUI(Bidaiaria u) {
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Traveler"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton eskaeraBotoia = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUITraveller.MakeTheBooking"));
		eskaeraBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame eskaerak=new ErreserbakEginGUI(u);
				eskaerak.setVisible(true);
			}
		});
		eskaeraBotoia.setBounds(20, 65, 185, 51);
		contentPane.add(eskaeraBotoia);

		JButton bidaiSortuBotoia = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		bidaiSortuBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new FindRidesGUI();
				a.setVisible(true);
			}
		});
		bidaiSortuBotoia.setBounds(20, 130, 185, 51);
		contentPane.add(bidaiSortuBotoia);

		JButton diruaSartuBotoia = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DiruaSartuGUI.TakeOut"));
		diruaSartuBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame diruaSartu= new DiruaSartuGUI(u);
				diruaSartu.setVisible(true);
			}
		});
		diruaSartuBotoia.setBounds(20, 10, 167, 32);
		contentPane.add(diruaSartuBotoia);

		
		JButton erreserbaBaieztatuBotoia = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BidaiariGUI.ErreserbaBaieztatu"));
		erreserbaBaieztatuBotoia.setBounds(20, 195, 185, 51);
		contentPane.add(erreserbaBaieztatuBotoia);

		erreserbaBaieztatuBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame erreserbaBaieztatu= new ErreserbaBaieztatuGUI(u);
				erreserbaBaieztatu.setVisible(true);
			}
		});


		JButton erreserbaEgoeraBotoia = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BidaiariGUI.ErreserbaEgoera"));
		erreserbaEgoeraBotoia.setBounds(20, 260, 185, 51);
		contentPane.add(erreserbaEgoeraBotoia);
		
		JButton mugimenduakIkusiBotoia = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusi"));
		mugimenduakIkusiBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame mugimenduakIkusi= new MugimenduakIkusiGUI(u);
				mugimenduakIkusi.setVisible(true);
			}
		});
		mugimenduakIkusiBotoia.setBounds(20, 325, 185, 51);
		contentPane.add(mugimenduakIkusiBotoia);
		
		//GEHITU -->
		
		JButton btnErabiltzaileaEzabatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErabiltzaileaEzabatu")); //$NON-NLS-1$ //$NON-NLS-2$
		btnErabiltzaileaEzabatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame erabiltzaileaEzabatuGUI= new ErabiltzaileaEzabatuGUI(u);
				erabiltzaileaEzabatuGUI.setVisible(true);
			}
		});
		
		btnErabiltzaileaEzabatu.setBounds(20, 390, 185, 51);
		contentPane.add(btnErabiltzaileaEzabatu);
		
		JButton btnBalorazioaIkusi = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BidaiariGUI.BalorazioaIkusi"));
		btnBalorazioaIkusi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame balorazioaIkusiGUI= new BalorazioaIkusiGUI(u);
				balorazioaIkusiGUI.setVisible(true);
			}
		});
		btnBalorazioaIkusi.setBounds(225, 65, 185, 51);
		contentPane.add(btnBalorazioaIkusi);
		
		JButton btnBalorazioaSortu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BidaiariGUI.BalorazioaSortu"));
		btnBalorazioaSortu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame balorazioaSortuGUI= new BalorazioaSortuBidaiariGUI(u);
				balorazioaSortuGUI.setVisible(true);
			}
		});
		
		btnBalorazioaSortu.setBounds(225, 130, 185, 51);
		contentPane.add(btnBalorazioaSortu);
		
		JButton btnErreklamazioaKudeatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaKudeatu")); //ALDATU
		btnErreklamazioaKudeatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame erreklamazioaKudeatuBidaiariGUI= new ErreklamazioaKudeatuBidaiariGUI(u);
				erreklamazioaKudeatuBidaiariGUI.setVisible(true);
			}
		});
		btnErreklamazioaKudeatu.setBounds(225, 195, 185, 51);
		contentPane.add(btnErreklamazioaKudeatu);
		
		JButton btnErreklamazioaSortu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaSortu")); //ALDATU
		btnErreklamazioaSortu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame erreklamazioaSortuGUI= new ErreklamazioaSortuGUI(u);
				erreklamazioaSortuGUI.setVisible(true);
			}
		});
		btnErreklamazioaSortu.setBounds(225, 260, 185, 51);
		contentPane.add(btnErreklamazioaSortu);
		
		
		JButton btnAlertaIkusi = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BidaiariGUI.AlertaIkusi"));
		btnAlertaIkusi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame alertaIkusiGUI= new AlertaIkusiGUI(u);
				alertaIkusiGUI.setVisible(true);
			}
		});
		btnAlertaIkusi.setBounds(225, 325, 185, 51);
		contentPane.add(btnAlertaIkusi);
		
	
		JButton btnAlertaSortu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BidaiariGUI.AlertaSortu"));
		btnAlertaSortu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame alertaSortuGUI= new AlertaSortuGUI(u);
				alertaSortuGUI.setVisible(true);
			}
		});
		btnAlertaSortu.setBounds(225, 390, 185, 51);
		contentPane.add(btnAlertaSortu);

		//
		
		erreserbaEgoeraBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame erreserbaEgoera= new ErreserbaEgoeraGUI(u);
				erreserbaEgoera.setVisible(true);
			}
		});
		
	}
}
