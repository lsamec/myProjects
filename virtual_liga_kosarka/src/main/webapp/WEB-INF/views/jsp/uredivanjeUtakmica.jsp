<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="fragments/header.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Uređivanje Utakmica</title>
</head>
<body>
<div>
<div style="float: right;">
		<a
			href="/virtual_liga_kosarka/osobnaSlOsoba"
			class="btn btn-default">Natrag</a>
		</div>
			<h2 class="mid" style="font-style: italic">
				Utakmice<br>
			</h2>
			<table class="table table-bordered" width="60%">
				<thead>
					<tr>
						<th width="5%">#</th>
						<th width="15%">Domaćin</th>
						<th width="15%">Gost</th>
						<th width="15%">Odaberi</th>
					</tr>
				</thead>
				
				<tbody>
					<c:set var="count" value="1" scope="page" />
					<c:forEach items="${Utakmica}" var="utakmica">
						<tr>
							<td><c:out value="${count}" /></td>
							<td>
								 <c:out value="${utakmica.ekipa_domacin.ime_ekipa}" />
							</td>
							<td>
								<c:out value="${utakmica.ekipa_gost.ime_ekipa}" />
							</td>
							<td>
								<a href="/virtual_liga_kosarka/osobnaSlOsoba/dogadaji?utakmica=<c:out value="${utakmica.utakmica_id}"/>"><c:out value="Odaberi utakmicu (id=${utakmica.utakmica_id})" /><c:if test="${utakmica.rezultat != null}">
							<span class="badge" style="float: right">odigr.</span>
						</c:if></a>
							</td>
						</tr>
						<c:set var="count" value="${count + 1}" scope="page" />
					</c:forEach>
				</tbody>
			</table>
		<!-- </div>-->
		</div>
		
</body>
</html>