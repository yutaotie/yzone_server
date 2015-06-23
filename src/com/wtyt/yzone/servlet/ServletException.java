package com.wtyt.yzone.servlet;

/**
 * 服务器接口异常
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class ServletException extends Exception {
	
	
	/**
	 * 无参数
	 */
	ServletException(){
	}
	
	
	/**
	 * 返回具体的异常
	 * @param msg
	 * @param cause
	 */
	public ServletException(String msg,Throwable cause){
		super(msg,cause);
	}
	
	/**
	 * 返回具体的异常
	 * @param msg
	 * @param cause
	 */
	public ServletException(String msg){
		super(msg);
	}

}
