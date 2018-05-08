package fer.opp.vlk.model;

import java.util.HashSet;
import java.util.Set;

public class Pozicija {

	private Integer pozicija_id;
	private String ime_pozicija;
	private Set<Igrac> sviIgraciNaTojPoziciji = new HashSet<Igrac>();
	
	public Integer getPozicija_id() {
		return pozicija_id;
	}
	public void setPozicija_id(Integer pozicija_id) {
		this.pozicija_id = pozicija_id;
	}

	public Set<Igrac> getSviIgraciNaTojPoziciji() {
		return sviIgraciNaTojPoziciji;
	}
	public void setSviIgraciNaTojPoziciji(Set<Igrac> sviIgraciNaTojPoziciji) {
		this.sviIgraciNaTojPoziciji = sviIgraciNaTojPoziciji;
	}
	public String getIme_pozicija() {
		return ime_pozicija;
	}
	public void setIme_pozicija(String ime_pozicija) {
		this.ime_pozicija = ime_pozicija;
	}
	
}
