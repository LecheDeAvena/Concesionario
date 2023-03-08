package ch.makery.address.dao;

import java.util.List;
import org.hibernate.Session;

import ch.makery.address.models.Mecanico;

public class MecanicoDao {
	private Session session;
	
	public MecanicoDao(Session session) {
		super();
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	public List<Mecanico> getAllEmpleado() {
		return session.createQuery("FROM Mecanico").list();
	}
	
	public Mecanico getMecanico(int id) {
		return session.get(Mecanico.class, id);
	}
	
	public void insertMecanico(Mecanico Mecanico) {
		session.save(Mecanico);
	}
	
	public void updateMecanico(Mecanico Mecanico) {
		session.update(Mecanico);
	}
	
	public void deleteMecanico(Mecanico Mecanico) {
		((List<ch.makery.address.models.Mecanico>) session).remove(Mecanico);
	}
}