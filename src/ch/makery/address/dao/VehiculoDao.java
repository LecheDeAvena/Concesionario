package ch.makery.address.dao;

import java.util.List;
import org.hibernate.Session;

import ch.makery.address.models.Vehiculo;

public class VehiculoDao {
	private Session session;

	public VehiculoDao(Session session) {
		super();
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	public List<Vehiculo> getAllConcesionarioYCliente() {
		return session.createQuery("FROM Vehiculo").list();
	}

	public Vehiculo getVehiculo(int id) {
		return session.get(Vehiculo.class, id);
	}

	public void insertVehiculo(Vehiculo Vehiculo) {
		session.save(Vehiculo);
	}

	public void updateVehiculo(Vehiculo Vehiculo) {
		session.update(Vehiculo);
	}

	@SuppressWarnings("unchecked")
	public void deleteVehiculo(Vehiculo Vehiculo) {
		((List<ch.makery.address.models.Vehiculo>) session).remove(Vehiculo);
	}
}