package fer.opp.vlk.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import fer.opp.vlk.dao.DogadajDao;
import fer.opp.vlk.dao.DrzavaDao;
import fer.opp.vlk.dao.EkipaDao;
import fer.opp.vlk.dao.IgracDao;
import fer.opp.vlk.dao.KonfiguracijaDao;
import fer.opp.vlk.dao.KorisnikDao;
import fer.opp.vlk.dao.NatjecateljDao;
import fer.opp.vlk.dao.PozicijaDao;
import fer.opp.vlk.dao.RazinaPravaDao;
import fer.opp.vlk.model.Dogadaj;
import fer.opp.vlk.model.Drzava;
import fer.opp.vlk.model.Ekipa;
import fer.opp.vlk.model.Igrac;
import fer.opp.vlk.model.Konfiguracija;
import fer.opp.vlk.model.Korisnik;
import fer.opp.vlk.model.Natjecatelj;
import fer.opp.vlk.model.Pozicija;
import fer.opp.vlk.model.RazinaPrava;

public class FileReader {

	public static void spremiSastavEkipa(File xlsFile, PozicijaDao pozicijaDao,
			EkipaDao ekipaDao, IgracDao igracDao) throws BiffException, IOException {
		Workbook w;
			w = Workbook.getWorkbook(xlsFile);
			Sheet sheet = w.getSheet(0);
			int noRows = sheet.getRows();

			Boolean endOfFile = false;

			for (int j = 1; j < noRows; j++) {
				Igrac igrac = new Igrac();
				for (int i = 0; i < 6; i++) {
					Cell cell = sheet.getCell(i, j);
					String contents = cell.getContents();

					if (contents.equals("")) {
						endOfFile = true;
						break;
					}
					switch (i) {
					case 0:
						Ekipa ekipa = ekipaDao.dohvatiPoImenu(contents);
						igrac.setEkipa(ekipa);
						break;
					case 1:
						igrac.setBroj_dresa(Integer.parseInt(contents));
						break;
					case 2:
						Pozicija pozicija = pozicijaDao
								.dohvatiPoImenu(contents);
						igrac.setPozicija(pozicija);
						break;
					case 3:
						igrac.setIme_igrac(contents);
						break;
					case 4:
						igrac.setPrezime_igrac(contents);
						break;
					case 5:
						igrac.setVrijednost(Integer.parseInt(contents));
						break;
					}
				}
				if (endOfFile)
					break;
				igrac.setUk_bodovi(0);
				igracDao.spremi(igrac);
			}
	}
	
	public static void spremiSastavEkipaBezVrijednosti(File xlsFile, PozicijaDao pozicijaDao,
			EkipaDao ekipaDao, IgracDao igracDao) throws BiffException, IOException {
		Workbook w;
			w = Workbook.getWorkbook(xlsFile);
			Sheet sheet = w.getSheet(0);
			int noRows = sheet.getRows();

			Boolean endOfFile = false;

			for (int j = 1; j < noRows; j++) {
				Igrac igrac = new Igrac();
				for (int i = 0; i < 5; i++) {
					Cell cell = sheet.getCell(i, j);
					String contents = cell.getContents();

					if (contents.equals("")) {
						endOfFile = true;
						break;
					}
					switch (i) {
					case 0:
						Ekipa ekipa = ekipaDao.dohvatiPoImenu(contents);
						igrac.setEkipa(ekipa);
						break;
					case 1:
						igrac.setBroj_dresa(Integer.parseInt(contents));
						break;
					case 2:
						Pozicija pozicija = pozicijaDao
								.dohvatiPoImenu(contents);
						igrac.setPozicija(pozicija);
						break;
					case 3:
						igrac.setIme_igrac(contents);
						break;
					case 4:
						igrac.setPrezime_igrac(contents);
						break;
					}
				}
				if (endOfFile)
					break;
				igrac.setUk_bodovi(0);
				igracDao.spremi(igrac);
			}
	}

