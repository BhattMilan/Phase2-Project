package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dbmanager.DBManager;

public class RegistrationServlet extends HttpServlet{

	DBManager dbManager = new DBManager();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int recordInserted = 0;
		Map<String, String> paramDetails;
		String firstName = req.getParameter("fname");
		String lastName = req.getParameter("lname");
		String email = req.getParameter("email");
		String gender = req.getParameter("genderRad");
		String mobNo = req.getParameter("mobNo");
		String age = req.getParameter("age");
		String pass = req.getParameter("pass");
		String confPass = req.getParameter("confPass");
		
		if((!"".equals(pass) && !"".equals(confPass) && pass.equals(confPass))){
			paramDetails = new HashMap<>();
			paramDetails.put("firstName", firstName);
			paramDetails.put("lastName", lastName);
			paramDetails.put("email", email);
			paramDetails.put("gender", gender);
			paramDetails.put("age", age);
			paramDetails.put("mobNo", mobNo);
			paramDetails.put("password", pass);
			recordInserted = dbManager.saveRegiUser(paramDetails);
			if(recordInserted == 1) {
				res.getWriter().write("<html><body><h3>User "+firstName+"  "+lastName+" registered scuessfully! <br>"
						+ "And your email is: "+email+"</h3></body></html>");
			}
			else if(recordInserted == 3) {
				res.sendRedirect("EmailExist.html");
			}
			else {
				res.getWriter().write("<html><body><h3>User registration is failed!</h3></body></html>");
			}
		}
		else {
			res.getWriter().write("<html><body><h3>Password is not valid</h3></body></html>");
		}
	}
}
