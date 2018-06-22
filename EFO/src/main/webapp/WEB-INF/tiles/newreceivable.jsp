<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form method="post" action="/accounting/addreceivable" modelAttribute="receivables">
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Invoice Date</b></td>
			<td><b>Customer</b></td>
			<td><b>Total Due</b></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="invoice_date" type="date" /></td>
			<td><sf:input class="fancy" path="customer" readonly="true" /></td>
			<td><sf:input class="fancy" path="total_due" type="number" step=".01"/>
		</tr>
		<tr>
			<td><sf:errors path="invoice_date" class="error" /></td>
			<td>&nbsp;</td>
			<td><sf:errors path="total_due" class="error" /></td>
		</tr>
		<tr>
			<td><b>Pay by</b></td>
			<td><b>Number of payments</b></td>
			<td><b>Current Balance</b></td>
			<td><b>Status</b></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="date_due" type="date" /></td>
			<td><sf:input class="fancy" path="total_payments" type="number" step="1" /></td>
			<td><sf:input class="fancy" path="total_balance" type="number" step=".01"/></td>
			<td><sf:select class="fancy" path="status" >
				<sf:option value="O">Open</sf:option>
				<sf:option value="C">Closed</sf:option>
				<sf:option value="D">Dispute</sf:option>
			</sf:select></td>
		</tr>
		<tr>
			<sf:errors path="date_due" class="error" />
			<sf:errors path="total_payments" class="error" />
			<sf:errors path="total_balance" class="error" />
			<sf:errors path="status" class="error" />
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit"><b>Save</b></sf:button>
			<td><sf:button class="fancy-button" type="button" onclick="window.location.href='/accounting/ar'"><b>Cancel</b></sf:button></td>
		</tr>
	</table>
	<sf:hidden path="username"/>
</sf:form>