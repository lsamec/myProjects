<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Poredak</title>
</head>
<%@include file="fragments/header.jsp" %>
<script type="text/javascript">
	
	$(document).ready(function(){
		setInterval(function(){
			$.ajax({
				url : "/virtual_liga_kosarka/provjeriNaslovnicu.html",
				dataType: 'text',
				success : function(data){
					    var json = $.parseJSON(data);
					    $('#myTable > tbody').html("");
					    
        				for (var i=0;i<json.length;++i)
        				{
        					var link = "";
        					$('#myTable > tbody:last-child').append('<tr><td>'+
        						(i+1) + '</td>'+
        						'<td> <a href ="/virtual_liga_kosarka/detaljiEkipe?ekipa='+json[i].ime_ekipa+'">'+
        						' ' +json[i].ime_ekipa+' </a> </td><td>'+
        						json[i].pobjede +'</td><td>'+ json[i].porazi+'</td></tr>');
        					
        					
        					//   <td> <a href ="/virtual_liga_kosarka/detaljiEkipe?ekipa=<c:out value="${ekipaL.ime_ekipa}" />">
							//   <c:out value="${ekipaL.ime_ekipa}" />
							//	                                         </a>
							//	                                               </td>
        				    //$('#manage_user').append('<div class="ime_ekipa">'+json[i].ime_ekipa+'</>');
        				    //$("#manage_bodovi").append('<div class="bodovi">'+json[i].bodovi+'</>');
        				}
				}
			});
		}, 4000);
	});
	</script>



<body>
	<div style="float: right;">
		<a
			href="/virtual_liga_kosarka/sveUtakmice"
			class="btn btn-default">Sve Utakmice</a> <!--    list-group-item col-sm-2 -->
	</div>
	
	<div class="container mid">
		<div class="col-md-4">
			<h2 class="mid" style="font-style: italic">
				Poredak<br><br>
			</h2>
			<table class="table table-bordered" id=myTable>
				<thead>
					<tr>
						<th width="100">#</th>
						<th width="100">Ekipa</th>
						<th width="100">Pobjede</th>
						<th width="100">Porazi</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="count" value="1" scope="page" />
					<c:forEach items="${ekipaList}" var="ekipaL">
						<tr>
							<td><c:out value="${count}" /></td>
							<td> <a href ="/virtual_liga_kosarka/detaljiEkipe?ekipa=<c:out value="${ekipaL.ime_ekipa}" />">
											<c:out value="${ekipaL.ime_ekipa}" />
										</a>
								</td>
							<td><c:out value="${ekipaL.pobjedene}" /></td>
							<td><c:out value="${ekipaL.izgubljene}" /></td>
						</tr>
						<c:set var="count" value="${count + 1}" scope="page" />
					</c:forEach>
				</tbody>
			</table>
		</div>
		<p><br>
		<p>
	</div>
	
</body>

<%@include file="fragments/footer.jsp" %>

</html>