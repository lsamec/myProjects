package fer.opp.vlk.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fer.opp.vlk.dao.DogadajDao;
import fer.opp.vlk.dao.DrzavaDao;
import fer.opp.vlk.dao.EkipaDao;
import fer.opp.vlk.dao.IgracDao;
import fer.opp.vlk.dao.IgracUtakmicaDogadajDao;
import fer.opp.vlk.dao.KonfiguracijaDao;
import fer.opp.vlk.dao.KorisnikDao;
import fer.opp.vlk.dao.NatjecateljDao;
import fer.opp.vlk.dao.PozicijaDao;
import fer.opp.vlk.dao.RazinaPravaDao;
import fer.opp.vlk.dao.UtakmicaDao;
import fer.opp.vlk.model.Igrac;
import fer.opp.vlk.model.Korisnik;
import fer.opp.vlk.model.Natjecatelj;
import fer.opp.vlk.model.ParNatjecateljKorisnik;

@Controller
public class NatjecateljController {
	
	private PozicijaDao pozicijaDao;
	private IgracDao igracDao;
	private EkipaDao ekipaDao;
	private KonfiguracijaDao konfiguracijaDao;
	private DogadajDao dogadajDao;
	private NatjecateljDao natjecateljDao;
	private KorisnikDao korisnikDao;
	private DrzavaDao drzavaDao;
	private RazinaPravaDao razinaPravaDao;
	private UtakmicaDao utakmicaDao;
	private IgracUtakmicaDogadajDao igracUtakmicaDogadajDao;
	
	@RequestMapping(value = "/osobnaNatjecatelj/mojiPodaci", method = RequestMethod.GET)
	public String mojiPodaci(ModelMap model) {

		String username =(String) model.get("username");	
		Korisnik korisnik = korisnikDao.dohvatiPoKorisnickomImenu(username);	
		Natjecatelj natjecatelj = natjecateljDao.dohvatiPoId(korisnik.getKorisnik_id());
		
		model.addAttribute("korisnik",korisnik);
		model.addAttribute("natjecatelj",natjecatelj);

		
		return "mojiPodaci";
	}
	
	@RequestMapping(value = "/osobnaNatjecatelj/mojTim", method = RequestMethod.GET)
	public String mojTim(ModelMap model) {

		String username =(String) model.get("username");		
		Korisnik korisnik = korisnikDao.dohvatiPoKorisnickomImenu(username);		
		Natjecatelj natjecatelj = natjecateljDao.dohvatiPoId(korisnik.getKorisnik_id());
		
		Igrac centar = null;
		Igrac organizator = null;
		Igrac bek = null;
		Igrac krilo = null;
		Igrac krilniCentar = null;
		
		List<Igrac> listaIgraca = igracDao.dohvatiPoVirtEkipi(natjecatelj.getKorisnik_id()); 
		
		for(Igrac igrac : listaIgraca){
			
			switch(igrac.getPozicija().getIme_pozicija()){
			case "centar":
				centar = igrac;
				break;
			case "organizator":
				organizator = igrac;
				break;
			case "bek":
				bek = igrac;
				break;
			case "krilo":
				krilo = igrac;
				break;
			case "krilni centar":
				krilniCentar = igrac;
				break;			
			}
			
		}
		
		model.addAttribute("natjecatelj",natjecatelj);
		
		model.addAttribute("centar",centar);
		model.addAttribute("organizator",organizator);
		model.addAttribute("bek",bek);
		model.addAttribute("krilo",krilo);
		model.addAttribute("krilniCentar",krilniCentar);

		
		return "mojTim";
	}
	
	@RequestMapping(value = "/osobnaNatjecatelj/poredakVirtualneLige", method = RequestMethod.GET)
	public String poredakVirtualneLige(ModelMap model) {
		
		String username =(String) model.get("username");	
		Korisnik korisnik = korisnikDao.dohvatiPoKorisnickomImenu(username);		
		Natjecatelj natjecatelj = natjecateljDao.dohvatiPoId(korisnik.getKorisnik_id());
		
		model.addAttribute("podupireEkipu",natjecatelj.getPodupire_ekipu());
		model.addAttribute("drzava",natjecatelj.getDrzava());
		
		List<Natjecatelj> listaNatjecatelja = natjecateljDao.dohvatiSve();
		
		List<ParNatjecateljKorisnik> listaNatKor = new ArrayList<ParNatjecateljKorisnik>();
		
		for(Natjecatelj aNatjecatelj : listaNatjecatelja){
			Korisnik akorisnik = korisnikDao.dohvatiPoId(aNatjecatelj.getKorisnik_id());
			listaNatKor.add(new ParNatjecateljKorisnik(aNatjecatelj, akorisnik));
		}
		
		model.addAttribute("listaNatKor",listaNatKor);
	
		return "poredakVirtualneLige";
	}
	
	@RequestMapping(value = "/osobnaNatjecatelj/poredakPoDrzavi", method = RequestMethod.GET)
	public String poredakPoDrzavi(ModelMap model) {
		
		String username =(String) model.get("username");	
		Korisnik korisnik = korisnikDao.dohvatiPoKorisnickomImenu(username);		
		Natjecatelj natjecatelj = natjecateljDao.dohvatiPoId(korisnik.getKorisnik_id());
		
		model.addAttribute("podupireEkipu",natjecatelj.getPodupire_ekipu());
		model.addAttribute("drzava",natjecatelj.getDrzava());
		
		List<Natjecatelj> listaNatjecatelja = natjecateljDao.dohvatiPoDrzavaId(natjecatelj.getDrzava().getDrzava_id());
		
		List<ParNatjecateljKorisnik> listaNatKor = new ArrayList<ParNatjecateljKorisnik>();
		
		for(Natjecatelj aNatjecatelj : listaNatjecatelja){
			Korisnik akorisnik = korisnikDao.dohvatiPoId(aNatjecatelj.getKorisnik_id());
			listaNatKor.add(new ParNatjecateljKorisnik(aNatjecatelj, akorisnik));
		}
		
		model.addAttribute("listaNatKor",listaNatKor);
		
		model.addAttribute("tipPoretka",1);
	
		return "poredakInterno";
	}
	
