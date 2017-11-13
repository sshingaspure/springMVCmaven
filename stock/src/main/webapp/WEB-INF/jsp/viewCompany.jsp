<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List Of Companies</title>
</head>
<body>

	<h3>Company Details</h3>
	<hr size="4" color="gray" />
	<h2>${message}</h2>
	<table>
		<tr>
			<td>Company ID</td>
			<td>Company Name</td>
			<td>Shares Value</td>
		</tr>
		<c:forEach items="${companyList}" var="company">
			<tr>
				<td><c:out value="${company.cmp_id}" /></td>
				<td><c:out value="${company.cmp_name}" /></td>
				<td><c:out value="${company.share_value}" /></td>
			</tr>

		</c:forEach>
		<tr></tr>
		<tr></tr>
		<tr>
			<td><a href="logout">Logout</a></td>
		</tr>
	</table>

</body>
</html>