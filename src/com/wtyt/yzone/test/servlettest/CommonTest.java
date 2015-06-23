package com.wtyt.yzone.test.servlettest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import com.wtyt.yzone.util.YzoneUtil;



public class CommonTest {
	
	private static Logger LOG = Logger.getLogger(CommonTest.class);
	
	/**
	 * post方式访问接口
	 * @param url
	 * @param data
	 * @return
	 */
	public static String sendPost(String url,String data){
		//访问的结果
		String postResult = "";
		//访问的返回的内容
		String postDetailInfo = "";
		HttpURLConnection con = null;
		BufferedReader in = null;
		String result = "";
		try{		
			URL httpUrl = new URL(url);		
			con = (HttpURLConnection) httpUrl.openConnection();			
			con.setRequestMethod("POST");
			con.setDoOutput(true);		
			PrintWriter  out = new PrintWriter(con.getOutputStream());  
			out.println(data);
			out.flush();
			out.close();
			int responseCode = con.getResponseCode();		
			if(HttpURLConnection.HTTP_OK == responseCode ){
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
				postResult = "SUCCESS";
				postDetailInfo = result;
			}else{
				postResult = "ERROR";
				postDetailInfo = "HTTP REQUEST ERROR  [ERRORCODE : "+responseCode + "]";
			}		
		}catch(Exception e){
			postResult = "ERROR";
			postDetailInfo = "OCCUR EXCEPTION [EXCEPTION INFO : "+e.getMessage()+"]";
		}finally{
			if (in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}			
			if (con != null){
				con.disconnect();
			}
		}
		JSONObject jsob = new JSONObject();
		try {
			jsob.append("postResult", postResult);
			jsob.append("postDetailInfo", postDetailInfo);
		} catch (JSONException e) {
			System.out.println("Warning catch JSONException!");
		}
		return jsob.toString();
	}

	
	/**
	 * 本地测试地址
	 * @return
	 */
	public static String getUrlLocalHost(){		
		return "http://218.206.70.235:20011/YzoneServer/commonServlet"; //"http://192.168.1.140:18080/YzoneServer/commonServlet";
	}
	
	
	/**
	 * 访问网络接口
	 * 
	 * @param clientUrl
	 * @param params
	 * @return
	 */
	public static String client(String clientUrl, String params) {
		LOG.info("访问接口,传入参数值为:" + params);
		System.out.println("访问接口,传入参数值为:" + params);
		PostMethod post = new PostMethod(clientUrl);
		post.setRequestHeader("Content-Type", "text/html; charset=UTF-8");
		post.setRequestBody(params);
		// 创建client对象
		HttpClient client = new HttpClient();
		client.setConnectionTimeout(30000);
		client.setTimeout(30000);
		String result = "";
		try {
			client.executeMethod(post);
		    InputStream resStream = post.getResponseBodyAsStream(); 
			BufferedReader br = new BufferedReader(new InputStreamReader(resStream));  
		    StringBuffer resBuffer = new StringBuffer();  
		        String resTemp = "";  
		        while((resTemp = br.readLine()) != null){  
		            resBuffer.append(resTemp);  
		        }  
		        result = resBuffer.toString(); 			  
			if (result.length() == 0) {
				result = null;
			}
		} catch (Exception e) {
			YzoneUtil.printExceptionInfo(e);
			LOG.info("访问网关异常" + e.toString(), e);
			result = null;
		} finally {
			post.releaseConnection();
		}
		System.out.println("访问接口,返回的参数值为:" + result);
		LOG.info("访问接口,返回的参数值为:" + result);
		return result;
	}
	
}
