package com.glad.credit.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	private static final int timeout = 10000;
	public static String HttpSend(String param,String url,GladAES gladAES,String acct_id){
		 String result = "";
		 CloseableHttpClient httpClient = HttpClients.custom().build();
		 CloseableHttpResponse response = null;
		 StringEntity entity = new StringEntity(param, Charset.forName("UTF-8"));
	        entity.setContentEncoding("UTF-8");
	        entity.setContentType("application/json");
	     
		 HttpPost httpPost = new HttpPost(url);
		 RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeout).
				 setSocketTimeout(timeout).setConnectTimeout(timeout).build();
		 httpPost.setConfig(requestConfig);
		 httpPost.addHeader(HTTP.CONTENT_TYPE,
					"application/json");
		 httpPost.addHeader("X_GLAD_ACCT_ID", acct_id);
		 httpPost.setEntity(entity);
		 try {
			long start01 = new Date().getTime();
			response = httpClient.execute(httpPost);
			System.out.println("http调用完成，耗时："+(new Date().getTime()-start01)+" ms");
			if(response != null){
					HttpEntity res_entity = response.getEntity();
				    result = entityToString(res_entity);
				    System.out.println("返回加密信息:"+result);
				    byte[] encryptAESResponseByte = Base64.decodeBase64(result);
			        byte[] responseByte = gladAES.decrypt(encryptAESResponseByte);
			        return new String(responseByte);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{			
				try {
					if(httpClient!=null)
						httpClient.close();
					if(response!=null)
						response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
   }
	public static String entityToString(HttpEntity entity) throws IOException {
        String result = null;
        if(entity != null){
            long lenth = entity.getContentLength();
            if(lenth != -1 && lenth < 2048){
                result = EntityUtils.toString(entity,"UTF-8");
            }else {
               InputStreamReader reader1 = new InputStreamReader(entity.getContent(), "UTF-8");
                CharArrayBuffer buffer = new CharArrayBuffer(2048);
                char[] tmp = new char[1024];
                int l;
                while((l = reader1.read(tmp)) != -1) {
                    buffer.append(tmp, 0, l);
                }
                result = buffer.toString();
            }
        }
        return result;
    }
}
