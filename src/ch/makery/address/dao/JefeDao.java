package ch.makery.address.dao;

import java.util.List;
import org.hibernate.Session;

import ch.makery.address.models.Jefe;

public class JefeDao {
	private Session session;

	public JefeDao(Session session) {
		super();
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	public List<Jefe> getAllEmpleado() {
		return session.createQuery("FROM Jefe").list();
	}

	public Jefe getJefe(int id) {
		return session.get(Jefe.class, id);
	}

	public void insertJefe(Jefe Jefe) {
		session.save(Jefe);
	}

	public void updateJefe(Jefe Jefe) {
		session.update(Jefe);
	}

	@SuppressWarnings("unchecked")
	public void deleteJefe(Jefe Jefe) {
		((List<ch.makery.address.models.Jefe>) session).remove(Jefe);
	}
}