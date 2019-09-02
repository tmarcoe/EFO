<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />

<table class="tableview tableshadow tableborder rjsixth">
	<tr>
		<th>Title</th>
		<th>Begin Period</th>
		<th>End Period</th>
		<th>Department</th>
		<th>Created On</th>
		<th>Total Cost</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
	</tr>
		<c:forEach var="item" items="${objectList.pageList}">
			<tr>
				<td>${item.title}</td>
				<td><fmt:formatDate value="${item.begin}"/> </td>
				<td><fmt:formatDate value="${item.end}"/> </td>
				<td>${item.department}</td>
				<td><fmt:formatDate value="${item.creation}"/> </td>
				<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.total}"/></td>
				<td><button type="button" onclick="window.location.href='/budget/editbudget?reference=${item.reference}'">Edit</button></td>
				<td><button type="button" onclick="window.location.href='/budget/listbudgetitems/${item.reference}/0?parent='">Budget Items</button></td>
				<td><button type="button" onclick="submitBudget(${item.reference}, ${item.total})">Submit Budget</button></td>
				
			</tr>
		</c:forEach>
		<tfoot class="tablefooter">
			<tr>
				<td colspan="8"><button type="button" onclick="window.location.href='/budget/newbudget'">New Budget</button></td>
				<td ><button type="button" onclick="window.location.href='/#tabs-8'">Back</button></td>
			</tr>
		</tfoot>
</table>
<div id="warning" class="modal" >
	<div class="modal-content small-modal fancy" >
		<h3>You need to set your budget total first.</h3>
		<button class="fancy" type="button" onclick="$('#warning').hide()" >OK</button>
	</div>
</div>
<script type="text/javascript">
	function submitBudget(reference, total) {
		if (total > 0) {
			window.location.href='/budget/submitbudget/' + reference;
		}else{
			$("#warning").show();
		}
	}
</script>
