package com.siwe.dutschedule.infoGeter;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;

public abstract class InfoGeter {

	protected String FirstUrl;
	protected String SecondUrl;
	protected Context context;
	private String result1;
	private String result2;
	
	public InfoGeter(Context cont){
		this.context = cont;
	}

	public boolean getTwoPageInfo() {

		// ��һ�ε�½/////////////////////////
		HttpGet httpRequest = new HttpGet(FirstUrl);
		try {
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);
			DoOnCookie.SaveCookies(httpResponse);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result1 = EntityUtils.toString(httpResponse.getEntity());
			}
			if(isLogined(result1)){  //�б��Ƿ��¼�ɹ�
				System.out.println("��½�ɹ�");
				//���ζ���/////////////////////////////////////////////////////
				HttpGet httpRequest2 = new HttpGet(SecondUrl);
				DoOnCookie.AddCookies(httpRequest2);
				HttpResponse httpResponse2 = new DefaultHttpClient().execute(httpRequest2);
				if (httpResponse2.getStatusLine().getStatusCode() == 200) {
					result2 = EntityUtils.toString(httpResponse2.getEntity());
					result2 = format(result2);
					dealStr(result2);
					return true;
				}
			}
			else{
				System.out.println("�û����������");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			System.out.println("ClientProtocolException e");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOException e");
			e.printStackTrace();
		}
		return false;

	}
	
	public boolean getOnePageInfo(){
		HttpGet httpRequest = new HttpGet(FirstUrl);
		try {
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result2 = EntityUtils.toString(httpResponse.getEntity());
				result2 = format(result2);
				dealStr(result2);
				return true;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			System.out.println("ClientProtocolException e");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOException e");
			e.printStackTrace();
		}
		return false;
	}

	
	
	/**
	 * �Ƿ��¼�ɹ�
	 * @param result
	 * @return 
	 */
	private boolean isLogined(String result) {
		
		String flag = "ѧ�����ۺϽ���"; //��½�ɹ��ı�־
		result = Regular.eregi_replace("(\\s)*", "", result);
		result = Regular.eregi_replace("<html>(.)*?<title>", "", result);
		result = Regular.eregi_replace("</title>(.)*?</html>", "", result);
		return result.equalsIgnoreCase(flag);
		
	}

	/**
	 * �Ի�ȡ�ַ������д���
	 * @param str
	 */
	public abstract void dealStr(String str);
	
	
	/**
	 * ��ҳ�ַ����ĸ�ʽ������
	 * @param origStr
	 * @return 
	 */
	public abstract String format(String origStr);


	

}
