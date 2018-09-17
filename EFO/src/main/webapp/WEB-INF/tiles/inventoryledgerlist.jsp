<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<table class="tableview tableshadow tableborder tableSpacing rjthird rjfourth rjfifth rjsixth">
	<thead>
		<tr>
			<th>Entry Number</th>
			<th>Timestamp</th>
			<th>Quantity</th>
			<th>Debit</th>
			<th>Credit</th>
			<th>Balance</th>
			<th>Description</th>
		</tr>
	</thead>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td><fmt:formatNumber type="number" pattern="00000000" value="${item.id}" /></td>
			<td><fmt:formatDate value="${item.timestamp}" /></td>
			<td>${item.qty}</td>
			<td><c:if test="${item.debit  != 0}">
					<fmt:formatNumber type="currency" currencySymbol="" value="${item.debit}" />
				</c:if></td>
			<td><c:if test="${item.credit != 0}">
					<fmt:formatNumber type="currency" currencySymbol="" value="${item.credit}" />
				</c:if></td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.balance}" /></td>
			<td>${item.description}</td>
		</tr>
	</c:forEach>
	<tfoot class="tablefooter">
		<tr>
			<td colspan="7"><button type="button" onclick="window.location.href='/#tabs-4'">OK</button></td>
		</tr>
	</tfoot>
</table>
