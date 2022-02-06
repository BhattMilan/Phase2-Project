<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.dbmanager.DBManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>
	<h3>Hello Routes</h3>
	<%
		DBManager dbMan = new DBManager();
		List<Map<String, Object>> getRouteDetails = dbMan.getRoutes();
		request.setAttribute("routeDetails", getRouteDetails);
	%>
	<div id="table-route-details" class="class-routeDetails">
		<table border="1">
			<tbody>
				<tr>
					<th>Route ID</th>
					<th>Source</th>
					<th>Destination</th>
					<th>Available Date</th>
					<th>Delete Route</th>
				</tr>
				<c:forEach items="${routeDetails}" var="routeDetails">
					<tr>
						<td> <c:out value="${routeDetails['routeID']}"></c:out> </td>
						<td> <c:out value="${routeDetails['source']}"></c:out> </td>
						<td> <c:out value="${routeDetails['destination']}"></c:out> </td>
						<td> <c:out value="${routeDetails['availableDate']}"></c:out> </td>
						<td> <a href="deleteRoute?routeID=${routeDetails['routeID']}">Delete Route</a>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="add-route-btn" class="class-addRoute-btn">
		<input type="button" onclick="location.href='AddFlight.html'" value="Add Flight">
		<input type="button" onclick="location.href='SysAdminDashboard.jsp'" value="Go Dashboard">
	</div>
</body>
</html>