<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />
<link type="text/css" media="screen" rel="stylesheet" href="/css/multi-select.css" />

<script type="text/javascript" src="/script/jquery.multi-select.js"></script>

<sf:form id="assignAccounts" action="/personnel/updateassignedaccounts" modelAttribute="employee" >
	<table class="fancy-table tableshadow">
		<tr>
			<th>Account Numbers</th>
		</tr>
		<tr>
			<td><select multiple class="fancy-roles" id="an" name="an[]">
					<c:forEach items="${accounts}" var="item">
						<option value="${item.account_number}">${item.description}</option>
					</c:forEach>
				</select></td>
		</tr>
		<tr>
			<td><sf:button class="fancy-button" type="button" onclick="formSubmit()" ><b>Save</b></sf:button></td>
		</tr>
	</table>
	<sf:hidden path="user_id"/>
	<sf:hidden path="salutation"/>
	<sf:hidden path="firstname"/>
	<sf:hidden path="lastname"/>
	<sf:hidden path="maleFemale"/>
	<sf:hidden path="home_phone"/>
	<sf:hidden path="cell_phone"/>
	<sf:hidden path="emer_contact"/>
	<sf:hidden path="emer_ph"/>
	<sf:hidden path="company"/>
	<sf:hidden path="division"/>
	<sf:hidden path="supervisor"/>
	<sf:hidden path="extension"/>
	<sf:hidden path="office_loc"/>
	<sf:hidden path="position"/>
	<sf:hidden path="dnr"/>
	<sf:hidden path="emp_type"/>
	<sf:hidden path="start_date"/>
	<sf:hidden path="end_date"/>
	<sf:hidden id="aa" path="accountString"/>
</sf:form>

<script type="text/javascript">
$(document).ready(
		function() {
			$('#an').multiSelect();
			var ndx = $("#aa").val();
			var selectedOptions = ndx.split(";");
			$('#an').multiSelect('select', selectOptions);
		});
		
function formSubmit() {

	var opt = document.getElementById("an");
	var tsAccounts = "";
	for (var i = 0; i < opt.options.length; i++) {
		if (opt.options[i].selected) {
			if (tsAccounts == "") {
				tsAccounts += opt.options[i].value;
			} else {
				tsAccounts += ";" + opt.options[i].value;
			}
		}
	}
	$("#aa").val(tsAccounts);
	$("#assignAccounts").submit();
	
}

</script>