package com;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dbmanager.DBManager;

public class DeleteRouteServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		DBManager dbManager = new DBManager();
		String routeID = req.getParameter("routeID");
		int isRecDeleted = dbManager.deleteRoute(routeID);
		if(isRecDeleted == 1) {
			RequestDispatcher rd = req.getRequestDispatcher("DeleteRouteSuccessfully.html");
			rd.forward(req, res);
		}
		else {
			res.getWriter().write("<html><body>Having an issue in deleting a route, please try again</body></html>");
		}
	}
}
