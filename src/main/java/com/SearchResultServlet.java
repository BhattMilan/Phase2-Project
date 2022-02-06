package com;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dbmanager.DBManager;

public class SearchResultServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DBManager dbManager = new DBManager();
		String sourceRoute = req.getParameter("sourceRoute");
		String destinationRoute = req.getParameter("destinationRoute");
		String date = req.getParameter("deptDate");
		
		List<Map<String, Object>> getFlight = dbManager.searchFlightResult(sourceRoute, destinationRoute, date);
		if(getFlight != null && !getFlight.isEmpty()) {
			req.setAttribute("flightDetails", getFlight);
			RequestDispatcher rd = req.getRequestDispatcher("SearchFlightResult.jsp");
			rd.forward(req, resp);
		}
		else {
			resp.getWriter().write("<html><body>No Flights Found!</body><html/>");
		}
	}
}
