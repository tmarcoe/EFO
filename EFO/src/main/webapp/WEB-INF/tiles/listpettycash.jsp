<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<table class="tableview tableshadow tableborder">
	
	<thead>
		<tr>
			<th>Transaction ID</th>
			<th>Timestamp</th>
			<th>Recipient</th>
			<th>Amount</th>
			<th>&nbsp;</th>
		</tr>
	</thead>
	<c:forEach var="item" items="${objectList.pageList}">
	<tr>
		<td><fmt:formatNumber value="${item.id}" pattern="00000000"/></td>
		<td><fmt:formatDate value="${item.timeStamp}"/></td>
		<td>${item.recipient}</td>
		<td><fmt:formatNumber  type="currency" currencySymbol="" value="${item.amount}" /></td>
		<td><button type="button" onclick="window.location.href='/accounting/editpettycash?id=${item.id}'" >Edit</button></td>
	</tr>
	</c:forEach>
	<tfoot class="tablefooter" >
		<tr>
			<td colspan="2"><button type="button" onclick="window.location.href='/accounting/replenish'">Replenish Petty Cash</button></td>
			<td><button type="button" onclick="window.location.href='/accounting/newpettycash'">New Disbursement</button></td>
			<td><button type="button" onclick="window.location.href='/accounting/changeamounts'">Setup</button></td>
			<td ><button type="button" onclick="window.location.href='/#tabs-4'">Back</button></td>
		</tr>
	</tfoot>
</table>