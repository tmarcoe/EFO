<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/autocomplete.css" />

<sf:form id="investment" method="post" action="/basic/addequity" modelAttribute="transaction">
	<table class="fancy-table tableshadow">
		<tr>
			<td colspan="2"><sf:input id="autocomplete" class="fancy" path="payment_name" size="55" placeholder="Enter Investor Name" /></td>
		</tr>
		<tr>
			<td><sf:errors path="payment_name" class="error" />
		</tr>
		<tr>
			<td><b>Amount of Investment:</b><br> <sf:input class="fancy" id="total_due" path="amount" type="number"
					step=".01" value="0" onchange="eachPayment()" /></td>
			<td><b>Profile Name:</b><br> <sf:select class="fancy" path="name">
					<sf:option value="">---Select---</sf:option>
					<sf:options items="${namesList}" />
				</sf:select></td>
		</tr>
		<tr>
			<td><sf:errors path="amount" class="error" /></td>
			<td><sf:errors path="name" class="error" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>Transaction Description:</b><br> <sf:textarea class="fancy-textarea" path="descr"
					rows="5" cols="50" /></td>
		</tr>
		<tr>
			<td><sf:errors path="descr" class="error" /></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit">
					<b>Save</b>
				</sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()">
					<b>Back</b>
				</sf:button>
		</tr>
		<tr>
			<td><sf:hidden path="down" value="0" /></td>
			<td><sf:hidden path="interest" value="0" /></td>
			<td><sf:hidden path="num_payments" value="0" /></td>
			<td><sf:hidden path="each_payment" value="0" /></td>
			<td><sf:hidden path="schedule"  value="" /></td>
			<td><sf:hidden path="start" /></td>
			<td><sf:hidden path="reference" /></td>
			<td><sf:hidden path="timestamp" /></td>
			<td><sf:hidden path="payment_ref" /></td>
			<td><sf:hidden id="user_id" path="user_id"/></td>
			<td><input type="hidden" id="total_tax" value="0" /></td>
		</tr>
	</table>
</sf:form>
<script type="text/javascript">
	function eachPayment() {
		var total_due = $("#total_due").val();
		var total_tax = $("#total_tax").val();
		var down_payment = $("#down_payment").val();
		var interest = $("#interest").val();
		var num_payments = $("#num_payments").val();

		if (total_due > 0 && num_payments > 0) {
			var ttl = parseFloat(total_due) + parseFloat(total_tax);
			$.getJSON(
					"/rest/calculatepayments?total=" + ttl + "&down="
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
	$('#autocomplete').devbridgeAutocomplete(
			{
				lookup : function(query, done) {
					var name = $("#autocomplete").val();
					$.getJSON("/rest/lookupinvestor?name=" + name,
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
					$("#user_id").val(data.data.user_id);
				}
			});
</script>
