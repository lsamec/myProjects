package fer.opp.vlk.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import fer.opp.vlk.model.Ekipa;
import fer.opp.vlk.model.Igrac;

@Transactional
public class IgracDao {
	
	private SessionFactory sessionFactory; 
	
	
	public List<Igrac> dohvatiSve(){
		Session session = sessionFactory.getCurrentSession();
		List<Igrac> list =(List<Igrac>) session.createQuery(
                      "from Igrac"
                ).list();
		return list;
	}
	
	public List<Igrac> dohvatiPoVirtEkipi(Integer natjecateljId){
		Session session = sessionFactory.getCurrentSession();
		List<Igrac> list =(List<Igrac>) session.createQuery(
                      "select igrac from Igrac as igrac join igrac.sviNatjecateljiUCijojJeVirtEkipi as natjecatelj with natjecatelj.korisnik_id="+natjecateljId
                ).list();
		return list;
	}
	
	public Igrac dohvatiPoId(Integer igracId){
		Session session = sessionFactory.getCurrentSession();
		List<Igrac> list =(List<Igrac>) session.createQuery(
                      "from Igrac where igrac_id="+ igracId
                ).list();
		Igrac object = null;
		try{
			object = list.get(0);
		}catch(Exception e){			
		}
		return object;
	}
	
	public List<Igrac> dohvatiPoUlozi(String uloga){
		Session session = sessionFactory.getCurrentSession();
		List<Igrac> list =(List<Igrac>) session.createQuery(
                      "from Igrac as igrac where igrac.pozicija.ime_pozicija='"+uloga+"'"
                ).list();
		return list;
	}
	
	public Igrac dohvatiPoEkipiIBrojuDresa(Integer ekipa_id,Integer broj_dresa){
		Session session = sessionFactory.getCurrentSession();
		List<Igrac> list =(List<Igrac>) session.createQuery(
                      "from Igrac where ekipa="+ekipa_id+" and broj_dresa="+broj_dresa
                ).list();
		Igrac object = null;
		try{
			object = list.get(0);
		}catch(Exception e){			
		}
		return object;
	}
	
	public List<Igrac> dohvatiPoEkipi(Integer ekipa_id){
		Session session = sessionFactory.getCurrentSession();
		List<Igrac> list =(List<Igrac>) session.createQuery(
                      "from Igrac where ekipa="+ekipa_id
                ).list();
		return list;
	}
	
	public void spremi(Igrac igrac){
		Session session = sessionFactory.getCurrentSession();
		session.save(igrac);
	}
	
	public void osvjezi(Igrac igrac){
		Session session = sessionFactory.getCurrentSession();
		session.update(igrac);
	}
	
	public void obrisi(Igrac igrac){
		Session session = sessionFactory.getCurrentSession();
		session.delete(igrac);
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}


