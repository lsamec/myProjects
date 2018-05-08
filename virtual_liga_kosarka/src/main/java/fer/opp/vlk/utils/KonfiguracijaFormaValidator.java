package fer.opp.vlk.utils;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import fer.opp.vlk.model.KonfiguracijaForma;

public class KonfiguracijaFormaValidator implements Validator {


	@Override
	public boolean supports(Class<?> clazz) {
		
		return KonfiguracijaForma.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		KonfiguracijaForma konfiguracijaForma = (KonfiguracijaForma) target;
		
		try{
			Integer.parseInt(konfiguracijaForma.getProracun());
		}catch(NumberFormatException e){
			errors.rejectValue("proracun", "Pattern.konfiguracijaForma");
		}
		try{
			Integer.parseInt(konfiguracijaForma.getPostignutKos());
		}catch(NumberFormatException e){
			errors.rejectValue("postignutKos", "Pattern.konfiguracijaForma");
		}
		try{
			Integer.parseInt(konfiguracijaForma.getKosIzSlobodnogBacanja());
		}catch(NumberFormatException e){
			errors.rejectValue("kosIzSlobodnogBacanja", "Pattern.konfiguracijaForma");
		}
		try{
			Integer.parseInt(konfiguracijaForma.getTrica());
		}catch(NumberFormatException e){
			errors.rejectValue("trica", "Pattern.konfiguracijaForma");
		}
		try{
			Integer.parseInt(konfiguracijaForma.getSkok());
		}catch(NumberFormatException e){
			errors.rejectValue("skok", "Pattern.konfiguracijaForma");
		}
		try{
			Integer.parseInt(konfiguracijaForma.getOduzimanjeLopte());
		}catch(NumberFormatException e){
			errors.rejectValue("oduzimanjeLopte", "Pattern.konfiguracijaForma");
		}
		try{
			Integer.parseInt(konfiguracijaForma.getDodavanje());
		}catch(NumberFormatException e){
			errors.rejectValue("dodavanje", "Pattern.konfiguracijaForma");
		}
		try{
			Integer.parseInt(konfiguracijaForma.getBlokada());
		}catch(NumberFormatException e){
			errors.rejectValue("blokada", "Pattern.konfiguracijaForma");
		}
		try{
			Integer.parseInt(konfiguracijaForma.getIgracUtakmice());
		}catch(NumberFormatException e){
			errors.rejectValue("igracUtakmice", "Pattern.konfiguracijaForma");
		}
		try{
			Integer.parseInt(konfiguracijaForma.getIzgubljenaLopta());
		}catch(NumberFormatException e){
			errors.rejectValue("izgubljenaLopta", "Pattern.konfiguracijaForma");
		}
		try{
			Integer.parseInt(konfiguracijaForma.getPromasenoSlobodnoBacanje());
		}catch(NumberFormatException e){
			errors.rejectValue("promasenoSlobodnoBacanje", "Pattern.konfiguracijaForma");
		}
		try{
			Integer.parseInt(konfiguracijaForma.getPromasenSutZaDva());
		}catch(NumberFormatException e){
			errors.rejectValue("promasenSutZaDva", "Pattern.konfiguracijaForma");
		}
		try{
			Integer.parseInt(konfiguracijaForma.getPromasenaTrica());
		}catch(NumberFormatException e){
			errors.rejectValue("promasenaTrica", "Pattern.konfiguracijaForma");
		}
		try{
			Integer.parseInt(konfiguracijaForma.getTehnickaPogreska());
		}catch(NumberFormatException e){
			errors.rejectValue("tehnickaPogreska", "Pattern.konfiguracijaForma");
		}
		try{
			Integer.parseInt(konfiguracijaForma.getOsobnaPogreska());
		}catch(NumberFormatException e){
			errors.rejectValue("osobnaPogreska", "Pattern.konfiguracijaForma");
		}
		try{
			Integer.parseInt(konfiguracijaForma.getIskljucenje());
		}catch(NumberFormatException e){
			errors.rejectValue("iskljucenje", "Pattern.konfiguracijaForma");
		}
		
		
	}
	
}
