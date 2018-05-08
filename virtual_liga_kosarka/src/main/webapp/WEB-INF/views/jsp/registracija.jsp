<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<%@include file="fragments/header.jsp"%>

<p hidden id="holder">${proracun}</p>

<script>
	function naplati(pozicija, proracun) {
		var pozicijaValue = pozicija.options[pozicija.selectedIndex].value;
		if (pozicijaValue.indexOf("$") == 0) {
			pozicijaValue = parseInt(pozicijaValue.substring(pozicijaValue
					.indexOf("$") + 1, pozicijaValue.indexOf(":")));
			proracun.vrijednost = proracun.vrijednost - pozicijaValue;
		}
	}

	function racunajStanjeRacuna() {

		var proracun = {
			vrijednost : 0
		};
		proracun.vrijednost = parseInt(document.getElementById("holder").innerHTML);

		naplati(document.getElementById("organizator"), proracun);
		naplati(document.getElementById("bek"), proracun);
		naplati(document.getElementById("krilo"), proracun);
		naplati(document.getElementById("centar"), proracun);
		naplati(document.getElementById("krilniCentar"), proracun);

		var gumbZaRegistraciju = document.getElementById("registriraj");
		if (proracun.vrijednost < 0) {
			gumbZaRegistraciju.className = "btn-lg btn-warning pull-mid";
			gumbZaRegistraciju.disabled = true;
		} else {
			gumbZaRegistraciju.className = "btn-lg btn-primary pull-mid";
			gumbZaRegistraciju.disabled = false;
		}

		document.getElementById("proracun").innerHTML = proracun.vrijednost;
	}
</script>

<div class="container mid">

	<div class="container">
		<div class="col-md-4">
			<h2 class="mid" style="font-style: italic">
				Obrazac za registraciju<br> <br>
			</h2>
		</div>
	</div>
	<spring:url value="/registiraj" var="akcijaRegistracije" />

	<form:form class="form-horizontal" method="post"
		modelAttribute="natjecateljForma" action="${akcijaRegistracije}"
		acceptCharset="UTF-8">

		<spring:bind path="korisnicko_ime">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Korisničko ime</label>
				<div class="col-sm-10">
					<form:input path="korisnicko_ime" type="text"
						class="form-control inputForBox" id="korisnicko_ime"
						placeholder="Korisnicko ime" />
					<form:errors path="korisnicko_ime" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="lozinka">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Lozinka</label>
				<div class="col-sm-10">
					<form:password path="lozinka" class="form-control inputForBox"
						id="lozinka" placeholder="Lozinka" />
					<form:errors path="lozinka" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="naziv_virt_ekipe">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Naziv virtualne ekipe</label>
				<div class="col-sm-10">
					<form:input path="naziv_virt_ekipe" type="text"
						class="form-control inputForBox" id="naziv_virt_ekipe"
						placeholder="Naziv virtualne ekipe" />
					<form:errors path="naziv_virt_ekipe" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="ime">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Ime</label>
				<div class="col-sm-10">
					<form:input path="ime" type="text" class="form-control inputForBox"
						id="ime" placeholder="Ime" />
					<form:errors path="ime" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="prezime">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Prezime</label>
				<div class="col-sm-10">
					<form:input path="prezime" type="text"
						class="form-control inputForBox" id="prezime"
						placeholder="Prezime" />
					<form:errors path="prezime" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="e_mail">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">E-mail</label>
				<div class="col-sm-10">
					<form:input path="e_mail" class="form-control inputForBox"
						id="e_mail" placeholder="E-mail" />
					<form:errors path="e_mail" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="drzava">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Država</label>
				<div class="col-sm-5">
					<form:select path="drzava" class="form-control inputForBox">
						<form:option value="none" label="--- Select ---" />
						<form:options items="${drzave}" />
					</form:select>
					<form:errors path="drzava" class="control-label" />
				</div>
				<div class="col-sm-5"></div>
			</div>
		</spring:bind>

		<spring:bind path="podupire_ekipu">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Podupirem ekipu</label>
				<div class="col-sm-5">
					<form:select path="podupire_ekipu" class="form-control inputForBox">
						<form:option value="none" label="--- Select ---" />
						<form:options items="${ekipe}" />
					</form:select>
					<form:errors path="podupire_ekipu" class="control-label" />
				</div>
				<div class="col-sm-5"></div>
			</div>
		</spring:bind>

		<div class="container">
			<div class="col-md-4">
				<h3 class="mid" style="font-style: italic">
					Igrači<br> <br>
				</h3>
			</div>
		</div>

		<div class="container">
			<div class="col-md-4">
				<h5 class="mid" style="font-style: italic">
					Preostalo sredstava: <span id="proracun">${proracun}</span> <br>
					<br>
				</h5>
			</div>
		</div>

		<spring:bind path="organizator">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Organizator</label>
				<div class="col-sm-5">
					<form:select id="organizator" onchange="racunajStanjeRacuna()"
						path="organizator" class="form-control inputDropdown">
						<form:option value="none" label="--- Select ---" />
						<form:options items="${listaOrganizatora}" />
					</form:select>
					<form:errors path="organizator" class="control-label" />
				</div>
				<div class="col-sm-5"></div>
			</div>
		</spring:bind>

		<spring:bind path="bek">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Bek</label>
				<div class="col-sm-5">
					<form:select id="bek" onchange="racunajStanjeRacuna()" path="bek"
						class="form-control inputDropdown">
						<form:option value="none" label="--- Select ---" />
						<form:options items="${listaBekova}" />
					</form:select>
					<form:errors path="bek" class="control-label" />
				</div>
				<div class="col-sm-5"></div>
			</div>
		</spring:bind>

		<spring:bind path="centar">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Centar</label>
				<div class="col-sm-5">
					<form:select id="centar" onchange="racunajStanjeRacuna()"
						path="centar" class="form-control inputDropdown">
						<form:option value="none" label="--- Select ---" />
						<form:options items="${listaCentra}" />
					</form:select>
					<form:errors path="centar" class="control-label" />
				</div>
				<div class="col-sm-5"></div>
			</div>
		</spring:bind>

		<spring:bind path="krilo">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Krilo</label>
				<div class="col-sm-5">
					<form:select id="krilo" onchange="racunajStanjeRacuna()"
						path="krilo" class="form-control inputDropdown">
						<form:option value="none" label="--- Select ---" />
						<form:options items="${listaKrila}" />
					</form:select>
					<form:errors path="krilo" class="control-label" />
				</div>
				<div class="col-sm-5"></div>
			</div>
		</spring:bind>

		<spring:bind path="krilniCentar">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Krilni Centar</label>
				<div class="col-sm-5">
					<form:select id="krilniCentar" onchange="racunajStanjeRacuna()"
						path="krilniCentar" class="form-control inputDropdown">
						<form:option value="none" label="--- Select ---" />
						<form:options items="${listaKrilnogCentra}" />
					</form:select>
					<form:errors path="krilniCentar" class="control-label" />
				</div>
				<div class="col-sm-5"></div>
			</div>
		</spring:bind>



		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" id="registriraj"
					class="btn-lg btn-primary pull-mid">Registriraj</button>
			</div>
		</div>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form:form>


</div>

<%@include file="fragments/footer.jsp"%>
</html>