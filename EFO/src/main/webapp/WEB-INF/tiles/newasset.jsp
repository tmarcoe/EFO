<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<sf:form method="post" action="/accounting/addasset"
	modelAttribute="asset">
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Name: </b><br> <sf:input class="fancy"
					path="item_name" /></td>
			<td><b>Purchase Date: </b><br> <sf:input type="date"
					class="fancy" path="date_purchased" /></td>
			<td><b>Supplier: </b><br> <sf:input class="fancy"
					path="vendor" /></td>
			<td><b>Cost: </b><br> <sf:input type="number" step=".01"
					class="fancy" path="item_cost" /></td>
		</tr>
		<tr>
			<td><sf:errors path="item_name" class="error" /></td>
			<td><sf:errors path="date_purchased" class="error" /></td>
			<td><sf:errors path="vendor" class="error" /></td>
			<td><sf:errors path="item_cost" class="error" /></td>
		</tr>
		<tr>
			<td><b>Depreciation Method: </b><br> <sf:select class="fancy" path="depreciation_method">
					<sf:option value="Double Declining">Double Declining</sf:option>
					<sf:option value="Straight Method">Straight Method</sf:option>
				</sf:select></td>
			<td><b>Salvage Value: </b><br> <sf:input type="number" step=".01"
					class="fancy" path="salvage_value" /></td>
			<td><b>Asset Lifetime: </b><br> <sf:input type="number" step="1"
					class="fancy" path="lifetime" /></td>
		</tr>
		<tr>
			<td><sf:errors path="item_description" class="error" /></td>
			<td><sf:errors path="salvage_value" class="error" /></td>
			<td><sf:errors path="lifetime" class="error" /></td>
		</tr>
		<tr>
			<td colspan="4"><b></b><br> <sf:textarea
					class="fancy-textarea" path="item_description" rows="4" cols="102" /></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit" ><b>Save</b></sf:button> </td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()" ><b>Cancel</b></sf:button></td>
		</tr>
	</table>

</sf:form>
