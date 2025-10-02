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
import domain.Erreklamazioa;
import domain.Mezua;
import exceptions.ErreklamazioaDeuseztatuaExpcetion;
import exceptions.IsEmptyException;
import exceptions.erreklamazioaEbatzitaException;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ErreklamazioaKudeatuGidariGUI extends JFrame {

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
	private JTextField txtErantzuna;
	private JButton btnAtzera;
	private JLabel lblMezua;
	private JLabel lblErantzuna;



	/**
	 * Create the frame.
	 */
	public ErreklamazioaKudeatuGidariGUI(Driver g) {

		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaKudeatuGUI.title"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(800, 100, 625, 394);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);


		scrollPaneMezuak = new JScrollPane();
		tableMezuak = new JTable();
		tableMezuak.setForeground(new Color(255, 255, 255));
		scrollPaneMezuak.setViewportView(tableMezuak);



		tableMezuak.setForeground(Color.BLACK);
		tableMezuak.setBackground(Color.WHITE);


		lblErantzuna = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("GehituNahiDuzunMezua")); //$NON-NLS-1$ //$NON-NLS-2$
		lblErantzuna.setBounds(28, 210, 133, 13);
		contentPane.add(lblErantzuna);

		scrollPaneBalorazioak = new JScrollPane();
		scrollPaneBalorazioak.setBounds(28, 23, 371, 148);
		contentPane.add(scrollPaneBalorazioak);

		tableErreklamazioak = new JTable();
		tableErreklamazioak.setForeground(new Color(255, 255, 255));
		scrollPaneBalorazioak.setViewportView(tableErreklamazioak);

		tableErreklamazioak.setForeground(Color.BLACK);
		tableErreklamazioak.setBackground(Color.WHITE);

		txtErantzuna = new JTextField("");
		txtErantzuna.setBounds(170, 210, 394, 53);
		contentPane.add(txtErantzuna);
		txtErantzuna.setColumns(10);

		lblMezua = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		lblMezua.setBounds(78, 331, 429, 13);
		lblMezua.setForeground(Color.RED);
		contentPane.add(lblMezua);

		loadErreklamazioak(g);

		btnOnartu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Accept"));
		btnOnartu.setBounds(173, 289, 132, 31);
		contentPane.add(btnOnartu);
		btnOnartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableErreklamazioak.getSelectedRow() != -1) {
					BLFacade facade = MainGUI.getBusinessLogic();
					int bidai_errenkada = tableErreklamazioak.getSelectedRow();
					int errekZenb = (int) tableErreklamazioak.getValueAt(bidai_errenkada,0);
					try {
						facade.egoeraEzarri(errekZenb,"onartu");
						loadErreklamazioak(g);
					} catch(erreklamazioaEbatzitaException e1) {
						lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.ErreklamazioaEbatzita"));
					}
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
					int erreserbaZenbaki = (int) tableErreklamazioak.getValueAt(bidai_errenkada,0);
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
					try {
						facade.egoeraEzarri(erreserbaZenbaki,"deuseztatu");
						loadErreklamazioak(g);
					} catch(erreklamazioaEbatzitaException e1) {
						lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.ErreklamazioaEbatzita"));
					}
				} else {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.IsEmptyException"));
				}
			}
		});
		btnDeuseztatu.setBounds(315, 289, 132, 31);
		contentPane.add(btnDeuseztatu);


		btnAtzera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnAtzera.setBounds(23, 289, 138, 31);
		contentPane.add(btnAtzera);
		btnAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame a = new GidariGUI(g);
				a.setVisible(true);

			}
		});



		JButton btnBidali = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Bidali"));
		btnBidali.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mezua = txtErantzuna.getText();
				try {
					if(tableErreklamazioak.getSelectedRow() != -1) {
						BLFacade facade = MainGUI.getBusinessLogic();
						int bidai_errenkada = tableErreklamazioak.getSelectedRow();
						int erreklamaziozenb = (int) tableErreklamazioak.getValueAt(bidai_errenkada,0);
						Erreklamazioa err = facade.erreklamazioaLortu(erreklamaziozenb);
						if(err.getEgoera().equals("deuseztatu")) {
							throw new ErreklamazioaDeuseztatuaExpcetion();
						} else if(mezua == null || mezua.trim().isEmpty()) {
							throw new IsEmptyException();
						}
						mezua = ResourceBundle.getBundle("Etiquetas").getString("Driver") + ": " + mezua;
						facade.addMezua(mezua,erreklamaziozenb);
						loadErreklamazioak(g);
						lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("MezuaBidaliDa"));
					} else {
						throw new IsEmptyException();
					}
				} catch(ErreklamazioaDeuseztatuaExpcetion e2) {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.ErreklamazioaDeuseztatuaExpcetion"));
				} catch(IsEmptyException e2) {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.IsEmptyException"));
				}
			}
		});

		scrollPaneMezuak.setBounds(431, 23, 146, 150);
		contentPane.add(scrollPaneMezuak);


		btnBidali.setBounds(457, 289, 132, 31);
		contentPane.add(btnBidali);



	}


	private void loadErreklamazioak(Driver g) {
		txtErantzuna.setText("");
		BLFacade facade = MainGUI.getBusinessLogic();
		List<Erreklamazioa> erreklamazioak = facade.erreklamazioaErakutsiGidari(g);
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
				int erreklamazioZenbaki = (int) tableErreklamazioak.getValueAt(bidai_errenkada,0);
				loadMezuak(erreklamazioZenbaki);
			}
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