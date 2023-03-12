package ch.makery.address.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.Id;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

	private int codCli;
	private String nomCli;
	private String apeCli;
	private String gmaCli;
	private String tlfCli;
	private String dirCli;

	public Cliente() {

	}

	public Cliente(String nomCli, String apeCli, String gmaCli, String tlfCli, String dirCli) {
		super();
		this.nomCli = nomCli;
		this.apeCli = apeCli;
		this.gmaCli = gmaCli;
		this.gmaCli = tlfCli;
		this.gmaCli = dirCli;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CodCli")
	public int getCodCli() {
		return codCli;
	}

	public void setCodCli(int codCli) {
		this.codCli = codCli;
	}

	@Column(name = "NomCli")
	public String getNomCli() {
		return nomCli;
	}

	public void setNomCli(String nomCli) {
		this.nomCli = nomCli;
	}

	@Column(name = "ApeCli")
	public String getApeCli() {
		return apeCli;
	}

	public void setApeCli(String apeCli) {
		this.apeCli = apeCli;
	}

	public SimpleStringProperty nomAndApeCliProperty() {
		return new SimpleStringProperty(this.nomCli + " " + this.apeCli);
	}

	@Column(name = "GmaCli")
	public String getGmaCli() {
		return gmaCli;
	}

	public void setGmaCli(String gmaCli) {
		this.gmaCli = gmaCli;
	}

	public SimpleStringProperty mailProperty() {
		return new SimpleStringProperty(this.gmaCli);
	}

	@Column(name = "TlfCli")
	public String getTlfCli() {
		return tlfCli;
	}

	public void setTlfCli(String tlfCli) {
		this.tlfCli = tlfCli;
	}

	public SimpleStringProperty phoneProperty() {
		return new SimpleStringProperty(this.tlfCli);
	}

	@Column(name = "DirCli")
	public String getDirCli() {
		return dirCli;
	}

	public void setDirCli(String dirCli) {
		this.dirCli = dirCli;
	}

	public SimpleStringProperty dirProperty() {
		return new SimpleStringProperty(this.dirCli);
	}
}