<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Unos dogadaja</title>
</head>
<style>
.column-left {
	float: left;
	width: 33%;
}

.column-right {
	float: right;
	width: 33%;
}

.column-center {
	display: inline-block;
	width: 33%;
}
</style>

<%@include file="fragments/header.jsp"%>
<body>
	<div style="float: right;">
			<a href="/virtual_liga_kosarka/osobnaSlOsoba/uredivanjeUtakmica" class="btn btn-default">Natrag</a>
		</div>
	<div class="container midLL">
		<div class="col-md-6">
			<h2 class="mid" style="font-style: italic">
				Unos događaja (<c:choose>
    <c:when test="${utakmica.rezultat == null}">
       nije odigrano
    </c:when>
    <c:otherwise>
        Rezultat: ${utakmica.rezultat}
    </c:otherwise>
</c:choose>)<br> <br>
			</h2>
		</div>
	</div>
	<div>
		<div class="column-left">
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Broj dresa</th>
						<th>Igrač (Domaćin) - ${utakmica.ekipa_domacin.ime_ekipa}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${igracD}" var="igrac">
						<tr>
							<td><c:out value="${igrac.broj_dresa}" /></td>
							<td><c:out value="${igrac.ime_igrac} ${igrac.prezime_igrac}" />
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>


		<div class="column-center">
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Broj dresa</th>
						<th>Igrač (Gost) - ${utakmica.ekipa_gost.ime_ekipa}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${igracG}" var="igrac2">
						<tr>
							<td><c:out value="${igrac2.broj_dresa}" /></td>
							<td><c:out
									value="${igrac2.ime_igrac} ${igrac2.prezime_igrac}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="column-right">
			<table class="table table-bordered" width="40%">
				<thead>
					<tr>
						<th width="20%">ID</th>
						<th width="20%">Događaji</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="count" value="1" scope="page" />
					<c:forEach items="${dogadaj}" var="dogadaj">
						<tr>
							<td><c:out value="${count}" /></td>
							<td><c:out value="${dogadaj.ime_dogadaj}" /></td>
						</tr>
						<c:set var="count" value="${count + 1}" scope="page" />
					</c:forEach>
				</tbody>
			</table>
				
		</div>
		<c:url
			value="/osobnaSlOsoba/unesiDogadajeUtakmice?${_csrf.parameterName}=${_csrf.token}"
			var="unesiDogadajeUtakmice" />
		<form action="${unesiDogadajeUtakmice}" method="post"
			id="unesiDogadajeUtakmice">
					<p  style="font-style: oblique">Upute za unošenje događaja:</p><p  style="font-style: italic"> u svaki redak slijedno unijeti po jedan događaj u obliku -><br>
			{Domacin(D)}/{Gost(G)}{Broj dresa} {ID događaja} {vrijeme(bez prefiksnih nula)}<br>
			primjer: D6 3 4:9 (domaći igrač sa brojem dresa 6 je ubacio tricu u četvrtoj minuti i devetoj sekundi)
		</p>
		<div class="container">
			<textarea rows="30" cols="100" name="dogadaji">${uneseniDogadaji}</textarea>
		</div>
			<input type="hidden" id="utakmicaId" name="utakmicaId" value="${utakmica.utakmica_id}"/>
		</form>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button onClick="unesiDogadaje()" id="unesiDogadaje"
					class="btn-lg btn-primary pull-mid">Unesi
					dogadaje</button>
			</div>
		</div>
	</div>
<script>
	function unesiDogadaje(){
		document.getElementById("unesiDogadajeUtakmice").submit();
	}
</script>
</body>
</html>