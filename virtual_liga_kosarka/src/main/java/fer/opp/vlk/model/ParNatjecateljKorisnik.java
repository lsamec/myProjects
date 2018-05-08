package fer.opp.vlk.model;

public class ParNatjecateljKorisnik {
	
	private Natjecatelj natjecatelj;
	private Korisnik korisnik;
	
	public Natjecatelj getNatjecatelj() {
		return natjecatelj;
	}
	public void setNatjecatelj(Natjecatelj natjecatelj) {
		this.natjecatelj = natjecatelj;
	}
	public Korisnik getKorisnik() {
		return korisnik;
	}
	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}
	public ParNatjecateljKorisnik(Natjecatelj natjecatelj, Korisnik korisnik) {
		super();
		this.natjecatelj = natjecatelj;
		this.korisnik = korisnik;
	}
}
