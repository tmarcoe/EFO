<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/autocomplete.css" />
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />

<sf:form id="capitalExpense" method="post" action="/basic/addcapitalexpense" modelAttribute="transaction">
	<table class="fancy-table tableshadow">
		<tr>
			<td colspan="2"><sf:input id="autocomplete" class="fancy" path="payment_name" size="55"
					placeholder="Enter Vendor Name" /></td>
		</tr>
		<tr>
			<td><sf:errors path="payment_name" class="error" />
		</tr>
		<tr>
			<td><b>Amount of Sale:</b><br> <sf:input class="fancy" id="total_due" path="amount" type="number"
					step=".01" value="0" onchange="eachPayment()" /></td>
			<td><b>Profile Name:</b><br> <sf:select id="profileName" class="fancy" path="name">
					<sf:option value="">---Select---</sf:option>
					<sf:options items="${namesList}" />
				</sf:select></td>
		</tr>
		<tr>
			<td><sf:errors path="amount" class="error" /></td>
			<td><sf:errors path="name" class="error" /></td>
		</tr>
		<tr>
			<td><b>Start of Payments:</b><br> <sf:input id="stDate" class="fancy" path="start" type="text" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>Transaction Description:</b><br> <sf:textarea class="fancy-textarea" path="descr"
					rows="5" cols="50" /></td>
		</tr>
		<tr>
			<td><sf:errors path="descr" class="error" /></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="button" onclick="checkTerms()">
					<b>Submit</b>
				</sf:button>
				<sf:button class="fancy-button" type="button" onclick="$('#showTerms').show()">
					<b>Show Terms</b>
				</sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()">
					<b>Cancel</b>
				</sf:button>
		</tr>
		<tr>
			<td><sf:hidden path="reference" /></td>
			<td><sf:hidden path="timestamp" /></td>
			<td><sf:hidden path="payment_ref" /></td>
			<td><sf:hidden id="user_id" path="user_id" /></td>
			<td><input type="hidden" id="total_tax" value="0" /></td>
		</tr>
	</table>
	<div id="showTerms" class="modal">
		<div class="modal-content small-modal fancy">
			<table style="margin-left: auto; margin-right: auto;">
				<tr>
					<td><b>Down Payment:</b><br> <sf:input class="fancy" path="down" id="down_payment" type="number"
							step=".01" value="0" onchange="eachPayment()" /></td>
					<td><b>Interest:</b><br> <sf:input class="fancy" path="interest" id="interest" type="number" step=".01"
							value="0" onchange="eachPayment()" /></td>
				</tr>
				<tr>
					<td><b>Number of Payments:</b><br> <sf:input class="fancy" id="num_payments" path="num_payments"
							type="number" step="1" value="0" onchange="eachPayment()" /></td>
					<td><b>Each Payment:</b><br> <sf:input class="fancy" id="each_payment" path="each_payment" type="number"
							step=".01" value="0" /></td>
				</tr>
				<tr>
					<td><b>Schedule:</b><br> <sf:select path="schedule" id="sched" class="fancy">
							<sf:option value="">---Select---</sf:option>
							<sf:option value="Annually">Annually</sf:option>
							<sf:option value="Bi-Annually">Bi-Annually</sf:option>
							<sf:option value="Quarterly">Quarterly</sf:option>
							<sf:option value="Monthly">Monthly</sf:option>
							<sf:option value="Bi-Monthly">Bi-Monthly</sf:option>
							<sf:option value="Weekly">Weekly</sf:option>
							<sf:option value="Daily">Daily</sf:option>
						</sf:select></td>
				</tr>
				<tr>
					<td><sf:button class="fancy-button" type="button" onclick="$('#showTerms').hide()" ><b>Save</b></sf:button>
				</tr>
			</table>
		</div>
	</div>
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
					$.getJSON("/rest/lookupvendor?name=" + name + "&type=C",
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
	function checkTerms() {
		var profileName = $('#profileName option:selected').val();
		if (profileName != '') {
		$.getJSON("/rest/showterms?profilename=" + profileName,
				function(result) {
					if (result.showCreditTerms == true && ($('#num_payments').val() == 0 || $('#sched option:selected').val() === "" )) {
						$('#showTerms').show();
					}else{
						$('#capitalExpense').submit();
					}
			
				}).fail(
						function(jqXHR, textStatus, errorThrown) {
							alert("error " + textStatus + "\n"
									+ "incoming Text "
									+ jqXHR.responseText);
						});	
				}

			}
	  $( function() {
		    $( "#stDate" ).datepicker({
		    	dateFormat: "yy-mm-dd",
		        changeMonth: true,
		        changeYear: true,
		        clickInput: true
		    	});
		  } );

</script>
