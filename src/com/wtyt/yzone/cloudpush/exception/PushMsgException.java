package com.wtyt.yzone.cloudpush.exception;

@SuppressWarnings("serial")
public class PushMsgException extends Exception {
	
	/**
	 * 无参数
	 */
	PushMsgException(){
	}
	
	
	/**
	 * 返回具体的异常
	 * @param msg
	 * @param cause
	 */
	public PushMsgException(String msg,Throwable cause){
		super(msg,cause);
	}
	
	/**
	 * 返回具体的异常
	 * @param msg
	 * @param cause
	 */
	public PushMsgException(String msg){
		super(msg);
	}

}
