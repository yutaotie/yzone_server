package com.wtyt.yzone.locate.handle;

import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;

import com.wtyt.util.init.StartUp;
import com.wtyt.yzone.locate.bean.LocateBean;
import com.wtyt.yzone.locate.service.LocateService;
import com.wtyt.yzone.servlet.CommonSerlvetHandle;
import com.wtyt.yzone.servlet.ServletException;

/**
 * 定位数据处理
 * @author Administrator
 *
 */
public class LocateHandle {

	private static LocateService locateService = (LocateService) StartUp.factory.getBean("locateService");
	
	public void locate(JSONObject obj) throws ServletException{		
		try {
			locateSecond(obj);
		} catch (ServletException e) {
			throw(e);
		} catch (JSONException e) {
			throw new ServletException("json异常",e);
		} catch (SQLException e) {
			throw new ServletException("数据库异常",e);
		}catch (Exception e) {
			throw new ServletException("未知异常",e);
		}
	}
	
	/**
	 * 
	 * @param obj
	 * @throws ServletException
	 * @throws JSONException
	 * @throws SQLException
	 */
	public void locateSecond(JSONObject obj) throws ServletException, JSONException, SQLException{
		String mobileNo = CommonSerlvetHandle.getJsonObjectValueCanNull(obj,"mobile_no");
		String areaX =  CommonSerlvetHandle.getJsonObjectValueCanNull(obj,"area_x");		
		String areaY =  CommonSerlvetHandle.getJsonObjectValueCanNull(obj,"area_y");	
		String locTime =  CommonSerlvetHandle.getJsonObjectValueCanNull(obj,"loc_time");	
		String areaName =  CommonSerlvetHandle.getJsonObjectValueCanNull(obj,"area_name");	
		LocateBean locBean = new LocateBean();
		locBean.setAreaName(areaName);
		locBean.setAreaX(areaX);
		locBean.setAreaY(areaY);
		locBean.setLocTime(locTime);
		locBean.setMobileNo(mobileNo);
		locateService.locateSaveDataInfo(locBean);
	
	}
	
	
}
