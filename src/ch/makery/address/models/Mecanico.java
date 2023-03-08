package ch.makery.address.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "mecanico")
public class Mecanico implements Serializable{
  
  private Empleado empleado;
  private boolean jefMec;
	
  public Mecanico(){
    
  }
  
  public Mecanico(Empleado empleado, boolean jefMec) {
    this.empleado = empleado;
    this.jefMec = jefMec;
  }
  
	@Id
  @OneToOne()
	@JoinColumn(name="CodEmp")
	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
  
	@Column(name="JefMec")
	public boolean getJefMec() {
		return jefMec;
	}

	public void setJefMec(boolean jefMec) {
		this.jefMec = jefMec;
	}
}