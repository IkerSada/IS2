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
import domain.Bidaiaria;
import domain.Erreserba;


public class ErreserbaBaieztatuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableErreserbaOnartuak;
	private JButton btnBaieztatu;
	private JButton btnAtzera;
	private JScrollPane scrollPaneErreserbaOnartuak;
	private DefaultTableModel model;



	/**
	 * Create the frame.
	 */
	public ErreserbaBaieztatuGUI(Bidaiaria u) {

		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("ErreserbaBaieztatuGUI.title"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 476, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		scrollPaneErreserbaOnartuak = new JScrollPane();
		scrollPaneErreserbaOnartuak.setBounds(46, 23, 366, 174);
		contentPane.add(scrollPaneErreserbaOnartuak);

		tableErreserbaOnartuak = new JTable();
		tableErreserbaOnartuak.setForeground(new Color(255, 255, 255));
		scrollPaneErreserbaOnartuak.setViewportView(tableErreserbaOnartuak);

		tableErreserbaOnartuak.setForeground(Color.BLACK);
		tableErreserbaOnartuak.setBackground(Color.WHITE);
		
		loadErreserbak(u);
		
		btnBaieztatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Confirm"));
		btnBaieztatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableErreserbaOnartuak.getSelectedRow() != -1) {
					BLFacade facade = MainGUI.getBusinessLogic();
					int bidai_errenkada = tableErreserbaOnartuak.getSelectedRow();
					int erreserbaZenbaki = (int) tableErreserbaOnartuak.getValueAt(bidai_errenkada,6);	
					facade.erreserbaBaieztatu(erreserbaZenbaki);
					model.removeRow(tableErreserbaOnartuak.getSelectedRow());
				}
			}
		});
		btnBaieztatu.setEnabled(false);
		btnBaieztatu.setBounds(235, 219, 131, 21);
		contentPane.add(btnBaieztatu);

		btnAtzera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnAtzera.setBounds(71, 219, 131, 21);
		contentPane.add(btnAtzera);
		btnAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame a = new BidaiariGUI(u);
				a.setVisible(true);
			}
		});    


		if(tableErreserbaOnartuak.getSelectedRow() != -1) {
			btnBaieztatu.setEnabled(true);
		}
		setContentPane(contentPane);

	}


	private void loadErreserbak(Bidaiaria b) {
		BLFacade facade = MainGUI.getBusinessLogic();
		List<Erreserba> erreserbak = facade.getBidaiariarenErreserbak(b);
		
		System.out.println(erreserbak);

		String[] columnNames = {
				ResourceBundle.getBundle("Etiquetas").getString("From"),
				ResourceBundle.getBundle("Etiquetas").getString("To"),
				ResourceBundle.getBundle("Etiquetas").getString("Date"),
				ResourceBundle.getBundle("Etiquetas").getString("Price"),
				ResourceBundle.getBundle("Etiquetas").getString("Traveler"),
				ResourceBundle.getBundle("Etiquetas").getString("NumberOfPlaces"),
				ResourceBundle.getBundle("Etiquetas").getString("BookNumber")};
		
		model = new DefaultTableModel(columnNames, 0);

		tableErreserbaOnartuak.setModel(model);

		model.setRowCount(0);

		if (erreserbak != null && !erreserbak.isEmpty()) {
			for (Erreserba e : erreserbak) {
				if(e.getEgoera().equals("onartu")) {
					Object[] row = {
							e.getBidaia().getFrom(),
							e.getBidaia().getTo(),
							e.getBidaia().getDate(),
							e.getBidaia().getPrice(),
							e.getTraveler().getIzena(),
							e.getnPlaces(),
							e.getBookNumber()
					};
					model.addRow(row);
				}
			}
		    if (model.getRowCount() > 0) {
		        tableErreserbaOnartuak.setRowSelectionInterval(0, 0);
		    }
		}
	}


}