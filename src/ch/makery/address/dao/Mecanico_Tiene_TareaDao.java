package ch.makery.address.dao;

import java.util.List;
import org.hibernate.Session;

import ch.makery.address.models.Mecanico_Tiene_Tarea;

public class Mecanico_Tiene_TareaDao {
	private Session session;
	
	public Mecanico_Tiene_TareaDao(Session session) {
		super();
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	public List<Mecanico_Tiene_Tarea> getAllEmpleadoYTarea() {
		return session.createQuery("FROM Mecanico_Tiene_Tarea").list();
	}
	
	public Mecanico_Tiene_Tarea getMecanico_Tiene_Tarea(int id) {
		return session.get(Mecanico_Tiene_Tarea.class, id);
	}
	
	public void insertMecanico_Tiene_Tarea(Mecanico_Tiene_Tarea Mecanico_Tiene_Tarea) {
		session.save(Mecanico_Tiene_Tarea);
	}
	
	public void updateMecanico_Tiene_Tarea(Mecanico_Tiene_Tarea Mecanico_Tiene_Tarea) {
		session.update(Mecanico_Tiene_Tarea);
	}
	
	public void deleteMecanico_Tiene_Tarea(Mecanico_Tiene_Tarea Mecanico_Tiene_Tarea) {
		((List<ch.makery.address.models.Mecanico_Tiene_Tarea>) session).remove(Mecanico_Tiene_Tarea);
	}
}