package com.wtyt.util.init;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;





import com.wtyt.packets.PacketsException;
import com.wtyt.packets.ParsePacketsXml;
import com.wtyt.util.init.dao.StartUpDao;
import com.wtyt.util.publicclass.Constants;
import com.wtyt.util.publicclass.SystemParameterBean;
import com.zhou.work.security.DESFunc;
 

public class StartUp extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(StartUp.class);

	private static StringBuffer sWebContext = null;
	// 系统参数列表
	private static List<SystemParameterBean> sysParameters;
	
	private static StartUpDao startUpDao;
	
	private static final String key = "EAAE8A86B904044A";


	// 这里定义的BeanFactory可以全局灵活调用
	public final static BeanFactory factory;

	static {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml","packets-spring.xml"});
		factory = (BeanFactory) context;
		
		startUpDao = (StartUpDao) factory.getBean("startUpDao");
		 
	}
	
/*	public static SMSDAO dao = null; //全局变量，可以在项目的任何地方调用
	 *//**
	   * 初始化短信发送接口
	   *//*
	  public void InitSMS() {
	    dao = new SMSDAO(System.getProperty("ORACLE_IP"),
	                     System.getProperty("ORACLE_PORT"),
	                     System.getProperty("ORACLE_SID"),
	                     System.getProperty("ORACLE_USERNAME"),
	                     System.getProperty("ORACLE_PWD"),
	                     System.getProperty("ORACLE_DRIVER"));
	    logger.info("初始化短信接口成功");
	  }*/

	private void initLog4j() {
		System.out.println("======系统初始化=====");
		String log4jConfigFile = Constants.LOG4J_CONFIG_FILE;

		sWebContext = new StringBuffer(getServletContext().getRealPath("/"));
		System.out.println("绝对路径:" + sWebContext.toString());
		System.setProperty("AbsolutePath", sWebContext.toString());

		// 获取系统类型
		String os = System.getProperty("os.name");
		if (os.indexOf("Windows") >= 0) {
			log4jConfigFile = sWebContext.toString() + "WEB-INF\\"
					+ log4jConfigFile;
		} else {
			log4jConfigFile = sWebContext.toString() + "WEB-INF//"
					+ log4jConfigFile;
		}

		// 配置Log4j
		DOMConfigurator.configure(log4jConfigFile);
		System.out.println("配置log4j成功");

	}

	/**
	 * 初始化系统参数
	 */
	private static void initSystemParameters() {
		logger.info("进入initSystemParameters");
		try {
			sysParameters = startUpDao.getSysParameters();
			for (int i = 0; i < sysParameters.size(); i++) {
				SystemParameterBean param = (SystemParameterBean) sysParameters.get(i);
				if("ORACLE_PWD".equals(param.getParam_name())||"ORACLE_USERNAME".equals(param.getParam_name())){
					System.setProperty(param.getParam_name(),DESFunc.decryptDES(param.getParam_value(),StartUp.key));
				}else{
					System.setProperty(param.getParam_name(), param.getParam_value());
				}
			}
			logger.info("initSystemParameters成功");
		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error("初始化initSystemParameters失败,数据库异常！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("初始化initSystemParameters失败");
			System.exit(0);
		}
		logger.info("离开initSystemParameters");
	}

	
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		initLog4j();
		initPacketsInfo();
		//initSystemParameters();		
		logger.info("======系统初始化完成=====");
	}
	
	/**
	 * 初始化报文信息
	 */
	public  void initPacketsInfo(){
			try {
					ParsePacketsXml.produceXmlMapInfo(new File(ParsePacketsXml.getRootPath()+"packets.xml"));
					System.out.println(ParsePacketsXml.getXmlClassMap());
					System.out.println(ParsePacketsXml.getXmlMethodMap());
			} catch (FileNotFoundException e) {	
				 logger.info("找不到文件异常");
				 e.printStackTrace();
			} catch (PacketsException e) {	
				 logger.info(e.getMessage());
					e.printStackTrace();
			} catch (Exception e) {				
					e.printStackTrace();
			}
	}

}
