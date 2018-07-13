<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" href="/css/tables.css" rel="stylesheet" />
<table class="tableview tableshadow tableborder">
	<thead>
		<tr>
			<th>Reference #</th>
			<th>Account #</th>
			<th>Vendor</th>
			<th>Reason</th>
			<th>Payment Schedule</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
		</tr>
	</thead>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td><fmt:formatNumber type="number" pattern="00000000" value="${item.reference}" /></td>
			<td>${item.account}</td>
			<td>${item.vendor}</td>
			<td>${item.reason}</td>
			<td>${item.schedule}</td>
			<td><button type="button" onclick="window.location.href='/accounting/editexpense?reference=${item.reference}'">Edit</button></td>
			<td><button type="button" onclick="window.location.href='/accounting/listpayments?reference=${item.reference}'">Payments</button></td>
		</tr>
	</c:forEach>
	<tfoot class="tablefooter">
		<tr>
			<td colspan="6"><button type="button" onclick="window.location.href='/accounting/newexpense'">New
					Overhead Expense</button></td>
			<td><button type="button" onclick="window.location.href='/#tabs-4'">back</button></td>
		</tr>
	</tfoot>
</table>