package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Driver extends User implements Serializable {

	private static final long serialVersionUID = 1L;
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Ride> rides=new Vector<Ride>();

	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST) 
	private List<Kotxea> kotxeak=new Vector<Kotxea>();		

	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)  
	private List<Erreklamazioa> jasotakoErreklamazioak=new Vector<Erreklamazioa>();		// GEHITU



	public Driver() {		
		super();
	}

	public Driver(String email, String name) {
		super(email,name);
	}

	public Driver(String email, String pasahitza, String izena) {
		super(email,pasahitza,izena);
	}

	public Driver(String izena, String abizena, Date jaiotzeData, String sexua, String email, String pasahitza) {
		super(izena, abizena, jaiotzeData, sexua, email, pasahitza);
	}



	public String getEmail() {
		return super.getEmail();
	}

	public void setEmail(String email) {
		super.setEmail(email);
	}

	public String getPasahitza() {
		return super.getPasahitza();
	}

	public void setPasahitza(String pasahitza) {
		super.setPasahitza(pasahitza);
	}

	public String getName() {
		return super.getIzena();
	}

	public List<Ride> getRides() {
		return rides;
	}

	public void setRides(List<Ride> rides) {		
		this.rides = rides;
	}

	public List<Kotxea> getKotxeak() { 	
		return kotxeak;
	}

	public void setKotxeak(List<Kotxea> kotxeak) { 
		this.kotxeak = kotxeak;
	}

	public String toString(){
		return super.getEmail()+";"+super.getIzena()+rides;
	}

	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual profit
	 * 
	 * @param question to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
	public Ride addRide(String from, String to, Date date, int nPlaces, float price)  {
		Ride ride=new Ride(from,to,date,nPlaces,price, this);
		rides.add(ride);
		return ride;
	}

	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location 
	 * @param to the destination location 
	 * @param date the date of the ride 
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesRideExists(String from, String to, Date date)  {	
		for (Ride r:rides)
			if ( (java.util.Objects.equals(r.getFrom(),from)) && (java.util.Objects.equals(r.getTo(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
				return true;

		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Driver other = (Driver) obj;
		if (super.getEmail() != other.getEmail())
			return false;
		return true;
	}

	public Ride removeRide(String from, String to, Date date) {
		boolean found=false;
		int index=0;
		Ride r=null;
		while (!found && index<rides.size()) {
			r=rides.get(index);
			if ( (java.util.Objects.equals(r.getFrom(),from)) && (java.util.Objects.equals(r.getTo(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
				found=true;
			index++;
		}

		if (found) {
			rides.remove(index-1);
			return r;
		} else return null;
	}

	// ZUZENDU
	public Kotxea addKotxe(String matrikula, int eserleku, String marka) {
		Kotxea k = new Kotxea(matrikula, eserleku, marka, this);
		kotxeak.add(k);
		return k;
	}

	public List<Ride> getBidaiakMartxan() {		
		List<Ride> ride = new Vector<Ride>();
		for (Ride r: rides) {
			if(r.getEgoera().equals("martxan")) {
				ride.add(r);
			}
		}
		return ride;
	}

	// GEHITU
	public List<Ride> getBaloratuGabekoBidaiaAmaituak() {		
		List<Ride> ride = new Vector<Ride>();
		for (Ride r: rides) {
			if(r.getEgoera().equals("amaituta") && !r.isBaloratuta()) {
				ride.add(r);
			}
		}
		return ride;
	}

	// GEHITU
	public List<Erreklamazioa> getJasotakoErreklamazioak() {
		return jasotakoErreklamazioak;
	}

	// GEHITU
	public void setJasotakoErreklamazioak(List<Erreklamazioa> jasotakoErreklamazioak) {
		this.jasotakoErreklamazioak = jasotakoErreklamazioak;
	}
	
	// GEHITU
	public Erreklamazioa addErreklamazioa(Erreserba e, String deskripzioa, Bidaiaria b, Driver g) { 
		Erreklamazioa err=new Erreklamazioa(e,deskripzioa,b,g);
		jasotakoErreklamazioak.add(err);
		return err;
	}

}
