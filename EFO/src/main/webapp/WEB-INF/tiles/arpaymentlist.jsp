<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<table class="tableview tableshadow tableborder rjfourth rjfifth">
	<thead>
		<tr>
			<th>Id</th>
			<th>Payment Date</th>
			<th>Date Due</th>
			<th>Payment Due</th>
			<th>Payment Made</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
		</tr>
	</thead>
	<c:forEach var="item" items="${objectList.pageList}">
		<c:set var="reference" value="${item.reference}" />
		<tr>
			<td><fmt:formatNumber value="${item.id}" pattern="00000000" /></td>
			<td><fmt:formatDate value="${item.payment_date}" /></td>
			<td><fmt:formatDate value="${item.date_due}" /></td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.payment_due}" /></td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.payment}" /></td>
			<td><button type="button" onclick="window.location.href = '/accounting/editrpayment?id=${item.id}'">Edit</button></td>
			<c:choose>
				<c:when test="${empty item.payment_date}">
					<td><button type="button" onclick="window.location.href = '/accounting/receivepayment?id=${item.id}'">Receive
						Payment</button></td>
				</c:when>
				<c:otherwise>
					<td><b>Payment Received</b></td>
				</c:otherwise>
			</c:choose>
		</tr>
	</c:forEach>
	<tfoot class="tablefooter">
		<tr>
			<td colspan="7"><button type="button" onclick="window.location.href = '/accounting/ar'">Back</button></td>
		</tr>
	</tfoot>
</table>