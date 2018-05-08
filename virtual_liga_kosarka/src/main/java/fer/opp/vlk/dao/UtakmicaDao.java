package fer.opp.vlk.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import fer.opp.vlk.model.Drzava;
import fer.opp.vlk.model.Korisnik;
import fer.opp.vlk.model.Utakmica;

@Transactional
public class UtakmicaDao {
	
	private SessionFactory sessionFactory; 
	
	public Utakmica dohvatiPoId(Integer id){
		Session session = sessionFactory.getCurrentSession();
		List<Utakmica> list =(List<Utakmica>) session.createQuery(
                      "from Utakmica where utakmica_id="+ id
                ).list();
		Utakmica object = null;
		try{
			object = list.get(0);
		}catch(Exception e){			
		}
		return object;
	}
	
	public List<Utakmica> dohvatiSve(){
		Session session = sessionFactory.getCurrentSession();
		if(session == null){
			return null;
		}
		List<Utakmica> list =(List<Utakmica>) session.createQuery(
                      "from Utakmica"
                ).list();
		return list;
	}
	
	public List<Utakmica> dohvatiSveOdigrane(){
		Session session = sessionFactory.getCurrentSession();
		List<Utakmica> list =(List<Utakmica>) session.createQuery(
                      "from Utakmica where rezultat is not null"
                ).list();
		return list;
	}
	
	public List<Utakmica> dohvatiPoEkipi(Integer ekipa){
		Session session = sessionFactory.getCurrentSession();
		Query q= (Query) session.createQuery(
				"from Utakmica where ekipa_domacin ="+ekipa+" or ekipa_gost= "+ekipa);
		List<Utakmica> list = q.list();
		return list;
	}


	public void spremi(Utakmica object) {
		Session session = sessionFactory.getCurrentSession();
		session.save(object);		
	}


	public void osvjezi(Utakmica object) {
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






