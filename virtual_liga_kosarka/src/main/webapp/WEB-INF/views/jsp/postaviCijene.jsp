<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Unos vrijednosti</title>
</head>
<%@include file="fragments/header.jsp"%>
<body>
<p hidden id="vrijednosti_string">${vrijednosti_string}</p>
<div class="container mid">

	<h3 style="font-style: italic">
		Postavljanje cijena(vrijednosti)<br> <br>
	</h3>
</div>
	<div class="container mid col-md-4">

		<table class="table table-bordered" width="60%">
			<thead>
				<tr>
					<th width="5%">#</th>
					<th width="15%">Igrač</th>
					<th width="10%">Vrijednost</th>
				</tr>
			</thead>

			<tbody id="tablicaIgraca">
				<c:set var="count" value="1" scope="page" />
				<c:forEach items="${igraci}" var="igrac">
					<tr>
						<td><c:out value="${count}" /></td>
						<td><c:out value="${igrac.ime_igrac} ${igrac.prezime_igrac}" />
						</td>
						<td><input onchange="odobriPostavljanje(${igrac.igrac_id})"
							type="text" id="${igrac.igrac_id}" value=""></td>
					</tr>
					<c:set var="count" value="${count + 1}" scope="page" />
				</c:forEach>
			</tbody>

		</table>
		<!-- </div>-->
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button onClick="posaljiVrijednosti()" id="postaviVrijednosti"
					class="btn-lg btn-warning pull-mid" disabled>Postavi vrijednosti</button>
			</div>
		</div>
	</div>
	<div style="float: right;">
		<a href="/virtual_liga_kosarka/osobnaSlOsoba/" class="btn btn-default">Natrag</a>
	</div>
	<div style="float: right;">
		<button onClick="postaviProsjecne()" class="btn btn-default">Postavi prosječne vrijednosti (ne šalje)</button>
	</div>
	<c:url value="/osobnaSlOsoba/upisiVrijednostiIgraca?${_csrf.parameterName}=${_csrf.token}" var="upisiVrijednostiIgraca" />
	<form action="${upisiVrijednostiIgraca}" method="post"
		id="upisiVrijednostiIgraca">
		<input type="hidden" id="vrijednosti" name="vrijednosti" />
	</form>

	<script>
	function posaljiVrijednosti(){
		var igraciNode = document.getElementsByTagName('input');
		var igraciNodeLength = igraciNode.length;
		var brojIgraca = new Array();
		for(var i=0;i<igraciNodeLength;i++){
			if(!isNaN(parseInt(igraciNode[i].id))){
				brojIgraca.push(igraciNode[i].id);
			}
		}
		var stringVrijednosti="";
		var brojIgracaLength = brojIgraca.length;
		for(var i=0;i<brojIgracaLength;i++){
			var vrijednost = document.getElementById(brojIgraca[i].toString()).value;
			if(vrijednost){
					stringVrijednosti = stringVrijednosti.concat(brojIgraca[i].toString()+":"+vrijednost.toString()+";");
			}
		}
		if(stringVrijednosti){
			stringVrijednosti = stringVrijednosti.substring(0,stringVrijednosti.length-1);
			document.getElementById("vrijednosti").value = stringVrijednosti;
			document.getElementById("upisiVrijednostiIgraca").submit();
		}
	}
	function odobriPostavljanje(igracIdString){
		var postaviVrijednostiGumb = document.getElementById("postaviVrijednosti");
		var svePrazno = "true";
		var igraciNode = document.getElementsByTagName('input');
		var igraciNodeLength = igraciNode.length;
		var brojIgraca = new Array();
		for(var i=0;i<igraciNodeLength;i++){
			if(!isNaN(parseInt(igraciNode[i].id))){
				brojIgraca.push(igraciNode[i].id);
			}
		}
		var brojIgracaLength = brojIgraca.length;
		for(var i=0;i<brojIgracaLength;i++){
			var vrijednost = document.getElementById(brojIgraca[i].toString()).value;
			if(vrijednost){
				svePrazno = "false";
			}
		}
		if(svePrazno == "true"){
			postaviVrijednostiGumb.className = "btn-lg btn-warning pull-mid";
			postaviVrijednostiGumb.disabled = true;
			return;
		}
		var igracPoljeId = igracIdString; 
		var vrijednost = document.getElementById(igracPoljeId).value;
		if (!isNaN(parseInt(vrijednost)) || !vrijednost.length){
			if(parseInt(vrijednost) > 0 || !vrijednost.length){					
				postaviVrijednostiGumb.className = "btn-lg btn-primary pull-mid";
				postaviVrijednostiGumb.disabled = false;
			} else {
				postaviVrijednostiGumb.className = "btn-lg btn-warning pull-mid";
				postaviVrijednostiGumb.disabled = true;
			}
		} else {
			postaviVrijednostiGumb.className = "btn-lg btn-warning pull-mid";
			postaviVrijednostiGumb.disabled = true;
		}
	}
	function postaviTrenutne(){
		var vrijednostiString = document.getElementById("vrijednosti_string").innerHTML;
		if(vrijednostiString){
		var paroviIdVrijednost = vrijednostiString.split(";");
		for(var parIdVrijednost in paroviIdVrijednost){
			var id = paroviIdVrijednost[parIdVrijednost].split(":")[0];
			var vrijednost = paroviIdVrijednost[parIdVrijednost].split(":")[1];
			var igracPoljeId = id; 
			document.getElementById(igracPoljeId).value = vrijednost;
		}
	}
	}
	function postaviProsjecne(){
		var igraciNode = document.getElementsByTagName('input');
		var igraciNodeLength = igraciNode.length;
		var brojIgraca = new Array();
		for(var i=0;i<igraciNodeLength;i++){
			if(!isNaN(parseInt(igraciNode[i].id))){
				brojIgraca.push(igraciNode[i].id);
			}
		}
		var brojIgracaLength = brojIgraca.length;
		for(var i=0;i<brojIgracaLength;i++){
			document.getElementById(brojIgraca[i].toString()).value = 2;			
		}
		var postaviVrijednostiGumb = document.getElementById("postaviVrijednosti");
		postaviVrijednostiGumb.className = "btn-lg btn-primary pull-mid";
		postaviVrijednostiGumb.disabled = false;
	}
	window.onload = postaviTrenutne;
</script>
</body>
<%@include file="fragments/footer.jsp"%>
</html>