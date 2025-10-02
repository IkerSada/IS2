package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Bidaiaria;
import domain.Ride;
import exceptions.EserlekuHutsikException;
import exceptions.EserlekuMaxBainoGehiagoException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class ErreserbakEginGUI extends JFrame {
	private static final long serialVersionUID = 1L;


	private JComboBox<String> jComboBoxOrigin = new JComboBox<String>();
	DefaultComboBoxModel<String> originLocations = new DefaultComboBoxModel<String>();

	private JComboBox<String> jComboBoxDestination = new JComboBox<String>();
	DefaultComboBoxModel<String> destinationCities = new DefaultComboBoxModel<String>();

	private JLabel jLabelOrigin = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.LeavingFrom"));
	private JLabel jLabelDestination = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GoingTo"));
	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Rides")); 

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();

	private List<Date> datesWithRidesCurrentMonth = new Vector<Date>();

	private JTable tableRides= new JTable();

	private DefaultTableModel tableModelRides;


	private String[] columnNamesRides = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Driver"), 
			ResourceBundle.getBundle("Etiquetas").getString("ErreserbakEginGUI.NPlaces"), 
			ResourceBundle.getBundle("Etiquetas").getString("Price"),
			ResourceBundle.getBundle("Etiquetas").getString("ErreserbakEginGUI.BookNumber")
	};
	private JTextField nEserleku;
	private JButton btnErreserbatu;
	private JButton btnAtzera;
	private JLabel lblMezua;



	public ErreserbakEginGUI(Bidaiaria u)
	{
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("ErreserbakEginGUI.title"));

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));

		jLabelEventDate.setBounds(new Rectangle(560, 15, 140, 25));
		jLabelEvents.setBounds(162, 223, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelEvents);

		lblMezua = new JLabel(); 
		lblMezua.setBounds(10, 15, 380, 58);
		lblMezua.setForeground(Color.red);
		getContentPane().add(lblMezua);

		BLFacade facade = MainGUI.getBusinessLogic();
		List<String> origins=facade.getDepartCities();

		for(String location:origins) originLocations.addElement(location);

		jLabelOrigin.setBounds(new Rectangle(6, 100, 92, 20));
		jLabelDestination.setBounds(6, 132, 92, 16);
		getContentPane().add(jLabelOrigin);

		getContentPane().add(jLabelDestination);

		jComboBoxOrigin.setModel(originLocations);
		jComboBoxOrigin.setBounds(new Rectangle(181, 100, 172, 20));


		List<String> aCities=facade.getDestinationCities((String)jComboBoxOrigin.getSelectedItem());
		for(String aciti:aCities) {
			destinationCities.addElement(aciti);
		}

		jComboBoxOrigin.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				destinationCities.removeAllElements();
				BLFacade facade = MainGUI.getBusinessLogic();

				List<String> aCities=facade.getDestinationCities((String)jComboBoxOrigin.getSelectedItem());
				for(String aciti:aCities) {
					destinationCities.addElement(aciti);
				}
				tableModelRides.getDataVector().removeAllElements();
				tableModelRides.fireTableDataChanged();


			}
		});


		jComboBoxDestination.setModel(destinationCities);
		jComboBoxDestination.setBounds(new Rectangle(181, 130, 172, 20));
		jComboBoxDestination.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,	new Color(210,228,238));

				BLFacade facade = MainGUI.getBusinessLogic();

				datesWithRidesCurrentMonth=facade.getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
				paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);

			}
		});
		this.getContentPane().add(jComboBoxOrigin, null);

		this.getContentPane().add(jComboBoxDestination, null);


		jCalendar1.setBounds(new Rectangle(400, 50, 225, 150));


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



					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);

					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar estÃ¡ 30 de enero y se avanza al mes siguiente, devolverÃ­a 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este cÃ³digo se dejarÃ¡ como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						

						jCalendar1.setCalendar(calendarAct);

					}

					try {
						tableModelRides.setDataVector(null, columnNamesRides);
						tableModelRides.setColumnCount(5); // another column added to allocate ride objects

						BLFacade facade = MainGUI.getBusinessLogic();
						List<domain.Ride> rides=facade.getRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),UtilDate.trim(jCalendar1.getDate()));

						if (rides.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("ErreserbakEginGUI.NoRides")+ ": "+dateformat1.format(calendarAct.getTime()));
						else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Rides")+ ": "+dateformat1.format(calendarAct.getTime()));
						for (domain.Ride ride:rides){
							if (ride.getnPlaces() > 0 && ride.getEgoera().equals("martxan")) { 		
								Vector<Object> row = new Vector<Object>();
								row.add(ride.getDriver().getName());
								row.add(ride.getnPlaces());
								row.add(ride.getPrice());
								row.add(ride.getRideNumber());
								row.add(ride); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,3)
								tableModelRides.addRow(row);		
							}
						}
						datesWithRidesCurrentMonth=facade.getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
						paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);


					} catch (Exception e1) {

						e1.printStackTrace();
					}
					tableRides.getColumnModel().getColumn(0).setPreferredWidth(170);
					tableRides.getColumnModel().getColumn(1).setPreferredWidth(50);
					tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
					tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
					tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(4)); // not shown in JTable

				}
			} 

		});

		this.getContentPane().add(jCalendar1, null);

		scrollPaneEvents.setBounds(new Rectangle(108, 247, 463, 150));

		scrollPaneEvents.setViewportView(tableRides);
		tableModelRides = new DefaultTableModel(null, columnNamesRides);

		tableRides.setModel(tableModelRides);

		tableModelRides.setDataVector(null, columnNamesRides);
		tableModelRides.setColumnCount(5); // another column added to allocate ride objects

		tableRides.getColumnModel().getColumn(0).setPreferredWidth(170);
		tableRides.getColumnModel().getColumn(1).setPreferredWidth(50);
		tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
		tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);

		tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(4)); // not shown in JTable

		this.getContentPane().add(scrollPaneEvents, null);
		datesWithRidesCurrentMonth=facade.getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
		paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);


		//Eserleku kopurua aukeratu
		nEserleku = new JTextField();
		nEserleku.setBounds(181, 160, 172, 19);
		nEserleku.setColumns(10);
		nEserleku.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) { 
					e.consume();
				}
			}
		});
		this.getContentPane().add(nEserleku);


		//Eserleku kopurua etiketa
		JLabel lblEserlekuKopurua = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreserbakEginGUI.eserlekuKopurua")); 
		lblEserlekuKopurua.setBounds(6, 163, 165, 13);
		this.getContentPane().add(lblEserlekuKopurua);


		//Erreserbatu botoia
		btnErreserbatu = new JButton();
		btnErreserbatu.setBounds(340, 408, 150, 30);
		btnErreserbatu.setText(ResourceBundle.getBundle("Etiquetas").getString("ErreserbakEginGUI.Erreserbatu"));
		btnErreserbatu.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) { // Eserleku kantitatea begiratu behar da. 
				if(tableRides.getSelectedRow()!=-1) {
					int bidai_errenkada = tableRides.getSelectedRow();
					BLFacade facade = MainGUI.getBusinessLogic();	
					float prezioa = (float)tableRides.getValueAt(bidai_errenkada,2);
					int bidaiZenbaki = (int) tableRides.getValueAt(bidai_errenkada,3);	
					try {
						if(nEserleku.getText().isEmpty()){
							throw new EserlekuHutsikException();
						} else {
							int eserlekuKop = Integer.parseInt(nEserleku.getText());
							if(prezioa*eserlekuKop>u.getDirua()) {
								throw new IllegalArgumentException();
							}
							datuakZuzenak(nEserleku.getText(),eserlekuKop);
							facade.erreserbaEgin(bidaiZenbaki,(Bidaiaria)u,eserlekuKop);
							// u.setDirua(u.getDirua()-prezioa*eserlekuKop);		KENDU
							// GEHITU
							Ride r = facade.getRide(bidaiZenbaki);					
							float p = r.getBidaiarenPrezioa(eserlekuKop);
							u.diruaKendu(p);			  			
							//
							int bidaiEserleku = Integer.parseInt(tableRides.getValueAt(bidai_errenkada, 1).toString());		
							tableRides.setValueAt(bidaiEserleku-eserlekuKop, bidai_errenkada, 1);
							tableModelRides.fireTableDataChanged();
							if(bidaiEserleku-eserlekuKop<=0) {
								tableModelRides.removeRow(bidai_errenkada); 
							}
						}
					} catch (IllegalArgumentException e2) {
						lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.EzDagoSaldoNahikorik"));
					} catch (EserlekuHutsikException e3) {
						lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.EserlekuHutsik"));
					} catch (EserlekuMaxBainoGehiagoException e4) {
						lblMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Exception.EserlekuMaxBainoGehiago"));
					}
				}
			}
		});
		this.getContentPane().add(btnErreserbatu);


		//Atzera botoia
		btnAtzera = new JButton();
		btnAtzera.setBounds(185, 408, 130, 30);
		btnAtzera.setText(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnAtzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JFrame a = new BidaiariGUI((Bidaiaria)u);
				a.setVisible(true);
			}
		});	
		this.getContentPane().add(btnAtzera);

	}
	public static void paintDaysWithEvents(JCalendar jCalendar,List<Date> datesWithEventsCurrentMonth, Color color) {
		//		// For each day with events in current month, the background color for that day is changed to cyan.


		Calendar calendar = jCalendar.getCalendar();

		int month = calendar.get(Calendar.MONTH);
		int today=calendar.get(Calendar.DAY_OF_MONTH);
		int year=calendar.get(Calendar.YEAR);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);


		offset += 5;


		for (Date d:datesWithEventsCurrentMonth){

			calendar.setTime(d);


			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
			//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(color);
		}

		calendar.set(Calendar.DAY_OF_MONTH, today);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);


	}

	private void datuakZuzenak(String nEserleku, int eserlekuKop) throws EserlekuMaxBainoGehiagoException {
		if(eserlekuKop-Integer.parseInt(nEserleku)<0) {
			throw new EserlekuMaxBainoGehiagoException();
		}
	}
	
}
