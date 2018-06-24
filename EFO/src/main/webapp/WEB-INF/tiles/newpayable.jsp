<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form method="post" action="/accounting/addpayable" modelAttribute="payables">
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Invoice Number</b></td>
			<td><b>Begin Payment</b></td>
			<td><b>Supplier</b></td>
			<td><b>Capital Or Retail</b></td>
			<td><b>Payment Schedule</b></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="invoice_num" /></td>
			<td><sf:input class="fancy" path="date_begin" type="date"/></td>
			<td><sf:input id="supplierInp" class="fancy" path="supplier" onclick="chooseSupplier()" /></td>
			<td><sf:select class="fancy" id="typeInp" path="type">
				<sf:option value="">--select--</sf:option>
				<sf:option value="C">Capital</sf:option>
				<sf:option value="R">Revenue</sf:option>
			</sf:select></td>
			<td><sf:select class="fancy" path="schedule">
				<sf:option value="Annually">Annually</sf:option>
				<sf:option value="Bi-Annually">Bi-Annually</sf:option>
				<sf:option value="Quarterly">Quarterly</sf:option>
				<sf:option value="Monthly">Monthly</sf:option>
				<sf:option value="Bi-Monthly">Bi-Monthly</sf:option>
				<sf:option value="Weekly">Weekly</sf:option>
				<sf:option value="Daily">Daily</sf:option>
			</sf:select></td>
		</tr>
		<tr>
			<td><sf:errors path="invoice_num" class="error" /></td>
			<td><sf:errors path="date_begin" class="error" /></td>
			<td><sf:errors path="supplier" class="error" /></td>
			<td>&nbsp;</td>
			<td><sf:errors path="schedule" class="error" /></td>
		</tr>
		<tr>
			<td><b>Total Due</b></td>
			<td><b>Down Payment</b></td>
			<td><b>Interest</b></td>
			<td><b>Number of Payments</b></td>
			<td><b>Payment Amount</b></td>
		</tr>
		<tr>
			<td><sf:input id="total_due" class="fancy" path="total_due" type="number" step=".01"/></td>
			<td><sf:input id="down_payment" class="fancy" path="down_payment" type="number" step=".01"/>
			<td><sf:input id="interest" class="fancy" path="interest" type="number" step=".01"/>
			<td><sf:input id="num_payments" class="fancy" path="num_payments" type="number" step="1"/>
			<td><sf:input id="each_payment" class="fancy" path="each_payment" type="number" step=".01" onclick="eachPayment()"/>
		</tr>
		<tr>
			<td><sf:errors path="total_due" class="error" /></td>
			<td><sf:errors path="down_payment" class="error" /></td>
			<td><sf:errors path="interest" class="error" /></td>
			<td><sf:errors path="num_payments" class="error" /></td>
			<td><sf:errors path="each_payment" class="error" /></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit"><b>Save</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()"><b>Cancel</b></sf:button></td>
		</tr>
	</table>
	<sf:hidden path="total_balance" value="0.0"/>
	<div id="getSupplier" class="modal">
		<div class="modal-content small-modal">
			<table style="margin-left: auto; margin-right: auto;">
				<tr><th>Select the Supplier</th></tr>
				<tr>
					<td><select id="selectedSupplier" >
						<c:forEach var="item" items="${suppliers}">
							<option value="${item.company_name},${item.type}" >${item.company_name}</option>
						</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td><button type="button" onclick="getSupplier()" >OK</button>
					<td><button type="button" onclick="cancel()" >Cancel</button>
				</tr>
			</table>
		</div>
	</div>
</sf:form>
<script type="text/javascript">
	function cancel() {
		var modal = document.getElementById('getSupplier');
		modal.style.display = "none";
	}
	function chooseSupplier() {
		var modal = document.getElementById('getSupplier');
		modal.style.display = "block";
	}
	function getSupplier() {
		var record = $("#selectedSupplier option:selected").val();
		var rec = record.split(",");
		$("#supplierInp").val(rec[0]);
		$("#typeInp").val(rec[1]);
		var modal = document.getElementById('getSupplier');
		modal.style.display = "none";
	}
	function eachPayment() {
		var total_due = $("#total_due").val();
		var down_payment = $("#down_payment").val();
		var interest = $("#interest").val();
		var num_payments = $("#num_payments").val();
		
		if (total_due !=0 && down_payment !=0 && interest != 0 && num_payments) {
			$.getJSON("/rest/calculatepayments?total=" + total_due +
					  "&down=" + down_payment +
					  "&interest=" + interest +
					  "&num_payments=" + num_payments, function(data) {
					$("#each_payment").val(data.each_payment);
				}).fail(function(jqXHR, textStatus, errorThrown) {
					alert("error " + textStatus + "\n"
							+ "incoming Text "
							+ jqXHR.responseText);
			});
		}
		
	}
</script>

