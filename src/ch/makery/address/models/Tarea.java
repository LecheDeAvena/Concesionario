package ch.makery.address.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import javafx.beans.property.SimpleStringProperty;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Table(name = "tarea")
public class Tarea implements Serializable {

	private int codTar;
	private String nomTar;
	private String desTar;
	private Date fecIni;
	private Date fecFin;
	private int cosTar;
	private String matTar;
	private Vehiculo vehiculo;

	public Tarea() {

	}

	public Tarea(String nomTar, String desTar, Date fecIni, Date fecFin, int cosTar, String matTar, Vehiculo vehiculo) {
		super();
		this.nomTar = nomTar;
		this.desTar = desTar;
		this.fecIni = fecIni;
		this.fecFin = fecFin;
		this.cosTar = cosTar;
		this.matTar = matTar;
		this.vehiculo = vehiculo;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CodTar")
	public int getCodTar() {
		return codTar;
	}

	public void setCodTar(int codTar) {
		this.codTar = codTar;
	}

	@Column(name = "NomTar")
	public String getNomTar() {
		return nomTar;
	}

	public void setNomTar(String nomTar) {
		this.nomTar = nomTar;
	}

	public SimpleStringProperty nomTarProperty() {
		return new SimpleStringProperty(this.nomTar);
	}

	@Column(name = "DesTar")
	public String getDesTar() {
		return desTar;
	}

	public void setDesTar(String desTar) {
		this.desTar = (desTar);
	}

	public SimpleStringProperty desTarProperty() {
		return new SimpleStringProperty(this.desTar);
	}

	@Column(name = "FecIni")
	public Date getFecIni() {
		return fecIni;
	}

	public void setFecIni(Date fecIni) {
		this.fecIni = fecIni;
	}

	public SimpleStringProperty fechIniProperty() {
		return new SimpleStringProperty(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(this.fecIni));
	}

	@Column(name = "FecFin")
	public Date getFecFin() {
		return fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}
	
	public SimpleStringProperty fechFinProperty() {
		if(this.fecFin!=null)
			return new SimpleStringProperty(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(this.fecFin));
		else
			return new SimpleStringProperty("No finalizada");
	}

	@Column(name = "CosTar")
	public int getCosTar() {
		return cosTar;
	}

	public void setCosTar(int cosTar) {
		this.cosTar = (cosTar);
	}

	public SimpleStringProperty cosTarProperty() {
		return new SimpleStringProperty(Integer.toString(this.cosTar) + " €");
	}

	@Column(name = "MatTar")
	public String getMatTar() {
		return matTar;
	}

	public void setMatTar(String matTar) {
		this.matTar = (matTar);
	}

	public SimpleStringProperty matTarProperty() {
		return new SimpleStringProperty(this.matTar);
	}

	@OneToOne
	@JoinColumn(name = "CodVeh")
	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}
}