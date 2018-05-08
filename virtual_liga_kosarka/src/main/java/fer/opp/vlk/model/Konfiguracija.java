package fer.opp.vlk.model;

public class Konfiguracija {
	
	private Integer konfiguracija_id;
	private Integer proracun;
	private Integer status_simulatora;
	private Boolean odobren_konf;
	private Boolean postoji_sastav_ekipa;
	private Boolean igraci_ok;
	private Boolean moze_sim;
	private Boolean odobrena_reg;
	private Boolean zapoceta_liga;
	private Boolean just_a_konf_holder;
	
	public void obrisi() {

		this.proracun = null;
		this.status_simulatora = null;
		this.odobren_konf = null;
		this.moze_sim = null;
		this.odobrena_reg = null;
		this.zapoceta_liga = null;
		this.just_a_konf_holder = true;
	}
	
	public void pseudoKonstruktor(Integer proracun, Integer status_simulatora,
			Boolean odobren_konf,
			Boolean moze_sim, Boolean odobrena_reg,
			Boolean zapoceta_liga) {
		this.proracun = proracun;
		this.status_simulatora = status_simulatora;
		this.odobren_konf = odobren_konf;
		this.moze_sim = moze_sim;
		this.odobrena_reg = odobrena_reg;
		this.zapoceta_liga = zapoceta_liga;
		this.just_a_konf_holder = false;
	}

	public Integer getKonfiguracija_id() {
		return konfiguracija_id;
	}

	public void setKonfiguracija_id(Integer konfiguracija_id) {
		this.konfiguracija_id = konfiguracija_id;
	}

	public Integer getProracun() {
		return proracun;
	}

	public void setProracun(Integer proracun) {
		this.proracun = proracun;
	}

	public Integer getStatus_simulatora() {
		return status_simulatora;
	}

	public void setStatus_simulatora(Integer status_simulatora) {
		this.status_simulatora = status_simulatora;
	}

	public Boolean getOdobren_konf() {
		return odobren_konf;
	}

	public void setOdobren_konf(Boolean odobren_konf) {
		this.odobren_konf = odobren_konf;
	}

	public Boolean getPostoji_sastav_ekipa() {
		return postoji_sastav_ekipa;
	}

	public void setPostoji_sastav_ekipa(Boolean postoji_sastav_ekipa) {
		this.postoji_sastav_ekipa = postoji_sastav_ekipa;
	}

	public Boolean getIgraci_ok() {
		return igraci_ok;
	}

	public void setIgraci_ok(Boolean igraci_ok) {
		this.igraci_ok = igraci_ok;
	}

	public Boolean getMoze_sim() {
		return moze_sim;
	}

	public void setMoze_sim(Boolean moze_sim) {
		this.moze_sim = moze_sim;
	}

	public Boolean getOdobrena_reg() {
		return odobrena_reg;
	}

	public void setOdobrena_reg(Boolean odobrena_reg) {
		this.odobrena_reg = odobrena_reg;
	}

	public Boolean getZapoceta_liga() {
		return zapoceta_liga;
	}

	public void setZapoceta_liga(Boolean zapoceta_liga) {
		this.zapoceta_liga = zapoceta_liga;
	}

	public Boolean getJust_a_konf_holder() {
		return just_a_konf_holder;
	}

	public void setJust_a_konf_holder(Boolean just_a_konf_holder) {
		this.just_a_konf_holder = just_a_konf_holder;
	}
	
}	