package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Bidaiaria;
import domain.Erreserba;
import domain.Ride;
import exceptions.IsEmptyException;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;


public class BalorazioaSortuBidaiariGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnAtzera;
	private JTextField txtDeskripzioa;
	private JComboBox<Integer>comboErreserba;
	private DefaultComboBoxModel<Integer> ErreserbaBox = new DefaultComboBoxModel<Integer>();
	private DefaultComboBoxModel<Integer> PuntuazioBox = new DefaultComboBoxModel<Integer>();
	private JLabel lblErreserba;
	private JButton btnBidali;
	private JLabel lblGidaria;
	private JLabel lblMezua;
	private List <Erreserba> amaitutakoErreserbak = new ArrayList<Erreserba>();



	public BalorazioaSortuBidaiariGUI(Bidaiaria b) {
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("BalorazioaSortuGUI.title"));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);


		lblMezua = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		lblMezua.setForeground(Color.RED);
		lblMezua.setBounds(248, 229, 222, 14);
		contentPane.add(lblMezua);
		

		btnAtzera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnAtzera.setBounds(150, 267, 146, 31);
		contentPane.add(btnAtzera);
		btnAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame a = new BidaiariGUI(b);
				a.setVisible(true);
			}
		});    


		setContentPane(contentPane);

		txtDeskripzioa = new JTextField();
		txtDeskripzioa.setText(""); //$NON-NLS-1$ //$NON-NLS-2$
		txtDeskripzioa.setBounds(173, 151, 430, 42);
		contentPane.add(txtDeskripzioa);
		txtDeskripzioa.setColumns(10);

		JLabel lblDeskripzioa = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Deskripzioa")); //$NON-NLS-1$ //$NON-NLS-2$
		lblDeskripzioa.setBounds(46, 151, 90, 13);
		contentPane.add(lblDeskripzioa);

		JLabel lblPuntuazioa = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusiGUI.Rating")); //$NON-NLS-1$ //$NON-NLS-2$
		lblPuntuazioa.setBounds(46, 93, 90, 13);
		contentPane.add(lblPuntuazioa);

		JComboBox<Integer> comboPuntuazioa = new JComboBox<Integer>();
		comboPuntuazioa.setModel(PuntuazioBox);
		for(int i = 1; i <= 5; i++ ) {
			PuntuazioBox.addElement(i);
		}
		comboPuntuazioa.setBounds(173, 89, 69, 21);
		contentPane.add(comboPuntuazioa);

		
		lblGidaria = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		lblGidaria.setBounds(448, 39, 187, 21);
		contentPane.add(lblGidaria);

		comboErreserba = new JComboBox<Integer>();
		comboErreserba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ErreserbaBox.getSelectedItem() != null){
					boolean aurkitua = false;
					int i = 0;
					while(!aurkitua && i < amaitutakoErreserbak.size()) {
						if(amaitutakoErreserbak.get(i).getBookNumber().equals(ErreserbaBox.getSelectedItem())) {
							lblGidaria.setText(amaitutakoErreserbak.get(i).getGidariarenEmaila());
							aurkitua = true;
						}
					i++;
					}
				}
			}
		});
		comboErreserba.setModel(ErreserbaBox);
		comboErreserba.setBounds(176, 39, 66, 21);
		contentPane.add(comboErreserba);
		
		amaitutakoErreserbak = loadAmaitutakoErreserbak(b);

		lblErreserba = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BookNumber")); //$NON-NLS-1$ //$NON-NLS-2$
		lblErreserba.setBounds(46, 43, 113, 13);
		contentPane.add(lblErreserba);

		btnBidali = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Bidali")); //$NON-NLS-1$ //$NON-NLS-2$
		btnBidali.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				try {
					Integer erreserbaZenbaki = (Integer)ErreserbaBox.getSelectedItem();
					String gidari = lblGidaria.getText();
					Integer puntuazioa = (Integer) PuntuazioBox.getSelectedItem();
					String deskripzioa = txtDeskripzioa.getText();
					datuakZuzenak(gidari);
					BLFacade facade = MainGUI.getBusinessLogic();
					facade.balorazioaSortu(gidari,puntuazioa,deskripzioa,b);
					facade.erreserbaBaloratuDa(erreserbaZenbaki);
					amaitutakoErreserbak = loadAmaitutakoErreserbak(b);
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("BalorazioaSortuDa"));
				} catch (IsEmptyException e2) {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.IsEmptyException"));
				}
			}
		});
		btnBidali.setBounds(383, 267, 135, 31);
		contentPane.add(btnBidali);
		
		JLabel lblGidariEmail = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DriverEmail")); //$NON-NLS-1$ //$NON-NLS-2$
		lblGidariEmail.setBounds(292, 43, 146, 14);
		contentPane.add(lblGidariEmail);
		

	}


	private List<Erreserba> loadAmaitutakoErreserbak(Bidaiaria b) {
		BLFacade facade = MainGUI.getBusinessLogic();
		List<Erreserba> erreserbak = facade.getBaloratuGabekoErreserbak(b);
		Ride bid;
		List<Erreserba> amaitutakoErreserbak = new ArrayList<Erreserba>();
		ErreserbaBox.removeAllElements();
		lblGidaria.setText("");
		txtDeskripzioa.setText("");
		if (erreserbak != null && !erreserbak.isEmpty()) {
			for (Erreserba e : erreserbak) {
				bid=e.getBidaia();
				if(bid.getEgoera().equals("amaituta")) {
					amaitutakoErreserbak.add(e);
					ErreserbaBox.addElement(e.getBookNumber());
				}
			}
		}
		if (ErreserbaBox.getSize() > 0 && !amaitutakoErreserbak.isEmpty()) {
			comboErreserba.setSelectedIndex(0);
			lblGidaria.setText(amaitutakoErreserbak.get(0).getGidariarenEmaila());
		}
		return amaitutakoErreserbak;
	}
	
	private void datuakZuzenak(String gidari) throws IsEmptyException {
		if (gidari.isEmpty()) {
			throw new IsEmptyException();
		} 	
	}
	
}