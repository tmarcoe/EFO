<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" media="screen" rel="stylesheet" href="/css/multi-select.css" />

<script type="text/javascript" src="/script/jquery.multi-select.js"></script>

<sf:form id="vendor" method="post" action="/personnel/addvendor" modelAttribute="user">
	<sf:hidden id="selectedRoles" path="roleString" />
	<table class="fancy-table tableshadow">
		<thead>
			<tr>
				<td><sf:hidden path="user_id" /></td>
				<td><sf:hidden path="common.user_id" /></td>
				<td><sf:hidden path="vendor.user_id" /></td>
				<td><sf:hidden path="vendor.keywords"/></td>
			</tr>
		</thead>
		<tr>
			<td colspan="6"><b>Company Name: </b><sf:input class="fancy" path="vendor.company_name" size="50"/></td>
		</tr>
		<tr>
			<td><sf:errors path="vendor.company_name" class="error" /></td>
		</tr>
		<tr>
			<td colspan="3"><b>Contanct: </b>
			<sf:select class="fancy" path="vendor.salutation">
					<sf:option value="Mr.">Mr.</sf:option>
					<sf:option value="Mrs.">Mrs.</sf:option>
					<sf:option value="Ms.">Ms.</sf:option>
					<sf:option value="Miss.">Miss.</sf:option>
					<sf:option value="Dr.">Dr.</sf:option>
				</sf:select> 
				<sf:input class="fancy" path="vendor.firstname" placeholder="First Name" /> 
				<sf:input class="fancy" path="vendor.lastname" placeholder="Last Name" /></td>
		</tr>
		<tr>
			<td><sf:errors path="vendor.salutation" class="error" /></td>
			<td><sf:errors path="vendor.firstname" class="error" /></td>
			<td><sf:errors path="vendor.lastname" class="error" /></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="common.address1" placeholder="Address 1"/></td>
			<td><sf:input class="fancy" path="common.address2" placeholder="Address 2"/></td>
			<td><sf:input class="fancy" path="common.city" placeholder="City"/></td>
		</tr>
		<tr>
			<td><sf:errors path="common.address1" class="error" /></td>
			<td><sf:errors path="common.address2" class="error" /></td>
			<td><sf:errors path="common.city" class="error" /></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="common.region" placeholder="Region"/></td>
			<td><sf:input class="fancy" path="common.postalCode" placeholder="Postal Code"/></td>
			<td><sf:input class="fancy" path="common.country" placeholder="Country Code" /></td>
		</tr>
		<tr>
			<td><sf:errors path="common.region" class="error" /></td>
			<td><sf:errors path="common.postalCode" class="error" /></td>
			<td><sf:errors path="common.country" class="error" /></td>
		</tr>
		<tr>
			<td colspan="4"><b>Vendor Type: </b><sf:select class="fancy" path="vendor.type">
					<sf:option value="C">Capital</sf:option>
					<sf:option value="R">Revenue</sf:option>
					<sf:option value="O">Overhead</sf:option>
					<sf:option value="L">Lending Institution</sf:option>
				</sf:select>
				<b>Type of Product: </b>
				<sf:input class="fancy" path="vendor.category" /></td>
		</tr>
		<tr>
			<td><sf:errors path="vendor.type" class="error" /></td>
			<td><sf:errors path="vendor.category" class="error" /></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="username" autocomplete="false" placeholder="Email" /> 
				<sf:errors path="username" class="error" /></td>
			<td><sf:password id="password" class="fancy" path="password" autocomplete="false" showPassword="true" placeholder="Password"/> 
				<sf:errors path="password" class="error" /></td>
				
			<td><input class="fancy" id="confirmpass" class="control" name="confirmpass" type="password" placeholder="Repeat Password" /></td>
			<td><b>Enable Logins? </b> <sf:checkbox id="enabled" path="enabled" onclick="disableInput()" /></td>
		</tr>
		<tr>
			<td><sf:errors path="username" class="error"/></td>
			<td><div id="pbar">
					<label id="pLabel"></label>
					<div id="pStrength"></div>
				</div>&nbsp;</td>
			<td><div id="matchpass"></div>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2"><b>Role(s):</b><br> <sf:select class="fancy-roles" path="roles" id="roles" multiselect="true">
					<sf:options items="${roles}" itemValue="id" itemLabel="role" />
				</sf:select></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="button" onclick="formSubmit()">
					<b>Save</b>
				</sf:button></td>
			<td><button class="fancy-button" type="button" onclick="window.history.back()">
					<b>Cancel</b>
				</button></td>
		</tr>
	</table>
</sf:form>
<script type="text/javascript">
	$(document).ready(
			function() {
				$('#roles').multiSelect({
					selectableHeader: "<div class='custom-header'>Click here to select</div>",
					selectionHeader: "<div class='custom-header'>Click here to deselect</div>"
				});
				var ndx = $("#selectedRoles").val();
				var selectedOptions = ndx.split(";");
				
				$('#roles').multiSelect('select', selectedOptions);
				
				if ($("#enabled").prop('checked') == false) {
					$("#password").prop("readonly", true);
					$("#confirmpass").prop("readonly", true);
				}
			});

	function formSubmit() {

		if ($("#password").val() == "") {
			$("#password").val("password");
		}
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
		document.getElementById("vendor").submit();
	}

	function disableInput() {
		if ($("#enabled").prop('checked') == false) {
			$("#password").prop("readonly", true);
			$("#confirmpass").prop("readonly", true);
		} else {
			$("#password").prop("readonly", false);
			$("#confirmpass").prop("readonly", false);
		}
	}
</script>
