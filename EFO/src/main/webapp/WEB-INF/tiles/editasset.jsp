<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/autocomplete.css" />

<sf:form method="post" action="/accounting/updateasset" modelAttribute="asset">
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Invoice Number: </b><br> <sf:input class="fancy" path="invoice_num" readonly="true" /></td>
			<td><b>Name: </b><br> <sf:input class="fancy" path="item_name" /></td>
			<td><b>Purchase Date: </b><br> <sf:input type="date" class="fancy" path="date_purchased" /></td>
			<td colspan="2"><b>Supplier: </b><br> <sf:input id="vendor" class="fancy" path="vendor" size="36" /></td>
		</tr>
		<tr>
			<td><sf:errors path="item_name" class="error" /></td>
			<td><sf:errors path="date_purchased" class="error" /></td>
			<td><sf:errors path="vendor" class="error" /></td>
		</tr>
		<tr>
			<td><b>Cost: </b><br> <sf:input type="number" step=".01" class="fancy" path="item_cost" /></td>
			<td><b>Lifetime (Years): </b><br> <sf:input type="number" step="1" class="fancy" path="lifetime" /></td>
			<td><b>Salvage Value: </b><br> <sf:input type="number" step=".01" class="fancy" path="salvage_value" /></td>
			<td><b>Depreciation Method: </b><br>
				<div class="fancy">${asset.depreciation_method}</div></td>
			<td><b>Purchase Type: </b><br>
				<div class="fancy">${asset.purchase_type}</div></td>
		</tr>
		<tr>
			<td><sf:errors path="item_cost" class="error" /></td>
			<td><sf:errors path="lifetime" class="error" /></td>
			<td><sf:errors path="salvage_value" class="error" /></td>
			<td><sf:errors path="depreciation_method" class="error" /></td>
			<td><sf:errors path="purchase_type" class="error" /></td>
		</tr>
		<tr>
			<td colspan="4"><b>Item Description: </b><br> <sf:textarea class="fancy-textarea" path="item_description"
					rows="4" cols="102" /></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit">
					<b>Save</b>
				</sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()">
					<b>Cancel</b>
				</sf:button></td>
		</tr>
	</table>

	<sf:hidden path="depreciation_method" />
	<sf:hidden path="purchase_type" />
	<sf:hidden path="payables.invoice_num" />
	<sf:hidden path="payables.date_begin" />
	<sf:hidden path="payables.supplier" />
	<sf:hidden path="payables.type" />
	<sf:hidden path="payables.total_due" />
	<sf:hidden path="payables.down_payment" />
	<sf:hidden path="payables.interest" />
	<sf:hidden path="payables.each_payment" />
	<sf:hidden path="payables.num_payments" />
	<sf:hidden path="payables.schedule" />
	<sf:hidden path="payables.total_balance" />
	<sf:hidden path="payables.status" />
</sf:form>
<script type="text/javascript">
	$('#vendor').devbridgeAutocomplete(
			{
				lookup : function(query, done) {
					var name = $("#vendor").val();
					$.getJSON("/rest/lookupvendor?name=" + name + "&type=C",
							function(result) {
								var data = {
									suggestions : result
								};
								done(data);
							})
							.fail(
									function(jqXHR, textStatus, errorThrown) {
										alert("error " + textStatus + "\n"
												+ "incoming Text "
												+ jqXHR.responseText);
									});

				},
				onSelect : function(data) {
					$("#vendor").val(data.data.company_name);
				}
			});
</script>