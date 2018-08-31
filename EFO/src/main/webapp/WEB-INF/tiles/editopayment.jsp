<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form method="post" action="/accounting/updopayment" modelAttribute="payment">
	<div class="centerTitle">${expenses.vendor}: ${expenses.reason}</div>
	<table class="fancy-table tableshadow"> 
		<tr>
			<td><b>Invoice #: </b><br>
				<sf:input class="fancy" path="invoice_num"/></td>
			<td><b>Due Date: </b><br>
				<sf:input type="date" class="fancy" path="date_due" readonly="true" /></td>
			<td><b>Date Paid: </b><br>
				<sf:input type="date" class="fancy" path="date_paid"/></td>
		</tr>
		<tr>
			<td><b>Amount Due: </b><br>
				<sf:input type="number" step=".01" class="fancy" path="amount_due" /></td>
			<td><b>Amount Paid: </b><br>
				<sf:input type="number" step=".01" class="fancy" path="amount_paid"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><sf:button type="submit" class="fancy-button" ><b>Make Payment</b></sf:button>
		</tr>
	</table>
	<sf:hidden path="id"/>
	<sf:hidden path="reference"/>
</sf:form>
