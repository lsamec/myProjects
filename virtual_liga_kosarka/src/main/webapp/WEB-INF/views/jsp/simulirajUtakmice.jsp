<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<%@include file="fragments/header.jsp"%>
<p hidden id="status_simulatora">${status_simulatora}</p>
<div class="container mid">

	<h3 style="font-style: italic">
		Simuliranje utakmica<br> <br>
	</h3>
</div>
<div class="container midLD">

	<h4 style="font-style: italic">
		Broj dosad simuliranih utakmica: ${status_simulatora - 1}<br>
	</h4>
</div>
<div class="container midLD">

	<h4 style="font-style: italic">
		Broj preostalih nesimuliranih utakmica: ${66 + 1 - status_simulatora}<br> <br><br>
	</h4>
</div>

<div class="container mid">
	<form:form method="POST" modelAttribute="brojUtakmicaForma"
		acceptCharset="UTF-8" enctype="multipart/form-data"
		action="simulirajNekolikoUtakmica?${_csrf.parameterName}=${_csrf.token}">
		Broj idućih utakmica za simulaciju:<br>
		<form:input type="text" onchange="odobriSimulaciju()"
			path="brojUtakmica" id="brojUtakmica" />
		<br />
		<br>
		<button type="submit" id="simuliraj"
			class="btn-lg btn-warning pull-mid" disabled>Simuliraj</button>

	</form:form>
</div>
<br>
<br>
<div class="container">
	<div class="list-group">
		<a href="/virtual_liga_kosarka/osobnaAdmin"
			class="list-group-item col-sm-2 pull-middle">Natrag</a>
	</div>
</div>
<script>
	function odobriSimulaciju() {
		var simulirajGumb = document.getElementById("simuliraj");
		var status_simulatora = parseInt(document
				.getElementById("status_simulatora").innerHTML);
		var brojUtakmica = document.getElementById("brojUtakmica").value;
		if (!isNaN(parseInt(brojUtakmica))) {
			if (parseInt(brojUtakmica) <= 66 - status_simulatora + 1) {

				simulirajGumb.className = "btn-lg btn-primary pull-mid";
				simulirajGumb.disabled = false;
			} else {
				simulirajGumb.className = "btn-lg btn-warning pull-mid";
				simulirajGumb.disabled = true;
			}
		} else {
			simulirajGumb.className = "btn-lg btn-warning pull-mid";
			simulirajGumb.disabled = true;
		}
	}
</script>
</html>