package ch.makery.address.dao;

import java.util.List;
import org.hibernate.Session;

import ch.makery.address.models.Empleado;

public class EmpleadoDao {
	private Session session;

	public EmpleadoDao(Session session) {
		super();
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	public List<Empleado> getAllConcesionario() {
		return session.createQuery("FROM Empleado").list();
	}

	public Empleado getEmpleado(int id) {
		return session.get(Empleado.class, id);
	}

	public void insertEmpleado(Empleado Empleado) {
		session.save(Empleado);
	}

	public void updateEmpleado(Empleado Empleado) {
		session.update(Empleado);
	}

	@SuppressWarnings("unchecked")
	public void deleteEmpleado(Empleado Empleado) {
		((List<ch.makery.address.models.Empleado>) session).remove(Empleado);
	}
}