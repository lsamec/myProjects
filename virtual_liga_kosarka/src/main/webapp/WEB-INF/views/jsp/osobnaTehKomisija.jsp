<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<%@include file="fragments/header.jsp"%>
<div class="container mid">

	<h2 style="font-style: italic">
		Osobna stranica tehničke komisije<br> <br>
	</h2>
	<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>

	<div class="list-group">
		<a href="/virtual_liga_kosarka/osobnaTehKomisija/odigraneUtakmice" class="list-group-item col-sm-2">Odigrane utakmice</a>
		<a href="#" onClick="formSubmit()" class="list-group-item col-sm-2">Odjava</a>
	</div>
	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>
</div>

<%@include file="fragments/footer.jsp"%>

</html>