<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<sf:form method="post" action="/accounting/updchange" modelAttribute="pettyCash" >
	<table class="table">
		<tr>
			<td><b>Minimum Amount</b></td>
			<td><b>Maximum Amount</b></td>
		</tr>
		<tr>
			<td><sf:input path="minAmount"/></td>
			<td><sf:input path="maxAmount"/></td>
		</tr>
		<tr>
			<td><sf:errors class="error" path="minAmount"/>
			<td><sf:errors class="error" path="maxAmount"/>
		</tr>
		<tr>
			<td><sf:button type="submit">Save</sf:button></td>
		</tr>
	</table>
	<sf:hidden path="lastTransaction"/>
	<sf:hidden path="pc_id"/>
</sf:form>
