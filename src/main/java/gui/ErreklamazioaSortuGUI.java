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


public class ErreklamazioaSortuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnAtzera;
	private JTextField txtDeskripzioa;
	private JComboBox<Integer> comboErreserba;
	private DefaultComboBoxModel<Integer> ErreserbaBox = new DefaultComboBoxModel<Integer>();
	private DefaultComboBoxModel<Integer> PuntuazioBox = new DefaultComboBoxModel<Integer>();
	private JLabel lblErreserba;
	private JButton btnBidali;
	private JLabel lblGidaria;
	private List <Erreserba> amaitutakoErreserbak = new ArrayList<Erreserba>();



	public ErreklamazioaSortuGUI(Bidaiaria b) {

		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaSortuGUI.title"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 601, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		lblGidaria = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Driver")); //$NON-NLS-1$ //$NON-NLS-2$
		lblGidaria.setBounds(425, 39, 134, 21);
		contentPane.add(lblGidaria);

		JLabel lblMezua = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		lblMezua.setForeground(Color.red);
		lblMezua.setBounds(200, 217, 209, 13);
		contentPane.add(lblMezua);


		btnAtzera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnAtzera.setBounds(76, 267, 146, 31);
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
		txtDeskripzioa.setBounds(183, 151, 330, 42);
		contentPane.add(txtDeskripzioa);
		txtDeskripzioa.setColumns(10);
		JLabel lblDeskripzioa = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Deskripzioa")); //$NON-NLS-1$ //$NON-NLS-2$
		lblDeskripzioa.setBounds(25, 151, 116, 13);
		contentPane.add(lblDeskripzioa);
		for(int i = 1; i <= 5; i++ ) {
			PuntuazioBox.addElement(i);
		}
		amaitutakoErreserbak = loadAmaitutakoErreserbak(b);
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
		comboErreserba.setBounds(172, 39, 97, 21);
		contentPane.add(comboErreserba);

		lblErreserba = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BookNumber")); //$NON-NLS-1$ //$NON-NLS-2$
		lblErreserba.setBounds(25, 43, 127, 17);
		contentPane.add(lblErreserba);
		btnBidali = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Bidali")); //$NON-NLS-1$ //$NON-NLS-2$
		btnBidali.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String gidari = lblGidaria.getText();
					String deskripzioa = txtDeskripzioa.getText();
					if(comboErreserba.getSelectedItem() != null){
						Integer erreserbazbk = (int) comboErreserba.getSelectedItem();
						if(gidari == null || deskripzioa == null || deskripzioa.trim().isEmpty()) {
							throw new IsEmptyException();
						}
						BLFacade facade = MainGUI.getBusinessLogic();
						facade.erreklamazioaSortu(erreserbazbk,deskripzioa,b,gidari);
						lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaSortuDa"));
						amaitutakoErreserbak = loadAmaitutakoErreserbak(b);
					} else {
						throw new IsEmptyException();
					}

				} catch(IsEmptyException ex) {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.IsEmptyException"));
				} 
			}
		});
		btnBidali.setBounds(317, 267, 135, 31);
		contentPane.add(btnBidali);

		JLabel lblDriver = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DriverEmail")); //$NON-NLS-1$ //$NON-NLS-2$
		lblDriver.setBounds(317, 39, 97, 21);
		contentPane.add(lblDriver);



	}


	private List<Erreserba> loadAmaitutakoErreserbak(Bidaiaria b) {
		BLFacade facade = MainGUI.getBusinessLogic();
		List<Erreserba> erreserbak = facade.getBidaiariarenErreserbak(b);
		ErreserbaBox.removeAllElements();
		lblGidaria.setText("");
		txtDeskripzioa.setText("");
		Ride bid;
		List<Erreserba> amaitutakoErreserbak = new ArrayList<Erreserba>();
		if (erreserbak != null && !erreserbak.isEmpty()) {
			for (Erreserba e : erreserbak) {
				bid=e.getBidaia();
				if(bid.getEgoera().equals("amaituta") && e.getEgoera().equals("amaituta")) {
						amaitutakoErreserbak.add(e);
						ErreserbaBox.addElement(e.getBookNumber());
						lblGidaria.setText(e.getGidariarenEmaila());
				}
			}
		}
		return amaitutakoErreserbak;

	}
}