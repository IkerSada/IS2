package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Mezua implements Serializable {
	@XmlID
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer mezuzbk;
	private String testua;
	private Erreklamazioa erreklamazioa;		
	
	
	
	public Mezua() {
		super();
	}
	
	public Mezua(String testua,Erreklamazioa e) {
		this.testua = testua;
		erreklamazioa = e;
	}

	
	
	public int getMezuzbk() {
		return mezuzbk;
	}

	public void setMezuzbk(int mezuzbk) {
		this.mezuzbk = mezuzbk;
	}

	public String getTestua() {
		return testua;
	}

	public void setTestua(String testua) {
		this.testua = testua;
	}

	public Erreklamazioa getErreklamazioa() {
		return erreklamazioa;
	}

	public void setErreklamazioa(Erreklamazioa erreklamazioa) {
		this.erreklamazioa = erreklamazioa;
	}

}