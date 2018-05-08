package fer.opp.vlk.model;

public class NatjecateljForma {
	
	private String proracun;
	private String korisnicko_ime;
	private String lozinka;
	private String podupire_ekipu;
	private String ime;
    private String prezime;
	private String e_mail;
	private String naziv_virt_ekipe;
	private String drzava;
	private String organizator;
	private String bek;
	private String krilo;
	private String centar;
	private String krilniCentar;
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
	public String getPodupire_ekipu() {
		return podupire_ekipu;
	}
	public void setPodupire_ekipu(String podupire_ekipu) {
		this.podupire_ekipu = podupire_ekipu;
	}
	public String getDrzava() {
		return drzava;
	}
	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}
	public String getOrganizator() {
		return organizator;
	}
	public void setOrganizator(String organizator) {
		this.organizator = organizator;
	}
	public String getProracun() {
		return proracun;
	}
	public void setProracun(String proracun) {
		this.proracun = proracun;
	}
	public String getBek() {
		return bek;
	}
	public void setBek(String bek) {
		this.bek = bek;
	}
	public String getKrilo() {
		return krilo;
	}
	public void setKrilo(String krilo) {
		this.krilo = krilo;
	}
	public String getCentar() {
		return centar;
	}
	public void setCentar(String centar) {
		this.centar = centar;
	}
	public String getKrilniCentar() {
		return krilniCentar;
	}
	public void setKrilniCentar(String krilniCentar) {
		this.krilniCentar = krilniCentar;
	}
	@Override
	public String toString() {
		return "NatjecateljForma [proracun=" + proracun + ", korisnicko_ime="
				+ korisnicko_ime + ", lozinka=" + lozinka + ", podupire_ekipu="
				+ podupire_ekipu + ", ime=" + ime + ", prezime=" + prezime
				+ ", e_mail=" + e_mail + ", naziv_virt_ekipe="
				+ naziv_virt_ekipe + ", drzava=" + drzava + ", organizator="
				+ organizator + ", bek=" + bek + ", krilo=" + krilo
				+ ", centar=" + centar + ", krilniCentar=" + krilniCentar + "]";
	}

}
