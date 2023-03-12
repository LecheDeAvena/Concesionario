package ch.makery.address.dao;

import java.util.List;
import org.hibernate.Session;

import ch.makery.address.models.Ventas_Vende_Vehiculo;

public class Ventas_Vende_VehiculoDao {
	private Session session;

	public Ventas_Vende_VehiculoDao(Session session) {
		super();
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	public List<Ventas_Vende_Vehiculo> getAllVentasClienteYVehiculo() {
		return session.createQuery("FROM Ventas_Vende_Vehiculo").list();
	}

	public Ventas_Vende_Vehiculo getVentas_Vende_Vehiculo(int id) {
		return session.get(Ventas_Vende_Vehiculo.class, id);
	}

	public void insertVentas_Vende_Vehiculo(Ventas_Vende_Vehiculo Ventas_Vende_Vehiculo) {
		session.save(Ventas_Vende_Vehiculo);
	}

	public void updateVentas_Vende_Vehiculo(Ventas_Vende_Vehiculo Ventas_Vende_Vehiculo) {
		session.update(Ventas_Vende_Vehiculo);
	}

	@SuppressWarnings("unchecked")
	public void deleteVentas_Vende_Vehiculo(Ventas_Vende_Vehiculo Ventas_Vende_Vehiculo) {
		((List<ch.makery.address.models.Ventas_Vende_Vehiculo>) session).remove(Ventas_Vende_Vehiculo);
	}
}