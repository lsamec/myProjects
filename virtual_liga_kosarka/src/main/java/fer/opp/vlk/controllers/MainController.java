package fer.opp.vlk.controllers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fer.opp.vlk.dao.DrzavaDao;
import fer.opp.vlk.dao.EkipaDao;
import fer.opp.vlk.dao.IgracDao;
import fer.opp.vlk.dao.IgracUtakmicaDogadajDao;
import fer.opp.vlk.dao.KonfiguracijaDao;
import fer.opp.vlk.dao.KorisnikDao;
import fer.opp.vlk.dao.NatjecateljDao;
import fer.opp.vlk.dao.RazinaPravaDao;
import fer.opp.vlk.dao.UtakmicaDao;
import fer.opp.vlk.model.Dogadaj;
import fer.opp.vlk.model.Drzava;
import fer.opp.vlk.model.Ekipa;
import fer.opp.vlk.model.Igrac;
import fer.opp.vlk.model.IgracUtakmicaDogadaj;
import fer.opp.vlk.model.Konfiguracija;
import fer.opp.vlk.model.Korisnik;
import fer.opp.vlk.model.Natjecatelj;
import fer.opp.vlk.model.NatjecateljForma;
import fer.opp.vlk.model.RazinaPrava;
import fer.opp.vlk.model.Utakmica;
import fer.opp.vlk.utils.NatjecateljFormaValidator;

@Controller
public class MainController {

	private EkipaDao ekipaDao;
	private IgracDao igracDao;
	private DrzavaDao drzavaDao;
	private KonfiguracijaDao konfiguracijaDao;
	private NatjecateljDao natjecateljDao;
	private KorisnikDao korisnikDao;
	private RazinaPravaDao razinaPravaDao;
	private IgracUtakmicaDogadajDao igracUtakmicaDogadajDao;
	private UtakmicaDao utakmicaDao;

