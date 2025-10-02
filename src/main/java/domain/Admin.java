package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Admin {

	@XmlID
	@Id
	private String email;
	private String pasahitza;

	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Erreklamazioa> jasotakoErreklamazioak = new ArrayList<Erreklamazioa>();



	public Admin() {
		super();
	}

	public Admin(String email, String pasahitza) {
		this.email=email;
		this.pasahitza=pasahitza;
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

	public void addErreklamazioa(Erreklamazioa e) {
		jasotakoErreklamazioak.add(e);
	}
	
	public List<Erreklamazioa> getJasotakoErreklamazioak() {
		return jasotakoErreklamazioak;
	}

	public void setJasotakoErreklamazioak(List<Erreklamazioa> jasotakoErreklamazioak) {
		this.jasotakoErreklamazioak = jasotakoErreklamazioak;
	}
	
	public boolean erabiltzaileBerdina(String email, String pasahitza) { 
		return this.email.equals(email) && this.pasahitza.equals(pasahitza);
	}
	
	public void erreklamazioaKendu(Erreklamazioa err) {
		jasotakoErreklamazioak.remove(err);
	}

}