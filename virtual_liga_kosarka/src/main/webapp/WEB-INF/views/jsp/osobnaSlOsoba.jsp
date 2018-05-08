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

<div class="container mid">

	<h2 style="font-style: italic">
		Osobna stranica službene osobe<br> <br>
	</h2>
</div>
<c:url value="/j_spring_security_logout" var="logoutUrl" />
<form action="${logoutUrl}" method="post" id="logoutForm">
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />
</form>
<div class="container midLL">
	<div class="list-group">
			<a href="/virtual_liga_kosarka/osobnaSlOsoba/postaviKonfiguraciju"
			class="list-group-item col-sm-2" id="postaviKonf">Postavi konfiguraciju</a>
			<a href="/virtual_liga_kosarka/osobnaSlOsoba/postaviVrijednostiIgraca"
			class="list-group-item col-sm-2" id="postaviCijene">Postavi cijene (potrebni igraci)</a> 
			<a href="/virtual_liga_kosarka/osobnaSlOsoba/uredivanjeUtakmica"
			class="list-group-item col-sm-2" id="urediUtakmicu">Uređivanje utakmica</a> 
			<a href="#"
			onClick="formSubmit()" class="list-group-item col-sm-2">Odjava</a>
	</div>
</div>
<script>
	function disablingFunction() {
		var odobren_konf = document.getElementById("odobren_konf").innerHTML;
		var postoji_sastav_ekipa = document
				.getElementById("postoji_sastav_ekipa").innerHTML;
		var moze_sim = document.getElementById("moze_sim").innerHTML;
		var zapoceta_liga = document.getElementById("zapoceta_liga").innerHTML;

		var postaviCijene = document.getElementById("postaviCijene");
		var postaviKonf = document.getElementById("postaviKonf");
		var urediUtakmicu = document.getElementById("urediUtakmicu");

		if(postoji_sastav_ekipa == "true" && zapoceta_liga == "false" && moze_sim == "false"){
			postaviCijene.className = "list-group-item col-sm-2";
		} else{
			postaviCijene.className = "list-group-item col-sm-2 disabled";
		}
		
		if(odobren_konf == "false"){
			postaviKonf.className = "list-group-item col-sm-2";
		} else{
			postaviKonf.className = "list-group-item col-sm-2 disabled";
		}
		
		if(zapoceta_liga == "true"){
			urediUtakmicu.className = "list-group-item col-sm-2";
		} else{
			urediUtakmicu.className = "list-group-item col-sm-2 disabled";
		}

	}

	function formSubmit() {
		document.getElementById("logoutForm").submit();
	}
	window.onload = disablingFunction;
</script>
<%@include file="fragments/footer.jsp"%>

</html>