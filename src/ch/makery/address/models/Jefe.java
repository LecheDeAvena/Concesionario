package ch.makery.address.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "jefe")
public class Jefe implements Serializable {

	private Empleado empleado;

	public Jefe() {

	}

	public Jefe(Empleado empleado) {
		super();
		this.empleado = empleado;
	}

	@Id
	@JoinColumn(name = "CodJef")
	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
}