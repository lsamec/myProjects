package fer.opp.vlk.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
import fer.opp.vlk.model.BrojUtakmicaForma;
import fer.opp.vlk.model.Dogadaj;
import fer.opp.vlk.model.Ekipa;
import fer.opp.vlk.model.Igrac;
import fer.opp.vlk.model.IgracUtakmicaDogadaj;
import fer.opp.vlk.model.Konfiguracija;
import fer.opp.vlk.model.Korisnik;
import fer.opp.vlk.model.Natjecatelj;
import fer.opp.vlk.model.Utakmica;
import fer.opp.vlk.utils.FileReader;
import fer.opp.vlk.utils.Utils;

@Controller
public class AdminController {

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

	@RequestMapping(value = "/osobnaAdmin/simulirajNekolikoUtakmica", method = RequestMethod.POST)
	public String simulirajUtakmicu(
			@ModelAttribute("brojUtakmicaForma") BrojUtakmicaForma brojUtakmicaForma,
			ModelMap model) {

		List<Dogadaj> listaDogadaja = dogadajDao.dohvatiSve();
		Integer listaDogadajaSize = listaDogadaja.size();
		Map<Integer, Dogadaj> mapaDogadaja = new HashMap<Integer, Dogadaj>();
		for (int i = 0; i < listaDogadajaSize; i++) {
			Dogadaj dogadaj = listaDogadaja.get(i);
			mapaDogadaja.put(i + 1, dogadaj);
		}

		Konfiguracija konfiguracija = konfiguracijaDao.dohvati();
		Integer pocetni_status_simulatora = konfiguracija
				.getStatus_simulatora();
		Integer brojUtakmica = Integer.parseInt(brojUtakmicaForma
				.getBrojUtakmica());

		for (int brojUtakmice = pocetni_status_simulatora; brojUtakmice < pocetni_status_simulatora
				+ brojUtakmica; brojUtakmice++) {

			Utakmica utakmica = utakmicaDao.dohvatiPoId(brojUtakmice);

			Ekipa gostEkipa = utakmica.getEkipa_gost();
			Ekipa domacinEkipa = utakmica.getEkipa_domacin();

			List<Igrac> gostIgraci = new LinkedList<Igrac>(
					gostEkipa.getIgraciEkipe());
			List<Igrac> domacinIgraci = new LinkedList<Igrac>(
					domacinEkipa.getIgraciEkipe());
			Collections.shuffle(gostIgraci);
			Collections.shuffle(domacinIgraci);

			Map<Integer, Integer> igracIdBodovi = new HashMap<Integer, Integer>();

			Integer gostKosevi = 0;
			Integer domacinKosevi = 0;

			Integer gostiKvaliteta = 0;
			Integer domacinKvaliteta = 0;

			Map<String, List<Igrac>> gostiMapa = new HashMap<String, List<Igrac>>();
			Map<String, List<Igrac>> domaciniMapa = new HashMap<String, List<Igrac>>();

			gostiMapa.put("organizator", new LinkedList<Igrac>());
			gostiMapa.put("bek", new LinkedList<Igrac>());
			gostiMapa.put("krilo", new LinkedList<Igrac>());
			gostiMapa.put("krilni centar", new LinkedList<Igrac>());
			gostiMapa.put("centar", new LinkedList<Igrac>());

			domaciniMapa.put("organizator", new LinkedList<Igrac>());
			domaciniMapa.put("bek", new LinkedList<Igrac>());
			domaciniMapa.put("krilo", new LinkedList<Igrac>());
			domaciniMapa.put("krilni centar", new LinkedList<Igrac>());
			domaciniMapa.put("centar", new LinkedList<Igrac>());

			for (Igrac igrac : gostIgraci) {
				List<Igrac> lista = gostiMapa.get(igrac.getPozicija()
						.getIme_pozicija());
				igracIdBodovi.put(igrac.getIgrac_id(), 0);
				lista.add(igrac);
			}
			for (Igrac igrac : domacinIgraci) {
				List<Igrac> lista = domaciniMapa.get(igrac.getPozicija()
						.getIme_pozicija());
				igracIdBodovi.put(igrac.getIgrac_id(), 0);
				lista.add(igrac);
			}

			for (List<Igrac> lista : gostiMapa.values()) {
				gostiKvaliteta += lista.get(0).getVrijednost();
			}

			for (List<Igrac> lista : domaciniMapa.values()) {
				domacinKvaliteta += lista.get(0).getVrijednost();
			}

			for (int sec = 1; sec <= 2400; sec++) {

				Integer hitNumber = Utils.rand.nextInt(1000);

				if (hitNumber < 13) { // kos za 2
					Integer ekipaHitNumber = Utils.rand.nextInt(gostiKvaliteta
							+ domacinKvaliteta);
					Dogadaj dogadaj = mapaDogadaja.get(1);
					Integer igracHitNumberBound = 0;
					Map<String, List<Igrac>> mapaIgraca;
					if (ekipaHitNumber < gostiKvaliteta) {
						mapaIgraca = gostiMapa;
						gostKosevi += 2;
						igracHitNumberBound = gostiKvaliteta;
					} else {
						mapaIgraca = domaciniMapa;
						domacinKosevi += 2;
						igracHitNumberBound = domacinKvaliteta;
					}

					Igrac igrac = Utils.izaberiIgracaProp(mapaIgraca,
							igracHitNumberBound);
					Utils.stvoriDogadajIgraca(igracUtakmicaDogadajDao, sec,
							dogadaj, utakmica, igrac, igracIdBodovi);
				}

				if (hitNumber < 13 + 8 && hitNumber >= 13) { // trica
					Integer ekipaHitNumber = Utils.rand.nextInt(gostiKvaliteta
							+ domacinKvaliteta);
					Dogadaj dogadaj = mapaDogadaja.get(3);
					Integer igracHitNumberBound;
					Map<String, List<Igrac>> mapaIgraca;
					if (ekipaHitNumber < gostiKvaliteta) {
						mapaIgraca = gostiMapa;
						gostKosevi += 3;
						igracHitNumberBound = gostiKvaliteta;
					} else {
						mapaIgraca = domaciniMapa;
						domacinKosevi += 3;
						igracHitNumberBound = domacinKvaliteta;
					}

					Igrac igrac = Utils.izaberiIgracaProp(mapaIgraca,
							igracHitNumberBound);
					Utils.stvoriDogadajIgraca(igracUtakmicaDogadajDao, sec,
							dogadaj, utakmica, igrac, igracIdBodovi);
				}

				if (hitNumber < 13 + 8 + 417 && hitNumber >= 13 + 8) {// skok,oduzimanje
																		// lopte,dodavanje,blokada
					Integer dogadajHitNumber = Utils.rand.nextInt(4) + 4;
					Dogadaj dogadaj = mapaDogadaja.get(dogadajHitNumber);
					Integer ekipaHitNumber = Utils.rand.nextInt(gostiKvaliteta
							+ domacinKvaliteta);
					Integer igracHitNumberBound;
					Map<String, List<Igrac>> mapaIgraca;
					if (ekipaHitNumber < gostiKvaliteta) {
						mapaIgraca = gostiMapa;
						igracHitNumberBound = gostiKvaliteta;
					} else {
						mapaIgraca = domaciniMapa;
						igracHitNumberBound = domacinKvaliteta;
					}
					Igrac igrac = Utils.izaberiIgracaProp(mapaIgraca,
							igracHitNumberBound);
					Utils.stvoriDogadajIgraca(igracUtakmicaDogadajDao, sec,
							dogadaj, utakmica, igrac, igracIdBodovi);
				}

				if (hitNumber < 13 + 8 + 417 + 83 && hitNumber >= 13 + 8 + 417) { // greske
					Integer dogadajHitNumber = Utils.rand.nextInt(4);
					if (dogadajHitNumber >= 1) {
						dogadajHitNumber += 10;
					} else {
						dogadajHitNumber += 9;
					}
					Dogadaj dogadaj = mapaDogadaja.get(dogadajHitNumber);
					Integer ekipaHitNumber = Utils.rand.nextInt(2);
					Integer igracHitNumberBound = 5;
					Map<String, List<Igrac>> mapaIgraca = null;
					if (ekipaHitNumber.equals(0)) {
						mapaIgraca = gostiMapa;
					} else {
						mapaIgraca = domaciniMapa;
					}

					Igrac igrac = Utils.izaberiIgracaJednakeSanse(mapaIgraca,
							igracHitNumberBound);
					Utils.stvoriDogadajIgraca(igracUtakmicaDogadajDao, sec,
							dogadaj, utakmica, igrac, igracIdBodovi);

					if (dogadaj.getIme_dogadaj().equals("tehnicka_pogreska")) {

						Integer slobodnoBacHitNumber = Utils.rand.nextInt(3);
						if (slobodnoBacHitNumber <= 0) {
							Igrac pucac = null;
							Boolean domaciniPucaju = null;
							if (Utils.jeUPostavi(igrac, domaciniMapa)) {
								pucac = Utils.izaberiIgracaProp(gostiMapa,
										igracHitNumberBound);
								domaciniPucaju = false;
							} else {
								pucac = Utils.izaberiIgracaProp(domaciniMapa,
										igracHitNumberBound);
								domaciniPucaju = true;
							}
							Integer pogodak1HitNumber = Utils.rand.nextInt(3);
							if (pogodak1HitNumber <= 1) {
								Utils.stvoriDogadajIgraca(
										igracUtakmicaDogadajDao, sec,
										mapaDogadaja.get(2), utakmica, pucac,
										igracIdBodovi);
								if (domaciniPucaju) {
									domacinKosevi += 1;
								} else {
									gostKosevi += 1;
								}
							} else {
								Utils.stvoriDogadajIgraca(
										igracUtakmicaDogadajDao, sec,
										mapaDogadaja.get(10), utakmica, pucac,
										igracIdBodovi);
							}
							Integer pogodak2HitNumber = Utils.rand.nextInt(3);
							if (pogodak2HitNumber <= 1) {
								Utils.stvoriDogadajIgraca(
										igracUtakmicaDogadajDao, sec,
										mapaDogadaja.get(2), utakmica, pucac,
										igracIdBodovi);
								if (domaciniPucaju) {
									domacinKosevi += 1;
								} else {
									gostKosevi += 1;
								}
							} else {
								Utils.stvoriDogadajIgraca(
										igracUtakmicaDogadajDao, sec,
										mapaDogadaja.get(10), utakmica, pucac,
										igracIdBodovi);

							}
						}
						Integer iskljucenjeHitNumber = Utils.rand.nextInt(10);
						if (iskljucenjeHitNumber <= 0) {
							if (Utils.iskljuciIgraca(mapaIgraca, igrac)) {
								Utils.stvoriDogadajIgraca(
										igracUtakmicaDogadajDao, sec,
										mapaDogadaja.get(15), utakmica, igrac,
										igracIdBodovi);
								if (Utils.jeUPostavi(igrac, domaciniMapa)) {
									domacinKvaliteta = 0;
									for (List<Igrac> lista : domaciniMapa
											.values()) {
										domacinKvaliteta += lista.get(0)
												.getVrijednost();
									}
								} else {
									gostiKvaliteta = 0;
									for (List<Igrac> lista : gostiMapa.values()) {
										gostiKvaliteta += lista.get(0)
												.getVrijednost();
									}
								}
							}
						}
					}
				}

				if (hitNumber < 13 + 8 + 417 + 83 + 19
						&& hitNumber >= 13 + 8 + 417 + 83) {// osobne pogreske
					Dogadaj dogadaj = mapaDogadaja.get(14);
					Integer ekipaHitNumber = Utils.rand.nextInt(2);
					Integer igracHitNumberBound = 5;
					Map<String, List<Igrac>> mapaIgraca;
					if (ekipaHitNumber.equals(0)) {
						mapaIgraca = gostiMapa;
					} else {
						mapaIgraca = domaciniMapa;
					}

					Igrac igrac = Utils.izaberiIgracaJednakeSanse(mapaIgraca,
							igracHitNumberBound);
					Utils.stvoriDogadajIgraca(igracUtakmicaDogadajDao, sec,
							dogadaj, utakmica, igrac, igracIdBodovi);
				}

			}
			if (domacinKosevi.equals(gostKosevi)) {
				Integer ekipaHitNumber = Utils.rand.nextInt(gostiKvaliteta
						+ domacinKvaliteta);
				Dogadaj dogadaj = mapaDogadaja.get(1);
				Integer igracHitNumberBound = 0;
				Map<String, List<Igrac>> mapaIgraca;
				if (ekipaHitNumber < gostiKvaliteta) {
					mapaIgraca = gostiMapa;
					gostKosevi += 2;
					igracHitNumberBound = gostiKvaliteta;
				} else {
					mapaIgraca = domaciniMapa;
					domacinKosevi += 2;
					igracHitNumberBound = domacinKvaliteta;
				}

				Igrac igrac = Utils.izaberiIgracaProp(mapaIgraca,
						igracHitNumberBound);
				Utils.stvoriDogadajIgraca(igracUtakmicaDogadajDao, 2400,
						dogadaj, utakmica, igrac, igracIdBodovi);
			}

			Igrac igracUtakmice = null;
			Boolean nasaoIgracaUtakmice = false;
			Entry<Integer, Integer> igracUtakmiceEntry = Collections.max(
					igracIdBodovi.entrySet(),
					new Comparator<Entry<Integer, Integer>>() {

						@Override
						public int compare(Entry<Integer, Integer> o1,
								Entry<Integer, Integer> o2) {

							Integer b1 = o1.getValue();
							Integer b2 = o2.getValue();

							return b1.compareTo(b2);
						}
					});
			for (Igrac igrac : gostIgraci) {
				if (igrac.getIgrac_id().equals(igracUtakmiceEntry.getKey())) {
					igracUtakmice = igrac;
					nasaoIgracaUtakmice = true;
					break;
				}
			}
			if (!nasaoIgracaUtakmice) {
				for (Igrac igrac : domacinIgraci) {
					if (igrac.getIgrac_id().equals(igracUtakmiceEntry.getKey())) {
						igracUtakmice = igrac;
						break;
					}
				}
			}
			utakmica.setIgrac_utakmice(igracUtakmice);
			Utils.stvoriDogadajIgraca(igracUtakmicaDogadajDao, 2400,
					mapaDogadaja.get(8), utakmica, igracUtakmice, igracIdBodovi);

			if (domacinKosevi > gostKosevi) {
				gostEkipa.setIzgubljene(gostEkipa.getIzgubljene() + 1);
				domacinEkipa.setPobjedene(domacinEkipa.getPobjedene() + 1);

			} else {
				domacinEkipa.setIzgubljene(domacinEkipa.getIzgubljene() + 1);
				gostEkipa.setPobjedene(gostEkipa.getPobjedene() + 1);
			}

			for (Igrac igrac : gostIgraci) {
				igracDao.osvjezi(igrac);
			}
			for (Igrac igrac : domacinIgraci) {
				igracDao.osvjezi(igrac);
			}

			ekipaDao.osvjezi(gostEkipa);
			ekipaDao.osvjezi(domacinEkipa);
			utakmica.setRezultat(domacinKosevi.toString() + ":"
					+ gostKosevi.toString());
			utakmicaDao.osvjezi(utakmica);

			Utils.osvjeziBodoveNatjecatelja(igracDao, natjecateljDao);

			konfiguracija.setStatus_simulatora(brojUtakmice + 1);
			konfiguracijaDao.osvjezi(konfiguracija);

		}

		return "redirect:/osobnaAdmin";

	}

