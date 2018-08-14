<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<table class="tableview tableshadow tableborder">

	<tr>
		<th>Item</th>
		<th>Parent</th>
		<th>Amount</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
	</tr>
		<c:forEach var="item" items="${objectList.pageList}">
			<tr>
				<td>${item.category}</td>
				<td>${item.parent}</td>
				<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.amount}" /></td>
				<td><button type="button" onclick="window.location.href='/accounting/listbudgetitems/${reference}/${item.category}'">SubCategories</button></td>
				<td><button type="button" onclick="window.location.href='/accounting/editbudgetitem?id=${item.id}'">Edit</button></td>
				<td><button type="button" onclick="window.location.href='/accounting/deletebudgetitem/${reference}/${item.category}/${item.id}'">Delete</button>				
			</tr>
		</c:forEach>
		<tfoot class="tablefooter">
			<tr>
				<td colspan="3"><button type="button" onclick="window.location.href='/accounting/newbudgetitem/${reference}/${parent}'">New Buget Item</button></td>
				<td><button type="button" onclick="window.location.href='/accounting/uponelevel/${reference}/${parent}'">Up One Level</button></td>
				<td><button type="button" onclick="window.location.href='/accounting/submitbudget/${reference}'">Submit</button></td>				
				<td><button type="button" onclick="window.location.href='/#tabs-5'">Back</button>
			</tr>
		</tfoot>
</table>