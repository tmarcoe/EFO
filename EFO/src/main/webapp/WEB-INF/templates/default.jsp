<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description"
	content="Enjoy the taste of the Philippines. Enjoy Peach's Coffee.">
<meta name="keywords" content="coffee">
<title><tiles:insertAttribute name="title"></tiles:insertAttribute></title>
<link rel="icon" href="<c:url value='/images/favicon.png' />">
<link type="text/css" href="/css/style.css" rel="stylesheet" />
<link type="text/css" href="/css/jquery-ui.css" rel="stylesheet" />

<script type="text/javascript" src="/script/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="/script/jquery-ui.js"></script>
<script type="text/javascript" src="/script/jquery.autocomplete.min.js"></script>

<tiles:insertAttribute name="includes"></tiles:insertAttribute>

</head>

<body>
	<div class="content">
		<div class="heading">
			<tiles:insertAttribute name="heading_title"></tiles:insertAttribute>
		</div>

		<div class="toolbar">
			<tiles:insertAttribute name="toolbar"></tiles:insertAttribute>
		</div>
		<div class="pageBody">
			<tiles:insertAttribute name="content"></tiles:insertAttribute>
		</div>
		<tiles:insertAttribute name="footer" />
	</div>



	<script>
		function shutdown() {
			if (confirm("This will shutdown the entire system. Do you wish to proceed?") == true) {
				window.location.href = "/admin/shutdown";
			}
		}
	</script>
</body>
</html>