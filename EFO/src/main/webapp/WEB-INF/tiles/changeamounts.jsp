<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<sf:form method="post" action="/accounting/updchange" modelAttribute="pettyCash" >
	<table class="fancy-table tableshadow">
		<tr>
			<td>Minimum Amount: <sf:input class="fancy" path="minAmount"/></td>
		</tr>
		<tr>
			<td>Maximum Amount: <sf:input class="fancy" path="maxAmount"/></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit" >Save</sf:button>
		</tr>
	</table>
	<sf:hidden path="lastTransaction"/>
	<sf:hidden path="pc_id"/>
</sf:form>
