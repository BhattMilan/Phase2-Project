<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
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
	<div id="userDetForm" align="center">
		<form action="bookTicket" method="post">
				<div id="userFullDetails">
				<input type="text" name="firstNameText" placeholder="Enter Your First Name"> <br>
				<input type="text" name="lastNameText" placeholder="Enter Your Last Name"> <br>
				<input type="text" name="emailText" placeholder="Enter Your Email ID"> <br>
				<input type="text" name="mobText" placeholder="Enter Your Mobile No"> <br>
				<input type="number" name="noOfPax" placeholder="Enter Number of Passanger">
				</div>
				<input type="submit" name="bookTicket" value="Book">
			<%
				List<Map<String, Object>> flightDetails = (List<Map<String, Object>>) request.getServletContext().getAttribute("map");
			%>
		</form>
	</div>
</body>
</html>