<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<table
	class="tableview tableshadow tableborder tableSpacing rjsixth rjseventh">
	<thead>
		<tr>
			<th>Entry Number</th>
			<th>Account Number</th>
			<th>User ID</th>
			<th>Entry Date</th>
			<th>Description</th>
			<th>Debit</th>
			<th>Credit</th>
		</tr>
	</thead>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td><fmt:formatNumber type="number" pattern="00000000"
					value="${item.entryNum}" /></td>
			<td>${item.accountNum}</td>
			<td><fmt:formatNumber type="number" pattern="00000000"
					value="${item.userID}" /></td>
			<td><fmt:formatDate value="${item.entryDate}" /></td>
			<td>${item.description}</td>
			<td><c:if test="${item.debitAmt  != 0}">
					<fmt:formatNumber type="currency" currencySymbol=""
						value="${item.debitAmt}" />
				</c:if></td>
			<td><c:if test="${item.creditAmt != 0}">
					<fmt:formatNumber type="currency" currencySymbol=""
						value="${item.creditAmt}" />
				</c:if></td>
		</tr>
	</c:forEach>
	<tfoot class="tablefooter">
		<tr>
			<td colspan="7"><button type="button"
					onclick="window.location.href='/#tabs-4'">OK</button></td>
		</tr>
	</tfoot>
</table>
