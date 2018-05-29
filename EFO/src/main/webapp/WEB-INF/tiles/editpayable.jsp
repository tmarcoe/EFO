<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<sf:form method="post" action="/accounting/updatepayable" modelAttribute="payables">
	<table class="menuTable">
		<tr>
			<td><b>Invoice Number</b></td>
			<td><b>Begin Payment</b></td>
			<td><b>Supplier</b></td>
		</tr>
		<tr>
			<td><sf:input path="invoice_num" /></td>
			<td><sf:input path="date_begin" type="date"/></td>
			<td><sf:input path="supplier" readonly="true" /></td>
		</tr>
		<tr>
			<td><sf:errors path="invoice_num" class="error" /></td>
			<td><sf:errors path="date_begin" class="error" /></td>
			<td><sf:errors path="supplier" class="error" /></td>
		</tr>
		<tr>
			<td><b>Total Due</b></td>
			<td><b>Pay by</b></td>
			<td><b>Current Balance</b></td>
			<td><b>Loan Category</b></td>
		</tr>
		<tr>
			<td><sf:input path="total_due" type="number" step=".01"/></td>
			<td><sf:input path="date_due" type="date" /></td>
			<td><sf:input path="total_balance" type="number" step=".01"/></td>
			<td><sf:input path="type" readonly="true" size="1" /></td>
		</tr>
		<tr>
			<td><sf:errors path="total_due" class="error" /></td>
			<td><sf:errors path="date_due" class="error" /></td>
			<td><sf:errors path="total_balance" class="error" /></td>
		</tr>
		<tr>
			<td><sf:button type="submit">Save</sf:button>
			<td><sf:button type="button" onclick="window.history.back()">Cancel</sf:button>
		</tr>
	</table>
</sf:form>