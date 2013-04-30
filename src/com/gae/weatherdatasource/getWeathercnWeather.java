package com.gae.weatherdatasource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import net.sf.json.JSONObject;  

import org.apache.http.HttpResponse;  
import org.apache.http.HttpStatus;  

import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.util.EntityUtils;  
import com.gae.util.*;

public class getWeathercnWeather {

 	/**
 	 * 
 	 * @param cityName
 	 * ע��weatherд����е���������
 	 * @return Map ����
 	 * ������Դ��weather.com.cn
 	 * @throws UnsupportedEncodingException 
 	 * @throws ParserConfigurationException 
 	 * �޷���Main�������ز���
 	 */
 	@SuppressWarnings("rawtypes")
 	public static Map getWeather(String cityName) throws UnsupportedEncodingException, ParserConfigurationException {
 		Map<String, String> map = null ; 
 		URL url = null ;
 			try {				 			    			     
 				url = new URL("http://m.weather.com.cn/data/"+CityInfoUtil.getCityNoByName(cityName)+".html");
 				System.out.println(url);
 				try  
 				{  				     								
 		            HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
 		            InputStream is = conn.getInputStream();  
 		            BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));  
 		            String msg = null;  
 		            StringBuilder sb = new StringBuilder();  
 		            while((msg = br.readLine()) != null) {  
 		                sb.append(msg);  
 		            }  					 				     			        	
 		            if(sb==null||sb.toString().contains("�ܱ�Ǹ����Ҫ���ʵ�ҳ�治����")||sb.toString().contains("404 Not Found"))  //�޷���ȡ����
 		            {
 		            	 return null;
 		            }
 		               //ȡ�÷��ص��ַ���   	
 				        System.out.println("��weather.com.cn��ȡ����Ϊ��"+sb.toString());  
 				        JSONObject jo = JSONObject.fromObject(sb.toString());
 				       jo = jo.getJSONObject("weatherinfo");
 				        System.out.println(jo.toString());  
 				        System.out.println(jo.getString("city"));
 				        map = new HashMap<String, String>();
 				        map.put("city",jo.getString("city").toString());
 				        map.put("date",jo.getString("date_y").toString());
 				        map.put("status",jo.getString("weather1").toString());
 				        //�����¶�
 				        if(jo.getString("temp1").toString()!=null&& !jo.getString("temp1").toString().equals(""));
 				        {
 				        	String[] tempStrs = jo.getString("temp1").toString().split("~");			       
 	 				        map.put("temperatureLow",tempStrs[1]);
 	 				        map.put("temperatureHigh",tempStrs[0]);
 				        }	        
 				        map.put("sportAdvice",jo.getString("index_d").toString());
 				        map.put("dataSource", "http://m.weather.com.cn"); 				       				        				      				        
 				    				   
 				}  
 				catch (ClientProtocolException e1)  
 				{  
 				    e1.getMessage().toString();  
 				}

 			} catch (MalformedURLException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			} catch (IOException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}					
 		return map;
 	}

}
