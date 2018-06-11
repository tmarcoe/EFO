<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<table class="tableview tableshadow tableborder" >
	<tr>
		<th>SKU</th>
		<th>UPC</th>
		<th>Name</th>
		<th>In Stock</th>
		<th>Min Amount</th>
		<th>Price</th>
		<th>Sold By</th>
		<th>On Sale?</th>
	</tr>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td>${item.sku}</td>
			<td>${item.upc}</td>
			<td>${item.product_name}</td>
			<td>${item.amount_in_stock}</td>
			<td>${item.min_amount}</td>
			<td>${item.price}</td>
			<td>${item.unit}</td>
			<td>${item.on_sale}</td>
		</tr>
	</c:forEach>
</table>