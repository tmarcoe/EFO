<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<table class="tableview tableshadow tableborder">
	<tr>
		<th>Title</th>
		<th>Begin Period</th>
		<th>End Period</th>
		<th>Department</th>
		<th>Created On</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
	</tr>
		<c:forEach var="item" items="${objectList.pageList}">
			<tr>
				<td>${item.title}</td>
				<td><fmt:formatDate value="${item.begin}"/> </td>
				<td><fmt:formatDate value="${item.end}"/> </td>
				<td>${item.department}</td>
				<td><fmt:formatDate value="${item.creation}"/> </td>
				<td><button type="button" onclick="window.location.href='/accounting/editbudget?reference=${item.reference}'">Edit</button></td>
				<td><button type="button">Budget Items</button>
			</tr>
		</c:forEach>
		<tfoot class="tablefooter">
			<tr>
				<td colspan="7"><button type="button" onclick="window.location.href='/accounting/newbudget'">New Budget</button></td>
			</tr>
		</tfoot>
</table>
