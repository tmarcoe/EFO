<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" href="/css/tables.css" rel="stylesheet" />
<script type="text/javascript" src="/script/date.js"></script>

<sf:form method="post" modelAttribute="timeSheet" >
	<h3>Time Sheet For ${timesheet.name}</h3>
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
					<td colspan="5"><button class="fancy-button" type="button" onclick="" ><b>New Account Number</b></button>
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
	<sf:hidden path=""/>
</sf:form>

<script type="text/javascript">

	
	
</script>