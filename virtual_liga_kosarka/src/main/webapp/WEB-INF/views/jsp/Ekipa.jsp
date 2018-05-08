<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ekipa</title>
</head>
<%@include file="fragments/header.jsp" %>
<body>
	<!-- <div class="col-md-4">-->
		<div class="container mid col-md-4">
			<h2 class="mid" style="font-style: italic">
				Ekipa <c:out value="${ekipa}"></c:out><br><br>
			</h2>
			<table class="table table-bordered" width="60%">
				<thead>
					<tr>
						<th width="5%">#</th>
						<th width="15%">Igrač</th>
						<th width="10%">Broj dresa</th>
						<th width="10%">Ukupno bodova</th>
					</tr>
				</thead>
				
				<tbody>
					<c:set var="count" value="1" scope="page" />
					<c:forEach items="${igraci}" var="igrac">
						<tr>
							<td><c:out value="${count}" /></td>
							<td>
									<c:out value="${igrac.ime_igrac} ${igrac.prezime_igrac}" />
							</td>
							<td>
									<c:out value="${igrac.broj_dresa}" />
							</td>
							<td>
									<c:out value="${igrac.uk_bodovi}" />
							</td>
						</tr>
						<c:set var="count" value="${count + 1}" scope="page" />
					</c:forEach>
				</tbody>
			
			</table>
		<!-- </div>-->
		</div>
		<div style="float: right;">
		<a
			href="/virtual_liga_kosarka/"
			class="btn btn-default">Natrag</a> <!--    list-group-item col-sm-2 -->
		</div>
</body>
<%@include file="fragments/footer.jsp" %>
</html>