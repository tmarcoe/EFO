<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link type="text/css" href="/css/tables.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />

<table class="tableview tableshadow tableborder cjfirst">
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td colspan="2">${item.role}</td>
		</tr>
	</c:forEach>
	<tfoot class="tablefooter">
		<tr>
			<td><button type="button" onclick="enterRole()">New
					Role</button></td>
			<td><button type="button"
					onclick="window.location.href='/#tabs-2'">Back</button></td>
		</tr>
	</tfoot>
</table>
<div id="enterRole" class="modal">
	<div class="modal-content small-modal fancy">
		<h2>Enter the New Role</h2>
		<table style="margin-left: auto; margin-right: auto;">
			<tr>
				<td><input class="fancy" id="newrole" /></td>
			</tr>
			<tr>
				<td><button class="fancy-button" type="button" onclick="addRole()">Save</button></td>
				<td><button class="fancy-button" type="button"
						onclick="window.location.href='/#tabs-2'">Cancel</button></td>
			</tr>
		</table>
	</div>
</div>
<script type="text/javascript">
	function enterRole() {
		var modal = document.getElementById('enterRole');
		modal.style.display = "block";
	}
	function addRole() {
		var r = document.getElementById('newrole');
		window.location.href = '/admin/addrole?role=' + r.value;
	}
</script>