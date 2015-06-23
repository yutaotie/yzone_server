package com.wtyt.yzone.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;



public class YzoneUtil {

  
  /**
   * 将错误信息以字符串形式打印出来
   * @param e
   */
	public static void printExceptionInfo(Exception e){
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw,true));
		System.out.println("--warning exception ！异常信息为：-"+sw.toString());
	}
	
	/**
	 * 对IP地址鉴权
	 * */
	public static boolean isLegalIp(String ip, String ipList) {
		String ss[] = ipList.split(",");
		for (int i = 0; i < ss.length; i++) {
			if (ss[i].equals(ip)) {
				return true;
			}
		}
		return false;
	}

	public static String[] getCurrentTimeAndBefore() throws ParseException {
		String[] times = new String[2];
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);

		String currentTime = cal.get(Calendar.YEAR) + "-" + (month + 1) + "-"
				+ cal.get(Calendar.DAY_OF_MONTH) + " 23";

		Date d = formate.parse(currentTime);
		times[0] = formate.format(d);

		currentTime = cal.get(Calendar.YEAR) + "-" + month + "-01" + " 00";
		d = formate.parse(currentTime);
		times[1] = formate.format(d);

		return times;
	}

	/**
	 *中文转码
	 * 
	 * @param str
	 * @return String
	 * @see java.net.URLEncoder.encode(String s, String enc)
	 */
	public static String URLEncode2UTF8(String str) {
		if (null == str)
			return null;
		String strRet = "";
		try {
			strRet = java.net.URLEncoder.encode(str, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRet;
	}

	public static String URLEncode2UTF8TwoTimes(String str) {
		return URLEncode2UTF8(URLEncode2UTF8(str));
	}

	public static String URLDecode2UTF8(String str) {
		if (null == str)
			return null;
		String strRet = "";
		try {
			strRet = java.net.URLDecoder.decode(str, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRet;
	}

	/**
	 * 以UTF-8编码encode参数的值
	 * 
	 * @param parameters
	 * @return
	 */
	public static String encodeParameters2GBK(String parameters) {
		if (null == parameters || "".equals(parameters))
			return parameters;

		String[] parArray = parameters.split("&");
		if (null == parArray || parArray.length == 0)
			return parameters;

		StringBuffer bufRet = new StringBuffer();
		for (int i = 0; i < parArray.length; i++) {
			String[] keyAndValue = parArray[i].split("=");
			if (null != keyAndValue && keyAndValue.length == 2) {
				String key = keyAndValue[0];
				String value = keyAndValue[1];
				String encodeValue = URLEncode2UTF8(value);
				addParameter(bufRet, key, encodeValue);
			} else {
				bufRet.append("&" + parArray[i]);
			}
		}

		return bufRet.toString();
	}

	/**
	 * 取出一个指定长度大小的随机正整数.
	 * 
	 * @param length
	 *            int 设定所取出随机数的长度。length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}

	/**
	 * 添加参数
	 * 
	 * @param buf
	 * @param parameterName
	 *            参数名
	 * @param parameterValue
	 *            参数值
	 * @return StringBuffer
	 */
	public static StringBuffer addParameter(StringBuffer buf,
			String parameterName, String parameterValue) {

		if ("".equals(buf.toString())) {
			buf.append(parameterName);
			buf.append("=");
			buf.append(parameterValue);
		} else {
			buf.append("&");
			buf.append(parameterName);
			buf.append("=");
			buf.append(parameterValue);
		}

		return buf;
	}

	/**
	 * 添加参数,若参数值不为空串,则添加。反之,不添加。
	 * 
	 * @param buf
	 * @param parameterName
	 * @param parameterValue
	 * @return StringBuffer
	 */
	public static StringBuffer addBusParameter(StringBuffer buf,
			String parameterName, String parameterValue) {
		if (null == parameterValue || "".equals(parameterValue)) {
			return buf;
		}

		if ("".equals(buf.toString())) {
			buf.append(parameterName);
			buf.append("=");
			buf.append(parameterValue);
		} else {
			buf.append("&");
			buf.append(parameterName);
			buf.append("=");
			buf.append(parameterValue);
		}
		return buf;
	}

	/**
	 * 跳转到显示的页面
	 * 
	 * @param url
	 *            显示的页面,以绝对地址出现
	 * @param name
	 *            参数名字
	 * @param value
	 *            参数值
	 * @return String 返回跳转的js字符串代码
	 */
	public static String gotoShow(String url, String name, String value) {
		String strScript = "<script language='javascript' type='text/javascript'>";
		strScript += "window.location.href='";
		strScript += url + "?" + name + "=" + value;
		strScript += "'</script>";
		return strScript;
	}

	public static String gotoShow(String url) {
		String strScript = "<script language='javascript' type='text/javascript'>";
		strScript += "window.location.href='";
		strScript += url;
		strScript += "'</script>";
		return strScript;
	}

	public static void main(String[] args) throws ParseException {

		double x = 0.001500;
		System.out.println(x * 10000);

	}

	

	/**
	 * 获取企业活动状态信息
	 * 
	 * @param orgState
	 * @return
	 */
	public static String orgState(int orgState) {
		String str = "";
		if (orgState == 0) {
			str = "正常";
		} else if (orgState == 1) {
			str = "暂停";
		} else if (orgState == 2) {
			str = "体验";
		} else if (orgState == 3) {
			str = "到期";
		} else if (orgState == 4) {
			str = "停用";
		} else {
			str = "未知状态";
		}
		return str;
	}

	/**
	 * 获取企业资费状态信息
	 * 
	 * @param orgState
	 * @return
	 */
	public static String orgFeeState(int orgState) {
		return (orgState == 2) ? "高级" : "普通";
	}

	/**
	 * 获取用户状态
	 * 
	 * @param state
	 * @return
	 */
	public static String gcbUserState(int state) {
		String strState = "";
		if (0 == state) {
			strState = "可用";
		} else if (1 == state){
			strState = "暂停";
		}else if (2 == state){
			strState = "停用";
		}
		return strState;
	}

	/**
	 * 操作显示
	 * 
	 * @return
	 */
	public static String getOperateState(int action, int type) {
		String result = "";
		switch (type) {
		case 1:
			result = (action == 0) ? "开户" : "退开户";
			break;
		case 2:
			result = (action == 0) ? "续费" : "退年费";
			break;
		case 3:
			result = (action == 0) ? "充值" : "退充值";
			break;
		}
		return result;
	}

	/**
	 *回款状态显示
	 * 
	 * @return
	 */
	public static String getRemitMoneyState(int state) {
		String str = "";
		if (state == 1) {
			str = "未回款";
		} else if (state == 2) {
			str = "无需回款";
		} else if (state == 3) {
			str = "已回款";
		} else if (state == 4) {
			str = "退点";
		} else {
			str = "退费";
		}
		return str;
	}

	/**
	 * 前一个月的最后一天
	 * @return
	 */
	public static String getPreviousMonthLast() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 0);// 设为当前月的1号
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 前一个月的第一天
	 * @return
	 */
	public static String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);
		lastDate.add(Calendar.MONTH, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 当月的第一天
	public static String getCurrentMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获取今天日期
	public static String getTodayTime() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		// lastDate.set(Calendar.DATE,1);
		// lastDate.add(Calendar.MONTH,-1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */

	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */

	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */

	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供把double转化为需要的精确值
	 * 
	 * @param v
	 *            要精确的double值
	 * @param scale
	 *            精确的位数，如果scale 小于零，则采用默认的精确值，精确到小数位 2 位
	 * */
	public static double round(double v, int scale) {
		if (scale < 0) {
			scale = 2;
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供把double 精确到 2 位 小数
	 * 
	 * @param v
	 *            要精确的double值
	 * 
	 * */
	public static double round(double v) {
		return round(v, 2);
	}

	

	private static final SimpleDateFormat formate = new SimpleDateFormat(
			"yyyy-MM-dd HH");

	/**
	 * 
	 * 获取用户的ip地址
	 * 
	 * @param request
	 * @return 用户ip
	 */
	public static String getRemortIP(HttpServletRequest request) {
		String result = request.getHeader("x-forwarded-for");
		if (result != null && result.trim().length() > 0) {
			// 可能有代理
			if (result.indexOf(".") == -1) {
				// 没有"."肯定是非IPv4格式
				result = null;
			} else {
				if (result.indexOf(",") != -1) {
					// 有","，估计多个代理。取第一个不是内网的IP。
					result = result.trim().replace("'", "");
					String[] temparyip = result.split(",");
					for (int i = 0; i < temparyip.length; i++) {
						if (isIPAddress(temparyip[i])
								&& temparyip[i].substring(0, 3).equals("10.")
								&& temparyip[i].substring(0, 7).equals(
										"192.168")
								&& temparyip[i].substring(0, 7).equals(
										"172.16.")) {
							return temparyip[i]; // 找到不是内网的地址
						}
					}
				} else if (isIPAddress(result)) {// 代理即是IP格式
					return result;
				} else {
					result = null; // 代理中的内容 非IP，取IP
				}
			}
		}

		if (result == null || result.trim().length() == 0) {
			result = request.getHeader("Proxy-Client-IP");
		}
		if (result == null || result.length() == 0) {
			result = request.getHeader("WL-Proxy-Client-IP");
		}
		if (result == null || result.trim().length() == 0) {
			result = request.getRemoteAddr();
		}
		return result;
	}

	/**
	 * 判断是否是IP地址格式
	 * 
	 * @param str1
	 * @return
	 */
	private static boolean isIPAddress(String str1) {
		if (str1 == null || str1.trim().length() < 7
				|| str1.trim().length() > 15) {
			return false;
		}
		return true;
	}

	// 用户添加金额转换
	/**
	 * 元转换成系统点1元==200点 对于0.011元转换成为2点,小数点后面第3位以后的(包含第三位)将舍弃.
	 * 
	 * @param money
	 * @return long
	 */
	public static long pointYuan(double money) {
		// 使用BigDecimal 转换long型计算,否则获取数字不精确.
		BigDecimal b1 = new BigDecimal(Double.toString(money));
		BigDecimal b2 = new BigDecimal(Double.toString(200));
		String strFen = b1.multiply(b2).doubleValue() + "";
		return Long.parseLong(strFen.substring(0, strFen.indexOf(".")));
	}

	/**
	 * 元转换成点. 一元 == 200点
	 * 
	 * @param money
	 * @return
	 */
	public static long yuan2Fen(String money) {
		return pointYuan(Double.valueOf(money));
	}

	public static long yuan2Fen(int money) {
		return pointYuan(Double.valueOf(money));
	}

	/**
	 * 点转换成元 200点==1元
	 * 
	 * @param money
	 * @return double
	 */
	public static double fen2Yuan(long money) {
		return (double) money / 200;
	}

	/**
	 * 点转换成元 200点==1元
	 * 
	 * @param money
	 * @return double
	 */
	public static double fen2Yuan(String money) {
		if (StringUtils.isEmpty(money)) {
			return 0;
		}
		double m = Double.parseDouble(money);
		if (m <= 0) {
			m = 0;
		}
		return fen2Yuan(m);
	}

	/**
	 * 点转换成元 200点==1元
	 * 
	 * @param money
	 * @return double
	 */
	public static double fen2Yuan(double money) {
		return money / 200;
	}

	


	/**
	 * 正则判别字符串是否全是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]+(.[0-9]+)?");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 比较时间大小
	 * 
	 * @param str1时间字符串1
	 *            ,str2时间字符串2,format时间格式
	 * @return int 返回0-相等；返回负数 :str1小于str2；返回正数：str1大于str2
	 **/
	public static int compareStringDate(String str1, String str2, String format)
			throws ParseException {
		if (format == null || "".equals(format)) {
			format = "yyyy-MM-dd HH:mm:ss";// 默认时间格式
		}
		DateFormat df = new SimpleDateFormat(format);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(df.parse(str1));
		c2.setTime(df.parse(str2));
		return c1.compareTo(c2);
	}

	/**
	 * 比较时间大小
	 * 
	 * @param str1时间字符串1
	 *            ,str2时间类型,format时间格式
	 * @return int 返回0-相等；返回负数 :str1小于str2；返回正数：str1大于str2
	 **/
	public static int compareStringDate(String str1, Date str2, String format)
			throws ParseException {
		if (format == null || "".equals(format)) {
			format = "yyyy-MM-dd HH:mm:ss";// 默认时间格式
		}
		DateFormat df = new SimpleDateFormat(format);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(df.parse(str1));
		c2.setTime(df.parse(df.format(str2)));
		return c1.compareTo(c2);
	}
	
	
	 /**
	  * gzip压缩文件流   
	  * @param str
	  * @return
	  * @throws IOException
	  */
	 public static String compressGzip(String str) throws IOException {   
	     if (str == null || str.length() == 0) {   
	      return str;   
	    }   
	     ByteArrayOutputStream out = new ByteArrayOutputStream();   
	    GZIPOutputStream gzip = new GZIPOutputStream(out);   
	     gzip.write(str.getBytes());   
	     gzip.close();   
	    return out.toString("utf-8");   
	   }   
	   
	   /**
	    * gzip解压缩文件流
	    * @param str
	    * @return
	    * @throws IOException
	    */
	  public static String uncompressGzip(String str) throws IOException {   
	     if (str == null || str.length() == 0) {   
	       return str;   
	   }   
	    ByteArrayOutputStream out = new ByteArrayOutputStream();   
	    ByteArrayInputStream in = new ByteArrayInputStream(str .getBytes("utf-8"));   
	     GZIPInputStream gunzip = new GZIPInputStream(in);   
	     byte[] buffer = new byte[256];   
	     int n;   
	    while ((n = gunzip.read(buffer))>= 0) {   
	     out.write(buffer, 0, n);   
	     }   
	     // toString()使用平台默认编码，也可以显式的指定如toString(&quot;GBK&quot;)   
	     return out.toString();   
	   }   
}
