package com.gae.util;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*
 * 根据城市的中文或者拼音，获取www.weather.com.cn 中国天气网的城市代码
 * */
public class CityInfoUtil { 

	public static DocumentBuilderFactory documentBuilderFactory = null;
	public static DocumentBuilder documentBuilder = null;
	public static Document document = null;

	public static String getCityNoByName(String cityName) {
		String cityNo = null;
//		File file = new File("C:\\Users\\IBM_ADMIN\\Workspaces\\MyEclipse 9\\WeatherCNN\\war\\WEB-INF\\classes\\cifyInfo.xml");
		File file = new File("cifyInfo.xml"); 
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(file);
			if (file.exists()) {
				NodeList nodeList = document.getElementsByTagName("CityInfo");
				System.out.println("CityInfo数：" + nodeList.getLength());
				for (int i = 0; i < nodeList.getLength(); i++) 
				{
					if(document.getElementsByTagName("CityInfo").item(i).getAttributes().getNamedItem("Name").getNodeValue()!=null && document.getElementsByTagName("CityInfo").item(i).getAttributes().getNamedItem("Name").getNodeValue().contains(cityName))
					  cityNo = document.getElementsByTagName("CityInfo").item(i).getAttributes().getNamedItem("WeatherCode").getNodeValue();
				}
			} else
				System.err.print("系统找不到文件");

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cityNo;
	}

	public static void main(String[] args) {
		CityInfoUtil cityinfouitl = new CityInfoUtil();
		System.out.println("北京的城市代码为："+cityinfouitl.getCityNoByName("北京"));
		
	}
}
