package fer.opp.vlk.utils;

import java.util.List;
import java.util.Map;
import java.util.Random;

import fer.opp.vlk.dao.IgracDao;
import fer.opp.vlk.dao.IgracUtakmicaDogadajDao;
import fer.opp.vlk.dao.NatjecateljDao;
import fer.opp.vlk.model.Dogadaj;
import fer.opp.vlk.model.Igrac;
import fer.opp.vlk.model.IgracUtakmicaDogadaj;
import fer.opp.vlk.model.Natjecatelj;
import fer.opp.vlk.model.Utakmica;

public class Utils {
	public static Random rand = new Random();

	public static String secToVrijemeString(Integer ukSec) {
		Integer min = ukSec / 60;
		Integer sec = ukSec % 60;
		String vrijeme = min.toString() + ":" + sec.toString();
		return vrijeme;
	}

	public static Igrac izaberiIgracaProp(Map<String, List<Igrac>> mapaIgraca,
			Integer igracHitNumberBound) {
		Integer igracHitNumber = Utils.rand.nextInt(igracHitNumberBound) + 1;
		Integer accumulation = 0;
		Igrac igrac = null;
		for (List<Igrac> igraci : mapaIgraca.values()) {
			igrac = igraci.get(0);
			accumulation+=igrac.getVrijednost();
			if (accumulation >= igracHitNumber) {
				return igrac;
			} 
		}
		return igrac;
	}

	public static Igrac izaberiIgracaJednakeSanse(
			Map<String, List<Igrac>> mapaIgraca, Integer igracHitNumberBound) {
		Integer igracHitNumber = Utils.rand.nextInt(igracHitNumberBound);
		for (List<Igrac> igraci : mapaIgraca.values()) {
			Igrac igrac = igraci.get(0);
			if (igracHitNumber.equals(0)) {
				return igrac;
			} else {
				igracHitNumber -= 1;
			}
		}
		return null;
	}

	public static void stvoriDogadajIgraca(
			IgracUtakmicaDogadajDao igracUtakmicaDogadajDao, Integer sec,
			Dogadaj dogadaj, Utakmica utakmica, Igrac igrac,Map<Integer,Integer> igracIdBodovi) {

		igrac.setUk_bodovi(igrac.getUk_bodovi() + dogadaj.getVrijednost());
		IgracUtakmicaDogadaj igracUtakmicaDogadaj = new IgracUtakmicaDogadaj();
		igracUtakmicaDogadaj.setDogadaj(dogadaj);
		igracUtakmicaDogadaj.setIgrac(igrac);
		igracUtakmicaDogadaj.setUtakmica(utakmica);
		igracUtakmicaDogadaj.setVrijeme(Utils.secToVrijemeString(sec));
		igracUtakmicaDogadajDao.spremi(igracUtakmicaDogadaj);
		Integer bodoviUUtakmici = igracIdBodovi.get(igrac.getIgrac_id());
		bodoviUUtakmici+=dogadaj.getVrijednost();
		igracIdBodovi.put(igrac.getIgrac_id(), bodoviUUtakmici);

	}

	public static Boolean jeUPostavi(Igrac igrac,
			Map<String, List<Igrac>> mapaIgraca) {
		for (List<Igrac> listaIgraca : mapaIgraca.values()) {
			if (igrac.getIgrac_id().equals(listaIgraca.get(0).getIgrac_id())) {
				return true;
			}
		}
		return false;
	}

	public static Boolean iskljuciIgraca(Map<String, List<Igrac>> mapaIgraca,
			Igrac igrac) {
		for (List<Igrac> listaIgraca : mapaIgraca.values()) {
			if (igrac.getIgrac_id().equals(listaIgraca.get(0).getIgrac_id())) {
				if (listaIgraca.size() > 1) {
					listaIgraca.remove(0);
					return true;
				}
			}
		}
		return false;
	}
	
	public static void osvjeziBodoveNatjecatelja(IgracDao igracDao,NatjecateljDao natjecateljDao){
		List<Igrac> sviIgraci = igracDao.dohvatiSve();
		List<Natjecatelj> sviNatjecatelji = natjecateljDao.dohvatiSve();
		
		for(Natjecatelj natjecatelj : sviNatjecatelji){
			Integer ukBodovi=0;
			for(Igrac igrac : sviIgraci){				
				for(Igrac igracVirt : natjecatelj.getIgraciVirtualneEkipe()){
					if(igrac.getIgrac_id().equals(igracVirt.getIgrac_id())){
						ukBodovi+=igrac.getUk_bodovi();
						break;
					}
				}
			}
			natjecatelj.setBodovi_virt_ekipe(ukBodovi);
			natjecateljDao.osvjezi(natjecatelj);
		}
	}
	
}
