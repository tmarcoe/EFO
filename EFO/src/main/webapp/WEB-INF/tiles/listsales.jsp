<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<table class="tableview tableshadow tableborder rjsecond">
	<tr>
		<th>Invoice Number</th>
		<th>Total Price</th>
		<th>Date Ordered</th>
		<th>Date Processed</th>
		<th>Date Shipped</th>
		<th>Cash or Credit?</th>
		<th>Customer Name</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
	</tr>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td>${item.invoice_num}</td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.total_price}" /></td>
			<td><fmt:formatDate value="${item.ordered}" /></td>
			<td><fmt:formatDate value="${item.processed}" /></td>
			<td><fmt:formatDate value="${item.shipped}" /></td>
			<td>${item.payment_type}</td>
			<td>${item.customer_name}</td>
			<td><button type="button" onclick="window.location.href='/admin/editsales?reference=${item.reference}'">View</button></td>
			<td><button type="button" onclick="window.location.href='/admin/shipsales?reference=${item.reference}'" >Ship Order</button>
		</tr>
	</c:forEach>
	<tfoot class="tablefooter">
		<tr>
			<td colspan="9"><button type="button" onclick="window.location.href='/admin/browseproducts'">Back</button></td>
		</tr>
	</tfoot>
</table>
