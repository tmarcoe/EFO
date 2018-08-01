<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" href="/css/tables.css" rel="stylesheet" />

<table class="tableview tableshadow tableborder spread-out rjfourth rjfifth rjsixth rjseventh rjeighth">
	<tr>
		<th>SKU</th>
		<th>UPC</th>
		<th>Name</th>
		<th>Price</th>
		<th>Unit</th>
		<th>On Sale?</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
	</tr>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td>${item.sku}</td>
			<td>${item.upc}</td>
			<td>${item.product_name}</td>
			<td><fmt:formatNumber type="currency" currencySymbol="" value="${item.price}"/></td>
			<td>${item.unit}</td>
			<td>${item.on_sale}</td>
			<td><button type="button"
					onclick="window.location.href='/admin/editproduct?sku=${item.sku}'">View/Edit</button></td>
			<td><button type="button"
					onclick="deleteProduct('${item.sku}', '${item.product_name}')">Delete</button></td>
		</tr>
	</c:forEach>
	<tfoot class="tablefooter">
		<tr>
			<td><button type="button"
					onclick="window.location.href='/admin/listproductorders'">Product
					Orders</button>
			<td colspan="5"><button type="button"
					onclick="window.location.href='/admin/newproduct'">New
					Product</button></td>
			<td><button type="button"
					onclick="window.location.href='/admin/newproductorder'">Order Products</button></td>
			<td><button type="button"
					onclick="window.location.href='/#tabs-5'">Back</button></td>
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