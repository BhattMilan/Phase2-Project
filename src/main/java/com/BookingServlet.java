package com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dbmanager.DBManager;

public class BookingServlet extends HttpServlet{

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		DBManager dbManager = new DBManager();
		String firstName = req.getParameter("firstNameText");
		String lastName = req.getParameter("lastNameText");
		String email = req.getParameter("emailText");
		String mobNo = req.getParameter("mobText");
		String noPax = req.getParameter("noOfPax");
		Map<String, String> formDetails = new HashMap<>();
		formDetails.put("firstName", firstName);
		formDetails.put("lastName", lastName);
		formDetails.put("email", email);
		formDetails.put("mobNo", mobNo);
		formDetails.put("noPax", noPax);
		
		List<Map<String, Object>> flightDetails = (List<Map<String, Object>>) req.getServletContext().getAttribute("map");
		List<String> getBookingInfoDetails = getBookingInfo(formDetails, flightDetails);
		Map<String, Object> map = null;
		for(int i=0; i<flightDetails.size(); i++) {
			map = (LinkedHashMap<String, Object>) flightDetails.get(i);
		}
		if(Integer.parseInt(noPax) <= (Integer)(map.get("availableSeats"))) {
			boolean isTicketBooked = dbManager.bookTicket(formDetails, flightDetails);
			if(isTicketBooked) {
				req.setAttribute("bookingInfoDetails", getBookingInfoDetails);
				RequestDispatcher rd = req.getRequestDispatcher("TicketBooked.jsp");
				rd.forward(req, res);
			}
			else {
				
			}
		}
		else {
			res.getWriter().write("<html><body><h3>Seats Not Available!</h3</body></html>");
		}
	}

	private double getTotalPrice(String noPax, String eachPrice) {
		double totalPax = Double.parseDouble(noPax);
		double priceForEachPax = Double.parseDouble(eachPrice);
		return totalPax * priceForEachPax;
	}

	private List<String> getBookingInfo(Map<String, String> formDetails, List<Map<String, Object>> flightDetails) {
		
		List<String> getBookingInfoDetails = new ArrayList<>();
		Map<String, Object> map = null;
		for(int i=0; i<flightDetails.size(); i++) {
			map = (LinkedHashMap<String, Object>) flightDetails.get(i);
		}
		double totalPrice = getTotalPrice(formDetails.get("noPax"), map.get("price").toString());
		getBookingInfoDetails.add(formDetails.get("firstName"));
		getBookingInfoDetails.add(formDetails.get("lastName"));
		getBookingInfoDetails.add(formDetails.get("email"));
		getBookingInfoDetails.add(formDetails.get("mobNo"));
		getBookingInfoDetails.add(formDetails.get("noPax"));
		
		getBookingInfoDetails.add((String) map.get("flightNumber"));
		getBookingInfoDetails.add((String) map.get("flightName"));
		getBookingInfoDetails.add((String) map.get("source"));
		getBookingInfoDetails.add((String) map.get("destination"));
		getBookingInfoDetails.add((String) map.get("departureTime"));
		getBookingInfoDetails.add((String) map.get("arrivalTime"));
		getBookingInfoDetails.add(map.get("price").toString());
		getBookingInfoDetails.add(Double.toString(totalPrice));
		
		return getBookingInfoDetails;
	}
}
