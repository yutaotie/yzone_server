package com.wtyt.yzone.servlet;



import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.wtyt.packets.ParsePacketsXml;
import com.wtyt.yzone.util.YzoneUtil;


/**
 * 公用报文处理入口
 * @author Administrator
 *
 */
public class CommonSerlvetHandle {
	
	private final static Logger log = Logger.getLogger(CommonSerlvetHandle.class);
	
	/**
	 * 执行处理方法
	 * @param requestParam
	 * @throws ServletException 
	 * @throws InvocationTargetException 
	 * @throws JSONException 
	 */
	public static Object doCommonSerlvetHandle(String requestParam) throws ServletException, InvocationTargetException{		
		JSONObject dataObj = null;
		try {
			dataObj = new JSONObject(YzoneUtil.URLDecode2UTF8(YzoneUtil.URLDecode2UTF8(requestParam)));
			String typeCode = dataObj.getString("id");
			String className = ParsePacketsXml.getXmlClassMap().get(typeCode);
			String methodName = ParsePacketsXml.getXmlMethodMap().get(typeCode);
			Class<?>  clasz = Class.forName(className);
			Object o = clasz.newInstance();			
			Method m = clasz.getMethod(methodName, JSONObject.class);
			Object object = m.invoke(o, dataObj);
			return object;
		} catch (JSONException e) {			
           throw new ServletException("解析传入的JSON异常",e);
		} catch (ClassNotFoundException e) {
		   throw new ServletException("找不到执行的类",e);
		} catch (NoSuchMethodException e) {
		   throw new ServletException("找不到要执行的方法",e);
		} catch (InstantiationException e) {
		   throw new ServletException("解析执行业务逻辑异常",e);
		} catch (IllegalAccessException e) {
		   throw new ServletException("解析执行业务逻辑异常",e);
		} catch (IllegalArgumentException e) {
		   throw new ServletException("解析执行业务逻辑异常",e);
		}catch (InvocationTargetException e) {	
			Throwable t = e.getTargetException();		
			String message = t.getMessage();			
           	throw new ServletException(message,t);
		} 
		
	}
	
	
	
	
	/**
	 * 通过json获取到value的值
	 * @param paramObj
	 * @param keyName
	 * @return
	 * @throws ServletException
	 */
	public static String getJsonObjectValue(JSONObject paramObj,String keyName) throws ServletException{
		String value = "";		
		try {
			value = paramObj.getString(keyName);
		} catch (JSONException e) {
			throw new ServletException("获取不到"+keyName+"的值",e);
		}			
		return value;
	}
	
	
	/**
	 * 通过json获取到value的值,可以是空值
	 * @param paramObj
	 * @param keyName
	 * @return
	 * @throws ServletException
	 */
	public static String getJsonObjectValueCanNull(JSONObject paramObj,String keyName) throws ServletException{
		String value = "";		
		try {
			value = paramObj.getString(keyName);
		} catch (Exception e) {
			log.info("获取不到"+keyName+"的值");
			value = "";
		}			
		return value;
	}
	
	
	/**
	 * 检查是否为空
	 */
	public static boolean checkNull(String param){		
		return  param == null || "".equals(param.trim()) || "null".equals(param.trim());
	}
	
	
	
}
