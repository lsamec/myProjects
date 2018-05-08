package fer.opp.vlk.model;

import java.util.HashSet;
import java.util.Set;

public class Natjecatelj {

	private Integer korisnik_id;
	private Ekipa podupire_ekipu;
	private String ime;
    private String prezime;
	private String e_mail;
	private String naziv_virt_ekipe;
	private Drzava drzava;
	private Integer bodovi_virt_ekipe;
	private Set<Igrac> igraciVirtualneEkipe = new HashSet<Igrac>();
	
	public Ekipa getPodupire_ekipu() {
		return podupire_ekipu;
	}
	public void setPodupire_ekipu(Ekipa podupire_ekipu) {
		this.podupire_ekipu = podupire_ekipu;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getE_mail() {
		return e_mail;
	}
	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}
	public String getNaziv_virt_ekipe() {
		return naziv_virt_ekipe;
	}
	public void setNaziv_virt_ekipe(String naziv_virt_ekipe) {
		this.naziv_virt_ekipe = naziv_virt_ekipe;
	}
	public Drzava getDrzava() {
		return drzava;
	}
	public void setDrzava(Drzava drzava) {
		this.drzava = drzava;
	}
	public Integer getBodovi_virt_ekipe() {
		return bodovi_virt_ekipe;
	}
	public void setBodovi_virt_ekipe(Integer bodovi_virt_ekipe) {
		this.bodovi_virt_ekipe = bodovi_virt_ekipe;
	}
	public Set<Igrac> getIgraciVirtualneEkipe() {
		return igraciVirtualneEkipe;
	}
	public void setIgraciVirtualneEkipe(Set<Igrac> igraciVirtualneEkipe) {
		this.igraciVirtualneEkipe = igraciVirtualneEkipe;
	}
	public Integer getKorisnik_id() {
		return korisnik_id;
	}
	public void setKorisnik_id(Integer korisnik_id) {
		this.korisnik_id = korisnik_id;
	}

	
}
