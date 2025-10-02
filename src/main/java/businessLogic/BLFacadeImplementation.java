package businessLogic;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
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

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");


		dbManager=new DataAccess();

		//dbManager.close();


	}

	public BLFacadeImplementation(DataAccess da)  {

		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();

		dbManager=da;		
	}


	/**
	 * {@inheritDoc}
	 */
	@WebMethod public List<String> getDepartCities(){
		dbManager.open();	

		List<String> departLocations=dbManager.getDepartCities();		

		dbManager.close();

		return departLocations;

	}
	/**
	 * {@inheritDoc}
	 */
	@WebMethod public List<String> getDestinationCities(String from){
		dbManager.open();	

		List<String> targetCities=dbManager.getArrivalCities(from);		

		dbManager.close();

		return targetCities;
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public Ride createRide( String from, String to, Date date, int nPlaces, float price, String driverEmail ) throws RideMustBeLaterThanTodayException, RideAlreadyExistException{

		dbManager.open();
		Ride ride=dbManager.createRide(from, to, date, nPlaces, price, driverEmail);		
		dbManager.close();
		return ride;
	};

	/**
	 * {@inheritDoc}
	 */
	@WebMethod 
	public List<Ride> getRides(String from, String to, Date date){
		dbManager.open();
		List<Ride>  rides=dbManager.getRides(from, to, date);
		dbManager.close();
		return rides;
	}


	/**
	 * {@inheritDoc}
	 */
	@WebMethod 
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date){
		dbManager.open();
		List<Date>  dates=dbManager.getThisMonthDatesWithRides(from, to, date);
		dbManager.close();
		return dates;
	}


	public void close() {
		DataAccess dB4oManager=new DataAccess();

		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod	
	public void initializeBD(){
		dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}


	@WebMethod 
	public boolean erregistratu(String izena, String abizena, Date jaiotzeData, String sexua, String erabiltzaileMota, String email, String pasahitza) {
		return dbManager.erregistratu(izena, abizena, jaiotzeData, sexua, erabiltzaileMota, email, pasahitza);
	}

	@WebMethod 
	public boolean saioaHasi(String email, String pasahitza) {
		return dbManager.saioaHasi(email, pasahitza);
	}

	@WebMethod 
	public boolean saioaHasiAdmin(String email, String pasahitza) {  // GEHITU
		return dbManager.saioaHasiAdmin(email, pasahitza);
	}

	@WebMethod 
	public User erabiltzaileaBilatu(String email) {
		return dbManager.erabiltzaileaBilatu(email);
	}
	
	@WebMethod 
	public Admin erabiltzaileaBilatuAdmin(String email) {  // GEHITU
		return dbManager.erabiltzaileaBilatuAdmin(email);
	}

	@WebMethod 
	public void diruaSartu(float dirua, Bidaiaria t) {
		dbManager.diruaSartu(dirua,t);
	}

	@WebMethod 
	public void diruaAtera(float dirua, Driver d) {
		dbManager.diruaAtera(dirua,d);
	}

	@WebMethod
	public List<Erreserba> getReservedRidesByDriver(Driver d) {
		return dbManager.getReservedRidesByDriver(d);
	}

	@WebMethod
	public void erreserbaEgin(int bidaiZenbaki, Bidaiaria bidaiari, int eserlekuKop) {
		dbManager.erreserbaEgin(bidaiZenbaki,bidaiari,eserlekuKop);
	}

	@WebMethod
	public void erreserbaOnartu(int erreserbaZenbaki, Driver gidari) {
		dbManager.erreserbaOnartu(erreserbaZenbaki,gidari);
	}

	@WebMethod
	public void erreserbaDeuseztatu(int erreserbaZenbaki) {
		dbManager.erreserbaDeuseztatu(erreserbaZenbaki);
	}

	@WebMethod
	public void erreserbaBaieztatu(int erreserbaZenbaki) {
		dbManager.erreserbaBaieztatu(erreserbaZenbaki);
	}

	// ZUZENDU
	@WebMethod
	public Kotxea kotxeGehitu(String matrikula, int eserleku, String marka, Driver gidari) throws KotxeaAlreadyExistException {
		return dbManager.kotxeGehitu(matrikula,eserleku,marka,gidari);
	}
	
	@WebMethod
	public int getnPlaces(String matrikula) {
		return dbManager.getnPlaces(matrikula);
	}

	@WebMethod
	public List<Erreserba> getBidaiariarenErreserbak(Bidaiaria b) {
		return dbManager.getBidaiariarenErreserbak(b);
	}

	@WebMethod
	public List<Kotxea> kotxeakEskuratu(Driver d) {
		return dbManager.kotxeakEskuratu(d);
	}

	// KENDU
	//	@WebMethod
	//	public boolean kotxeaExistitu(String matrikula) {
	//		return dbManager.kotxeaExistitu(matrikula);
	//	}

	@WebMethod
	public List<Mugimendua> getErabiltzailearenMugimenduak(User u) {
		return dbManager.getErabiltzailearenMugimenduak(u);
	}

	@WebMethod
	public List<Ride> getRidesByDriver(Driver d) {
		return dbManager.getRidesByDriver(d);
	}

	@WebMethod
	public void bidaiKantzelatu(Driver gidari,int bidaiZenbaki) {
		dbManager.bidaiKantzelatu(gidari,bidaiZenbaki);
	}

	// GEHITU
	@WebMethod
	public List<Balorazioa> balorazioaErakutsi(User u) { 
		return dbManager.balorazioaErakutsi(u);
	}

	// GEHITU
	@WebMethod 
	public void erabiltzaileaEzabatu(User u) { 
		dbManager.erabiltzaileaEzabatu(u);
	}

	// GEHITU
	@WebMethod  
	public void balorazioaSortu(String nori, int puntuazioa, String deskripzioa, User nork) {
		dbManager.balorazioaSortu(nori,puntuazioa,deskripzioa,nork);

	}

	// GEHITU
	@WebMethod
	public void erreserbaBaloratuDa(Integer erreserbaZenbaki) {
		dbManager.erreserbaBaloratuDa(erreserbaZenbaki);
	}

	// GEHITU
	@WebMethod
	public void bidaiariaBaloratuDa(Integer bidaiZenbaki, String b) {
		dbManager.bidaiariaBaloratuDa(bidaiZenbaki, b);
	}

	// GEHITU
	@WebMethod
	public void erreklamazioaSortu(int erreserbazbk, String deskripzioa, Bidaiaria b, String gidari) { 
		dbManager.erreklamazioaSortu(erreserbazbk, deskripzioa, b, gidari);
	}

	// GEHITU
	@WebMethod public List<Ride> getBaloratuGabekoBidaiaAmaituak(Driver d) {
		return dbManager.getBaloratuGabekoBidaiaAmaituak(d);
	}

	// GEHITU
	@WebMethod public List<Erreserba> getBidaiarenErreserbak(Ride r) {
		return dbManager.getBidaiarenErreserbak(r);
	}

	// GEHITU
	@WebMethod
	public List<Erreserba> getBaloratuGabekoErreserbak(Bidaiaria b) {
		return dbManager.getBaloratuGabekoErreserbak(b);
	}

	// GEHITU
	@WebMethod
	public List<Alerta> alertaIkusi(Bidaiaria b) { 
		return dbManager.alertaIkusi(b);
	}

	// GEHITU
	@WebMethod
	public void alertaSortu(String nondik, String nora, Date data, Bidaiaria b) throws AlertaAlreadyExistException { 
		dbManager.alertaSortu(nondik, nora, data, b);
	}

	// GEHITU
	@WebMethod
	public List<Erreklamazioa> erreklamazioaErakutsiBidaiari(Bidaiaria b) {
		return dbManager.erreklamazioaErakutsiBidaiari(b);
	}

	// GEHITU
	@WebMethod
	public List<Erreklamazioa> erreklamazioaErakutsiAdmin(Admin a) {
		return dbManager.erreklamazioaErakutsiAdmin(a);
	}


	// GEHITU
	@WebMethod
	public List<Erreklamazioa> erreklamazioaErakutsiGidari(Driver g) {
		return dbManager.erreklamazioaErakutsiGidari(g);
	}


	// GEHITU
	@WebMethod
	public void addMezua(String testua, int errekzbk) {
		dbManager.addMezua(testua,errekzbk);
	}

	// GEHITU
	@WebMethod
	public Erreklamazioa erreklamazioaLortu(int errekzbk) { 
		return dbManager.erreklamazioaLortu(errekzbk);
	}

	// GEHITU
	@WebMethod
	public void egoeraEzarriAdmin(int errekzbk, String egoera, Admin a) { 
		dbManager.egoeraEzarriAdmin(errekzbk,egoera,a.getEmail());
	}

	// GEHITU
	@WebMethod
	public void egoeraEzarri(int errekzbk, String egoera)throws erreklamazioaEbatzitaException {
		dbManager.egoeraEzarri(errekzbk,egoera);
	}

	// GEHITU
	@WebMethod
	public void alertakEguneratu(Ride r) {
		dbManager.alertakEguneratu(r);
	}
	
	// GEHITU
	@WebMethod
	public List<Mezua> mezuakLortu(int errekzbk) {		
		return dbManager.mezuakLortu(errekzbk);
	}

	// GEHITU
	@WebMethod 
	public Ride getRide(int bidaiZenbaki) {
		return dbManager.getRide(bidaiZenbaki);
	}

}