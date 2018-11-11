<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" href="/css/tables.css" rel="stylesheet" />
<table class="tableview tableshadow tableborder spread-out">
	<tr>
		<th>Investor</th>
		<th>Amount</th>
		<th>Time Stamp</th>
		<th>&nbsp;</th>
	</tr>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td>${item.name}</td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.amount}"/></td>
			<td><fmt:formatDate value="${item.time_stamp}"/></td>
			<td><button type="button" onclick="window.location.href='/accounting/editequity?id=${item.id}'" >Edit</button>
		</tr>
	</c:forEach>
	<tfoot class="tablefooter">
		<tr>
			<td colspan="3"><button type="button" onclick="window.location.href='/accounting/newequity'">New Equity</button></td>
			<td><button type="button" onclick="window.location.href='/#tabs-4'">Back</button></td>			
		</tr>
	</tfoot>
</table>
