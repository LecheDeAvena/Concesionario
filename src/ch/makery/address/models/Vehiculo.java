package ch.makery.address.models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.Id;

@Entity
@Table(name = "vehiculo")
public class Vehiculo implements Serializable {

	private int codVeh;
	private String marca;
	private String color;
	private Date fecLle;
	private int preVeh;
	private String matVeh;
	private String tipVeh;
	private String tipGas;
	private String modVeh;
	private Concesionario concesionario;
	private Cliente cliente;

	public Vehiculo() {

	}

	public Vehiculo(String marca, String color, Date fecLle, int preVeh, String matVeh, String tipVeh, String tipGas,
			String modVeh, Concesionario concesionario, Cliente cliente) {
		super();
		this.marca = marca;
		this.color = color;
		this.fecLle = fecLle;
		this.matVeh = matVeh;
		this.tipVeh = tipVeh;
		this.tipGas = tipGas;
		this.modVeh = modVeh;
		this.concesionario = concesionario;
		this.cliente = cliente;
	}

	public Vehiculo(String marca, String color, Date fecLle, int preVeh, String matVeh, String tipVeh, String tipGas,
			String modVeh) {
		super();
		this.marca = marca;
		this.color = color;
		this.fecLle = fecLle;
		this.preVeh = preVeh;
		this.matVeh = matVeh;
		this.tipVeh = tipVeh;
		this.tipGas = tipGas;
		this.modVeh = modVeh;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CodVeh")
	public int getCodVeh() {
		return codVeh;
	}

	public void setCodVeh(int codVeh) {
		this.codVeh = codVeh;
	}

	@Column(name = "Marca")
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public SimpleStringProperty marcaProperty() {
		return new SimpleStringProperty(this.marca);
	}

	@Column(name = "Color")
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public SimpleStringProperty colorProperty() {
		return new SimpleStringProperty(this.color);
	}

	@Column(name = "FecLle")
	public Date getFecLle() {
		return fecLle;
	}

	public void setFecLle(Date fecLle) {
		this.fecLle = fecLle;
	}

	public SimpleStringProperty fechProperty() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		return new SimpleStringProperty(dateFormat.format(this.fecLle));
	}

	@Column(name = "PreVeh")
	public int getPreVeh() {
		return preVeh;
	}

	public void setPreVeh(int preVeh) {
		this.preVeh = preVeh;
	}

	public SimpleStringProperty precioProperty() {
		return new SimpleStringProperty(String.valueOf(this.preVeh));
	}

	@Column(name = "MatVeh")
	public String getMatVeh() {
		return matVeh;
	}

	public void setMatVeh(String matVeh) {
		this.matVeh = matVeh;
	}

	public SimpleStringProperty matriculaProperty() {
		return new SimpleStringProperty(this.matVeh);
	}

	@Column(name = "TipVeh")
	public String getTipVeh() {
		return tipVeh;
	}

	public void setTipVeh(String tipVeh) {
		this.tipVeh = tipVeh;
	}

	@Column(name = "TipGas")
	public String getTipGas() {
		return tipGas;
	}

	public void setTipGas(String tipGas) {
		this.tipGas = tipGas;
	}

	public SimpleStringProperty gasProperty() {
		return new SimpleStringProperty(this.tipGas);
	}

	@Column(name = "ModVeh")
	public String getModVeh() {
		return modVeh;
	}

	public void setModVeh(String modVeh) {
		this.modVeh = modVeh;
	}

	public SimpleStringProperty modelProperty() {
		return new SimpleStringProperty(this.modVeh);
	}

	public SimpleStringProperty cocheNameProperty() {
		return new SimpleStringProperty(this.marca + " " + this.modVeh);
	}

	@ManyToOne
	@JoinColumn(name = "CodCon")
	public Concesionario getConcesionario() {
		return concesionario;
	}

	public void setConcesionario(Concesionario concesionario) {
		this.concesionario = concesionario;
	}

	@ManyToOne
	@JoinColumn(name = "CodCli")
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}