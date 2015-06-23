package com.wtyt.util.base;


import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import com.opensymphony.xwork2.ActionSupport;


public class BaseAction extends ActionSupport implements SessionAware,
		ApplicationAware, ServletRequestAware, ServletResponseAware {
	// Action基类
	private static final long serialVersionUID = 1L;
	protected Map<String, Object> session;
	protected Map<String, Object> application;
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}

	public Map<String, Object> getApplication() {
		return application;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletResponse getServletResponse() {
		return response;
	}
	
	 

}
