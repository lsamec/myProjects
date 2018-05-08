package fer.opp.vlk.model;

public class UtakmicaTehForma {
	
	private Integer utakmicaId;
	private String ekipaDomacinIme;
	private String ekipaGostIme;
	private Boolean izabranIgracUtakmice;
	
	public UtakmicaTehForma(Integer utakmica_id, String ekipaDomacinIme,
			String ekipaGostIme, Boolean izabranIgracUtakmice) {
		super();
		this.utakmicaId = utakmica_id;
		this.ekipaDomacinIme = ekipaDomacinIme;
		this.ekipaGostIme = ekipaGostIme;
		this.izabranIgracUtakmice = izabranIgracUtakmice;
	}
	

	public String getEkipaDomacinIme() {
		return ekipaDomacinIme;
	}
	public void setEkipaDomacinIme(String ekipaDomacinIme) {
		this.ekipaDomacinIme = ekipaDomacinIme;
	}
	public String getEkipaGostIme() {
		return ekipaGostIme;
	}
	public void setEkipaGostIme(String ekipaGostIme) {
		this.ekipaGostIme = ekipaGostIme;
	}
	public Boolean getIzabranIgracUtakmice() {
		return izabranIgracUtakmice;
	}
	public void setIzabranIgracUtakmice(Boolean izabranIgracUtakmice) {
		this.izabranIgracUtakmice = izabranIgracUtakmice;
	}
	public Integer getUtakmicaId() {
		return utakmicaId;
	}

	public void setUtakmicaId(Integer utakmicaId) {
		this.utakmicaId = utakmicaId;
	}
}
