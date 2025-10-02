package dataAccess;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import java.time.LocalDate;
import java.time.ZoneId;

//import javax.jws.WebMethod;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Admin;
import domain.Alerta;
import domain.Balorazioa;
import domain.Bidaiaria;
import domain.Driver;
import domain.Erreklamazioa;
import domain.Erreserba;
import domain.Kotxea;
import domain.Mezua;
import domain.Mugimendua;
import domain.Ride;
import domain.User;
import exceptions.AlertaAlreadyExistException;
import exceptions.KotxeaAlreadyExistException;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.erreklamazioaEbatzitaException;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	private  EntityManager  db;
	private  EntityManagerFactory emf;


	ConfigXML c=ConfigXML.getInstance();

	public DataAccess()  {
		if (c.isDatabaseInitialized()) {
			String fileName=c.getDbFilename();

			File fileToDelete= new File(fileName);
			if(fileToDelete.delete()){
				File fileToDeleteTemp= new File(fileName+"$");
				fileToDeleteTemp.delete();

				System.out.println("File deleted");
			} else {
				System.out.println("Operation failed");
			}
		}
		open();
		if  (c.isDatabaseInitialized())initializeDB();

		System.out.println("DataAccess created => isDatabaseLocal: "+c.isDatabaseLocal()+" isDatabaseInitialized: "+c.isDatabaseInitialized());

		close();

	}

	public DataAccess(EntityManager db) {
		this.db=db;
	}



	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){

		db.getTransaction().begin();

		try {

			Calendar today = Calendar.getInstance();

			int month=today.get(Calendar.MONTH);
			int year=today.get(Calendar.YEAR);
			if (month==12) { month=1; year+=1;}  


			// Create drivers 
			Driver driver1=new Driver("driver1@gmail.com","Aitor Fernandez");
			Driver driver2=new Driver("driver2@gmail.com","Ane Gaztañaga");
			Driver driver3=new Driver("driver3@gmail.com","Alejandra Redondo");

			// Bidaiari berria sortu
			User user1=new Bidaiaria("bidaiari@gmail.com","bidaiari","Magdalena Sevillano");
			user1.setDirua(100);

			// Gidari berria sortu
			User user2=new Driver("driver@gmail.com","driver","Fernando Rodriguez");
			user2.setDirua(50);

			// Administratzaile berria sortu
			Admin admin1 = new Admin("admin@gmail.com","admin"); 	//GEHITU 

			// Create rides
			driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 4, 7);
			driver1.addRide("Donostia", "Gazteiz", UtilDate.newDate(year,month,6), 4, 8);
			driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 4, 4);
			driver1.addRide("Donostia", "Iruña", UtilDate.newDate(year,month,7), 4, 8);

			driver2.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 3, 3);
			driver2.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 2, 5);
			driver2.addRide("Eibar", "Gasteiz", UtilDate.newDate(year,month,6), 2, 5);

			driver3.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,14), 1, 3);
			
			// GEHITU kotxea
			((Driver)user2).addKotxe("1234 ABC", 4, "Audi");
			//
			
			// GEHITU DUGU ADMIN-REN KUDEAKETA PROBATZEKO
			Ride bidaiberri2=((Driver)user2).addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 2, 5);
			Ride bidaiberri=((Driver)user2).addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,28), 2, 5);

			Erreserba errb0= new Erreserba(1,bidaiberri,(Bidaiaria) user1);
			Erreklamazioa err1=new Erreklamazioa(errb0, "esperientzia txarra", (Bidaiaria) user1, (Driver)user2);
			err1.setEgoera("deuseztatu");
			err1.addMezua("Bidaiari: nire dirua bueltan nahi dut");
			err1.addMezua("Gidari: ez");
			admin1.addErreklamazioa(err1);

			Erreserba errs4= new Erreserba(1,bidaiberri2,(Bidaiaria) user1);
			Erreklamazioa err4=new Erreklamazioa(errs4, "berandu iritsi gara", (Bidaiaria) user1, (Driver)user2);
			err4.setEgoera("deuseztatu");
			err4.addMezua("Bidaiari: gidaria oso motela da");
			err4.addMezua("Gidari: ez da egia");
			admin1.addErreklamazioa(err4);
			//
			
			db.persist(driver1);
			db.persist(driver2);
			db.persist(driver3);

			db.persist(user1);
			db.persist(user2);

			// GEHITU DUGU PROBA EGITEKO
			db.persist(bidaiberri2); 
			db.persist(errs4); 
			db.persist(err4); 
			
			db.persist(bidaiberri); 
			db.persist(errb0); 
			db.persist(err1); 
			db.persist(admin1);
			//
			
			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	public List<String> getDepartCities(){
		TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.from FROM Ride r ORDER BY r.from", String.class);
		List<String> cities = query.getResultList();
		return cities;

	}
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getArrivalCities(String from){
		TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.to FROM Ride r WHERE r.from=?1 ORDER BY r.to",String.class);
		query.setParameter(1, from);
		List<String> arrivingCities = query.getResultList(); 
		return arrivingCities;

	}
	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driverEmail to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail) throws  RideAlreadyExistException, RideMustBeLaterThanTodayException {
		System.out.println(">> DataAccess: createRide=> from= "+from+" to= "+to+" driver="+driverEmail+" date "+date);
		try {
			if(new Date().compareTo(date)>0) {
				throw new RideMustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
			}
			db.getTransaction().begin();

			Driver driver = db.find(Driver.class, driverEmail);
			if (driver.doesRideExists(from, to, date)) {
				db.getTransaction().commit();
				throw new RideAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
			}
			Ride ride = driver.addRide(from, to, date, nPlaces, price);
			//next instruction can be obviated
			db.persist(driver); 
			db.getTransaction().commit();

			return ride;
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}



	}

	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	public List<Ride> getRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getRides=> from= "+from+" to= "+to+" date "+date);

		List<Ride> res = new ArrayList<>();	
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",Ride.class);   
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, date);
		List<Ride> rides = query.getResultList();
		for (Ride ride:rides){
			res.add(ride);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		List<Date> res = new ArrayList<>();	

		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);


		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT r.date FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date BETWEEN ?3 and ?4",Date.class);   

		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, firstDayMonthDate);
		query.setParameter(4, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d:dates){
			res.add(d);
		}
		return res;
	}


	public void open() {

		String fileName=c.getDbFilename();
		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);
			db = emf.createEntityManager();
		}
		System.out.println("DataAccess opened => isDatabaseLocal: "+c.isDatabaseLocal());

	}

	public void close(){
		db.close();
		System.out.println("DataAcess closed");
	}



	public boolean erregistratu(String izena, String abizena, Date jaiotzeData, String sexua, String erabiltzaileMota, String email, String pasahitza) {
		open();
		if(erabiltzaileMota.equals("Gidaria")) {
			Driver u = db.find(Driver.class,email);
			if (u==null) {
				db.getTransaction().begin();
				Driver d=new Driver(izena, abizena, jaiotzeData, sexua, email, pasahitza);
				db.persist(d);
				db.getTransaction().commit();
				close();
				return true;
			} else {
				return false;
			}
		} else {
			Bidaiaria u = db.find(Bidaiaria.class,email);
			if (u==null) {
				db.getTransaction().begin();
				Bidaiaria b=new Bidaiaria(izena, abizena, jaiotzeData, sexua, email, pasahitza);
				db.persist(b);
				db.getTransaction().commit();
				close();
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean saioaHasi(String email, String pasahitza) {
		open();
		User u = db.find(User.class,email);
		close();
		boolean dago=false;
		if(u!=null) {
			if(u.erabiltzaileBerdina(email,pasahitza)) {
				dago=true;
			} 
		}
		return dago;
	}

	public User erabiltzaileaBilatu(String email) {
		open();
		User u = db.find(User.class,email);
		close();
		return u;
	}

	// GEHITU
	public boolean saioaHasiAdmin(String email, String pasahitza) { 
		open();
		Admin a = db.find(Admin.class,email);
		close();
		boolean dago=false;
		if(a!=null) {
			if(a.erabiltzaileBerdina(email,pasahitza)) {
				dago=true;
			} 
		}
		return dago;
	}

	// GEHITU
	public Admin erabiltzaileaBilatuAdmin(String email) { 
		open();
		Admin a = db.find(Admin.class,email);
		close();
		return a;
	}

	public void diruaSartu (float dirua, Bidaiaria t) {
		open();
		db.getTransaction().begin();

		// ZUZENDU
		// User bidaiari = db.find(Bidaiaria.class,t.getEmail());
		String email = t.getEmail();
		User bidaiari = db.find(Bidaiaria.class,email);

		// bidaiari.setDirua(bidaiari.getDirua()+dirua);	
		bidaiari.diruaGehitu(dirua);
		
		// Mugimendua m=new Mugimendua("Diruzorroan dirua sartu da");	
		// bidaiari.addMugimendua(m);
		// db.persist(m);
		bidaiari.addMugimendua("Diruzorroan dirua sartu da");
		//

		db.getTransaction().commit();
		close();
	}

	public void diruaAtera (float dirua, Driver d) {
		open();
		db.getTransaction().begin();

		// ZUZENDU
		// User gidari = db.find(Driver.class,d.getEmail());
		String email = d.getEmail();
		User gidari = db.find(Driver.class,email);

		// gidari.setDirua(gidari.getDirua()-dirua);	
		gidari.diruaKendu(dirua);	
		
		// Mugimendua m=new Mugimendua("Diruzorrotik dirua atera da");		
		// db.persist(m);
		gidari.addMugimendua("Diruzorrotik dirua atera da");
		//

		db.getTransaction().commit();
		close();
	}

	public List<Erreserba> getReservedRidesByDriver(Driver d) {
		open();
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.driver = :driver", Ride.class);
		query.setParameter("driver", d);
		List<Ride> rides = query.getResultList();

		List<Erreserba> reservedRides = new ArrayList<>();
		for (Ride ride : rides) {
			reservedRides.addAll(ride.getErreserbakItxaron()); 		
		}
		close();
		return reservedRides;
	}

	public void erreserbaEgin(int bidaiZenbaki, Bidaiaria bidaiari2, int eserlekuKop) {		
		open();

		// ZUZENDU
		// Bidaiaria bidaiari = db.find(Bidaiaria.class,bidaiari2.getEmail());
		String email = bidaiari2.getEmail();
		Bidaiaria bidaiari = db.find(Bidaiaria.class,email);
		//

		Ride r = db.find(Ride.class,bidaiZenbaki);
		Erreserba e = bidaiari.erreserbaBilatu(bidaiZenbaki);
		db.getTransaction().begin();
		if(e==null) {
			e = bidaiari.addErreserba(r,eserlekuKop);
		} else {
			if(e.getEgoera().equals("deuseztatu")) {
				e.setnPlaces(0);
			}
			e.setEgoera("itxaron"); 	

			// ZUZENDU
			// e.setnPlaces(e.getnPlaces()+eserlekuKop);	
			e.updatePlaces(eserlekuKop);
			//

		}
		r.updateSeat(eserlekuKop);

		// ZUZENDU
		// bidaiari.setDirua(bidaiari.getDirua()-r.getPrice()*eserlekuKop);		
		float prezioa = r.getBidaiarenPrezioa(eserlekuKop);
		bidaiari.diruaKendu(prezioa);

		// e.setDiruIzoztua(e.getDiruIzoztua()+r.getPrice()*eserlekuKop);						
		e.eguneratuDiruIzoztua(prezioa);	

		// Mugimendua m=new Mugimendua("Diruzorrotik erreserbaren prezioa kobratu da (diru hori izoztuta)");	
		// bidaiari.addMugimendua(m);
		// db.persist(m);
		bidaiari.addMugimendua("Diruzorrotik erreserbaren prezioa kobratu da (diru hori izoztuta)");
		//

		db.getTransaction().commit();
		close();
	}

	public void erreserbaOnartu(int erreserbaZenbaki, Driver gidari) {
		open();
		Erreserba e = db.find(Erreserba.class,erreserbaZenbaki);
		db.getTransaction().begin();
		e.setEgoera("onartu"); 
		db.getTransaction().commit();
		close();
	}

	public void erreserbaDeuseztatu(int erreserbaZenbaki) {
		open();
		Erreserba e = db.find(Erreserba.class,erreserbaZenbaki);
		db.getTransaction().begin();

		// ZUZENDU
		// e.getTraveler().setDirua(e.getTraveler().getDirua()+e.getBidaia().getPrice()*e.getnPlaces());	
		Bidaiaria b = e.getTraveler();
		float dirua = e.getDiruIzoztua();
		b.diruaGehitu(dirua);

		// e.getBidaia().setnPlaces(e.getBidaia().getnPlaces()+e.getnPlaces());	
		Ride r = e.getBidaia();
		int eserlekuKop = e.getnPlaces();
		r.updateSeatPlus(eserlekuKop);

		// Mugimendua m=new Mugimendua("Erreserba deuseztatu da eta dirua itzuli zaizu");
		// e.getTraveler().addMugimendua(m);
		// b.addMugimendua(m);
		// db.persist(m);
		b.addMugimendua("Erreserba deuseztatu da eta dirua itzuli zaizu");
		//

		e.setEgoera("deuseztatu");
		e.setDiruIzoztua(0);

		db.getTransaction().commit();
		close();
	}


	public void erreserbaBaieztatu(int erreserbaZenbaki) {
		open();
		Erreserba e = db.find(Erreserba.class,erreserbaZenbaki);
		db.getTransaction().begin();		
		e.setEgoera("amaituta");

		// ZUZENDU
		// Driver g = db.find(Driver.class, e.getBidaia().getDriver().getEmail());
		String email = e.getGidariarenEmaila();
		Driver g = db.find(Driver.class, email);

		// g.setDirua(g.getDirua()+e.getDiruIzoztua());	
		float dirua = e.getDiruIzoztua();
		g.diruaGehitu(dirua);

		// Mugimendua m=new Mugimendua("Egindako bidaiaren erreserba baten dirua gehitu da");	
		// db.persist(m);
		g.addMugimendua("Egindako bidaiaren erreserba baten dirua gehitu da");

		// e.getBidaia().bidaiaAmaitua();
		Ride r = e.getBidaia();
		r.bidaiaAmaitua();
		//

		db.persist(g);
		db.getTransaction().commit();
		close();
	}

	// ZUZENDU
	public Kotxea kotxeGehitu(String matrikula, int eserleku, String marka, Driver gidari) throws KotxeaAlreadyExistException {
		open();
		db.getTransaction().begin();
		// KENDU
		// Kotxea k=new Kotxea(matrikula, eserleku, marka);
		// db.persist(k);
		// Driver g = db.find(Driver.class, gidari.getEmail());	
		// g.addKotxe(k);
		//

		// GEHITU
		if(db.find(Kotxea.class, matrikula)!=null) {
			throw new KotxeaAlreadyExistException();
		}
		String email = gidari.getEmail();
		Driver g = db.find(Driver.class, email);	
		Kotxea k = g.addKotxe(matrikula, eserleku, marka);
		//

		db.getTransaction().commit();
		close();
		return k;
	}


	public int getnPlaces(String matrikula) {
		open();
		Kotxea k = db.find(Kotxea.class, matrikula);
		close();
		return k.getnEserleku();
	}

	public List<Erreserba> getBidaiariarenErreserbak(Bidaiaria b) {
		open();

		// ZUZENDU
		// Bidaiaria b2 = db.find(Bidaiaria.class, b.getEmail());
		String email = b.getEmail();
		Bidaiaria b2 = db.find(Bidaiaria.class, email);
		//

		close();
		return b2.getErreserbak();
	}

	public List<Kotxea> kotxeakEskuratu(Driver d) {
		open();

		// ZUZENDU
		// Driver d2 = db.find(Driver.class, d.getEmail());	
		String email = d.getEmail();
		Driver d2 = db.find(Driver.class, email);	
		//

		close();
		return d2.getKotxeak();
	}

	// KENDU
	//	public boolean kotxeaExistitu(String matrikula) {
	//		open();
	//		if(db.find(Kotxea.class, matrikula)==null) {
	//			close();
	//			return false;
	//		} else {
	//			return true;
	//		}
	//	}

	public List<Mugimendua> getErabiltzailearenMugimenduak(User u) {
		open();

		// ZUZENDU
		// User u2 = db.find(User.class, u.getEmail());
		String email = u.getEmail();
		User u2 = db.find(User.class, email);
		//

		close();
		return u2.getMugimenduak();
	}

	public void bidaiKantzelatu(Driver gidari,int bidaiZenbaki) {
		open();
		db.getTransaction().begin();
		Ride r = db.find(Ride.class, bidaiZenbaki);
		r.setEgoera("kantzelatu");
		r.erreserbaGuztiakKantzelatu();
		db.getTransaction().commit();
		close();
	}

	public List<Ride> getRidesByDriver(Driver d) {
		open();

		// ZUZENDU
		// Driver d2 = db.find(Driver.class, d.getEmail());
		String email = d.getEmail();
		Driver d2 = db.find(Driver.class, email);
		//

		close();
		return d2.getBidaiakMartxan();
	}

	// GEHITU
	public List<Balorazioa> balorazioaErakutsi(User u) { 
		open();
		String email = u.getEmail();
		User u2 = db.find(User.class, email);
		close();
		return u2.getBalorazioak();
	}

	// GEHITU
	public void erabiltzaileaEzabatu(User u) { 
		open();
		db.getTransaction().begin();
		User u2 = db.merge(u); 
		db.remove(u2);
		db.getTransaction().commit();
		close();
	}

	// GEHITU
	public void balorazioaSortu(String nori, int puntuazioa, String deskripzioa, User nork) { 
		open();
		User u = db.find(User.class, nori);
		db.getTransaction().begin();
		u.addBalorazioa(puntuazioa, deskripzioa, nork);
		db.getTransaction().commit();
		close();
	}

	// GEHITU
	public void erreserbaBaloratuDa(Integer erreserbaZenbaki) {
		open();
		Erreserba e = db.find(Erreserba.class, erreserbaZenbaki);
		db.getTransaction().begin();
		e.setBaloratutaBidaiari(true);
		db.getTransaction().commit();
		close();
	}

	// GEHITU
	public void bidaiariaBaloratuDa(Integer bidaiZenbaki, String b) {
		open();
		db.getTransaction().begin();
		Ride r = db.find(Ride.class, bidaiZenbaki);
		Bidaiaria b2 = db.find(Bidaiaria.class, b);
		r.setBidaiariBaloratuta(b2);
		r.bidaiaBaloratuta();
		db.getTransaction().commit();
		close();
	}

	// GEHITU
	public void erreklamazioaSortu(int erreserbazbk, String deskripzioa, Bidaiaria b, String gidari) { 
		open();
		db.getTransaction().begin();
		Driver g = db.find(Driver.class, gidari);
		String email = b.getEmail();
		Bidaiaria b2 = db.find(Bidaiaria.class, email);
		Erreserba e = db.find(Erreserba.class, erreserbazbk);
		e.setEgoera("erreklamatuta");
		Erreklamazioa err = g.addErreklamazioa(e,deskripzioa,b2,g);
		b2.addErreklamazioa(err);
		db.getTransaction().commit();
		close();
	}

	// GEHITU
	public List<Ride> getBaloratuGabekoBidaiaAmaituak(Driver d) {
		open();
		String email = d.getEmail();
		Driver d2 = db.find(Driver.class, email);
		close();
		return d2.getBaloratuGabekoBidaiaAmaituak();
	}

	// GEHITU
	public List<Erreserba> getBidaiarenErreserbak(Ride r) {
		open();
		int bidaiZenbaki = r.getRideNumber();
		Ride r2 = db.find(Ride.class, bidaiZenbaki);
		close();
		return r2.getErreserbak();
	}

	// GEHITU
	public List<Erreserba> getBaloratuGabekoErreserbak(Bidaiaria b) {
		open();		
		String email = b.getEmail();
		Bidaiaria b2 = db.find(Bidaiaria.class, email);
		close();
		return b2.getBaloratuGabekoErreserbak();
	}

	// GEHITU
	public List<Alerta> alertaIkusi(Bidaiaria b){ 
		List<Alerta> alertak = new ArrayList<Alerta>();
		open();
		db.getTransaction().begin();
		String email = b.getEmail();
		Bidaiaria b2 = db.find(Bidaiaria.class, email);
		alertak = b2.getAlertak();
		db.getTransaction().commit();
		close();
		return alertak;
	}

	// GEHITU
	public void alertaSortu(String nondik, String nora, Date data, Bidaiaria b) throws AlertaAlreadyExistException {
		open();
		db.getTransaction().begin();
		String email = b.getEmail();
		Bidaiaria b2 = db.find(Bidaiaria.class, email);
		b2.addAlerta(nondik, nora, data, b2);
		db.getTransaction().commit();
		close();
	}

	// GEHITU
	public List<Erreklamazioa> erreklamazioaErakutsiBidaiari(Bidaiaria b) {
		open();
		String email = b.getEmail();
		Bidaiaria b2 = db.find(Bidaiaria.class, email);
		close();
		return b2.getBidalitakoErreklamazioak();
	}

	// GEHITU
	public List<Erreklamazioa> erreklamazioaErakutsiAdmin(Admin a) {
		open();
		String email = a.getEmail();
		Admin a2 = db.find(Admin.class, email);
		close();
		return a2.getJasotakoErreklamazioak();
	}

	// GEHITU
	public List<Erreklamazioa> erreklamazioaErakutsiGidari(Driver g) {
		open();
		String email = g.getEmail();
		Driver g2 = db.find(Driver.class, email);
		close();
		return g2.getJasotakoErreklamazioak();
	}

	// GEHITU
	public void addMezua(String testua, int errekzbk) {
		open();
		db.getTransaction().begin();
		Erreklamazioa e = db.find(Erreklamazioa.class, errekzbk);
		e.addMezua(testua);
		db.getTransaction().commit();
		close();
	}

	// GEHITU
	public Erreklamazioa erreklamazioaLortu(int errekzbk) { 
		open();
		Erreklamazioa e = db.find(Erreklamazioa.class, errekzbk);
		close();
		return e;
	}

	// GEHITU
	public void egoeraEzarriAdmin(int errekzbk, String egoera, String adminEmail) { 
		open();
		db.getTransaction().begin();
		Erreklamazioa e = db.find(Erreklamazioa.class, errekzbk);
		e.setEgoera(egoera);
		Admin a2 = db.find(Admin.class, adminEmail);
		if(egoera.equals("onartu")) {
			float diruIzoztua = e.getErreserbarenDiruIzoztua();
			Bidaiaria b = e.getNork(); 
			Driver d = e.getNori();
			b.diruaGehitu(diruIzoztua);
			b.addMugimendua("Dirua itzuli zaizu erreklamazioaren onarpenarengatik");
			d.diruaKendu(diruIzoztua);
			d.addMugimendua("Dirua kendu zaizu (administratzailea erabakita) erreklamazioaren onarpenarengatik");
		}
		a2.erreklamazioaKendu(e);
		db.getTransaction().commit();
		close();
	}

	// GEHITU
	public void egoeraEzarri(int errekzbk, String egoera) throws erreklamazioaEbatzitaException { 
		open();
		db.getTransaction().begin();
		Erreklamazioa e = db.find(Erreklamazioa.class, errekzbk);
		if(e.getEgoera().equals("itxaron")) {
			e.setEgoera(egoera);
			if(egoera.equals("onartu")) {
				float diruIzoztua = e.getErreserbarenDiruIzoztua();
				Bidaiaria b = e.getNork(); 
				Driver d = e.getNori();
				b.diruaGehitu(diruIzoztua);
				b.addMugimendua("Dirua itzuli zaizu erreklamazioaren onarpenarengatik");
				d.diruaKendu(diruIzoztua);
				d.addMugimendua("Dirua kendu zaizu erreklamazioaren onarpenarengatik");		
			}
			else if(egoera.equals("deuseztatu")) {
				TypedQuery<Admin> aquery = db.createQuery("SELECT a FROM Admin a", Admin.class);
				Admin a = aquery.getResultList().get(0);
				a.addErreklamazioa(e);
			}
		} else {
			throw new erreklamazioaEbatzitaException();
		}
		db.getTransaction().commit();
		close();
	}

	// GEHITU
	public void alertakEguneratu(Ride r) {
		open();
		TypedQuery<Alerta> query = db.createQuery("SELECT a FROM Alerta a", Alerta.class);
		db.getTransaction().begin();
		Integer bidaiZenbaki = r.getRideNumber();
		Ride r2 = db.find(Ride.class, bidaiZenbaki);
		String from = r2.getFrom();
		String to = r2.getTo();
		Date date = r2.getDate();

		List<Alerta> alertak = query.getResultList();
		for(Alerta a: alertak) {
			String nondik = a.getNondik();
			String nora = a.getNora();
			Date data = a.getDate();
			if(nondik.equals(from) && nora.equals(to) && egunBerdinakDira(data,date)) {
				a.setAbisatuta(true);
			}
		}
		db.getTransaction().commit();
		close();
	}

	// GEHITU
	private boolean egunBerdinakDira(Date d1, Date d2) {
		LocalDate ld1 = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate ld2 = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return ld1.equals(ld2);
	}

	// GEHITU
	public List<Mezua> mezuakLortu(int errekzbk) {	
		open();
		Erreklamazioa e = db.find(Erreklamazioa.class, errekzbk);
		close();
		return e.getMezuak();
	}
	
	// GEHITU 
	public Ride getRide(int bidaiZenbaki) {
		open();
		Ride r = db.find(Ride.class, bidaiZenbaki);
		close();
		return r;
	}

}
