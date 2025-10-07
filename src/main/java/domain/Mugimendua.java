package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Mugimendua implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer mugimenduZenbaki;
	// private float dirua;			KENDU
	private String deskripzioa;
	
	@ManyToOne
	private User user;
	
	
	public Mugimendua() {
		super();
	}
	
	public Mugimendua(String deskripzioa) {		// ZUZENDU: dirua parametroa kendu
		// this.dirua = dirua;				KENDU
		this.deskripzioa = deskripzioa;
	}

	
	// KENDU
//	public float getDirua() {
//		return dirua;
//	}
//
//	public void setDirua(float dirua) {
//		this.dirua = dirua;
//	}
	//
	
	public String getDeskripzioa() {
		return deskripzioa;
	}

	public void setDeskripzioa(String deskripzioa) {
		this.deskripzioa = deskripzioa;
	}

	public Integer getMugimenduZenbaki() {
		return mugimenduZenbaki;
	}

	public void setMugimenduZenbaki(Integer mugimenduZenbaki) {
		this.mugimenduZenbaki = mugimenduZenbaki;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
