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
	<%
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Cache-Control","no-store");
		response.setHeader("Pragma","no-cache");
		response.setDateHeader ("Expires", 0);
		if(session.getAttribute("firstName")==null){
			response.sendRedirect("login.html");
		}
		else{%>
			<h3>Hello <%= session.getAttribute("firstName") %></h3>			
		<%}
	%>
	<div class="flightDetHeader" align="center">
		<h1>Flight Details</h1>
	</div>
	<div class="flightDetTable" align="left">
		<table border="1">
			<tbody>
				<tr>
					<th>From</th>
					<th>To</th>
					<th>Date</th>
					<th>Flight Number</th>
					<th>Flight Name</th>
					<th>Price</th>
					<th>Departure Time</th>
					<th>Arrival Time</th>
					<th>Available Seats</th>
					<th>Book Ticket</th>
				</tr>
				<tr>
					<c:forEach var="flightDet" items="${flightDetails}">
					<%
						List<Map<String, Object>> flightDetailsList = (List<Map<String, Object>>) request.getAttribute("flightDetails");
						pageContext.setAttribute("map", flightDetailsList, PageContext.APPLICATION_SCOPE);
					%>	
						<tr>			
							<td> <c:out value="${flightDet['source']}"></c:out> </td>
							<td> <c:out value="${flightDet['destination']}"></c:out> </td>
							<td> <c:out value="${flightDet['availableDate']}"></c:out> </td>
							<td> <c:out value="${flightDet['flightNumber']}"></c:out> </td>
							<td> <c:out value="${flightDet['flightName']}"></c:out> </td>
							<td> <c:out value="${flightDet['price']}"></c:out> </td>
							<td> <c:out value="${flightDet['departureTime']}"></c:out> </td>
							<td> <c:out value="${flightDet['arrivalTime']}"></c:out> </td>
							<td> <c:out value="${flightDet['availableSeats']}"></c:out> </td>
							<td> 
								<div class="bookingBtn">
									<% 
										if(session.getAttribute("firstName") != null){%>
											<a href="FillUserDetails.jsp">
												<input type="submit" value="Book Ticket" >
											</a>
										<% }
										else{ %>
											<a href="login.html">
												<input type="submit" value="Book Ticket" >
											</a>
										<%}
									%>
								</div> 
							</td>
						</tr>
					</c:forEach>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>