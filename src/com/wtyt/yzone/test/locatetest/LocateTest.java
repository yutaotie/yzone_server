package com.wtyt.yzone.test.locatetest;


import org.json.JSONException;
import org.json.JSONObject;

import com.wtyt.util.publicclass.edacoder.CommonCoderAgl;
import com.wtyt.yzone.test.servlettest.CommonTest;
import com.wtyt.yzone.util.YzoneUtil;




import junit.framework.TestCase;


public class LocateTest extends TestCase {
	
	public LocateTest(String arg0) {
		super(arg0);
	}
	
	  /** 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception{       

          super.setUp();
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception{
        super.tearDown();
    }

    
    
	public static void testLocateDataSave() throws JSONException{		
		//CommonTest.sendPost(CommonTest.getUrlLocalHost(), getParam());
	  	CommonTest.client(CommonTest.getUrlLocalHost(), getParamFee());
	}
	
	
	public static String getParamFee() throws JSONException{
		JSONObject jsob = new JSONObject();
		jsob.put("checkValue", CommonCoderAgl.MD5("feeBackSection86638przdhel"));
		jsob.put("id", "locatePacket");
		jsob.put("mobile_no", "18652002140");
		jsob.put("area_x", "20.58");
		jsob.put("area_y", "60.5");
		jsob.put("loc_time", "2015-06-10 13:49:30");
		jsob.put("area_name", "波哥是大神");
		return YzoneUtil.URLEncode2UTF8TwoTimes(jsob.toString());
	}
	
	

	
	
	public static void main(String[] args) throws JSONException {

		  testLocateDataSave();
		
	}
	
	/**
	 * 打印返回结果
	 */
	public static  String getResult(String clientResult){
		String result = "";
		String resultInfo = "";
		JSONObject json = null;
		try {
			json = new JSONObject(YzoneUtil.URLDecode2UTF8(YzoneUtil.URLDecode2UTF8(clientResult)));			
			result = json.getString("reCode");
			resultInfo = json.getString("reInfo");			
		} catch (JSONException e) {
			
		}		
		if("0".equals(result)){
			result = "成功";
			//System.out.println("异常信息为:"+resultInfo);
			System.out.println("成功信息为:"+resultInfo);
		}else if("1".equals(result)){
			result = "自定义异常";
			//System.out.println("异常信息为:"+resultInfo);
			System.out.println("异常信息为:"+resultInfo);
		}else{
			result = "系统异常";
			System.out.println("异常信息为:"+resultInfo);
		}
		System.out.println("接口返回的结果:"+result);
		return result;
	}

}
