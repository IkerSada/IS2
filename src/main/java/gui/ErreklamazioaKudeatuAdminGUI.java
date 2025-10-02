package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Admin;
import domain.Erreklamazioa;
import domain.Mezua;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ErreklamazioaKudeatuAdminGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableErreklamazioak;
	private JTable tableMezuak; 
	private JButton btnOnartu;
	private JScrollPane scrollPaneBalorazioak;
	private JScrollPane scrollPaneMezuak; 
	private DefaultTableModel model;
	private DefaultTableModel model2; 
	private JButton btnDeuseztatu;
	private JLabel lblMezua;



	/**
	 * Create the frame.
	 */
	public ErreklamazioaKudeatuAdminGUI(Admin a) {
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Admin"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 700, 615, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		scrollPaneMezuak = new JScrollPane();
		
		lblMezua = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		lblMezua.setBounds(178, 191, 216, 13);
		lblMezua.setForeground(Color.RED);
		contentPane.add(lblMezua);

		tableMezuak = new JTable();
		tableMezuak.setForeground(new Color(255, 255, 255));
		scrollPaneMezuak.setViewportView(tableMezuak);	
		tableMezuak.setForeground(Color.BLACK);
		tableMezuak.setBackground(Color.WHITE);

		scrollPaneBalorazioak = new JScrollPane();
		scrollPaneBalorazioak.setBounds(28, 23, 371, 148);
		contentPane.add(scrollPaneBalorazioak);
		tableErreklamazioak = new JTable();

		tableErreklamazioak.setForeground(new Color(255, 255, 255));
		scrollPaneBalorazioak.setViewportView(tableErreklamazioak);
		tableErreklamazioak.setForeground(Color.BLACK);
		tableErreklamazioak.setBackground(Color.WHITE);


		loadErreklamazioak(a);

		btnOnartu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Accept"));
		btnOnartu.setBounds(96, 215, 146, 31);
		contentPane.add(btnOnartu);
		btnOnartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableErreklamazioak.getSelectedRow() != -1) {
					BLFacade facade = MainGUI.getBusinessLogic();
					int bidai_errenkada = tableErreklamazioak.getSelectedRow();
					int erreserbaZenbaki = Integer.parseInt(tableErreklamazioak.getValueAt(bidai_errenkada,0).toString());
					facade.egoeraEzarriAdmin(erreserbaZenbaki,"onartu",a);
					loadErreklamazioak(a);
				} else {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.IsEmptyException"));
				}
			}
		});    

		tableErreklamazioak.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(tableErreklamazioak.getSelectedRow() != -1) {
					int bidai_errenkada = tableErreklamazioak.getSelectedRow();
					int erreserbaZenbaki = Integer.parseInt(tableErreklamazioak.getValueAt(bidai_errenkada,0).toString());
					loadMezuak(erreserbaZenbaki);
				}
			}
		});

		setContentPane(contentPane);

		btnDeuseztatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EskaerakGUI.Reject")); //$NON-NLS-1$ //$NON-NLS-2$
		btnDeuseztatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableErreklamazioak.getSelectedRow() != -1) {
					BLFacade facade = MainGUI.getBusinessLogic();
					int bidai_errenkada = tableErreklamazioak.getSelectedRow();
					int erreserbaZenbaki = (int) tableErreklamazioak.getValueAt(bidai_errenkada,0);
					facade.egoeraEzarriAdmin(erreserbaZenbaki,"deuseztatu",a);
					loadErreklamazioak(a);
				} else {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.IsEmptyException"));
				}
			}
		});
		btnDeuseztatu.setBounds(340, 215, 146, 31);
		contentPane.add(btnDeuseztatu);

		scrollPaneMezuak.setBounds(431, 23, 146, 150);
		contentPane.add(scrollPaneMezuak);

	}


	private void loadErreklamazioak(Admin a) {
		BLFacade facade = MainGUI.getBusinessLogic();
		List<Erreklamazioa> erreklamazioak = facade.erreklamazioaErakutsiAdmin(a);
		String[] columnNames = {ResourceBundle.getBundle("Etiquetas").getString("ClaimNumber"),
				ResourceBundle.getBundle("Etiquetas").getString("Email"),
				ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusiGUI.Description"),
				ResourceBundle.getBundle("Etiquetas").getString("Status")
		};

		model = new DefaultTableModel(columnNames, 0);

		tableErreklamazioak.setModel(model);

		model.setRowCount(0);

		if (erreklamazioak != null && !erreklamazioak.isEmpty()) {
			for (Erreklamazioa e : erreklamazioak) {
				Object[] row = {
						e.getErreklamazioZenbaki(),
						e.getNori().getEmail(),
						e.getDeskripzioa(),
						e.getEgoera()
				};
				model.addRow(row);

			}

			if (model.getRowCount() > 0) {
				tableErreklamazioak.setRowSelectionInterval(0, 0);
				int bidai_errenkada = tableErreklamazioak.getSelectedRow();
				int erreserbaZenbaki = (int) tableErreklamazioak.getValueAt(bidai_errenkada,0);
				loadMezuak(erreserbaZenbaki);
			}
		} else {
			String[] columnNames0 = {
					ResourceBundle.getBundle("Etiquetas").getString("Mezua"),
			};
			DefaultTableModel model0 = new DefaultTableModel(columnNames0, 0);

			tableMezuak.setModel(model0);
		}
	}

	private void loadMezuak(Integer errekzenb) {
		BLFacade facade = MainGUI.getBusinessLogic();
		List<Mezua> mezuak = facade.mezuakLortu(errekzenb);
		String[] columnNames = {
				ResourceBundle.getBundle("Etiquetas").getString("Mezua"),
		};

		model2 = new DefaultTableModel(columnNames, 0);

		tableMezuak.setModel(model2);

		model2.setRowCount(0);

		if (mezuak != null && !mezuak.isEmpty()) {
			for (Mezua m : mezuak) {
				Object[] row = {
						m.getTestua()
				};
				model2.addRow(row);
			}

			if (model2.getRowCount() > 0) {
				tableMezuak.setRowSelectionInterval(0, 0);
			}

		}
	}

}