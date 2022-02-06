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
	<form action="searchResultPage" method="get">
			<label for="from" id="from"> From:  </label> 
			<input type="text" name="sourceRoute"> <br>
			<label for="to" id="to"> To:  </label>
			<input type="text" name="destinationRoute"> <br>
			<label for="deptDate" id="deptDate"> Departure Date:  </label>
			<input type="date" id="deptDate" name="deptDate"> <br>
			<input type="submit" id="search" name="search" value="search">
		</form>
	<form action="logout">
    	<input type="submit" value="Logout"/>
	</form>
</body>
</html>