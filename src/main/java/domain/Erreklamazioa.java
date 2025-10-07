package domain;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Erreklamazioa implements Serializable {

	@XmlID
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer erreklamazioZenbaki;
	private String egoera;
	private String deskripzioa;
	@ManyToOne
	private Bidaiaria nork;

	@ManyToOne  
	private Driver nori;

	@ManyToOne
	private Admin admin;

	@ManyToOne
	private Erreserba erreserba;

	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL) 
	private List<Mezua> mezuak = new Vector<Mezua>();

	
	
	public Erreklamazioa() {
		super();
	}

	public Erreklamazioa(Erreserba erres,String desk, Bidaiaria b, Driver g) {
		erreserba = erres;
		egoera = "itxaron";
		deskripzioa = desk;
		nork = b;
		nori = g;
	}



	public Erreserba getErreserba() {
		return erreserba;
	}

	public void setErreserba(Erreserba erreserba) {
		this.erreserba = erreserba;
	}

	public Integer getErreklamazioZenbaki() {
		return erreklamazioZenbaki;
	}

	public void setErreklamazioZenbaki(Integer erreklamazioZenbaki) {
		this.erreklamazioZenbaki = erreklamazioZenbaki;
	}

	public String getEgoera() {
		return egoera;
	}

	public void setEgoera(String egoera) {
		this.egoera = egoera;
	}

	public String getDeskripzioa() {
		return deskripzioa;
	}

	public void setDeskripzioa(String deskripzioa) {
		this.deskripzioa = deskripzioa;
	}

	public Bidaiaria getNork() {
		return nork;
	}

	public void setNork(Bidaiaria nork) {
		this.nork = nork;
	}

	public Driver getNori() {
		return nori;
	}

	public void setNori(Driver nori) {
		this.nori = nori;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
	public List<Mezua> getMezuak() {
		return mezuak;
	}

	public void setMezuak(List<Mezua> mezuak) { 
		this.mezuak = mezuak;
	}
	
	public void addMezua(String testua) { 
		Mezua m = new Mezua(testua,this);
		mezuak.add(m);
	}
	
	public String mezuakLortu() { 
		String mezu="Mezuak: ";
		for(Mezua m:mezuak) {
			mezu=mezu+m.getTestua();
		}
		return mezu;
	}
	
	public float getErreserbarenDiruIzoztua() {
		return erreserba.getDiruIzoztua();
	}
	
	

}