<%@page import="com.dbmanager.DBManager"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
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
	<h3>Hello Flights</h3>
	<%
		DBManager dbManager = new DBManager();
		List<Map<String, Object>> getAllFlights = dbManager.getFlights();
		request.setAttribute("allFlightsDetails", getAllFlights);
	%>
	<div id="table-flight-details" class="class-flightDetails">
		<table border="1">
			<tbody>
				<tr>
					<th>Flight Number</th>
					<th>Flight Name</th>
					<th>RouteID</th>
					<th>Price</th>
					<th>Departure Time</th>
					<th>Arrival Time</th>
					<th>Available Seats</th>
				</tr>
				<c:forEach items="${allFlightsDetails}" var="allFlights">
					<tr>
						<td> <c:out value="${allFlights['flightNumber']}"></c:out> </td>
						<td> <c:out value="${allFlights['flightName']}"></c:out> </td>
						<td> <c:out value="${allFlights['routeID']}"></c:out> </td>
						<td> <c:out value="${allFlights['price']}"></c:out> </td>
						<td> <c:out value="${allFlights['departureTime']}"></c:out> </td>
						<td> <c:out value="${allFlights['arrivalTime']}"></c:out> </td>
						<td> <c:out value="${allFlights['availableSeats']}"></c:out> </td>
						<td> <a href="deleteRoute?routeID=${allFlights['flightNumber']}">Delete Flight</a>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="btn-grp" class="class-btn-grp">
		<input type="button" onclick="location.href='AddFlight.html'" value="Add Flight">
		<input type="button" onclick="location.href='SysAdminDashboard.jsp'" value="Go To Dashboard">
	</div>
</body>
</html>