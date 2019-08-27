<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form id="vendor" method="post" action="/basic/addtransaction" modelAttribute="transaction">
	<table class="fancy-table tableshadow">
		<tr>
			<td colspan="2"><b>Payment To/Received From:</b><br><sf:input class="fancy" path="payment_name" size="55" readonly="true" /></td>
		</tr>
		<tr>
			<td><sf:errors path="payment_name" class="error"/>
		</tr>
		<tr>
			<td><b>Amount of Payment:</b><br>
			<sf:input class="fancy" id="total_due" path="amount" type="number" step=".01" /></td>
			<td><b>Profile Name:</b><br><sf:input class="fancy" path="name" readonly="true" /></td>
		</tr>
		<tr>
			<td><sf:errors path="amount" class="error" /></td>
			<td><sf:errors path="name" class="error" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>Transaction Description:</b><br>
				<sf:textarea class="fancy-textarea" path="descr" rows="5" cols="50"/>
			</td>
		</tr>
		<tr>
			<td><sf:errors path="descr" class="error" /></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit" ><b>Save</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()"><b>Back</b></sf:button>
		</tr>
		<tr>
			<td><sf:hidden path="reference" /></td>
			<td><sf:hidden path="timestamp" /></td>
			<td><sf:hidden path="payment_ref"/></td>
			<td><sf:hidden path="down" value="0" /></td>
			<td><sf:hidden path="interest" value="0" /></td>
			<td><sf:hidden path="num_payments" value="0" /></td>
			<td><sf:hidden path="each_payment" value="0" /></td>
			<td><sf:hidden path="start"/></td>
			<td><sf:hidden path="schedule" value="" /></td>
			<td><input type="hidden" id="total_tax" value="0" /></td>
		</tr>
	</table>
</sf:form>
<script type="text/javascript">
</script>
