package gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
import exceptions.EserlekuHutsikException;
import exceptions.IsEmptyException;
import exceptions.KotxeaAlreadyExistException;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class KotxeaGehituGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField txtMatrikula;
	private JTextField txtMarka;
	private JTextField txtEserleku;


	/**
	 * Create the frame.
	 */
	public KotxeaGehituGUI(Driver u) {
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGehituGUI.title"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(800, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblmatrikula = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGehituGUI.Matrikula"));
		lblmatrikula.setBounds(54, 48, 114, 14);
		contentPane.add(lblmatrikula);
		
		JLabel lblmarka = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGehituGUI.Marka"));
		lblmarka.setBounds(54, 91, 114, 14);
		contentPane.add(lblmarka);
		
		JLabel lblnEserleku = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGehituGUI.EserlekuKopurua"));
		lblnEserleku.setBounds(54, 135, 114, 14);
		contentPane.add(lblnEserleku);
		
		txtMatrikula = new JTextField();
		txtMatrikula.setBounds(234, 45, 135, 20);
		contentPane.add(txtMatrikula);
		txtMatrikula.setColumns(10);
		
		txtMarka = new JTextField();
		txtMarka.setBounds(234, 88, 135, 20);
		contentPane.add(txtMarka);
		txtMarka.setColumns(10);
		
		txtEserleku = new JTextField();
		txtEserleku.setBounds(234, 132, 135, 20);
		contentPane.add(txtEserleku);
		txtEserleku.setColumns(10);
		
		JLabel lblMezua = new JLabel();
		lblMezua.setBounds(43, 236, 326, 14);
		contentPane.add(lblMezua);
		lblMezua.setForeground(Color.red);
		
		JButton btnAtzera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back")); 
		btnAtzera.setBounds(54, 191, 114, 23);
        contentPane.add(btnAtzera);
        btnAtzera.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
				JFrame a = new GidariGUI(u);
				a.setVisible(true);
            }
        });
		
		JButton btnGehitu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGehituGUI.Gehitu"));
		btnGehitu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();		
				try {
					if(txtMatrikula.getText().isEmpty() || txtMarka.getText().isEmpty()) {
						throw new IsEmptyException();
					} else if(txtEserleku.getText().isEmpty()) {
						throw new EserlekuHutsikException();
					}
					// ZUZENDU
//					if(facade.kotxeaExistitu(txtMatrikula.getText())) {
//						throw new KotxeaAlreadyExistException();
//					} else {
//						int nEserleku = Integer.parseInt(txtEserleku.getText());
//						facade.kotxeGehitu(txtMatrikula.getText(),nEserleku,txtMarka.getText(),u);
//					}
					int nEserleku = Integer.parseInt(txtEserleku.getText());
					facade.kotxeGehitu(txtMatrikula.getText(),nEserleku,txtMarka.getText(),u);
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("KotxeaGehituGUI.CarCreated"));
					//
				} catch (IsEmptyException e2) {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.IsEmptyException"));
				} catch (KotxeaAlreadyExistException e3) {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.KotxeaAlreadyExistException"));
				} catch (EserlekuHutsikException e4) {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.EserlekuHutsik"));
				} catch (NumberFormatException e5) {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.NumberFormatException"));
				}
			}
		});
		btnGehitu.setBounds(239, 191, 130, 23);
		contentPane.add(btnGehitu);

	}
}
