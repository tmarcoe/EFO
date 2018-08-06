<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<table class="tableview tableshadow tableborder spread-out rjthird">
	<tr>
		<th>SKU #</th>
		<th>Product Name</th>
		<th>Price Paid</th>
		<th>Amount Ordered</th>
		<th>Total Received</th>
		<th>This Shipment</th>
		<th>&nbsp;</th>
	</tr>
	<c:forEach var="item" items="${objectList.pageList}">
	<tr>
		<td>${item.sku}</td>
		<td>${item.product_name}</td>
		<td><fmt:formatNumber type="currency"  currencySymbol="" value="${item.wholesale}"/> </td>
		<td>${item.amt_ordered}</td>
		<td>${item.amt_received}</td>
		<td><input id="thisShipment" type="number" step="1" size="4"></td>
		<td><button type="button" onclick="receive('${item.id}', '${item.reference}')">Receive</button></td>
	</tr>
	</c:forEach>
	<tfoot class="tablefooter">
		<tr>
			<td colspan="7"><button type="button" onclick="window.location.href='/admin/listproductorders'">Back</button>
		</tr>
	</tfoot>
</table>
<script type="text/javascript" >
	function receive(id, reference) {
		var qty = $("#thisShipment").val();
		if (parseFloat(qty) > 0.0 ) {
			window.location.href="/admin/updreceiveorder?id=" + id + "&qty=" + qty + "&reference=" + reference;
		}
	}

</script>