	private NatjecateljFormaValidator natjecateljFormaValidator;

	
	@InitBinder("natjecateljForma")
	protected void initBinder(WebDataBinder binder) {
			binder.setValidator(natjecateljFormaValidator);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String poredak(ModelMap model) {

		List<Ekipa> ekipaList = ekipaDao.dohvatiSve();
		model.addAttribute("ekipaList", ekipaList);
		return "naslovnica";

	}
	@RequestMapping(value="/provjeriNaslovnicu", method= RequestMethod.GET)
    public @ResponseBody
    String osvjezi() {
		List<Ekipa> ekipe = ekipaDao.dohvatiSve();
		String json = "["+ekipe.get(0).toJson();
		for(int i=1;i<ekipe.size();i++){
			json=json+","+ekipe.get(i).toJson();
		}
		json=json.concat("]");
		System.out.println(json);
 		return json;
    }
	
	@RequestMapping(value="/provjeriSveUtakmice", method= RequestMethod.GET)
    public @ResponseBody
    byte[] osvjeziSveUtakmice() throws UnsupportedEncodingException{
		List<Utakmica> utakmice = utakmicaDao.dohvatiSve();
		String json = "["+utakmice.get(0).toJson();
		for(int i=1;i<utakmice.size();i++){
			json=json+","+utakmice.get(i).toJson();
		}
		json=json.concat("]");
 		return json.getBytes("UTF-8");
    }
	

	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String odabrano(@RequestParam("ekipa") String ekipa, ModelMap model) {
		model.addAttribute("ekipa", ekipa);
		return "redirect:/detaljiEkipe";

	}
		
	@RequestMapping(value = "/detaljiEkipe", method=RequestMethod.GET)
	public String detalji(@ModelAttribute("ekipa") String ekipa, ModelMap model){
		Ekipa ekipa1 = ekipaDao.dohvatiPoImenu(ekipa);
		List<Utakmica> utakmica = utakmicaDao.dohvatiPoEkipi(ekipa1.getEkipa_id());
		model.addAttribute("Utakmice", utakmica);
		return "detaljiEkipe";
	}
	
	
	@RequestMapping(value = "/sveUtakmice", method = RequestMethod.GET)
	public String sveUtakmice(ModelMap model){
		List<Utakmica> utakmica = utakmicaDao.dohvatiSve();
		model.addAttribute("Utakmice", utakmica);
		return "sveUtakmice";
	}
	
	@RequestMapping(value = "/Ekipa", method = RequestMethod.GET)
	public String ekipa(@ModelAttribute("ekipa") String ekipa, ModelMap model){
		Ekipa ekipa1 = ekipaDao.dohvatiPoImenu(ekipa);
		List<Igrac> igraci = igracDao.dohvatiPoEkipi(ekipa1.getEkipa_id());
		model.addAttribute("igraci", igraci);
		return "Ekipa";
	}	

	@RequestMapping(value = "/prijava", method = RequestMethod.GET)
	public String prijava(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			ModelMap model) {

			if (error != null) {
				model.addAttribute("error",
						"Nevažeće korisničko ime ili lozinka!");
			}

			if (logout != null) {
				model.addAttribute("msg", "Uspješno ste se odjavili.");
			}

			return "prijava";

	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String nedozvoljenPristup(ModelMap model) {

		return "403";

	}

	@RequestMapping(value = "/osobnaStranica", method = RequestMethod.GET)
	public String otvoriOsobnuStranicu(ModelMap model) {

		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>(
				(Collection<SimpleGrantedAuthority>) SecurityContextHolder
						.getContext().getAuthentication().getAuthorities());
		String role = authorities.get(0).toString();
		switch (role) {
		case "admin":
			return "redirect:/osobnaAdmin";
		case "sluzbena_osoba":
			return "redirect:/osobnaSlOsoba";
		case "tehnicka_komisija":
			return "redirect:/osobnaTehKomisija";
		case "natjecatelj":
			return "redirect:/osobnaNatjecatelj";
		}

		return "redirect:/prijava";

	}

	@RequestMapping(value = "/osobnaAdmin", method = RequestMethod.GET)
	public String otvoriOsobnuAdmin(ModelMap model) {
		
		Konfiguracija konfiguracija = konfiguracijaDao.dohvati();
		if(konfiguracija.getJust_a_konf_holder().equals(true)){
			model.addAttribute("postoji_konf", false);
			model.addAttribute("odobren_konf", false);
			model.addAttribute("postoji_sastav_ekipa", konfiguracija.getPostoji_sastav_ekipa());
			model.addAttribute("igraci_ok", konfiguracija.getIgraci_ok());
			model.addAttribute("moze_sim", false);
			model.addAttribute("odobrena_reg", false);
			model.addAttribute("zapoceta_liga", false);
		} else{
			model.addAttribute("postoji_konf", true);
			model.addAttribute("odobren_konf", konfiguracija.getOdobren_konf());
			model.addAttribute("postoji_sastav_ekipa", konfiguracija.getPostoji_sastav_ekipa());
			model.addAttribute("igraci_ok", konfiguracija.getIgraci_ok());
			model.addAttribute("moze_sim", konfiguracija.getMoze_sim());
			model.addAttribute("odobrena_reg", konfiguracija.getOdobrena_reg());
			model.addAttribute("zapoceta_liga", konfiguracija.getZapoceta_liga());
		}

		return "osobnaAdmin";

	}
	


	@RequestMapping(value = "/osobnaSlOsoba", method = RequestMethod.GET)
	public String otvoriOsobnuSlOsoba(ModelMap model) {
		
		Konfiguracija konfiguracija = konfiguracijaDao.dohvati();
		if(konfiguracija.getJust_a_konf_holder().equals(true)){
			model.addAttribute("postoji_konf", false);
			model.addAttribute("odobren_konf", false);
			model.addAttribute("postoji_sastav_ekipa", konfiguracija.getPostoji_sastav_ekipa());
			model.addAttribute("igraci_ok", konfiguracija.getIgraci_ok());
			model.addAttribute("moze_sim", false);
			model.addAttribute("odobrena_reg", false);
			model.addAttribute("zapoceta_liga", false);
		} else{
			model.addAttribute("postoji_konf", true);
			model.addAttribute("odobren_konf", konfiguracija.getOdobren_konf());
			model.addAttribute("postoji_sastav_ekipa", konfiguracija.getPostoji_sastav_ekipa());
			model.addAttribute("igraci_ok", konfiguracija.getIgraci_ok());
			model.addAttribute("moze_sim", konfiguracija.getMoze_sim());
			model.addAttribute("odobrena_reg", konfiguracija.getOdobrena_reg());
			model.addAttribute("zapoceta_liga", konfiguracija.getZapoceta_liga());
		}

		return "osobnaSlOsoba";

	}

	@RequestMapping(value = "/osobnaTehKomisija", method = RequestMethod.GET)
	public String otvoriOsobnuTehKomisija(ModelMap model) {

		return "osobnaTehKomisija";

	}

	@RequestMapping(value = "/osobnaNatjecatelj", method = RequestMethod.GET)
	public String otvoriOsobnuNatjecatelj(ModelMap model) {

		return "osobnaNatjecatelj";

	}

	@RequestMapping(value = "/registracija", method = RequestMethod.GET)
	public String otvoriRegistraciju(ModelMap model) {

		Konfiguracija konf = konfiguracijaDao.dohvati();
		if(konf.getOdobrena_reg() == null || konf.getOdobrena_reg() == false) {
			return "403";
		}
		dodajPodatkeZaRegistraciju(model);

		NatjecateljForma natjecateljForma = new NatjecateljForma();
		model.addAttribute("natjecateljForma", natjecateljForma);

		return "registracija";

	}

	@RequestMapping(value = "/registiraj", method = RequestMethod.POST)
	public String registriraj(
			@ModelAttribute("natjecateljForma") @Validated NatjecateljForma natjecateljForma,
			BindingResult result, ModelMap model) {

		if (result.hasErrors()) {

			dodajPodatkeZaRegistraciju(model);
			return "registracija";
		} else {

			Igrac bek = igracDao.dohvatiPoEkipiIBrojuDresa(
					parsirajEkipu(natjecateljForma.getBek()),
					parsirajBrojDresa(natjecateljForma.getBek()));
			Igrac organizator = igracDao.dohvatiPoEkipiIBrojuDresa(
					parsirajEkipu(natjecateljForma.getOrganizator()),
					parsirajBrojDresa(natjecateljForma.getOrganizator()));
			Igrac krilo = igracDao.dohvatiPoEkipiIBrojuDresa(
					parsirajEkipu(natjecateljForma.getKrilo()),
					parsirajBrojDresa(natjecateljForma.getKrilo()));
			Igrac krilniCentar = igracDao.dohvatiPoEkipiIBrojuDresa(
					parsirajEkipu(natjecateljForma.getKrilniCentar()),
					parsirajBrojDresa(natjecateljForma.getKrilniCentar()));
			Igrac centar = igracDao.dohvatiPoEkipiIBrojuDresa(
					parsirajEkipu(natjecateljForma.getCentar()),
					parsirajBrojDresa(natjecateljForma.getCentar()));

			Natjecatelj natjecatelj = new Natjecatelj();
			Korisnik korisnik = new Korisnik();

			natjecatelj.setIme(natjecateljForma.getIme());
			natjecatelj.setPrezime(natjecateljForma.getPrezime());
			natjecatelj.setNaziv_virt_ekipe(natjecateljForma
					.getNaziv_virt_ekipe());
			natjecatelj.setE_mail(natjecateljForma.getE_mail());
			Drzava drzava = drzavaDao.dohvatiPoImenu(natjecateljForma
					.getDrzava());
			natjecatelj.setDrzava(drzava);
			Ekipa ekipa = ekipaDao.dohvatiPoImenu(natjecateljForma
					.getPodupire_ekipu());
			natjecatelj.setPodupire_ekipu(ekipa);

			korisnik.setKorisnicko_ime(natjecateljForma.getKorisnicko_ime());
			korisnik.setLozinka(natjecateljForma.getLozinka());

			natjecatelj.setBodovi_virt_ekipe(0);
			RazinaPrava razinaPrava = razinaPravaDao
					.dohvatiPoImenu("natjecatelj");
			korisnik.setRazina_prava(razinaPrava);
			korisnikDao.spremi(korisnik);
			Korisnik korisnikOut = korisnikDao
					.dohvatiPoKorisnickomImenu(korisnik.getKorisnicko_ime());
			natjecatelj.setKorisnik_id(korisnikOut.getKorisnik_id());
			natjecateljDao.spremi(natjecatelj);

			List<Igrac> listaIgraca = new ArrayList<Igrac>();
			listaIgraca.add(organizator);
			listaIgraca.add(bek);
			listaIgraca.add(centar);
			listaIgraca.add(krilniCentar);
			listaIgraca.add(krilo);

			natjecatelj.getIgraciVirtualneEkipe().addAll(listaIgraca);
			natjecateljDao.osvjezi(natjecatelj);

			return "redirect:/";

		}

	}

	private void dodajPodatkeZaRegistraciju(ModelMap model) {
		Konfiguracija konfiguracija = konfiguracijaDao.dohvati();
		if (konfiguracija == null) {
			model.addAttribute("proracun", "0");
		} else {
			model.addAttribute("proracun", konfiguracija.getProracun()
					.toString());
		}

		List<Igrac> listaOrganizatora = igracDao.dohvatiPoUlozi("organizator");
		List<String> listaOrganizatoraStrings = new ArrayList<String>();
		for (Igrac igrac : listaOrganizatora) {
			listaOrganizatoraStrings.add("$" + igrac.getVrijednost() + ": "
					+ igrac.getIme_igrac() + " " + igrac.getPrezime_igrac()
					+ " - " + igrac.getEkipa().getIme_ekipa() + " "
					+ igrac.getBroj_dresa());
		}
		List<Igrac> listaBekova = igracDao.dohvatiPoUlozi("bek");
		List<String> listaBekovaStrings = new ArrayList<String>();
		for (Igrac igrac : listaBekova) {
			listaBekovaStrings.add("$" + igrac.getVrijednost() + ": "
					+ igrac.getIme_igrac() + " " + igrac.getPrezime_igrac()
					+ " - " + igrac.getEkipa().getIme_ekipa() + " "
					+ igrac.getBroj_dresa());
		}
		List<Igrac> listaKrila = igracDao.dohvatiPoUlozi("krilo");
		List<String> listaKrilaStrings = new ArrayList<String>();
		for (Igrac igrac : listaKrila) {
			listaKrilaStrings.add("$" + igrac.getVrijednost() + ": "
					+ igrac.getIme_igrac() + " " + igrac.getPrezime_igrac()
					+ " - " + igrac.getEkipa().getIme_ekipa() + " "
					+ igrac.getBroj_dresa());
		}
		List<Igrac> listaKrilnogCentra = igracDao
				.dohvatiPoUlozi("krilni centar");
		List<String> listaKrilnogCentraStrings = new ArrayList<String>();
		for (Igrac igrac : listaKrilnogCentra) {
			listaKrilnogCentraStrings.add("$" + igrac.getVrijednost() + ": "
					+ igrac.getIme_igrac() + " " + igrac.getPrezime_igrac()
					+ " - " + igrac.getEkipa().getIme_ekipa() + " "
					+ igrac.getBroj_dresa());
		}
		List<Igrac> listaCentra = igracDao.dohvatiPoUlozi("centar");
		List<String> listaCentraStrings = new ArrayList<String>();
		for (Igrac igrac : listaCentra) {
			listaCentraStrings.add("$" + igrac.getVrijednost() + ": "
					+ igrac.getIme_igrac() + " " + igrac.getPrezime_igrac()
					+ " - " + igrac.getEkipa().getIme_ekipa() + " "
					+ igrac.getBroj_dresa());
		}
		model.addAttribute("listaOrganizatora", listaOrganizatoraStrings);
		model.addAttribute("listaBekova", listaBekovaStrings);
		model.addAttribute("listaKrila", listaKrilaStrings);
		model.addAttribute("listaKrilnogCentra", listaKrilnogCentraStrings);
		model.addAttribute("listaCentra", listaCentraStrings);

		List<Ekipa> ekipe = ekipaDao.dohvatiSve();
		List<String> ekipeString = new ArrayList<String>();
		for (Ekipa ekipa : ekipe) {
			ekipeString.add(ekipa.getIme_ekipa());
		}

		model.addAttribute("ekipe", ekipeString);

		List<Drzava> drzave = drzavaDao.dohvatiSve();

		List<String> drzaveString = new ArrayList<String>();
		for (Drzava drzava : drzave) {
			drzaveString.add(drzava.getIme_drzava());
		}

		model.addAttribute("drzave", drzaveString);

	}

	private Integer parsirajEkipu(String igrac) {
		String temp = igrac.substring(igrac.indexOf("-") + 2);
		String imeEkipa = temp.substring(0, temp.lastIndexOf(" "));
		Ekipa ekipa = ekipaDao.dohvatiPoImenu(imeEkipa);
		return ekipa.getEkipa_id();
	}

	private Integer parsirajBrojDresa(String igrac) {
		String temp = igrac.substring(igrac.indexOf("-") + 2);
		String brojDresa = temp.substring(temp.lastIndexOf(" ") + 1);
		return Integer.parseInt(brojDresa);
	}

	public EkipaDao getEkipaDao() {
		return ekipaDao;
	}

	@Autowired(required = true)
	@Qualifier(value = "ekipaDao")
	public void setEkipaDao(EkipaDao ekipaDao) {
		this.ekipaDao = ekipaDao;
	}

	public IgracDao getIgracDao() {
		return igracDao;
	}

	@Autowired(required = true)
	@Qualifier(value = "igracDao")
	public void setIgracDao(IgracDao igracDao) {
		this.igracDao = igracDao;
	}

	public DrzavaDao getDrzavaDao() {
		return drzavaDao;
	}

	@Autowired(required = true)
	@Qualifier(value = "drzavaDao")
	public void setDrzavaDao(DrzavaDao drzavaDao) {
		this.drzavaDao = drzavaDao;
	}

	public KonfiguracijaDao getKonfiguracijaDao() {
		return konfiguracijaDao;
	}

	@Autowired(required = true)
	@Qualifier(value = "konfiguracijaDao")
	public void setKonfiguracijaDao(KonfiguracijaDao konfiguracijaDao) {
		this.konfiguracijaDao = konfiguracijaDao;
	}

	public NatjecateljFormaValidator getNatjecateljFormaValidator() {
		return natjecateljFormaValidator;
	}

	@Autowired(required = true)
	@Qualifier(value = "natjecateljFormaValidator")
	public void setNatjecateljFormaValidator(
			NatjecateljFormaValidator natjecateljFormaValidator) {
		this.natjecateljFormaValidator = natjecateljFormaValidator;
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

	public RazinaPravaDao getRazinaPravaDao() {
		return razinaPravaDao;
	}

	@Autowired(required = true)
	@Qualifier(value = "razinaPravaDao")
	public void setRazinaPravaDao(RazinaPravaDao razinaPravaDao) {
		this.razinaPravaDao = razinaPravaDao;
	}
	
	@Autowired(required = true)
	@Qualifier(value = "utakmicaDao")
	public void setUtakmicaDao(UtakmicaDao utakmicaDao){
		this.utakmicaDao = utakmicaDao;
	}
	
	public UtakmicaDao getUtakmicaDao(){
		return utakmicaDao;
	}
	
	@Autowired(required = true)
	@Qualifier(value = "igracUtakmicaDogadajDao")
	public void setIgracUtakmicaDogadajDao(IgracUtakmicaDogadajDao igracUtakmicaDogadajDao){
		this.igracUtakmicaDogadajDao = igracUtakmicaDogadajDao;
	}
	
	public IgracUtakmicaDogadajDao getIgracUtakmicaDogadajDao(){
		return igracUtakmicaDogadajDao;
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
