package com.gae.weatherservlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.*;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;

import com.gae.util.ConverUtil;
import com.gae.weatherdatasource.GetSinaWeather;
import com.gae.weatherdatasource.getWeathercnWeather;

@SuppressWarnings("serial")
public class GetWeatherCNNServlet extends HttpServlet {
	@SuppressWarnings("rawtypes")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		String cityName = req.getParameter("city").toString();
		System.out.println("city 原值："+cityName);
//		对不同的浏览器采用不同的编码方式
		String agent = req.getHeader("USER-AGENT");   
		if (null != agent && -1 != agent.indexOf("MSIE")) {
			cityName = new String(req.getParameter("city").getBytes("GBK"),"GB2312");	
		} 		
		Map dataMap = null;
	
	try {
			dataMap = getWeathercnWeather.getWeather(cityName);  //从Weather.com.cn获取天气数据
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(dataMap==null||dataMap.size()==0)
		{
	    	System.out.println("log info:当前无法从Weather.com.cn 获取数据！，将选择从Sina获取数据...");
			dataMap = GetSinaWeather.getWeather(cityName);	  //从sina获取天气数据
		}
		System.out.println("system out..."+dataMap.size());
		if(dataMap != null && !dataMap.isEmpty())
		{
			if(req.getParameter("resultFormat")!=null && req.getParameter("resultFormat").toString().equalsIgnoreCase("json"))
			{
			   resp.getWriter().println(ConverUtil.maptoJSON(dataMap));
			   System.out.println(ConverUtil.maptoJSON(dataMap));
			}
			else 
			{
				resp.setContentType("text/xml; charset=utf-8");
				resp.getWriter().println(ConverUtil.maptoXml(dataMap));
				System.out.println(ConverUtil.maptoXml(dataMap));
			}
			
		}		
	}
}
