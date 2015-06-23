package com.wtyt.yzone.cloudpush.baidu;

import com.wtyt.yzone.cloudpush.face.MessageInfo;

public class BaiduMessageInfo implements MessageInfo {
	
	private String content;
	
	private String title;
	
	private String tagName;
	
	private String userId ;
	
	private long channelId;
	
	
		
	


	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}


	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}


	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}


	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}


	/**
	 * @return the tagName
	 */
	public String getTagName() {
		return tagName;
	}


	/**
	 * @param tagName the tagName to set
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
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

}
