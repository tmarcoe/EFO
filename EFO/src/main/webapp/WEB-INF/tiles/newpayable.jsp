<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />

<sf:form method="post" action="/accounting/addpayable" modelAttribute="payables">
	<table class="menuTable">
		<tr>
			<td><b>Invoice Number</b></td>
			<td><b>Begin Payment</b></td>
			<td><b>Supplier</b></td>
		</tr>
		<tr>
			<td><sf:input path="invoice_num" /></td>
			<td><sf:input path="date_begin" type="date"/></td>
			<td><sf:input onclick="chooseSupplier()" id="supplierInp" path="supplier" readonly="true" /></td>
		</tr>
		<tr>
			<td><sf:errors path="invoice_num" class="error" /></td>
			<td><sf:errors path="date_begin" class="error" /></td>
		</tr>
		<tr>
			<td><b>Total Due</b></td>
			<td><b>Pay by</b></td>
			<td><b>Current Balance</b></td>
			<td><b>Type</b></td>
		</tr>
		<tr>
			<td><sf:input path="total_due" type="number" step=".01"/></td>
			<td><sf:input path="date_due" type="date" /></td>
			<td><sf:input path="total_balance" type="number" step=".01"/></td>
			<td><sf:select id="typeInp" path="type">
				<sf:option value="">--select--</sf:option>
				<sf:option value="C">Capital</sf:option>
				<sf:option value="R">Revenue</sf:option>
			</sf:select></td>
		</tr>
		<tr>
			<td><sf:errors path="total_due" class="error" /></td>
			<td><sf:errors path="date_due" class="error" /></td>
			<td><sf:errors path="total_balance" class="error" /></td>
		</tr>
		<tr>
			<td><sf:button type="submit">Save</sf:button>
			<td><sf:button type="button" onclick="window.history.back()">Cancel</sf:button>
		</tr>
	</table>
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
					<td><button type="button" onclick="cancel()" >Cancel</button>
					<td><button type="button" onclick="getSupplier()" >OK</button>
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
</script>

