package domain;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso ({Bidaiaria.class, Driver.class})		
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
	private static final long serialVersionUID = 1L;
	private String izena; 
	private String abizena;
	private Date jaiotzeData;
	private String sexua;
	@XmlID
	@Id
	private String email;
	private String pasahitza;
	private float dirua;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Mugimendua> mugimenduak=new Vector<Mugimendua>();	 
		
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Balorazioa> balorazioak=new Vector<Balorazioa>(); 	// GEHITU
	

		
	public User() {		
		super();
	}

	public User(String email, String izena) {
		this.email=email;
		this.izena=izena;
	}

	public User(String email, String pasahitza, String izena) {
		// ZUZENDU
		// this.email=email;
		// this.izena=izena;
		this(email,izena);
		//
		this.pasahitza=pasahitza;
	}
	
	public User(String izena, String abizena, Date jaiotzeData, String sexua, String email, String pasahitza) {
		// ZUZENDU
		// this.izena=izena;
		// this.email=email;
		// this.pasahitza=pasahitza;
		this(email,pasahitza,izena);
		//
		this.abizena=abizena;
		this.jaiotzeData=jaiotzeData;
		this.sexua=sexua;
	}

	
	
	public String getIzena() {
		return izena;
	}

	public void setIzena(String izena) {
		this.izena = izena;
	}

	public String getAbizena() {
		return abizena;
	}

	public void setAbizena(String abizena) {
		this.abizena = abizena;
	}

	public Date getJaiotzeData() {
		return jaiotzeData;
	}

	public void setJaiotzeData(Date jaiotzeData) {
		this.jaiotzeData = jaiotzeData;
	}

	public String getSexua() {
		return sexua;
	}

	public void setSexua(String sexua) {
		this.sexua = sexua;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasahitza() {
		return pasahitza;
	}

	public void setPasahitza(String pasahitza) {
		this.pasahitza = pasahitza;
	}

	public float getDirua() {
		return dirua;
	}

	public void setDirua(float dirua) {
		this.dirua = dirua;
	}
	
	public List<Mugimendua> getMugimenduak() { 
		return mugimenduak;
	}

	public void setMugimenduak(List<Mugimendua> mugimenduak) { 
		this.mugimenduak = mugimenduak;
	}
	
	public boolean erabiltzaileBerdina(String email, String pasahitza) {
		return this.email.equals(email) && this.pasahitza.equals(pasahitza);
	}
	
	// ZUZENDU
	public void addMugimendua(String s) {
		Mugimendua m=new Mugimendua(s);				
		mugimenduak.add(m);
	}
	
	// GEHITU
	public void diruaGehitu(float d) {
		dirua += d;
	}
	
	// GEHITU
	public void diruaKendu(float d) {
		dirua -= d;
	}
		
	// GEHITU
	public List<Balorazioa> getBalorazioak() { 
		return balorazioak;
	}
	
	// GEHITU
	public void setBalorazioak(List<Balorazioa> balorazioak) { 
		this.balorazioak = balorazioak;
	}
	
	// GEHITU
	public void addBalorazioa(int puntuazioa, String deskripzioa, User nork) {
		Balorazioa b = new Balorazioa(puntuazioa, deskripzioa, nork);
		balorazioak.add(b);
	}
	
}
