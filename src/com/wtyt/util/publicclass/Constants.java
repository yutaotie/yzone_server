package com.wtyt.util.publicclass;

public final class Constants {
	
	/*
	 * 是否对json解密，用于本地测试
	 */
	public static final boolean isDecrypt = false;
	/**
	 * 分页显示行数
	 */
	public static final int PAGE_SIZE = 20;
	/**
	 * LOG4J初始化参数
	 */
	public static final String CONFIG_DIR = "GpsWebServer//WEB-INF//";
	public static final String LOG4J_CONFIG_FILE = "log_config.xml";
	public static final String WEB_URI = "GpsWebServer";
	/**
	 * LOG4J XML
	 */
	public static final String CONFIGURATION_ITEM = "item";
	public static final String CONFIGURATION_ITEM_NAME = "name";
	public static final String CONFIGURATION_ITEM_VALUE = "value";
	/**
	 * 系统常量
	 */
	public static final String USER_KEY = "sessionUserBean";
	public static final String ORG_KEY = "sessionOrgBean";
	public static final String INDEX = "index.jsp";
}
