package com;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if(session != null) {
			session.removeAttribute("firstName");
			session.removeAttribute("lastName");
			session.removeAttribute("email");
			session.invalidate();
			resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			RequestDispatcher rd = req.getRequestDispatcher("login.html");
			rd.forward(req, resp);
		}
	}
}
