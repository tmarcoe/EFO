<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<sf:form method="post" action="/accounting/updatepettycash" modelAttribute="pettyCashVoucher">
	<table class="table">
		<tr>
			<td><b>Date</b></td>
			<td><b>Recipient</b></td>
			<td><b>Type Of Disbursement</b></td>
			<td><b>Amount</b></td>
		</tr>
		<tr>
			<td><sf:input type="date" path="timeStamp"/></td>
			<td><sf:input path="recipient"/></td>
			<td><sf:select path="fromAccount">
				<sf:option value="Petty Cash (Office Supplies)">Office Supplies</sf:option>
				<sf:option value="Petty Cash (Delivery Dxpense)">Delivery Expense</sf:option>
				<sf:option value="Petty Cash (Postage Expense)">Postage Expense</sf:option>
				<sf:option value="Petty Cash (General Office Expense)">General Office Expense</sf:option>
			</sf:select>
			<td><sf:input type="number" step=".01" path="amount" /></td>
		</tr>
		<tr>
			<td><sf:errors class="error" path="timeStamp" /></td>
			<td><sf:errors class="error" path="recipient" /></td>
			<td>&nbsp;</td>
			<td><sf:errors class="error" path="amount" /></td>
		</tr>
		<tr>
			<td colspan="3"><b>Notes:</b><br><sf:textarea path="reason" rows="10" cols="50"/></td>
		</tr>
		<tr>
			<td><sf:button type="submit" >Save</sf:button></td>
			<td><sf:button type="button" onclick="window.history.back()">Back</sf:button>
		</tr>
	</table>
	<sf:hidden path="id"/>
</sf:form>
