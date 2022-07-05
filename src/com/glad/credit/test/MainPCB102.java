package com.glad.credit.test;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import com.alibaba.fastjson.JSON;
import com.glad.credit.dto.ProductDto;
import com.glad.credit.util.GladAES;
import com.glad.credit.util.HttpUtils;
import com.glad.credit.util.ImageUtils;

/**
 * 人脸比对
 */
public class MainPCB102 {
	// TODO 请联系格兰德员工提供相关的账号信息
	public static String ACCOUNT_ID = "XXXX";
	public static String HEX_AES_128_PASSWORD = "XXXX";
	public static String TEST_URI = "http://122.152.195.249:21000/credit-gw/service";

	public static void main(String[] paras) throws Exception {
		String req_sn1 = UUID.randomUUID().toString().replace("-", "");
		System.out.println("request_sn:"+req_sn1);
		long start01 = new Date().getTime();
		Map<String, Object> productDetailParam = new HashMap<>();
		String imgPath = System.getProperty("user.dir")
				+ File.separator + "config" + File.separator + "qrping.jpg";
		//params
		productDetailParam.put("name", "刘南");//姓名
		productDetailParam.put("cardNo", "1309XXXXX67X");//身份证号
		productDetailParam.put("photo", ImageUtils.encodeFileToBase64(imgPath));//活体照
		System.out.println("图片加载完成，耗时："+(new Date().getTime()-start01)+" ms");
		long start02 = new Date().getTime();
		ProductDto productDto = new ProductDto();
		productDto.setAcct_id(ACCOUNT_ID);
		//product_id
		productDto.setInf_id("P_C_B102");
		productDto.setProd_id("P_C_B102");
		productDto.setReq_time(System.currentTimeMillis());
		productDto.setRequest_sn(req_sn1); //请保证这个id唯一
		productDto.setReq_data(productDetailParam);
		GladAES gladAES;
		try {
			gladAES = new GladAES(Hex.decodeHex(HEX_AES_128_PASSWORD.toCharArray()));
			String jsonString = JSON.toJSONString(productDto);
			System.out.println("实例初始化完成，耗时："+(new Date().getTime()-start02)+" ms");
			long start03 = new Date().getTime();
			byte[] encryptBytes = gladAES.encrypt(jsonString);
	        //2.2 BASE64
	        String base64String = Base64.encodeBase64String(encryptBytes);
	        System.out.println("加密完成，耗时："+(new Date().getTime()-start03)+" ms");
			String response = HttpUtils.HttpSend(base64String,TEST_URI,gladAES,ACCOUNT_ID);
			System.out.println("response:"+response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
