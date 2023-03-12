package ch.makery.address.dao;

import java.util.List;
import org.hibernate.Session;

import ch.makery.address.models.Concesionario;

public class ConcesionarioDao {
	private Session session;

	public ConcesionarioDao(Session session) {
		super();
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	public List<Concesionario> getAllConcesionario() {
		return session.createQuery("FROM Concesionario").list();
	}

	public Concesionario getConcesionario(int id) {
		return session.get(Concesionario.class, id);
	}

	public void insertConcesionario(Concesionario Concesionario) {
		session.save(Concesionario);
	}

	public void updateConcesionario(Concesionario Concesionario) {
		session.update(Concesionario);
	}

	@SuppressWarnings("unchecked")
	public void deleteConcesionario(Concesionario Concesionario) {
		((List<ch.makery.address.models.Concesionario>) session).remove(Concesionario);
	}
}