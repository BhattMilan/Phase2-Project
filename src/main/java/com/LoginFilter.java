package com;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		if(session.getAttribute("firstName") == null || session == null) {
			RequestDispatcher rd = req.getRequestDispatcher("index.html");
			rd.forward(req, res);
		}
		else {
			res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	        res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	        res.setDateHeader("Expires", 0);
			chain.doFilter(request, response);
		}
	}

}
