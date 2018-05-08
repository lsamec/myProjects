package fer.opp.vlk.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import fer.opp.vlk.model.Konfiguracija;
import fer.opp.vlk.model.Korisnik;

@Transactional
public class KonfiguracijaDao {
	
	private SessionFactory sessionFactory; 
	
	public Konfiguracija dohvati(){
		Session session = sessionFactory.getCurrentSession();
		List<Konfiguracija> list =(List<Konfiguracija>) session.createQuery(
                      "from Konfiguracija"
                ).list();
		Konfiguracija object = null;
		try{
			object = list.get(0);
		}catch(Exception e){			
		}
		return object;
	}

	public void spremi(Konfiguracija object){
		Session session = sessionFactory.getCurrentSession();
		session.save(object);
	}
	
	public void osvjezi(Konfiguracija object){
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

