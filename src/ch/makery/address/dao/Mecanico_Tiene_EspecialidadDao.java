package ch.makery.address.dao;

import java.util.List;
import org.hibernate.Session;

import ch.makery.address.models.Mecanico_Tiene_Especialidad;

public class Mecanico_Tiene_EspecialidadDao {
	private Session session;
	
	public Mecanico_Tiene_EspecialidadDao(Session session) {
		super();
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	public List<Mecanico_Tiene_Especialidad> getAllEmpleadoYEspecialidad() {
		return session.createQuery("FROM Mecanico_Tiene_Especialidad").list();
	}
	
	public Mecanico_Tiene_Especialidad getMecanico_Tiene_Especialidad(int id) {
		return session.get(Mecanico_Tiene_Especialidad.class, id);
	}
	
	public void insertMecanico_Tiene_Especialidad(Mecanico_Tiene_Especialidad Mecanico_Tiene_Especialidad) {
		session.save(Mecanico_Tiene_Especialidad);
	}
	
	public void updateMecanico_Tiene_Especialidad(Mecanico_Tiene_Especialidad Mecanico_Tiene_Especialidad) {
		session.update(Mecanico_Tiene_Especialidad);
	}
	
	public void deleteMecanico_Tiene_Especialidad(Mecanico_Tiene_Especialidad Mecanico_Tiene_Especialidad) {
		((List<ch.makery.address.models.Mecanico_Tiene_Especialidad>) session).remove(Mecanico_Tiene_Especialidad);
	}
}