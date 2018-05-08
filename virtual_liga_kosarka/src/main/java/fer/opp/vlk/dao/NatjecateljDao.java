package fer.opp.vlk.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import fer.opp.vlk.model.Igrac;
import fer.opp.vlk.model.Korisnik;
import fer.opp.vlk.model.Natjecatelj;

@Transactional
public class NatjecateljDao {
	
	private SessionFactory sessionFactory; 
	
	public Natjecatelj dohvatiPoId(Integer korsinik_id){
		Session session = sessionFactory.getCurrentSession();
		List<Natjecatelj> list =(List<Natjecatelj>) session.createQuery(
                      "from Natjecatelj where korisnik_id="+ korsinik_id
                ).list();
		
		Natjecatelj object = null;
		try{
			object = list.get(0);
		}catch(Exception e){			
		}
		return object;
	}
	
	public Natjecatelj dohvatiPoNazivuVirtEkipe(String naziv_virt_ekipe){
		Session session = sessionFactory.getCurrentSession();
		List<Natjecatelj> list =(List<Natjecatelj>) session.createQuery(
                      "from Natjecatelj where naziv_virt_ekipe='"+ naziv_virt_ekipe+"'"
                ).list();
		
		Natjecatelj object = null;
		try{
			object = list.get(0);
		}catch(Exception e){			
		}
		return object;
	}
	
	public List<Natjecatelj> dohvatiSve(){
		Session session = sessionFactory.getCurrentSession();
		List<Natjecatelj> list =(List<Natjecatelj>) session.createQuery(
                      "from Natjecatelj order by bodovi_virt_ekipe desc"
                ).list();
		return list;
	}
	
	public List<Natjecatelj> dohvatiPoPodupireEkipuId(Integer podupire_ekipu_id){
		Session session = sessionFactory.getCurrentSession();
		List<Natjecatelj> list =(List<Natjecatelj>) session.createQuery(
                      "from Natjecatelj where podupire_ekipu="+podupire_ekipu_id + " order by bodovi_virt_ekipe desc"
                ).list();
		return list;
	}
	
	public List<Natjecatelj> dohvatiPoDrzavaId(Integer drzava_id){
		Session session = sessionFactory.getCurrentSession();
		List<Natjecatelj> list =(List<Natjecatelj>) session.createQuery(
                      "from Natjecatelj where drzava="+drzava_id+ " order by bodovi_virt_ekipe desc"
                ).list();
		return list;
	}

	public void spremi(Natjecatelj object) {
		Session session = sessionFactory.getCurrentSession();
		session.save(object);		
	}


	public void osvjezi(Natjecatelj object) {
		Session session = sessionFactory.getCurrentSession();
		session.update(object);	
	}
	
	
	public void obrisi(Natjecatelj object) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(object);	
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}





