package fer.opp.vlk.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import fer.opp.vlk.model.Ekipa;
import fer.opp.vlk.model.Korisnik;
import fer.opp.vlk.model.Natjecatelj;

@Transactional
public class KorisnikDao {
	
	private SessionFactory sessionFactory; 
	

	public void spremi(Korisnik object){
		Session session = sessionFactory.getCurrentSession();
		session.save(object);
	}

	public void osvjezi(Korisnik object){
		Session session = sessionFactory.getCurrentSession();
		session.update(object);
	}
	
	public void obrisi(Korisnik object){
		Session session = sessionFactory.getCurrentSession();
		session.delete(object);
	}
	
	public void delete(Korisnik object){
		Session session = sessionFactory.getCurrentSession();
		session.delete(object);
	}
	
	public List<Korisnik> dohvatiSve(){
		Session session = sessionFactory.getCurrentSession();
		List<Korisnik> list =(List<Korisnik>) session.createQuery(
                      "from Korisnik"
                ).list();
		return list;
	}
	
	public Korisnik dohvatiPoId(Integer korsinik_id){
		Session session = sessionFactory.getCurrentSession();
		List<Korisnik> list =(List<Korisnik>) session.createQuery(
                      "from Korisnik where korisnik_id="+ korsinik_id
                ).list();
		
		Korisnik object = null;
		try{
			object = list.get(0);
		}catch(Exception e){			
		}
		return object;
	}
	
	public Korisnik dohvatiPoKorisnickomImenu(String korisnicko_ime){
		Session session = sessionFactory.getCurrentSession();
		List<Korisnik> list =(List<Korisnik>) session.createQuery(
                      "from Korisnik where korisnicko_ime='"+ korisnicko_ime+"'"
                ).list();
		Korisnik object = null;
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
