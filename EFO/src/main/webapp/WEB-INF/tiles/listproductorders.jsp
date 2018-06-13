<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<table class="tableview tableshadow tableborder" >
	<tr>
		<th>Invoice #</th>
		<th>Vendor</th>
		<th>Order Price</th>
		<th>Quantity Ordered</th>
		<th>Order Date</th>
		<th>Delivery Date</th>
	</tr>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td>${item.invoice_num}</td>
			<td>${item.vendor}</td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.amount}" /></td>
			<td>${item.qty}</td>
			<td><fmt:formatDate value="${item.order_date}"/></td>
			<td><fmt:formatDate value="${item.delivery_date}" /></td>
		</tr>
	</c:forEach>

</table> 