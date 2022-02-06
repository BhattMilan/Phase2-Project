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
<h1>Your Ticket has been booked successfully</h1>
	<div id="ticketInfo">
		<table border="1">
			<tbody>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Email</th>
						<th>Mobile Number</th>
						<th>Number of Passengers</th>
						<th>Flight Number</th>
						<th>Flight Name</th>
						<th>From</th>
						<th>To</th>
						<th>Departure Time</th>
						<th>Arrival Time</th>
						<th>Price</th>
						<th>Total Price</th>
					</tr>
					<tr>
						<c:forEach items="${bookingInfoDetails}" var="bookingList">
							<td> <c:out value="${bookingList}"></c:out> </td>
						</c:forEach>
					</tr>
			</tbody>
		</table>
	</div>
	<div id="btn-grp" class="class-btn-grp">
		<input type="button" onclick="location.href='Dashboard.jsp'" value="Book More Ticket">
	</div>
</body>
</html>