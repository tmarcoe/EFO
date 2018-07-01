<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form method="post" action="/admin/stockorder" modelAttribute="order">
	<div class="centerTitle">Product: ${order.product_name}</div>
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Delivery Date</b></td>
			<td><b>Amount Recieved</b></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" type="date" path="delivery_date" /></td>
			<td><sf:input class="fancy" type="number" step=".01" path="amt_this_shipment" />
		</tr>
		<tr>
			<td><sf:errors class="error" path="delivery_date" /></td>
			<td><sf:errors class="error" path="amt_this_shipment" /></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit" ><b>Receive</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()" ><b>Back</b></sf:button>
		</tr>
	</table>
	<sf:hidden path="sku"/>
	<sf:hidden path="product_name"/>
	<sf:hidden path="invoice_num"/>
	<sf:hidden path="vendor"/>
	<sf:hidden path="wholesale"/>
	<sf:hidden path="amt_ordered"/>
	<sf:hidden path="amt_received"/>
	<sf:hidden path="payment_type"/>
	<sf:hidden path="order_date"/>
	<sf:hidden path="status"/>
</sf:form>