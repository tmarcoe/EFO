<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />


<script type="text/javascript">
	$(document).ready(function() {
		document.f.j_username.focus();
	});
</script>
<div class="page-centered">
	<div class="div-centered">


		<c:if test="${param.error != null}">

			<p class="error">Login failed. Check that your username and password are correct.</p>

		</c:if>

		<form action='/login' method='post' id=loginForm>
			<table class="fancy-table tableshadow">
				<tr>
					<td><div class="fancy" style="text-align:center;"><b>Email</b></div></td>
				</tr>
				<tr>
					<td><input class="fancy" type='text' name='username' value=''></td>
				</tr>
				<tr>
					<td><div class="fancy" style="text-align:center;"><b>Password</b></div></td>
				</tr>
				<tr>
					<td><input class="fancy" type='password' name='password' /></td>
				</tr>
				<!-- 
		<tr>
			<td>Remember me:</td>
			<td><input type='checkbox' name='_spring_security_remember_me' 
				id='remember_me' onchange='alertUser()' /></td>
		</tr>
		 -->
				<tr>
					<td><button class="fancy-button" type="submit" style="width: 100%;"><b>Login</b></button></td>
				</tr>
			</table>
		</form>
	</div>
</div>
<div class="modal" id="popup">

	<div class="modal-content small-medium" id="extraInfo">
		<h3>Enter your Email</h3>
		<form action="/public/passwordrecovery">
			<table>
			</table>
		</form>
	</div>
</div>
<script type="text/javascript">
	function alertUser() {
		if (document.getElementById("remember_me").checked == true) {
			alert("It is not recommended to use this feature on public computers!");
		}
	}

	function followLink(link) {
		window.location.href = "${pageContext.request.contextPath}" + link;
	}
	function pwRecovery() {
		var mode = document.getElementById("popup");
		mode.style.display = "block";
	}
</script>