<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />

<sf:form method="post" action="/accounting/addpayment" modelAttribute="billed">
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Payment Date: </b><br>
			<sf:input class="fancy" type="date" path="payment_date"/></td>
			<td><b>Payment Amount: </b><br>
			<sf:input class="fancy" type="number" step=".01" path="payment"/></td>
			<td><b>Penalties: </b><br>
			<sf:input class="fancy" type="number" step=".01" path="penalties"/></td>
		</tr>
		<tr>
			<td colspan="3"><b>Comments: </b><br>
			<sf:textarea class="fancy-textarea" path="comments" cols="71" rows="4"/></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit" ><b>Make Payment</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()"><b>Back</b></sf:button></td>
		</tr>
	</table>
	<sf:hidden path="id"/>
	<sf:hidden path="reference"/>
	<sf:hidden path="date_due"/>
	<sf:hidden path="payment_due"/>
	<sf:hidden path="event"/>
</sf:form>
