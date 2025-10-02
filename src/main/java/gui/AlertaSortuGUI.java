package gui;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import domain.Bidaiaria;
import exceptions.AlertaAlreadyExistException;
import exceptions.IsEmptyException;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AlertaSortuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNondik;
	private JTextField txtNora;
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public AlertaSortuGUI(Bidaiaria b) {
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("AlertaSortuGUI.title"));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 553, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JLabel lblMezua = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		lblMezua.setForeground(Color.RED);
		lblMezua.setBounds(10, 195, 441, 14);
		contentPane.add(lblMezua);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblNondik = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("From"));
		lblNondik.setBounds(33, 84, 45, 13);
		contentPane.add(lblNondik);
		JLabel lblNora = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("To"));
		lblNora.setBounds(33, 140, 45, 13);
		contentPane.add(lblNora);
		txtNondik = new JTextField();
		txtNondik.setBounds(117, 81, 96, 19);
		contentPane.add(txtNondik);
		txtNondik.setColumns(10);
		txtNora = new JTextField();
		txtNora.setBounds(117, 137, 96, 19);
		contentPane.add(txtNora);
		txtNora.setColumns(10);
		JButton btnAtzera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame a = new BidaiariGUI(b);
				a.setVisible(true);
			}
		});
		btnAtzera.setBounds(96, 220, 85, 21);
		contentPane.add(btnAtzera);
		JButton btnSortu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AlertaSortuGUI.CreateAlert"));
		btnSortu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					lblMezua.setText("");
					BLFacade facade = MainGUI.getBusinessLogic();
					String nondik = txtNondik.getText();
					String nora = txtNora.getText();
					datuakZuzenak(nondik,nora);
					facade.alertaSortu(nondik,nora,jCalendar1.getDate(),b);
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("AlertaSortuGUI.AlertCreated"));
					txtNondik.setText("");
					txtNora.setText("");
				} catch (IsEmptyException e2) {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.IsEmptyException"));
				} catch (IllegalArgumentException e2) {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.OnlyLettersException"));  
				} catch (AlertaAlreadyExistException e2) {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.AlertaAlreadyExistException"));
				}
			}
		});
		btnSortu.setBounds(310, 220, 138, 21);
		contentPane.add(btnSortu);
		jCalendar1.setBounds(276, 34, 225, 150);


		// Code for JCalendar
		jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{

				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);

					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar1.setCalendar(calendarAct);

					}
				}
			}
		});
		this.getContentPane().add(jCalendar1);
	}
	
	private void datuakZuzenak(String nondik, String nora) throws IsEmptyException, IllegalArgumentException {
		if (nondik.isEmpty() || nora.isEmpty()) {
			throw new IsEmptyException();
		} 	
		if (!nondik.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") || !nora.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
			throw new IllegalArgumentException();
		}	
	}
	
}