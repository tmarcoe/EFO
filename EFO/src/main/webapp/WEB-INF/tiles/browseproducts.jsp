<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/autocomplete.css" />

<sf:form id="orderProduct" method="post" action="/admin/retailorder"
	modelAttribute="product" autocomplete="off">
	<table class="fancy-table tableshadow">
		<tr>
			<td colspan="4"><sf:input class="fancy" id="product_name" 
							path="product_name" size="46" 
							placeholder="Enter the Product Name"/></td>
		</tr>
		<tr>
			<td colspan="2"><b>SKU Code: <br></b> <sf:input
					class="fancy" id="sku" path="sku" readonly="true" /></td>
			<td colspan="2"><b>Price: <br></b> <sf:input class="fancy"
					id="price" path="price" readonly="true" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>UPC Code: <br></b> <sf:input
					class="fancy" id="upc" path="upc" readonly="true" /></td>
			<td colspan="2"><b>Units Sold As: <br></b> <sf:input
					class="fancy" id="unit" path="unit" readonly="true" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>Category: <br></b> <sf:input
					class="fancy" id="category" path="category" readonly="true" /></td>
			<td colspan="2"><b>Sub-Category: <br></b> <sf:input
					class="fancy" id="subcategory" path="subcategory" readonly="true" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>Is On Sale: <br></b> <sf:input
					class="fancy" id="on_sale" path="on_sale" readonly="true" /></td>
			<td colspan="2"><b>Keywords: <br></b> <sf:input
					class="fancy" id="keywords" path="keywords" readonly="true" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>Quantity: <br></b> <sf:input
					class="fancy" id="order_qty" type="number" step=".01"
					path="order_qty" value="1.0" /></td>
			<td colspan="2"><b>Discontinue: <br></b> <sf:input
					class="fancy" id="discontinue" path="discontinue" readonly="true" /></td>
		</tr>
		<tr>
			<td><sf:button type="button" class="fancy-button"
					onclick="formSubmit()">
					<b>Add Item</b>
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
			<th colspan="3">Invoice</th>
		</tr>
		<tr>
			<th>Qty</th>
			<th>Product Name</th>
			<th>&nbsp;</th>
		</tr>
		<c:forEach var="item" items="${items}">
			<tr>
				<td>${item.qty}</td>
				<td>${item.product_name}</td>
				<td><button type="button"
						onclick="window.location.href='/admin/editsalesitem?item_id=${item.item_id}'">Edit</button>
					<button type="button"
						onclick="window.location.href='/admin/deletesalesitem?item_id=${item.item_id}'">Delete</button>
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
<script type="text/javascript">
	$('#product_name').devbridgeAutocomplete(
			{

				lookup : function(query, done) {
					var name = $("#product_name").val();
					$.getJSON("/rest/lookupname?name=" + name,
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
					$("#sku").val(data.data.sku);
					$("#upc").val(data.data.upc);
					$("#product_name").val(data.data.product_name);
					$("#price").val(data.data.price);
					$("#unit").val(data.data.unit);
					$("#category").val(data.data.category);
					$("#subcategory").val(data.data.subcategory);
					$("#keywords").val(data.data.keywords);
					$("#on_sale").val(data.data.on_sale);
					$("#discontinue").val(data.data.discontinue);
				}
			});
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