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
 * ���ݳ��е����Ļ���ƴ������ȡwww.weather.com.cn �й��������ĳ��д���
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
				System.out.println("CityInfo����" + nodeList.getLength());
				for (int i = 0; i < nodeList.getLength(); i++) 
				{
					if(document.getElementsByTagName("CityInfo").item(i).getAttributes().getNamedItem("Name").getNodeValue()!=null && document.getElementsByTagName("CityInfo").item(i).getAttributes().getNamedItem("Name").getNodeValue().contains(cityName))
					  cityNo = document.getElementsByTagName("CityInfo").item(i).getAttributes().getNamedItem("WeatherCode").getNodeValue();
				}
			} else
				System.err.print("ϵͳ�Ҳ����ļ�");

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
		System.out.println("�����ĳ��д���Ϊ��"+cityinfouitl.getCityNoByName("����"));
		
	}
}
