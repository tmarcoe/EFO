<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form method="post" action="/admin/updateeachproduct" modelAttribute="product">
	<table  class="fancy-table tableshadow">
		<tr>
			<td><b>SKU: </b><br><sf:input class="fancy" path="sku" readonly="true" /></td>
			<td><b>UPC: </b><br><sf:input class="fancy" path="upc" readonly="true" /></td>
			<td colspan="2"><b>Product Name: </b><br><sf:input class="fancy" path="product_name"  size="56" readonly="true" /></td>
			<td><b>Unit: </b><br><sf:input class="fancy" path="unit" readonly="true"/>
		</tr>
		<tr>
			<td><b>Reorder At: </b><br><sf:input class="fancy" type="number" step=".01" path="min_amount"/></td>
			<td><b>Order Amount: </b><br><sf:input class="fancy" type="number" step=".01" path="order_amount"/></td>
			<td><b>Price: </b><br><sf:input class="fancy" type="number" step=".01" path="price"/></td>
			<td><b>Shelf Life (in days): </b><br><sf:input class="fancy" type="number" step="1" path="shelf_life"/>
		</tr>
		<tr>
			<td><sf:errors path="min_amount" class="error"/></td>
			<td><sf:errors path="order_amount" class="error"/></td>
			<td><sf:errors path="price" class="error"/></td>
			<td><sf:errors path="shelf_life" class="error"/></td>
		</tr>
		<tr>
			<td><b>Category</b></td>
			<td><b>Subcategory</b></td>
			<td colspan="3"><b>Keywords (separated by comas)</b></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="category" /></td>
			<td><sf:input class="fancy" path="subcategory"/></td>
			<td colspan="3"><sf:textarea class="fancy-textarea" path="keywords" rows="4" cols="58"/></td>
		</tr>
		<tr>
			<td><sf:errors path="category" class="error"/></td>
			<td><sf:errors path="subcategory" class="error"/></td>
			<td colspan="3"><sf:errors path="keywords" class="error"/></td>
		</tr>
		<tr>
			<td><b>On Sale? </b><sf:checkbox class="fancy" path="on_sale" /></td>
			<td><b>Discontinue Product? </b><sf:checkbox class="fancy" path="discontinue"/></td>
			<td><b>Consignment: </b><sf:checkbox path="consignment"/></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit"><b>Save</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()"><b>Cancel</b></sf:button>
		</tr>
	</table>
</sf:form>