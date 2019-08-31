<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<link type="text/css" rel="stylesheet" href="/css/modal-popup.css" />
<link type="text/css" href="/css/tables.css" rel="stylesheet" />


<sf:form id="pgform" method="post" modelAttribute="pparam" action="${pageLink}">
	<table class="tableview tableshadow tableborder" >

		<thead>
			<tr>
				<th>user ID</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
				<th colspan="4">&nbsp;</th>
			</tr>
		</thead>
		<c:forEach var="user" items="${objectList.pageList}" >
			<tr>
				<td><fmt:formatNumber type="number" pattern="00000000" value="${user.user_id}" />  </td>
				<td>${user.employee.firstname}</td>
				<td>${user.employee.lastname}</td>
				<td>${user.username}</td>
				<td><button type="button" onclick="window.location.href='/personnel/editemployee?user_id=${user.user_id}'">Edit</button></td>
				<td><button type="button" onclick="deleteUser('${user.user_id}')">Delete</button></td>
				<td><button type="button" onclick="window.location.href='/personnel/assigntsacccounts?user_id=${user.user_id}'">Time Sheet Accounts</button>
				<c:choose>
					<c:when test="${user.enabled == true}">
						<td><button type="button" onclick="window.location.href='/personnel/assignpassword?user_id=${user.user_id}'">Temporary Password</button></td>
					</c:when>
					<c:otherwise>
						<td>&nbsp;</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
		<tfoot class="tablefooter" >
			<tr>
				<td colspan="7"><button type="button" onclick="window.location.href = '/personnel/newemployee'" >New Employee</button></td>
				<td colspan="1"><button type="button" onclick="window.location.href = '/#tabs-2'">Back</button></td>
			</tr>
		</tfoot>
	</table>
</sf:form>
<script type="text/javascript">
	function deleteUser(user_id) {
		if (confirm("Do you really want to delete this user?") == true) {
			window.location.href="/personnel/deleteuser?user_id=" + user_id;
		}
	}
</script>
