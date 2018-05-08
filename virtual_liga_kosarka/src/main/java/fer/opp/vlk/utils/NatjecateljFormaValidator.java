package fer.opp.vlk.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import fer.opp.vlk.dao.KorisnikDao;
import fer.opp.vlk.dao.NatjecateljDao;
import fer.opp.vlk.model.Korisnik;
import fer.opp.vlk.model.Natjecatelj;
import fer.opp.vlk.model.NatjecateljForma;

@Component
public class NatjecateljFormaValidator implements Validator {

	private Pattern pattern;
	private Matcher matcher;
	private NatjecateljDao natjecateljDao;
	private KorisnikDao korisnikDao;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public NatjecateljFormaValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	private boolean validEmail(final String email) {

		matcher = pattern.matcher(email);
		return matcher.matches();

	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return NatjecateljForma.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		

		NatjecateljForma natjecateljForma = (NatjecateljForma) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "korisnicko_ime", "NotEmpty.natjecateljForma.korisnicko_ime");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ime", "NotEmpty.natjecateljForma.ime");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "prezime", "NotEmpty.natjecateljForma.prezime");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lozinka", "NotEmpty.natjecateljForma.lozinka");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "e_mail", "NotEmpty.natjecateljForma.e_mail");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "naziv_virt_ekipe", "NotEmpty.natjecateljForma.naziv_virt_ekipe");

		if(!validEmail(natjecateljForma.getE_mail())){
			errors.rejectValue("e_mail", "Pattern.natjecateljForma.e_mail");
		}
		
		if(natjecateljForma.getBek().equals("none")){
			errors.rejectValue("bek", "None.natjecateljForma.igrac");
		}
		
		if(natjecateljForma.getOrganizator().equals("none")){
			errors.rejectValue("organizator", "None.natjecateljForma.igrac");
		}
		
		if(natjecateljForma.getCentar().equals("none")){
			errors.rejectValue("centar", "None.natjecateljForma.igrac");
		}
		
		if(natjecateljForma.getKrilo().equals("none")){
			errors.rejectValue("krilo", "None.natjecateljForma.igrac");
		}
		
		if(natjecateljForma.getKrilniCentar().equals("none")){
			errors.rejectValue("krilniCentar", "None.natjecateljForma.igrac");
		}
		
		if(natjecateljForma.getPodupire_ekipu().equals("none")){
			errors.rejectValue("podupire_ekipu", "None.natjecateljForma.podupire_ekipu");
		}
		
		if(natjecateljForma.getDrzava().equals("none")){
			errors.rejectValue("drzava", "None.natjecateljForma.drzava");
		}

		Korisnik korisnik = korisnikDao.dohvatiPoKorisnickomImenu(natjecateljForma.getKorisnicko_ime());
		if(korisnik != null){
			errors.rejectValue("korisnicko_ime", "Exists.natjecateljForma.korisnickoIme");
		}
		
		Natjecatelj natjecatelj = natjecateljDao.dohvatiPoNazivuVirtEkipe(natjecateljForma.getNaziv_virt_ekipe());
		if(natjecatelj != null){
			errors.rejectValue("naziv_virt_ekipe", "Exists.natjecateljForma.naziv_virt_ekipe");
		}

	}

	public NatjecateljDao getNatjecateljDao() {
		return natjecateljDao;
	}
	@Autowired(required=true)
    @Qualifier(value="natjecateljDao")
	public void setNatjecateljDao(NatjecateljDao natjecateljDao) {
		this.natjecateljDao = natjecateljDao;
	}

	public KorisnikDao getKorisnikDao() {
		return korisnikDao;
	}
	@Autowired(required=true)
    @Qualifier(value="korisnikDao")
	public void setKorisnikDao(KorisnikDao korisnikDao) {
		this.korisnikDao = korisnikDao;
	}

	
}
