package ch.makery.address.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.context.internal.ThreadLocalSessionContext;

public class HibernateUtil {

	private static SessionFactory sessionFactory;
	private static Session session;
	
	
	public static Session getSession() {
		if (sessionFactory == null) {
			session = getSessionFactory().openSession();
		}
				
		return session;
	}

	public static void closeSession() {
		Session session = ThreadLocalSessionContext.unbind(sessionFactory);
		if (session != null) {
			session.close();
		}
		closeSessionFactory();
	}
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			StandardServiceRegistry sr = new StandardServiceRegistryBuilder().configure().build();
			sessionFactory = new MetadataSources(sr).buildMetadata().buildSessionFactory();
		}
		return sessionFactory;
	}

	private static void closeSessionFactory() {
		if ((sessionFactory != null) && (sessionFactory.isClosed() == false)) {
			sessionFactory.close();
		}
	}

}
