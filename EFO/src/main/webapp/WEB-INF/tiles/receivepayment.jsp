<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form method="post" action="/accounting/updatereceive" modelAttribute="payment" >
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Reference: </b><br><sf:input class="fancy" path="reference" readonly="true" /></td>
			<td><b>Date Paid:</b><br><sf:input class="fancy" type="date" path="payment_date" /></td>
			<td><b>Payment Made:</b><br><sf:input class="fancy" type="number" step=".01" path="payment"/>
			<td><b>Penalties</b><br><sf:input class="fancy" type="number" step=".01" path="penalties"/>
		</tr>
		<tr>
			<sf:errors path="reference" class="error" />
			<sf:errors path="payment_date" class="error" />
			<sf:errors path="payment" class="error" />
			<sf:errors path="penalties" class="error" />
		</tr>
		<tr>
			<td colspan="3"><b>Comments:</b><br>
				<sf:textarea class="fancy-textarea" path="comments" rows="4" cols="75"/>
			</td>
		</tr>
		<tr>
			<td colspan="2"><sf:button class="fancy-button" type="submit" ><b>Save Payment</b></sf:button></td>
			<td><button type="button" class="fancy-button" onclick="window.history.back()"><b>Cancel</b></button></td>
		</tr>
	</table>
	<sf:hidden path="id" />
	<sf:hidden path="date_due"/>
	<sf:hidden path="payment_due"/>
</sf:form>


