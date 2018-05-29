<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />

<sf:form id="details" method="post" action="/admin/adduser" modelAttribute="user">
	<table class="table">
		<tr>
			<td><b>Email:</b><sf:input path="username"/></td>
			<td><b>Enabled:</b><sf:checkbox path="enabled" checked="true"/></td>
			<td><b>Temporary Passoword:</b><sf:input path="password" /></td>
		</tr>
		<tr>
			<td><sf:errors path="username" class="error" /></td>
			<td></td>
			<td><sf:errors path="password" class="error" /></td>
		</tr>
		<tr>
			<td ><sf:button type="submit">Save</sf:button></td>
			<td ><button type="button"onclick="window.history.back()" >Cancel</button></td>
		</tr>
	</table>
</sf:form>
<script type="text/javascript" >
</script>
