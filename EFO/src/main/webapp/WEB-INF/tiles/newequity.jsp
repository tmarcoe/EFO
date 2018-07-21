<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form method="post" action="/accounting/addequity" modelAttribute="equity">
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Amount Of Equity: </b><br>
				<sf:input class="fancy" type="number" step=".01" path="amount"/>
			</td>
			<td><b>Date: </b><br>
				<sf:input class="fancy" type="date" path="time_stamp"/>
			</td>
			</tr>
			<tr>
			<td colspan="2"><b>Comments: </b><br>
				<sf:textarea class="fancy-textarea" path="comments" rows="4" cols="50"/>
			</td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit" ><b>Save</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()" ><b>Cancel</b></sf:button></td>
		</tr>
	</table>
	<sf:hidden path="id"/>
</sf:form>
