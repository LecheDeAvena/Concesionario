package ch.makery.address.dao;

import java.util.List;

import org.hibernate.Session;

import ch.makery.address.models.Cliente;

public class ClienteDao {
	private Session session;
	
	public ClienteDao(Session session) {
		super();
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> getAllCliente() {
		return session.createQuery("FROM Cliente").list();
	}
	
	public Cliente getCliente(int id) {
		return session.get(Cliente.class, id);
	}
	
	public void insertCliente(Cliente Cliente) {
		session.save(Cliente);
	}
	
	public void updateCliente(Cliente Cliente) {
		session.update(Cliente);
	}
	
	public void deleteCliente(Cliente Cliente) {
		((List<ch.makery.address.models.Cliente>) session).remove(Cliente);
	}
}