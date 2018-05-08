package fer.opp.vlk.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import fer.opp.vlk.model.Dogadaj;
import fer.opp.vlk.model.Korisnik;

@Transactional
public class DogadajDao {
	
	private SessionFactory sessionFactory; 
	
	public Dogadaj dohvatiPoImenu(String ime_dogadaj){
		Session session = sessionFactory.getCurrentSession();
		List<Dogadaj> list =(List<Dogadaj>) session.createQuery(
                      "from Dogadaj where ime_dogadaj='"+ ime_dogadaj+"'"
                ).list();
		Dogadaj object = null;
		try{
			object = list.get(0);
		}catch(Exception e){			
		}
		return object;
	}
	public Dogadaj dohvatiPoId(Integer dogadaj_id){
		Session session = sessionFactory.getCurrentSession();
		List<Dogadaj> list =(List<Dogadaj>) session.createQuery(
                      "from Dogadaj where dogadaj_id="+ dogadaj_id
                ).list();
		Dogadaj object = null;
		try{
			object = list.get(0);
		}catch(Exception e){			
		}
		return object;
	}
	
	public List<Dogadaj> dohvatiSve(){
		Session session = sessionFactory.getCurrentSession();
		List<Dogadaj> list =(List<Dogadaj>) session.createQuery(
                      "from Dogadaj order by dogadaj_id asc"
                ).list();
		return list;
	}



	public void spremi(Dogadaj object) {
		Session session = sessionFactory.getCurrentSession();
		session.save(object);		
	}


	public void osvjezi(Dogadaj object) {
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




