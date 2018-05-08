<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<%@include file="fragments/header.jsp"%>
	<div style="float: right;">
			<a href="/virtual_liga_kosarka/osobnaTehKomisija" class="btn btn-default">Natrag</a>
		</div>
<div class="container mid">

	<h3 style="font-style: italic">
		Odigrane utakmice<br> <br>
	</h3>
</div>
<c:url value="/j_spring_security_logout" var="logoutUrl" />
<form action="${logoutUrl}" method="post" id="logoutForm">
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />
</form>
<div class="container">
	<div class="list-group">
		<c:forEach items="${listaFormiOdigranihUtakmica}" var="utakmica">
			<a href="/virtual_liga_kosarka/osobnaTehKomisija/odigraneUtakmice/<c:out value="${utakmica.utakmicaId}" />" class="list-group-item col-sm-2">
						<c:if test="${utakmica.izabranIgracUtakmice}">
							<span class="badge">iz.</span>
						</c:if>
						
			<c:out value="${utakmica.utakmicaId}" />: <c:out
					value="${utakmica.ekipaDomacinIme}" />-<c:out
					value="${utakmica.ekipaGostIme}" /></a>
		</c:forEach>
	</div>
	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>
</div>

<%@include file="fragments/footer.jsp"%>

</html>