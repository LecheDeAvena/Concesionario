package ch.makery.address.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "especialidad")
public class Especialidad implements Serializable {

	private int codEsp;
	private String nomEsp;

	public Especialidad() {

	}

	public Especialidad(String nomEsp) {
		super();
		this.nomEsp = nomEsp;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CodEsp")
	public int getCodEsp() {
		return codEsp;
	}

	public void setCodEsp(int codEsp) {
		this.codEsp = codEsp;
	}

	@Column(name = "NomEsp")
	public String getNomEsp() {
		return nomEsp;
	}

	public void setNomEsp(String nomEsp) {
		this.nomEsp = nomEsp;
	}
}