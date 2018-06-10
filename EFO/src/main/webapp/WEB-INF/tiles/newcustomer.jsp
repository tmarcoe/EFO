<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<sf:form id="details" method="post" action="/admin/addcustomer"
	modelAttribute="user">
	<sf:hidden id="selectedRoles" path="roleString" />
	<table class="table">
		<thead>
			<tr>
				<td><sf:hidden path="user_id" /></td>
				<td><sf:hidden path="customer.user_id" /></td>
				<td><sf:hidden path="common.user_id" /></td>
			</tr>
		</thead>
		<tr>
			<td><b>Email</b></td>
			<td><b>Temporary Password</b></td>
			<td><b>Comfirm Password</b></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><sf:input path="username" autocomplete="false" /></td>
			<td><sf:password path="password" autocomplete="false"
					showPassword="true" /></td>
			<td><input id="confirmpass" class="control" name="confirmpass"
				type="password" /></td>
			<td><b>Enabled logins? </b><sf:checkbox path="enabled" /></td>
		</tr>
		<tr>
			<td><sf:errors path="username" class="error" /></td>
			<td><sf:errors path="password" class="error" /></td>
			<td>&nbsp;</td>
			<td><sf:errors path="enabled" class="error" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><div id="pbar">
					<label id="pLabel"></label>
					<div id="pStrength"></div>
				</div>&nbsp;</td>
			<td><div id="matchpass"></div>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><b>&nbsp;</b></td>
			<td><b>First Name</b></td>
			<td><b>Last Name</b></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><sf:select path="customer.salutation">
				<sf:option value="Mr.">Mr.</sf:option>
				<sf:option value="Mrs.">Mrs.</sf:option>
				<sf:option value="Ms.">Ms.</sf:option>
				<sf:option value="Miss.">Miss.</sf:option>
				<sf:option value="Dr.">Dr.</sf:option>
			</sf:select></td>
			<td><sf:input path="customer.firstname" /></td>
			<td><sf:input path="customer.lastname" /></td>
			<td><sf:select path="customer.maleFemale">
					<sf:option value="M">Male</sf:option>
					<sf:option value="F">Female</sf:option>
				</sf:select></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><sf:errors path="customer.firstname" class="error" /></td>
			<td><sf:errors path="customer.lastname" class="error" /></td>
			<td><sf:errors path="customer.maleFemale" class="error" /></td>
		</tr>
		<tr>
			<td><b>City</b></td>
			<td><b>Region</b></td>
			<td><b>Postal Code</b></td>
			<td><b>Country Code</b></td>
			<td><b>Address 1</b></td>
			<td><b>Address 2</b></td>
		</tr>
		<tr>
			<td><sf:input path="common.city" /></td>
			<td><sf:input path="common.region" /></td>
			<td><sf:input path="common.postalCode" /></td>
			<td><sf:input path="common.country" /></td>
			<td><sf:input path="common.address1" />
			<td><sf:input path="common.address2" />
		</tr>
		<tr>
			<td><sf:errors path="common.city" class="error" /></td>
			<td><sf:errors path="common.region" class="error" /></td>
			<td><sf:errors path="common.postalCode" class="error" /></td>
			<td><sf:errors path="common.country" class="error" /></td>
			<td><sf:errors path="common.address1" class="error" /></td>
			<td><sf:errors path="common.address2" class="error" /></td>
		</tr>
		<tr>
			<td><b>Start Date:</b><br><sf:input path="customer.since" type="date" /></td>
			<td><b>Role(s):</b><br><sf:select path="roles" id="roles" multiselect="true">
					<sf:options items="${roles}" itemValue="id" itemLabel="role" />
				</sf:select></td>
		</tr>
		<tr>			
			<td><sf:errors path="customer.since" class="error" /></td>
		</tr>
		<tr>
			<td><sf:button type="button" onclick="formSubmit()">Save</sf:button></td>
			<td><button type="button" onclick="window.history.back()">Cancel</button></td>
		</tr>
	</table>
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
