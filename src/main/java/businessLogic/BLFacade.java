package businessLogic;

import java.util.Date;
import java.util.List;

//import domain.Booking;
import domain.Ride;
import domain.User;
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
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.erreklamazioaEbatzitaException;
import exceptions.AlertaAlreadyExistException;
import exceptions.KotxeaAlreadyExistException;
import exceptions.RideAlreadyExistException;

import javax.jws.WebMethod;
import javax.jws.WebService;
 
/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	@WebMethod public List<String> getDepartCities();
	
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	@WebMethod public List<String> getDestinationCities(String from);


	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driver to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
 	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
   @WebMethod
   public Ride createRide( String from, String to, Date date, int nPlaces, float price, String driverEmail) throws RideMustBeLaterThanTodayException, RideAlreadyExistException;
	
	
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	@WebMethod public List<Ride> getRides(String from, String to, Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	@WebMethod public List<Date> getThisMonthDatesWithRides(String from, String to, Date date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();
	
	
	@WebMethod public boolean erregistratu(String izena, String abizena, Date jaiotzeData, String sexua, String erabiltzaileMota, String email, String pasahitza);

	@WebMethod public boolean saioaHasi(String email, String pasahitza);
	
	@WebMethod public boolean saioaHasiAdmin(String email, String pasahitza); // GEHITU
	
	@WebMethod public User erabiltzaileaBilatu(String email);
	
	@WebMethod public Admin erabiltzaileaBilatuAdmin(String email); // GEHITU
	
	@WebMethod public void diruaSartu(float dirua, Bidaiaria t);

	@WebMethod public void diruaAtera(float dirua, Driver d);
	
	@WebMethod public List<Erreserba> getReservedRidesByDriver(Driver d);
	
	@WebMethod public void erreserbaEgin(int bidaiZenbaki,Bidaiaria bidaiari, int eserlekuKop);
	
	@WebMethod public void erreserbaOnartu(int erreserbaZenbaki, Driver gidari);
	
	@WebMethod public void erreserbaDeuseztatu(int erreserbaZenbaki);
	
	@WebMethod public void erreserbaBaieztatu(int erreserbaZenbaki);	
	
	@WebMethod public Kotxea kotxeGehitu(String matrikula, int eserleku, String marka, Driver gidari) throws KotxeaAlreadyExistException;//ZUZENDU	
	
	@WebMethod public int getnPlaces(String matrikula);		
	
	@WebMethod public List<Erreserba> getBidaiariarenErreserbak(Bidaiaria b);		
	
	@WebMethod public List<Kotxea> kotxeakEskuratu(Driver d);		
	
	// @WebMethod public boolean kotxeaExistitu(String matrikula);		KENDU
	
	@WebMethod public List<Mugimendua> getErabiltzailearenMugimenduak(User u); 	
	
	@WebMethod public List<Ride> getRidesByDriver(Driver d); 	
	
	@WebMethod public void bidaiKantzelatu(Driver gidari,int bidaiZenbaki);  
	
	@WebMethod public List<Balorazioa> balorazioaErakutsi(User u); 		// GEHITU
	
	@WebMethod public void erabiltzaileaEzabatu(User u); 	// GEHITU
	
	@WebMethod public void balorazioaSortu(String nori, int puntuazioa, String deskripzioa, User nork); 	// GEHITU
	
	@WebMethod public void erreserbaBaloratuDa(Integer erreserbaZenbaki);		// GEHITU

	@WebMethod public void bidaiariaBaloratuDa(Integer bidaiZenbaki, String b);		// GEHITU
	
	@WebMethod public void erreklamazioaSortu(int erreserbazbk, String deskripzioa, Bidaiaria b, String gidari); 	// GEHITU
	
	@WebMethod public List<Ride> getBaloratuGabekoBidaiaAmaituak(Driver d);		// GEHITU
	
	@WebMethod public List<Erreserba> getBidaiarenErreserbak(Ride r);		// GEHITU
	
	@WebMethod public List<Erreserba> getBaloratuGabekoErreserbak(Bidaiaria b);		// GEHITU
	
	@WebMethod public List<Alerta> alertaIkusi(Bidaiaria b);	 //GEHITU
	
	@WebMethod public void alertaSortu(String nondik, String nora, Date data, Bidaiaria b) throws AlertaAlreadyExistException;	// GEHITU
	
	@WebMethod public List<Erreklamazioa> erreklamazioaErakutsiBidaiari(Bidaiaria b); 		//GEHITU
	
	@WebMethod public List<Erreklamazioa> erreklamazioaErakutsiAdmin(Admin a); 		//GEHITU
	
	@WebMethod public List<Erreklamazioa> erreklamazioaErakutsiGidari(Driver g); 	// GEHITU
	
	@WebMethod public void addMezua(String testua, int errekzbk); 		//GEHITU
	
	@WebMethod public Erreklamazioa erreklamazioaLortu(int errekzbk); 		//GEHITU
	
	@WebMethod public void egoeraEzarriAdmin(int errekzbk, String egoera, Admin a); 	//GEHITU
	
	@WebMethod public void egoeraEzarri(int errekzbk, String egoera) throws erreklamazioaEbatzitaException; 	// GEHITU
	
	@WebMethod public void alertakEguneratu(Ride r);		// GEHITU
	
	@WebMethod public List<Mezua> mezuakLortu(int errekzbk);		// GEHITU
	
	@WebMethod public Ride getRide(int bidaiZenbaki);		// GEHITU

}
