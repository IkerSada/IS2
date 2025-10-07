package domain;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Ride implements Serializable {
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer rideNumber;
	
	// CAMBIO: Usar nombres que no sean palabras reservadas de SQL
	@Column(name = "from_location")
	private String from;
	
	@Column(name = "to_location")  
	private String to;
	
	private int nPlaces;
	private Date date;
	private float price;
	
	@ManyToOne
	private Driver driver;
	private String egoera;	
	private boolean baloratuta; 		// GEHITU

	// CAMBIO: Eliminar @XmlIDREF y cambiar cascade
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Erreserba> erreserbak=new Vector<Erreserba>();



	public Ride(){
		super();
	}

	public Ride(Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver) {
		super();
		this.rideNumber = rideNumber;
		this.from = from;
		this.to = to;
		this.nPlaces = nPlaces;
		this.date=date;
		this.price=price;
		this.driver = driver;
		this.egoera = "martxan";	
		baloratuta = false;			// GEHITU
	}

	public Ride(String from, String to,  Date date, int nPlaces, float price, Driver driver) {
		super();
		this.from = from;
		this.to = to;
		this.nPlaces = nPlaces;
		this.date=date;
		this.price=price;
		this.driver = driver;
		this.egoera = "martxan";
		baloratuta = false;			// GEHITU
	}



	/**
	 * Get the  number of the ride
	 * 
	 * @return the ride number
	 */
	public Integer getRideNumber() {
		return rideNumber;
	}

	/**
	 * Set the ride number to a ride
	 * 
	 * @param ride Number to be set	 */

	public void setRideNumber(Integer rideNumber) {
		this.rideNumber = rideNumber;
	}

	/**
	 * Get the origin  of the ride
	 * 
	 * @return the origin location
	 */

	public String getFrom() {
		return from;
	}

	/**
	 * Set the origin of the ride
	 * 
	 * @param origin to be set
	 */	

	public void setFrom(String origin) {
		this.from = origin;
	}

	/**
	 * Get the destination  of the ride
	 * 
	 * @return the destination location
	 */

	public String getTo() {
		return to;
	}

	/**
	 * Set the origin of the ride
	 * 
	 * @param destination to be set
	 */	
	public void setTo(String destination) {
		this.to = destination;
	}

	/**
	 * Get the free places of the ride
	 * 
	 * @return the available places
	 */

	/**
	 * Get the date  of the ride
	 * 
	 * @return the ride date 
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * Set the date of the ride
	 * 
	 * @param date to be set
	 */	
	public void setDate(Date date) {
		this.date = date;
	}

	public int getnPlaces() {
		return nPlaces;
	}

	/**
	 * Set the free places of the ride
	 * 
	 * @param  nPlaces places to be set
	 */

	public void setBetMinimum(int nPlaces) {
		this.nPlaces = nPlaces;
	}

	/**
	 * Get the driver associated to the ride
	 * 
	 * @return the associated driver
	 */
	public Driver getDriver() {
		return driver;
	}

	/**
	 * Set the driver associated to the ride
	 * 
	 * @param driver to associate to the ride
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setnPlaces(int nPlaces) {
		this.nPlaces = nPlaces;
	}

	public List<Erreserba> getErreserbak() {
		return erreserbak;
	}

	public void setErreserbak(List<Erreserba> erreserbak) {
		this.erreserbak = erreserbak;
	}

	public String toString(){
		return rideNumber+";"+";"+from+";"+to+";"+date;  
	}

	public void addErreserba(Erreserba e) {
		erreserbak.add(e);
	}

	public void updateSeat(int eserlekuKop) {
		nPlaces=nPlaces-eserlekuKop;
	}

	public void removeErreserba(Erreserba e) {		
		erreserbak.remove(e);
	}

	public void erreserbaGuztiakKantzelatu() {		
		for (Erreserba e: erreserbak) {
			if(e.getEgoera().equals("onartu")) {
				e.setEgoera("kantzelatu");
			} else {
				e.setEgoera("deuseztatu");
			}
			
			// ZUZENDU
			//Mugimendua m=new Mugimendua("Bidaia kantzelatu da eta dirua itzuli zaizu");		
			Bidaiaria b = e.getTraveler();
			b.addMugimendua("Bidaia kantzelatu da eta dirua itzuli zaizu");
			Float dirua = e.diruaKalkulatu();
			b.setDirua(dirua);
			//
			
		}
	}

	public List<Erreserba> getErreserbakItxaron() {	
		List<Erreserba> erre = new Vector<Erreserba>();
		for (Erreserba e: erreserbak) {
			if(e.getEgoera().equals("itxaron")) {
				erre.add(e);
			}
		}
		return erre;
	}

	public String getEgoera() {		
		return egoera;
	}

	public void setEgoera(String egoera) {			
		this.egoera = egoera;
	}

	public void bidaiaAmaitua() {		
		int i = 0;
		boolean denak = true;
		while (i<erreserbak.size() && denak) {
			if(!erreserbak.get(i).getEgoera().equals("amaituta")) {
				denak = false;
			}
			i++;
		}
		if(denak) {
			this.setEgoera("amaituta");
		}
	}

	// GEHITU
	public float getBidaiarenPrezioa(int eserlekuKop) {
		return price*eserlekuKop;
	}

	// GEHITU
	public void updateSeatPlus(int eserlekuKop) {
		nPlaces=nPlaces+eserlekuKop;
	}

	// GEHITU
	public String getGidariarenEmaila() {
		return driver.getEmail();
	}

	// GEHITU
	public boolean isBaloratuta() {
		return baloratuta;
	}

	// GEHITU
	public void setBaloratuta(boolean baloratuta) {
		this.baloratuta = baloratuta;
	}

	// GEHITU
	public void setBidaiariBaloratuta(Bidaiaria b) {	
		int i=0;
		boolean aurkitu=false;
		List<Erreserba> erreserbak = b.getErreserbak();
		while(i<erreserbak.size() && !aurkitu) {
			if(erreserbak.get(i).getBidaiZenbaki().equals(this.rideNumber)) {
				erreserbak.get(i).setBaloratutaGidari(true);
				aurkitu=true;
			}
			i++;
		}
	}

	// GEHITU
	public void bidaiaBaloratuta() {		
		int i = 0;
		boolean denak = true;
		while (i<erreserbak.size() && denak) {
			if(!erreserbak.get(i).isBaloratutaGidari()) {
				denak = false;
			}
			i++;
		}
		this.setBaloratuta(denak);
	}

}