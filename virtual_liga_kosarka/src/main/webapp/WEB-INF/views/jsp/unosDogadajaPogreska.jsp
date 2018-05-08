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
		Pogreska pri unosu dogadaja<br> <br>
	</h3>
</div>

<div class="container midLL">

	<h4 style="font-style: italic">
		Utakmica :  #${utakmica.utakmica_id} ${utakmica.ekipa_domacin.ime_ekipa} - ${utakmica.ekipa_gost.ime_ekipa}<br> <br>
	</h4>
	<h4 style="font-style: italic">
		Poruka: ${poruka}
	</h4>
</div>	
<br>
<br>
<div class="container midL">
	<div class="list-group">
		<a href="/virtual_liga_kosarka/osobnaSlOsoba/uredivanjeUtakmica"
			class="list-group-item col-sm-3">Natrag na popis utakmica</a>
	</div>
</div>

<%@include file="fragments/footer.jsp"%>

</html>