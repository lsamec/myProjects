package fer.opp.vlk.dao;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.transaction.annotation.Transactional;

import fer.opp.vlk.model.Drzava;
import fer.opp.vlk.model.Ekipa;

@Transactional
public class EkipaDao {

	private SessionFactory sessionFactory;

	public Ekipa dohvatiPoImenu(String ime_ekipa) {
		Session session = sessionFactory.getCurrentSession();
		List<Ekipa> list = (List<Ekipa>) session.createQuery(
				"from Ekipa where ime_ekipa='" + ime_ekipa + "'").list();
		
		Ekipa object = null;
		try{
			object = list.get(0);
		}catch(Exception e){			
		}
		return object;
	}

	public List<Ekipa> dohvatiSve() {
		Session session = sessionFactory.getCurrentSession();
		List<Ekipa> list = (List<Ekipa>) session.createQuery(
				"from Ekipa order by pobjedene desc, izgubljene asc").list();
		return list;
	}

	public List<Ekipa> dohvatiPoId(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		List<Ekipa> list = (List<Ekipa>) session.createQuery(
				"from Ekipa where ekipa_id=" + id).list();
		return list;
	}

	public void spremi(Ekipa object) {
		Session session = sessionFactory.getCurrentSession();
		session.save(object);
	}

	public void osvjezi(Ekipa object) {
		Session session = sessionFactory.getCurrentSession();
		session.update(object);
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
