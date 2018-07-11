<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/autocomplete.css" />
<sf:form method="post" action="/admin/updatesales" modelAttribute="sales">
	<table class="fancy-table tableshadow" style="position: fixed; top: 100px; right: 150px;">
		<tr>
			<td><b>Invoice Number:</b><br> <sf:input class="fancy" path="invoice_num" readonly="true" /></td>
			<td><b>Customer Name: </b><br> <sf:input class="fancy" path="customer_name" readonly="true" /></td>
			<td><b>Price: </b><br>
				<div class="fancy">
					<fmt:formatNumber type="currency" currencySymbol="" value="${sales.total_price}" />
				</div></td>
		</tr>
		<tr>
			<td><b>Date Ordered: </b><br>
				<div class="fancy">
					<fmt:formatDate value="${sales.ordered}" />
				</div></td>
			<td><b>Date Processed :</b><br>
				<div class="fancy">
					<fmt:formatDate value="${sales.processed}" />
				</div></td>
			<td><b>Cash or Credit: </b><br> <sf:input class="fancy" path="payment_type" readonly="true" /></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit">
					<b>Accept</b>
				</sf:button></td>
		</tr>
		<tfoot>
			<tr>
				<td colspan="6"><div id="subtotal" class="totalsDiv"></div></td>
			</tr>
			<tr>
				<td colspan="6"><div id="totalTax" class="totalsDiv"></div></td>
			</tr>
			<tr>
				<td colspan="6"><div id="grandTotal" class="totalsDiv"></div></td>
			</tr>

		</tfoot>
	</table>
	<sf:hidden path="reference" />
	<sf:hidden path="user_id" />
	<sf:hidden path="total_price" />
	<sf:hidden path="ordered" />
	<sf:hidden path="processed" />
	<sf:hidden path="shipped" />
	<sf:hidden path="customer_id" />
	<sf:hidden path="changed" />

	<div class="scrollPanel">
		<c:set var="subtotal" value="0" />
		<c:set var="totalTax" value="0" />
		<c:set var="grandTotal" value="0" />
		<c:if test="${sales.salesItem.size() > 0}">
			<table class="fieldTable tableborder tableshadow rjthird">
				<tr>
					<th colspan="7">Invoice</th>
				</tr>
				<tr>
					<th>Qty</th>
					<th>Product Name</th>
					<th>Price (each)</th>
					<th>Total</th>
					<th>Tax</th>
				</tr>
				<c:forEach var="item" items="${sales.salesItem}">
					<tr>
						<td>${item.qty}</td>
						<td>${item.product_name}</td>
						<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.sold_for}" /></td>
						<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.qty * item.sold_for}" /></td>
						<td><fmt:formatNumber type="currency" currencySymbol="" value="${(item.qty * item.sold_for) * .08}" /></td>
					</tr>
					<c:set var="subtotal" value="${subtotal + (item.qty * item.sold_for)}" />
					<c:set var="totalTax" value="${subtotal * .08}" />
					<c:set var="grandTotal" value="${subtotal + totalTax}" />
				</c:forEach>
			</table>
		</c:if>
	</div>
</sf:form>
<script type="text/javascript">
	var subttl = ${subtotal};
	var ttltax = ${totalTax};
	var grandttl = ${grandTotal};
	function totals() {
		$("#subtotal").text("Subtotal: " + subttl + "    ");
		$("#totalTax").text("Tax: " + ttltax + "    ");
		$("#grandTotal").text("Grand Total: " + grandttl + "    ");
	}
</script>
<c:if test="${sales.salesItem.size() > 0}">
	<script>
		totals();
	</script>
</c:if>

