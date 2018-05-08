<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<%@include file="fragments/header.jsp"%>
	<div style="float: right;">
			<a href="/virtual_liga_kosarka/osobnaTehKomisija/odigraneUtakmice" class="btn btn-default">Natrag</a>
		</div>
<div class="container mid">

	<h3 style="font-style: italic">
		Izbor igrača utakmice (Utakmica ${utakmicaId})<br>
		<br>
	</h3>
</div>
<div class="container midL">
	<c:if test="${not empty igracUtakmice}">
		<h5 style="color: red">
			Trenutni igrač utakmice: ${igracUtakmice} <br>
			<br><br><br><br>
		</h5>
	</c:if>
</div>

<div class="container">
	
	<spring:url value="/osobnaTehKomisija/izborIgracaUtakmice/${utakmicaId}" var="izborIgracaUtakmice" />
	<form:form modelAttribute="igracUtakmiceForma" name='igracUtakmiceForma' class="form-horizontal"
		action="${izborIgracaUtakmice}" method='POST'>
		<div class="form-group">
			<label class="col-sm-2 control-label">Igrač utakmice</label>
			<div class="col-sm-5">
				<form:select path="igracUtakmice" id="igracUtakmice"  class="form-control inputDropdown">
					<form:options items="${listaIgraca}" />
				</form:select>
			</div>
			<div class="col-sm-5"></div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" id="izaberi"
					class="btn-lg btn-primary pull-mid">Izaberi</button>
			</div>
		</div>
	</form:form>
</div>
<br>

<%@include file="fragments/footer.jsp"%>

</html>