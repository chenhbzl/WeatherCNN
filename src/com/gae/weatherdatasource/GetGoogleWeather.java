package com.gae.weatherdatasource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * @description ͨ��Java��ȡGoogle�ļ�����Ԥ��
 * @author tianyu
 * @link http://weibo.com/10792402
 */
public class GetGoogleWeather {

	/**
	 * 
	 * @param cityName
	 * ע��weather��д����е�ƴ��ת��һ�¾��У� ��֮����XML��ʽ��Ȼ������ȡ��
	 * @return
	 */
	public String getWeather(String cityName, String fileAddr) {
		// ��ȡgoogle�ϵ����������д���ļ�
		try {
			URL url = new URL("http://www.google.com/ig/api?hl=zh_cn&weather="
					+ cityName);
			InputStream inputstream = url.openStream();
			String s, str;
			BufferedReader in = new BufferedReader(new InputStreamReader(inputstream,"GB2312"));//�������ñ���Ϊgb2312
			StringBuffer stringbuffer = new StringBuffer();
			
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileAddr), "UTF-8"));
			
			while ((s = in.readLine()) != null) {
				stringbuffer.append(s);
			}
			str = new String(stringbuffer);
			out.write(str);
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ��ȡ��Ҫ������
		File file = new File(fileAddr);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		String str = null;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			NodeList nodelist1 = (NodeList) doc
					.getElementsByTagName("forecast_conditions");
			NodeList nodelist2 = nodelist1.item(0).getChildNodes();

			str = nodelist2.item(4).getAttributes().item(0).getNodeValue()
					+ ",�¶�:"
					+ nodelist2.item(1).getAttributes().item(0).getNodeValue()
					+ "��-"
					+ nodelist2.item(2).getAttributes().item(0).getNodeValue()
					+ "��";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static void main(String args[]) {
		GetGoogleWeather ggw = new GetGoogleWeather();

		String cityName = "beijing";

		Date nowDate = new Date();
		DateFormat dateformat = DateFormat.getDateInstance();
		String today = dateformat.format(nowDate);

	}
}