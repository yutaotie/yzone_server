package com.wtyt.yzone.locate.test;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;



public class LocateProxyHandler implements InvocationHandler    {

	public Object proxied;
	
	public LocateProxyHandler( Object proxied )   
	  {   
	    this.proxied = proxied;   
	  }  
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {	
		System.out.println("我执行了什么什么");
		String result = (String) method.invoke(proxied, args);
		System.out.println(result);
		System.out.println("什么什么我执行了");
		return result;
	}

	

}
