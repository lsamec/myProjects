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
		Moji podaci<br> <br>
	</h3>
</div>
<div class="container midL">

	<table class="tablePodaci">
		<tr>
			<td>Korisničko ime:</td>
			<td>${korisnik.korisnicko_ime}</td>
		</tr>
		<tr>
			<td>Ime:</td>
			<td>${natjecatelj.ime}</td>
		</tr>
		<tr>
			<td>Prezime:</td>
			<td>${natjecatelj.prezime}</td>
		</tr>
		<tr>
			<td>E-mail:</td>
			<td>${natjecatelj.e_mail}</td>
		</tr>
		<tr>
			<td>Podupirem ekipu:</td>
			<td>${natjecatelj.podupire_ekipu.ime_ekipa}</td>
		</tr>
		<tr>
			<td>Virtualna ekipa:</td>
			<td>${natjecatelj.naziv_virt_ekipe}</td>
		</tr>
		<tr>
			<td>Država:</td>
			<td>${natjecatelj.drzava.ime_drzava}</td>
		</tr>
	</table>
</div>
<br>
<br>
<div class="container midL">
	<div class="list-group">
		<a href="/virtual_liga_kosarka/osobnaNatjecatelj"
			class="list-group-item col-sm-2">Natrag</a>
	</div>
</div>

<%@include file="fragments/footer.jsp"%>

</html>