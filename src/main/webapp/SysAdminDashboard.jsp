<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
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
	<div id="btn-rt-fli" class="class-rt-fli">
		<input type="button" onclick="location.href='SysAdminRoutes.jsp'" value="View Routes">
		<input type="button" onclick="location.href='SysAdminFlights.jsp'" value="View Flights">
		<input type="button" onclick="location.href='AddFlight.html'" value="Add Flight">
	</div>
	<div id="logout-btn" class="btn-logout">
		<input type="button" onclick="location.href='logout'" value="Logout">
	</div>
</body>
</html>