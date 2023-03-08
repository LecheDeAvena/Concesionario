package ch.makery.address.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.Id;

@Entity
@Table(name = "empleado")
public class Empleado {
  
  private int codEmp;
  private String nomEmp;
  private String apeEmp;
  private String gmaEmp;
  private String tlfEmp;
  private String dirEmp;
  private int salEmp;
  private Date fecIni;
  private String dniEmp;
  private String conEmp;
  private Concesionario concesionario;
	
  public Empleado(){
    
  }
  
  public Empleado(String nomEmp, String apeEmp, String gmaEmp, String tlfEmp, String dirEmp, int salEmp, Date fecIni, String dniEmp, String conEmp, Concesionario concesionario) {
    this.nomEmp = nomEmp;
    this.apeEmp = apeEmp;
    this.gmaEmp = gmaEmp;
    this.dirEmp = dirEmp;
    this.salEmp = salEmp;
    this.fecIni = fecIni;
    this.dniEmp = dniEmp;
    this.conEmp = conEmp;
    this.concesionario = concesionario;
  }
  
  public Empleado(String nomEmp, String apeEmp, String gmaEmp, String tlfEmp, String dirEmp, int salEmp, Date fecIni, String dniEmp, String conEmp) {
    this.nomEmp = nomEmp;
    this.apeEmp = apeEmp;
    this.gmaEmp = gmaEmp;
    this.gmaEmp = tlfEmp;
    this.dirEmp = dirEmp;
    this.salEmp = salEmp;
    this.fecIni = fecIni;
    this.dniEmp = dniEmp;
    this.conEmp = conEmp;
  }
  
	@Id
	@Column(name = "CodEmp")
	@GeneratedValue
	public int getCodEmp(){
    return codEmp;
  }
  
  public void setCodEmp(int codEmp){
    this.codEmp = codEmp;
  }
	
  @Column(name = "NomEmp")
	public String getNomEmp(){
    return nomEmp;
  }
  
  public void setNomEmp(String nomEmp){
    this.nomEmp = nomEmp;
  }
  
  public SimpleStringProperty nombreEmpProperty() {
	  return new SimpleStringProperty(this.nomEmp);
  }
	
  @Column(name = "ApeEmp")
	public String getApeEmp(){
    return apeEmp;
  }
  
  public void setApeEmp(String apeEmp){
    this.apeEmp = apeEmp;
  }
	
  public SimpleStringProperty apellidosEmpProperty() {
	  return new SimpleStringProperty(this.apeEmp);
  }
  
  public SimpleStringProperty nomAndApeEmpProperty() {
	  return new SimpleStringProperty(this.nomEmp+" "+this.apeEmp);
  }
  
  @Column(name = "GmaEmp")
  public String getGmaEmp(){
    return gmaEmp;
  }
  
  public void setGmaEmp(String gmaEmp){
    this.gmaEmp = gmaEmp;
  }
	
  @Column(name = "TlfEmp")
	public String getTlfEmp(){
    return tlfEmp;
  }
  
  public void setTlfEmp(String tlfEmp){
    this.tlfEmp = tlfEmp;
  }
	
  @Column(name = "DirEmp")
	public String getDirEmp(){
    return dirEmp;
  }
  
  public void setDirEmp(String dirEmp){
    this.dirEmp = dirEmp;
  }
	
  @Column(name = "SalEmp")
	public int getSalEmp(){
    return salEmp;
  }
  
  public void setSalEmp(int salEmp){
    this.salEmp = salEmp;
  }
	
  @Column(name = "FecIni")
	public Date getFecIni(){
    return fecIni;
  }
  
  public void setFecIni(Date fecIni){
    this.fecIni = fecIni;
  }
  
  @Column(name = "DniEmp")
	public String getDniEmp(){
    return dniEmp;
  }
  
  public void setDniEmp(String dniEmp){
    this.dniEmp = dniEmp;
  }
  
  @Column(name = "ConEmp")
	public String getConEmp(){
    return conEmp;
  }
  
  public void setConEmp(String conEmp){
    this.conEmp = conEmp;
  }
  
  	@ManyToOne()
	@JoinColumn(name="CodCon")
	public Concesionario getConcesionario() {
		return concesionario;
	}

	public void setConcesionario(Concesionario concesionario) {
		this.concesionario = concesionario;
	}
}