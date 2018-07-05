<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/autocomplete.css" />

<sf:form method="post" action="/accounting/updatepayable" modelAttribute="payables">
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Invoice Number</b></td>
			<td><b>Begin Payment</b></td>
			<td><b>Supplier</b></td>
			<td>&nbsp;</td>
			<td><b>Capital Or Retail</b></td>
			<td><b>Payment Schedule</b></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="invoice_num" readonly="true"/></td>
			<td><sf:input class="fancy" path="date_begin" type="date" readonly="true"/></td>
			<td colspan="2"><sf:input class="fancy" path="supplier" size="46" readonly="true"/></td>
			<td><sf:input class="fancy" path="type" readonly="true" size="1" /></td>
			<td><sf:input class="fancy" path="schedule" readonly="true"/></td>
		</tr>
		<tr>
			<td><b>Total Due</b></td>
			<td><b>Down Payment</b></td>
			<td><b>Interest</b></td>
			<td><b>Number of Payments</b></td>
			<td><b>Payment Amount</b></td>
			<td><b>Current Balance</b></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="total_due" type="number" step=".01" readonly="true"/></td>
			<td><sf:input class="fancy" path="down_payment" type="number" step=".01" readonly="true"/>
			<td><sf:input class="fancy" path="interest" type="number" step=".01" readonly="true"/>
			<td><sf:input class="fancy" path="num_payments" type="number" step="1" readonly="true"/>
			<td><sf:input class="fancy" path="each_payment" type="number" step=".01" readonly="true"/>
			<td><sf:input class="fancy" path="total_balance" type="number" step=".01" readonly="true"/></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit"><b>Save</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()"><b>Cancel</b></sf:button>
		</tr>
	</table>
</sf:form>