<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<c:if test="${param.error != null}">
	<div class="errorTitle" >You cannot cancel a partially filled order.</div>
</c:if>

<table class="tableview tableshadow tableborder" >
	<tr>
		<th>Invoice #</th>
		<th>Product</th>
		<th>Vendor</th>
		<th>Order Price</th>
		<th>Quantity Ordered</th>
		<th>Quantity Received</th>
		<th>Order Date</th>
		<th>Delivery Date</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
	</tr>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td>${item.reference}</td>
			<td>${item.product_name}</td>
			<td>${item.vendor}</td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.wholesale}" /></td>
			<td>${item.amt_ordered}</td>
			<td>${item.amt_received}</td>
			<td><fmt:formatDate value="${item.order_date}"/></td>
			<td><fmt:formatDate value="${item.delivery_date}" /></td>
			<td><button type="button" onclick="window.location.href='/admin/receiveorder?reference=${item.reference}'">Receive Order</button></td>
			<td><button type="button" onclick="window.location.href='/admin/editproductorder?reference=${item.reference}'">Edit Order</button></td>
			<td><button type="button" onclick="window.location.href='/admin/cancelorder?reference=${item.reference}'">CancelOrder</button></td>
		</tr>
	</c:forEach>
	<tfoot  class="tablefooter">
		<tr>
			<td colspan="11"><button type="button" onclick="window.location.href='/admin/listproduct'">Back</button></td>
		</tr>
	</tfoot>

</table> 