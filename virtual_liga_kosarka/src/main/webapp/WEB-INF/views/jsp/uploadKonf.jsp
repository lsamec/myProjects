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
		Učitavanje konfiguracije<br>
		<br>
	</h3>
</div>
<div class="container mid">
	<form method="POST" enctype="multipart/form-data" action="uploadKonfiguraciju?${_csrf.parameterName}=${_csrf.token}">
		Datoteka za upload:<br> <input type="file" name="file"/><br /><br> <button type="submit" id="ucitaj"
					class="btn-lg btn-primary pull-mid">Učitaj</button>

	</form>
</div>
<br>
<br>
<div class="container">
	<div class="list-group">
		<a href="/virtual_liga_kosarka/osobnaAdmin"
			class="list-group-item col-sm-2 pull-middle">Natrag</a>
	</div>
</div>
</html>