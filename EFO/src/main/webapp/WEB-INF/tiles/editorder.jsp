<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<sf:form method="post" action="/admin/updproductorder" modelAttribute="productOrder">
	<table class="table">
		<tr>
			<td><b>Invoice #</b></td>
			<td><b>Vendor</b></td>
			<td><b>Order Price</b></td>
			<td><b>Order Qty</b></td>
			<td><b>Payment Type</b></td>
			<td><b>Date Ordered</b></td>
			<td><b>Date Received</b></td>
		</tr>
		<tr>
			<td><sf:input path="invoice_num" /></td>
			<td><sf:input path="vendor" /></td>
			<td><sf:input path="amount" /></td>
			<td><sf:input path="qty" /></td>
			<td><sf:select path="payment_type">
				<sf:option value="">---Select---</sf:option>
				<sf:option value="Cash">Cash</sf:option>
				<sf:option value="Credit">Credit</sf:option>
			</sf:select></td>
			<td><sf:input path="order_date" /></td>
			<td><sf:input path="delivery_date" /></td>
		</tr>
		<tr>
			<td><sf:errors path="invoice_num" class="error" /></td>
			<td><sf:errors path="vendor" class="error" /></td>
			<td><sf:errors path="amount" class="error" /></td>
			<td><sf:errors path="qty" class="error" /></td>
			<td><sf:errors path="payment_type" class="error" /></td>
			<td><sf:errors path="order_date" class="error" /></td>
			<td><sf:errors path="delivery_date" class="error" /></td>
		</tr>
		<tr>
			<td><sf:button type="submit">Save</sf:button></td>
			<td><sf:button type="button" onclick="window.history.back()">Cancel</sf:button></td>
		</tr>
	</table>
</sf:form>