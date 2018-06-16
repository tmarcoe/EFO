<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<sf:form id="details" method="post" action="/admin/orderproduct" modelAttribute="item">
	<table class="table">
		<tr>
			<td><b>SKU #</b></td>
			<td><b>UPC #</b></td>
			<td><b>Name</b></td>
			<td><b>Price</b></td>
		</tr>
		<tr>
			<td>${product.sku}</td>
			<td>${product.upc}</td>
			<td>${product.product_name}</td>
			<td><b><fmt:formatNumber type="currency" currencySymbol="" value="${product.price}"/></b></td>
			<td><sf:input type="number" path="qty"/>
		</tr>
	</table>
	
</sf:form>
