<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<table class="tableview tableshadow tableborder spread-out rjfifth rjeighth">
	<thead>
		<tr>
			<th>Item Name</th>
			<th>Purchase Date</th>
			<th>Purchase Type</th>
			<th>Supplier</th>
			<th>Price</th>
			<th>Deprecation</th>
			<th>Lifetime</th>
			<th>Salvage Value</th>
			<th>&nbsp;</th>
		</tr>
	</thead>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td>${item.item_name}</td>
			<td><fmt:formatDate value="${item.date_purchased}"/></td>
			<td>${item.purchase_type}</td>
			<td>${item.vendor}</td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.item_cost}" /></td>
			<td>${item.depreciation_method}</td>
			<td>${item.lifetime} years</td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.salvage_value}" /></td>
			<td><button type="button" onclick="window.location.href='/accounting/editasset?reference=${item.reference}'">Edit</button></td>
		</tr>
	</c:forEach>
	<tfoot class="tablefooter">
		<tr>
			<td colspan="8"><button type="button" onclick="window.location.href='/accounting/newasset'" >Purchase Asset</button></td>
			<td><button type="button" onclick="window.location.href='/#tabs-4'">Back</button></td>
		</tr>
	</tfoot>
</table>