	@RequestMapping(value = "/osobnaNatjecatelj/poredakNavijaca", method = RequestMethod.GET)
	public String poredakNavijaca(ModelMap model) {
		
		String username =(String) model.get("username");	
		Korisnik korisnik = korisnikDao.dohvatiPoKorisnickomImenu(username);		
		Natjecatelj natjecatelj = natjecateljDao.dohvatiPoId(korisnik.getKorisnik_id());
		
		model.addAttribute("podupireEkipu",natjecatelj.getPodupire_ekipu());
		model.addAttribute("drzava",natjecatelj.getDrzava());
		
		List<Natjecatelj> listaNatjecatelja = natjecateljDao.dohvatiPoPodupireEkipuId(natjecatelj.getPodupire_ekipu().getEkipa_id());
		
		List<ParNatjecateljKorisnik> listaNatKor = new ArrayList<ParNatjecateljKorisnik>();
		
		for(Natjecatelj aNatjecatelj : listaNatjecatelja){
			Korisnik akorisnik = korisnikDao.dohvatiPoId(aNatjecatelj.getKorisnik_id());
			listaNatKor.add(new ParNatjecateljKorisnik(aNatjecatelj, akorisnik));
		}
		
		model.addAttribute("listaNatKor",listaNatKor);
		
		model.addAttribute("tipPoretka",2);
	
		return "poredakInterno";
	}
	
	public PozicijaDao getPozicijaDao() {
		return pozicijaDao;
	}

	@Autowired(required = true)
	@Qualifier(value = "pozicijaDao")
	public void setPozicijaDao(PozicijaDao pozicijaDao) {
		this.pozicijaDao = pozicijaDao;
	}

	public IgracDao getIgracDao() {
		return igracDao;
	}

	@Autowired(required = true)
	@Qualifier(value = "igracDao")
	public void setIgracDao(IgracDao igracDao) {
		this.igracDao = igracDao;
	}

	public EkipaDao getEkipaDao() {
		return ekipaDao;
	}

	@Autowired(required = true)
	@Qualifier(value = "ekipaDao")
	public void setEkipaDao(EkipaDao ekipaDao) {
		this.ekipaDao = ekipaDao;
	}

	public KonfiguracijaDao getKonfiguracijaDao() {
		return konfiguracijaDao;
	}

	@Autowired(required = true)
	@Qualifier(value = "konfiguracijaDao")
	public void setKonfiguracijaDao(KonfiguracijaDao konfiguracijaDao) {
		this.konfiguracijaDao = konfiguracijaDao;
	}

	public DogadajDao getDogadajDao() {
		return dogadajDao;
	}

	@Autowired(required = true)
	@Qualifier(value = "dogadajDao")
	public void setDogadajDao(DogadajDao dogadajDao) {
		this.dogadajDao = dogadajDao;
	}

	public NatjecateljDao getNatjecateljDao() {
		return natjecateljDao;
	}

	@Autowired(required = true)
	@Qualifier(value = "natjecateljDao")
	public void setNatjecateljDao(NatjecateljDao natjecateljDao) {
		this.natjecateljDao = natjecateljDao;
	}

	public KorisnikDao getKorisnikDao() {
		return korisnikDao;
	}

	@Autowired(required = true)
	@Qualifier(value = "korisnikDao")
	public void setKorisnikDao(KorisnikDao korisnikDao) {
		this.korisnikDao = korisnikDao;
	}

	public DrzavaDao getDrzavaDao() {
		return drzavaDao;
	}

	@Autowired(required = true)
	@Qualifier(value = "drzavaDao")
	public void setDrzavaDao(DrzavaDao drzavaDao) {
		this.drzavaDao = drzavaDao;
	}

	public RazinaPravaDao getRazinaPravaDao() {
		return razinaPravaDao;
	}

	@Autowired(required = true)
	@Qualifier(value = "razinaPravaDao")
	public void setRazinaPravaDao(RazinaPravaDao razinaPravaDao) {
		this.razinaPravaDao = razinaPravaDao;
	}

	public UtakmicaDao getUtakmicaDao() {
		return utakmicaDao;
	}

	@Autowired(required = true)
	@Qualifier(value = "utakmicaDao")
	public void setUtakmicaDao(UtakmicaDao utakmicaDao) {
		this.utakmicaDao = utakmicaDao;
	}

	public IgracUtakmicaDogadajDao getIgracUtakmicaDogadajDao() {
		return igracUtakmicaDogadajDao;
	}

	@Autowired(required = true)
	@Qualifier(value = "igracUtakmicaDogadajDao")
	public void setIgracUtakmicaDogadajDao(
			IgracUtakmicaDogadajDao igracUtakmicaDogadajDao) {
		this.igracUtakmicaDogadajDao = igracUtakmicaDogadajDao;
	}
	
	@ModelAttribute("username")
	private String dodajAtributKorisnika(ModelMap model){
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String username = null;
		if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			username = userDetail.getUsername();
		}
		return username;
	}
}
