<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/autocomplete.css" />

<sf:form method="post" action="/accounting/updatebudgetitem" modelAttribute="budget">
	<table class="fancy-table tableshadow">
		<tr>
			<td colspan="3"><b>Budget Item: </b><br><sf:input id="category" class="fancy" path="category" readonly="true"  size="40" /></td>
		</tr>
		<tr>
			<td><sf:errors path="category" class="error"/></td>
		</tr>
		<tr>
			<td colspan="3"><b>Amount: </b><br><sf:input id="amount" class="fancy" type="number" step=".01" path="amount"/></td>
		</tr>
		<tr>
			<td><sf:errors path="amount" class="error"/></td>
		</tr>
		<tr>
			<td colspan="3"><b>Justification: </b><br><sf:textarea class="fancy-textarea" path="justification" rows="4" cols="40"/>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit"><b>Save</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()"><b>Cancel</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="sumChildren()"><b>Sum Child Items</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="calcLabor()"><b>Labor Calculator</b></sf:button></td>
		</tr>
	</table>
	<div id="laborCost" class="modal" >
		<div class="modal-content small-modal fancy">
			<h3>Employee Labor Calculator</h3>
			<b>Employee: </b><br><input class="fancy" id="lookupName" /><br>
			<b>Number of Hours: </b><br><input class="fancy" id="hours" type="number" step=".25"/><br>
		
		</div>
	</div>
	<sf:hidden path="id"/>
	<sf:hidden id="user_id" path="user_id"/>
	<sf:hidden path="level"/>
	<sf:hidden path="parent"/>
	<sf:hidden path="department"/>
	<sf:hidden path="ceiling"/>
	<sf:hidden path="creation_date"/>
	<sf:hidden path="protect" value="true"/>
</sf:form>
<script type="text/javascript">

function sumChildren() {
	var usr = $("#user_id").val();
	var cat = $("#category").val();
	
		$.getJSON(
				"/rest/sumchildren?user_id=" + usr + "&parent=" + cat, function(data) {
					if (data.sum > 0.0 ) {
						$("#amount").val(data.sum);
					}else{
						alert("No Children to Sum");
					}
				}).fail(function(jqXHR, textStatus, errorThrown) {
						alert("error " + textStatus + "\n" + "incoming Text "
									   + jqXHR.responseText);
				});
}
function calcLabor() {
	$("#laborCost").css("display", "block");
}
$('#lookupName').devbridgeAutocomplete(
		{
			lookup : function(query, done) {
				var name = $("#lookupName").val();
				$.getJSON("/rest/lookupemployee?name=" + name,
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
				if ($("#hours").val() != "") {
					var hrs = parseFloat($("#hours").val());
					$("#amount").val(hrs * data.data);
					$("#laborCost").css("display", "none");
				}
			}
		});


</script>
