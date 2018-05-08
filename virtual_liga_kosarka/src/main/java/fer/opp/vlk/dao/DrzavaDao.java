package fer.opp.vlk.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import fer.opp.vlk.model.Drzava;
import fer.opp.vlk.model.Korisnik;

@Transactional
public class DrzavaDao {
	
	private SessionFactory sessionFactory; 
	
	public Drzava dohvatiPoImenu(String ime_drzava){
		Session session = sessionFactory.getCurrentSession();
		List<Drzava> list =(List<Drzava>) session.createQuery(
                      "from Drzava where ime_drzava='"+ ime_drzava+"'"
                ).list();
		Drzava object = null;
		try{
			object = list.get(0);
		}catch(Exception e){			
		}
		return object;
	}
	
	public List<Drzava> dohvatiSve(){
		Session session = sessionFactory.getCurrentSession();
		List<Drzava> list =(List<Drzava>) session.createQuery(
                      "from Drzava"
                ).list();
		return list;
	}



	public void spremi(Drzava object) {
		Session session = sessionFactory.getCurrentSession();
		session.save(object);		
	}


	public void osvjezi(Drzava object) {
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





