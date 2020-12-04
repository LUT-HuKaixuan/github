package com.lut.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoginFilter implements Filter {

	public void destroy() {
		
	}

	public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain)
					throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		Object user = request.getSession().getAttribute("sessionUser");
		if(user==null){
			request.setAttribute("code", "error");
			request.setAttribute("msg", "您还未登录！");
			request.getRequestDispatcher("/jsps/msg.jsp").forward(request, res);
		}else{
			chain.doFilter(request, res);
		}
	}
	
	public void init(FilterConfig config) throws ServletException {
		
	}

}
