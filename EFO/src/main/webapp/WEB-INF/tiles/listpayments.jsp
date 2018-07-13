<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" href="/css/tables.css" rel="stylesheet" />
<table class="tableview tableshadow tableborder rjfifth rjsixth cjeighth">
	<thead>
		<tr>
			<th>Expense Reference</th>
			<th>Invoice Number</th>
			<th>Date Due</th>
			<th>Date Paid</th>
			<th>Amount Due</th>
			<th>Amount Paid</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
		</tr>
	</thead>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td><fmt:formatNumber type="number" pattern="00000000" value="${item.reference}" /></td>
			<td>${item.invoice_num}</td>
			<td><fmt:formatDate value="${item.date_due}"/></td>
			<td><fmt:formatDate value="${item.date_paid}"/></td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.amount_due}" /></td>
			<td> <fmt:formatNumber type="currency" currencySymbol="" value="${item.amount_paid}" /></td>
			<td><button type="button" onclick="window.location.href='/accounting/editopayment?id=${item.id}'">Edit</button>
			<c:choose>
				<c:when test="${empty item.date_paid}">
					<td><button type="button" onclick="window.location.href='/accounting/makeopayment?id=${item.id}'">Make Payment</button>	
				</c:when>
				<c:otherwise>
					<td><b>**Paid**</b></td>
				</c:otherwise>	
			</c:choose>	
		</tr>
	</c:forEach>
	<tfoot class="tablefooter">
		<tr>
			<td colspan="8"><button type="button" onclick="window.location.href='/accounting/listoverhead'">Back</button></td>
		</tr>
	</tfoot>
</table>