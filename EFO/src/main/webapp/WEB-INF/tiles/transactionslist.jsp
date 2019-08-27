<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<table class="tableview tableshadow tableborder tableSpacing rjsixth rjseventh">
	<thead>
		<tr>
			<th>Reference Number</th>
			<th>Name</th>
			<th>Amount</th>
			<th>Created On</th>
			<th>&nbsp;</th>
		</tr>
	</thead>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td><fmt:formatNumber type="number" pattern="00000000" value="${item.reference}" /></td>
			<td>${item.name}</td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.amount}" /></td>
			<td><fmt:formatDate value="${item.timestamp}" /></td>
			<td><button type="button" onclick="window.location.href='/basic/viewtransaction?reference=${item.reference}'">View Details</button></td>
		</tr>
	</c:forEach>
	<tfoot class="tablefooter">
		<tr>
			<td colspan="5"><button type="button" onclick="window.location.href='/#tabs-3'">Back</button></td>
		</tr>
	</tfoot>
</table>
