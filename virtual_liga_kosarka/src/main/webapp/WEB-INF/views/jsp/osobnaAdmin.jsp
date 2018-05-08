<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>

<%@include file="fragments/header.jsp"%>
<p hidden id="postoji_konf">${postoji_konf}</p>
<p hidden id="odobren_konf">${odobren_konf}</p>
<p hidden id="postoji_sastav_ekipa">${postoji_sastav_ekipa}</p>
<p hidden id="igraci_ok">${igraci_ok}</p>
<p hidden id="moze_sim">${moze_sim}</p>
<p hidden id="odobrena_reg">${odobrena_reg}</p>
<p hidden id="zapoceta_liga">${zapoceta_liga}</p>

<c:url value="/osobnaAdmin/odobriKonfiguraciju?${_csrf.parameterName}=${_csrf.token}" var="odobriKonfiguraciju" />
	<form action="${odobriKonfiguraciju}" method="post" id="odobriKonfUrl">
</form>

<c:url value="/osobnaAdmin/obrisiKonfiguraciju?${_csrf.parameterName}=${_csrf.token}" var="obrisiKonfiguraciju" />
	<form action="${obrisiKonfiguraciju}" method="post" id="obrisiKonfUrl">
</form>

<c:url value="/osobnaAdmin/globalniReset?${_csrf.parameterName}=${_csrf.token}" var="globalniReset" />
	<form action="${globalniReset}" method="post" id="globalniResetUrl">
</form>

<c:url value="/osobnaAdmin/zapocniLigu?${_csrf.parameterName}=${_csrf.token}" var="zapocniLigu" />
	<form action="${zapocniLigu}" method="post" id="zapocniLiguUrl">
</form>

<c:url value="/osobnaAdmin/odobriRegistraciju?${_csrf.parameterName}=${_csrf.token}" var="odobriReg" />
	<form action="${odobriReg}" method="post" id="odobriRegUrl">
</form>

<div class="container mid">

	<h2 style="font-style: italic">
		Osobna stranica administratora<br> <br>
	</h2>
	<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
</div>
<div class="container">
	<div class="list-group">
		<a href="/virtual_liga_kosarka/osobnaAdmin/ucitajKonfiguraciju"
			id="ucitajKonf" class="list-group-item col-sm-2 disabled">Učitaj
			konfiguraciju</a> <a href="#"
			onClick="formSubmitOdobri()"
			id="odobriKonf" class="list-group-item col-sm-2 disabled">Odobri
			konfiguraciju</a> <a
			href="#"
			onClick="formSubmitObrisi()" id="obrisiKonf" class="list-group-item col-sm-2 disabled">Obrisi
			konfiguraciju</a> <a
			href="/virtual_liga_kosarka/osobnaAdmin/ucitajSastavEkipa"
			id="ucitajSastav" class="list-group-item col-sm-2 disabled">Učitaj
			sastav ekipa</a> <a href="#" id="odobriReg"
			class="list-group-item col-sm-2 disabled" onClick="formSubmitOdobriReg()"></a> 
			<a
			href="#" onClick="formSubmitZapocniLigu()" id="zapocniLigu"
			class="list-group-item col-sm-2 disabled">Započni ligu</a> 
	</div>
</div>
<br>
<br>
<div class="container">
	<div class="list-group">
		<a
			href="/virtual_liga_kosarka/osobnaAdmin/ucitajProbnePodatke"
			id="ucitajProbno" class="list-group-item col-sm-2 disabled">Učitaj
			probne podatke (potrebna odobrena konfiguracija)</a><a
			href="/virtual_liga_kosarka/osobnaAdmin/simulirajUtakmice" id="simulirajUtakmice"
			class="list-group-item col-sm-2 disabled">Simuliraj utakmice</a>

	</div>
</div>
<br>
<br>
<div class="container">
	<div class="list-group">
		<a
			href="#" id="globalniReset" onClick="formSubmitGlobalniReset()"
			class="list-group-item col-sm-2">Globalni reset</a> <a href="#"
			onClick="formSubmit()" class="list-group-item col-sm-2">Odjava</a>

	</div>
