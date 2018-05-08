package fer.opp.vlk.model;


public class IgracUtakmicaDogadaj {

	private Integer igracUtakmicaDogadaj_id;
	private Igrac igrac;
	private Utakmica utakmica;
	private Dogadaj dogadaj;
	private String vrijeme;
	
	public IgracUtakmicaDogadaj(){};
	
	public IgracUtakmicaDogadaj(Integer id, Igrac igrac, Utakmica utakmica, Dogadaj dogadaj, String vrijeme){
		this.igrac = igrac;
		this.igracUtakmicaDogadaj_id = id;
		this.utakmica=utakmica;
		this.dogadaj=dogadaj;
		this.vrijeme=vrijeme;
	}
	
	public Integer getIgracUtakmicaDogadaj_id() {
		return igracUtakmicaDogadaj_id;
	}
	public void setIgracUtakmicaDogadaj_id(Integer igracUtakmicaDogadaj_id) {
		this.igracUtakmicaDogadaj_id = igracUtakmicaDogadaj_id;
	}
	public Igrac getIgrac() {
		return igrac;
	}
	public void setIgrac(Igrac igrac) {
		this.igrac = igrac;
	}
	public Utakmica getUtakmica() {
		return utakmica;
	}
	public void setUtakmica(Utakmica utakmica) {
		this.utakmica = utakmica;
	}
	public Dogadaj getDogadaj() {
		return dogadaj;
	}
	public void setDogadaj(Dogadaj dogadaj) {
		this.dogadaj = dogadaj;
	}
	public String getVrijeme() {
		return vrijeme;
	}
	public void setVrijeme(String vrijeme) {
		this.vrijeme = vrijeme;
	}


	
}
