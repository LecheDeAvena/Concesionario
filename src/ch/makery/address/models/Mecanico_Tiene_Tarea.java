package ch.makery.address.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "mecanico_tiene_tarea")
public class Mecanico_Tiene_Tarea implements Serializable{
  
  private Empleado empleado;
  private Tarea tarea;
	
  public Mecanico_Tiene_Tarea(){
    
  }
  
  public Mecanico_Tiene_Tarea(Empleado empleado, Tarea tarea) {
    super();
    this.empleado = empleado;
    this.tarea = tarea;
  }
  
	@Id
  @ManyToOne
	@JoinColumn(name="CodEmp")
	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
  
  @Id
  @ManyToOne
	@JoinColumn(name="CodTar")
	public Tarea getTarea() {
		return tarea;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}
}