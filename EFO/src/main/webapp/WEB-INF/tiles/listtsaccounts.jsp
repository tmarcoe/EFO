<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />

<table class="tableview tableshadow tableborder padding" >
	<tr>
		<th>Account Number</th>
		<th>Department</th>
		<th>Description</th>
		<th>Starting Date</th>
		<th>Ending Date</th>
		<th>Active</th>
		<th>&nbsp;</th>
	</tr>
		<c:forEach var="item" items="${objectList.pageList}">
			<tr>
				<td>${item.account_number}</td>
				<td>${item.department}</td>
				<td>${item.description}</td>
				<td><fmt:formatDate value="${item.begins}"/></td>
				<td><fmt:formatDate value="${item.expires}"/></td>
				<td>${item.active}</td>
				<td><button type="button" onclick="window.location.href='/timesheet/edittsaccount?account_num=${item.account_number}'"><b>Edit</b></button></td>
			</tr>
		</c:forEach>
		<tfoot class="tablefooter">
			<tr>
				<td colspan="6"><button type="button" onclick="window.location.href='/#tabs-9'"><b>Back</b></button></td>
				<td><button type="button" onclick="window.location.href='/timesheet/newtsaccount'"><b>New Account</b></button></td>
			</tr>
		</tfoot>
</table>
