<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" href="/css/tables.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="/css/autocomplete.css" />


<sf:form id="details" method="post" action="/admin/updorder" modelAttribute="sales">
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Invoice: </b><br>
				<div class="fancy">
					${sales.invoice_num}
				</div></td>
			<td><b>Total Price: </b><br>
				<div class="fancy">
					<fmt:formatNumber type="currency" currencySymbol="" value="${sales.total_price}" />
				</div></td>
		</tr>
		<tr>
			<td><b>Date Ordered: </b><br>
				<sf:input type="date" class="fancy" path="ordered"/> </td>
			<td><b>Payment Type: </b><br> <sf:select id="paymentType" class="fancy" path="payment_type"
					onchange="showReceivable()">
					<sf:option value="">---Select---</sf:option>
					<sf:option value="Cash">Cash</sf:option>
					<sf:option value="Credit">Credit</sf:option>
				</sf:select></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><sf:errors path="payment_type" class="error" /></td>
		</tr>
		<tr>
			<td><b>Customer: </b><br> <sf:input class="fancy" id="autocomplete" path="customer_name"
					placeholder="Enter Customer Name" /></td>
		</tr>
		<tr>
			<td><sf:errors path="customer_name" class="error"/>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit">
					<b>Process</b>
				</sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.location.href='/#tabs-4'">
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
						<div class="fancy">
							<fmt:formatNumber type="currency" currencySymbol="" value="${sales.receivables.total_due}" />
						</div></td>
					<td><b>Down Payment: </b><br> <sf:input id="down_payment" class="fancy" type="number" step=".01"
							path="receivables.down_payment" onchange="eachPayment()" /></td>
					<td><b>Interest: </b><br> <sf:input id="interest" class="fancy" type="number" step=".01"
							path="receivables.interest" onchange="eachPayment()" />%</td>
				</tr>
				<tr>
					<td><b>Number of Payments: </b><br> <sf:input id="num_payments" class="fancy" type="number" step="1"
							path="receivables.num_payments" onchange="eachPayment()" /></td>
					<td><b>Payment Schedule: </b><br> <sf:select class="fancy" path="receivables.schedule">
							<sf:option value="Annually">Annually</sf:option>
							<sf:option value="Bi-Annually">Bi-Annually</sf:option>
							<sf:option value="Quarterly">Quarterly</sf:option>
							<sf:option value="Monthly">Monthly</sf:option>
							<sf:option value="Bi-Monthly">Bi-Monthly</sf:option>
							<sf:option value="Weekly">Weekly</sf:option>
							<sf:option value="Daily">Daily</sf:option>
						</sf:select></td>
					<td><b>Amount Per Payment: </b><br> <sf:input id="each_payment" class="fancy" type="number" step=".01"
							path="receivables.each_payment" /></td>
				</tr>
				<tr>
					<td><sf:button class="fancy-button" type="button" onclick="receiveClose()">
							<b>OK</b>
						</sf:button></td>
					<td><sf:button class="fancy-button" type="button" onclick="receiveCancel()">
							<b>Cancel</b>
						</sf:button></td>
				</tr>
			</table>
		</div>
	</div>
	<sf:hidden path="reference" />
	<sf:hidden path="invoice_num"/>
	<sf:hidden path="user_id" />
	<sf:hidden id="total_due" path="total_price" />
	<sf:hidden path="processed" />
	<sf:hidden path="shipped" />
	<sf:hidden path="changed" />
	<sf:hidden id="customer_id" path="customer_id" />
	<sf:hidden path="receivables.reference" />
	<sf:hidden path="receivables.invoice_date" />
	<sf:hidden path="receivables.customer" />
	<sf:hidden path="receivables.customer" />
	<sf:hidden path="receivables.status" value="O" />
	<sf:hidden path="receivables.total_due" />
</sf:form>
<script type="text/javascript">
	function showReceivable() {
		if ($("#paymentType option:selected").text() == "Credit") {
			var modal = document.getElementById('setReceivable');
			modal.style.display = "block";
			eachPayment();
		}
	}
	function receiveCancel() {
		var modal = document.getElementById('setReceivable');
		$("#paymentType").val("Cash").change();
		modal.style.display = "none";
	}
	
	function receiveClose() {
		var modal = document.getElementById('setReceivable');
		modal.style.display = "none";
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

	$('#autocomplete').devbridgeAutocomplete(
			{

				lookup : function(query, done) {
					var name = $("#autocomplete").val();
					$.getJSON("/rest/lookupcustomer?name=" + name,
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
					$("#customer_id").val(data.data);
				}
			});
</script>