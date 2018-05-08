package fer.opp.vlk.model;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

public class Utakmica {
	
	private Integer utakmica_id;
	private Ekipa ekipa_domacin;
	private Ekipa ekipa_gost;
	private Igrac igrac_utakmice;
	private String rezultat;
	private Set<IgracUtakmicaDogadaj> sviDogadajiNaUtakmici = new HashSet<IgracUtakmicaDogadaj>();
	
	public String toJson(){
		String igrac = "";
		String rez = "";
		if(this.rezultat == null){
			rez = "";
		}
		else{
			rez = this.rezultat;
		}
		if(this.igrac_utakmice == null){
			igrac = "";
		}
		else{
			igrac = this.igrac_utakmice.getIme_igrac() + " " + this.igrac_utakmice.getPrezime_igrac();
		}
		String json = "{\"ekipa_domacin\":\""+this.ekipa_domacin.getIme_ekipa()+
				"\",\"ekipa_gost\":\""+this.ekipa_gost.getIme_ekipa()+
				"\",\"igrac_utakmice\":\""+igrac+
				"\",\"rezultat\":\""+rez+"\"}";
		
		return json;
	}
	
	
	public Integer getUtakmica_id() {
		return utakmica_id;
	}
	public void setUtakmica_id(Integer utakmica_id) {
		this.utakmica_id = utakmica_id;
	}
	public Ekipa getEkipa_domacin() {
		return ekipa_domacin;
	}
	public void setEkipa_domacin(Ekipa ekipa_domacin) {
		this.ekipa_domacin = ekipa_domacin;
	}
	public Ekipa getEkipa_gost() {
		return ekipa_gost;
	}
	public void setEkipa_gost(Ekipa ekipa_gost) {
		this.ekipa_gost = ekipa_gost;
	}
	public Igrac getIgrac_utakmice() {
		return igrac_utakmice;
	}
	public void setIgrac_utakmice(Igrac igrac_utakmice) {
		this.igrac_utakmice = igrac_utakmice;
	}
	public Set<IgracUtakmicaDogadaj> getSviDogadajiNaUtakmici() {
		return sviDogadajiNaUtakmici;
	}
	public void setSviDogadajiNaUtakmici(
			Set<IgracUtakmicaDogadaj> sviDogadajiNaUtakmici) {
		this.sviDogadajiNaUtakmici = sviDogadajiNaUtakmici;
	}
	public String getRezultat() {
		return rezultat;
	}
	public void setRezultat(String rezultat) {
		this.rezultat = rezultat;
	}
	
}
