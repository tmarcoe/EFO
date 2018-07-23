<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/autocomplete.css" />

<sf:form method="post" action="/admin/addproductorder" modelAttribute="productOrder">
	<table class="fancy-table tableshadow">
		<tr>
			<td colspan="3"><b>Product Name: </b><br> <sf:input class="fancy" path="product_name" readonly="true" size="70"/>
		</tr>
		<tr>
			<td colspan="3"><b>Vendor: </b><br> <sf:input class="fancy" id="supplierInp" path="vendor" size = "70"/></td>
		</tr>
		<tr>
			<td><sf:errors path="vendor" class="error" /></td>
		</tr>
		<tr>
			<td><b>Invoice #: </b><br> <sf:input class="fancy" path="invoice_num" /></td>
			<td><b>SKU: </b><br> <sf:input class="fancy" path="sku" readonly="true" /></td>
			<td><b>Payment Type: </b><br>
			<sf:select id="paymentType" class="fancy" path="payment_type" onchange="showReceivable()">
					<sf:option value="">---Select---</sf:option>
					<sf:option value="Cash">Cash</sf:option>
					<sf:option value="Credit">Credit</sf:option>
				</sf:select></td>
		</tr>
		<tr>
			<td><sf:errors path="invoice_num" class="error" /></td>
			<td><sf:errors path="sku" class="error" /></td>
			<td><sf:errors path="payment_type" class="error" /></td>
		</tr>
		<tr>
			<td><b>Order Price: </b><br>
			<sf:input id="total_due" class="fancy" type="number" step=".01" path="wholesale" /></td>
			<td><b>Amount Ordered: </b><br>
			<sf:input class="fancy" type="number" step=".01" path="amt_ordered" /></td>
			<td><b>Date Ordered: </b><br>
			<sf:input class="fancy" type="date" path="order_date" /></td>
		</tr>
		<tr>
			<td><sf:errors path="wholesale" class="error" /></td>
			<td><sf:errors path="amt_ordered" class="error" /></td>
			<td><sf:errors path="order_date" class="error" /></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit">
					<b>Process</b>
				</sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()">
					<b>Cancel</b>
				</sf:button></td>
		</tr>
	</table>
	<div id="setReceivable" class="modal">
		<div class="modal-content medium-modal fancy">
			<h2>Accounts Payable</h2>
			<table style="margin-left: auto; margin-right: auto;">
				<tr>
					<td><b>Amount Due: </b><br>
						<div id="popupTotal" class="fancy"></div></td>
					<td><b>Down Payment: </b><br> <sf:input id="down_payment" class="fancy" type="number" step=".01"
							path="payables.down_payment" onchange="eachPayment()" /></td>
					<td><b>Interest: </b><br> <sf:input id="interest" class="fancy" type="number" step=".01"
							path="payables.interest" onchange="eachPayment()" />%</td>
				</tr>
				<tr>
					<td><b>Number of Payments: </b><br> <sf:input id="num_payments" class="fancy" type="number" step="1"
							path="payables.num_payments" onchange="eachPayment()" /></td>
					<td><b>Payment Schedule: </b><br> <sf:select class="fancy" path="payables.schedule">
							<sf:option value="Annually">Annually</sf:option>
							<sf:option value="Bi-Annually">Bi-Annually</sf:option>
							<sf:option value="Quarterly">Quarterly</sf:option>
							<sf:option value="Monthly">Monthly</sf:option>
							<sf:option value="Bi-Monthly">Bi-Monthly</sf:option>
							<sf:option value="Weekly">Weekly</sf:option>
							<sf:option value="Daily">Daily</sf:option>
						</sf:select></td>
					<td><b>Amount Per Payment: </b><br> <sf:input id="each_payment" class="fancy" type="number" step=".01"
							path="payables.each_payment" /></td>
				</tr>
				<tr>
					<td><sf:button class="fancy-button" type="button" onclick="receiveClose()">
							<b>Save</b>
						</sf:button></td>
					<td><sf:button class="fancy-button" type="button" onclick="receiveCancel()">
							<b>Cancel</b>
						</sf:button></td>
				</tr>
			</table>
		</div>
	</div>
	<sf:hidden path="reference"/>
	<sf:hidden path="amt_received" />
	<sf:hidden path="amt_this_shipment" />
	<sf:hidden path="delivery_date" />
	<sf:hidden path="status" value="O" />
	<sf:hidden id="hiddenTotalDue" path="payables.total_due" />
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

	$('#supplierInp').devbridgeAutocomplete(
			{
				lookup : function(query, done) {
					var name = $("#supplierInp").val();
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
					$("#supplierInp").val(data.data.company_name);
				}
			});
</script>
