package fer.opp.vlk.model;

import java.util.HashSet;
import java.util.Set;

public class Dogadaj {
	
	private Integer dogadaj_id;
	private String ime_dogadaj;
	private Integer vrijednost;
	private Set<IgracUtakmicaDogadaj> sviDogadajiTogTipa = new HashSet<IgracUtakmicaDogadaj>();
	public Integer getDogadaj_id() {
		return dogadaj_id;
	}
	public void setDogadaj_id(Integer dogadaj_id) {
		this.dogadaj_id = dogadaj_id;
	}
	public String getIme_dogadaj() {
		return ime_dogadaj;
	}
	public void setIme_dogadaj(String ime_dogadaj) {
		this.ime_dogadaj = ime_dogadaj;
	}
	public Set<IgracUtakmicaDogadaj> getSviDogadajiTogTipa() {
		return sviDogadajiTogTipa;
	}
	public void setSviDogadajiTogTipa(Set<IgracUtakmicaDogadaj> sviDogadajiTogTipa) {
		this.sviDogadajiTogTipa = sviDogadajiTogTipa;
	}
	public Integer getVrijednost() {
		return vrijednost;
	}
	public void setVrijednost(Integer vrijednost) {
		this.vrijednost = vrijednost;
	}
	@Override
	public String toString() {
		return "Dogadaj [dogadaj_id=" + dogadaj_id + ", ime_dogadaj="
				+ ime_dogadaj + ", vrijednost=" + vrijednost + "]";
	}
	
	
}
