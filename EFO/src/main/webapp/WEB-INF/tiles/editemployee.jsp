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
			<td colspan="5"><sf:select class="fancy" path="employee.salutation">
				<sf:option value="Mr.">Mr.</sf:option>
				<sf:option value="Mrs.">Mrs.</sf:option>
				<sf:option value="Ms.">Ms.</sf:option>
				<sf:option value="Miss.">Miss.</sf:option>
				<sf:option value="Dr.">Dr.</sf:option>
			</sf:select>
			<sf:input class="fancy" path="employee.firstname" />
			<sf:input class="fancy" path="employee.lastname" />
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
			<td><b>Position: </b><br><sf:input class="fancy" path="employee.position" /></td>
			<td><b>Starting Date: </b><br><sf:input class="fancy" type="date" path="employee.start_date"/></td>
			<td><b>Ending Date: </b><br><sf:input class="fancy" type="date" path="employee.end_date"/></td>
		</tr>
		<tr>
			<td><sf:errors path="employee.position" class="error"/></td>
			<td><sf:errors path="employee.start_date" class="error" /></td>
			<td><sf:errors path="employee.end_date" class="error" /></td>
		</tr>
		<tr>
			<td><b>Company</b><br><sf:input class="fancy" path="employee.company" /></td>
			<td><b>Division</b><br><sf:input class="fancy" path="employee.division" /></td>
			<td><b>Supervisor</b><br><sf:input class="fancy" path="employee.supervisor" /></td>
		</tr>
		<tr>
			<td><sf:errors path="employee.company" class="error"/>
			<td><sf:errors path="employee.division" class="error"/>
			<td><sf:errors path="employee.supervisor" class="error"/>
		</tr>
		<tr>
			<td><b>Direct Line</b><br><sf:input class="fancy" path="employee.extension" /></td>
			<td><b>Office Location</b><br><sf:input class="fancy" path="employee.office_loc" /></td>
			<td><b>Home Ph</b><br><sf:input class="fancy" path="employee.home_phone"/></td>
		</tr>
		<tr>
			<td><sf:errors path="employee.extension" class="error"/></td>
			<td><sf:errors path="employee.office_loc" class="error"/></td>
			<td><sf:errors path="employee.home_phone" class="error"/></td>
		</tr>
		<tr>
			<td><b>Address 1</b><br><sf:input class="fancy" path="common.address1"/></td>
			<td><b>Address 2</b><br><sf:input class="fancy" path="common.address2"/></td>
			<td><b>City</b><br><sf:input class="fancy" path="common.city"/></td>
		<tr>
			<td><sf:errors path="common.address1" class="error"/></td>
			<td><sf:errors path="common.address2" class="error"/></td>
			<td><sf:errors path="common.city" class="error"/></td>
		</tr>
		<tr>
			<td><b>Region</b><br><sf:input class="fancy" path="common.region"/></td>
			<td><b>Postal Code</b><br><sf:input class="fancy" path="common.postalCode"/></td>
			<td><b>Country Code</b><br><sf:input class="fancy" path="common.country"/></td>
		</tr>
		<tr>
			<td><sf:errors path="common.region" class="error"/></td>
			<td><sf:errors path="common.postalCode" class="error"/></td>
			<td><sf:errors path="common.country" class="error"/></td>
		</tr>
		<tr>
			<td><b>Cell Phone</b><br><sf:input class="fancy" path="employee.cell_phone"/></td>
			<td><b>Emergency Cont</b><br><sf:input class="fancy" path="employee.emer_contact"/></td>
			<td><b>Contact Ph</b><br><sf:input class="fancy" path="employee.emer_ph"/></td>
		</tr>
		<tr>
			<td><sf:errors path="employee.cell_phone" class="error"/></td>
			<td><sf:errors path="employee.emer_contact" class="error"/></td>
			<td><sf:errors path="employee.emer_ph" class="error"/></td>
		</tr>
		<tr>
			<td><b>Do Not Rehire:</b><br><sf:checkbox class="fancy" path="employee.dnr" /></td>
			<td><b>Employment Type:</b><br>
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
		<tr>
			<td><b>Email: </b><sf:input class="fancy"  path="username" readonly="true" /></td>
			<td><b>Enable logins? </b><sf:checkbox  class="fancy" path="enabled"/></td>
		</tr>
		<tr>
			<td><sf:errors path="username" class="error" />
			<td><sf:errors path="enabled" class="error" />
		</tr>
	</table>
		<div class="modal" id="empFinancial">
		<div class="modal-content medium-large-modal fancy">
			<table>
				<tr>
					<th>Tax ID</th>
					<th>State Exemptions</th>
					<th>Federal Exemptioins</th>
				</tr>
				<tr>
					<td><sf:input class="fancy" type="text" path="employee.emp_financial.tax_id" /></td>
					<td><sf:input class="fancy" type="number" step="1" path="employee.emp_financial.st_exempt" /></td>
					<td><sf:input class="fancy" type="number" step="1" path="employee.emp_financial.fd_exempt"/></td>
				</tr>
				<tr>
					<td>Hourly Rate</td>
					<td>% Federal Tax</td>
					<td>% State Tax</td>
					<td>% Fed Unemployment</td>
				</tr>
				<tr>
					<td><sf:input class="fancy" type="number" step=".01" path="employee.emp_financial.hourlyRate" /></td>
					<td><sf:input class="fancy" type="number" step=".01" path="employee.emp_financial.fTaxPrcnt" /></td>
					<td><sf:input class="fancy" type="number" step=".01" path="employee.emp_financial.sTaxPrcnt" /></td>
					<td><sf:input class="fancy" type="number" step=".01" path="employee.emp_financial.fUnPrcnt" /></td>
				</tr>
				<tr>
					<td>% St Unemployment</td>
					<td>% Medical</td>
					<td>% SSI</td>
					<td>% Ret</td>
				</tr>
				<tr>
					<td><sf:input class="fancy" type="number" step=".01" path="employee.emp_financial.sUnPrcnt" /></td>
					<td><sf:input class="fancy" type="number" step=".01" path="employee.emp_financial.medPrcnt" /></td>
					<td><sf:input class="fancy" type="number" step=".01" path="employee.emp_financial.ssiPrcnt" /></td>
					<td><sf:input class="fancy" type="number" step=".01" path="employee.emp_financial.retirePrcnt" /></td>
				</tr>
				<tr>
					<td>Garnishment</td>
					<td>Other</td>
					<td>Reason</td>
				</tr>
				<tr>
					<td><sf:input class="fancy" type="number" step=".01" path="employee.emp_financial.garnishment" /></td>
					<td><sf:input class="fancy" type="number" step=".01" path="employee.emp_financial.other" /></td>
					<td><sf:input class="fancy" type="text" path="employee.emp_financial.otherExpl" /></td>
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
					<td><sf:select class="fancy" type="text" path="employee.emp_financial.payMethod">
						<sf:option value="Check">Check</sf:option>
						<sf:option value="Automatic Deposit">Automatic Deposit</sf:option>
					</sf:select></td>
					<td><sf:input class="fancy" type="text" path="employee.emp_financial.accountNum" /></td>
					<td><sf:input class="fancy" type="text" path="employee.emp_financial.routingNum" /></td>
				</tr>
				<tr>
					<td><button class="fancy-button" type="button" onclick="closePopup();">Save</button></td>
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
