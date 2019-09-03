<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<table class="tableview tableshadow tableborder">
	<tr>
		<th>Name</th>
		<th>Begining Period</th>
		<th>Submitted On</th>
		<th>&nbsp;</th>
	</tr>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td>${item.name}</td>
			<td><fmt:formatDate value="${item.begin_period}" /></td>
			<td><fmt:formatDate value="${item.submitted}" /></td>
			<td><button type="button"
					onclick="window.location.href='/timesheet/viewtimesheet?reference=${item.reference}'">View Time Sheet</button>
		</tr>
	</c:forEach>
	<tfoot class="tablefooter">
		<tr>
			<td colspan="6"><button type="button" onclick="window.location.href='/#tabs-9'">Back</button></td>
		</tr>
	</tfoot>
</table>