<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/autocomplete.css" />

<sf:form id="orderProduct" method="post" action="/admin/processorder" modelAttribute="sales" autocomplete="off">
	<table class="fancy-table tableshadow" style="position: fixed; top: 100px; right: 100px;padding: .5em .5em .5em .5em;">
		<tr>
			<td colspan="4"><input class="fancy" id="product_name" size="50" placeholder="Enter the Product Name" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>SKU Code: <br></b> <input class="fancy" id="sku" readonly="true" /></td>
			<td colspan="2"><b>Price: </b><br> <input class="fancy" id="price" readonly="true" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>UPC Code: <br></b> <input class="fancy" id="upc" readonly="true" /></td>
			<td colspan="2"><b>Units Sold As: <br></b> <input class="fancy" id="unit" readonly="true" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>Category: <br></b> <input class="fancy" id="category" readonly="true" /></td>
			<td colspan="2"><b>Sub-Category: <br></b> <input class="fancy" id="subcategory" readonly="true" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>Is On Sale: <br></b> <input class="fancy" id="on_sale" readonly="true" /></td>
			<td colspan="2"><b>Keywords: <br></b> <input class="fancy" id="keywords" readonly="true" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>Quantity: <br></b> <input class="fancy" id="order_qty" type="number" step=".01"
				value="1.0" /></td>
			<td colspan="2"><b>Discontinue: <br></b> <input class="fancy" id="discontinue" readonly="true" /></td>
		</tr>
		<tr>
			<td><button type="button" class="fancy-button" onclick="checkStock()">
					<b>Add Item</b>
				</button></td>
			<td><sf:button type="submit" class="fancy-button">
					<b>Process Order</b>
				</sf:button></td>
			<td><sf:button type="button" class="fancy-button" onclick="window.location.href='/admin/cancelsales'">
					<b>Cancel Order</b>
				</sf:button></td>
			<td><sf:button type="button" class="fancy-button" onclick="window.location.href='/#tabs-5'">
					<b>Back</b>
				</sf:button></td>
		</tr>
		<tr>
			<td colspan="2"><button class="fancy-button" type="button" onclick="window.location.href='/admin/listsales'">
					<b>View/Ship Processed Orders</b>
				</button></td>
		</tr>
		<tfoot>
			<tr>
				<td colspan="4"><div id="errorMsg" class="bigError"></div></td>
			</tr>
			<tr>
				<td colspan="4"><div id="subtotal" class="totalsDiv"></div></td>
			</tr>
			<tr>
				<td colspan="4"><div id="totalTax" class="totalsDiv"></div></td>
			</tr>
			<tr>
				<td colspan="4"><div id="grandTotal" class="totalsDiv"></div></td>
			</tr>

		</tfoot>
	</table>
	<div id="errMsg" class="modal">
		<div class="modal-content small-modal fancy-button">
			<h2 class="error" style="font-size: 20px;">Order Quanity Exceeds Stock</h2>
			<br>
			<button class="fancy-button" type="button" onclick="closeError()">OK</button>
		</div>
	</div>

	<sf:hidden id="reference" path="reference" />
	<sf:hidden path="user_id" />
	<sf:hidden path="total_price" />
	<sf:hidden path="ordered" />
	<sf:hidden path="processed" />
	<sf:hidden path="shipped" />
	<sf:hidden path="payment_type" />
	<sf:hidden path="customer_id" />
	<sf:hidden path="customer_name" />
	<sf:hidden path="changed" />
	<sf:hidden path="salesItem" />

	<div class="scrollPanel">
		<c:set var="subtotal" value="0" />
		<c:set var="totalTax" value="0" />
		<c:set var="grandTotal" value="0" />
		<c:if test="${sales.salesItem.size() > 0}">
			<table class="fieldTable tableborder tableshadow rjthird rjfourth rjfifth">
				<tr>
					<th colspan="7">Invoice</th>
				</tr>
				<tr>
					<th>Qty</th>
					<th>Product Name</th>
					<th>Price (each)</th>
					<th>Total</th>
					<th>Tax</th>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
				</tr>
				<c:forEach var="item" items="${sales.salesItem}">
					<tr>
						<td>${item.qty}</td>
						<td>${item.product_name}</td>
						<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.sold_for}" /></td>
						<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.qty * item.sold_for}" /></td>
						<td><fmt:formatNumber type="currency" currencySymbol="" value="${(item.qty * item.sold_for) * .08}" /></td>
						<td>
							<button type="button" onclick="window.location.href='/admin/editsalesitem?item_id=${item.item_id}'">Edit</button>
						</td>
						<td>
							<button type="button" onclick="window.location.href='/admin/deletesalesitem?item_id=${item.item_id}'">Delete</button>
						</td>
					</tr>
					<c:set var="subtotal" value="${subtotal + (item.qty * item.sold_for)}" />
					<c:set var="totalTax" value="${subtotal * .08}" />
					<c:set var="grandTotal" value="${subtotal + totalTax}" />
				</c:forEach>
			</table>
		</c:if>
	</div>
	<input id="subttl" type="hidden" value="${subtotal}"/>
	<input id="ttltax" type="hidden" value="${totalTax}"/>
	<input id="grandttl" type="hidden" value="${grandTotal}"/>
</sf:form>
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

	function addItem() {

		var sku = $("#sku").val();
		var reference = $("#reference").val();
		var order_qty = $("#order_qty").val();
		if (order_qty == 0.0) {
			$("#errorMsg").text("Order quantity cannot be 0.")
		} else {
			if (sku.length > 0 && reference > 0) {
				window.location.href = "/admin/additem?reference=" + reference
						+ "&sku=" + sku + "&order_qty=" + order_qty;
			}
		}
	}

	function checkStock() {
		var sku = $("#sku").val();
		if (sku.length == 0)
			return;
		$.getJSON("/rest/checkstock?sku=" + sku, function(result) {
			var qty = $("#order_qty").val();
			if (Number(qty) > Number(result.amt_in_stock)) {
				$("#errMsg").css("display", "block");
			} else {
				addItem();
			}
		}).fail(
				function(jqXHR, textStatus, errorThrown) {
					alert("error " + textStatus + "\n" + "incoming Text "
							+ jqXHR.responseText);
				});

	}

	function closeError() {
		$("#errMsg").css("display", "none");
		clearAll();
	}
	function clearAll() {
		$("#product_name").val("");
		$("#sku").val("");
		$("#price").val("0.0");
		$("#upc").val("");
		$("#unit").val("");
		$("#category").val("");
		$("#subcategory").val("");
		$("#on_sale").val("false");
		$("#keywords").val("");
		$("#order_qty").val("1.0");
		$("#discontinue").val("false");
	}
	function totals() {
		var subttl = $("#subttl").val();
		var ttltax = $("#ttltax").val();
		var grandttl = $("#grandttl").val();
		$("#subtotal").text("Subtotal: " + subttl + "    ");
		$("#totalTax").text("Tax: " + ttltax + "    ");
		$("#grandTotal").text("Grand Total: " + grandttl + "    ");
	}
</script>
<c:if test="${sales.salesItem.size() > 0}">
	<script>
		totals();
	</script>
</c:if>

