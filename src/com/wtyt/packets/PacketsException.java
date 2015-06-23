package com.wtyt.packets;

/**
 * 报文异常
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class PacketsException extends Exception {
	
	public PacketsException(){
		super();
	}
	
	public PacketsException(String message){
		super(message);
	}
	
	
	public PacketsException(String message,Throwable e){
		  super(message,e);
	}
	
}
