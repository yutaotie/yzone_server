package com.wtyt.yzone.cloudpush.baidu;

import com.wtyt.yzone.cloudpush.face.IdentityInfo;

public class BaiduIdentityInfo implements IdentityInfo {
	
    private  String apiKey = Constants.API_KEY;
	
	
    private  String secretKey = Constants.SECRET_KEY;
	
	
    private  int  deviceType  = Constants.BD_DEVICE_TYPE_ANDROID;//安卓设备类型
	


	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}


	/**
	 * @param apiKey the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}


	/**
	 * @return the secretKey
	 */
	public String getSecretKey() {
		return secretKey;
	}


	/**
	 * @param secretKey the secretKey to set
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}


	/**
	 * @return the deviceType
	 */
	public int getDeviceType() {
		return deviceType;
	}


	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}


	

}
