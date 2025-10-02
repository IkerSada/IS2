package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Kotxea implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlID
	@Id 
	private String matrikula;
	private int nEserleku;
	private String marka;
	private Driver gidari;
	
	
	
	public Kotxea() {
		super();
	}

	// ZUZENDU
	public Kotxea(String matrikula, int eserleku, String marka, Driver gidari) {
		this.matrikula = matrikula;
		this.nEserleku = eserleku;
		this.marka = marka;
		this.gidari = gidari;
	}
	
	

	public String getMatrikula() {
		return matrikula;
	}

	public void setMatrikula(String matrikula) {
		this.matrikula = matrikula;
	}

	public int getnEserleku() {
		return nEserleku;
	}

	public void setnEserleku(int nEserleku) {
		this.nEserleku = nEserleku;
	}

	public String getMarka() {
		return marka;
	}

	public void setMarka(String marka) {
		this.marka = marka;
	}
	
	public Driver getGidari() {
		return gidari;
	}

	public void setGidari(Driver gidari) {
		this.gidari = gidari;
	}
	
}