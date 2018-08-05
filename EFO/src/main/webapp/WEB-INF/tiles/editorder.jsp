<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/autocomplete.css" />

<sf:form method="post" action="/admin/updproductorder" modelAttribute="productOrder">
	<table class="fancy-table tableshadow" style="position: fixed; top: 100px; right: 100px;">
		<tr>
			<td colspan="3"><b>Vendor:</b><br><sf:input id="vendor" class="fancy" path="vendor" size="70"/></td>
		</tr>
		<tr>
			<td><b>Invoice Number: </b><br><sf:input class="fancy" path="invoice_num"/></td>
			<td><b>Payment type:</b><br><sf:input class="fancy" path="payment_type" readonly="true"/></td>
		</tr>
		<tr>
			<td><b>Order Date:</b><br><sf:input class="fancy" type="date" path="order_date" readonly="true"/></td>
			<td><b>Process Date:</b><br><sf:input class="fancy" type="date" path="process_date" readonly="true"/></td>
		</tr>
		<tr>
			<td><b>Total Price:</b><br><sf:input class="fancy" path="total_price" readonly="true"/></td>
			<td><b>Status:</b><br><sf:input class="fancy" path="status" readonly="true"/></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit" ><b>Save</b></sf:button>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()" ><b>Cancel</b></sf:button>
		</tr>
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
				</tr>
				<c:forEach var="item" items="${productOrder.orderItems}">
					<tr>
						<td>${item.amt_ordered}</td>
						<td>${item.amt_received}</td>
						<td>${item.product_name}</td>
						<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.wholesale}" /></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>
	<sf:hidden id="reference" path="reference" />
	<sf:hidden path="user_id" />
	<sf:hidden path="delivery_date" />
	<sf:hidden path="payables.reference"/>
	<sf:hidden path="payables.date_begin"/>
	<sf:hidden path="payables.supplier"/>
	<sf:hidden path="payables.type"/>
	<sf:hidden path="payables.total_due"/>
	<sf:hidden path="payables.down_payment"/>
	<sf:hidden path="payables.interest"/>
	<sf:hidden path="payables.each_payment"/>
	<sf:hidden path="payables.num_payments"/>
	<sf:hidden path="payables.schedule"/>
	<sf:hidden path="payables.total_balance"/>
	<sf:hidden path="payables.status"/>
	
</sf:form>

<script type="text/javascript">
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
