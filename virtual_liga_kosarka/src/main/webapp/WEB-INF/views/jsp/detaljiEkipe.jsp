<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Utakmice</title>
</head>
<%@include file="fragments/header.jsp" %>
<p hidden id="holder">${ekipa}</p>
<p hidden id="holder2">${igrac}</p>

<script type="text/javascript">
	
	$(document).ready(function(){
		setInterval(function(){
			$.ajax({
				url : "/virtual_liga_kosarka/provjeriSveUtakmice.html",
				dataType: 'html',
				//contentType: "text/html;charset=utf-16",
				success : function(data){
					    var json = $.parseJSON(data);
					    //alert(utf8_encode(data));
					    $('#myTableUtakmiceE > tbody').html("");
					    var ekipa = document.getElementById("holder").innerHTML;
					    //document.getElementById("proba").innerHTML="asdfČČČ";
					    //alert(ekipa);
					    var j =1;
					    //alert(json[0].igrac_utakmice);
        				for (var i=0;i<json.length;++i)
        				{
        					if(json[i].ekipa_domacin != ekipa && json[i].ekipa_gost != ekipa){
        						continue;
        					}
        					var rez = json[i].rezultat;
        					if(rez == ""){
        						rez="Nije odigrano";
        					}
        					var domacin;
        					if(json[i].ekipa_domacin == ekipa){
        						domacin = '<b>'+json[i].ekipa_domacin+'</b>';
        					}
        					else{
        						domacin = '<a href="/virtual_liga_kosarka/detaljiEkipe?ekipa=' + json[i].ekipa_domacin+ '">'+json[i].ekipa_domacin+'</a>';
        					}
        					       					
        					var gost;
        					if(json[i].ekipa_gost == ekipa){
        						gost = '<b>'+ json[i].ekipa_gost+'</b>';
        					}
        					else{
        						gost = '<a href="/virtual_liga_kosarka/detaljiEkipe?ekipa=' +json[i].ekipa_gost+'">'+json[i].ekipa_gost+'</a>';
	        					
        					}
        					//document.getElementById("holder2").innerHTML = json[i].igrac_utakmice;
        					$('#myTableUtakmiceE > tbody:last-child').append('<tr>'+
        								'<td>'+j+'</td>'+
        								'<td>'+
        									domacin+
        								'</td>'+
        								'<td>'+
        									gost+
        								'</td>'+
        								'<td>'+
        									rez+
        								'</td>'+
        								'<td>'+json[i].igrac_utakmice+'</td>'+
        								//'<td><c:out value="${igrac}"/></td>'+
        							'</tr>');
        					j++;
        				}
        				
				}
			});
		}, 4000);
	});
	</script>
<body>
		<div style="float: right;">
		<a
			href="/virtual_liga_kosarka/Ekipa?ekipa=${ekipa}"
			class="btn btn-default" id="proba">Igrači</a>

		<a
			href="/virtual_liga_kosarka/"
			class="btn btn-default">Natrag</a><br> <!--    list-group-item col-sm-2 -->

		</div>
		<div>
			<h2 class="mid" style="font-style: italic">
				Utakmice(<c:out value="${ekipa}"></c:out>)<br><br>
			</h2>
			<table class="table table-bordered" width="60%" id="myTableUtakmiceE">
				<thead>
					<tr>
						<th width="5%">#</th>
						<th width="15%">Domaćin</th>
						<th width="15%">Gost</th>
						<th width="10%">Rezultat</th>
						<th width="15%">Igrač utakmice</th>
					</tr>
				</thead>
				
				<tbody>
					<c:set var="count" value="1" scope="page" />
					<c:forEach items="${Utakmice}" var="utakmica">
						<tr>
							<td><c:out value="${count}" /></td>
							<td>
								<c:choose>
								<c:when test="${utakmica.ekipa_domacin.ime_ekipa == ekipa}"> 
									<b><c:out value="${utakmica.ekipa_domacin.ime_ekipa}" /> </b>
								</c:when>
								<c:otherwise>
									<a href="/virtual_liga_kosarka/detaljiEkipe?ekipa=<c:out value="${utakmica.ekipa_domacin.ime_ekipa}"/>"> <c:out value="${utakmica.ekipa_domacin.ime_ekipa}" /></a>
								</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
								<c:when test="${utakmica.ekipa_gost.ime_ekipa == ekipa}"> 
									<b><c:out value="${utakmica.ekipa_gost.ime_ekipa}" /></b>
								</c:when>
								<c:otherwise>
									<a href="/virtual_liga_kosarka/detaljiEkipe?ekipa=<c:out value="${utakmica.ekipa_gost.ime_ekipa}" />"> <c:out value="${utakmica.ekipa_gost.ime_ekipa}" /></a>
								</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
								<c:when test="${utakmica.rezultat != null}"> 
									<c:out value="${utakmica.rezultat}"></c:out>
								</c:when>
								<c:otherwise>
									<c:out value="Nije odigrano" />
								</c:otherwise>
								</c:choose>
							</td>
							<td><c:out value="${utakmica.igrac_utakmice.ime_igrac} ${utakmica.igrac_utakmice.prezime_igrac}" /></td>
						</tr>
						<c:set var="count" value="${count + 1}" scope="page" />
					</c:forEach>
				</tbody>
			</table>
		<!-- </div>-->
		</div>
		
</body>
<%@include file="fragments/footer.jsp" %>
</html>