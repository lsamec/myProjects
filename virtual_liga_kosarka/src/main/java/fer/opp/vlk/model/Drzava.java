package fer.opp.vlk.model;

import java.util.HashSet;
import java.util.Set;

public class Drzava {
	
	private Integer drzava_id;
	private String ime_drzava;
	private Set<Natjecatelj> sviNatjecateljiIzDrzave = new HashSet<Natjecatelj>();
	
	public Integer getDrzava_id() {
		return drzava_id;
	}
	public void setDrzava_id(Integer drzava_id) {
		this.drzava_id = drzava_id;
	}
	public String getIme_drzava() {
		return ime_drzava;
	}
	public void setIme_drzava(String ime_drzava) {
		this.ime_drzava = ime_drzava;
	}
	public Set<Natjecatelj> getSviNatjecateljiIzDrzave() {
		return sviNatjecateljiIzDrzave;
	}
	public void setSviNatjecateljiIzDrzave(Set<Natjecatelj> sviNatjecateljiIzDrzave) {
		this.sviNatjecateljiIzDrzave = sviNatjecateljiIzDrzave;
	}
	@Override
	public String toString() {
		return "Drzava [drzava_id=" + drzava_id + ", ime_drzava=" + ime_drzava
				+ ", sviNatjecateljiIzDrzave=" + sviNatjecateljiIzDrzave + "]";
	}

}
