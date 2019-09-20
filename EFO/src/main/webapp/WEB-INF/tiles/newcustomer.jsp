<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" media="screen" rel="stylesheet" href="/css/multi-select.css" />

<script type="text/javascript" src="/script/jquery.multi-select.js"></script>

<sf:form id="customer" method="post" action="/personnel/addcustomer"
	modelAttribute="user">
	<sf:hidden id="selectedRoles" path="roleString" />
	<table class="fancy-table tableshadow">
		<thead>
			<tr>
				<td><sf:hidden path="user_id" /></td>
				<td><sf:hidden path="customer.user_id" /></td>
				<td><sf:hidden path="common.user_id" /></td>
			</tr>
		</thead>
		<tr>
			<td colspan="4"><b>Customer Name: </b><sf:select class="fancy" path="customer.salutation">
				<sf:option value="Mr.">Mr.</sf:option>
				<sf:option value="Mrs.">Mrs.</sf:option>
				<sf:option value="Ms.">Ms.</sf:option>
				<sf:option value="Miss.">Miss.</sf:option>
				<sf:option value="Dr.">Dr.</sf:option>
			</sf:select>
			<sf:input class="fancy" path="customer.firstname" placeholder="First Name" />
			<sf:input class="fancy" path="customer.lastname" placeholder="Last Name" />
			<sf:select class="fancy" path="customer.maleFemale">
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
			<td><sf:input class="fancy" path="common.address1" placeholder="Address 1" />
			<td><sf:input class="fancy" path="common.address2" placeholder="Address 2" />
			<td><sf:input class="fancy" path="common.city" placeholder="City" /></td>
		</tr>
		<tr>
			<td><sf:errors path="common.address1" class="error" /></td>
			<td><sf:errors path="common.address2" class="error" /></td>
			<td><sf:errors path="common.city" class="error" /></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="common.region" placeholder="Region" /></td>
			<td><sf:input class="fancy" path="common.postalCode" placeholder="Postal Code" /></td>
			<td><sf:input class="fancy" path="common.country" placeholder="Country Code" /></td>
		</tr>
		<tr>
			<td><sf:errors path="common.region" class="error" /></td>
			<td><sf:errors path="common.postalCode" class="error" /></td>
			<td><sf:errors path="common.country" class="error" /></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="username" autocomplete="false" placeholder="Email" /></td>
			<td><sf:password id="password" class="fancy" path="password" autocomplete="false" showPassword="true" placeholder="Password" /></td>
			<td><input class="fancy" id="confirmpass" class="control" name="confirmpass" type="password" placeholder="confirm Password" /></td>
			<td><b>Enabled logins? </b><sf:checkbox id="enabled" class="fancy" path="enabled" onclick="disableInput()"/></td>
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
			<td><sf:input id="since" class="fancy" path="customer.since" type="text" placeholder="Start Date" /></td>
			<td colspan="2"><b>Role(s):</b><br><select multiple class="fancy-roles" id="roles">
					<c:forEach items="${roles}" var="item">
						<option value="${item.id}" >${item.role}</option>
					</c:forEach>
				</select></td>
		</tr>
		<tr>			
			<td><sf:errors path="customer.since" class="error" /></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="button" onclick="formSubmit()"><b>Save</b></sf:button></td>
			<td><button class="fancy-button" type="button" onclick="window.history.back()"><b>Cancel</b></button></td>
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
					$("#password").prop("readonly",true);
					$("#confirmpass").prop("readonly",true);
				}
			});

	function formSubmit() {
		if ($("#password").val() == "" ) {
			$("#password").val("password");
		}
		$('#roles').multiSelect('deselect', '9');
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
		document.getElementById("customer").submit();
	}
	function disableInput() {
		if ($("#enabled").prop('checked') == false) {
			$("#password").prop("readonly",true);
			$("#confirmpass").prop("readonly",true);
		}else{			
			$("#password").prop("readonly",false);
			$("#confirmpass").prop("readonly",false);
		}
	}
	$(function(){
		$("#since").datepicker({
	    	dateFormat: "yy-mm-dd",
	        changeMonth: true,
	        changeYear: true,
	        clickInput: true		
		});
	});
</script>
