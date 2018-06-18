<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<sf:form method="post" action="/admin/stockorder" modelAttribute="order">
	<div class="centerTitle">Product: ${order.product_name}</div>
	<table class="table">
		<tr>
			<td><b>Delivery Date</b></td>
			<td><b>Amount Recieved</b></td>
		</tr>
		<tr>
			<td><sf:input type="date" path="delivery_date" /></td>
			<td><sf:input type="number" step=".01" path="amt_this_shipment" />
		</tr>
		<tr>
			<td><sf:errors class="error" path="delivery_date" /></td>
			<td><sf:errors class="error" path="amt_this_shipment" /></td>
		</tr>
		<tr>
			<td><sf:button type="submit" >Receive</sf:button></td>
			<td><sf:button type="button" onclick="window.history.back()" >Back</sf:button>
		</tr>
	</table>
	<sf:hidden path="id"/>
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