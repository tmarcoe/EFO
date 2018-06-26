<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form method="post" action="/admin/updateproduct" modelAttribute="product">
	<table  class="fancy-table tableshadow">
		<tr>
			<td><b>SKU</b></td>
			<td><b>UPC</b></td>
			<td><b>Name</b></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="sku" readonly="true" /></td>
			<td><sf:input class="fancy" path="upc"/></td>
			<td colspan="2"><sf:input class="fancy" path="product_name"  size="55" /></td>
			<td><b>Amount In Stock: </b>${product.inventory.amt_in_stock}</td>
		</tr>
		<tr>
			<td><sf:errors path="sku" class="error" /></td>
			<td><sf:errors path="upc" class="error" /></td>
			<td><sf:errors path="product_name" class="error" /></td>
		</tr>
		<tr>
			<td><b>Reorder At</b></td>
			<td><b>Price</b></td>
			<td><b>Unit</b></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" type="number" step=".01" path="inventory.min_amount"/></td>
			<td><sf:input class="fancy" type="number" step=".01" path="price"/></td>
			<td><sf:select class="fancy" path="unit">
				<sf:option value="Each">Each</sf:option>
				<sf:option value="Pack">Pack</sf:option>
				<sf:option value="Hourly">Hourly</sf:option>
				<sf:option value="Daily">Daily</sf:option>
				<sf:option value="Weekly">Weekly</sf:option>
				<sf:option value="Monthly">Monthly</sf:option>
				<sf:option value="Annually">Annually</sf:option>
				<sf:option value="Ounce">Ounce</sf:option>
				<sf:option value="Pound">Pound</sf:option>
				<sf:option value="Gallon">Gallon</sf:option>
				<sf:option value="Gram">Gram</sf:option>
				<sf:option value="Kilogram">Kilogram</sf:option>
				<sf:option value="Liter">Liter</sf:option>
			</sf:select></td>
			<td><b>Consignment: </b><sf:checkbox path="consignment"/></td>
		</tr>
		<tr>
			<td><sf:errors path="inventory.min_amount" class="error"/></td>
			<td><sf:errors path="price" class="error"/></td>
			<td><sf:errors path="unit" class="error"/></td>
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
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit"><b>Save</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()"><b>Cancel</b></sf:button>
		</tr>
	</table>
	<sf:hidden path="inventory.sku"/>
	<sf:hidden path="inventory.amt_in_stock"/>
	<sf:hidden path="inventory.min_amount"/>
</sf:form>