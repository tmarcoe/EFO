<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form method="post" action="/accounting/updatereceivable" modelAttribute="receivables">
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Invoice Number</b></td>
			<td><b>Invoice Date</b></td>
			<td><b>Customer</b></td>
			<td><b>Total Due</b></td>
			<td><b>Down Payment</b></td>
			<td><b>Interest</b></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="invoice_num" readonly="true" /></td>
			<td><sf:input class="fancy" path="invoice_date" type="date"/></td>
			<td><sf:input class="fancy" path="customer" readonly="true" /></td>
			<td><sf:input class="fancy" path="total_due" type="number" step=".01"/></td>
			<td><sf:input class="fancy" path="down_payment" type="number" step=".01"/></td>
			<td><sf:input class="fancy" path="interest" type="number" step=".01"/></td>			
		</tr>
		<tr>
			<td><sf:errors path="invoice_num" class="error" /></td>
			<td><sf:errors path="invoice_date" class="error" /></td>
			<td><sf:errors path="customer" class="error" /></td>
			<td><sf:errors path="total_due" class="error" /></td>
			<td><sf:errors path="down_payment" class="error" /></td>
			<td><sf:errors path="interest" class="error" /></td>
		</tr>
		<tr>
			<td><b>Each Payment</b></td>
			<td><b>Number of payments</b></td>
			<td><b>Current Balance</b></td>
			<td><b>Payment Schedule</b></td>
			<td><b>Status</b></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="each_payment" type="number" step=".01" readonly="true" /></td>
			<td><sf:input class="fancy" path="num_payments" type="number" step="1" /></td>
			<td><sf:input class="fancy" path="total_balance" type="number" step=".01" readonly="true" /></td>
			<td><sf:select class="fancy" path="schedule">
				<sf:option value="Annually">Annually</sf:option>
				<sf:option value="Bi-Annually">Bi-Annually</sf:option>
				<sf:option value="Quarterly">Quarterly</sf:option>
				<sf:option value="Monthly">Monthly</sf:option>
				<sf:option value="Bi-Monthly">Bi-Monthly</sf:option>
				<sf:option value="Weekly">Weekly</sf:option>
				<sf:option value="Daily">Daily</sf:option>
			</sf:select></td>
			<td><sf:select class="fancy" path="status" >
				<sf:option value="O">Open</sf:option>
				<sf:option value="C">Closed</sf:option>
				<sf:option value="D">Dispute</sf:option>
			</sf:select></td>
		</tr>
		<tr>
			<td><sf:errors path="each_payment" class="error" /></td>
			<td><sf:errors path="num_payments" class="error" /></td>
			<td><sf:errors path="total_balance" class="error" /></td>
			<td><sf:errors path="schedule" class="error" /></td>
			<td><sf:errors path="status" class="error" /></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit"><b>Save</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.location.href='/accounting/ar'"><b>Cancel</b></sf:button></td>
		</tr>
	</table>
	<sf:hidden path="username"/>
</sf:form>