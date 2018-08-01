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
				<sf:select class="fancy" path="payment_type">
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
	<sf:hidden path="order_date"/>
	<sf:hidden path="reference"/>
	<sf:hidden path="delivery_date"/>
	<sf:hidden path="status"/>
	<sf:hidden path="user_id"/>
	<sf:hidden path="total_price"/>
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