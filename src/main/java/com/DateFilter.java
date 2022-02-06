package com;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class DateFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

			String depDate = request.getParameter("deptDate");
			try {
				Date date = new SimpleDateFormat("yyyy-MM-DD").parse(depDate);
				LocalDate currentLocalDate = LocalDate.now();
				ZoneId sysTimeZone = ZoneId.systemDefault();
				ZonedDateTime zonedDateTime  = currentLocalDate.atStartOfDay(sysTimeZone);
				Date currentDate = Date.from(zonedDateTime.toInstant());
				if(currentDate.after(date)) {
					response.getWriter().write("<html><body>You Can't select past date</body></html>");
				}
				else {
					doFilter(request, response, chain);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
	}

}
