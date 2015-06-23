package com.wtyt.packets;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.wtyt.yzone.util.YzoneUtil;

/**
 * 解析报文xml
 * @author Administrator
 * 
 */
public class ParsePacketsXml {
	
	private static final  String KEY_ID = "id";//xml的关联id
	
	private static final  String KEY_CLASS = "class";//xml的关联class
	
	private static final  String KEY_METHOD = "method";//xml的关联method
	
	public static Map<String,String>  classMap = new HashMap<String,String>();//反射的类map
	
	public static Map<String,String>  methodMap = new HashMap<String,String>();//反射的方法map
	
	public static final String KEY_SRC = "src";//import 的子路径

	public static void produceXmlMapInfo(File filePath) throws FileNotFoundException,PacketsException,Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(filePath);
		// 获得根元素结点
		Element root = doc.getDocumentElement();
		parseElement(root);
	}

	private static void parseElement(Element element) throws FileNotFoundException,PacketsException,Exception {
		NodeList children = element.getChildNodes();	
		//element元素的所有属性所构成的NamedNodeMap对象，需要对其进行判断
		NamedNodeMap map = element.getAttributes();
       // String elementName = element.getNodeName();
		// 如果该元素存在属性
		if (null != map) {	
			Map<String,String> nameValueMap = new HashMap<String,String>();
			for (int i = 0; i < map.getLength(); i++) {
				//获得该元素的每一个属性
				Attr attr = (Attr) map.item(i);			
				String attrName = attr.getName();
				String attrValue = attr.getValue();			
				if(attrName.equals(KEY_SRC)){
					//递归
					produceXmlMapInfo(new File(getRootPath()+attrValue));
					break;
				}else{
					nameValueMap.put(attrName, attrValue);
					//System.out.println(attrName + "=\"" + attrValue + "\"");				
					}
			}
			if(!nameValueMap.isEmpty()){
				if(classMap.containsKey(nameValueMap.get(KEY_ID))){
					  throw new PacketsException("报文ID:"+nameValueMap.get(KEY_ID)+"已存在");
				}				
				if(methodMap.containsKey(nameValueMap.get(KEY_ID))){
					 throw new PacketsException("报文ID:"+nameValueMap.get(KEY_ID)+"已存在");
				}
				classMap.put(nameValueMap.get(KEY_ID),nameValueMap.get(KEY_CLASS));
			    methodMap.put(nameValueMap.get(KEY_ID),nameValueMap.get(KEY_METHOD));
			}
			nameValueMap = null;
		}
		
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			// 获得结点的类型
			short nodeType = node.getNodeType();
			if (nodeType == Node.ELEMENT_NODE) {
				// 是元素，继续递归
				parseElement((Element) node);
			}
		 }
	}

	/**
	 * 获取当前项目的根目录
	 * 
	 * @return
	 */
	public static String getRootPath() {
		String url = Thread.currentThread().getContextClassLoader().getResource("").toString().substring(5);		
		url = url.replaceAll("%20", " ");		
		return url;
	}
	
	/**
	 * 返回类信息
	 * @return
	 */
	public static  Map<String,String> getXmlClassMap(){
		 if(null==classMap||classMap.isEmpty()){
			 try {
				produceXmlMapInfo(new File(getRootPath() + "packets.xml"));
			} catch (Exception e) {
				YzoneUtil.printExceptionInfo(e);
			}
		 }
		 return classMap;
	}
	
	
	/**
	 * 返回方法信息
	 * @return
	 */
	public static  Map<String,String> getXmlMethodMap(){
		 if(null==methodMap ||methodMap.isEmpty()){
			 try {
				produceXmlMapInfo(new File(getRootPath() + "packets.xml"));
			} catch (Exception e) {
				YzoneUtil.printExceptionInfo(e);
			}
		 }
		 return methodMap;
	}
}
