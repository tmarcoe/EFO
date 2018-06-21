<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form method="post" action="/admin/addproductorder" modelAttribute="productOrder">
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>SKU</b></td>
			<td><b>Product Name</b></td>
			<td><b>Invoice #</b></td>
			<td><b>Vendor</b></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="sku" readonly="true"/></td>
			<td><sf:input class="fancy" path="product_name" readonly="true"/>
			<td><sf:input class="fancy" path="invoice_num" /></td>
			<td><sf:input class="fancy" onclick="chooseSupplier()" id="supplierInp" path="vendor" /></td>
		</tr>
		<tr>
			<td><sf:errors path="sku" class="error" /></td>
			<td>&nbsp;</td>
			<td><sf:errors path="invoice_num" class="error" /></td>
			<td><sf:errors onclick="chooseSupplier()" id="supplierInp" path="vendor" class="error" /></td>
		</tr>
		<tr>
			<td><b>Order Price</b></td>
			<td><b>Amount Ordered</b></td>
			<td><b>Payment Type</b></td>
			<td><b>Date Ordered</b></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" type="number" step=".01" path="wholesale" /></td>
			<td><sf:input class="fancy" type="number" step=".01" path="amt_ordered" /></td>
			<td><sf:select class="fancy" path="payment_type">
				<sf:option value="">---Select---</sf:option>
				<sf:option value="Cash">Cash</sf:option>
				<sf:option value="Credit">Credit</sf:option>
			</sf:select></td>
			<td><sf:input class="fancy" type="date" path="order_date" /></td>
			<td><sf:errors path="wholesale" class="error" /></td>
		</tr>
		<tr>
			<td><sf:errors path="amt_ordered" class="error" /></td>
			<td><sf:errors path="payment_type" class="error" /></td>
			<td><sf:errors path="order_date" class="error" /></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit"><b>Save</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()"><b>Cancel</b></sf:button></td>
		</tr>
	</table>
	<div id="getSupplier" class="modal">
		<div class="modal-content small-modal">
			<table style="margin-left: auto; margin-right: auto;">
				<tr><th>Select the Supplier</th></tr>
				<tr>
					<td><select id="selectedSupplier" >
						<c:forEach var="item" items="${suppliers}">
							<option value="${item.vendor.company_name}" >${item.vendor.company_name}</option>
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
	<sf:hidden path="amt_received"/>
	<sf:hidden path="delivery_date" />
	<sf:hidden path="status" value="O"/>
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
		$("#supplierInp").val(record);
		var modal = document.getElementById('getSupplier');
		modal.style.display = "none";
	}
</script>
