package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Ride;


public class BidaiaKantzelatuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableBidaiak;
	private JButton btnAtzera;
	private JScrollPane scrollPaneErreserbaOnartuak;
	private DefaultTableModel model;
	private BLFacade facade;



	/**
	 * Create the frame.
	 */
	public BidaiaKantzelatuGUI(Driver u) {
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("BidaiaKantzelatuGUI.title"));

		facade = MainGUI.getBusinessLogic();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(800, 100, 601, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		scrollPaneErreserbaOnartuak = new JScrollPane();
		scrollPaneErreserbaOnartuak.setBounds(46, 23, 492, 222);
		contentPane.add(scrollPaneErreserbaOnartuak);

		tableBidaiak = new JTable();
		tableBidaiak.setForeground(new Color(255, 255, 255));
		scrollPaneErreserbaOnartuak.setViewportView(tableBidaiak);

		tableBidaiak.setForeground(Color.BLACK);
		tableBidaiak.setBackground(Color.WHITE);

		loadBidaiak(u);

		btnAtzera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnAtzera.setBounds(104, 267, 146, 30);
		contentPane.add(btnAtzera);
		btnAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame a = new GidariGUI(u);
				a.setVisible(true);
			}
		});    



		setContentPane(contentPane);

		JButton btnKantzelatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BidaiaKantzelatuGUI.Kantzelatu"));
		btnKantzelatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				facade.bidaiKantzelatu(u,(int)model.getValueAt(tableBidaiak.getSelectedRow(),5));
				model.removeRow(tableBidaiak.getSelectedRow());
			}
		});
		btnKantzelatu.setBounds(330, 267, 146, 30);
		contentPane.add(btnKantzelatu);

	}


	private void loadBidaiak(Driver d) {

		List<Ride> bidaiak = facade.getRidesByDriver(d);

		String[] columnNames = {
				ResourceBundle.getBundle("Etiquetas").getString("From"),
				ResourceBundle.getBundle("Etiquetas").getString("To"),
				ResourceBundle.getBundle("Etiquetas").getString("Date"),
				ResourceBundle.getBundle("Etiquetas").getString("Price"),
				ResourceBundle.getBundle("Etiquetas").getString("NumberOfPlaces"),
				ResourceBundle.getBundle("Etiquetas").getString("RideNumber"),
				ResourceBundle.getBundle("Etiquetas").getString("Status")};


		model = new DefaultTableModel(columnNames, 0);

		tableBidaiak.setModel(model);

		model.setRowCount(0);

		if (bidaiak != null && !bidaiak.isEmpty()) {
			for (Ride r : bidaiak) {
				Object[] row = {
						r.getFrom(),
						r.getTo(),
						r.getDate(),
						r.getPrice(),
						r.getnPlaces(),
						r.getRideNumber(),
						r.getEgoera()
				};
				model.addRow(row);

			}
			tableBidaiak.setRowSelectionInterval(0, 0);
		}
	}
}