</div>
<script>
		function disablingFunction() {
			var postoji_konf = document.getElementById("postoji_konf").innerHTML;
			var odobren_konf = document.getElementById("odobren_konf").innerHTML;
			var postoji_sastav_ekipa = document.getElementById("postoji_sastav_ekipa").innerHTML;
			var igraci_ok = document.getElementById("igraci_ok").innerHTML;
			var moze_sim = document.getElementById("moze_sim").innerHTML;
			var odobrena_reg = document.getElementById("odobrena_reg").innerHTML;
			var zapoceta_liga = document.getElementById("zapoceta_liga").innerHTML;
			
			var ucitajKonf = document.getElementById("ucitajKonf");
			var odobriKonf = document.getElementById("odobriKonf");
			var obrisiKonf = document.getElementById("obrisiKonf");
			var ucitajSastav = document.getElementById("ucitajSastav");
			var ucitajProbno = document.getElementById("ucitajProbno");
			var odobriReg = document.getElementById("odobriReg");
			var zapocniLigu = document.getElementById("zapocniLigu");
			var simulirajUtakmice = document.getElementById("simulirajUtakmice");
			
			if(postoji_konf == "false"){
				ucitajKonf.className = "list-group-item col-sm-2";
			} else{
				ucitajKonf.className = "list-group-item col-sm-2 disabled";
			}
			
			if(odobren_konf == "false" && postoji_konf == "true"){
				odobriKonf.className = "list-group-item col-sm-2";
			} else{
				odobriKonf.className = "list-group-item col-sm-2 disabled";
			}
			
			if(postoji_konf == "true" && zapoceta_liga == "false" && moze_sim == "false"){
				obrisiKonf.className = "list-group-item col-sm-2";
			} else{
				obrisiKonf.className = "list-group-item col-sm-2 disabled";
			}
			
			if(postoji_sastav_ekipa == "false"){
				ucitajSastav.className = "list-group-item col-sm-2";
			}else{
				ucitajSastav.className = "list-group-item col-sm-2 disabled";
			}
			
			if(odobren_konf == "true" && postoji_sastav_ekipa == "false"){
				ucitajProbno.className = "list-group-item col-sm-2";
			}else{
				ucitajProbno.className = "list-group-item col-sm-2 disabled";
			}
			
			if(igraci_ok == "true" && odobren_konf == "true" && zapoceta_liga == "false" && moze_sim == "false"){
				odobriReg.className = "list-group-item col-sm-2";
			}else{
				odobriReg.className = "list-group-item col-sm-2 disabled";
			}
			
			if(igraci_ok == "true" && odobren_konf == "true" && zapoceta_liga == "false" && moze_sim == "false"){
				zapocniLigu.className = "list-group-item col-sm-2";
			}else{
				zapocniLigu.className = "list-group-item col-sm-2 disabled";
			}
			
			//zamjena - invert u controlleru
			if(odobrena_reg == "true"){
				odobriReg.innerHTML = "Zabrani registraciju";
			} else {
				odobriReg.innerHTML = "Odobri registraciju (potrebni igrači s vrijednostima)";
			}
			
			if(moze_sim == "true"){
				simulirajUtakmice.className = "list-group-item col-sm-2";
			} else{
				simulirajUtakmice.className = "list-group-item col-sm-2 disabled";
			}
						
		}

		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
		function formSubmitOdobri() {
			document.getElementById("odobriKonfUrl").submit();
		}
		function formSubmitZapocniLigu(){
			document.getElementById("zapocniLiguUrl").submit();
		}
		function formSubmitObrisi() {
			document.getElementById("obrisiKonfUrl").submit();
		}
		function formSubmitGlobalniReset() {
			if(confirm('Jeste li sigurni? Ova akcija će obrisati sve podatke o virtualnoj ligi.')){
				document.getElementById("globalniResetUrl").submit();
			}
		}
		function formSubmitOdobriReg(){
			document.getElementById("odobriRegUrl").submit();
		}
		window.onload = disablingFunction;
	</script>
<spring:url value="/resources/bootstrap/js/jquery-1.11.3.min.css"
	var="jqueryMinCss" />
<link href="${jqueryMinCss}" rel="stylesheet" />

<%@include file="fragments/footer.jsp"%>

</html>