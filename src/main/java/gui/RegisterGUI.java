package gui;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import businessLogic.*;
import exceptions.AdinTxikikoException;
import exceptions.IsEmptyException;

public class RegisterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtIzena;
	private JTextField txtAbizena;
	private JTextField txtEmail;
	private JLabel lblIzena;
	private JLabel lblAbizena;
	private JLabel lblJaiotzeData;
	private JLabel lblSexua;
	private JLabel lblErabiltzaileMota;
	private JLabel lblemaila;
	private JRadioButton rdbtnEmakume;
	private JRadioButton rdbtnBidaiari;
	private JRadioButton rdbtnGizon;
	private JRadioButton rdbtnGidari;
	private JButton btnAtzera;
	private JButton btnErregistratu;
	private JPasswordField pasahitzafield;
	
	private String sexua=null;
	private String erabiltzaileMota=null;
	private final ButtonGroup genderButtonGroup1 = new ButtonGroup(); 
	private final ButtonGroup userTypeButtonGroup = new ButtonGroup(); 
	
	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;

	private JLabel lblMezua;

	/**
	 * Create the frame.
	 */
	public RegisterGUI() {
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Register"));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 401);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblMezua = new JLabel();
		lblMezua.setBounds(32, 311, 349, 13);
		lblMezua.setForeground(Color.red);
		contentPane.add(lblMezua);
		
		txtIzena = new JTextField();
		txtIzena.setBounds(183, 11, 198, 19);
		contentPane.add(txtIzena);
		txtIzena.setColumns(10);
		txtAbizena = new JTextField();
		txtAbizena.setBounds(183, 43, 198, 19);
		contentPane.add(txtAbizena);
		txtAbizena.setColumns(10);
		txtEmail = new JTextField();
		txtEmail.setBounds(183, 256, 198, 19);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		rdbtnBidaiari = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Traveler"));
		rdbtnBidaiari.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnBidaiari.isSelected()) {
					erabiltzaileMota="Bidaiaria";	
				}
			}
		});
		rdbtnBidaiari.setBounds(278, 222, 103, 21);
		contentPane.add(rdbtnBidaiari);
		
		rdbtnGidari = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("Driver"));
		rdbtnGidari.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnGidari.isSelected()) {
					erabiltzaileMota="Gidaria";
				}
			}
		});
		rdbtnGidari.setBounds(173, 222, 103, 21);
		contentPane.add(rdbtnGidari);
		
		userTypeButtonGroup.add(rdbtnBidaiari); 
		userTypeButtonGroup.add(rdbtnGidari); 
		
		rdbtnEmakume = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Female"));
		rdbtnEmakume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnEmakume.isSelected()) {
					sexua="Emakume";
				}
			}
		});
		rdbtnEmakume.setBounds(278, 199, 103, 21);
		contentPane.add(rdbtnEmakume);
		rdbtnGizon = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Male"));
		rdbtnGizon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnGizon.isSelected()) {
					sexua="Gizona";
				}
			}
		});
		rdbtnGizon.setBounds(173, 199, 103, 21);
		contentPane.add(rdbtnGizon);
		
		genderButtonGroup1.add(rdbtnGizon); 
		genderButtonGroup1.add(rdbtnEmakume);
		
		lblIzena = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Name"));
		lblIzena.setBounds(32, 14, 141, 13);
		contentPane.add(lblIzena);
		lblAbizena = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Surname"));
		lblAbizena.setBounds(32, 46, 141, 13);
		contentPane.add(lblAbizena);
		lblJaiotzeData = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.BirthDate"));;
		lblJaiotzeData.setBounds(32, 80, 141, 13);
		contentPane.add(lblJaiotzeData);
		lblSexua = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Gender"));
		lblSexua.setBounds(32, 203, 135, 13);
		contentPane.add(lblSexua);
		lblErabiltzaileMota = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.UserType"));
		lblErabiltzaileMota.setBounds(32, 226, 135, 13);
		contentPane.add(lblErabiltzaileMota);
		lblemaila = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Email"));
		lblemaila.setBounds(32, 259, 141, 13);
		contentPane.add(lblemaila);
		JLabel lblpasahitza = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PassWord"));
		lblpasahitza.setBounds(32, 288, 141, 13);
		contentPane.add(lblpasahitza);
		btnAtzera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnAtzera.setBounds(104, 330, 103, 21);
		contentPane.add(btnAtzera);
		
		
		btnErregistratu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Register"));
		btnErregistratu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				
				try {
					Date jaiotzeData = jCalendar1.getDate();
					datuakZuzenak(txtIzena.getText(), txtAbizena.getText(), jaiotzeData, sexua, erabiltzaileMota, txtEmail.getText(), new String(pasahitzafield.getPassword()));
					if(facade.erregistratu(txtIzena.getText(), txtAbizena.getText(), jaiotzeData, sexua, erabiltzaileMota, txtEmail.getText(), new String(pasahitzafield.getPassword()))) {
						JFrame a = new LoginGUI();
						a.setVisible(true);
						setVisible(false);
					} else {
						lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.UserAlreadyExist"));
					}	
				} catch (IllegalArgumentException e2) {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.OnlyLettersException"));        
				} catch (AdinTxikikoException e3) {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.AdinTxikikoException"));
				} catch (IsEmptyException e3) {
					lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.IsEmptyException"));
				}
			}
		
			
		});
		btnErregistratu.setBounds(226, 330, 103, 21);
		contentPane.add(btnErregistratu);
		pasahitzafield = new JPasswordField();
		pasahitzafield.setBounds(183, 285, 198, 19);
		contentPane.add(pasahitzafield);	
		
		
		jCalendar1.setBounds(new Rectangle(183, 74, 198, 118));

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

		this.getContentPane().add(jCalendar1, null);
		
	}
	
	private void datuakZuzenak(String izena, String abizena, Date jaiotzeData, String sexua, String erabiltzaileMota, String email, String pasahitza) throws IllegalArgumentException, AdinTxikikoException, IsEmptyException {
		if (izena.isEmpty() || abizena.isEmpty() || sexua==null || erabiltzaileMota==null || email.isEmpty() || pasahitza.isEmpty()) {
			throw new IsEmptyException();
		} 
		if (!izena.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") || !abizena.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
			throw new IllegalArgumentException();
		}	    
		LocalDate jaioData = jaiotzeData.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate unekoData = LocalDate.now();
		int urteKopurua = Period.between(jaioData, unekoData).getYears();
		if (urteKopurua < 18) {
			throw new AdinTxikikoException();
		}	
	}
	
}