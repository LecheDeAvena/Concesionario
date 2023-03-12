package ch.makery.address.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "ventas_vende_vehiculo")
public class Ventas_Vende_Vehiculo implements Serializable {

	private Empleado empleado;
	private Vehiculo vehiculo;
	private Cliente cliente;
	private Date fecVen;
	private boolean venta;

	public Ventas_Vende_Vehiculo() {

	}

	public Ventas_Vende_Vehiculo(Empleado empleado, Vehiculo vehiculo, Date fecVen, Cliente cliente, boolean venta) {
		super();
		this.empleado = empleado;
		this.vehiculo = vehiculo;
		this.cliente = cliente;
		this.fecVen = fecVen;
		this.venta = venta;
	}

	public Ventas_Vende_Vehiculo(Date fecVen, boolean venta) {
		super();
		this.fecVen = fecVen;
		this.venta = venta;
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
	@JoinColumn(name = "CodVeh")
	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	@ManyToOne
	@JoinColumn(name = "CodCli")
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Column(name = "FecVen")
	public Date getFecVen() {
		return fecVen;
	}

	public void setFecVen(Date fecVen) {
		this.fecVen = fecVen;
	}

	@Column(name = "Venta")
	public boolean getVenta() {
		return venta;
	}

	public void setVenta(boolean venta) {
		this.venta = venta;
	}
}