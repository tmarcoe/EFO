<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form id="orderProduct" method="post" action="/admin/retailorder"
	modelAttribute="product" autocomplete="off">
	<table class="fancy-table tableshadow">
		<tr>
			<td colspan="2"><b>SKU Code: <br></b> <sf:input
					class="fancy" id="sku" path="sku" readonly="true" /></td>
			<td colspan="2"><b>UPC Code: <br></b> <sf:input
					class="fancy" id="upc" path="upc" readonly="true" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>Product Name: <br></b> <sf:input
					class="fancy" id="product_name" path="product_name" readonly="true" /></td>
			<td colspan="2"><b>Price: <br></b> <sf:input class="fancy"
					id="price" path="price" readonly="true" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>Units Sold As: <br></b> <sf:input
					class="fancy" id="unit" path="unit" readonly="true" /></td>
			<td colspan="2"><b>Category: <br></b> <sf:input
					class="fancy" id="category" path="category" readonly="true" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>Sub-Category: <br></b> <sf:input
					class="fancy" id="subcategory" path="subcategory" readonly="true" /></td>
			<td colspan="2"><b>Keywords: <br></b> <sf:input
					class="fancy" id="keywords" path="keywords" readonly="true" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>Is On Sale: <br></b> <sf:input
					class="fancy" id="on_sale" path="on_sale" readonly="true" /></td>
			<td colspan="2"><b>Discontinue: <br></b> <sf:input
					class="fancy" id="discontinue" path="discontinue" readonly="true" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>Search: <br></b><input class="fancy"
				id="lookup" autocomplete="off" /></td>
			<td colspan="2"><b>Quantity: <br></b> <sf:input
					class="fancy" id="order_qty" type="number" step=".01"
					path="order_qty" /></td>
		</tr>
		<tr>
			<td><sf:button type="button" class="fancy-button"
					onclick="formSubmit()">
					<b>Add Order</b>
				</sf:button></td>
			<td><sf:button type="button" class="fancy-button"
					onclick="window.location.href='/admin/processorder'">
					<b>Process Order</b>
				</sf:button></td>
			<td><sf:button type="button" class="fancy-button"
					onclick="window.location.href='/admin/cancelsales'">
					<b>Cancel Order</b>
				</sf:button></td>
			<td><sf:button type="button" class="fancy-button"
					onclick="window.location.href='/#tabs-4'">
					<b>Back</b>
				</sf:button></td>
		</tr>
		<tfoot>
			<tr>
				<td colspan="4"><div id="errorMsg" class="bigError"></div></td>
			</tr>
		</tfoot>
	</table>

</sf:form>
<c:if test="${items.size() > 0}">
	<table class="fieldTable tableborder tableshadow rjthird">
		<tr>
			<th colspan="4">Invoice</th>
		</tr>
		<tr>
			<th>SKU#</th>
			<th>Product Name</th>
			<th>Qty</th>
			<th>&nbsp;</th>
		</tr>
		<c:forEach var="item" items="${items}">
			<tr>
				<td>${item.sku}</td>
				<td>${item.product_name}</td>
				<td>${item.qty}</td>
				<td><button type="button" onclick="window.location.href='/admin/editsalesitem?item_id=${item.item_id}'">Edit</button>
					<button type="button" onclick="window.location.href='/admin/deletesalesitem?item_id=${item.item_id}'">Delete</button>
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
<script type="text/javascript">
	$("#lookup").keyup(
			function(name) {
				var name = $("#lookup").val();
				if (name.length > 0) {
					$.getJSON("/rest/lookupname?name=" + name, function(data) {
						$("#sku").val(data.sku);
						$("#upc").val(data.upc);
						$("#product_name").val(data.product_name);
						$("#price").val(data.price);
						$("#unit").val(data.sku);
						$("#category").val(data.upc);
						$("#subcategory").val(data.product_name);
						$("#keywords").val(data.price);
						$("#on_sale").val(data.product_name);
						$("#discontinue").val(data.price);

					})
							.fail(
									function(jqXHR, textStatus, errorThrown) {
										alert("error " + textStatus + "\n"
												+ "incoming Text "
												+ jqXHR.responseText);
									});
				} else {
					clearAll();
				}
			});

	function clearAll() {
		$("#sku").val("");
		$("#upc").val("");
		$("#product_name").val("");
		$("#price").val("0");
		$("#unit").val("");
		$("#category").val("");
		$("#subcategory").val("");
		$("#keywords").val("");
		$("#on_sale").val("false");
		$("#discontinue").val("false");

	}
	function formSubmit() {
		if ($("#sku").val().length > 0) {
			var qty = parseFloat($("#order_qty").val(), 10);
			if (qty == 0.0) {
				$("#errorMsg").text("Order quantity cannot be 0.")
			} else {
				$("#orderProduct").submit();
			}	
		}
	}
</script>