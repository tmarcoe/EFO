<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/autocomplete.css" />

<sf:form method="post" action="/timesheet/addtsaccount" modelAttribute="timeReportingAccounts">
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Account Number:</b><br><sf:input path="account_number" class="fancy" /></td>
			<td><b>Beginning Period:</b><br><sf:input id="begin" path="begins" class="fancy" /></td>
			<td><b>Ending Period:</b><br><sf:input id="end" path="expires" class="fancy" /></td>
		</tr>
		<tr>
			<td><sf:errors path="account_number" class="error"/></td>
			<td><sf:errors path="begins" class="error"/></td>
			<td><sf:errors path="expires" class="error"/></td>
		</tr>
		<tr>
			<td><b>Brief Description:</b><br><sf:input path="description" class="fancy" /></td>
			<td><b>Chart Of Accounts #: </b><br><sf:input path="cofa_account" class="fancy" /></td>
			<td><b>Department:<br>(blank for all departments)</b><br><sf:input path="department" class="fancy" /></td>
		</tr>
		<tr>
			<td><sf:errors path="description" class="error"/></td>
			<td><sf:errors path="cofa_account" class="error"/></td>
			<td><sf:errors path="department" class="error"/></td>
		</tr>
		<tr>
			<td><b>Maximum Billabe Amount:</b><br><sf:input path="max_amount" type="number" step=".01" class="fancy" /></td>
			<td><b>Maximum Billable Hours:</b><br><sf:input path="max_hours" type="number" step=".25" class="fancy" /></td>
			<td><b>Account Active: </b><sf:checkbox path="active"/></td>
		</tr>
		<tr>
			<td><sf:errors path="max_amount" class="error"/></td>
			<td><sf:errors path="max_hours" class="error"/></td>
		</tr>
		<tr>
			<td><sf:button type="submit" class="fancy-button" ><b>Save</b></sf:button></td>
			<td><sf:button type="button" class="fancy-button" onclick="window.history.back()"><b>Cancel</b></sf:button></td>
		</tr>
	</table>
</sf:form>
<script type="text/javascript"> 
$(function() {
	var dates = $("#begin").datepicker({
		dateFormat : "yy-mm-dd",
		changeMonth : true,
		changeYear : true,
		clickInput : true
	});
	$("#end").datepicker({
		dateFormat : "yy-mm-dd",
		changeMonth : true,
		changeYear : true,
		clickInput : true
	});
});
</script>
