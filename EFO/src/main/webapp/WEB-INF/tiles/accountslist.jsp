<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<table class="tableview tableshadow tableborder rjthird tableSpacing" >
	<thead>
		<tr>
			<th>Account Number</th>
			<th>Account Name</th>
			<th>Account Balance</th>
			<th>Account Type</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
		</tr>
	</thead>
	<c:forEach var="item" items="${objectList.pageList}" >
		<tr>
			<td>${item.accountNum}</td>
			<td>${item.accountName}</td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.accountBalance}"/>
			<td>${item.accountType}</td>
			<td><button type="button" onclick="window.location.href='/accounting/editaccount?account=${item.accountNum}'">View/Edit</button></td>
			<td><button type="button" onclick="remove('${item.accountNum}')">Delete</button></td>
		</tr>
	</c:forEach>
	<tfoot class="tablefooter" >
		<tr>
			<td colspan="5"><button type="button" onclick="window.location.href='/accounting/newaccount'">New Account</button></td>
			<td><button type="button" onclick="window.location.href='/#tabs-4'">Back</button></td>
		</tr>
	</tfoot>
</table>
<script type="text/javascript">
	function remove(account) {
		if (confirm("Are you sure you want to delete acount # " + account + " ?") == true){
			window.location.href = "/accounting/deleteaccount?account=" + account;
		}
	}
</script>