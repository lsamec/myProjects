package fer.opp.vlk.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import fer.opp.vlk.model.IgracUtakmicaDogadaj;
import fer.opp.vlk.model.Korisnik;

@Transactional
public class IgracUtakmicaDogadajDao {

	private SessionFactory sessionFactory;

	public IgracUtakmicaDogadaj dohvatiPoId(Integer igracUtakmicaDogadaj_id) {
		Session session = sessionFactory.getCurrentSession();
		List<IgracUtakmicaDogadaj> list = (List<IgracUtakmicaDogadaj>) session.createQuery(
				"from IgracUtakmicaDogadaj where igracUtakmicaDogadaj_id='" + igracUtakmicaDogadaj_id + "'").list();
		IgracUtakmicaDogadaj object = null;
		try{
			object = list.get(0);
		}catch(Exception e){		
		}
		return object;
	}
	public IgracUtakmicaDogadaj dohvatiPoIgracuUtakmiciIVrsti(Integer utakmicaId,Integer dogadajId) {
		Session session = sessionFactory.getCurrentSession();
		List<IgracUtakmicaDogadaj> list = (List<IgracUtakmicaDogadaj>) session.createQuery(
				"from IgracUtakmicaDogadaj where utakmica="+ utakmicaId + " and dogadaj="+ dogadajId ).list();
		IgracUtakmicaDogadaj object = null;
		try{
			object = list.get(0);
		}catch(Exception e){			
		}
		return object;
	}
	
	public List<IgracUtakmicaDogadaj> dohvatiPoUtakmici(Integer utakmicaId) {
		Session session = sessionFactory.getCurrentSession();
		List<IgracUtakmicaDogadaj> list = (List<IgracUtakmicaDogadaj>) session.createQuery(
				"from IgracUtakmicaDogadaj where utakmica="+ utakmicaId).list();
		return list;
	}
	
	public List<IgracUtakmicaDogadaj> dohvatiSve() {
		Session session = sessionFactory.getCurrentSession();
		List<IgracUtakmicaDogadaj> list = (List<IgracUtakmicaDogadaj>) session.createQuery("from IgracUtakmicaDogadaj")
				.list();
		return list;
	}

	public void spremi(IgracUtakmicaDogadaj object) {
		Session session = sessionFactory.getCurrentSession();
		session.save(object);
	}
	
	public void obrisi(IgracUtakmicaDogadaj object) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(object);
	}

	public void osvjezi(IgracUtakmicaDogadaj object) {
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
