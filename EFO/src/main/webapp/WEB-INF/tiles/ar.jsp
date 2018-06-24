<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<table class="tableview tableshadow tableborder rjfourth rjfifth rjsixth rjseventh rjeighth cjnineth rjtenth cjeleventh" >
	<thead>
		<tr>
			<th>Invoice Number</th>
			<th>Invoice Date</th>
			<th>Customer</th>
			<th>Total Due</th>
			<th>Down Payment</th>
			<th>Interest</th>
			<th>Number Of Payments</th>
			<th>Each Payment</th>
			<th>Payment Schedule</th>
			<th>Balance</th>
			<th>Status</th>
			<th colspan="2">&nbsp;</th>
		</tr>
	</thead>
	<c:forEach var="item" items="${objectList.pageList}" >
		<tr>
			<td>${item.invoice_num}</td>
			<td><fmt:formatDate value="${item.invoice_date}"/></td>
			<td>${item.customer}</td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.total_due}"/></td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.down_payment}"/></td>
			<td><fmt:formatNumber type="percent" value="${item.interest / 100}"/></td>
			<td>${item.each_payment}</td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.each_payment}"/></td>
			<td>${item.schedule}</td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.total_balance}"/></td>
			<td>${item.status}</td>
			<td><button type="button" onclick="window.location.href = '/accounting/arpaymentlist?invoice_num=${item.invoice_num}'">View Payments</button></td>
			<td><button type="button" onclick="window.location.href = '/accounting/editreceivable?invoice_num=${item.invoice_num}'">Edit</button></td>
		</tr>
	</c:forEach>
	<tfoot class="tablefooter" >
		<tr>
			<td colspan="12"><button type="button" onclick="window.location.href='/admin/choosecustomer'">New Receivable</button></td>
			<td><button type="button" onclick="window.location.href='/#tabs-4'">Back</button></td>
		</tr>
	</tfoot>
</table>