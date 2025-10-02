package gui;

import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Erreserba;
import domain.User;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EskaeraGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableEskaerak;
	private JButton btnOnartu;
	private JButton btnDeuseztatu;
	private JButton btnAtzera;
	private JScrollPane scrollPaneEskaerak;
	private DefaultTableModel model;
	private JLabel lblMezua;

	public EskaeraGUI(Driver u) {
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("EskaeraGUI.title"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(800, 100, 553, 357);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblMezua = new JLabel();
		lblMezua.setBounds(281, 226, 131, 33);
		contentPane.add(lblMezua);

		scrollPaneEskaerak = new JScrollPane();
		scrollPaneEskaerak.setBounds(46, 23, 439, 163);
		contentPane.add(scrollPaneEskaerak);

		tableEskaerak = new JTable();
		tableEskaerak.setForeground(new Color(255, 255, 255));
		scrollPaneEskaerak.setViewportView(tableEskaerak);

		
		tableEskaerak.setForeground(Color.BLACK);
		tableEskaerak.setBackground(Color.WHITE);
	

		loadReservedRides(u);

		btnOnartu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Accept")); 
		btnOnartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableEskaerak.getSelectedRow() != -1) {
					BLFacade facade = MainGUI.getBusinessLogic();
					facade.erreserbaOnartu((int)model.getValueAt(tableEskaerak.getSelectedRow(),6),u);
					model.removeRow(tableEskaerak.getSelectedRow());

				}
			}
		});
		btnOnartu.setEnabled(false);
		btnOnartu.setBounds(108, 207, 131, 21);
		contentPane.add(btnOnartu);

		btnDeuseztatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EskaerakGUI.Reject"));
		btnDeuseztatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableEskaerak.getSelectedRow() != -1) {
					BLFacade facade = MainGUI.getBusinessLogic();
					facade.erreserbaDeuseztatu((int)model.getValueAt(tableEskaerak.getSelectedRow(),6));
					model.removeRow(tableEskaerak.getSelectedRow());
				}		
			}
		});
		btnDeuseztatu.setEnabled(false);
		btnDeuseztatu.setBounds(281, 207, 137, 21);
		contentPane.add(btnDeuseztatu);

		btnAtzera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back")); 
		btnAtzera.setBounds(221, 251, 85, 21);
		contentPane.add(btnAtzera);
		btnAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame a = new GidariGUI(u);
				a.setVisible(true);
			}
		});



		if(tableEskaerak.getSelectedRow() != -1) {
			btnOnartu.setEnabled(true);
			btnDeuseztatu.setEnabled(true);
		}


	}

	private void loadReservedRides(User u) {
		Driver d = (Driver) u;
		BLFacade facade = MainGUI.getBusinessLogic();
		List<Erreserba> reservedRides = facade.getReservedRidesByDriver(d);

		String[] columnNames = {
				ResourceBundle.getBundle("Etiquetas").getString("From"),
				ResourceBundle.getBundle("Etiquetas").getString("To"),
				ResourceBundle.getBundle("Etiquetas").getString("Date"),
				ResourceBundle.getBundle("Etiquetas").getString("Price"),
				ResourceBundle.getBundle("Etiquetas").getString("Traveler"),
				ResourceBundle.getBundle("Etiquetas").getString("NumberOfPlaces"),
				ResourceBundle.getBundle("Etiquetas").getString("BookNumber")};

		model = new DefaultTableModel(columnNames, 0);

		tableEskaerak.setModel(model);

		model.setRowCount(0);

		if (reservedRides != null && !reservedRides.isEmpty()) {
			for (Erreserba erreserba : reservedRides) {
				Object[] row = {
						erreserba.getBidaia().getFrom(),
						erreserba.getBidaia().getTo(),
						erreserba.getBidaia().getDate(),
						erreserba.getDiruIzoztua(),
						erreserba.getTraveler().getIzena(),
						erreserba.getnPlaces(),
						erreserba.getBookNumber()	
				};
				model.addRow(row);
			}
		    if (model.getRowCount() > 0) {
		    	tableEskaerak.setRowSelectionInterval(0, 0);
		    }
		}


		tableEskaerak.revalidate();
		tableEskaerak.repaint();
	}


}
