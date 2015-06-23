package com.wtyt.yzone.servlet;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.wtyt.yzone.util.YzoneUtil;


public class CommonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger log = Logger.getLogger(CommonServlet.class);
	
	/**
	 * post入口
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		log.info("进入公用报文处理入口");		
		Object returnObject = null;
		String messageInfo = "成功";
		try {
			String ip = YzoneUtil.getRemortIP(request);
			/*if(!YzoneUtil.isLegalIp(ip,System.getProperty("YZONE_IP_ALLOWED"))){
				log.info("请求的IP为："+ip);
				throw new ServletException("IP鉴权失败");				
			}		*/	
			String requestInfo = parseClientStream(request);//获取请求传入的参数
			returnObject = 	CommonSerlvetHandle.doCommonSerlvetHandle(requestInfo);
		}catch(ServletException e){
			log.info("warning!发生服务器异常");	
			messageInfo = e.getMessage() ;
			YzoneUtil.printExceptionInfo(e);
		}catch(Exception e){
			log.info("warning!发生系统异常");	
			messageInfo = "系统异常";
			YzoneUtil.printExceptionInfo(e);
		} finally {		
			if(returnObject==null){				
				returnObject  = getErrorReturnPackets(messageInfo);
			}		
			responseOut(response,YzoneUtil.URLEncode2UTF8TwoTimes(returnObject.toString()));
			log.info("离开进入公用报文处理入口");			
		}		
		
	}

	/**
	 * 将方法返回的其他json值拼装返回
	 * @param jsonResult
	 * @param returnObject
	 */
	private String getErrorReturnPackets(String messageInfo) {
		
		  return messageInfo;  
	}


	/**
	 * 获取传入的参数
	 * @throws ServletException 
	 * */
	public static String parseClientStream(HttpServletRequest request) throws ServletException
			 {
		byte[] b = null;
		ByteArrayOutputStream baos = null;	
		InputStream is = null;
		try{			
			try {
				is = request.getInputStream();			
				byte[] buf = new byte[1024];
				int num = -1;
				baos = new ByteArrayOutputStream();
				while ((num = is.read(buf, 0, buf.length)) != -1) {
					baos.write(buf, 0, num);
				}
				b = baos.toByteArray();
				baos.flush();
			} finally {
				if (baos != null) {
					baos.close();
				}
				if (is != null) {
					is.close();
				}
			}
		}catch(Exception e){
			throw new ServletException("获取传入的参数异常",e);					
		}		
		return new String(b);
	}

	
	
	/**
	 * 返回给调用者
	 * 
	 * @throws IOException
	 * 
	 */
	public static void responseOut(HttpServletResponse response, String result)	{
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(result);
		} catch (Exception e) {			
			YzoneUtil.printExceptionInfo(e);
		}finally{			
			if(null!=out)out.close();			
		}
		
	}
	
}