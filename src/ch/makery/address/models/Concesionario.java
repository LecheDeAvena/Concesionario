package ch.makery.address.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "concesionario")
public class Concesionario implements Serializable {

	private int codCon;
	private String nomCon;
	private String dirCon;

	public Concesionario() {

	}

	public Concesionario(String nomCon, String dirCon) {
		super();
		this.nomCon = nomCon;
		this.dirCon = dirCon;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CodCon")
	public int getCodCon() {
		return codCon;
	}

	public void setCodCon(int codCon) {
		this.codCon = codCon;
	}

	@Column(name = "NomCon")
	public String getNomCon() {
		return nomCon;
	}

	public void setNomCon(String nomCon) {
		this.nomCon = nomCon;
	}

	@Column(name = "DirCon")
	public String getDirCon() {
		return dirCon;
	}

	public void setDirCon(String dirCon) {
		this.dirCon = dirCon;
	}
}