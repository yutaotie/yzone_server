package com.wtyt.yzone.cloudpush;

/**
 * 绑定的信息
 * @author Administrator
 *
 */
public class DeviceInfo {
	
	private String bindName;//
	private long bindTime;//
	private String deviceId;
	private int deviceType;
	private long timestamp;
	private long expire;
	
	private long channelId ;
	private String userId ;
	private int status ;
	
	/**
	 * @return the bindName
	 */
	public String getBindName() {
		return bindName;
	}
	/**
	 * @param bindName the bindName to set
	 */
	public void setBindName(String bindName) {
		this.bindName = bindName;
	}
	/**
	 * @return the bindTime
	 */
	public long getBindTime() {
		return bindTime;
	}
	/**
	 * @param bindTime the bindTime to set
	 */
	public void setBindTime(long bindTime) {
		this.bindTime = bindTime;
	}
	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}
	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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
	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return the expire
	 */
	public long getExpire() {
		return expire;
	}
	/**
	 * @param expire the expire to set
	 */
	public void setExpire(long expire) {
		this.expire = expire;
	}
	/**
	 * @return the channelId
	 */
	public long getChannelId() {
		return channelId;
	}
	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	

}
