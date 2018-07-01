<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/autocomplete.css" />

<sf:form method="post" action="/accounting/addasset" modelAttribute="asset">
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Invoice Number: </b><br> <sf:input class="fancy" path="invoice_num" /></td>
			<td><b>Item Name: </b><br> <sf:input class="fancy" path="item_name" /></td>
			<td><b>Purchase Date: </b><br> <sf:input type="date" class="fancy" path="date_purchased" /></td>
			<td colspan="2"><b>Supplier: </b><br> <sf:input id="vendor" class="fancy" path="vendor" size="41" /></td>
		</tr>
		<tr>
			<td><sf:errors path="invoice_num" class="error" /></td>
			<td><sf:errors path="item_name" class="error" /></td>
			<td><sf:errors path="date_purchased" class="error" /></td>
			<td><sf:errors path="vendor" class="error" /></td>
		</tr>
		<tr>
			<td><b>Salvage Value: </b><br> <sf:input type="number" step=".01" class="fancy" path="salvage_value" /></td>
			<td><b>Asset Lifetime: </b><br> <sf:input type="number" step="1" class="fancy" path="lifetime" /></td>
			<td><b>Depreciation Method: </b><br> <sf:select class="fancy" path="depreciation_method">
					<sf:option value="Double Declining">Double Declining</sf:option>
					<sf:option value="Straight Method">Straight Method</sf:option>
				</sf:select></td>
			<td><b>Purchase Type: </b><br> <sf:select id="paymentType" class="fancy" path="purchase_type"
					onchange="showReceivable()">
					<sf:option value="">---Select---</sf:option>
					<sf:option value="Cash">Cash</sf:option>
					<sf:option value="Credit">Credit</sf:option>
				</sf:select></td>
			<td><b>Cost: </b><br> <sf:input id="item_cost" type="number" step=".01" class="fancy" path="item_cost" /></td>
		</tr>
		<tr>
			<td><sf:errors path="salvage_value" class="error" /></td>
			<td><sf:errors path="lifetime" class="error" /></td>
			<td><sf:errors path="depreciation_method" class="error" /></td>
			<td><sf:errors path="purchase_type" class="error" /></td>
			<td><sf:errors id="errorCost" path="item_cost" class="error" /></td>
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
					<td><sf:button class="fancy-button" type="submit">
							<b>Process</b>
						</sf:button></td>
					<td><sf:button class="fancy-button" type="button" onclick="receiveCancel()">
							<b>Cancel</b>
						</sf:button></td>
				</tr>
			</table>
		</div>
	</div>
</sf:form>
<script type="text/javascript">
	function receiveCancel() {
		$("#paymentType").val("Cash").change();
		var modal = document.getElementById('setReceivable');
		modal.style.display = "none";
	}

	function showReceivable() {
		if ($("#paymentType option:selected").text() == "Credit") {
			if (Number($("#item_cost").val()) == 0) {
				$("#paymentType").val("Cash").change();
				return;
			}
			var newVal = Number($("#item_cost").val());
			$("#popupTotal").text(newVal.toFixed(2));
			$("#hiddenTotalDue").val(newVal);
			var modal = document.getElementById('setReceivable');
			modal.style.display = "block";
			eachPayment();
		}
	}

	function eachPayment() {
		var total_due = $("#item_cost").val();
		var down_payment = $("#down_payment").val();
		var interest = $("#interest").val();
		var num_payments = $("#num_payments").val();

		if (total_due != 0 && interest != 0 && num_payments) {
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