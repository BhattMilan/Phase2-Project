package com;

import java.io.IOException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dbmanager.DBManager;

public class LoginServlet extends HttpServlet{

	DBManager dbManager = new DBManager();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String email = req.getParameter("email");
		String pass = req.getParameter("pass");
		Map<String, String> userDetails = null;
		userDetails = dbManager.getUserDetails(email, pass);
		
		if(userDetails != null && !userDetails.isEmpty()) {
			int isSysAdmin = Integer.parseInt(userDetails.get("isSystemAdmin"));
			if(isSysAdmin == 1) {
				HttpSession session = req.getSession();
				session.setAttribute("firstName", userDetails.get("firstName"));
				session.setAttribute("lastName", userDetails.get("lastName"));
				session.setAttribute("email", userDetails.get("email"));
				dbManager.getRoutes();
				res.sendRedirect("SysAdminDashboard.jsp");
			}
			else {
				HttpSession session = req.getSession();
				session.setAttribute("firstName", userDetails.get("firstName"));
				session.setAttribute("lastName", userDetails.get("lastName"));
				session.setAttribute("email", userDetails.get("email"));
				res.sendRedirect("Dashboard.jsp");
			}
		}
		else {
			RequestDispatcher rd = req.getRequestDispatcher("userNotAva.html");
			rd.include(req, res);
		}
	}
}
