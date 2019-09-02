<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" href="/css/tables.css" rel="stylesheet" />
<script type="text/javascript" src="/script/date.js"></script>

<sf:form method="post" modelAttribute="timeSheet">
	<fmt:formatDate type="date" value='${endPeriod}' var="endDt" />
	<fmt:formatDate type="date" value='${timeSheet.begin_period}' var="strDate" />
	<c:if test="${not empty timeSheet.submitted}">
		<h3>Period Closed</h3>
	</c:if>
	<table
		class="tableview tableborder padding tableshadow rjsecond rjthird rjfourth rjfifth rjsixth rjseventh rjeighth rjnineth">
		<col width="200" />
		<col width="70" />
		<col width="70" />
		<col width="70" />
		<col width="70" />
		<col width="70" />
		<col width="70" />
		<col width="70" />
		<caption>From: ${strDate}&emsp;&emsp;To: ${endDt}</caption>
		<tr>
			<th>Account Number</th>
			<th>Su</th>
			<th>Mo</th>
			<th>Tu</th>
			<th>We</th>
			<th>Th</th>
			<th>Fr</th>
			<th>Sa</th>
			<th>Weekly Totals</th>
			<th>&nbsp;</th>
		</tr>
		<c:set var="TotalSu" value="0" />
		<c:set var="TotalMo" value="0" />
		<c:set var="TotalTu" value="0" />
		<c:set var="TotalWe" value="0" />
		<c:set var="TotalTh" value="0" />
		<c:set var="TotalFr" value="0" />
		<c:set var="TotalSa" value="0" />

		<c:forEach var="item" items="${timeSheet.timeSheetItems}">

			<c:set var="TotalSu" value="${TotalSu + item.sun}" />
			<c:set var="TotalMo" value="${TotalMo + item.mon}" />
			<c:set var="TotalTu" value="${TotalTu + item.tue}" />
			<c:set var="TotalWe" value="${TotalWe + item.wed}" />
			<c:set var="TotalTh" value="${TotalTh + item.thu}" />
			<c:set var="TotalFr" value="${TotalFr + item.fri}" />
			<c:set var="TotalSa" value="${TotalSa + item.sat}" />
			<tr>
				<td>${item.account_num}</td>
				<td>${item.sun}</td>
				<td>${item.mon}</td>
				<td>${item.tue}</td>
				<td>${item.wed}</td>
				<td>${item.thu}</td>
				<td>${item.fri}</td>
				<td>${item.sat}</td>
				<td>${item.sun + item.mon + item.tue + item.wed + item.thu + item.fri + item.sat}</td>
				<td><button type="button" 
						onclick="updateWindow(${item.id}, '${item.account_num}', ${item.sun}, ${item.mon}, ${item.tue}, ${item.wed}, ${item.thu}, ${item.fri}, ${item.sat})" >Edit</button></td>
			</tr>
		</c:forEach>
		<tfoot class="tablefooter">
			<tr>
				<td>Totals -></td>
				<td>${TotalSu}</td>
				<td>${TotalMo}</td>
				<td>${TotalTu}</td>
				<td>${TotalWe}</td>
				<td>${TotalTh}</td>
				<td>${TotalFr}</td>
				<td>${TotalSa}</td>
				<td>${TotalSu + TotalMo + TotalTu + TotalWe + TotalTh + TotalFr + TotalSa}</td>
				<td>&nbsp;</td>

			</tr>
			<tr>
				<c:if test="${empty timeSheet.submitted}">
					<td colspan="5"><button class="fancy-button" type="button" onclick="addWindow(${timeSheet.reference})" ><b>New Account Number</b></button>
					<td><button class="fancy-button" type="button" onclick="window.location.href='#'">
							<b>Submit</b>
						</button>
				</c:if>
				
				<td colspan="5"><button class="fancy-button" type="button" onclick="window.location.href='/#tabs-9'">
						<b>Back</b>
					</button></td>
			</tr>
		</tfoot>
	</table>
</sf:form>
<div id="editTs" class="modal">
	<div class="modal-content large-modal fancy">
		<c:set var="sz" value="6"/>
		<table  style="margin-left: auto; margin-right: auto;">
			<tr>
				<th>Account Number</th>
				<th>Sun</th>
				<th>Mon</th>
				<th>Tue</th>
				<th>Wed</th>
				<th>Thu</th>
				<th>Fri</th>
				<th>Sat</th>
			</tr>
			<tr>
				<td><input id="an" class="fancy" readonly="readonly" /></td>
				<td><input id="su" class="fancy" type="number" step=".25" maxlength="5" size="${sz}"/></td>
				<td><input id="m" class="fancy" type="number" step=".25" maxlength="5" size="${sz}" /></td>
				<td><input id="tu" class="fancy" type="number" step=".25" maxlength="5" size="${sz}" /></td>
				<td><input id="w" class="fancy" type="number" step=".25" maxlength="5" size="${sz}" /></td>
				<td><input id="th" class="fancy" type="number" step=".25" maxlength="5" size="${sz}" /></td>
				<td><input id="f" class="fancy" type="number" step=".25" maxlength="5" size="${sz}" /></td>
				<td><input id="sa" class="fancy" type="number" step=".25" maxlength="5" size="${sz}" /></td>
			</tr>
			<tr>
				<td colspan="4"><button class="fancy-button" type="button" onclick="update()"><b>Update</b></button></td>
				<td colspan="4"><button class="fancy-button" type="button" onclick="$('#editTs').hide()"><b>Cancel</b></button> 
			</tr>		
		</table>
		<input id="id" type="hidden">
	</div>
