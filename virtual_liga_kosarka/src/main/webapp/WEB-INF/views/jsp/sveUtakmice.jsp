<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sve utakmice</title>
</head>
<%@include file="fragments/header.jsp" %>
<p hidden id="holder">${igraci}</p>


<script type="text/javascript" charset="utf-8">
	
	$(document).ready(function(){
		setInterval(function(){
			$.ajax({
				url : "/virtual_liga_kosarka/provjeriSveUtakmice.html",
				dataType: 'text',
				success : function(data){
					    var json = $.parseJSON(data);
					    //alert(data);
					    $('#myTableUtakmice > tbody').html("");
					    
        				for (var i=0;i<json.length;++i)
        				{
        					var rez = json[i].rezultat;
        					if(rez == ""){
        						rez="Nije odigrano";
        					}
        					document.getElementById("holder").innerHTML
        					$('#myTableUtakmice > tbody:last-child').append('<tr>'+
        								'<td>'+(i+1)+'</td>'+
        								'<td>'+
        									'<a href="/virtual_liga_kosarka/detaljiEkipe?ekipa=' + json[i].ekipa_domacin+ '">'+json[i].ekipa_domacin+'</a>'+
        								'</td>'+
        								'<td>'+
        									'<a href="/virtual_liga_kosarka/detaljiEkipe?ekipa='+json[i].ekipa_gost+'">'+json[i].ekipa_gost+'</a>'+
        								'</td>'+
        								'<td>'+
        									rez+
        								'</td>'+
        								'<td>'+json[i].igrac_utakmice+'</td>'+
        								//'<c:out value="${utakmica.igrac_utakmice.ime_igrac} ${utakmica.igrac_utakmice.prezime_igrac}" /></td>'+
        							'</tr>');				
        				}
				}
			});
		}, 4000);
	});
	</script>


<body>

	<!-- <div class="col-md-4">-->
			<div style="float: right;">
		<a
			href="/virtual_liga_kosarka/"
			class="btn btn-default">Natrag</a> <!--    list-group-item col-sm-2 -->
		</div>
		<div>
			<h2 class="mid" style="font-style: italic">
				Utakmice<br><br>
			</h2>
			<table class="table table-bordered" width="60%" id="myTableUtakmice">
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
								<a href="/virtual_liga_kosarka/detaljiEkipe?ekipa=<c:out value="${utakmica.ekipa_domacin.ime_ekipa}"/>"> <c:out value="${utakmica.ekipa_domacin.ime_ekipa}" /></a>
							</td>
							<td>
								<a href="/virtual_liga_kosarka/detaljiEkipe?ekipa=<c:out value="${utakmica.ekipa_gost.ime_ekipa}" />"> <c:out value="${utakmica.ekipa_gost.ime_ekipa}" /></a>
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