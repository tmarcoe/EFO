<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/autocomplete.css" />

<sf:form id="newOrder" method="post" action="/admin/processproductorder" modelAttribute="productOrder">
	<table class="fancy-table tableshadow" style="position: fixed; top: 100px; right: 100px;">
		<tr>
			<td colspan="4"><input class="fancy" id="product_name" size="50" onchange="clearAll()" placeholder="Enter the product name"/> </td>
		</tr>
		<tr>
			<td colspan="2"><b>SKU Code: <br></b> <input class="fancy" id="sku" readonly="true" /></td>
			<td colspan="2"><b>UPC Code: <br></b> <input class="fancy" id="upc" readonly="true" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>Quantity: <br></b> <input class="fancy" id="order_qty" type="number" step="1"
				value="1.0" /></td>
			<td colspan="2"><b>Total Price for this Item: <br></b><input class="fancy" id="price" type="number" step=".01" /></td>
		</tr>
		<tr>
			<td><button class="fancy-button" type="button" onclick="addItem()"><b>Order Item</b></button>
			<td><button class="fancy-button" type="button" onclick="checkSubmit('${productOrder.orderItems.size()}')" ><b>Process Order</b></button>
			<td><button class="fancy-button" type="button" onclick="window.location.href='/admin/listproductorders'" ><b>Back</b></button>
			
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
	<div class="scrollPanel">
		<c:if test="${productOrder.orderItems.size() > 0}">
			<table class="fieldTable tableborder tableshadow rjfirst rjsecond rjfourth">
				<tr>
					<th colspan="6">Product Order</th>
				</tr>
				<tr>
					<th>Amount Ordered</th>
					<th>Amount Received</th>
					<th>Product Name</th>
					<th>Total Item Amount</th>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
				</tr>
				<c:forEach var="item" items="${productOrder.orderItems}">
					<tr>
						<td>${item.amt_ordered}</td>
						<td>${item.amt_received}</td>
						<td>${item.product_name}</td>
						<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.wholesale}" /></td>
						<td>
							<button type="button" onclick="window.location.href='/admin/editorderitem?id=${item.id}'">Edit</button></td>
						<td>
							<button type="button" onclick="window.location.href='/admin/deleteorderitem?id=${item.id}'">Delete</button>
						</td>
					</tr>
					<c:set var="subtotal" value="${subtotal + (item.wholesale)}" />
				</c:forEach>
			</table>
		</c:if>
	</div>
	<sf:hidden id="reference" path="reference" />
	<sf:hidden path="invoice_num"/>
	<sf:hidden path="vendor" />
	<sf:hidden path="user_id" />
	<sf:hidden path="payment_type" value="Cash"/>
	<sf:hidden path="order_date" />
	<sf:hidden path="process_date" />
	<sf:hidden path="delivery_date" />
	<sf:hidden path="total_price" />
	<sf:hidden path="status" value="O"/>
	
	<input id="subttl" type="hidden" value="${subtotal}"/>	
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
				}
			});
	function clearAll() {
		if ($("#product_name").val() == "") {
			$("#product_name").val("");
			$("#sku").val("");
			$("#upc").val("");
			$("#order_qty").val("1.0");
		}
	}
	function addItem() {

		var sku = $("#sku").val();
		var reference = $("#reference").val();
		var order_qty = $("#order_qty").val();
		var price = $("#price").val();
		if (order_qty == 0.0 || price == 0.0) {
			if (order_qty == 0.0 ) {
				$("#errorMsg").text("Order quantity cannot be 0.")
			}else if (price == 0.0) {
				$("#errorMsg").text("Price cannot be 0.")
			}
		} else {
			if (sku.length > 0 && reference > 0) {
				window.location.href = "/admin/addorderitem?reference=" + reference
						+ "&sku=" + sku + "&order_qty=" + order_qty + "&price=" + price;
			}
		}
	}
	
	function checkSubmit(size) {
		if (Number(size) > 0) {
			$("#newOrder").submit();
		}
	}
	
	function totals() {
		var subttl = $("#subttl").val();
		$("#subtotal").text("Total: " + subttl + "    ");
	}

</script>

<c:if test="${productOrder.orderItems.size() > 0}">
	<script>
		totals();
	</script>
</c:if>

