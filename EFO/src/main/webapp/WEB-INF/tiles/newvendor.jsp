<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<sf:form id="details" method="post" action="/admin/addvendor"
	modelAttribute="user">
	<sf:hidden id="selectedRoles" path="roleString" />
	<table class="table">
		<thead>
			<tr>
				<td><sf:hidden path="user_id" /></td>
				<td><sf:hidden path="common.user_id" /></td>
				<td><sf:hidden path="vendor.user_id" /></td>
			</tr>
		</thead>
		<tr>
			<td><b>Email</b></td>
			<td><b>Temporary Password</b></td>
			<td><b>Repeat Password</b></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><sf:input path="username" autocomplete="false" />
				<sf:errors path="username" class="error" /></td>
			<td><sf:password path="password" autocomplete="false"
					showPassword="true" />
				<sf:errors path="password" class="error" /></td>
			<td><input id="confirmpass" class="control" name="confirmpass"
						type="password" /></td>
			<td><b>Enable Logins? </b><sf:checkbox path="enabled" /></td>
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
			<td><b>Salutation</b></td>
			<td><b>First Name</b></td>
			<td><b>Last Name</b></td>
			<td><b>Company Name</b></td>
		</tr>
		<tr>
			<td><sf:select path="vendor.salutation">
				<sf:option value="Mr.">Mr.</sf:option>
				<sf:option value="Mrs.">Mrs.</sf:option>
				<sf:option value="Ms.">Ms.</sf:option>
				<sf:option value="Miss.">Miss.</sf:option>
				<sf:option value="Dr.">Dr.</sf:option>
			</sf:select></td>
			<td><sf:input path="vendor.firstname" /></td>
			<td><sf:input path="vendor.lastname"/></td>
			<td><sf:input path="vendor.company_name" /></td>
		</tr>
		<tr>
			<td><sf:errors path="vendor.salutation" class="error" /></td>			
			<td><sf:errors path="vendor.firstname" class="error" /></td>
			<td><sf:errors path="vendor.lastname" class="error" /></td>
			<td><sf:errors path="vendor.company_name" class="error" /></td>
		</tr>
		<tr>
			<td><b>Address 1</b></td>
			<td><b>Address 2</b></td>
			<td><b>City</b></td>
			<td><b>Region</b></td>
			<td><b>Postal Code</b></td>
			<td><b>Country Code</b></td>
		</tr>
		<tr>
			<td><sf:input path="common.address1" />
			<td><sf:input path="common.address2" />
			<td><sf:input path="common.city" /></td>
			<td><sf:input path="common.region" /></td>
			<td><sf:input path="common.postalCode" /></td>
			<td><sf:input path="common.country" /></td>
		</tr>
		<tr>
			<td><sf:errors path="common.address1" class="error" /></td>
			<td><sf:errors path="common.address2" class="error" /></td>
			<td><sf:errors path="common.city" class="error" /></td>
			<td><sf:errors path="common.region" class="error" /></td>
			<td><sf:errors path="common.postalCode" class="error" /></td>
			<td><sf:errors path="common.country" class="error" /></td>
		</tr>
		<tr>
			<td><b>Capital or Revenue?</b></td>
			<td><b>Type of Product</b></td>
			<td colspan="2"><b>Keywords: (Separated by commas)</b></td>
		</tr>
		<tr>
			<td><sf:select path="vendor.type">
				<sf:option value="C">Capital</sf:option>
				<sf:option value="R">Revenue</sf:option>
			</sf:select></td>
			<td><sf:input path="vendor.category" /></td>
			<td colspan="2"><sf:textarea path="vendor.keywords" rows="4" cols="58"/></td>
		</tr>
		<tr>
			<td><sf:errors path="vendor.type" class="error" /></td>
			<td><sf:errors path="vendor.category" class="error" /></td>
			<td><sf:errors path="vendor.keywords" /></td>
		</tr>
		<tr>
			<td><b>Role(s):</b><br><sf:select path="roles" id="roles" multiselect="true">
					<sf:options items="${roles}" itemValue="id" itemLabel="role" />
				</sf:select></td>
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
