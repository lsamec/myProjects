package fer.opp.vlk.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import fer.opp.vlk.model.Korisnik;
import fer.opp.vlk.model.RazinaPrava;

@Transactional
public class RazinaPravaDao {
		
		private SessionFactory sessionFactory; 
		
		public RazinaPrava dohvatiPoId(Integer id){
			Session session = sessionFactory.getCurrentSession();
			List<RazinaPrava> list =(List<RazinaPrava>) session.createQuery(
	                      "from RazinaPrava where razina_prava_id="+ id
	                ).list();
			RazinaPrava object = null;
			try{
				object = list.get(0);
			}catch(Exception e){			
			}
			return object;
		}
		
		public RazinaPrava dohvatiPoImenu(String type){
			Session session = sessionFactory.getCurrentSession();
			List<RazinaPrava> list =(List<RazinaPrava>) session.createQuery(
	                      "from RazinaPrava where razina_prava_vrsta='"+ type+"'"
	                ).list();
			return list.get(0);
		}

		public SessionFactory getSessionFactory() {
			return sessionFactory;
		}

		public void setSessionFactory(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;
		}
}


