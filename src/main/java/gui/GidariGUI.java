package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Driver;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class GidariGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public GidariGUI(Driver u) {
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Driver"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(800, 100, 450, 435);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JButton btnEskaera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Eskaerak"));
		btnEskaera.setBounds(20, 65, 185, 51);
		btnEskaera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame eskaerak=new EskaeraGUI((Driver)u);
				eskaerak.setVisible(true);
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnEskaera);
		
		JButton btnBidaiSortu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BidaiaSortu"));
		btnBidaiSortu.setBounds(20, 130, 185, 51);
		btnBidaiSortu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame bidaiSortu = new CreateRideGUI((Driver)u);
				bidaiSortu.setVisible(true);
			}
		});
		contentPane.add(btnBidaiSortu);
		
		JButton btnDiruaAtera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DiruaAtera"));
		btnDiruaAtera.setBounds(20, 10, 167, 32);
		btnDiruaAtera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame saldoAtera= new DiruaAteraGUI((Driver)u);
				saldoAtera.setVisible(true);
				setVisible(false);
			}
		});
		contentPane.add(btnDiruaAtera);
		
		
		JButton btnGehituKotxea = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GidariGUI.GehituKotxe"));
		btnGehituKotxea.setBounds(20, 195, 185, 51);
		contentPane.add(btnGehituKotxea);
		btnGehituKotxea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame gehituKotxe = new KotxeaGehituGUI((Driver)u);
				gehituKotxe.setVisible(true);
			}
		});
		
		JButton btnBidaiaKantzelatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GidariGUI.BidaiaKantzelatu"));
		btnBidaiaKantzelatu.setBounds(20, 260, 185, 51);
		contentPane.add(btnBidaiaKantzelatu);
		btnBidaiaKantzelatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame bidaiaKantzelatu = new BidaiaKantzelatuGUI((Driver)u);
				bidaiaKantzelatu.setVisible(true);
			}
		});
		
		JButton btnMugimenduakIkusi = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusi"));
		btnMugimenduakIkusi.setBounds(20, 325, 185, 51);
		btnMugimenduakIkusi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame mugimenduakIkusi= new MugimenduakIkusiGUI(u);
				mugimenduakIkusi.setVisible(true);
			}
		});
		contentPane.add(btnMugimenduakIkusi);
		
		JButton btnBalorazioaIkusi = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BidaiariGUI.BalorazioaIkusi")); //$NON-NLS-1$ //$NON-NLS-2$
		btnBalorazioaIkusi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame balorazioaIkusiGUI= new BalorazioaIkusiGUI(u);
				balorazioaIkusiGUI.setVisible(true);
			}
		});
		btnBalorazioaIkusi.setBounds(225, 65, 185, 51);
		contentPane.add(btnBalorazioaIkusi);
		
		JButton btnBalorazioaSortu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BidaiariGUI.BalorazioaSortu")); //$NON-NLS-1$ //$NON-NLS-2$
		btnBalorazioaSortu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame balorazioaSortuGUI= new BalorazioaSortuGidariGUI(u);
				balorazioaSortuGUI.setVisible(true);
			}
		});
		btnBalorazioaSortu.setBounds(225, 130, 185, 51);
		contentPane.add(btnBalorazioaSortu);
		
		//GEHITU
		JButton btnErreklamazioaKudeatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaKudeatu")); 
		btnErreklamazioaKudeatu.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			JFrame erreklamazioaKudeatuGidariGUI= new ErreklamazioaKudeatuGidariGUI(u);
			erreklamazioaKudeatuGidariGUI.setVisible(true);
			}
		});
		btnErreklamazioaKudeatu.setBounds(225, 195, 185, 51);
		contentPane.add(btnErreklamazioaKudeatu);
		//
		
		JButton btnErabiltzaileaEzabatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErabiltzaileaEzabatu"));
		btnErabiltzaileaEzabatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				setVisible(false);
				JFrame erabiltzaileaEzabatuGUI= new ErabiltzaileaEzabatuGUI(u);
				erabiltzaileaEzabatuGUI.setVisible(true);
			}
		});
		btnErabiltzaileaEzabatu.setBounds(225, 260, 185, 51);
		contentPane.add(btnErabiltzaileaEzabatu);
		
	}
}
