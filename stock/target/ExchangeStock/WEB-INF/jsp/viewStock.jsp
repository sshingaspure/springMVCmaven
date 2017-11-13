<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.util.List"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View stocks</title>
</head>
<body>
	<h3>Employee Details</h3>
	<hr size="4" color="gray" />
	<h2>${message}</h2>
	<table>
		<tr>
			<td>Company Name</td>
			<td>Shares Number</td>
		</tr>
		<c:forEach items="${listShares}" var="list">
			<tr>
				<td> <c:out value="${list.cmp_name}" /></td>
				<td> <c:out value="${list.share_num}" /></td>
			</tr>

		</c:forEach>
			<tr></tr>
			<tr></tr>
			<tr><td><a href="logout">Logout</a></td></tr>
	</table>

</body>
</html>