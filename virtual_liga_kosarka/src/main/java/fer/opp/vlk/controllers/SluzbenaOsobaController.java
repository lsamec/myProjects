package fer.opp.vlk.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
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
import fer.opp.vlk.model.IgracDogadajVrijemeForma;
import fer.opp.vlk.model.IgracUtakmicaDogadaj;
import fer.opp.vlk.model.Konfiguracija;
import fer.opp.vlk.model.KonfiguracijaForma;
import fer.opp.vlk.model.Utakmica;
import fer.opp.vlk.utils.KonfiguracijaFormaValidator;
import fer.opp.vlk.utils.Utils;

@Controller
public class SluzbenaOsobaController {

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

	private KonfiguracijaFormaValidator konfiguracijaFormaValidator;

	@InitBinder("konfiguracijaForma")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(konfiguracijaFormaValidator);
	}

	@RequestMapping(value = "/osobnaSlOsoba/postaviKonfiguraciju", method = RequestMethod.GET)
	public String postaviKonfiguraciju(ModelMap model) {

		KonfiguracijaForma konfiguracijaForma = new KonfiguracijaForma();
		Konfiguracija konf = konfiguracijaDao.dohvati();
		if (konf.getProracun() != null) {
			konfiguracijaForma.setProracun(konf.getProracun().toString());
		}
		List<Dogadaj> listaDogadaja = dogadajDao.dohvatiSve();
		for (Dogadaj dogadaj : listaDogadaja) {
			switch (dogadaj.getDogadaj_id()) {
			case 1:
				if (dogadaj.getVrijednost() != null) {
					konfiguracijaForma.setPostignutKos(dogadaj.getVrijednost()
							.toString());
				}
				break;
			case 2:
				if (dogadaj.getVrijednost() != null) {
					konfiguracijaForma.setKosIzSlobodnogBacanja(dogadaj
							.getVrijednost().toString());
				}
				break;
			case 3:
				if (dogadaj.getVrijednost() != null) {
					konfiguracijaForma.setTrica(dogadaj.getVrijednost()
							.toString());
				}
				break;
			case 4:
				if (dogadaj.getVrijednost() != null) {
					konfiguracijaForma.setSkok(dogadaj.getVrijednost()
							.toString());
				}
				break;
			case 5:
				if (dogadaj.getVrijednost() != null) {
					konfiguracijaForma.setOduzimanjeLopte(dogadaj
							.getVrijednost().toString());
				}
				break;
			case 6:
				if (dogadaj.getVrijednost() != null) {
					konfiguracijaForma.setDodavanje(dogadaj.getVrijednost()
							.toString());
				}
				break;
			case 7:
				if (dogadaj.getVrijednost() != null) {
					konfiguracijaForma.setBlokada(dogadaj.getVrijednost()
							.toString());
				}
				break;
			case 8:
				if (dogadaj.getVrijednost() != null) {
					konfiguracijaForma.setIgracUtakmice(dogadaj.getVrijednost()
							.toString());
				}
				break;
			case 9:
				if (dogadaj.getVrijednost() != null) {
					konfiguracijaForma.setIzgubljenaLopta(dogadaj
							.getVrijednost().toString());
				}
				break;
			case 10:
				if (dogadaj.getVrijednost() != null) {
					konfiguracijaForma.setPromasenoSlobodnoBacanje(dogadaj
							.getVrijednost().toString());
				}
				break;
			case 11:
				if (dogadaj.getVrijednost() != null) {
					konfiguracijaForma.setPromasenSutZaDva(dogadaj
							.getVrijednost().toString());
				}
				break;
			case 12:
				if (dogadaj.getVrijednost() != null) {
					konfiguracijaForma.setPromasenaTrica(dogadaj
							.getVrijednost().toString());
				}
				break;
			case 13:
				if (dogadaj.getVrijednost() != null) {
					konfiguracijaForma.setTehnickaPogreska(dogadaj
							.getVrijednost().toString());
				}
				break;
			case 14:
				if (dogadaj.getVrijednost() != null) {
					konfiguracijaForma.setOsobnaPogreska(dogadaj
							.getVrijednost().toString());
				}
				break;
			case 15:
				if (dogadaj.getVrijednost() != null) {
					konfiguracijaForma.setIskljucenje(dogadaj.getVrijednost()
							.toString());
				}
				break;

			}
		}
		model.addAttribute("konfiguracijaForma", konfiguracijaForma);

		return "unosKonfSlOsoba";
	}

	@RequestMapping(value = "/osobnaSlOsoba/unesiKonfiguraciju", method = RequestMethod.POST)
	public String mojiPodaci(
			@ModelAttribute("konfiguracijaForma") @Validated KonfiguracijaForma konfiguracijaForma,
			BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			return "unosKonfSlOsoba";
		} else {
			Konfiguracija konf = konfiguracijaDao.dohvati();
			List<Dogadaj> listaDogadaja = dogadajDao.dohvatiSve();
			for (Dogadaj dogadaj : listaDogadaja) {
				switch (dogadaj.getDogadaj_id()) {
				case 1:
					dogadaj.setVrijednost(Integer.parseInt(konfiguracijaForma
							.getProracun()));
					break;
				case 2:
					dogadaj.setVrijednost(Integer.parseInt(konfiguracijaForma
							.getKosIzSlobodnogBacanja()));
					break;
				case 3:
					dogadaj.setVrijednost(Integer.parseInt(konfiguracijaForma
							.getTrica()));
					break;
				case 4:
					dogadaj.setVrijednost(Integer.parseInt(konfiguracijaForma
							.getSkok()));
					break;
				case 5:
					dogadaj.setVrijednost(Integer.parseInt(konfiguracijaForma
							.getOduzimanjeLopte()));
					break;
				case 6:
					dogadaj.setVrijednost(Integer.parseInt(konfiguracijaForma
							.getDodavanje()));
					break;
				case 7:
					dogadaj.setVrijednost(Integer.parseInt(konfiguracijaForma
							.getBlokada()));
					break;
				case 8:
					dogadaj.setVrijednost(Integer.parseInt(konfiguracijaForma
							.getIgracUtakmice()));
					break;
				case 9:
					dogadaj.setVrijednost(Integer.parseInt(konfiguracijaForma
							.getIzgubljenaLopta()));
					break;
				case 10:
					dogadaj.setVrijednost(Integer.parseInt(konfiguracijaForma
							.getPromasenoSlobodnoBacanje()));
					break;
				case 11:
					dogadaj.setVrijednost(Integer.parseInt(konfiguracijaForma
							.getPromasenSutZaDva()));
					break;
				case 12:
					dogadaj.setVrijednost(Integer.parseInt(konfiguracijaForma
							.getPromasenaTrica()));
					break;
				case 13:
					dogadaj.setVrijednost(Integer.parseInt(konfiguracijaForma
							.getTehnickaPogreska()));
					break;
				case 14:
					dogadaj.setVrijednost(Integer.parseInt(konfiguracijaForma
							.getOsobnaPogreska()));
					break;
				case 15:
					dogadaj.setVrijednost(Integer.parseInt(konfiguracijaForma
							.getIskljucenje()));
					break;

				}
				dogadajDao.osvjezi(dogadaj);
			}
			konf.pseudoKonstruktor(
					Integer.parseInt(konfiguracijaForma.getProracun()), 1,
					false, false, false, false);
			konfiguracijaDao.osvjezi(konf);
			return "redirect:/osobnaSlOsoba";
		}
	}

	@RequestMapping(value = "/osobnaSlOsoba/uredivanjeUtakmica", method = RequestMethod.GET)
	public String uredivanje(ModelMap model) {
		List<Utakmica> utakmica = utakmicaDao.dohvatiSve();
		model.addAttribute("Utakmica", utakmica);
		return "uredivanjeUtakmica";
	}

	@RequestMapping(value = "/osobnaSlOsoba/dogadaji", method = RequestMethod.GET)
	public String dogadaji(@RequestParam("utakmica") Integer utakmicaId,
			ModelMap model) {
		Utakmica utakmica = utakmicaDao.dohvatiPoId(utakmicaId);
		List<Igrac> listaDomacihIgraca = igracDao.dohvatiPoEkipi(utakmica
				.getEkipa_domacin().getEkipa_id());
		List<Igrac> listaIgracaGosta = igracDao.dohvatiPoEkipi(utakmica.getEkipa_gost()
				.getEkipa_id());
		List<IgracUtakmicaDogadaj> listaIgracUtakmicaDogadaj = igracUtakmicaDogadajDao.dohvatiPoUtakmici(utakmicaId);
		StringBuilder iud = new StringBuilder();
		for(IgracUtakmicaDogadaj igracUtakmicaDogadaj : listaIgracUtakmicaDogadaj){
			Igrac igrac = igracUtakmicaDogadaj.getIgrac();
			Boolean domaci = false;
			for(Igrac domaciIgrac : listaDomacihIgraca){
				if(domaciIgrac.getIgrac_id().equals(igrac.getIgrac_id())){
					domaci = true;
				}
			}
			if(domaci){
				iud.append("D");
			}else{
				iud.append("G");
			}
			iud.append(igrac.getBroj_dresa().toString() + " ");
			Dogadaj dogadaj = igracUtakmicaDogadaj.getDogadaj();
			if(dogadaj.getDogadaj_id() != 8){
				iud.append(dogadaj.getDogadaj_id().toString()+ " ");
			}
			iud.append(igracUtakmicaDogadaj.getVrijeme()+ "\n");
		}
		
		List<Dogadaj> listaDogadaja = dogadajDao.dohvatiSve();
		List<Dogadaj> dostupniDogadaji = new ArrayList<Dogadaj>();
		for (Dogadaj dogadaj : listaDogadaja) {
			if (dogadaj.getDogadaj_id() != 8) {
				dostupniDogadaji.add(dogadaj);
			}
		}
		model.addAttribute("utakmica", utakmica);
		model.addAttribute("igracD", listaDomacihIgraca);
		model.addAttribute("igracG", listaIgracaGosta);
		model.addAttribute("dogadaj", dostupniDogadaji);
		model.addAttribute("uneseniDogadaji", iud.toString());
		return "dogadaji";
	}

	@RequestMapping(value = "/osobnaSlOsoba/unesiDogadajeUtakmice", method = RequestMethod.POST)
	public String unesiDogadajeUtakmice(
			@RequestParam("utakmicaId") Integer utakmicaId,
			@RequestParam("dogadaji") String dogadaji, ModelMap model){
		
		List<IgracDogadajVrijemeForma> listaDogadajForma = new ArrayList<IgracDogadajVrijemeForma>();
		List<IgracUtakmicaDogadaj> listaIgracUtakmicaDogadaj = new ArrayList<IgracUtakmicaDogadaj>();
		Utakmica utakmica = utakmicaDao.dohvatiPoId(utakmicaId);
		Ekipa gostEkipa = utakmica.getEkipa_gost();
		Ekipa domacinEkipa = utakmica.getEkipa_domacin();
		Integer domacinKosevi = 0;
		Integer gostKosevi = 0;
		try {
			List<String> listaDogadaja = new ArrayList<String>(
					Arrays.asList(dogadaji.split("\\r?\\n")));
			for (String dogadaj : listaDogadaja) {
				if(!dogadaj.isEmpty()){
				String[] dogadajArray = dogadaj.split(" ");
				listaDogadajForma.add(new IgracDogadajVrijemeForma(
						dogadajArray[0], dogadajArray[1], dogadajArray[2]));
				}
			}
			// provjera
			for (IgracDogadajVrijemeForma igracUtakmicaDogadajForma : listaDogadajForma) {
				String domIlGost = igracUtakmicaDogadajForma.getIgrac()
						.substring(0, 1);
				String brojDresa = igracUtakmicaDogadajForma.getIgrac()
						.substring(1, igracUtakmicaDogadajForma.getIgrac().length());
				if (!(domIlGost.equals("D") || domIlGost.equals("G"))) {
					throw new Exception();
				}

				Igrac igrac = null;
				if (domIlGost.equals("D")) {
					igrac = igracDao.dohvatiPoEkipiIBrojuDresa(utakmica
							.getEkipa_domacin().getEkipa_id(), Integer
							.parseInt(brojDresa));
				}
				if (domIlGost.equals("G")) {
					igrac = igracDao.dohvatiPoEkipiIBrojuDresa(utakmica
							.getEkipa_gost().getEkipa_id(), Integer
							.parseInt(brojDresa));
				}
				if (igrac == null) {
					throw new Exception();
				}
				Integer idDogadaja = Integer.parseInt(igracUtakmicaDogadajForma
						.getDogadaj());
				if (idDogadaja >= 8) {
					idDogadaja++;
				}
				Dogadaj dogadaj = dogadajDao.dohvatiPoId(idDogadaja);
				if (dogadaj == null) {
					throw new Exception();
				}
				String vrijeme = igracUtakmicaDogadajForma.getVrijeme();
				String[] secMin = vrijeme.split(":");
				Integer.parseInt(secMin[0]);
				Integer.parseInt(secMin[1]);
			}
		} catch (Exception e) {
			model.addAttribute("utakmica", utakmica);
			model.addAttribute("poruka", "Pogreska pri unosu.");
			return "unosDogadajaPogreska";
		}
		for (IgracDogadajVrijemeForma igracUtakmicaDogadajForma : listaDogadajForma) {
			String domIlGost = igracUtakmicaDogadajForma.getIgrac().substring(
					0, 1);
			String brojDresa = igracUtakmicaDogadajForma.getIgrac().substring(
					1, igracUtakmicaDogadajForma.getIgrac().length());
			Igrac igrac = null;
			if (domIlGost.equals("D")) {
				igrac = igracDao.dohvatiPoEkipiIBrojuDresa(utakmica
						.getEkipa_domacin().getEkipa_id(), Integer
						.parseInt(brojDresa));
			}
			if (domIlGost.equals("G")) {
				igrac = igracDao.dohvatiPoEkipiIBrojuDresa(utakmica
						.getEkipa_gost().getEkipa_id(), Integer
						.parseInt(brojDresa));
			}
			Integer idDogadaja = Integer.parseInt(igracUtakmicaDogadajForma
					.getDogadaj());
			if (idDogadaja >= 8) {
				idDogadaja++;
			}
			Dogadaj dogadaj = dogadajDao.dohvatiPoId(idDogadaja);
			if (dogadaj.getDogadaj_id().equals(1)) {
				if (domIlGost.equals("D")) {
					domacinKosevi = domacinKosevi + 2;
				}
				if (domIlGost.equals("G")) {
					gostKosevi = gostKosevi + 2;
				}
			}
			if (dogadaj.getDogadaj_id().equals(2)) {
				if (domIlGost.equals("D")) {
					domacinKosevi = domacinKosevi + 1;
				}
				if (domIlGost.equals("G")) {
					gostKosevi = gostKosevi + 1;
				}
			}
			if (dogadaj.getDogadaj_id().equals(3)) {
				if (domIlGost.equals("D")) {
					domacinKosevi = domacinKosevi + 3;
				}
				if (domIlGost.equals("G")) {
					gostKosevi = gostKosevi + 3;
				}
			}
			IgracUtakmicaDogadaj igracUtakmicaDogadaj = new IgracUtakmicaDogadaj();
			igracUtakmicaDogadaj.setIgrac(igrac);
			igracUtakmicaDogadaj.setUtakmica(utakmica);
			igracUtakmicaDogadaj.setDogadaj(dogadaj);
			igracUtakmicaDogadaj.setVrijeme(igracUtakmicaDogadajForma
					.getVrijeme());
			listaIgracUtakmicaDogadaj.add(igracUtakmicaDogadaj);

		}
		if (domacinKosevi.equals(gostKosevi)) {
			model.addAttribute("utakmica", utakmica);
			model.addAttribute("poruka", "Ne moze biti nerjesen rezultat.");
			return "unosDogadajaPogreska";
		}
		List<IgracUtakmicaDogadaj> listaStariIgracUtakmicaDogadaj = igracUtakmicaDogadajDao
				.dohvatiPoUtakmici(utakmicaId);
		for (IgracUtakmicaDogadaj stariIgracUtakmicaDogadaj : listaStariIgracUtakmicaDogadaj) {
			Igrac igrac = igracDao.dohvatiPoId(stariIgracUtakmicaDogadaj.getIgrac().getIgrac_id());
			igrac.setUk_bodovi(igrac.getUk_bodovi()
					- stariIgracUtakmicaDogadaj.getDogadaj().getVrijednost());
			igracDao.osvjezi(igrac);
			igracUtakmicaDogadajDao.obrisi(stariIgracUtakmicaDogadaj);
		}
		if(utakmica.getRezultat() !=null){
			String[] kosevi = utakmica.getRezultat().split(":");
			Integer domKos = Integer.parseInt(kosevi[0]);
			Integer gosKos = Integer.parseInt(kosevi[1]);
			if(domKos > gosKos){
				gostEkipa.setIzgubljene(gostEkipa.getIzgubljene() - 1);
				domacinEkipa.setPobjedene(domacinEkipa.getPobjedene() - 1);

			} else {
				domacinEkipa.setIzgubljene(domacinEkipa.getIzgubljene() - 1);
				gostEkipa.setPobjedene(gostEkipa.getPobjedene() - 1);
			}
		}
		for (IgracUtakmicaDogadaj igracUtakmicaDogadaj : listaIgracUtakmicaDogadaj) {
			Igrac igrac = igracDao.dohvatiPoId(igracUtakmicaDogadaj.getIgrac().getIgrac_id());
			igrac.setUk_bodovi(igrac.getUk_bodovi()
					+ igracUtakmicaDogadaj.getDogadaj().getVrijednost());
			igracDao.osvjezi(igrac);
			igracUtakmicaDogadajDao.spremi(igracUtakmicaDogadaj);
		}

		
		if (domacinKosevi > gostKosevi) {
			gostEkipa.setIzgubljene(gostEkipa.getIzgubljene() + 1);
			domacinEkipa.setPobjedene(domacinEkipa.getPobjedene() + 1);

		} else {
			domacinEkipa.setIzgubljene(domacinEkipa.getIzgubljene() + 1);
			gostEkipa.setPobjedene(gostEkipa.getPobjedene() + 1);
		}

		ekipaDao.osvjezi(gostEkipa);
		ekipaDao.osvjezi(domacinEkipa);
		utakmica.setRezultat(domacinKosevi.toString() + ":"
				+ gostKosevi.toString());
		utakmicaDao.osvjezi(utakmica);

		Utils.osvjeziBodoveNatjecatelja(igracDao, natjecateljDao);

		return "redirect:/osobnaSlOsoba";
	}

	@RequestMapping(value = "/osobnaSlOsoba/postaviVrijednostiIgraca", method = RequestMethod.GET)
	public String postaviVrijednostiIgraca(ModelMap model) {
		List<Igrac> igraci = igracDao.dohvatiSve();
		String vrijednostiString = "";
		for (Igrac igrac : igraci) {
			if (igrac.getVrijednost() != null) {
				vrijednostiString = vrijednostiString + igrac.getIgrac_id()
						+ ":" + igrac.getVrijednost() + ";";
			}
		}
		if (!vrijednostiString.isEmpty()) {
			vrijednostiString = vrijednostiString.substring(0,
					vrijednostiString.length() - 1);
		}
		model.addAttribute("vrijednosti_string", vrijednostiString);
		model.addAttribute("igraci", igraci);
		return "postaviCijene";
	}

	@RequestMapping(value = "/osobnaSlOsoba/upisiVrijednostiIgraca", method = RequestMethod.POST)
	public String upisiVrijednostiIgraca(
			@RequestParam("vrijednosti") String vrijednostiString,
			ModelMap model) {
		Konfiguracija konf = konfiguracijaDao.dohvati();
		List<String> paroviIdVrijednost = Arrays.asList(vrijednostiString
				.split(";"));
		for (String parIdVrijednost : paroviIdVrijednost) {
			String id = parIdVrijednost.split(":")[0];
			String vrijednost = parIdVrijednost.split(":")[1];
			Igrac igrac = igracDao.dohvatiPoId(Integer.parseInt(id));
			igrac.setVrijednost(Integer.parseInt(vrijednost));
			igracDao.osvjezi(igrac);
		}
		List<Igrac> listaIgraca = igracDao.dohvatiSve();
		Boolean sviIgraciImajuVrijedost = true;
		for (Igrac igrac : listaIgraca) {
			if (igrac.getVrijednost() == null) {
				sviIgraciImajuVrijedost = false;
			}
		}
		if (sviIgraciImajuVrijedost) {
			konf.setIgraci_ok(true);
		} else {
			konf.setIgraci_ok(false);
		}
		konfiguracijaDao.osvjezi(konf);
		return "redirect:/osobnaSlOsoba";
	}

	/*
	 * @RequestMapping("/osobnaSlOsoba/dogadaji") public ModelAndView test(){
	 * return new ModelAndView("ajax", "message",
	 * "Crunchify Spring MVC with Ajax and JQuery Demo.."); }
	 * 
	 * @RequestMapping(value = "/ajaxtest", method = RequestMethod.GET) public
	 * 
	 * @ResponseBody String getTime() {
	 * 
	 * Random rand = new Random(); float r = rand.nextFloat() * 100; String
	 * result = "<br>Next Random # is <b>" + r + "</b>. Generated on <b>" + new
	 * Date().toString() + "</b>"; System.out.println(
	 * "Debug Message from CrunchifySpringAjaxJQuery Controller.." + new
	 * Date().toString()); return result; }
	 */

	// @RequestMappping(value="/osobnaSlOsoba/dogadaji", method =
	// RequestMethod.GET)
	// ******************************************************************************************
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

	public KonfiguracijaFormaValidator getKonfiguracijaFormaValidator() {
		return konfiguracijaFormaValidator;
	}

	@Autowired(required = true)
	@Qualifier(value = "konfiguracijaFormaValidator")
	public void setKonfiguracijaFormaValidator(
			KonfiguracijaFormaValidator konfiguracijaFormaValidator) {
		this.konfiguracijaFormaValidator = konfiguracijaFormaValidator;
	}

	@ModelAttribute("username")
	private String dodajAtributKorisnika(ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String username = null;
		if (auth != null && auth.isAuthenticated()
				&& !(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			username = userDetail.getUsername();
		}
		return username;
	}

}
