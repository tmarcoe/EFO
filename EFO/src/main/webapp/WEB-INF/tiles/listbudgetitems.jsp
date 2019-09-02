<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<div class="scrollPanel" >
<table class="tableview tableshadow tableborder rjthird">

	<tr>
		<th>Item</th>
		<th>Parent</th>
		<th>Amount</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
	</tr>
		<c:forEach var="item" items="${budgetList}">
			<tr>
				<td>${item.category}</td>
				<td>${item.parent}</td>
				<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.amount}" /></td>
				<td><button type="button" onclick="window.location.href='/budget/listbudgetitems/${reference}/${level + 1}?parent=${item.category}'">SubCategories</button></td>
				<td><button type="button" onclick="window.location.href='/budget/editbudgetitem?id=${item.id}'">Edit</button></td>
				<td><button type="button" onclick="window.location.href='/budget/deletebudgetitem/${reference}/${item.id}?parent=${item.category}'">Delete</button>				
			</tr>
		</c:forEach>
		<tfoot class="tablefooter">
			<tr>
				<td colspan="4"><button type="button" onclick="window.location.href='/budget/newbudgetitem/${reference}/${level}?parent=${parent}'">New Buget Item</button></td>
				<c:choose>
					<c:when test="${parent != ''}">
						<td><button type="button" onclick="window.location.href='/budget/uponelevel/${reference}/${level - 1}?parent=${parent}'">Up One Level</button></td>
					</c:when>
					<c:otherwise>
						<td>&nbsp;</td>
					</c:otherwise>
				</c:choose>				
				<td><button type="button" onclick="window.location.href='/budget/listbudget'">Back</button>
			</tr>
		</tfoot>
</table>
</div>