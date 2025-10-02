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
import domain.Balorazioa;
import domain.Bidaiaria;
import domain.Driver;
import domain.User;


public class BalorazioaIkusiGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableBalorazioak;
	private JButton btnAtzera;
	private JScrollPane scrollPaneBalorazioak;
	private DefaultTableModel model;



	/**
	 * Create the frame.
	 */
	public BalorazioaIkusiGUI(User u) {
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("BalorazioaIkusiGUI.title"));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(u instanceof Bidaiaria) {
			setBounds(100, 100, 601, 359);
		} else {
			setBounds(800, 100, 601, 359);
		}
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		scrollPaneBalorazioak = new JScrollPane();
		scrollPaneBalorazioak.setBounds(46, 23, 492, 222);
		contentPane.add(scrollPaneBalorazioak);

		tableBalorazioak = new JTable();
		tableBalorazioak.setForeground(new Color(255, 255, 255));
		scrollPaneBalorazioak.setViewportView(tableBalorazioak);

		tableBalorazioak.setForeground(Color.BLACK);
		tableBalorazioak.setBackground(Color.WHITE);

		loadBalorazioak(u);

		btnAtzera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnAtzera.setBounds(214, 267, 146, 31);
		contentPane.add(btnAtzera);
		btnAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame a;
				if(u instanceof Bidaiaria) {
					a = new BidaiariGUI((Bidaiaria)u);
				} else {
					a = new GidariGUI((Driver)u);
				}
				a.setVisible(true);
			}
		});    



		setContentPane(contentPane);

	}


	private void loadBalorazioak(User u) {
		BLFacade facade = MainGUI.getBusinessLogic();
		List<Balorazioa> balorazioak = facade.balorazioaErakutsi(u);
		String[] columnNames = {ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusiGUI.UserEmail"),
								ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusiGUI.Rating"),
								ResourceBundle.getBundle("Etiquetas").getString("Deskripzioa")};
		
		model = new DefaultTableModel(columnNames, 0);

		tableBalorazioak.setModel(model);

		model.setRowCount(0);

		if (balorazioak != null && !balorazioak.isEmpty()) {
			for (Balorazioa b : balorazioak) {
				Object[] row = {
						b.getUserEmail(),
						b.getPuntuazioa(),
						b.getDeskripzioa()
				};
				model.addRow(row);

			}
			tableBalorazioak.setRowSelectionInterval(0, 0);
		}
	}


}