	@RequestMapping(value = "/osobnaAdmin/ucitajSastavEkipa", method = RequestMethod.GET)
	public String ucitajSastavEkipa() {
		return "uploadSastavEkipa";
	}

	@RequestMapping(value = "/osobnaAdmin/simulirajUtakmice", method = RequestMethod.GET)
	public String simulirajUtakmice(ModelMap model) {
		BrojUtakmicaForma brojUtakmicaForma = new BrojUtakmicaForma();
		model.addAttribute("brojUtakmicaForma", brojUtakmicaForma);

		Konfiguracija konfiguracija = konfiguracijaDao.dohvati();
		model.addAttribute("status_simulatora",
				konfiguracija.getStatus_simulatora());
		return "simulirajUtakmice";
	}

	@RequestMapping(value = "/osobnaAdmin/ucitajKonfiguraciju", method = RequestMethod.GET)
	public String uploadInfoKonf() {
		return "uploadKonf";
	}

	@RequestMapping(value = "/osobnaAdmin/ucitajProbnePodatke", method = RequestMethod.GET)
	public String uploadProbniPodaci() {
		return "uploadProbniPodaci";
	}

	@RequestMapping(value = "/osobnaAdmin/uploadSastavEkipa", method = RequestMethod.POST)
	public String obradiUploadaniFileSastavEkipa(
			@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				FileOutputStream fileOuputStream = new FileOutputStream(
						"liga.xls");
				fileOuputStream.write(bytes);
				fileOuputStream.close();
				File fileOnDisk = new File("liga.xls");
				FileReader.spremiSastavEkipaBezVrijednosti(fileOnDisk,
						pozicijaDao, ekipaDao, igracDao);
				Konfiguracija konfiguracija = konfiguracijaDao.dohvati();
				konfiguracija.setPostoji_sastav_ekipa(true);
				konfiguracijaDao.osvjezi(konfiguracija);

				return "redirect:/osobnaAdmin";
			} catch (Exception e) {
				e.printStackTrace();
				return "redirect:/osobnaAdmin/neuspioUpload/"
						+ "datoteka nije u odgovarajucem formatu";
			}
		} else {
			return "redirect:/osobnaAdmin/neuspioUpload/"
					+ "datoteka je prazna";
		}
	}

	@RequestMapping(value = "/osobnaAdmin/uploadKonfiguraciju", method = RequestMethod.POST)
	public String obradiUploadaniFileKonf(
			@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				FileOutputStream fileOuputStream = new FileOutputStream(
						"konfiguracija.txt");
				fileOuputStream.write(bytes);
				fileOuputStream.close();
				File fileOnDisk = new File("konfiguracija.txt");
				FileReader.spremiKonfiguraciju(fileOnDisk, dogadajDao,
						konfiguracijaDao);
				return "redirect:/osobnaAdmin";
			} catch (Exception e) {
				e.printStackTrace();
				return "redirect:/osobnaAdmin/neuspioUpload/"
						+ "datoteka nije u odgovarajucem formatu";
			}
		} else {
			return "redirect:/osobnaAdmin/neuspioUpload/"
					+ "datoteka je prazna";
		}
	}

	@RequestMapping(value = "/osobnaAdmin/neuspioUpload/{message}", method = RequestMethod.GET)
	public String neuspioUpload(@PathVariable("message") String message,
			ModelMap model) {

		model.addAttribute("message", message);

		return "neuspioUpload";

	}

	@RequestMapping(value = "/osobnaAdmin/odobriKonfiguraciju", method = RequestMethod.POST)
	public String odobriKonfiguraciju(ModelMap model) {

		Konfiguracija konfiguracija = konfiguracijaDao.dohvati();
		konfiguracija.setOdobren_konf(true);
		konfiguracijaDao.osvjezi(konfiguracija);

		return "redirect:/osobnaAdmin";

	}

	@RequestMapping(value = "/osobnaAdmin/odobriRegistraciju", method = RequestMethod.POST)
	public String odobriReg(ModelMap model) {

		Konfiguracija konfiguracija = konfiguracijaDao.dohvati();
		konfiguracija.setOdobrena_reg(!konfiguracija.getOdobrena_reg());
		konfiguracijaDao.osvjezi(konfiguracija);

		return "redirect:/osobnaAdmin";

	}

	@RequestMapping(value = "/osobnaAdmin/zapocniLigu", method = RequestMethod.POST)
	public String zapocniLigu(ModelMap model) {

		Konfiguracija konfiguracija = konfiguracijaDao.dohvati();
		konfiguracija.setOdobrena_reg(false);
		konfiguracija.setZapoceta_liga(true);
		konfiguracijaDao.osvjezi(konfiguracija);

		return "redirect:/osobnaAdmin";

	}

	@RequestMapping(value = "/osobnaAdmin/obrisiKonfiguraciju", method = RequestMethod.POST)
	public String obrisiKonfiguraciju(ModelMap model) {

		Konfiguracija konfiguracija = konfiguracijaDao.dohvati();
		konfiguracija.obrisi();
		konfiguracijaDao.osvjezi(konfiguracija);

		List<Dogadaj> listaDogadaja = dogadajDao.dohvatiSve();
		for (Dogadaj dogadaj : listaDogadaja) {
			dogadaj.setVrijednost(null);
			dogadajDao.osvjezi(dogadaj);
		}

		return "redirect:/osobnaAdmin";
	}

	@RequestMapping(value = "/osobnaAdmin/globalniReset", method = RequestMethod.POST)
	public String globalniReset(ModelMap model) {

		List<Natjecatelj> listaNatjecatelja = natjecateljDao.dohvatiSve();
		for (Natjecatelj natjecatelj : listaNatjecatelja) {
			natjecateljDao.obrisi(natjecatelj);
		}

		List<Utakmica> sveUtakmice = utakmicaDao.dohvatiSve();
		for (Utakmica utakmica : sveUtakmice) {
			utakmica.setIgrac_utakmice(null);
			utakmica.setRezultat(null);
			utakmicaDao.osvjezi(utakmica);
		}

		List<Igrac> listaIgraca = igracDao.dohvatiSve();
		for (Igrac igrac : listaIgraca) {
			igracDao.obrisi(igrac);
		}
		Konfiguracija konfiguracija = konfiguracijaDao.dohvati();
		konfiguracija.obrisi();
		konfiguracija.setPostoji_sastav_ekipa(false);
		konfiguracija.setIgraci_ok(false);
		konfiguracijaDao.osvjezi(konfiguracija);

		List<IgracUtakmicaDogadaj> sviIgracUtakmicaDogadaj = igracUtakmicaDogadajDao
				.dohvatiSve();
		for (IgracUtakmicaDogadaj igracUtakmicaDogadaj : sviIgracUtakmicaDogadaj) {
			igracUtakmicaDogadajDao.obrisi(igracUtakmicaDogadaj);
		}

		List<Natjecatelj> sviNatjecatelji = natjecateljDao.dohvatiSve();
		for (Natjecatelj natjecatelj : sviNatjecatelji) {
			if (natjecatelj.getKorisnik_id() > 0) {
				natjecateljDao.obrisi(natjecatelj);
			}
		}

		List<Korisnik> sviKorisnici = korisnikDao.dohvatiSve();
		for (Korisnik korisnik : sviKorisnici) {
			if (korisnik.getRazina_prava().getRazina_prava_id().equals(4)) {
				korisnikDao.obrisi(korisnik);
			}
		}

		List<Ekipa> listaEkipa = ekipaDao.dohvatiSve();
		for (Ekipa ekipa : listaEkipa) {
			ekipa.setIzgubljene(0);
			ekipa.setPobjedene(0);
			ekipaDao.osvjezi(ekipa);
		}

		List<Dogadaj> listaDogadaja = dogadajDao.dohvatiSve();
		for (Dogadaj dogadaj : listaDogadaja) {
			dogadaj.setVrijednost(null);
			dogadajDao.osvjezi(dogadaj);
		}

		return "redirect:/osobnaAdmin";
	}

	@RequestMapping(value = "/osobnaAdmin/uploadProbniPodaci", method = RequestMethod.POST)
	public String handleFileUploadProbniPodaci(
			@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				FileOutputStream fileOuputStream = new FileOutputStream(
						"liga1.xls");
				fileOuputStream.write(bytes);
				fileOuputStream.close();
				File fileOnDisk = new File("liga1.xls");
				FileReader.spremiSastavEkipa(fileOnDisk, pozicijaDao, ekipaDao,
						igracDao);
				FileReader.spremiProbneNatjecatelje(fileOnDisk, natjecateljDao,
						korisnikDao, ekipaDao, drzavaDao, igracDao,
						razinaPravaDao);
				Konfiguracija konfiguracija = konfiguracijaDao.dohvati();
				konfiguracija.setMoze_sim(true);
				konfiguracija.setPostoji_sastav_ekipa(true);
				konfiguracijaDao.osvjezi(konfiguracija);
				return "redirect:/osobnaAdmin";
			} catch (Exception e) {
				e.printStackTrace();
				return "redirect:/osobnaAdmin/neuspioUpload/"
						+ "datoteka nije u odgovarajucem formatu";
			}
		} else {
			return "redirect:/osobnaAdmin/neuspioUpload/"
					+ "datoteka je prazna";
		}
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
