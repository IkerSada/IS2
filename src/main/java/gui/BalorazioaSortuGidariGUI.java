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
import domain.Driver;
import domain.Erreserba;
import domain.Ride;
import exceptions.IsEmptyException;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;


public class BalorazioaSortuGidariGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnAtzera;
	private JTextField txtDeskripzioa;
	private JComboBox<Integer> comboBidaia;
	private DefaultComboBoxModel<Integer> BidaiaBox = new DefaultComboBoxModel<Integer>();
	private DefaultComboBoxModel<Integer> PuntuazioBox = new DefaultComboBoxModel<Integer>();
	private JComboBox<String> comboBidaiari;
	private DefaultComboBoxModel<String> BidaiariBox = new DefaultComboBoxModel<String>();
	private JLabel lblBidaia;
	private JButton btnBidali;
	private JLabel lblMezua;
	private List <Ride> amaitutakoBidaiak = new ArrayList<Ride>();


	/**
	 * Create the frame.
	 */
	public BalorazioaSortuGidariGUI(Driver g) {
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("BalorazioaSortuGUI.title"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(800, 100, 601, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		lblMezua = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		lblMezua.setForeground(Color.RED);
		lblMezua.setBounds(174, 230, 222, 14);
		contentPane.add(lblMezua);
		
		btnAtzera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnAtzera.setBounds(89, 267, 146, 31);
		contentPane.add(btnAtzera);
		btnAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame a = new GidariGUI(g);
				a.setVisible(true);
			}
		});


		comboBidaiari = new JComboBox<String>();
		comboBidaiari.setModel(BidaiariBox);
		comboBidaiari.setBounds(335, 39, 178, 21);
		contentPane.add(comboBidaiari);
		JLabel lblBidaiaria = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Traveler")); //$NON-NLS-1$ //$NON-NLS-2$
		lblBidaiaria.setBounds(265, 43, 83, 13);
		contentPane.add(lblBidaiaria);
		

		setContentPane(contentPane);
		txtDeskripzioa = new JTextField();
		//txtDeskripzioa.setText(""); //$NON-NLS-1$ //$NON-NLS-2$
		txtDeskripzioa.setBounds(138, 151, 375, 42);
		contentPane.add(txtDeskripzioa);
		txtDeskripzioa.setColumns(10);
		JLabel lblDeskripzioa = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Deskripzioa")); //$NON-NLS-1$ //$NON-NLS-2$
		lblDeskripzioa.setBounds(46, 151, 82, 13);
		contentPane.add(lblDeskripzioa);
		JLabel lblPuntuazioa = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusiGUI.Rating")); //$NON-NLS-1$ //$NON-NLS-2$
		lblPuntuazioa.setBounds(46, 93, 69, 13);
		contentPane.add(lblPuntuazioa);
		JComboBox<Integer> comboPuntuazioa = new JComboBox<Integer>();
		comboPuntuazioa.setModel(PuntuazioBox);
		for(int i = 1; i <= 5; i++ ) {
			PuntuazioBox.addElement(i);
		}
		comboPuntuazioa.setBounds(138, 89, 69, 21);
		contentPane.add(comboPuntuazioa);
		
		comboBidaia = new JComboBox<Integer>();
		comboBidaia.setModel(BidaiaBox);
		comboBidaia.setBounds(138, 39, 69, 21);
		comboBidaia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadBidaiariakFromSelectedRide();
			}
		});
		contentPane.add(comboBidaia);
		
		amaitutakoBidaiak = loadAmaitutakoBidaiak(g);
		
		if (BidaiaBox.getSize() > 0) {
		    comboBidaia.setSelectedIndex(0);
		    loadBidaiariakFromSelectedRide();
		}

		lblBidaia = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Rides")); //$NON-NLS-1$ //$NON-NLS-2$
		lblBidaia.setBounds(46, 43, 82, 13);
		contentPane.add(lblBidaia);
		btnBidali = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Bidali")); //$NON-NLS-1$ //$NON-NLS-2$
		btnBidali.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Integer bidaiZenbaki = (Integer)BidaiaBox.getSelectedItem();
					String bidaiari = (String) BidaiariBox.getSelectedItem();
					Integer puntuazioa = (Integer) PuntuazioBox.getSelectedItem();
					String deskripzioa = txtDeskripzioa.getText();
					datuakZuzenak(bidaiari);
					BLFacade facade = MainGUI.getBusinessLogic();
					facade.balorazioaSortu(bidaiari,puntuazioa,deskripzioa,g);
					facade.bidaiariaBaloratuDa(bidaiZenbaki,bidaiari);
					amaitutakoBidaiak = loadAmaitutakoBidaiak(g);
					loadBidaiariakFromSelectedRide();
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("BalorazioaSortuDa"));				
				} catch (IsEmptyException e2) {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.IsEmptyException"));
				}
			}
		});
		btnBidali.setBounds(317, 267, 135, 31);
		contentPane.add(btnBidali);

	}
	

	private List<Ride> loadAmaitutakoBidaiak(Driver g) {
		BLFacade facade = MainGUI.getBusinessLogic();
		List<Ride> bidaiak = facade.getBaloratuGabekoBidaiaAmaituak(g);
		List<Ride> amaitutakoBidaiak = new ArrayList<Ride>();
		BidaiaBox.removeAllElements();
		if (bidaiak != null && !bidaiak.isEmpty()) {
			for (Ride r : bidaiak) {
				amaitutakoBidaiak.add(r);
				BidaiaBox.addElement(r.getRideNumber());
			}
		}
		
		return amaitutakoBidaiak;
	}
	
	private void loadBidaiariakFromSelectedRide() {
		BidaiariBox.removeAllElements();
		txtDeskripzioa.setText("");
		if(BidaiaBox.getSelectedItem() != null) {
			boolean aurkitua = false;
			int i = 0;
			while(!aurkitua && i < amaitutakoBidaiak.size()) {
				if(amaitutakoBidaiak.get(i).getRideNumber().equals(BidaiaBox.getSelectedItem())) {
					BLFacade facade = MainGUI.getBusinessLogic();
					List <Erreserba> erreserbak = facade.getBidaiarenErreserbak(amaitutakoBidaiak.get(i));
					for(int j = 0; j < erreserbak.size(); j++) {
						if(!erreserbak.get(j).isBaloratutaGidari()) {
							BidaiariBox.addElement(erreserbak.get(j).getBidaiariarenEmaila());
						}
					}
					aurkitua = true;
				}
				i++;
			}
		}
	}
	
	private void datuakZuzenak(String bidaiari) throws IsEmptyException {
		if (bidaiari==null) {
			throw new IsEmptyException();
		} 	
	}
}
