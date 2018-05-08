<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<%@include file="fragments/header.jsp"%>

<div id="login-box">

	<div class="container mid">

		<div class="container">
			<div class="col-md-4">
				<h2 class="mid" style="font-style: italic">
					Prijava<br> <br>
				</h2>
			</div>
		</div>

		<div class="container">
			<div class="col-md-4">
				<h5 class="mid" >

					<c:if test="${not empty error}">
						<div class="error" style="font-style: italic;color: red">${error}</div>
					</c:if>
					<c:if test="${not empty msg}">
						<div class="msg" style="font-style: italic;color: blue">${msg}</div>
					</c:if>
				</h5>
			</div>
		</div>

		<form name='loginForm' class="form-signin form-horizontal"
			action="<c:url value='/j_spring_security_check' />" method='POST'>

			<div class="form-group">
				<label class="col-sm-2 control-label">Korisničko ime</label>
				<div class="col-sm-10">
					<input type='text' class="form-control inputForBox" name='username' />
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label">Lozinka</label>
				<div class="col-sm-10">
					<input type='password' class="form-control inputForBox"
						name='password' />
				</div>
			</div>
			<br>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" id="prijavi"
						class="btn-lg btn-primary pull-mid">Prijavi se</button>
				</div>
			</div>

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />

		</form>
	</div>
</div>
<%@include file="fragments/footer.jsp"%>

</html>