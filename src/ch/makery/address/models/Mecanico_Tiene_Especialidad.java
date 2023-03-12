package ch.makery.address.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "mecanico_tiene_especialidad")
public class Mecanico_Tiene_Especialidad implements Serializable {

	private Empleado empleado;
	private Especialidad especialidad;

	public Mecanico_Tiene_Especialidad() {

	}

	public Mecanico_Tiene_Especialidad(Empleado empleado, Especialidad especialidad) {
		super();
		this.empleado = empleado;
		this.especialidad = especialidad;
	}

	@Id
	@ManyToOne
	@JoinColumn(name = "CodEmp")
	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	@Id
	@ManyToOne
	@JoinColumn(name = "CodEsp")
	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}
}