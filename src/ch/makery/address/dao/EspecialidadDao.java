package ch.makery.address.dao;

import java.util.List;
import org.hibernate.Session;

import ch.makery.address.models.Especialidad;

public class EspecialidadDao {
	private Session session;
	
	public EspecialidadDao(Session session) {
		super();
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	public List<Especialidad> getAllEspecialidad() {
		return session.createQuery("FROM Especialidad").list();
	}
	
	public Especialidad getEspecialidad(int id) {
		return session.get(Especialidad.class, id);
	}
	
	public void insertEspecialidad(Especialidad Especialidad) {
		session.save(Especialidad);
	}
	
	public void updateEspecialidad(Especialidad Especialidad) {
		session.update(Especialidad);
	}
	
	public void deleteEspecialidad(Especialidad Especialidad) {
		((List<ch.makery.address.models.Especialidad>) session).remove(Especialidad);
	}
}