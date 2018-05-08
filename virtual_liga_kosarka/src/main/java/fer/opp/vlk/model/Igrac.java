package fer.opp.vlk.model;

import java.util.HashSet;
import java.util.Set;

public class Igrac {

	private Integer igrac_id;
	private Ekipa ekipa;
	private String ime_igrac;
	private String prezime_igrac;
	private Integer broj_dresa;
	private Pozicija pozicija;
	private Integer vrijednost;
	private Integer uk_bodovi;
	private Set<IgracUtakmicaDogadaj> sviDogadajiIgraca = new HashSet<IgracUtakmicaDogadaj>();
	private Set<Natjecatelj> sviNatjecateljiUCijojJeVirtEkipi = new HashSet<Natjecatelj>();
	private Set<Utakmica> sveUtakmiceGdjeJeNajboljiIgrac = new HashSet<Utakmica>();
	
	public Integer getIgrac_id() {
		return igrac_id;
	}
	public void setIgrac_id(Integer igrac_id) {
		this.igrac_id = igrac_id;
	}
	public Ekipa getEkipa() {
		return ekipa;
	}
	public void setEkipa(Ekipa ekipa) {
		this.ekipa = ekipa;
	}
	public String getIme_igrac() {
		return ime_igrac;
	}
	public void setIme_igrac(String ime_igrac) {
		this.ime_igrac = ime_igrac;
	}
	public String getPrezime_igrac() {
		return prezime_igrac;
	}
	public void setPrezime_igrac(String prezime_igrac) {
		this.prezime_igrac = prezime_igrac;
	}
	public Integer getBroj_dresa() {
		return broj_dresa;
	}
	public void setBroj_dresa(Integer broj_dresa) {
		this.broj_dresa = broj_dresa;
	}
	public Pozicija getPozicija() {
		return pozicija;
	}
	public void setPozicija(Pozicija pozicija) {
		this.pozicija = pozicija;
	}
	public Integer getVrijednost() {
		return vrijednost;
	}
	public void setVrijednost(Integer vrijednost) {
		this.vrijednost = vrijednost;
	}
	public Integer getUk_bodovi() {
		return uk_bodovi;
	}
	public void setUk_bodovi(Integer uk_bodovi) {
		this.uk_bodovi = uk_bodovi;
	}
	public Set<IgracUtakmicaDogadaj> getSviDogadajiIgraca() {
		return sviDogadajiIgraca;
	}
	public void setSviDogadajiIgraca(Set<IgracUtakmicaDogadaj> sviDogadajiIgraca) {
		this.sviDogadajiIgraca = sviDogadajiIgraca;
	}
	public Set<Natjecatelj> getSviNatjecateljiUCijojJeVirtEkipi() {
		return sviNatjecateljiUCijojJeVirtEkipi;
	}
	public void setSviNatjecateljiUCijojJeVirtEkipi(
			Set<Natjecatelj> sviNatjecateljiUCijojJeVirtEkipi) {
		this.sviNatjecateljiUCijojJeVirtEkipi = sviNatjecateljiUCijojJeVirtEkipi;
	}
	public Set<Utakmica> getSveUtakmiceGdjeJeNajboljiIgrac() {
		return sveUtakmiceGdjeJeNajboljiIgrac;
	}
	public void setSveUtakmiceGdjeJeNajboljiIgrac(
			Set<Utakmica> sveUtakmiceGdjeJeNajboljiIgrac) {
		this.sveUtakmiceGdjeJeNajboljiIgrac = sveUtakmiceGdjeJeNajboljiIgrac;
	}
	@Override
	public String toString() {
		return "Igrac [igrac_id=" + igrac_id + ", ime_igrac=" + ime_igrac
				+ ", prezime_igrac=" + prezime_igrac + ", broj_dresa="
				+ broj_dresa + ", vrijednost=" + vrijednost + ", uk_bodovi="
				+ uk_bodovi + "]";
	}
	
	
	
}
