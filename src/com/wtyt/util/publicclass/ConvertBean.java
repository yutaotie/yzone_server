package com.wtyt.util.publicclass;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts2.ServletActionContext;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ConvertBean {
	private static final long serialVersionUID = 1L;

	private final static String dateFormat1 = "yyyy-MM-dd";
	private final static String dataFormat2 = "yyyy-MM-dd hh:mm:ss";

	private final static String MCC_PHONE_REGULAR_EXPRESSIONS_STR = "^13[0-9][0-9]{8}|15[4-9][0-9]{8}$";
	private final static String MCC_DATE_REGULAR_EXPRESSIONS_STR = "[1-9][0-9]{3}\\-([1-9]|0[1-9]|1[0-2])\\-([1-9]|0[1-9]|1[0-9]|2[0-9]|3[0-1])";
	private final static String NUMBER_REGULAR_EXPRESSIONS_STR = "[0-9]*";

	/**
	 * 方法说明： 转换日期格式的时间为字符格式
	 * 
	 * @param date
	 * @param iFormatType
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getDateStrFormat(Date date, int iFormatType) {

		// 字符格式的时间
		if (date == null) {
			// date = new Date();
			return "";
		}

		StringBuffer stringBufferDate = new StringBuffer(200);

		int iYear = date.getYear() + 1900;
		stringBufferDate.append(iYear + "-");

		int iMonth = date.getMonth() + 1;
		if (iMonth < 10) {
			stringBufferDate.append("0");
		}
		stringBufferDate.append(iMonth + "-");

		int iDate = date.getDate();
		if (iDate < 10) {
			stringBufferDate.append("0");
		}
		stringBufferDate.append(iDate + " ");

		if (iFormatType == 1) {
			return stringBufferDate.toString();
		} else if (iFormatType == 2) {
			int iHour = date.getHours();
			if (iHour < 10) {
				stringBufferDate.append("0");
			}
			stringBufferDate.append(iHour + ":");

			int iMinutes = date.getMinutes();
			if (iMinutes < 10) {
				stringBufferDate.append("0");
			}
			stringBufferDate.append(iMinutes + ":");

			int iSeconds = date.getSeconds();
			if (iSeconds < 10) {
				stringBufferDate.append("0");
			}
			stringBufferDate.append(iSeconds);
			return stringBufferDate.toString();

		} else {
			return stringBufferDate.toString();
		}

	}

	/**
	 * 将字符串形式的日期按照规定的格式转换成日期类型
	 * 
	 * @param strDate
	 * @param formate
	 * @return
	 */
	public static Date ConvetStringToDate(String strDate, int formate) {
		SimpleDateFormat formatter = null;
		if (formate == 1) {
			formatter = new SimpleDateFormat(dateFormat1);
		} else {
			formatter = new SimpleDateFormat(dataFormat2);
		}

		if (strDate == null || strDate.length() == 0) {
			Date date = new Date();
			strDate = formatter.format(date);
		}

		ParsePosition pos = new ParsePosition(0);
		Date returnDate = formatter.parse(strDate, pos);
		return returnDate;
	}

	// 将字符串用BASE64编码
	public static String base64Encode(String s) {
		if (s == null) {
			return null;
		}
		return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
	}

	// 将被BASE64编码的字符串解码
	public static String base64Decode(String s) {
		if (s == null) {
			return null;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}

	// 照片解码
	public static byte[] getFromBASE64(String s) {
		BASE64Decoder decoder;
		if (s == null) {
			return null;
		}
		decoder = new BASE64Decoder();
		try {
			byte b[] = decoder.decodeBuffer(s);
			return b;
		} catch (java.io.IOException ie) {
			System.out.println("照片解码错误");
			ie.printStackTrace();
			return null;
		}
	}

	/**
	 * base64编码，从byte数组到字符串
	 * 
	 * @param byte[]
	 * @return String
	 */
	public static String base64Encode(byte[] b) {
		if (b == null) {
			return "";
		}
		BASE64Encoder encoder = new BASE64Encoder();
		try {
			String result = encoder.encode(b);
			return result;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 检测手机号码是否是中国移动手机号码 返回true表示是移动手机号码 返回false表示是联通手机号码
	 * 
	 * @param mobileNo
	 *            String
	 * @return boolean
	 */
	public static boolean isMobileNo(String mobileNo) {
		if(isCmccMobileNo(mobileNo) || isUnicomMobileNo(mobileNo) || isTelecomMobileNo(mobileNo)){
			return true;
		}
		return false;
		
	}

	/**
	 * 检测手机号码是否是中国移动手机号码 返回true表示是移动手机号码 返回false表示是联通手机号码
	 * 
	 * @param mobileNo
	 *            String
	 * @return boolean
	 */
	public static boolean isCmccMobileNo(String mobileNo) {
		Pattern p = Pattern.compile(System.getProperty("CmccNoPattern"));
		Matcher m = p.matcher(mobileNo);
		return m.matches();
	}

	/**
	 * 判断是否是中国联通的手机号码
	 * 
	 * @param mobileNo
	 *            String
	 * @return boolean
	 */
	public static boolean isUnicomMobileNo(String mobileNo) {
		Pattern p = Pattern.compile(System.getProperty("UnicomNoPattern"));
		Matcher m = p.matcher(mobileNo);
		return m.matches();
	}

	/**
	 * 判断是否是中国电信的手机号码
	 * 
	 * @param mobileNo
	 *            String
	 * @return boolean
	 */
	public static boolean isTelecomMobileNo(String mobileNo) {
		Pattern p = Pattern.compile(System.getProperty("TelecomNoPattern"));
		Matcher m = p.matcher(mobileNo);
		return m.matches();
	}

	/**
	 * 检测日期字符串 返回true表示是 返回false表示否
	 * 
	 * @param date
	 *            String
	 * @return boolean
	 */
	public static boolean isDate(String str) {

		Pattern p = Pattern.compile(MCC_DATE_REGULAR_EXPRESSIONS_STR);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 函数功能: 检查字符串是否是数字
	 * 
	 * @param str
	 *            String
	 * @return boolean
	 */
	public static boolean isNumber(String str) {

		Pattern p = Pattern.compile(NUMBER_REGULAR_EXPRESSIONS_STR);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 检测手机号码是否是手机号码 返回true表示是手机号码 返回false表示不是手机号码
	 * 
	 * @param mobileNo
	 *            String
	 * @return boolean
	 */
	public static boolean isPhoneNo(String mobileNo) {

		Pattern p = Pattern.compile(MCC_PHONE_REGULAR_EXPRESSIONS_STR);
		Matcher m = p.matcher(mobileNo);
		return m.matches();
	}

	/**
	 * 方法功能: 将其他编码格式的数据串转换为 ISO-8859-1 编码格式 1) jdk是按照 ISO-8859-1格式的编译java原文件的.
	 * 
	 * @return String
	 */
	public static String ISO88591TOGBK(String temp) {

		System.out.println(" ISO88591TOGBK 1");
		if (temp == null) {
			return null;
		}

		byte byteTemp[] = null;
		System.out.println(" ISO88591TOGBK 2");
		try {
			byteTemp = temp.getBytes("ISO-8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(" ISO88591TOGBK 3");
		return new String(byteTemp);
	}

	/**
	 * 判断是否是数字，支持0x11，9.5， 1E3等格式
	 * 
	 * @param str
	 *            String
	 * @return boolean
	 */
	public static boolean isDigitalNumber(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		char[] chars = str.toCharArray();
		int sz = chars.length;
		boolean hasExp = false;
		boolean hasDecPoint = false;
		boolean allowSigns = false;
		boolean foundDigit = false;
		int start = (chars[0] == '-') ? 1 : 0;
		if (sz > start + 1) {
			if (chars[start] == '0' && chars[start + 1] == 'x') {
				int i = start + 2;
				if (i == sz) {
					return false;
				}
				for (; i < chars.length; i++) {
					if ((chars[i] < '0' || chars[i] > '9')
							&& (chars[i] < 'a' || chars[i] > 'f')
							&& (chars[i] < 'A' || chars[i] > 'F')) {
						return false;
					}
				}
				return true;
			}
		}
		sz--;
		int i = start;
		while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
			if (chars[i] >= '0' && chars[i] <= '9') {
				foundDigit = true;
				allowSigns = false;

			} else if (chars[i] == '.') {
				if (hasDecPoint || hasExp) {
					return false;
				}
				hasDecPoint = true;
			} else if (chars[i] == 'e' || chars[i] == 'E') {
				if (hasExp) {
					return false;
				}
				if (!foundDigit) {
					return false;
				}
				hasExp = true;
				allowSigns = true;
			} else if (chars[i] == '+' || chars[i] == '-') {
				if (!allowSigns) {
					return false;
				}
				allowSigns = false;
				foundDigit = false;
			} else {
				return false;
			}
			i++;
		}
		if (i < chars.length) {
			if (chars[i] >= '0' && chars[i] <= '9') {
				return true;
			}
			if (chars[i] == 'e' || chars[i] == 'E') {
				return false;
			}
			if (!allowSigns
					&& (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F')) {
				return foundDigit;
			}
			if (chars[i] == 'l' || chars[i] == 'L') {
				return foundDigit && !hasExp;
			}
			return false;
		}
		return !allowSigns && foundDigit;
	}

	/**
	 * 方法说明： 转换日期格式的时间为中文显示
	 */
	public static String getDateStrFormatCh(String date) {
		String month = date.substring(5, 7);
		if ("0".equals(month.substring(0, 1))) {
			month = month.substring(1, 2);
		}
		String time = date.substring(0, 4) + "年" + month + "月"
				+ date.substring(8, 10) + "日";
		return time;
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
	 * 获得文件后缀名 如果fileName 等于null或为空或者没有后缀名 则返回 空字符串
	 * 
	 * @param fileName
	 *            文件名字符串
	 * @return String -- 返回最后一个 . 后面的字符串
	 * */
	public static String getFileSuffix(String fileName) {

		if (fileName == null || "".equals(fileName)
				|| fileName.trim().length() == 0) {
			return "";
		}
		int index = fileName.lastIndexOf(".");
		if (index != -1) {
			return fileName.substring(index + 1);
		}
		return "";

	}

	/**
	 * 保险页面显示价值
	 * 
	 * @param st
	 *            String
	 * @return String
	 */
	public static String InsuranceShowPrice(String a) {
		char b[] = a.toCharArray();
		int j = b.length;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(b[i]);
			if ((j - 1) % 3 == 0 && i < b.length - 1)
				sb.append(",");
			j--;
		}
		return sb.toString();
	}

	/**
	 * 过滤直辖市
	 * 
	 * @param st
	 *            String
	 * @return String
	 */

	public static String filerProvince(String province) {
		if ("北京".equals(province) || "上海".equals(province)
				|| "天津".equals(province) || "重庆".equals(province)) {
			return "";
		}
		return province;
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

	// 金额转换为大写汉字(识别范围0~10,000,000)//Modify by jml
	public static String changeToEnglishMoney(int fee) {
		// 基本数词表
		String[] enNum = { "zero", "one", "two", "three", "four", "five",
				"six", "seven", "eight", "nine", "ten", "eleven", "twelve",
				"thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
				"eighteen", "nineteen", "twenty", "", "", "", "", "", "", "",
				"", "", "thirty", "", "", "", "", "", "", "", "", "", "fourty",
				"", "", "", "", "", "", "", "", "", "fifty", "", "", "", "",
				"", "", "", "", "", "sixty", "", "", "", "", "", "", "", "",
				"", "seventy", "", "", "", "", "", "", "", "", "", "eighty",
				"", "", "", "", "", "", "", "", "", "ninety" };
		String[] unit = { "hundred", "thousand", "million" };// 单位
		StringBuffer res = new StringBuffer();// 保存结果
		String feeStr = String.valueOf(fee);
		int num_length = feeStr.length();// 获取数字长度

		// 按3位分割分组
		int count = (num_length % 3 == 0) ? num_length / 3 : num_length / 3 + 1;
		// 判断组单位是否超过，
		if (count > unit.length) {
			return "数字过大,转换失败";
		}
		// 追加单位
		String[] group = new String[count];
		for (int i = num_length, j = group.length - 1; i > 0; i -= 3) {
			group[j--] = feeStr.substring(Math.max(i - 3, 0), i);
		}
		// 遍历分割的组
		int temp = -1;

		for (int i = 0; i < count; i++) {
			temp = Integer.valueOf(group[i]);
			if (temp >= 100) {
				res.append(enNum[temp / 100]).append(" ").append(unit[0])
						.append(" ");
				temp = temp % 100; // 获取百位，并得到百位以后的数
				if (temp != 0) {
					res.append("and ");
				} // 如果百位后的数不为0，则追加and
			}
			if (temp != 0) {
				if (temp < 20 || temp % 10 == 0) { // 如果小于20或10的整数倍，直接取基本数词表的单词
					res.append(enNum[temp]).append(" ");
				} else { // 否则取10位数词，再取个位数词
					res.append(enNum[temp - temp % 10]).append(" ");
					res.append(enNum[temp % 10]).append(" ");
				}

			}

			String lastUnit = res.substring(res.toString().trim().lastIndexOf(
					" ") + 1, res.length() - 1);
			if ((!lastUnit.equals(unit[2])) && (i != count - 1)) { // 百位以上的组追加相应的单位
				res.append(unit[count - 1 - i]).append(" ");
			}
		}
		return res.toString().toUpperCase().trim();
	}

	// 得到本系统实际保存文件根目录
	public static String getRootPath() {
		return ServletActionContext.getServletContext().getRealPath("")
				.replace("\\", "/");
	}

	public static String encode(InputStream in) throws IOException {
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		byte[] data = new byte[in.available()];
		in.read(data);
		return encoder.encode(data);
	}

	public static byte[] decode(String base64Str) throws IOException {
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		return decoder.decodeBuffer(base64Str);
	}

	public static void decode(OutputStream os, String srcEncoder) throws IOException {
		ByteArrayInputStream bis = null;
		try {
			bis = new ByteArrayInputStream(srcEncoder.getBytes());
			new BASE64Decoder().decodeBuffer(bis, os);
		} finally {
			if (bis != null) {
				bis.close();
			}
		}
	}
}
