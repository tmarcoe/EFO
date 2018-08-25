<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form id="orderProduct" method="post" action="/admin/updorderitem" modelAttribute="orderItem" autocomplete="off">
	<table class="fancy-table tableshadow">
		<tr>
			<td colspan="2"><b>SkU #: <br></b>
			<sf:input path="sku" class="fancy" readonly="true" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>Product Name: <br></b>
			<sf:input path="product_name" class="fancy" readonly="true" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>Quantity: <br></b>
			<sf:input path="amt_ordered" class="fancy" type="number" step="1" /></td>
		</tr>
		<tr>
			<td colspan="2"><sf:errors path="amt_ordered" class="error" />
		</tr>
		<tr>
			<td><button class="fancy-button" type="submit">
					<b>Update</b>
				</button></td>
			<td><button class="fancy-button" type="button" onclick="window.history.back()">
					<b>Cancel</b>
				</button>
		</tr>
	</table>
	<sf:hidden path="id" />
	<sf:hidden path="reference" />
	<sf:hidden path="wholesale" />
	<sf:hidden path="amt_received" />
	<sf:hidden path="delivery_date" />
</sf:form>