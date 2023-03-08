package ch.makery.address.dao;

import java.util.List;
import org.hibernate.Session;

import ch.makery.address.models.Ventas;

public class VentasDao {
	private Session session;
	
	public VentasDao(Session session) {
		super();
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	public List<Ventas> getAllEmpleado() {
		return session.createQuery("FROM Ventas").list();
	}
	
	public Ventas getVentas(int id) {
		return session.get(Ventas.class, id);
	}
	
	public void insertVentas(Ventas Ventas) {
		session.save(Ventas);
	}
	
	public void updateVentas(Ventas Ventas) {
		session.update(Ventas);
	}
	
	public void deleteVentas(Ventas Ventas) {
		((List<ch.makery.address.models.Ventas>) session).remove(Ventas);
	}
}