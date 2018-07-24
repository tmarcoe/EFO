<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<table class="tableview tableshadow tableborder spread-out ">
	<tr>
		<th>Loan Institution</th>
		<th>Date Approved</th>
		<th>Intrest</th>
		<th>Down Payment</th>
		<th>Payment Amount</th>
		<th>Number of Payments</th>
		<th>Payment Schedule</th>
		<th>Status</th>
		<th>&nbsp;</th>
	</tr>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td>${item.institution_name}</td>
			<td><fmt:formatDate value="${item.approval}"/></td>
			<td><fmt:formatNumber type="percent" value="${item.interest / 100}"/></td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.down_payment}"/></td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.each_payment}"/></td>
			<td>${item.num_payments}</td>
			<td>${item.schedule}</td>
			<td>${item.status}</td>
			<td><button type="button" onclick="window.location.href='/accounting/editloan?trans_id=${item.trans_id}'">Edit</button></td>
		</tr>
	</c:forEach>
	<tfoot class="tablefooter">
		<tr>
			<td colspan="8"><button type="button" onclick="window.location.href='/accounting/newloan'">New Loan</button></td>
			<td><button type="button" onclick="window.location.href='/#tabs-4'">Back</button></td>
		</tr>
	</tfoot>
</table>