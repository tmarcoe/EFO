<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/autocomplete.css" />

<sf:form method="post" action="/admin/addproductorder" modelAttribute="productOrder">
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
			<td><button class="fancy-button" type="button" onclick="window.location.href='/admin/processproductorder'"><b>Process Order</b></button>
		</tr>
	</table>
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
							<button type="button" onclick="window.location.href='/admin/editsalesitem?item_id=${item.id}'">Edit</button></td>
						<td>
							<button type="button" onclick="window.location.href='/admin/deletesalesitem?item_id=${item.id}'">Delete</button>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>
</sf:form>

<script type="text/javascript">
	function receiveCancel() {
		$("#paymentType").val("Cash").change();
		var modal = document.getElementById('setReceivable');
		modal.style.display = "none";
	}
	
	function receiveClose() {
		var modal = document.getElementById('setReceivable');
		modal.style.display = "none";		
	}

	function showReceivable() {
		if ($("#paymentType option:selected").text() == "Credit") {
			var newVal = Number($("#total_due").val());
			$("#popupTotal").text(newVal.toFixed(2));
			$("#hiddenTotalDue").val(newVal);
			var modal = document.getElementById('setReceivable');
			modal.style.display = "block";
			eachPayment();
		}
	}

	function eachPayment() {
		var total_due = $("#total_due").val();
		var down_payment = $("#down_payment").val();
		var interest = $("#interest").val();
		var num_payments = $("#num_payments").val();

		if (total_due > 0 && num_payments > 0) {
			$.getJSON(
					"/rest/calculatepayments?total=" + total_due + "&down="
							+ down_payment + "&interest=" + interest
							+ "&num_payments=" + num_payments, function(data) {
						$("#each_payment").val(data.each_payment);
					}).fail(
					function(jqXHR, textStatus, errorThrown) {
						alert("error " + textStatus + "\n" + "incoming Text "
								+ jqXHR.responseText);
					});
		}
	}

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
		if (order_qty == 0.0) {
			$("#errorMsg").text("Order quantity cannot be 0.")
		} else {
			if (sku.length > 0 && reference > 0) {
				window.location.href = "/admin/addorderitem?reference=" + reference
						+ "&sku=" + sku + "&order_qty=" + order_qty + "&price=" + price;
			}
		}
	}

</script>
