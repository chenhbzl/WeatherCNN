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
		System.out.println("city ԭֵ��"+cityName);
//		�Բ�ͬ����������ò�ͬ�ı��뷽ʽ
		String agent = req.getHeader("USER-AGENT");   
		if (null != agent && -1 != agent.indexOf("MSIE")) {
			cityName = new String(req.getParameter("city").getBytes("GBK"),"GB2312");	
		} 		
		Map dataMap = null;
	
	try {
			dataMap = getWeathercnWeather.getWeather(cityName);  //��Weather.com.cn��ȡ��������
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(dataMap==null||dataMap.size()==0)
		{
	    	System.out.println("log info:��ǰ�޷���Weather.com.cn ��ȡ���ݣ�����ѡ���Sina��ȡ����...");
			dataMap = GetSinaWeather.getWeather(cityName);	  //��sina��ȡ��������
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
