<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<link type="text/css" href="/css/tables.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />

<sf:form method="post" action="/accounting/addaccount" modelAttribute="account">
	<table class="fancy-table tableshadow">
		<tr>
			<th>Account Number</th>
			<th>Account Name</th>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="accountNum"/></td>
			<td><sf:input class="fancy" path="accountName" size="35" /></td>
		</tr>
		<tr>
			<td><sf:errors path="accountNum" class="error"/></td>
			<td><sf:errors path="accountName" class="error"/></td>
		</tr>
		<tr>
			<th>Balance</th>
			<th>Account Type</th>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="accountBalance" type="number" step=".01" /></td>
			<td><sf:select class="fancy" path="accountType">
				<sf:option value="Asset">Asset Account</sf:option>
				<sf:option value="Liability">Liability Account</sf:option>
				<sf:option value="Equity">Equity Account</sf:option>
				<sf:option value="Revenue">Revenue Account</sf:option>
				<sf:option value="Expense">Expense Account</sf:option>
				<sf:option value="Contra Asset">Contra Asset Account</sf:option>
			</sf:select></td>
		</tr>
		<tr>
			<td><sf:errors path="accountBalance" class="error"></sf:errors></td>
		</tr>
		<tr>
			<td colspan="2"><b>Description:</b><br>
			<sf:textarea class="fancy-textarea" path="description" rows="4" cols="65"/></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit"><b>Save Account</b></sf:button></td>
			<td><button class="fancy-button" type="button" onclick="window.history.back()"><b>Cancel</b></button></td>
		</tr>
	</table>
</sf:form>