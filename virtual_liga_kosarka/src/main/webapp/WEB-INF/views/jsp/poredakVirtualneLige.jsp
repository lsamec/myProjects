<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<%@include file="fragments/header.jsp"%>

<div class="container mid">
	<div class="col-md-4">
		<h3 class="mid" style="font-style: italic">
			Poredak virtualne lige<br>
			<br>
		</h3>
	</div>
</div>
<div class="container midL">
	<div class="list-group">
	<a
			href="/virtual_liga_kosarka/osobnaNatjecatelj"
			class="list-group-item col-sm-2">Natrag</a>
		<a href="/virtual_liga_kosarka/osobnaNatjecatelj/poredakPoDrzavi"
			class="list-group-item col-sm-3">Poredak u mojoj državi (${drzava.ime_drzava})</a> <a
			href="/virtual_liga_kosarka/osobnaNatjecatelj/poredakNavijaca"
			class="list-group-item col-sm-3">Poredak navijača ekipe
			${podupireEkipu.ime_ekipa}</a>
			
	</div>
</div>
<div class="container mid">
	<table class="table table-bordered" style="width: 50%">
		<thead>
			<tr>
				<th width="100">#</th>
				<th width="100">Ekipa - Natjecatelj</th>
				<th width="100">Bodovi</th>
			</tr>
		</thead>
		<tbody>
			<c:set var="count" value="1" scope="page" />
			<c:forEach items="${listaNatKor}" var="natKor">
				<tr>
					<td><c:out value="${count}" /></td>
					<td><c:out
							value="${natKor.natjecatelj.naziv_virt_ekipe} - ${natKor.korisnik.korisnicko_ime}" /></td>
					<td><c:out value="${natKor.natjecatelj.bodovi_virt_ekipe}" /></td>
				</tr>
				<c:set var="count" value="${count + 1}" scope="page" />
			</c:forEach>
		</tbody>
	</table>
</div>


<%@include file="fragments/footer.jsp"%>

</html>