</div>
<div id="newTs" class="modal">
	<div class="modal-content large-modal fancy">
		<c:set var="sz" value="6"/>
		<table  style="margin-left: auto; margin-right: auto;">
			<tr>
				<th>Account Number</th>
				<th>Sun</th>
				<th>Mon</th>
				<th>Tue</th>
				<th>Wed</th>
				<th>Thu</th>
				<th>Fri</th>
				<th>Sat</th>
			</tr>
			<tr>
				<td><select class="fancy" id="nan" >
						<c:forEach items="${accounts}" var="item">
							<option value="${item.account_number}">${item.description}</option>
						</c:forEach>
					</select></td>
				<td><input id="nsu" class="fancy" type="number" step=".25" width="${sz}"/></td>
				<td><input id="nm" class="fancy" type="number" step=".25" width="${sz}" /></td>
				<td><input id="ntu" class="fancy" type="number" step=".25" width="${sz}" /></td>
				<td><input id="nw" class="fancy" type="number" step=".25" width="${sz}" /></td>
				<td><input id="nth" class="fancy" type="number" step=".25" width="${sz}" /></td>
				<td><input id="nf" class="fancy" type="number" step=".25" width="${sz}" /></td>
				<td><input id="nsa" class="fancy" type="number" step=".25" width="${sz}" /></td>
			</tr>
			<tr>
				<td colspan="4"><button class="fancy-button" type="button" onclick="checkExists()"><b>Add Account</b></button></td>
				<td colspan="4"><button class="fancy-button" type="button" onclick="$('#newTs').hide()"><b>Cancel</b></button> 
			</tr>		
		</table>
		<input id="ref" type="hidden">
	</div>
</div>
<div id="exists" class="modal">
	<div class="modal-content small-modal fancy">
		<h2>That account is already on your time sheet.</h2>
		<h2>Press 'Edit' to add or change the hours.</h2>
		<button class="fancy-button" type="button" onclick="closeAll()">OK</button>
	</div>
</div>

<script type="text/javascript">

	function updateWindow(id, account_num, su, m, tu, w, th, f, sa) {
		$("#id").val(id);
		$("#an").val(account_num);
		$("#su").val(su);
		$("#m").val(m);
		$("#tu").val(tu);
		$("#w").val(w);
		$("#th").val(th);
		$("#f").val(f);
		$("#sa").val(sa);
		$("#editTs").show();
	}
	
	function update() {
		
		var id = $("#id").val();
		var su = $("#su").val();
		if (su === "") su = 0.0;
		var m = $("#m").val();
		if (m === "") m = 0.0;
		var tu = $("#tu").val();
		if (tu === "") tu = 0.0;
		var w = $("#w").val();
		if (w === "") w = 0.0;
		var th = $("#th").val();
		if (th === "") th = 0.0;
		var f = $("#f").val();
		if (f === "") f = 0.0;
		var sa = $("#sa").val();
		if (sa === "") sa = 0.0;
		
		var arguments = "?id=" + id + "&sun=" + su + "&mon=" + m + "&tue=" + tu + "&wed=" + w + "&thu=" + th + "&fri=" + f + "&sat=" + sa;
		
		window.location.href = "/timesheet/updatetsitem" + arguments;
		
	}
	
	function add() {
		
		var nan = $("#nan").val();
		if (nan === "") {
			$("#newTs").hide();
		}else{
			var rf = $("#ref").val();
			var su = $("#nsu").val();
			if (su === "") su = 0.0;
			var m = $("#nm").val();
			if (m === "") m = 0.0;
			var tu = $("#ntu").val();
			if (tu === "") tu = 0.0;
			var w = $("#nw").val();
			if (w === "") w = 0.0;
			var th = $("#nth").val();
			if (th === "") th = 0.0;
			var f = $("#nf").val();
			if (f === "") f = 0.0;
			var sa = $("#nsa").val();
			if (sa === "") sa = 0.0;
		
			var arguments = "?reference=" + rf + "&account_num=" + nan + "&sun=" + su + "&mon=" + m + "&tue=" + tu + "&wed=" + w + "&thu=" + th + "&fri=" + f + "&sat=" + sa;
			
			
			$("#nsu").val("0");
			$("#nm").val("0");
			$("#ntu").val("0");
			$("#nw").val("0");
			$("#nth").val("0");
			$("#nf").val("0");
			$("#nsa").val("0");
			$("#nan").val("");
			
			window.location.href = "/timesheet/addtsitem" + arguments;
		}
	}
	
	function addWindow(reference) {
		$("#ref").val(reference);
		$('#newTs').show();
	}
	
	function closeAll() {
		$("#nan").val("");
		$('#exists').hide();
		$('#newTs').hide();
	}

	function checkExists() {
		var reference = $("#ref").val();
		var accountNum = $("#nan").val();
		
		$.getJSON("/rest/accountnumexists?accountNum=" + accountNum + "&reference=" + reference, function(data) {
				if (data.exists == true) {
					$("#exists").show();
				}else{
					add();
				}
			}).fail(function(jqXHR, textStatus, errorThrown) {
				alert("error " + textStatus + "\n" + "incoming Text " + jqXHR.responseText);
			});
	}
	
	
</script>