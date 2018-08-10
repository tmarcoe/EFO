<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form id="orderProduct" method="post" action="/admin/updsalesitem"
	modelAttribute="salesItem" autocomplete="off">
	<table class="fancy-table tableshadow">
		<tr>
			<td colspan="2"><b>Invoice #: <br></b><sf:input path="reference" class="fancy" readonly="true"/></td>
		</tr>
		<tr>
			<td colspan="2"><b>SkU #: <br></b><sf:input path="sku" class="fancy" readonly="true"/></td>
		</tr>
		<tr>
			<td colspan="2"><b>Product Name: <br></b><sf:input path="product_name" class="fancy" readonly="true"/></td>
		</tr>
		<tr>
			<td colspan="2"><b>Quantity: <br></b><sf:input path="qty" class="fancy" type="number" step="1"/></td>
		</tr>
		<tr>
			<td colspan="2"><sf:errors path="qty" class="error"/>
		</tr>
		<tr>
			<td><button class="fancy-button" type="submit"><b>Update</b></button></td>
			<td><button class="fancy-button" type="button" onclick="window.history.back()"><b>Cancel</b></button>
		</tr>
	</table>
	<sf:hidden path="item_id"/>
	<sf:hidden path="sold_for"/>
	<sf:hidden path="regular_price"/>
	<sf:hidden path="options"/>
</sf:form>