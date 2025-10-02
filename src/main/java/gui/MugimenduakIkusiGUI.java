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
import domain.Driver;
import domain.Mugimendua;
import domain.User;


public class MugimenduakIkusiGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableMugimenduak;
	private JButton btnAtzera;
	private JScrollPane scrollPaneErreserbaOnartuak;
	private DefaultTableModel model;



	/**
	 * Create the frame.
	 */
	public MugimenduakIkusiGUI(User u) {

		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusiGUI.title"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(u instanceof Bidaiaria) {
			setBounds(100, 100, 601, 359);
		} else {
			setBounds(800, 100, 601, 359);
		}
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		scrollPaneErreserbaOnartuak = new JScrollPane();
		scrollPaneErreserbaOnartuak.setBounds(46, 23, 492, 222);
		contentPane.add(scrollPaneErreserbaOnartuak);

		tableMugimenduak = new JTable();
		tableMugimenduak.setForeground(new Color(255, 255, 255));
		scrollPaneErreserbaOnartuak.setViewportView(tableMugimenduak);

		tableMugimenduak.setForeground(Color.BLACK);
		tableMugimenduak.setBackground(Color.WHITE);

		loadMugimenduak(u);

		btnAtzera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnAtzera.setBounds(214, 267, 146, 31);
		contentPane.add(btnAtzera);
		btnAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				if(u instanceof Bidaiaria) {
					JFrame a = new BidaiariGUI((Bidaiaria)u);
					a.setVisible(true);
				} else {
					JFrame a = new GidariGUI((Driver)u);
					a.setVisible(true);
				}
			}
		});    



		setContentPane(contentPane);

	}


	private void loadMugimenduak(User u) {
		BLFacade facade = MainGUI.getBusinessLogic();
		List<Mugimendua> mugimenduak = facade.getErabiltzailearenMugimenduak(u);

		System.out.println(mugimenduak);

		String[] columnNames = {ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusiGUI.MovementNumber"),
				// ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusiGUI.Balance"),		KENDU
				ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusiGUI.Description")};
		
		model = new DefaultTableModel(columnNames, 0);

		tableMugimenduak.setModel(model);

		model.setRowCount(0);

		if (mugimenduak != null && !mugimenduak.isEmpty()) {
			for (Mugimendua m : mugimenduak) {
				Object[] row = {
						m.getMugimenduZenbaki(),
						// m.getDirua(),			KENDU
						m.getDeskripzioa()
				};
				model.addRow(row);

			}
			tableMugimenduak.setRowSelectionInterval(0, 0);
		}
	}


}