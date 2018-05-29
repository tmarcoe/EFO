<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />


<sf:form id="details" method="post" action="/admin/updatecustomer" modelAttribute="user">
	<sf:hidden id="selectedRoles" path="roleString" />
	<table class="table">
		<tr>
			<td><b>Email: </b><sf:input path="username" readonly="true" /></td>
			<td><b>Enabled: </b><sf:checkbox path="enabled"/></td>
		</tr>
		<tr>
			<td><sf:errors path="username" class="error" />
			<td><sf:errors path="enabled" class="error" />
		</tr>
		<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>M/F</th>
			<th>Customer Since</th>
		</tr>
		<tr>
			<td><sf:input path="customer.firstname" /></td>
			<td><sf:input path="customer.lastname" /></td>
			<td><sf:select path="customer.maleFemale">
					<sf:option value="M">Male</sf:option>
					<sf:option value="F">Female</sf:option>
				</sf:select></td>
			<td><sf:input type="date" path="customer.since"/></td>
		</tr>
		<tr>
			<td><sf:errors path="customer.firstname" class="error" /></td>
			<td><sf:errors path="customer.lastname" class="error" /></td>
			<td><sf:errors path="customer.maleFemale" class="error" /></td>
			<td><sf:errors path="customer.since" class="error"/></td>
		</tr>
		<tr>
			<th>Address 1</th>
			<th>Address 2</th>
			<th>City</th>
			<th>Region</th>
			<th>Postal Code</th>
			<th>Country Code</th>
		</tr>
		<tr>
			<td><sf:input path="common.address1"/>
			<td><sf:input path="common.address2"/>
			<td><sf:input path="common.city"/>
			<td><sf:input path="common.region"/>
			<td><sf:input path="common.postalCode"/>
			<td><sf:input path="common.country"/>
		</tr>
		<tr>
			<td><sf:errors path="common.address1" class="error"/></td>
			<td><sf:errors path="common.address2" class="error"/></td>
			<td><sf:errors path="common.city" class="error"/></td>
			<td><sf:errors path="common.region" class="error"/></td>
			<td><sf:errors path="common.postalCode" class="error"/></td>
			<td><sf:errors path="common.country" class="error"/></td>
		</tr>
		<tr>
			<td><b>Role(s):<br></b><sf:select path="roles" id="roles" multiselect="true">
					<sf:options items="${roles}" itemValue="id" itemLabel="role" />
				</sf:select></td>
		</tr>
		<tr>
			<td ><button type="button" onclick="formSubmit()">Save</button></td>
			<td ><button type="button"onclick="window.history.back()" >Cancel</button></td>
		</tr>
	</table>
	<sf:hidden path="user_id" />
	<sf:hidden path="common.user_id"/>
	<sf:hidden path="customer.user_id"/>
	<sf:hidden path="password"/>
	<sf:hidden path="temp_pw"/>
	
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
