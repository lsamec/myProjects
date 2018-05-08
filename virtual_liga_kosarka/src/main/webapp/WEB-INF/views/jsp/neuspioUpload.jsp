<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<%@include file="fragments/header.jsp"%>

<div class="container mid">
	<div class="col-md-4">
		<h2 class="mid" style="font-style: italic">
			Neuspio upload<br>
		</h2>
	</div>
</div>
<br>
<div class="container mid">
	<div class="col-md-4">
		<h4 class="mid" style="font-style: italic">
			Poruka: ${message}<br>
		</h4>
	</div>
</div>

<div class="container">
	<div class="list-group">
		<a href="/virtual_liga_kosarka/osobnaAdmin"
			class="list-group-item col-sm-2 pull-middle">Natrag</a>
	</div>
</div>

<%@include file="fragments/footer.jsp"%>

</html>