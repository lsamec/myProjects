package fer.opp.vlk.model;

import java.util.HashSet;
import java.util.Set;

public class Ekipa {

	private Integer ekipa_id;
	private String ime_ekipa;
	private Integer bodovi;
	private Integer pobjedene;
	private Integer izgubljene;
	private Integer nerjesene;
	private Set<Igrac> igraciEkipe = new HashSet<Igrac>();
	private Set<Natjecatelj> natjecateljiKojiPodupiru = new HashSet<Natjecatelj>();
	private Set<Utakmica> jeGost = new HashSet<Utakmica>();
	private Set<Utakmica> jeDomacin = new HashSet<Utakmica>();
	
	public Integer getEkipa_id() {
		return ekipa_id;
	}
	public void setEkipa_id(Integer ekipa_id) {
		this.ekipa_id = ekipa_id;
	}
	public String getIme_ekipa() {
		return ime_ekipa;
	}
	public void setIme_ekipa(String ime_ekipa) {
		this.ime_ekipa = ime_ekipa;
	}
	public Set<Igrac> getIgraciEkipe() {
		return igraciEkipe;
	}
	public void setIgraciEkipe(Set<Igrac> igraciEkipe) {
		this.igraciEkipe = igraciEkipe;
	}
	public Set<Natjecatelj> getNatjecateljiKojiPodupiru() {
		return natjecateljiKojiPodupiru;
	}
	public void setNatjecateljiKojiPodupiru(
			Set<Natjecatelj> natjecateljiKojiPodupiru) {
		this.natjecateljiKojiPodupiru = natjecateljiKojiPodupiru;
	}
	public Set<Utakmica> getJeGost() {
		return jeGost;
	}
	public void setJeGost(Set<Utakmica> jeGost) {
		this.jeGost = jeGost;
	}
	public Set<Utakmica> getJeDomacin() {
		return jeDomacin;
	}
	public void setJeDomacin(Set<Utakmica> jeDomacin) {
		this.jeDomacin = jeDomacin;
	}
	@Override
	public String toString() {
		return "Ekipa [ekipa_id=" + ekipa_id + ", ime_ekipa=" + ime_ekipa
				+ ", igraciEkipe=" + igraciEkipe
				+ ", natjecateljiKojiPodupiru=" + natjecateljiKojiPodupiru
				+ ", jeGost=" + jeGost + ", jeDomacin=" + jeDomacin + "]";
	}
	
	public String toJson(){
		return "{"
				+ "\"ime_ekipa\":\""+ime_ekipa+"\","
				+ "\"pobjede\":\""+pobjedene+"\","
				+ "\"porazi\":\""+izgubljene+"\""
				+ "}";
	}
	
	public Integer getBodovi() {
		return bodovi;
	}
	public void setBodovi(Integer bodovi) {
		this.bodovi = bodovi;
	}
	public Integer getPobjedene() {
		return pobjedene;
	}
	public void setPobjedene(Integer pobjedene) {
		this.pobjedene = pobjedene;
	}
	public Integer getIzgubljene() {
		return izgubljene;
	}
	public void setIzgubljene(Integer izgubljene) {
		this.izgubljene = izgubljene;
	}
	public Integer getNerjesene() {
		return nerjesene;
	}
	public void setNerjesene(Integer nerjesene) {
		this.nerjesene = nerjesene;
	}
	
}
