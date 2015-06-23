package com.wtyt.util.interceptor;

import java.util.Map;

import org.apache.log4j.Logger;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.wtyt.util.publicclass.Constants;

public class CheckIpInterceptor extends AbstractInterceptor{

	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(CheckIpInterceptor.class);

	public String intercept(ActionInvocation arg0) throws Exception {
		logger.info("进入拦截器");
		logger.info(arg0.invoke());
		logger.info(arg0.getInvocationContext());
		if(!arg0.getInvocationContext().getName().equals("ClientLogon") ){
			Map<String,Object> session = arg0.getInvocationContext().getSession();
			if (session.get(Constants.USER_KEY) == null) {
				logger.info("session失效，跳转到登录页面!");
				return "index";
			}
		}
		String res = arg0.invoke(); 
		logger.info("离开拦截器");
		return res;
	}

}
