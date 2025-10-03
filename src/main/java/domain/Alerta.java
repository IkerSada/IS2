package domain;

import java.io.Serializable;
import java.util.Date;

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
public class Alerta implements Serializable {
	
	@XmlID
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer alertazbk;
	private String nondik;
	private String nora;
	private Date data;
	private boolean abisatuta;
	private Bidaiaria bidaiari;
	
	
	
	public Alerta() {
		abisatuta = false;
	}
	
	public Alerta(String nondik, String nora, Date data, Bidaiaria b) {
		abisatuta = false;
		this.nondik = nondik;
		this.nora = nora;
		this.data = data;
		bidaiari = b;
	}

	
	
	public Integer getAlertazbk() {
		return Alertazbk;
	}

	public void setAlertazbk(Integer alertazbk) {
		Alertazbk = alertazbk;
	}

	public String getNondik() {
		return nondik;
	}

	public void setNondik(String nondik) {
		this.nondik = nondik;
	}

	public String getNora() {
		return nora;
	}

	public void setNora(String nora) {
		this.nora = nora;
	}

	public Date getDate() {
		return data;
	}

	public void setDate(Date date) {
		this.data = date;
	}

	public boolean isAbisatuta() {
		return abisatuta;
	}

	public void setAbisatuta(boolean abisatuta) {
		this.abisatuta = abisatuta;
	}

	public Bidaiaria getBidaiari() {
		return bidaiari;
	}

	public void setBidaiari(Bidaiaria bidaiari) {
		this.bidaiari = bidaiari;
	}
	
}