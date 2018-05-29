<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<sf:form id="details" method="post" action="/admin/savepassword"
	modelAttribute="user">
	<sf:hidden path="user_id" />
	<table class="table">
		<tr>
			<td><b>Password</b></td>
			<td><b>Confirm Password</b></td>
		</tr>
		<tr>
			<td><sf:password path="password" autocomplete="false"
					showPassword="true" /></td>
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
			<td><sf:button type="submit" >Save</sf:button></td>
			<td><sf:button type="button" onclick="window.history.back()">Cancel</sf:button>
		</tr>
	</table>
</sf:form>