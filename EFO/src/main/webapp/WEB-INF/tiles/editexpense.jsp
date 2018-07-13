<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" rel="stylesheet" href="/css/autocomplete.css" />

<sf:form id="details" method="post" action="/admin/updateexpense" modelAttribute="expense">
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Vendor: </b><br>
				<sf:input id="supplierInp" class="fancy" path="vendor" size="50"/></td>
		</tr>
		<tr>
			<td><sf:errors path="vendor" class="error" /></td>
		</tr>
		<tr>
			<td><b>Accounte #: </b><br>
				<sf:input class="fancy" path="account"/></td>
			<td><b>Type of Expense: </b><br>
				<sf:input class="fancy" path="reason"/></td>
		</tr>
		<tr>
			<td><sf:errors path="account" class="error" /></td>
			<td><sf:errors path="reason" class="error" /></td>
		</tr>
		<tr>
			<td><b>Payment Schedule: </b><br>
				<sf:select class="fancy" path="schedule">
							<sf:option value="Annually">Annually</sf:option>
							<sf:option value="Bi-Annually">Bi-Annually</sf:option>
							<sf:option value="Quarterly">Quarterly</sf:option>
							<sf:option value="Monthly">Monthly</sf:option>
							<sf:option value="Bi-Monthly">Bi-Monthly</sf:option>
							<sf:option value="Weekly">Weekly</sf:option>
							<sf:option value="Daily">Daily</sf:option>
				</sf:select></td>
			<td><b>Begining Date: </b><br>
				<sf:input class="fancy" path="begin_date" type="date" /></td>
		</tr>
		<tr>
			<td><sf:errors path="schedule" class="error" /></td>
			<td><sf:errors path="begin_date" class="error" /></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="submit" ><b>Save</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()"><b>Cancel</b></sf:button></td>
		</tr>
	</table>
	<sf:hidden path="reference"/>
</sf:form>
<script type="text/javascript">

$('#supplierInp').devbridgeAutocomplete(
		{
			lookup : function(query, done) {
				var name = $("#supplierInp").val();
				$.getJSON("/rest/lookupvendor?name=" + name + "&type=O",
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
				$("#supplierInp").val(data.data.company_name);
			}
		});

</script>