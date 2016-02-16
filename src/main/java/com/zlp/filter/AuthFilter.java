package com.zlp.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zlp.log.LoggerFactory;
import com.zlp.entity.Constant;

public class AuthFilter implements Filter {
	
	private static Logger log = LoggerFactory.getLogger(AuthFilter.class);
	
	private final static String fileName = "nofilterurl";
	private static Map<String, String> noFilterUrl = new HashMap<>();

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		log.log(Level.INFO, "filter destroy!");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Object sessionUser = httpRequest.getSession().getAttribute(Constant.SESSIONUSER);
		
		String url = httpRequest.getPathInfo();
		if(url == null) url = httpRequest.getServletPath();
		final String method = httpRequest.getMethod();
		log.info("request url:" + url);
		log.info("request method:" + method);
		boolean isSecureUrl = false;
		for(Map.Entry<String, String> urls : noFilterUrl.entrySet()) {
			if(!url.startsWith(urls.getKey())) continue;
			if(urls.getValue().indexOf(method) < 0) continue;
			isSecureUrl = true;
			break;
		}
		
		if(sessionUser != null || isSecureUrl) {
			chain.doFilter(request, response);
		} else {
			if(method.equalsIgnoreCase("POST")) {
				request.getRequestDispatcher("/login/").forward(request, response);
				return;
			}
			String loginAction = httpRequest.getContextPath()+"/login/";
			HttpServletResponse httpResponse = (HttpServletResponse)response;
			httpResponse.sendRedirect(loginAction);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		log.log(Level.INFO, "custom auth filter init!");
		ResourceBundle rb = ResourceBundle.getBundle(fileName);
		Iterator<String> urls = rb.keySet().iterator();
		while(urls != null && urls.hasNext()) {
			String key = urls.next();
			String val = rb.getString(key);
			log.info(key + ":" + val);
			noFilterUrl.put(key, val);
		}
	}

}
