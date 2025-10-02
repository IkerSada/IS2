package gui;

import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
import domain.User;
import exceptions.SaldoAteraException;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class DiruaAteraGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDiruaAtera;
	private User user;
	private JLabel lblSaldo;
	private JLabel lblMezua;

	/**
	 * Create the frame.
	 */
	public DiruaAteraGUI(Driver u) {

		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("DiruaAteraGUI.title"));

		BLFacade facade = MainGUI.getBusinessLogic();
		user=facade.erabiltzaileaBilatu(u.getEmail());		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(800, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblMezua = new JLabel();
		lblMezua.setBounds(44, 130, 336, 46);
		lblMezua.setForeground(Color.red);
		contentPane.add(lblMezua);

		JLabel lblDirua = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Saldo"));
		lblDirua.setBounds(21, 10, 74, 23);
		contentPane.add(lblDirua);

		lblSaldo= new JLabel(Float.toString(user.getDirua()));
		lblSaldo.setBounds(77, 10, 74, 23);
		contentPane.add(lblSaldo);

		txtDiruaAtera = new JTextField();
		txtDiruaAtera.setText((String)null); 
		txtDiruaAtera.setBounds(77, 102, 288, 21);
		contentPane.add(txtDiruaAtera);
		txtDiruaAtera.setColumns(10);


		JButton btnAtzera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame a = new GidariGUI((Driver)user);
				a.setVisible(true);
			}
		});
		btnAtzera.setBounds(93, 187, 93, 23);
		contentPane.add(btnAtzera);

		JButton btnAtera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Atera")); //$NON-NLS-1$ //$NON-NLS-2$
		btnAtera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					float dirua = Float.parseFloat(txtDiruaAtera.getText());
					if(dirua<=0) {
						throw new IllegalArgumentException();
					}else if(dirua>user.getDirua()){						
						throw new SaldoAteraException();				
					}
					facade.diruaAtera(dirua,(Driver) user);
					user=facade.erabiltzaileaBilatu(user.getEmail());
					lblSaldo.setText(Float.toString(user.getDirua()));
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Ondo"));
					txtDiruaAtera.setText("");		// GEHITU
				} catch (NumberFormatException x) {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.NumberFormatException"));
				} catch (IllegalArgumentException y) {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.IllegalArgumentException"));
				} catch (SaldoAteraException z) {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.SaldoAteraException"));
				}
			}
		});
		btnAtera.setBounds(220, 187, 93, 23);
		contentPane.add(btnAtera);


		JLabel lblDiruaAtera = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SaldoAteraTestua")); //$NON-NLS-1$ //$NON-NLS-2$
		lblDiruaAtera.setHorizontalAlignment(SwingConstants.CENTER);
		lblDiruaAtera.setBounds(21, 71, 363, 15);
		contentPane.add(lblDiruaAtera);
	}
}
