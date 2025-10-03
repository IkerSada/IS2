package domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

import exceptions.AlertaAlreadyExistException;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Bidaiaria extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Erreserba> erreserbak=new Vector<Erreserba>();

	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST) 
	private List<Alerta> alertak=new Vector<Alerta>();		// GEHITU

	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST) 
	private List<Erreklamazioa> bidalitakoErreklamazioak =new Vector<Erreklamazioa>();		// GEHITU



	public Bidaiaria() {		
		super();
	}

	public Bidaiaria(String email, String pasahitza, String izena) {
		super(email,pasahitza,izena);
	}

	public Bidaiaria(String izena, String abizena, Date jaiotzeData, String sexua, String email, String pasahitza) {
		super(izena, abizena, jaiotzeData, sexua, email, pasahitza);
	}



	public Erreserba addErreserba(Ride r, int eserlekuKop) {		
		Erreserba e=new Erreserba(eserlekuKop, r, this);	
		erreserbak.add(e);
		r.addErreserba(e);
		return e;
	}

	public Erreserba erreserbaBilatu(int bidaiZenbaki) {
		int i=0;
		while(i<erreserbak.size()) {
			if (erreserbak.get(i).getBidaia().getRideNumber() == bidaiZenbaki) {
				return erreserbak.get(i);
			}
			else 
				i++;
		}
		return null;
	}

	public String getEmail() {
		return super.getEmail();
	}

	public void setEmail(String email) {
		super.setEmail(email);
	}
	
	@Override
	public String getPasahitza() {
		return super.getPasahitza();
	}

	public void setPasahitza(String pasahitza) {
		super.setPasahitza(pasahitza);
	}

	public List<Erreserba> getErreserbak() {
		return erreserbak;
	}

	public void setErreserbak(List<Erreserba> erreserbak) {
		this.erreserbak = erreserbak;
	}

	// GEHITU
	public List<Erreserba> getBaloratuGabekoErreserbak() {
		List<Erreserba> erreserba = new Vector<Erreserba>();
		for (Erreserba e: erreserbak) {
			if(!e.isBaloratutaBidaiari()) {
				erreserba.add(e);
			}
		}
		return erreserba;
	} 

	// GEHITU
	public List<Alerta> getAlertak() {
		return alertak;
	}

	// GEHITU
	public void setAlertak(List<Alerta> alertak) {
		this.alertak = alertak;
	}

	// GEHITU
	public List<Erreklamazioa> getBidalitakoErreklamazioak() {
		return bidalitakoErreklamazioak;
	}

	// GEHITU
	public void setBidalitakoErreklamazioak(List<Erreklamazioa> bidalitakoErreklamazioak) {
		this.bidalitakoErreklamazioak = bidalitakoErreklamazioak;
	}

	// GEHITU
	public void addAlerta(String nondik, String nora, Date data, Bidaiaria b) throws AlertaAlreadyExistException { 
		for(Alerta a: alertak) {
			String from = a.getNondik();
			String to = a.getNora();
			Date date = a.getDate();
			if(from.equals(nondik) && to.equals(nora) && egunBerdinakDira(date,data)) {
				throw new AlertaAlreadyExistException();
			}
		}
		Alerta alerta = new Alerta(nondik,nora,data,b);
		alertak.add(alerta);
	}
	
	// GEHITU
	private boolean egunBerdinakDira(Date d1, Date d2) {
	    LocalDate ld1 = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    LocalDate ld2 = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    return ld1.equals(ld2);
	}

	// GEHITU
	public void addErreklamazioa(Erreklamazioa e) { 
		bidalitakoErreklamazioak.add(e);
	}

}