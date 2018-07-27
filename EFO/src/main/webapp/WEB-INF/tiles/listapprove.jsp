<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<table class="tableview tableshadow tableborder">
	<tr>
		<th>Department</th>
		<th>Created On</th>
		<th>Submitted On</th>
		<th>&nbsp;</th>
	</tr>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td>${item.department}</td>
			<td><fmt:formatDate value="${item.creation_date}" /></td>
			<td><fmt:formatDate value="${item.submission_date}" /></td>
			<td><button type="button"
					onclick="window.location.href='/accounting/approvebudget?department=${item.department}'">Approve</button>
		</tr>
	</c:forEach>
	<tfoot class="tablefooter">
		<tr>
			<td colspan="4"><button type="button" onclick="window.location.href='/#tabs-5'">Back</button></td>
		</tr>
	</tfoot>
</table>