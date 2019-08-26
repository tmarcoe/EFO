<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />

<div class="centerHeading">
	<h2>${budget.title}</h2>
	<h3>FROM: <fmt:formatDate value="${budget.begin}"/> To: <fmt:formatDate value="${budget.end}"/></h3>
	<h3>Budget Total: <fmt:formatNumber type="currency" value="${budget.total}"/></h3>
</div>

<table class="tableview spread-out tableshadow tableborder rjsecond" style="font-size: 18px;">
	<tr>
		<th>Item</th>
		<th>Cost</th>
		<th>Source</th>
	</tr>
	<c:forEach var="item" items="${listItems}">
		<tr>
			<c:choose>
				<c:when test="${item[6] > 0}">
					<td><b>${item[0]}</b></td>
					<td><b><fmt:formatNumber type="currency" value="${item[5]}" /></b></td>
				</c:when>
				<c:otherwise>
					<td>${item[0]}</td>
					<td><fmt:formatNumber type="currency" value="${item[5]}" /></td>
				</c:otherwise>
			</c:choose>
			<td>${item[2]}</td>
		</tr>
		<c:if test="${not empty item[4]}">
			<tr>
				<td colspan="3"><b>Justification: </b>${item[4]}</td>
			</tr>
		</c:if>
	</c:forEach>
</table>
<table class="fancy-table">
	<tr>
		<td><button class="fancy-button" type="button" onclick="window.location.href='/budget/approve?reference=${reference}'">
				<b>Approve</b>
			</button></td>
		<td><button class="fancy-button" type="button" onclick="rejectBudget()">
				<b>Reject</b>
			</button></td>
		<td><button class="fancy-button" type="button" onclick="window.history.back()">
				<b>Cancel</b>
			</button></td>
	</tr>
</table>
<div id="rej" class="modal">
	<div class="modal-content small-modal fancy">
		<table style="margin-left: auto; margin-right: auto;">
			<tr>
				<td><b>Reason for the rejection: </b><br> <textarea id="res" class="fancy-textarea" rows="5" cols="50"></textarea></td>
			</tr>
			<tr>
				<td><button class="fancy-button" type="button" onclick="submitReject()">
						<b>ok</b>
					</button></td>
			</tr>
		</table>
	</div>
</div>

<script type="text/javascript">
	function rejectBudget() {
		$("#rej").show();
	}
	
	function submitReject() {
		if ($.trim($("#res").val())) {
			window.location.href="/budget/reject?reference=${reference}&reason=" + $('#res').val();
		}else{
			$("#rej").hide();
		}

	}
</script>
