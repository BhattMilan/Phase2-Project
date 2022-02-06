package com;

import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.dbmanager.DBManager;

public class AddRouteServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		Map<String, String> params = getParameters(req, res);
		DBManager dbManager = new DBManager();
		String flightNumber = params.get("flightNumber");
		boolean isFlightExist = dbManager.isFlightNumberExist(flightNumber);
		boolean isRouteExist = dbManager.isRouteAvailable(params.get("from"), params.get("to"), params.get("date"));
		
		if(isFlightExist) {
			RequestDispatcher rd = req.getRequestDispatcher("FlightExist.html");
			rd.forward(req, res);
		}
		else if(isRouteExist) {
			System.out.println("This Route is already exist");
		}
		else {
			int isRecInserted = dbManager.addRoute(params);
			if(isRecInserted == 1) {
				RequestDispatcher rd = req.getRequestDispatcher("AddedRouteSuccessfully.html");
				rd.forward(req, res);
			}
			else {
				res.getWriter().write("<html><body>Having some issue in adding route, please try again.</body></html>");
			}
		}
	}

	private Map<String, String> getParameters(HttpServletRequest req, HttpServletResponse res){
		Map<String, String> params = new HashMap<>();
		params.put("from", req.getParameter("addSource"));
		params.put("to", req.getParameter("addDest"));
		params.put("date", req.getParameter("addDate"));
		params.put("flightNumber", req.getParameter("flightNumber"));
		params.put("flightName", req.getParameter("flightName"));
		params.put("price", req.getParameter("price"));
		params.put("departureTime", req.getParameter("departureTime"));
		params.put("arrivalTime", req.getParameter("arrivalTime"));
		params.put("availableSeats", req.getParameter("availableSeats"));
		return params;
	}
}
