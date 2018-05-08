package fer.opp.vlk.model;

import java.util.HashSet;
import java.util.Set;

public class RazinaPrava {
	
	private Integer razina_prava_id;
	private String razina_prava_vrsta;
	private Set<Korisnik> sviKorisniciSTomRazinomPrava = new HashSet<Korisnik>();
	
	public Integer getRazina_prava_id() {
		return razina_prava_id;
	}
	public void setRazina_prava_id(Integer razina_prava_id) {
		this.razina_prava_id = razina_prava_id;
	}
	public String getRazina_prava_vrsta() {
		return razina_prava_vrsta;
	}
	public void setRazina_prava_vrsta(String razina_prava_vrsta) {
		this.razina_prava_vrsta = razina_prava_vrsta;
	}
	public Set<Korisnik> getSviKorisniciSTomRazinomPrava() {
		return sviKorisniciSTomRazinomPrava;
	}
	public void setSviKorisniciSTomRazinomPrava(
			Set<Korisnik> sviKorisniciSTomRazinomPrava) {
		this.sviKorisniciSTomRazinomPrava = sviKorisniciSTomRazinomPrava;
	}
	@Override
	public String toString() {
		return "RazinaPrava [razina_prava_id=" + razina_prava_id
				+ ", razina_prava_vrsta=" + razina_prava_vrsta
				+ "]";
	}
	
}
