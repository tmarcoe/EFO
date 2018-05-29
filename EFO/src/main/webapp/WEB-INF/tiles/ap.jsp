<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<table class="tableview tableshadow tableborder rjfourth rjsixth cjseventh" >
	<thead>
		<tr>
			<th>Invoice Number</th>
			<th>Begin Payments</th>
			<th>Supplier</th>
			<th>Type</th>
			<th>Total Due</th>
			<th>Due By</th>
			<th>Balance</th>
			<th colspan="2">&nbsp;</th>
		</tr>
	</thead>
	<c:forEach var="item" items="${objectList.pageList}" >
		<tr>
			<td>${item.invoice_num}</td>
			<td><fmt:formatDate value="${item.date_begin}"/></td>
			<td>${item.supplier}</td>
			<td>${item.type}</td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.total_due}"/></td>
			<td><fmt:formatDate value="${item.date_due}"/></td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.total_balance}"/></td>
			<td><button type="button" onclick="window.location.href = '/accounting/appaymentlist?invoice_num=${item.invoice_num}'">View Payments</button></td>
			<td><button type="button" onclick="window.location.href = '/accounting/editpayable?invoice_num=${item.invoice_num}'">Edit</button></td>
		</tr>
	</c:forEach>
	<tfoot class="tablefooter" >
		<tr>
			<td colspan="8"><button type="button" onclick="window.location.href='/accounting/newpayable'">New Payable</button></td>
			<td><button type="button" onclick="window.location.href='/#tabs-4'">Back</button></td>
		</tr>
	</tfoot>
</table>