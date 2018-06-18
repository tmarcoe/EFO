<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<table class="tableview tableshadow tableborder">
	<tr>
		<th>SKU</th>
		<th>UPC</th>
		<th>Name</th>
		<th>In Stock</th>
		<th>Min Amount</th>
		<th>Price</th>
		<th>Sold By</th>
		<th>On Sale?</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
	</tr>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td>${item.sku}</td>
			<td>${item.upc}</td>
			<td>${item.product_name}</td>
			<td>${item.inventory.amt_in_stock}</td>
			<td>${item.inventory.min_amount}</td>
			<td>${item.price}</td>
			<td>${item.unit}</td>
			<td>${item.on_sale}</td>
			<td><button type="button"
					onclick="window.location.href='/admin/editproduct?sku=${item.sku}'">Edit</button></td>
			<td><button type="button"
					onclick="deleteProduct('${item.sku}', '${item.product_name}')">Delete</button></td>
			<td><button type="button"
					onclick="window.location.href='/admin/newproductorder?sku=${item.sku}'">order</button></td>
		</tr>
	</c:forEach>
	<tfoot class="tablefooter">
		<tr>
			<td><button type="button"
					onclick="window.location.href='/admin/listproductorders'">Product
					Orders</button>
			<td colspan="9"><button type="button"
					onclick="window.location.href='/admin/newproduct'">New
					Product</button></td>
			<td><button type="button"
					onclick="window.location.href='/#tabs-4'">Back</button></td>
		</tr>
	</tfoot>
</table>
<script type="text/javascript">
	function deleteProduct(sku, name) {
		if (confirm("Are you sure you want to delete " + name + "?")) {
			window.location.href = "/admin/deleteproduct?sku=" + sku;
		}
	}
</script>