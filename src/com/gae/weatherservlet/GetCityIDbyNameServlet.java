package com.gae.weatherservlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gae.util.CityInfoUtil;
import com.gae.util.ConverUtil;


@SuppressWarnings("serial")
public class GetCityIDbyNameServlet extends HttpServlet
{
	public  void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException 
	{
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		System.out.println(req.getParameter("cityName").toString());
		String  cityId = CityInfoUtil.getCityNoByName(req.getParameter("cityName").toString());  	
		if(cityId != null && !cityId.equals(""))
		{
			resp.getWriter().println(cityId);
			System.out.println(cityId);
		}		
		else
		{
			String err = "������ĳ������Ʋ���ȷ�����������룡"; 
			resp.getWriter().println(err);
			System.out.println(err);
		}
	
	}
		
}
