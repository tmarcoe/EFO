<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<sf:form method="post" action="/accounting/updatereceivable" modelAttribute="receivables">
	<table class="menuTable">
		<tr>
			<td><b>Invoice Number</b></td>
			<td><b>Invoice Date</b></td>
			<td><b>Customer</b></td>
			<td><b>Total Due</b></td>
		</tr>
		<tr>
			<td><sf:input path="invoice_num" readonly="true" /></td>
			<td><sf:input path="invoice_date" type="date"/></td>
			<td><sf:input path="customer" readonly="true" /></td>
			<td><sf:input path="total_due" type="number" />
		</tr>
		<tr>
			<td><sf:errors path="invoice_num" class="error" /></td>
			<td><sf:errors path="invoice_date" class="error" /></td>
			<td><sf:errors path="customer" class="error" /></td>
			<td><sf:errors path="total_due" class="error" /></td>
		</tr>
		<tr>
			<td><b>Pay by</b></td>
			<td><b>Number of payments</b></td>
			<td><b>Current Balance</b></td>
			<td><b>Status</b></td>
		</tr>
		<tr>
			<td><sf:input path="date_due" type="date" /></td>
			<td><sf:input path="total_payments" type="number" step="1" /></td>
			<td><sf:input path="total_balance" type="number" step=".01" readonly="true" /></td>
			<td><sf:select path="status" >
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
			<td><sf:button type="submit">Save</sf:button>
			<td><sf:button type="button" onclick="window.location.href='/accounting/ar'">Cancel</sf:button>
		</tr>
	</table>
	<sf:hidden path="username"/>
</sf:form>