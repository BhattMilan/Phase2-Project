package com;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;

public class RegistrationValidationFilter implements Filter{

	public void init() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		String pass = request.getParameter("pass");
		String confPass = request.getParameter("confPass");
		String passwordConst = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$";
		Pattern p = Pattern.compile(passwordConst);
		Matcher m = p.matcher(pass);
		if(pass !=null && confPass != null) {
			if(!m.matches()) {
				res.sendRedirect("RegPassConditionNotMatched.html");
			}
			else if(!pass.equals(confPass)) {
				res.sendRedirect("RegPassNotMatched.html");
			}
			else {
				chain.doFilter(request, response);
			}
		}
	}
}
