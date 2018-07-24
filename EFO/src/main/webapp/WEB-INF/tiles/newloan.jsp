<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/autocomplete.css" />

<sf:form method="post" action="/accounting/addloan" modelAttribute="loan">
	<table class="fancy-table tableshadow">
		<tr>
			<td colspan="2"><b>Lending Institution: </b><br>
			<sf:input id="instName" class="fancy" path="institution_name" size="47" /></td>
			<td><b>Approval Date: </b><br>
			<sf:input type="date" class="fancy" path="approval" /></td>
			<td><b>Reason for Loan: </b><br>
			<sf:input class="fancy" path="reason" /></td>
		</tr>
		<tr>
			<td colspan="2"><sf:errors path="institution_name" class="error" /></td>
			<td><sf:errors path="approval" class="error" /></td>
		</tr>
		<tr>
			<td><b>Loan Amount: </b><br>
			<sf:input id="total_due" type="number" step=".01" class="fancy" path="loan_amount" onchange="eachPayment()" /></td>
			<td><b>Interest: </b><br>
			<sf:input id="interest" type="number" step=".01" class="fancy" path="interest" onchange="eachPayment()" /></td>
			<td><b>down_payment: </b><br>
			<sf:input id="down_payment" type="number" step=".01" class="fancy" path="down_payment" onchange="eachPayment()" /></td>
			<td><b>Number of Payments: </b><br>
			<sf:input id="num_payments" type="number" step="1" class="fancy" path="num_payments" onchange="eachPayment()" /></td>
		</tr>
		<tr>
			<td><sf:errors path="loan_amount" class="error" /></td>
			<td><sf:errors path="interest" class="error" /></td>
			<td><sf:errors path="down_payment" class="error" /></td>
			<td><sf:errors path="num_payments" class="error" /></td>
		</tr>
		<tr>
			<td><b>Payment Schedule: </b><br> <sf:select class="fancy" path="schedule">
					<sf:option value="Annually">Annually</sf:option>
					<sf:option value="Bi-Annually">Bi-Annually</sf:option>
					<sf:option value="Quarterly">Quarterly</sf:option>
					<sf:option value="Monthly">Monthly</sf:option>
					<sf:option value="Bi-Monthly">Bi-Monthly</sf:option>
					<sf:option value="Weekly">Weekly</sf:option>
					<sf:option value="Daily">Daily</sf:option>
				</sf:select></td>
			<td><b>Payment Amount: </b><br>
			<sf:input id="each_payment" type="number" step=".01" class="fancy" path="each_payment" readonly="true" /></td>
		</tr>
		<tr>
			<td><sf:errors path="schedule" class="error" /></td>
			<td><sf:errors path="each_payment" class="error" /></td>
		</tr>
		<tr>
			<td><sf:button type="submit" class="fancy-button">
					<b>Save</b>
				</sf:button>
			<td><sf:button type="button" onclick="window.location.href='/accounting/listloans'" class="fancy-button">
					<b>Back</b>
				</sf:button>
		</tr>
	</table>
	<sf:hidden path="trans_id" />
	<sf:hidden id="user_id" path="user_id" />
	<sf:hidden path="status" value="O" />
</sf:form>
<script type="text/javascript">
	$('#instName').devbridgeAutocomplete(
			{
				lookup : function(query, done) {
					var name = $("#instName").val();
					$.getJSON("/rest/lookupvendor?name=" + name + "&type=L",
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
					$("#instName").val(data.data.company_name);
					$("#user_id").val(data.data.user_id);
				}
			});

	function eachPayment() {
		var total_due = $("#total_due").val();
		var down_payment = $("#down_payment").val();
		var interest = $("#interest").val();
		var num_payments = $("#num_payments").val();

		if (total_due > 0 && num_payments > 0) {
			$.getJSON(
					"/rest/calculatepayments?total=" + total_due + "&down="
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
</script>