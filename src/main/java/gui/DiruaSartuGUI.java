package gui;

import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Bidaiaria;
import domain.User;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class DiruaSartuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDiruaSartu;
	private JLabel lblDiruaSartu;
	private JButton btnAtzera;
	private JButton btnDiruaSartu;
	private User user;
	private JLabel lblSaldo;
	private JLabel lblMezua;

	/**
	 * Create the frame.
	 */

	public DiruaSartuGUI(Bidaiaria u) {
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("DiruaSartuGUI.title"));

		// user=u;		KENDU
		// GEHITU
		BLFacade facade = MainGUI.getBusinessLogic();
		user=facade.erabiltzaileaBilatu(u.getEmail());		
		//
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Saldo"));
		lblNewLabel.setBounds(21, 10, 74, 23);
		contentPane.add(lblNewLabel);

		lblSaldo= new JLabel(Float.toString(user.getDirua()));
		lblSaldo.setBounds(77, 10, 74, 23);
		contentPane.add(lblSaldo);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblMezua = new JLabel();
		lblMezua.setBounds(90, 148, 270, 52);
		lblMezua.setForeground(Color.red);
		contentPane.add(lblMezua);
		
		lblDiruaSartu = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DiruaSartuGUI.TakeOutText"));
		lblDiruaSartu.setHorizontalAlignment(SwingConstants.CENTER);
		lblDiruaSartu.setBounds(55, 80, 332, 13);
		contentPane.add(lblDiruaSartu);
		btnAtzera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame a = new BidaiariGUI((Bidaiaria)user);
				a.setVisible(true);
			}
		});
		btnAtzera.setBounds(55, 211, 150, 21);
		contentPane.add(btnAtzera);
		btnDiruaSartu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DiruaSartuGUI.TakeOut"));
		btnDiruaSartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int dirua = Integer.parseInt(txtDiruaSartu.getText());
					if(dirua<=0) {
						throw new IllegalArgumentException();
					}
					BLFacade facade = MainGUI.getBusinessLogic();
					facade.diruaSartu(dirua,(Bidaiaria) user);
					user=facade.erabiltzaileaBilatu(user.getEmail());
					lblSaldo.setText(Float.toString(user.getDirua()));
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Ondo"));
					txtDiruaSartu.setText("");	 		// GEHITU
				} catch (NumberFormatException x) {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.NumberFormatException"));
				} catch (IllegalArgumentException y) {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.IllegalArgumentException"));
				}
			}
		});
		btnDiruaSartu.setBounds(237, 211, 150, 21);
		contentPane.add(btnDiruaSartu);
		txtDiruaSartu = new JTextField();
		txtDiruaSartu.setBounds(90, 103, 270, 19);
		contentPane.add(txtDiruaSartu);
		txtDiruaSartu.setColumns(10);

	}

}