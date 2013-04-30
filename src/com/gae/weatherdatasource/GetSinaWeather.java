package com.gae.weatherdatasource;

//读取新浪天气预报,-read weather forecasts

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.gae.util.ConverUtil;

public class GetSinaWeather {
	/**
	 * 
	 * @param cityName
	 * 注意weather写入城市的拼音或者中文
	 * @return Map 类型
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("rawtypes")
	public static Map getWeather(String cityName) throws UnsupportedEncodingException {
		URLConnection cn = null;
		Map<String, String> map = null ; 
	    URL url = null ;
			try {
				url = new URL("http://php.weather.sina.com.cn/xml.php?city="+cityName+"&password=DJOYnieT8234jlsK&day=0");
				System.out.println("url="+url);
				cn = url.openConnection();
				cn.connect();
				InputStream stream = cn.getInputStream();
				DocumentBuilder docBuild = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc = docBuild.parse(stream);
				NodeList nodelist = (NodeList) doc.getElementsByTagName("Weather");
			    map = new HashMap<String, String>();
				for (int i = 0; i < nodelist.getLength(); i++) 
				{
					map.put("city", doc.getElementsByTagName("city").item(i).getFirstChild().getNodeValue());   //城市
					map.put("date", doc.getElementsByTagName("savedate_weather").item(i).getFirstChild().getNodeValue());  //日期
					map.put("status", doc.getElementsByTagName("status1").item(i).getFirstChild().getNodeValue()   //天气情况
							+ ","+ doc.getElementsByTagName("status2").item(i).getFirstChild().getNodeValue());
					map.put("temperatureLow", doc.getElementsByTagName("temperature1").item(i).getFirstChild().getNodeValue());//最低温度
					map.put("temperatureHigh", doc.getElementsByTagName("temperature2").item(i).getFirstChild().getNodeValue()); //最高温度
					map.put("sportAdvice", doc.getElementsByTagName("ssd_s").item(i).getFirstChild().getNodeValue());			//运动建议
					map.put("dataSource", "http://php.weather.sina.com.cn/xml.php");
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}					
		return map;
	}

	public static void main(String args[]) throws UnsupportedEncodingException {
		GetSinaWeather gsw = new GetSinaWeather();
		String cityName = "广州";
		Map map = gsw.getWeather(cityName);
		Date nowDate = new Date();
		DateFormat dateformat = DateFormat.getDateInstance();
		String today = dateformat.format(nowDate);
		System.out.println(today + " " + cityName + "的天气情况是(JSON)：" +"\r"+ ConverUtil.maptoJSON(map));
		System.out.println(today + " " + cityName + "的天气情况是(XML)：" +"\r"+ ConverUtil.maptoXml(map));
	}

}