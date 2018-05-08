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
		Nedozvoljen pristup<br> <br>
	</h2>
</div>
<div class="container midLD">
		<c:choose>
		<c:when test="${empty username}">
			<h3>Nemate pristup ovoj stranici!</h3>
		</c:when>
		<c:otherwise>
			<h3>Korisnicko ime : ${username} <br/>Nemate pristup ovoj stranici!</h3>
		</c:otherwise>
	</c:choose>
</div>
<br>
<br>
<div class="container mid">
	<div class="list-group">
		<a href="/virtual_liga_kosarka/"
			class="list-group-item col-sm-2">Natrag</a>
	</div>
</div>

<%@include file="fragments/footer.jsp"%>
</html>