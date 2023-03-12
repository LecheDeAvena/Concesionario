package ch.makery.address.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "ventas")
public class Ventas implements Serializable {

	private Empleado empleado;

	public Ventas() {

	}

	public Ventas(Empleado empleado) {
		super();
		this.empleado = empleado;
	}

	@Id
	@OneToOne
	@JoinColumn(name = "CodEmp")
	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
}