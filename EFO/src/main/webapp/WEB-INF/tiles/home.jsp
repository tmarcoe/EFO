<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="/WEB-INF/tld/security.tld"%>

<sql:setDataSource var="ds" driver="com.mysql.jdbc.Driver"
	url="jdbc:mysql://localhost/accountmaster?useSSL=false"
	user="donzalma_admin" password="In_heaven3" />
<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />

<sf:form method="post" action="/user/savepassword" commandName="user">
	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">My Tab</a></li>
			<li><a href="#tabs-2">Personel</a></li>
			<li><a href="#tabs-3">Operations</a></li>
			<li><a href="#tabs-4">Accounting</a></li>
		</ul>
		<div id="tabs-1">
			<table class="menuTable menuTableSpace">
				<tr>
					<td><a href="#" onclick="openPopup()"><img class="tile" alt="Change Password" src="<c:url value='/images/password.png'/>"></a></td>
					<sec:isAuthenticated>
						<td><a href="/logout"><img class="tile" alt="Login" src="<c:url value='/images/logout.png'/>"></a></td>
					</sec:isAuthenticated>
					<sec:isNotAuthenticated>
						<td><a href="/login"><img class="tile" alt="Login" src="<c:url value='/images/login.png'/>"></a></td>
					</sec:isNotAuthenticated>
				</tr>
			</table>
		</div>
		<div id="tabs-2">
			<table class="menuTable menuTableSpace">
				<tr>
					<td><a href="/admin/customerlist"><img class="tile" alt="Customer" src="<c:url value='/images/customer.png'/>"></a></td>
					<td><a href="/admin/vendorlist"><img class="tile" alt="Vendor" src="<c:url value='/images/vendor.png'/>"></a></td>
					<td><a href="/admin/employeelist"><img class="tile" alt="Employees" src="<c:url value='/images/employees.png'/>"></a></td>
				</tr>
			</table>
		</div>
		<div id="tabs-3">
			<p></p>
		</div>
		<div id="tabs-4">
			<table class="menuTable menuTableSpace">
				<tr>
					<td><a href="/accounting/ar"><img class="tile" alt="Accounts Receivable" src="<c:url value='/images/receivable.png'/>"></a></td>
					<td><a href="/accounting/ap"><img class="tile" alt="Accounts Payable" src="<c:url value='/images/payable.png'/>"></a></td>
					<td><a href="#" onclick="inputDate()"><img class="tile" alt="General Ledger" src="<c:url value='/images/general.png'/>"></a></td>
					<td><a href="/accounting/accountslist"><img class="tile" alt="Chart Of Accounts" src="<c:url value='/images/accounts.png'/>"></a></td>
					<td><a href="/accounting/listpettycash"><img class="tile" alt="Petty Cash" src="<c:url value='/images/petty-cash.png'/>"></a></td>
				</tr>
			</table>
		</div>
	</div>
	<div id="sPass" class="modal">
		<div class="modal-content small-modal">
			<h2>Change your password</h2>
			<table style="margin-left: auto; margin-right: auto;">
				<tr>
					<th>Password</th>
					<th>Repeat Password</th>
				</tr>
				<tr>
					<td><sf:password id="password" name="password" path="password"
							class="control" /></td>
					<td><input id="confirmpass" class="control" name="confirmpass"
						type="password" /></td>
				</tr>
				<tr>
					<td><div id="pbar">
							<label id="pLabel"></label>
							<div id="pStrength"></div>
						</div>&nbsp;</td>
					<td><div id="matchpass"></div>&nbsp;</td>
				</tr>
				<tr>
					<td><button type="submit">Save</button></td>
				</tr>
			</table>
		</div>
	</div>
	<div id="getPeriod" class="modal">
		<div class="modal-content small-modal">
			<h2>Enter the Period</h2>
			<table style="margin-left: auto; margin-right: auto;">
				<tr>
					<th>Start Date</th>
					<th>End Date</th>
				</tr>
				<tr>
					<td><input id="stDate" type="date" /></td>
					<td><input id="endDate" type="date" /></td>
				</tr>
				<tr>
					<td><button type="button" onclick="setDate()">View Period</button></td>
				</tr>
			</table>
		</div>
	</div>
</sf:form>
<script type="text/javascript">
	function disableButton(id) {
		document.getElementById(id).disabled = true;
	}
	function inputDate() {
		var modal = document.getElementById('getPeriod');
		modal.style.display = "block";
	}
	function setDate() {
		var from = document.getElementById('stDate').value;
		var to = document.getElementById('endDate').value;
		window.location.href = "/accounting/ledgerlist/from/" + from + "/to/" + to;
	}
	function openPopup() {
		var modal = document.getElementById('sPass');
		modal.style.display = "block"
	}

	function closePopup() {
		var modal = document.getElementById('sPass');
		modal.style.display = "none";
	}
	$(function() {
		$("#tabs").tabs({
			classes : {
				"ui-tabs" : "ui-corner-all"
			}
		});
	});
</script>
	<c:if test="${user.isTemp_pw() == true}">
		<script>openPopup()</script>
	</c:if>

