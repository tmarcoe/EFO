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
				<th>&nbsp;</th>
			</tr>
		</thead>
		<c:forEach var="user" items="${objectList.pageList}" >
			<tr>
				<td><fmt:formatNumber type="number" pattern="00000000" value="${user.user_id}" />  </td>
				<td>${user.customer.firstname}</td>
				<td>${user.customer.lastname}</td>
				<td>${user.username}</td>
				<td><button type="button" onclick="window.location.href='/accounting/newreceivable?username=${user.username}'">Choose</button></td>
			</tr>
		</c:forEach>
		<tfoot class="tablefooter" >
			<tr>
				<td colspan="5"><button type="button" onclick="window.location.href = '/#tabs-2'">Back</button></td>
			</tr>
		</tfoot>
	</table>
</sf:form>
<script type="text/javascript">
function deleteUser(user_id) {
	if (confirm("Do you really want to delete this user?") == true) {
		window.location.href="/admin/deleteuser?user_id=" + user_id;
	}
}
</script>
