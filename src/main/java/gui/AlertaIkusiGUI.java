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
import domain.Alerta;
import domain.Bidaiaria;


public class AlertaIkusiGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableBalorazioak;
	private JButton btnAtzera;
	//
	private JScrollPane scrollPaneBalorazioak;
	private DefaultTableModel model;



	/**
	 * Create the frame.
	 */
	public AlertaIkusiGUI(Bidaiaria b) {

		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("AlertaIkusiGUI.title"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 601, 359);
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

		loadAlertak(b);

		btnAtzera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnAtzera.setBounds(214, 267, 146, 31);
		contentPane.add(btnAtzera);
		btnAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame a = new BidaiariGUI(b);
				a.setVisible(true);
			}
		});



		setContentPane(contentPane);

	}


	private void loadAlertak(Bidaiaria b) {
		BLFacade facade = MainGUI.getBusinessLogic();
		List<Alerta> alertak = facade.alertaIkusi(b);

		String[] columnNames = {ResourceBundle.getBundle("Etiquetas").getString("AlertaIkusiGUI.AlertNumber"),
				ResourceBundle.getBundle("Etiquetas").getString("From"),
				ResourceBundle.getBundle("Etiquetas").getString("To"),
				ResourceBundle.getBundle("Etiquetas").getString("Date"),
				ResourceBundle.getBundle("Etiquetas").getString("AlertaIkusiGUI.Notified")};
		model = new DefaultTableModel(columnNames, 0);

		tableBalorazioak.setModel(model);

		model.setRowCount(0);

		if (alertak != null && !alertak.isEmpty()) {
			for (Alerta a : alertak) {
				Object[] row = {
						a.getAlertazbk(),
						a.getNondik(),
						a.getNora(),
						a.getDate(),
						a.isAbisatuta()
				};
				model.addRow(row);

			}
			tableBalorazioak.setRowSelectionInterval(0, 0);
		}
	}


}
