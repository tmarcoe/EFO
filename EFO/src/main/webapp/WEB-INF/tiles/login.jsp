<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />


<form action='/login' method='post' id=loginForm>
	<div class="page-centered">
		<div class="div-centered">

			<c:if test="${param.error != null}">

				<p class="error">Login failed. Check that your username and password are correct.</p>

			</c:if>


			<table class="fancy-table tableshadow">
				<tr>
					<td><div class="fancy" style="text-align: center;">
							<b>Email</b>
						</div></td>
				</tr>
				<tr>
					<td><input id="username" class="fancy" type='text' name='username' value=''></td>
				</tr>
				<tr>
					<td><div class="fancy" style="text-align: center;">
							<b>Password</b>
						</div></td>
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
					<td><button class="fancy-button" type="submit" style="width: 100%;">
							<b>Login</b>
						</button></td>
				</tr>
			</table>

		</div>
	</div>
</form>
<div class="modal" id="enterEmail">

	<div class="modal-content small fancy" id="extraInfo">
		<h3>Email Field is Blank</h3>
		<br>
		<button class="fancy-button" type="button" onclick="clearMsg()">
			<b>OK</b>
		</button>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		<!--document.j_username.focus();-->
	});

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

	function resetPassword() {
		var uName = $("#username").val();
		if (uName.length > 0) {
			window.location.href = "/resetpassword?username=" + uName;
		} else {
			$("#enterEmail").css("display", "block");
		}
	}
	function clearMsg() {
		$("#enterEmail").css("display", "none");
	}
</script>