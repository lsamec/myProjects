package fer.opp.vlk.model;

public class IgracDogadajVrijemeForma {
	private String igrac;
	private String dogadaj;
	private String vrijeme;
	public IgracDogadajVrijemeForma(String igrac, String dogadaj, String vrijeme) {
		super();
		this.igrac = igrac;
		this.dogadaj = dogadaj;
		this.vrijeme = vrijeme;
	}
	public String getIgrac() {
		return igrac;
	}
	public void setIgrac(String igrac) {
		this.igrac = igrac;
	}
	public String getDogadaj() {
		return dogadaj;
	}
	public void setDogadaj(String dogadaj) {
		this.dogadaj = dogadaj;
	}
	public String getVrijeme() {
		return vrijeme;
	}
	public void setVrijeme(String vrijeme) {
		this.vrijeme = vrijeme;
	}
	public IgracDogadajVrijemeForma() {
		super();
	}
	
	
	
}
