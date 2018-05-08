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
import fer.opp.vlk.model.Dogadaj;
import fer.opp.vlk.model.Ekipa;
import fer.opp.vlk.model.Igrac;
import fer.opp.vlk.model.IgracUtakmicaDogadaj;
import fer.opp.vlk.model.IgracUtakmiceForma;
import fer.opp.vlk.model.Utakmica;
import fer.opp.vlk.model.UtakmicaTehForma;
import fer.opp.vlk.utils.Utils;

@Controller
public class TehnickaKomisijaController {
	
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
	
	
	@RequestMapping(value = "/osobnaTehKomisija/odigraneUtakmice", method = RequestMethod.GET)
	public String otvoriOdigraneUtakmice(ModelMap model) {

		List<Utakmica> listaOdigranihUtakmica = utakmicaDao.dohvatiSveOdigrane();
		List<UtakmicaTehForma> listaFormiOdigranihUtakmica = new ArrayList<>();
		for(Utakmica utakmica : listaOdigranihUtakmica){
			listaFormiOdigranihUtakmica.add(new UtakmicaTehForma(utakmica.getUtakmica_id(), utakmica.getEkipa_domacin().getIme_ekipa(), utakmica.getEkipa_gost().getIme_ekipa(), utakmica.getIgrac_utakmice() != null));
		}
		model.addAttribute("listaFormiOdigranihUtakmica",listaFormiOdigranihUtakmica);
		return "odigraneUtakmice";
	}
	
	@RequestMapping(value = "/osobnaTehKomisija/odigraneUtakmice/{utakmicaId}", method = RequestMethod.GET)
	public String otvoriIzborNajboljegIgraca(@PathVariable("utakmicaId") Integer utakmicaId,ModelMap model) {

		Utakmica utakmica = utakmicaDao.dohvatiPoId(utakmicaId);
		if(utakmica.getRezultat() == null){
			return "redirect:/403";
		}
		model.addAttribute("utakmicaId",utakmicaId);
		Igrac igracUtakmice = utakmica.getIgrac_utakmice();
		String igracUtakmiceString = null;
		if(igracUtakmice != null){
			igracUtakmiceString=igracUtakmice.getIme_igrac()+ " " + igracUtakmice.getPrezime_igrac() + " - " + igracUtakmice.getEkipa().getIme_ekipa() + " " + igracUtakmice.getBroj_dresa();
		}
		model.addAttribute("igracUtakmice",igracUtakmiceString);
		List<Igrac> listaIgracaNaUtakmici = new ArrayList<>();
		listaIgracaNaUtakmici.addAll(utakmica.getEkipa_domacin().getIgraciEkipe());
		listaIgracaNaUtakmici.addAll(utakmica.getEkipa_gost().getIgraciEkipe());
		List<String> listaIgracaNaUtakmiciString = new ArrayList<String>();
		for(Igrac igrac : listaIgracaNaUtakmici){
			listaIgracaNaUtakmiciString.add(igrac.getIme_igrac()+ " " + igrac.getPrezime_igrac() + " - " + igrac.getEkipa().getIme_ekipa() + " " + igrac.getBroj_dresa());
		}
		model.addAttribute("listaIgraca",listaIgracaNaUtakmiciString);
		IgracUtakmiceForma igracUtakmiceForma = new IgracUtakmiceForma();
		model.addAttribute("igracUtakmiceForma",igracUtakmiceForma);
		
		return "izborIgracaUtakmice";
	}
	
	@RequestMapping(value = "/osobnaTehKomisija/izborIgracaUtakmice/{utakmicaId}", method = RequestMethod.POST)
	public String spremiNajboljegIgraca(@ModelAttribute("igracUtakmiceForma") IgracUtakmiceForma igracUtakmiceForma,@PathVariable("utakmicaId") Integer utakmicaId) {
		Utakmica utakmica = utakmicaDao.dohvatiPoId(utakmicaId);
		String ekipaBrojDresa = igracUtakmiceForma.getIgracUtakmice().substring(igracUtakmiceForma.getIgracUtakmice().lastIndexOf("-")+2);
		String ekipaIme = ekipaBrojDresa.substring(0,ekipaBrojDresa.lastIndexOf(" "));
		String brojDresa = ekipaBrojDresa.substring(ekipaBrojDresa.lastIndexOf(" ")+1);
		Dogadaj dogadaj = dogadajDao.dohvatiPoImenu("najbolji_igrac_utakmice");
		Ekipa ekipa = ekipaDao.dohvatiPoImenu(ekipaIme);
		Igrac igracUtakmice = igracDao.dohvatiPoEkipiIBrojuDresa(ekipa.getEkipa_id(), Integer.parseInt(brojDresa));
		IgracUtakmicaDogadaj najboljiIgracDog = igracUtakmicaDogadajDao.dohvatiPoIgracuUtakmiciIVrsti(utakmicaId,dogadaj.getDogadaj_id());
		if(najboljiIgracDog != null){
			Igrac prosliNajbolji = igracDao.dohvatiPoId(najboljiIgracDog.getIgrac().getIgrac_id());
			prosliNajbolji.setUk_bodovi(prosliNajbolji.getUk_bodovi()-dogadaj.getVrijednost());
			igracUtakmicaDogadajDao.obrisi(najboljiIgracDog);
			igracDao.osvjezi(prosliNajbolji);
		}
		IgracUtakmicaDogadaj noviNajboljiIgracDog = new IgracUtakmicaDogadaj();
		noviNajboljiIgracDog.setDogadaj(dogadaj);
		noviNajboljiIgracDog.setIgrac(igracUtakmice);
		noviNajboljiIgracDog.setUtakmica(utakmica);
		noviNajboljiIgracDog.setVrijeme("40:0");
		igracUtakmicaDogadajDao.spremi(noviNajboljiIgracDog);
		
		igracUtakmice.setUk_bodovi(igracUtakmice.getUk_bodovi()+dogadaj.getVrijednost());
		igracDao.osvjezi(igracUtakmice);
		utakmica.setIgrac_utakmice(igracUtakmice);
		utakmicaDao.osvjezi(utakmica);
		
		Utils.osvjeziBodoveNatjecatelja(igracDao, natjecateljDao);
		
		return "redirect:/osobnaTehKomisija/odigraneUtakmice";
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
