<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<%@include file="fragments/header.jsp"%>
<div style="float: right;">
		<a href="/virtual_liga_kosarka/osobnaSlOsoba/" class="btn btn-default">Natrag</a>
	</div>
<div class="container mid">
	<div class="container">
		<div class="col-md-4">
			<h2 class="mid" style="font-style: italic">
				Unos konfiguracije<br> <br>
			</h2>
		</div>
	</div>


	<spring:url value="/osobnaSlOsoba/unesiKonfiguraciju"
		var="unesiKonfiguraciju" />

	<form:form class="form-horizontal" method="post"
		modelAttribute="konfiguracijaForma" action="${unesiKonfiguraciju}"
		acceptCharset="UTF-8">

			<div class="container">
		<div class="col-md-4">
			<h4 class="mid" style="font-style: italic">
				Unesite proračun za natjecatelje<br> <br>
			</h4>
		</div>
	</div>
		
		<spring:bind path="proracun">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Proračun</label>
				<div class="col-sm-10">
					<form:input path="proracun" type="text"
						class="form-control inputForKonf" id="proracun" />
					<form:errors path="proracun" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<div class="container">
			<div class="col-md-4">
				<h4 class="mid" style="font-style: italic">
					Unesite bodovanje za pojedini događaj<br> <br>
				</h4>
			</div>
		</div>
		<spring:bind path="postignutKos">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Postignut koš</label>
				<div class="col-sm-10">
					<form:input path="postignutKos" type="text"
						class="form-control inputForKonf" id="postignutKos" />
					<form:errors path="postignutKos" class="control-label" />
				</div>
			</div>
		</spring:bind>
		<spring:bind path="kosIzSlobodnogBacanja">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Koš iz slobodnog bacanja</label>
				<div class="col-sm-10">
					<form:input path="kosIzSlobodnogBacanja" type="text"
						class="form-control inputForKonf" id="kosIzSlobodnogBacanja" />
					<form:errors path="kosIzSlobodnogBacanja" class="control-label" />
				</div>
			</div>
		</spring:bind>
		<spring:bind path="trica">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Trica</label>
				<div class="col-sm-10">
					<form:input path="trica" type="text"
						class="form-control inputForKonf" id="trica" />
					<form:errors path="trica" class="control-label" />
				</div>
			</div>
		</spring:bind>
		<spring:bind path="skok">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Skok</label>
				<div class="col-sm-10">
					<form:input path="skok" type="text"
						class="form-control inputForKonf" id="skok" />
					<form:errors path="skok" class="control-label" />
				</div>
			</div>
		</spring:bind>
		<spring:bind path="oduzimanjeLopte">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Oduzimanje lopte</label>
				<div class="col-sm-10">
					<form:input path="oduzimanjeLopte" type="text"
						class="form-control inputForKonf" id="oduzimanjeLopte" />
					<form:errors path="oduzimanjeLopte" class="control-label" />
				</div>
			</div>
		</spring:bind>
		<spring:bind path="dodavanje">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Dodavanje</label>
				<div class="col-sm-10">
					<form:input path="dodavanje" type="text"
						class="form-control inputForKonf" id="dodavanje" />
					<form:errors path="dodavanje" class="control-label" />
				</div>
			</div>
		</spring:bind>
		<spring:bind path="blokada">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Blokada</label>
				<div class="col-sm-10">
					<form:input path="blokada" type="text"
						class="form-control inputForKonf" id="blokada" />
					<form:errors path="blokada" class="control-label" />
				</div>
			</div>
		</spring:bind>
		<spring:bind path="igracUtakmice">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Igrač utakmice</label>
				<div class="col-sm-10">
					<form:input path="igracUtakmice" type="text"
						class="form-control inputForKonf" id="igracUtakmice" />
					<form:errors path="igracUtakmice" class="control-label" />
				</div>
			</div>
		</spring:bind>
		<spring:bind path="izgubljenaLopta">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Izgubljena lopta</label>
				<div class="col-sm-10">
					<form:input path="izgubljenaLopta" type="text"
						class="form-control inputForKonf" id="izgubljenaLopta" />
					<form:errors path="izgubljenaLopta" class="control-label" />
				</div>
			</div>
		</spring:bind>
		<spring:bind path="promasenoSlobodnoBacanje">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Promašeno slobodno bacanje</label>
				<div class="col-sm-10">
					<form:input path="promasenoSlobodnoBacanje" type="text"
						class="form-control inputForKonf" id="promasenoSlobodnoBacanje" />
					<form:errors path="promasenoSlobodnoBacanje" class="control-label" />
				</div>
			</div>
		</spring:bind>
		<spring:bind path="promasenSutZaDva">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Promašen šut za dva</label>
				<div class="col-sm-10">
					<form:input path="promasenSutZaDva" type="text"
						class="form-control inputForKonf" id="promasenSutZaDva" />
					<form:errors path="promasenSutZaDva" class="control-label" />
				</div>
			</div>
		</spring:bind>
		<spring:bind path="promasenaTrica">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Promašena trica</label>
				<div class="col-sm-10">
					<form:input path="promasenaTrica" type="text"
						class="form-control inputForKonf" id="promasenaTrica" />
					<form:errors path="promasenaTrica" class="control-label" />
				</div>
			</div>
		</spring:bind>
		<spring:bind path="tehnickaPogreska">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Tehnička pogreška</label>
				<div class="col-sm-10">
					<form:input path="tehnickaPogreska" type="text"
						class="form-control inputForKonf" id="tehnickaPogreska" />
					<form:errors path="tehnickaPogreska" class="control-label" />
				</div>
			</div>
		</spring:bind>
		<spring:bind path="osobnaPogreska">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Osobna pogreska</label>
				<div class="col-sm-10">
					<form:input path="osobnaPogreska" type="text"
						class="form-control inputForKonf" id="osobnaPogreska" />
					<form:errors path="osobnaPogreska" class="control-label" />
				</div>
			</div>
		</spring:bind>
		<spring:bind path="iskljucenje">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Isključenje</label>
				<div class="col-sm-10">
					<form:input path="iskljucenje" type="text"
						class="form-control inputForKonf" id="iskljucenje" />
					<form:errors path="iskljucenje" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<br>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" id="unesi" class="btn-lg btn-primary pull-mid">Unesi</button>
			</div>
		</div>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form:form>
</div>

<%@include file="fragments/footer.jsp"%>
</html>