<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/autocomplete.css" />

<sf:form method="post" action="/admin/updproductorder" modelAttribute="productOrder">
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Invoice #: </b><br><sf:input class="fancy" path="reference" readonly="true"/></td>
			<td colspan="2"><b>Product Name: </b><br><sf:input class="fancy" path="product_name" readonly="true" size="47"/>
			<td><b>Vendor: </b><br><sf:input class="fancy" id="vendor" path="vendor" /></td>
		</tr>
		<tr>
			<td><sf:errors path="reference" class="error" /></td>
			<td><sf:errors path="product_name" class="error"/></td>
			<td><sf:errors path="vendor" class="error" /></td>
		</tr>
		<tr>
			<td><b>Order Price: </b><br><sf:input class="fancy" type="number" step=".01" path="wholesale" /></td>
			<td><b>Order Qty: </b><br><sf:input class="fancy" type="number" step=".01" path="amt_ordered" /></td>
			<td><b>Payment Type: </b><br><sf:input class="fancy" path="payment_type" readonly="true"/></td>
			<td><b>Date Ordered: </b><br><sf:input class="fancy" type="date" path="order_date" readonly="true" /></td>
		</tr>
		<tr>
			<td><sf:errors path="wholesale" class="error" /></td>
			<td><sf:errors path="amt_ordered" class="error" /></td>
			<td><sf:errors path="payment_type" class="error" /></td>
			<td><sf:errors path="order_date" class="error" /></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit"><b>Save</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()"><b>Cancel</b></sf:button></td>
		</tr>
	</table>
	<sf:hidden path="delivery_date" />
	<sf:hidden path="amt_received" />
	<sf:hidden path="sku"/>
	<sf:hidden path="status" />
</sf:form>
<script type="text/javascript">
	$('#vendor').devbridgeAutocomplete(
			{
				lookup : function(query, done) {
					var name = $("#vendor").val();
					$.getJSON("/rest/lookupvendor?name=" + name + "&type=R",
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
