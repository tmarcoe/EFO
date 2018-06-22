<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form method="post" action="/accounting/updatepayable" modelAttribute="payables">
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Invoice Number</b></td>
			<td><b>Begin Payment</b></td>
			<td><b>Supplier</b></td>
			<td><b>Capital Or Retail</b></td>
			<td><b>Payment Schedule</b></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="invoice_num" /></td>
			<td><sf:input class="fancy" path="date_begin" type="date"/></td>
			<td><sf:input class="fancy" path="supplier" readonly="true" /></td>
			<td><sf:input class="fancy" path="type" readonly="true" size="1" /></td>
			<td><sf:select class="fancy" path="schedule">
				<sf:option value="Annually">Annually</sf:option>
				<sf:option value="Bi-Annually">Bi-Annually</sf:option>
				<sf:option value="Quarterly">Quarterly</sf:option>
				<sf:option value="Monthly">Monthly</sf:option>
				<sf:option value="Bi-Monthly">Bi-Monthly</sf:option>
				<sf:option value="Weekly">Weekly</sf:option>
				<sf:option value="Daily">Daily</sf:option>
			</sf:select></td>
		</tr>
		<tr>
			<td><sf:errors path="invoice_num" class="error" /></td>
			<td><sf:errors path="date_begin" class="error" /></td>
			<td><sf:errors path="supplier" class="error" /></td>
			<td>&nbsp;</td>
			<td><sf:errors path="schedule" class="error" /></td>
		</tr>
		<tr>
			<td><b>Total Due</b></td>
			<td><b>Down Payment</b></td>
			<td><b>Interest</b></td>
			<td><b>Payment Amount</b></td>
			<td><b>Number of Payments</b></td>
			<td><b>Current Balance</b></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="total_due" type="number" step=".01"/></td>
			<td><sf:input class="fancy" path="down_payment" type="number" step=".01"/>
			<td><sf:input class="fancy" path="intrest" type="number" step=".01"/>
			<td><sf:input class="fancy" path="each_payment" type="number" step=".01"/>
			<td><sf:input class="fancy" path="num_payments" type="number" step="1"/>
			<td><sf:input class="fancy" path="total_balance" type="number" step=".01"/></td>
		</tr>
		<tr>
			<td><sf:errors path="total_due" class="error" /></td>
			<td><sf:errors path="down_payment" class="error" /></td>
			<td><sf:errors path="intrest" class="error" /></td>
			<td><sf:errors path="each_payment" class="error" /></td>
			<td><sf:errors path="num_payments" class="error" /></td>
			<td><sf:errors path="total_balance" class="error" /></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit"><b>Save</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()"><b>Cancel</b></sf:button>
		</tr>
	</table>
</sf:form>