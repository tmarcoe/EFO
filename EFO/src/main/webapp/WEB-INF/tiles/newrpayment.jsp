<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<sf:form method="post" action="/accounting/addrpayment" modelAttribute="payment" >
	<table class="menuTable">
		<tr>
			<td><b>Invoice Number</b></td>
			<td><b>Payment Date</b></td>
		</tr>
		<tr>
			<td><sf:input path="invoice_num" readonly="true" /></td>
			<td><sf:input path="payment_date" type="date" /></td>
		</tr>
		<tr>
			<sf:errors path="invoice_num" class="error" />
			<sf:errors path="payment_date" class="error" />
		</tr>
		<tr>
			<td><b>Date Due</b></td>
			<td><b>Payment Due</b></td>
			<td><b>Payment</b></td>
		</tr>
		<tr>
			<td><sf:input path="date_due" type="date" /></td>
			<td><sf:input path="payment_due" type="number" step=".01"/></td>
			<td><sf:input path="payment" type="number" step=".01" /></td>
		</tr>
		<tr>
			<sf:errors path="date_due" class="error" />
			<sf:errors path="payment_due" class="error" />
			<sf:errors path="payment" class="error" />
		</tr>
		<tr>
			<td colspan="3">Comments:<br>
				<sf:textarea path="comments" rows="4" cols="110"/>
			</td>
		</tr>
		<tr>
			<td colspan="2"><sf:button type="submit" >Save Payment</sf:button></td>
			<td><button type="button" onclick="window.history.back()">Cancel</button></td>
		</tr>
	</table>

</sf:form>


