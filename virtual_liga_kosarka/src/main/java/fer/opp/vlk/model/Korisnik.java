package fer.opp.vlk.model;

public class Korisnik {

	private Integer korisnik_id;
	private String korisnicko_ime;
	private String lozinka;
	private RazinaPrava razina_prava;
	
	public Integer getKorisnik_id() {
		return korisnik_id;
	}
	public void setKorisnik_id(Integer korisnik_id) {
		this.korisnik_id = korisnik_id;
	}
	public String getKorisnicko_ime() {
		return korisnicko_ime;
	}
	public void setKorisnicko_ime(String korisnicko_ime) {
		this.korisnicko_ime = korisnicko_ime;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	public RazinaPrava getRazina_prava() {
		return razina_prava;
	}
	public void setRazina_prava(RazinaPrava razina_prava) {
		this.razina_prava = razina_prava;
	}
	@Override
	public String toString() {
		return "Korisnik [korisnik_id=" + korisnik_id + ", korisnicko_ime="
				+ korisnicko_ime + ", lozinka=" + lozinka + "]";
	}

	
	
}
