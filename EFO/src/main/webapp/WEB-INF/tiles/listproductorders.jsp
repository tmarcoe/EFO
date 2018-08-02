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
		<th>Vendor</th>
		<th>Total Price</th>
		<th>Order Date</th>
		<th>Process Date</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
	</tr>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td>${item.invoice_num}</td>
			<td>${item.vendor}</td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.total_price}" /></td>
			<td><fmt:formatDate value="${item.order_date}"/></td>
			<td><fmt:formatDate value="${item.process_date}"/></td>
			<td><button type="button" onclick="window.location.href='/admin/receiveorder?reference=${item.reference}'">Receive Order</button></td>
			<td><button type="button" onclick="window.location.href='/admin/editproductorder?reference=${item.reference}'">Edit Order</button></td>
			<td><button type="button" onclick="window.location.href='/admin/cancelorder?reference=${item.reference}'">CancelOrder</button></td>
		</tr>
	</c:forEach>
	<tfoot  class="tablefooter">
		<tr>
			<td colspan="7"><button type="button" onclick="window.location.href='/admin/newproductordert'">New Order</button></td>
			<td><button type="button" onclick="window.location.href='/admin/listproduct'">Back</button></td>
		</tr>
	</tfoot>

</table> 