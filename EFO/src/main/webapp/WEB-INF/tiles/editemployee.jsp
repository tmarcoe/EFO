<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form id="details" action="/admin/updemployee" modelAttribute="user">
<sf:hidden id="selectedRoles" path="roleString"/>
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Email: </b><sf:input class="fancy"  path="username" readonly="true" /></td>
			<td><b>Enable logins? </b><sf:checkbox  class="fancy" path="enabled"/></td>
		</tr>
		<tr>
			<td><sf:errors path="username" class="error" />
			<td><sf:errors path="enabled" class="error" />
		</tr>
		<tr>
			<th>&nbsp;</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>M/F</th>
			<th>Position</th>
			<th>Starting Date</th>
			<th>Ending Date</th>
		</tr>
		<tr>
			<td><sf:select class="fancy" path="employee.salutation">
				<sf:option value="Mr.">Mr.</sf:option>
				<sf:option value="Mrs.">Mrs.</sf:option>
				<sf:option value="Ms.">Ms.</sf:option>
				<sf:option value="Miss.">Miss.</sf:option>
				<sf:option value="Dr.">Dr.</sf:option>
			</sf:select></td>
			<td><sf:input class="fancy" path="employee.firstname" /></td>
			<td><sf:input class="fancy" path="employee.lastname" /></td>
			<td><sf:select class="fancy" path="employee.maleFemale">
					<sf:option value="M">Male</sf:option>
					<sf:option value="F">Female</sf:option>
				</sf:select></td>
			<td><sf:input class="fancy" path="employee.position" /></td>
			<td><sf:input class="fancy" type="date" path="employee.start_date"/></td>
			<td><sf:input class="fancy" type="date" path="employee.end_date"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><sf:errors path="employee.firstname" class="error" /></td>
			<td><sf:errors path="employee.lastname" class="error" /></td>
			<td><sf:errors path="employee.maleFemale" class="error" /></td>
			<td><sf:errors path="employee.position" class="error"/></td>
			<td><sf:errors path="employee.start_date" class="error" /></td>
			<td><sf:errors path="employee.end_date" class="error" /></td>
		</tr>
		<tr>
			<th>Company</th>
			<th>Division</th>
			<th>Supervisor</th>
			<th>Direct Line</th>
			<th>Office Location</th>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="employee.company" /></td>
			<td><sf:input class="fancy" path="employee.division" /></td>
			<td><sf:input class="fancy" path="employee.supervisor" /></td>
			<td><sf:input class="fancy" path="employee.extension" /></td>
			<td><sf:input class="fancy" path="employee.office_loc" /></td>
		</tr>
		<tr>
			<td><sf:errors path="employee.company" class="error"/>
			<td><sf:errors path="employee.division" class="error"/>
			<td><sf:errors path="employee.supervisor" class="error"/>
			<td><sf:errors path="employee.extension" class="error"/>
			<td><sf:errors path="employee.office_loc" class="error"/>
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
			<td><sf:input class="fancy" path="common.address1"/>
			<td><sf:input class="fancy" path="common.address2"/>
			<td><sf:input class="fancy" path="common.city"/>
			<td><sf:input class="fancy" path="common.region"/>
			<td><sf:input class="fancy" path="common.postalCode"/>
			<td><sf:input class="fancy" path="common.country"/>
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
			<th>Home Ph</th>
			<th>Cell Phone</th>
			<th>Emergency Cont</th>
			<th>Contact Ph</th>
		</tr>
		<tr>
			<td><sf:input class="fancy" path="employee.home_phone"/></td>
			<td><sf:input class="fancy" path="employee.cell_phone"/></td>
			<td><sf:input class="fancy" path="employee.emer_contact"/></td>
			<td><sf:input class="fancy" path="employee.emer_ph"/></td>
		</tr>
		<tr>
			<td><sf:errors path="employee.home_phone" class="error"/></td>
			<td><sf:errors path="employee.cell_phone" class="error"/></td>
			<td><sf:errors path="employee.emer_contact" class="error"/></td>
			<td><sf:errors path="employee.emer_ph" class="error"/></td>
		</tr>
		<tr>
			<td><b>Do Not Rehire:</b> <sf:checkbox class="fancy" path="employee.dnr" /></td>
			<td><b>Employment Type:</b>
				<sf:select class="fancy" path="employee.emp_type">
					<sf:option value="F">Full Time</sf:option>
					<sf:option value="S">Salary (non-exempt)</sf:option>
					<sf:option value="E">Salary (exempt)</sf:option>
					<sf:option value="P">Part Time</sf:option>
					<sf:option value="C">Contract</sf:option>
					<sf:option value="T">Temporary</sf:option>
				</sf:select></td>
			<td><b>Role(s):</b><br><sf:select class="fancy-roles" path="roles" id="roles" multiselect="true">
					<sf:options items="${roles}" itemValue="id" itemLabel="role" />
				</sf:select></td>
			
		</tr>
		<tr>
			<td><sf:errors path="roles" class="error" /></td>
		</tr>
		<tr>
			<td><button class="fancy-button" type="button" onclick="formSubmit()" ><b>Save</b></button></td>
			<td><button class="fancy-button" type="button" onclick="openPopup()"><b>Financial Information</b></button></td>
			<td><button class="fancy-button" type="button"onclick="window.history.back()" ><b>Cancel</b></button></td>
		</tr>
	</table>
		<div class="modal" id="empFinancial">
		<div class="modal-content medium-modal">
			<table>
				<tr>
					<th>Tax ID</th>
					<th>State Exemptions</th>
					<th>Federal Exemptioins</th>
				</tr>
				<tr>
					<td><sf:input type="text" path="employee.emp_financial.tax_id" /></td>
					<td><sf:input type="number" step="1" path="employee.emp_financial.st_exempt" /></td>
					<td><sf:input type="number" step="1" path="employee.emp_financial.fd_exempt"/></td>
				</tr>
				<tr>
					<td>Hourly Rate</td>
					<td>% Federal Tax</td>
					<td>% State Tax</td>
					<td>% Fed Unemployment</td>
				</tr>
				<tr>
					<td><sf:input type="number" step=".01" path="employee.emp_financial.hourlyRate" /></td>
					<td><sf:input type="number" step=".01" path="employee.emp_financial.fTaxPrcnt" /></td>
					<td><sf:input type="number" step=".01" path="employee.emp_financial.sTaxPrcnt" /></td>
					<td><sf:input type="number" step=".01" path="employee.emp_financial.fUnPrcnt" /></td>
				</tr>
				<tr>
					<td>% St Unemployment</td>
					<td>% Medical</td>
					<td>% SSI</td>
					<td>% Ret</td>
				</tr>
				<tr>
					<td><sf:input type="number" step=".01" path="employee.emp_financial.sUnPrcnt" /></td>
					<td><sf:input type="number" step=".01" path="employee.emp_financial.medPrcnt" /></td>
					<td><sf:input type="number" step=".01" path="employee.emp_financial.ssiPrcnt" /></td>
					<td><sf:input type="number" step=".01" path="employee.emp_financial.retirePrcnt" /></td>
				</tr>
				<tr>
					<td>Garnishment</td>
					<td>Other</td>
					<td>Reason</td>
				</tr>
				<tr>
					<td><sf:input type="number" step=".01" path="employee.emp_financial.garnishment" /></td>
					<td><sf:input type="number" step=".01" path="employee.emp_financial.other" /></td>
					<td><sf:input type="text" path="employee.emp_financial.otherExpl" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><hr></td>
					<td class="centerHeading" colspan="2">For Automatic deposit only</td>
					<td><hr></td>
				</tr>

				<tr>
					<td>Payment Type</td>
					<td>Account Number</td>
					<td>Routing Number</td>
				</tr>
				<tr>
					<td><sf:select type="text" path="employee.emp_financial.payMethod">
						<sf:option value="Check">Check</sf:option>
						<sf:option value="Automatic Deposit">Automatic Deposit</sf:option>
					</sf:select></td>
					<td><sf:input type="text" path="employee.emp_financial.accountNum" /></td>
					<td><sf:input type="text" path="employee.emp_financial.routingNum" /></td>
				</tr>
				<tr>
					<td><button type="button" onclick="closePopup();">Save</button></td>
				</tr>
			</table>
		</div>
	</div>
	<sf:hidden path="user_id" />
	<sf:hidden path="employee.emp_financial.user_id" />
	<sf:hidden path="employee.user_id"/>
	<sf:hidden path="common.user_id" />
	<sf:hidden path="password" />
	<sf:hidden path="temp_pw" />
</sf:form>

<script type="text/javascript">
$( document ).ready(function() {
	var ndx = $("#selectedRoles").val();
	var selectedOptions = ndx.split(";");
	for(var i in selectedOptions) {
		 var optionVal = selectedOptions[i];
		$("#roles").find("option[value="+optionVal+"]").prop("selected", "selected");
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

	var opt = document.getElementById("roles");
	var userRoles = "";
	for (var i=0; i < opt.options.length; i++) {
		if (opt.options[i].selected) {
			if (userRoles == "") {
				userRoles += opt.options[i].value;
			}else{
				userRoles += ";" + opt.options[i].value;
			}
		}
	}
	var rs = document.getElementById("selectedRoles");
	rs.value = userRoles;
	document.getElementById("details").submit();
}

</script>
