package fer.opp.vlk.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import fer.opp.vlk.model.Korisnik;
import fer.opp.vlk.model.Pozicija;

@Transactional
public class PozicijaDao {
	
	private SessionFactory sessionFactory; 
	
	public Pozicija dohvatiPoImenu(String ime_pozicija){
		Session session = sessionFactory.getCurrentSession();
		List<Pozicija> list =(List<Pozicija>) session.createQuery(
                      "from Pozicija where ime_pozicija='"+ ime_pozicija+"'"
                ).list();
		Pozicija object = null;
		try{
			object = list.get(0);
		}catch(Exception e){			
		}
		return object;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}

