package ch.makery.address.dao;

import java.util.List;
import org.hibernate.Session;

import ch.makery.address.models.Tarea;

public class TareaDao {
	private Session session;
	
	public TareaDao(Session session) {
		super();
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	public List<Tarea> getAllVehiculo() {
		return session.createQuery("FROM Tarea").list();
	}
	
	public Tarea getTarea(int id) {
		return session.get(Tarea.class, id);
	}
	
	public void insertTarea(Tarea Tarea) {
		session.save(Tarea);
	}
	
	public void updateTarea(Tarea Tarea) {
		session.update(Tarea);
	}
	
	public void deleteTarea(Tarea Tarea) {
		((List<ch.makery.address.models.Tarea>) session).remove(Tarea);
	}
}