package domain;

import java.io.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Erreserba implements Serializable {
	
    @XmlID
    @Id 
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    @GeneratedValue
    private Integer bookNumber;
    private int nPlaces;
    @ManyToOne
    private Ride bidaia;

    @ManyToOne
    private Bidaiaria traveler;

    @ManyToOne
    private Erreklamazioa erreklamazioa;
    private String egoera;		
    private float diruIzoztua;  
    private boolean baloratutaBidaiari; 		// GEHITU
    private boolean baloratutaGidari; 			// GEHITU
 		// GEHITU 

    
    
    public Erreserba(){
        super();
    }

    public Erreserba(int nPlaces,Ride bidaia, Bidaiaria traveler) {
        super();
        this.nPlaces = nPlaces;
        this.bidaia = bidaia;
        this.traveler = traveler;
        this.egoera = "itxaron";	
        baloratutaBidaiari = false;			// GEHITU
        baloratutaGidari = false;			// GEHITU
        erreklamazioa = null;
    }

    

	public String getEgoera() { 	
		return egoera;
	}
	
	public void setEgoera(String egoera) { 		
		this.egoera = egoera;
	}
	
	public float getDiruIzoztua() {	
		return diruIzoztua;
	}

	public void setDiruIzoztua(float diruIzoztua) {		
		this.diruIzoztua = diruIzoztua;
	}
	
	public Integer getBookNumber() {
		return bookNumber;
	}

	public void setBookNumber(Integer bookNumber) {
		this.bookNumber = bookNumber;
	}

	public int getnPlaces() {
		return nPlaces;
	}

	public void setnPlaces(int nPlaces) {
		this.nPlaces = nPlaces;
	}

	public Ride getBidaia() {
		return bidaia;
	}

	public void setBidaia(Ride bidaia) {
		this.bidaia = bidaia;
	}

	public Bidaiaria getTraveler() {
		return traveler;
	}

	public void setTraveler(Bidaiaria traveler) {
		this.traveler = traveler;
	}	
	
	// GEHITU
	public boolean isBaloratutaBidaiari() {
		return baloratutaBidaiari;
	}
	
	// GEHITU
	public void setBaloratutaBidaiari(boolean baloratutaBidaiari) {
		this.baloratutaBidaiari = baloratutaBidaiari;
	}

	// GEHITU
	public boolean isBaloratutaGidari() {
		return baloratutaGidari;
	}

	// GEHITU
	public void setBaloratutaGidari(boolean baloratutaGidari) {
		this.baloratutaGidari = baloratutaGidari;
	}

	// GEHITU
	public float diruaKalkulatu() {
		return traveler.getDirua()+diruIzoztua;
	}
	
	// GEHITU
	public void eguneratuDiruIzoztua(float prezioa) {
		diruIzoztua += prezioa;
	}
	
	// GEHITU
	public void updatePlaces(int eserlekuKop) {
		nPlaces += eserlekuKop;
	}
	
	// GEHITU
	public String getGidariarenEmaila() {
		return bidaia.getGidariarenEmaila();
	}
	
	// GEHITU
	public String getBidaiariarenEmaila() {
		return traveler.getEmail();
	}
	
	// GEHITU
	public Integer getBidaiZenbaki() {
		return bidaia.getRideNumber();
	}
	
	// GEHITU
	public Erreklamazioa getErreklamazioa() { 
		return erreklamazioa;
	}

	// GEHITU
	public void setErreklamazioa(Erreklamazioa erreklamazioa) { 
		this.erreklamazioa = erreklamazioa;
	}

}