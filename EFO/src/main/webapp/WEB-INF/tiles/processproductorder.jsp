<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/autocomplete.css" />

<sf:form method="post" action="/admin/markasordered" modelAttribute="productOrder">

	<table class="fancy-table tableshadow">
		<tr>
			<td colspan="3"><b>Vendor:</b><br><sf:input id="vendor" class="fancy" path="vendor" size="70"/></td>
		</tr>
		<tr>
			<td><b>Invoice Number:</b><br><sf:input class="fancy" path="invoice_num"/></td>
			<td><b>Process Date:</b><br><sf:input class="fancy" path="process_date" type="date"/></td>
			<td><b>Payment Type</b><br>
				<sf:select class="fancy" id="paymentType" path="payment_type" onchange="showPayable()">
					<sf:option value="">---Select Payment type---</sf:option>
					<sf:option value="Cash">Cash</sf:option>
					<sf:option value="Credit">Vendor Account</sf:option>
				</sf:select></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit" ><b>Order Product</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.location.href='/#tabs-5'"><b>Cancel</b></sf:button></td>
		</tr>
	</table>
	<div id="setPayable" class="modal">
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
					<td><sf:button class="fancy-button" type="button" onclick="closePayable()">
							<b>Save</b>
						</sf:button></td>
					<td><sf:button class="fancy-button" type="button" onclick="payableCancel()">
							<b>Cancel</b>
						</sf:button></td>
				</tr>
			</table>
		</div>
	</div>
	<sf:hidden path="order_date"/>
	<sf:hidden path="reference"/>
	<sf:hidden path="delivery_date"/>
	<sf:hidden path="status"/>
	<sf:hidden path="user_id"/>
	<sf:hidden id="total_due" path="total_price"/>
	<sf:hidden path="payables.reference"/>
	<sf:hidden path="payables.date_begin"/>
	<sf:hidden path="payables.supplier" value="${productOrder.vendor}"/>
	<sf:hidden path="payables.type" value="R"/>
	<sf:hidden path="payables.total_due" value="${productOrder.total_price}"/>
	<sf:hidden path="payables.total_balance" value="${productOrder.total_price}"/>
	<sf:hidden path="payables.status" value="O"/>
</sf:form>
<script type="text/javascript">

function payableCancel() {
	$("#paymentType").val("Cash").change();
	var modal = document.getElementById('setPayable');
	modal.style.display = "none";
}
function closePayable() {
	var modal = document.getElementById('setPayable');
	modal.style.display = "none";
}

function showPayable() {
	if ($("#paymentType option:selected").text() == "Vendor Account") {
		if (Number($("#item_cost").val()) == 0) {
			$("#paymentType").val("Cash").change();
			return;
		}
		var newVal = Number($("#total_due").val());
		$("#popupTotal").text(newVal.toFixed(2));
		$("#hiddenTotalDue").val(newVal);
		var modal = document.getElementById('setPayable');
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