	public static void spremiKonfiguraciju(File konfFile,
			DogadajDao dogadajDao, KonfiguracijaDao konfiguracijaDao) {

		List<String> lineList = null;
		try {
			lineList = Files.readAllLines(konfFile.toPath(),
					Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String line : lineList) {
			String[] parts = line.split(" ");
			if (parts[0].equals("proracun")) {
				Konfiguracija konf = konfiguracijaDao.dohvati();
				konf.pseudoKonstruktor(Integer.parseInt(parts[1]),1,false,false,false,false);
				konfiguracijaDao.osvjezi(konf);
			} else {
				Dogadaj dogadaj = dogadajDao.dohvatiPoImenu(parts[0]);
				dogadaj.setVrijednost(Integer.parseInt(parts[1]));
				dogadajDao.osvjezi(dogadaj);
			}
		}

	}
	public static void spremiProbneNatjecatelje(File xlsFile, NatjecateljDao natjecateljDao,KorisnikDao korisnikDao,
			EkipaDao ekipaDao, DrzavaDao drzavaDao,IgracDao igracDao,RazinaPravaDao razinaPravaDao) {
		Workbook w;
		try {
			w = Workbook.getWorkbook(xlsFile);
			Sheet sheet = w.getSheet(1);
			int noRows = sheet.getRows();

			Boolean endOfFile = false;

			for (int j = 1; j < noRows; j++) {
				Natjecatelj natjecatelj = new Natjecatelj();
				Korisnik korisnik = new Korisnik();
				for (int i = 0; i < 7; i++) {
					Cell cell = sheet.getCell(i, j);
					String contents = cell.getContents();

					if (contents.equals("")) {
						endOfFile = true;
						break;
					}
					switch (i) {
					case 0:
						natjecatelj.setIme(contents);
						break;
					case 1:
						natjecatelj.setPrezime(contents);
						break;
					case 2:
						korisnik.setKorisnicko_ime(contents);
						break;
					case 3:
						natjecatelj.setNaziv_virt_ekipe(contents);
						break;
					case 4:
						natjecatelj.setE_mail(contents);
						break;
					case 5:
						Drzava drzava =drzavaDao.dohvatiPoImenu(contents);
						natjecatelj.setDrzava(drzava);
						break;
					case 6:
						Ekipa ekipa =ekipaDao.dohvatiPoImenu(contents);
						natjecatelj.setPodupire_ekipu(ekipa);
						break;
					}
				}	
				
				if (endOfFile) break;
				natjecatelj.setBodovi_virt_ekipe(0);
				korisnik.setLozinka("lozinka");
				RazinaPrava razinaPrava = razinaPravaDao.dohvatiPoImenu("natjecatelj");
				korisnik.setRazina_prava(razinaPrava);
				korisnikDao.spremi(korisnik);
				Korisnik korisnikOut = korisnikDao.dohvatiPoKorisnickomImenu(korisnik.getKorisnicko_ime());
				natjecatelj.setKorisnik_id(korisnikOut.getKorisnik_id());
				natjecateljDao.spremi(natjecatelj);
				
				List<Igrac> listaIgraca = new ArrayList<Igrac>();
				
				List<Igrac> listaOrganizatora = igracDao.dohvatiPoUlozi("organizator");				
				listaIgraca.add(listaOrganizatora.get(Utils.rand.nextInt(listaOrganizatora.size())));
				
				List<Igrac> listaBekova = igracDao.dohvatiPoUlozi("bek");				
				listaIgraca.add(listaBekova.get(Utils.rand.nextInt(listaBekova.size())));
				
				List<Igrac> listaKrila = igracDao.dohvatiPoUlozi("krilo");				
				listaIgraca.add(listaKrila.get(Utils.rand.nextInt(listaKrila.size())));
				
				List<Igrac> listaKrilnogCentra = igracDao.dohvatiPoUlozi("krilni centar");				
				listaIgraca.add(listaKrilnogCentra.get(Utils.rand.nextInt(listaKrilnogCentra.size())));
				
				List<Igrac> listaCentra = igracDao.dohvatiPoUlozi("centar");				
				listaIgraca.add(listaCentra.get(Utils.rand.nextInt(listaCentra.size())));
				
				
				natjecatelj.getIgraciVirtualneEkipe().addAll(listaIgraca);
				natjecateljDao.osvjezi(natjecatelj);
				
			}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
