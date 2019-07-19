package com.glad.credit.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import com.alibaba.fastjson.JSON;
import com.glad.credit.dto.ProductDto;
import com.glad.credit.util.GladAES;
import com.glad.credit.util.GladCryptoAESException;

/**
 * 身份二要素核验
 */
public class MainPCB008 {
	// TODO 请联系格兰德员工提供相关的账号信息
	public static String ACCOUNT_ID = "XXXX";
	public static String HEX_AES_128_PASSWORD = "XXX";
	public static String TEST_URI = "http://122.152.195.249:21000/credit-gw/service";
//	public static String ONLINE_URI = "https://api2.x315.com/credit-gw/service";

    //超时时间设置10s
	private static final int timeout = 20000;
	public static void main(String[] paras) throws Exception {
		String req_sn1 = UUID.randomUUID().toString().replace("-", "");
		System.out.println("请求交易号为:"+req_sn1);
		Map<String, Object> productDetailParam = new HashMap<>();
			
		//产品入参
		productDetailParam.put("name", "刘南");//姓名
		productDetailParam.put("cardNo", "130XXXX467X");//身份证号
		
		ProductDto productDto = new ProductDto();
		productDto.setAcct_id(ACCOUNT_ID);
		//产品编号
		productDto.setInf_id("P_C_B008");
		productDto.setProd_id("P_C_B008");
		productDto.setReq_time(System.currentTimeMillis());
		productDto.setRequest_sn(req_sn1); //请保证这个id唯一
		productDto.setReq_data(productDetailParam);
		GladAES gladAES;
		try {
			gladAES = new GladAES(Hex.decodeHex(HEX_AES_128_PASSWORD.toCharArray()));
			String jsonString = JSON.toJSONString(productDto);
			System.out.println("请求字符串:"+jsonString);
			byte[] encryptBytes = gladAES.encrypt(jsonString);
	        //2.2 BASE64
	        String base64String = Base64.encodeBase64String(encryptBytes);
	        System.out.println("加密字符串:"+base64String);
			String response = HttpSend(base64String,TEST_URI,gladAES);
			System.out.println("调用身份核查产品返回结果值:\n"+response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 public static String HttpSend(String paramStr,String url,GladAES gladAES) 
			 throws ClientProtocolException, IOException, GladCryptoAESException{
    	DefaultHttpClient httpClient = new DefaultHttpClient();
    	HttpParams httpParams = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, timeout);  //设定10秒超时
		HttpConnectionParams.setSoTimeout(httpParams, timeout);
		HttpPost httpPost = new HttpPost(url);

		httpPost.addHeader(HTTP.CONTENT_TYPE,
				"application/json");
		httpPost.addHeader("X_GLAD_ACCT_ID", ACCOUNT_ID);
		StringEntity se = new StringEntity(paramStr,"utf-8");
		httpPost.setEntity(se);
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		InputStream content = entity.getContent();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(content));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		String string = sb.toString();
		System.out.println("返回信息:"+string);
		byte[] encryptAESResponseByte = Base64.decodeBase64(string);
        byte[] responseByte = gladAES.decrypt(encryptAESResponseByte);
        return new String(responseByte);
    }
}
