<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form id="vendor" method="post" action="/#tabs-3" modelAttribute="transaction">
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Amount of Transaction:</b><br>
			<sf:input class="fancy" path="amount" type="number" readonly="true" /></td>
			<td><b>Profile Name:</b><br>
				<sf:input class="fancy" path="name" readonly="true" /></td>
		</tr>
		<tr>
			<td><b>Down Payment:</b><br>
			<sf:input class="fancy" path="down" type="number" readonly="true" /></td>
			<td><b>Interest:</b><br>
			<sf:input class="fancy" path="interest" id="interest" type="number" readonly="true" /></td>
		</tr>
		<tr>
			<td><b>Number of Payments:</b><br>
			<sf:input class="fancy" path="num_payments" type="number" readonly="true" /></td>
			<td><b>Each Payment:</b><br>
			<sf:input class="fancy" path="each_payment" type="number" readonly="true" /></td>
		</tr>
		<tr>
			<td><b>Start of Payments:</b><br>
				<sf:input class="fancy" path="start" type="text" readonly="true" /></td>
			<td><b>Schedule:</b><br>
				<sf:input class="fancy" path="schedule" readonly="true" />	</td>
		</tr>
		<tr>
			<td colspan="2"><b>Transaction Description:</b><br>
				<sf:textarea class="fancy-textarea" path="descr" rows="5" cols="70" readonly="true" />
			</td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit" ><b>OK</b></sf:button></td>
		</tr>
		<tr>
			<td><sf:hidden path="reference" /></td>
			<td><sf:hidden path="timestamp" /></td>
		</tr>
	</table>
</sf:form>
<script type="text/javascript">

</script>
