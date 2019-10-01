<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" media="screen" rel="stylesheet" href="/css/multi-select.css" />

<script type="text/javascript" src="/script/jquery.multi-select.js"></script>

<sf:form id="employee" method="post" action="/personnel/addemployee" modelAttribute="user">
<sf:hidden id="selectedRoles" path="roleString"/>
	<table class="fancy-table tableshadow">
		<tr>
			<td colspan="4"><sf:select class="fancy" path="employee.salutation">
				<sf:option value="Mr.">Mr.</sf:option>
				<sf:option value="Mrs.">Mrs.</sf:option>
				<sf:option value="Ms.">Ms.</sf:option>
				<sf:option value="Miss.">Miss.</sf:option>
				<sf:option value="Dr.">Dr.</sf:option>
			</sf:select>
			<sf:input class="fancy" path="employee.firstname" placeholder="First Name" />
			<sf:input class="fancy" path="employee.lastname" placeholder="Last Name" />
			<sf:select class="fancy" path="employee.maleFemale">
					<sf:option value="M">Male</sf:option>
					<sf:option value="F">Female</sf:option>
				</sf:select></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><sf:errors path="employee.firstname" class="error" /></td>
			<td><sf:errors path="employee.lastname" class="error" /></td>
			<td><sf:errors path="employee.maleFemale" class="error" /></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="employee.position" placeholder="Position" /></td>
			<td><sf:input id="stDate" class="fancy" type="text" path="employee.start_date" placeholder="Starting Date"/></td>
			<td><sf:input id="endDate" class="fancy" type="text" path="employee.end_date" placeholder="Ending Date"/></td>
			<td><sf:input class="fancy" path="employee.company" placeholder="company"/></td>
		</tr>
		<tr>
			<td><sf:errors path="employee.position" class="error"/></td>
			<td><sf:errors path="employee.start_date" class="error" /></td>
			<td><sf:errors path="employee.end_date" class="error" /></td>
			<td><sf:errors path="employee.company" class="error"/>
		</tr>
		<tr>
			<td><sf:select class="fancy" path="employee.division">
					<sf:option value="" >---Select Department---</sf:option>
					<c:forEach items="${departments}" var="item">
						<sf:option value="${item}">${item}</sf:option>
					</c:forEach>
				</sf:select></td>
			<td><sf:input class="fancy" path="employee.supervisor" placeholder="Supervisor" /></td>
			<td><sf:input class="fancy" path="employee.extension" placeholder="Direct Line" /></td>
			<td><sf:input class="fancy" path="employee.office_loc" placeholder="Office Location" /></td>
		</tr>
		<tr>
			<td><sf:errors path="employee.division" class="error"/>
			<td><sf:errors path="employee.supervisor" class="error"/>
			<td><sf:errors path="employee.extension" class="error"/>
			<td><sf:errors path="employee.office_loc" class="error"/>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="employee.home_phone" placeholder="Home Phone" /></td>
			<td><sf:input class="fancy" path="common.address1" placeholder="Address 1" /></td>
			<td><sf:input class="fancy" path="common.address2" placeholder="Address 2" /></td>
			<td><sf:input class="fancy" path="common.city" placeholder="City" /></td>
		</tr>
		<tr>
			<td><sf:errors path="employee.home_phone" class="error"/></td>
			<td><sf:errors path="common.address1" class="error"/></td>
			<td><sf:errors path="common.address2" class="error"/></td>
			<td><sf:errors path="common.city" class="error"/></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="common.region" placeholder="Region" /></td>
			<td><sf:input class="fancy" path="common.postalCode" placeholder="Postal Code" /></td>
			<td><sf:input class="fancy" path="common.country" placeholder="Country Code" /></td>
			<td><sf:input class="fancy" path="employee.cell_phone" placeholder="Cell Phone" /></td>
		</tr>
		<tr>
			<td><sf:errors path="common.region" class="error"/></td>
			<td><sf:errors path="common.postalCode" class="error"/></td>
			<td><sf:errors path="common.country" class="error"/></td>
			<td><sf:errors path="employee.cell_phone" class="error"/></td>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="employee.emer_contact" placeholder="Emergency Contact" /></td>
			<td><sf:input class="fancy" path="employee.emer_ph" placeholder="Contact Phone" /></td>
			<td><sf:input class="fancy" path="username" autocomplete="false" placeholder="Email" /></td>
			<td><sf:password id="password" class="fancy" path="password" autocomplete="false" showPassword="true" placeholder="Temporary Password"/></td>
			<td><input class="fancy" id="confirmpass" class="control" name="confirmpass" type="password" placeholder="Repeat Password" /></td>
			<td><b>Enable logins? </b><sf:checkbox id="enabled" path="enabled" onclick="disableInput()"/></td>
		</tr>
		<tr>
			<td><sf:errors path="employee.emer_contact" class="error"/></td>
			<td><sf:errors path="employee.emer_ph" class="error"/></td>
			<td>&nbsp;<sf:errors path="username" class="error" /></td>
			<td><div id="pbar">
					<label id="pLabel"></label>
					<div id="pStrength"></div>
				</div>&nbsp;<sf:errors path="password" class="error" /></td>
			<td><div id="matchpass"></div>&nbsp;</td>
			<td>&nbsp;<sf:errors path="enabled" class="error" /></td>
		</tr>
		<tr>
			<td><b>Do Not Rehire:</b> <sf:checkbox class="fancy" path="employee.dnr" /></td>
			<td colspan="2"><b>Employment Type:</b>
				<sf:select class="fancy" path="employee.emp_type">
					<sf:option value="F">Full Time</sf:option>
					<sf:option value="S">Salary (non-exempt)</sf:option>
					<sf:option value="E">Salary (exempt)</sf:option>
					<sf:option value="P">Part Time</sf:option>
					<sf:option value="C">Contract</sf:option>
					<sf:option value="T">Temporary</sf:option>
				</sf:select></td>
		</tr>
		<tr>
			<td colspan="2"><b>Role(s):</b><select multiple class="fancy-roles" id="roles" >
					<c:forEach items="${roles}" var="item">
						<option value="${item.id}" >${item.role}</option>
					</c:forEach>
				</select></td>
			
		</tr>
		<tr>
			<td><button class="fancy-button" type="button" onclick="formSubmit()" ><b>Save</b></button></td>
			<td><button class="fancy-button" type="button"  onclick="openPopup()"><b>Financial Information</b></button></td>
			<td><button class="fancy-button" type="button" onclick="window.history.back()" ><b>Cancel</b></button></td>
		</tr>
	</table>
		<div class="modal" id="empFinancial">
		<div class="modal-content medium-modal fancy">
			<table style="margin-left: auto; margin-right: auto;">
				<tr>
					<td><b>SSN</b><br><sf:input class="fancy" type="text" path="employee.emp_financial.ssn" /></td>
					<td><b>Marital Status</b><br><sf:select  class="fancy" path="employee.emp_financial.status">
						<sf:option value="S">Single</sf:option>
						<sf:option value="M">Married</sf:option>
						<sf:option value="MH">Married Higher Rate</sf:option>
					</sf:select></td>
					<td><b>Exemptions</b><br><sf:input class="fancy" type="number" step="1" min="0" path="employee.emp_financial.exemptions" /></td>
				</tr>
				<tr>
					<td><b>Employer Number (EIN)</b><br><sf:input class="fancy" type="text" path="employee.emp_financial.ein"/></td>
					<td><b>Hourly Rate/Salary</b><br><sf:input class="fancy" type="number" step=".01" path="employee.emp_financial.pay_rate" /></td>
					<td><b>Medical</b><br><sf:input class="fancy" type="number" step=".01" path="employee.emp_financial.medical" /></td>
				</tr>
				<tr>
					<td><b>Retirement</b><br><sf:input class="fancy" type="number" step=".01" path="employee.emp_financial.retirement" /></td>
					<td><b>Union Dues</b><br><sf:input class="fancy" type="number" step=".01" path="employee.emp_financial.union_dues" /></td>
					<td><b>Garnishment</b><br><sf:input class="fancy" type="number" step=".01" path="employee.emp_financial.garnishment" /></td>
				</tr>
				<tr>
					<td colspan="4"><b>====== Payroll Transaction Names ======</b></td>
				</tr>
				<tr>
					<td><b>Federal</b><br><sf:input class="fancy" path="employee.emp_financial.fed_trans"/></td>
					<td><b>State</b><br><sf:input class="fancy" path="employee.emp_financial.st_trans"/></td>
					<td><b>City</b><br><sf:input class="fancy" path="employee.emp_financial.city_trans"/></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td class="centerHeading" colspan="3"><b>====== For Automatic deposit only ======</b></td>
				</tr>
				<tr>
					<td><b>Payment Method</b><br><sf:select class="fancy" type="text" path="employee.emp_financial.pay_method">
						<sf:option value="C">Check</sf:option>
						<sf:option value="D">Direct Deposit</sf:option>
					</sf:select></td>
					<td><b>Account Number</b><br><sf:input class="fancy" type="text" path="employee.emp_financial.account_num" /></td>
					<td><b>Routing Number</b><br><sf:input class="fancy" type="text" path="employee.emp_financial.routing_num" /></td>
				</tr>
				<tr>
					<td><button class="fancy-button" type="button" onclick="closePopup();"><b>Save</b></button></td>
				</tr>
			</table>
		</div>
	</div>
	<sf:hidden path="user_id" />
	<sf:hidden path="employee.user_id"/>
	<sf:hidden path="employee.emp_financial.user_id" />
	<sf:hidden path="common.user_id" />
	<sf:hidden path="employee.accountString"/>
</sf:form>

<script type="text/javascript">
$( document ).ready(function() {
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

	function openPopup() {
		var modal = document.getElementById('empFinancial');
		modal.style.display = "block"
	}

	function closePopup() {
		var modal = document.getElementById('empFinancial');
		modal.style.display = "none";
	}

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
		document.getElementById("employee").submit();
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
	
	$( function() {
	    $( "#stDate" ).datepicker({
	    	dateFormat: "yy-mm-dd",
	        changeMonth: true,
	        changeYear: true,
	        clickInput: true
	    	});
	    $( "#endDate" ).datepicker({
	    	dateFormat: "yy-mm-dd",
	    	    changeMonth: true,
	    	    changeYear: true,
	    	    clickInput: true
	    	});
	  } );

</script>
