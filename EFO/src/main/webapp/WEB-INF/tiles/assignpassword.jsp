<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link type="text/css" rel="stylesheet" href="/css/fancy-input.css" />
<link type="text/css" rel="stylesheet" href="/css/tables.css" />

<sf:form id="details" method="post" action="/personnel/savepassword"
	modelAttribute="user">
	<sf:hidden path="user_id" />
	<table class="fancy-table tableshadow">
		<tr>
			<td><b>Password</b></td>
			<td><b>Confirm Password</b></td>
		</tr>
		<tr>
			<td><sf:password class="fancy" path="password" autocomplete="false"
					showPassword="true" /></td>
			<td><input class="fancy" id="confirmpass" class="control" name="confirmpass"
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
			<td><sf:button class="fancy-button" type="submit" ><b>Save</b></sf:button></td>
			<td><sf:button class="fancy-button" type="button" onclick="window.history.back()"><b>Cancel</b></sf:button>
		</tr>
	</table>
	<sf:hidden path="user_id"/>
	<sf:hidden path="username"/>
	<sf:hidden path="temp_pw"/>
</sf:form>