package com.wtyt.yzone.locate.test;

import java.lang.reflect.Proxy;

public class DynamicProxy {
	
	public static void main(String[] args) {
		Locate lvo = new LocateVersonOne();
		Locate two = new LocateVersonTwo();
		Locate loc =(Locate) Proxy.newProxyInstance(LocateVersonOne.class.getClassLoader(), new Class[]{Locate.class}, new LocateProxyHandler(lvo));
		loc.locate();
		loc.laugh();
	}

}

