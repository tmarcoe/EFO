<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" href="/css/tables.css" rel="stylesheet" />
<table class="tableview tableshadow tableborder">
	<tr>
		<th>Shareholder</th>
		<th>Purchased On</th>
		<th>Price</th>
		<th>Value At</th>
		<th>Number of Shares</th>
	</tr>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td>${item.holders_name}</td>
			<td><fmt:formatDate value="${item.date_purchased}"/></td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.price}"/></td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.valued_at}"/></td>
			<td>${item.shares}</td>
		</tr>
	</c:forEach>
</table>
