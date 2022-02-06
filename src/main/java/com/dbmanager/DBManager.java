package com.dbmanager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DBManager {

	final String URL = "jdbc:sqlserver://TR-57P2Y33:1501;databaseName=Fly-AwayTesting";
	final String DB_USER = "sa";
	final String DB_PASS = "Admin123";

	public List<Map<String, Object>> searchFlightResult(String sourceRoute, String destinationRoute, String date) {
		Map<String, String> searchFlightRes = null;
		List<Map<String, Object>> searchFlightResult = new ArrayList<>();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = 	DriverManager.getConnection(URL, DB_USER, DB_PASS);
			PreparedStatement ps = conn.prepareStatement("SELECT r.source, r.destination, r.availableDate,\r\n"
					+ "f.flightNumber, f.flightName, f.routeID, f.price, f.departureTime, f.arrivalTime,\r\n"
					+ "f.availableSeats FROM routes r\r\n"
					+ "INNER JOIN flights f ON f.routeID = r.routeID \r\n"
					+ "WHERE source=? AND destination=? AND availableDate=?");
			ps.setString(1, sourceRoute);
			ps.setString(2, destinationRoute);
			ps.setString(3, date);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next()) {
				Map<String, Object> columns = new LinkedHashMap<>();
				int colCount = rsmd.getColumnCount();
				for (int i = 1; i <= colCount; i++) {
					columns.put(rsmd.getColumnLabel(i), rs.getObject(i));
				}
				searchFlightResult.add(columns);
//				searchFlightRes = new LinkedHashMap<>();
//				searchFlightRes.put("source", rs.getString("source"));
//				searchFlightRes.put("destination", rs.getString("destination"));
//				searchFlightRes.put("date", rs.getDate("availableDate").toString());
//				searchFlightRes.put("fNumber", rs.getString("flightNumber"));
//				searchFlightRes.put("fName", rs.getString("flightName"));
//				searchFlightRes.put("price", rs.getBigDecimal("price").toString());
//				searchFlightRes.put("departureTime", rs.getTime("departureTime").toString());
//				searchFlightRes.put("arrivalTime", rs.getTime("arrivalTime").toString());
//				searchFlightRes.put("avaSeats", Integer.toString(rs.getInt("availableSeats")));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return searchFlightResult;
	}

	public Connection connectToDB() {
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = 	DriverManager.getConnection(URL, DB_USER, DB_PASS);
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public Map<String, String> getUserDetails(String email, String pass) {
		Connection conn = connectToDB();
		ResultSet rs = null;
		Map<String, String> userDetails = null;
		if(conn != null) {
			try {
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE email=?");
				ps.setString(1, email);
				rs = ps.executeQuery();
				if(rs.next()) {
					if(rs.getString("password").equals(pass)) {
						userDetails = new HashMap<>();
						userDetails.put("firstName", rs.getString("firstName"));
						userDetails.put("lastName", rs.getString("lastName"));
						userDetails.put("email", rs.getString("email"));
						userDetails.put("gender", rs.getString("gender"));
						userDetails.put("age", rs.getString("age"));
						userDetails.put("mobNo", rs.getString("mobNo"));
						userDetails.put("isSystemAdmin", String.valueOf(rs.getInt("isSystemAdmin")));
					}
				}
				else {
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userDetails;
	}

	public int saveRegiUser(Map<String, String> paramList) {
		Connection conn = connectToDB();
		int recordInserted = 0;
		boolean emailExist = isEmailExist(paramList.get("email"));
		
		if(emailExist) {
			return recordInserted = 3;
		}
		else {
			try {
				PreparedStatement ps = conn.prepareStatement("INSERT INTO users VALUES(?,?,?,?,?,?,?,?)");
				ps.setString(1, paramList.get("firstName"));
				ps.setString(2, paramList.get("lastName"));
				ps.setString(3, paramList.get("email"));
				ps.setString(4, paramList.get("password"));
				ps.setString(5, (paramList.get("gender")));
				ps.setInt(6, Integer.parseInt(paramList.get("age")));
				ps.setBigDecimal(7, new BigDecimal (paramList.get("mobNo")));
				ps.setInt(8, 0);
				recordInserted = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return recordInserted;
		}
	}

	public boolean bookTicket(Map<String, String> formDetails, List<Map<String, Object>> flightDetails) {
		Connection conn = connectToDB();
		ResultSet rs = null;
		int paxNoOfForm = Integer.parseInt(formDetails.get("noPax"));
		int avaSeats = 0;
		Map<String, Object> map = null;
		for(int i=0; i<flightDetails.size(); i++) {
			map = (LinkedHashMap<String, Object>) flightDetails.get(i);
		}
		String flightNumber = (String) map.get("flightNumber");
		boolean isTicketBooked = false;

		try {
			PreparedStatement ps = conn.prepareStatement("SELECT availableSeats FROM flights WHERE flightNumber=?");
			ps.setString(1, flightNumber);
			rs = ps.executeQuery();
			if(rs.next()) {
				avaSeats = rs.getInt("availableSeats");
				if(avaSeats - paxNoOfForm >= 0) {
					isTicketBooked = bookTicketProcedure(conn, formDetails, flightDetails, avaSeats, paxNoOfForm);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isTicketBooked;
	}

	private boolean bookTicketProcedure(Connection conn, Map<String, String> formDetails, List<Map<String, Object>> flightDetails, int avaSeats, int paxNoOfForm) {
		int isRecordInserted = 0;
		int isAvaSeatsUpdated = 0;
		int totalAvaiSeats = 0;
		boolean isTicketBooked = false;
		Map<String, Object> map = null;
		for(int i=0; i<flightDetails.size(); i++) {
			map = (LinkedHashMap<String, Object>) flightDetails.get(i);
		}
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO booking VALUES ("
					+ "(SELECT flightNumber from flights WHERE flightNumber=?),"
					+ "(SELECT userID FROM users WHERE email=?),"
					+ "?,"
					+ "?)");

			ps.setString(1, (String) map.get("flightNumber"));
			ps.setString(2, formDetails.get("email"));
			ps.setString(3, map.get("availableDate").toString());
			ps.setInt(4, Integer.parseInt(formDetails.get("noPax")));
			isRecordInserted = ps.executeUpdate();
			if(isRecordInserted == 1) {
				totalAvaiSeats = avaSeats - paxNoOfForm;
				ps = conn.prepareStatement("UPDATE flights SET availableSeats=? WHERE flightNumber=?");
				ps.setInt(1, totalAvaiSeats);
				ps.setString(2, (String) map.get("flightNumber"));
				isAvaSeatsUpdated = ps.executeUpdate();
			}
			if(isAvaSeatsUpdated == 1) {
				isTicketBooked = true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return isTicketBooked;
	}
	
	public int getSeatsAvailability(String flightNumber) {
		ResultSet rs = null;
		int avaSeats = 0;
		Connection conn = connectToDB();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT availableSeats FROM flights WHERE flightNumber=?");
			ps.setString(1, flightNumber);
			rs = ps.executeQuery();
			while(rs.next()) {
				avaSeats = Integer.parseInt(rs.getString("availableSeats"));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return avaSeats;
	}

	private boolean isEmailExist(String email) {
		Connection conn = connectToDB();
		ResultSet rs = null;
		boolean emailExist = false;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT email FROM users WHERE email=?");
			ps.setString(1, email);
			rs = ps.executeQuery();
			if(rs.next()) {
				emailExist = true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return emailExist;
	}

	public List<Map<String, Object>> getRoutes() {
		Connection conn = connectToDB();
		ResultSet rs = null;
		List<Map<String, Object>> routeDetails = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM routes");
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			while(rs.next()) {
				Map<String, Object> columns = new LinkedHashMap<>();
				for (int i = 1; i <= colCount; i++) {
					columns.put(rsmd.getColumnLabel(i), rs.getObject(i));
				}
				routeDetails.add(columns);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return routeDetails;
	}

	public int addRoute(Map<String, String> params) {
		Connection conn = connectToDB();
		int isRecInsertedInRoute = 0;
		int isRecInsertedInFlight = 0;
		
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO routes VALUES(?,?,?)");
			ps.setString(1, params.get("from"));
			ps.setString(2, params.get("to"));
			ps.setDate(3, Date.valueOf(params.get("date")));
			isRecInsertedInRoute = ps.executeUpdate();
			if(isRecInsertedInRoute == 1) {
				String insertQuery = "INSERT INTO flights VALUES(\r\n"
						+ "?,\r\n"
						+ "?,\r\n"
						+ "(SELECT routes.routeID FROM routes WHERE source=? AND destination=? AND availableDate=?),\r\n"
						+ "?,\r\n"
						+ "?,\r\n"
						+ "?,\r\n"
						+ "?\r\n"
						+ ")";
				PreparedStatement psFlight = conn.prepareStatement(insertQuery);
				psFlight.setString(1, params.get("flightNumber"));
				psFlight.setString(2, params.get("flightName"));
				psFlight.setString(3, params.get("from"));
				psFlight.setString(4, params.get("to"));
				psFlight.setString(5, params.get("date"));
				psFlight.setBigDecimal(6, new BigDecimal(params.get("price")));
				psFlight.setString(7, params.get("departureTime"));
				psFlight.setString(8, params.get("arrivalTime"));
				psFlight.setInt(9, Integer.parseInt(params.get("availableSeats")));
				isRecInsertedInFlight = psFlight.executeUpdate();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return isRecInsertedInFlight;
	}

	public int deleteRoute(String routeID) {
		Connection conn = connectToDB();
		int isRecDeletedFromFlight = 0;
		int isRecDeletedFromRoute = 0;
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM flights WHERE routeID=?");
			ps.setInt(1, Integer.parseInt(routeID));
			isRecDeletedFromFlight = ps.executeUpdate();
			if(isRecDeletedFromFlight == 1) {
				PreparedStatement psRoute = conn.prepareStatement("DELETE FROM routes WHERE routeID=?");
				psRoute.setInt(1, Integer.parseInt(routeID));
				isRecDeletedFromRoute = psRoute.executeUpdate();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return isRecDeletedFromRoute;
	}

	public boolean isFlightNumberExist(String flightNumber) {
		Connection conn = connectToDB();
		boolean isFlightExist = false;
		String fNumber = null;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT flightNumber FROM flights WHERE flightNumber=?");
			ps.setString(1, flightNumber);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				fNumber = rs.getString("flightNumber");
				if(flightNumber.equals(fNumber)) {
					isFlightExist = true;
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return isFlightExist;
	}

	public boolean isRouteAvailable(String source, String destination, String avaiDate) {
		Connection conn = connectToDB();
		boolean isRouteExist = false;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM routes WHERE source=? AND destination=? AND availableDate=?");
			ps.setString(1, source);
			ps.setString(2, destination);
			ps.setDate(3, Date.valueOf(avaiDate));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				isRouteExist = true;
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return isRouteExist;
	}

	public List<Map<String, Object>> getFlights() {
		Connection conn = connectToDB();
		ResultSet rs = null;
		List<Map<String, Object>> flightDetails = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM flights");
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			while(rs.next()) {
				Map<String, Object> columns = new LinkedHashMap<>();
				for (int i = 1; i <= colCount; i++) {
					columns.put(rsmd.getColumnLabel(i), rs.getObject(i));
				}
				flightDetails.add(columns);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return flightDetails;
	}
}
