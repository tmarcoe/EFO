<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form id="details" method="post" action="/admin/updatevendor" modelAttribute="user">
	<sf:hidden id="selectedRoles" path="roleString" />
	<table class="fancy-table tableshadow">
		<tr>
			<td colspan="6"><b>Company Name</b><br>
				<sf:input class="fancy" path="vendor.company_name" size="50" /></td>
		</tr>
		<tr>
			<td><sf:errors path="vendor.company_name" class="error" /></td>
		</tr>
		<tr>
			<td colspan="6"><b>Contanct: </b><sf:select class="fancy" path="vendor.salutation">
				<sf:option value="Mr.">Mr.</sf:option>
				<sf:option value="Mrs.">Mrs.</sf:option>
				<sf:option value="Ms.">Ms.</sf:option>
				<sf:option value="Miss.">Miss.</sf:option>
				<sf:option value="Dr.">Dr.</sf:option>
			</sf:select>
			<sf:input class="fancy" path="vendor.firstname" />
			<sf:input class="fancy" path="vendor.lastname"/></td>
		</tr>
		<tr>
			<td><sf:errors path="vendor.salutation" class="error" /></td>			
			<td><sf:errors path="vendor.firstname" class="error" /></td>
			<td><sf:errors path="vendor.lastname" class="error" /></td>
		</tr>
		<tr>
			<td><b>Address 1</b><br>
				<sf:input class="fancy" path="common.address1" size="27"/>
			<td><b>Address 2</b><br>
				<sf:input class="fancy" path="common.address2" />
			<td><b>City</b><br>
				<sf:input class="fancy" path="common.city" /></td>
		</tr>
		<tr>
			<td><sf:errors path="common.address1" class="error" /></td>
			<td><sf:errors path="common.address2" class="error" /></td>
			<td><sf:errors path="common.city" class="error" /></td>
		</tr>
		<tr>
			<td><b>Region</b><br>
				<sf:input class="fancy" path="common.region"  size="27"/></td>
			<td><b>Postal Code</b><br>
				<sf:input class="fancy" path="common.postalCode" /></td>
			<td><b>Country Code</b><br>
				<sf:input class="fancy" path="common.country" /></td>
		</tr>
		<tr>
			<td><sf:errors path="common.region" class="error" /></td>
			<td><sf:errors path="common.postalCode" class="error" /></td>
			<td><sf:errors path="common.country" class="error" /></td>
		</tr>
		<tr>
			<td><b>Capital, Revenue? <br>or Overhead</b><br>
				<sf:select class="fancy" path="vendor.type">
					<sf:option value="C">Capital</sf:option>
					<sf:option value="R">Revenue</sf:option>
					<sf:option value="O">Overhead</sf:option>
				</sf:select></td>
			<td><b>Type of Product</b><br>
				<sf:input class="fancy" path="vendor.category" /></td>
		</tr>
		<tr>
			<td><sf:errors path="vendor.type" class="error" /></td>
			<td><sf:errors path="vendor.category" class="error" /></td>
		</tr>
		<tr>	
			<td colspan="3"><b>Keywords: (Separated by commas)</b><br>
				<sf:textarea class="fancy-textarea" path="vendor.keywords" rows="4" cols="80"/></td>
		</tr>
		<tr>
			<td><sf:errors path="vendor.keywords" class="error" /></td>
		</tr>
		<tr>
			<td><b>Email: </b><sf:input class="fancy" path="username"  readonly="true" /></td>
			<td><b>Enabled logins? </b><sf:checkbox class="fancy" path="enabled"/></td>
		</tr>
		<tr>
			<td><b>Role(s):</b><br><sf:select class="fancy-roles" path="roles" id="roles" multiselect="true">
					<sf:options items="${roles}" itemValue="id" itemLabel="role" />
				</sf:select></td>
		</tr>
		<tr>
			<td ><button class="fancy-button" type="button" onclick="formSubmit()"><b>Save</b></button></td>
			<td ><button class="fancy-button" type="button"onclick="window.history.back()" ><b>Cancel</b></button></td>
		</tr>
	</table>
	<sf:hidden path="user_id" />
	<sf:hidden path="common.user_id"/>
	<sf:hidden path="vendor.user_id"/>
	<sf:hidden path="password" />
	<sf:hidden path="temp_pw" />
	
</sf:form>

<script type="text/javascript">
$(document).ready(
		function() {
			var ndx = $("#selectedRoles").val();
			var selectedOptions = ndx.split(";");
			for ( var i in selectedOptions) {
				var optionVal = selectedOptions[i];
				$("#roles").find("option[value=" + optionVal + "]").prop(
						"selected", "selected");
			}
		});

function formSubmit() {

	var opt = document.getElementById("roles");
	var userRoles = "";
	for (var i = 0; i < opt.options.length; i++) {
		if (opt.options[i].selected) {
			if (userRoles == "") {
				userRoles += opt.options[i].value;
			} else {
				userRoles += ";" + opt.options[i].value;
			}
		}
	}
	var rs = document.getElementById("selectedRoles");
	rs.value = userRoles;
	document.getElementById("details").submit();
}
</script>
