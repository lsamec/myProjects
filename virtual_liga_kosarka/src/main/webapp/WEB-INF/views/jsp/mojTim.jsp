<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<%@include file="fragments/header.jsp"%>
<div class="container mid">

	<h3 style="font-style: italic">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Moj tim (ekipa ${natjecatelj.naziv_virt_ekipe}) <br> <br>
	</h3>
</div>

<div class="container midL">
	<div class="col-md-4">
		<h4 class="mid" style="font-style: italic">
			Ukupno bodova: ${natjecatelj.bodovi_virt_ekipe}<br>
		</h4>
		<table class="table table-bordered">
			<thead>
				<tr>
					<th width="100">Pozicija</th>
					<th width="100">Ime</th>
					<th width="100">Prezime</th>
					<th width="100">Ekipa</th>
					<th width="100">Broj dresa</th>
					<th width="100">Bodovi</th>
					<th width="100">Vrijednost($)</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>centar</td>
					<td>${centar.ime_igrac}</td>
					<td>${centar.prezime_igrac}</td>
					<td>${centar.ekipa.ime_ekipa}</td>
					<td>${centar.broj_dresa}</td>
					<td>${centar.uk_bodovi}</td>
					<td>${centar.vrijednost}</td>
				</tr>
				<tr>
					<td>bek</td>
					<td>${bek.ime_igrac}</td>
					<td>${bek.prezime_igrac}</td>
					<td>${bek.ekipa.ime_ekipa}</td>
					<td>${bek.broj_dresa}</td>
					<td>${bek.uk_bodovi}</td>
					<td>${bek.vrijednost}</td>
				</tr>
				<tr>
					<td>krilo</td>
					<td>${krilo.ime_igrac}</td>
					<td>${krilo.prezime_igrac}</td>
					<td>${krilo.ekipa.ime_ekipa}</td>
					<td>${krilo.broj_dresa}</td>
					<td>${krilo.uk_bodovi}</td>
					<td>${krilo.vrijednost}</td>
				</tr>
				<tr>
					<td>organizator</td>
					<td>${organizator.ime_igrac}</td>
					<td>${organizator.prezime_igrac}</td>
					<td>${organizator.ekipa.ime_ekipa}</td>
					<td>${organizator.broj_dresa}</td>
					<td>${organizator.uk_bodovi}</td>
					<td>${organizator.vrijednost}</td>
				</tr>
				<tr>
					<td>krilni centar</td>
					<td>${krilniCentar.ime_igrac}</td>
					<td>${krilniCentar.prezime_igrac}</td>
					<td>${krilniCentar.ekipa.ime_ekipa}</td>
					<td>${krilniCentar.broj_dresa}</td>
					<td>${krilniCentar.uk_bodovi}</td>
					<td>${krilniCentar.vrijednost}</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<br>
<br>
<div class="container mid">
	<div class="list-group">
		<a href="/virtual_liga_kosarka/osobnaNatjecatelj"
			class="list-group-item col-sm-2">Natrag</a>
	</div>
</div>

<%@include file="fragments/footer.jsp"%>
</html>