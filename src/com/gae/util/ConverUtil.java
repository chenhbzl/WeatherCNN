package com.gae.util;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
/*
 * 字符类型转换基础工具类
 * */
public class ConverUtil {
	

	/**
	 * map to xml
	 * 
	 * <weather>
	 *    <key1>value1</key1> 
	 *    <key2>value2</key2> 
	 *    ......
	 * </weather>
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String maptoXml(Map map) {
		Document document = DocumentHelper.createDocument();
		Element nodeElement = document.addElement("weather");	
		Iterator iter = map.entrySet().iterator(); 
		while (iter.hasNext()) 
		{ 
		    Map.Entry entry = (Map.Entry) iter.next(); 
		    Element keyElement = nodeElement.addElement(entry.getKey().toString());
		    keyElement.setText(String.valueOf(entry.getValue()));	   
		}
		return doc2String(document);
	}
	
	/**
	 * map to xml
	 * 
	 * {"weather":{"city":"东莞","cityid":"101281601","temp1":"23℃","temp2":"18℃","weather":"多云转阵雨","img1":"d1.gif","img2":"n3.gif","ptime":"11:00"}}
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String maptoJSON(Map map) 
	{	
		Set<String> keys = map.keySet();
		String key = "";
		String value = "";
		StringBuffer jsonBuffer = new StringBuffer();
		jsonBuffer.append("{\"weather\":{");
		for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			key = (String) it.next();
			value = (String) map.get(key);
			jsonBuffer.append("\""+key +"\""+ ":" +"\""+ value+"\"");
			if (it.hasNext()) {
				jsonBuffer.append(",");
			}
		}
		jsonBuffer.append("}}");
		return jsonBuffer.toString();
	}

	/**
	 * 
	 * @param document
	 * @return
	 */
	public static String doc2String(Document document) {
		String s = "";
		try {
			// 使用输出流来进行转化
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// 使用UTF-8编码
			OutputFormat format = new OutputFormat(" ", true, "UTF-8");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			s = out.toString("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
	}



	/**
	* list to xml xml <nodes><node><key label="key1">value1</key><key
	* label="key2">value2</key>......</node><node><key
	* label="key1">value1</key><key
	* label="key2">value2</key>......</node></nodes>
	*
	* @param list
	* @return
	*/
	public static String listtoXml(List list) {
	Document document = DocumentHelper.createDocument();
	Element nodesElement = document.addElement("nodes");
	for (Object o : list) {
	Element nodeElement = nodesElement.addElement("node");
	for (Object obj : ((Map) o).keySet()) {
	Element keyElement = nodeElement.addElement("key");
	keyElement.addAttribute("label", String.valueOf(obj));
	keyElement.setText(String.valueOf(((Map) o).get(obj)));
	}
	}
	return doc2String(document);
	}
	/**
	* json to xml {"node":{"key":{"@label":"key1","#text":"value1"}}} conver
	* <o><node class="object"><key class="object"
	* label="key1">value1</key></node></o>
	*
	* @param json
	* @return
	*/
	public static String jsontoXml(String json) {
	try {
	XMLSerializer serializer = new XMLSerializer();
	JSON jsonObject = JSONSerializer.toJSON(json);
	return serializer.write(jsonObject);
	} catch (Exception e) {
	e.printStackTrace();
	}
	return null;
	}
	/**
	* xml to map xml <node><key label="key1">value1</key><key
	* label="key2">value2</key>......</node>
	*
	* @param xml
	* @return
	*/
	public static Map xmltoMap(String xml) {
	try {
	Map map = new HashMap();
	Document document = DocumentHelper.parseText(xml);
	Element nodeElement = document.getRootElement();
	List node = nodeElement.elements();
	for (Iterator it = node.iterator(); it.hasNext();) {
	Element elm = (Element) it.next();
	map.put(elm.attributeValue("label"), elm.getText());
	elm = null;
	}
	node = null;
	nodeElement = null;
	document = null;
	return map;
	} catch (Exception e) {
	e.printStackTrace();
	}
	return null;
	}
	/**
	* xml to list xml <nodes><node><key label="key1">value1</key><key
	* label="key2">value2</key>......</node><node><key
	* label="key1">value1</key><key
	* label="key2">value2</key>......</node></nodes>
	*
	* @param xml
	* @return
	*/
	public static List xmltoList(String xml) {
	try {
	List<Map> list = new ArrayList<Map>();
	Document document = DocumentHelper.parseText(xml);
	Element nodesElement = document.getRootElement();
	List nodes = nodesElement.elements();
	for (Iterator its = nodes.iterator(); its.hasNext();) {
	Element nodeElement = (Element) its.next();
	Map map = xmltoMap(nodeElement.asXML());
	list.add(map);
	map = null;
	}
	nodes = null;
	nodesElement = null;
	document = null;
	return list;
	} catch (Exception e) {
	e.printStackTrace();
	}
	return null;
	}
	/**
	* xml to json <node><key label="key1">value1</key></node> 转化为
	* {"key":{"@label":"key1","#text":"value1"}}
	*
	* @param xml
	* @return
	*/
	public static String xmltoJson(String xml) {
	XMLSerializer xmlSerializer = new XMLSerializer();
	return xmlSerializer.read(xml).toString();
	}
	
}
