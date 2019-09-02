<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<link type="text/css" href="/css/tables.css" rel="stylesheet" />


<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form id="editAccount" method="post" action="/accounting/updateaccount" modelAttribute="account">
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Account Number: </b><sf:input path="accountNum"  class="fancy" /></td>
			<td><b>Account Name: </b><sf:input path="accountName" size="20"  class="fancy" /></td>
		</tr>
		<tr>
			<td><sf:errors path="accountNum" class="error"/></td>
			<td><sf:errors path="accountName" class="error"/></td>
		</tr>
		<tr>
			<td><b>Account Balance: </b><sf:input path="accountBalance" type="number" step=".01"  class="fancy" /></td>
			<td><b>Account Type: </b><sf:select path="accountType"  class="fancy" >
				<sf:option value="Asset">Asset Account</sf:option>
				<sf:option value="Liability">Liability Account</sf:option>
				<sf:option value="Equity">Equity Account</sf:option>
				<sf:option value="Revenue">Revenue Account</sf:option>
				<sf:option value="Expense">Expense Account</sf:option>
				<sf:option value="Contra Asset">Contra Asset Account</sf:option>
			</sf:select></td>
		</tr>
		<tr>
			<td><sf:errors path="accountBalance" class="error" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>Description:</b><br>
			<sf:textarea class="fancy-textarea" path="description" rows="4" cols="65"/></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit" ><b>Submit</b></sf:button>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()"><b>Cancel</b></sf:button>
		</tr>
	</table>
</sf:form>