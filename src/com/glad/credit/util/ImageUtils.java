package com.glad.credit.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import com.bankcomm.gbicc.util.base64.BASE64Decoder;
import com.bankcomm.gbicc.util.base64.BASE64Encoder;

/**
* @Title: 图片类工具类  
* @date 2019年1月20日 下午4:18:34 
* @version V1.0
 */
public class ImageUtils {
    /**
     * 将网络图片进行Base64位编码
     * 
     * @param imgUrl
     *            图片的url路径，如http://.....xx.jpg
     * @return
     * @throws IOException 
     */
    public static String encodeImgageToBase64(URL imageUrl) throws IOException {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        ByteArrayOutputStream outputStream = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(imageUrl);
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
        } catch (Exception e1) {
            e1.printStackTrace();
        }finally{
        	outputStream.close();
        }
        // 对字节数组Base64编码
        return new Base64().encodeBase64String(outputStream.toByteArray());// 返回Base64编码过的字节数组字符串
    }

    /**
     * 将本地图片进行Base64位编码
     * 
     * @param imgUrl
     *            图片的url路径，如http://.....xx.jpg
     * @return
     * @throws IOException 
     */
    public static String encodeImgageToBase64(File imageFile) throws IOException {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        ByteArrayOutputStream outputStream = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
        } catch (Exception e1) {
            e1.printStackTrace();
        }finally{
        	outputStream.close();
        }
        // 对字节数组Base64编码
        return new Base64().encodeBase64String(outputStream.toByteArray());// 返回Base64编码过的字节数组字符串
    }

    /**
     * 将Base64位编码的图片进行解码，并保存到指定目录
     * 
     * @param base64
     *            base64编码的图片信息
     * @return
     */
    public static void decodeBase64ToImage(String base64, String path,
            String imgName) {
        BASE64Decoder decoder = new BASE64Decoder();
        FileOutputStream write = null;
        try {
            write = new FileOutputStream(new File(path
                    + imgName));
            byte[] decoderBytes = decoder.decodeBuffer(base64);
            write.write(decoderBytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
        	try {
				write.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
    
    
    /**
     * 将文件转成base64 字符串
     * @param path文件路径
     * @return  * 
     * @throws Exception
     */

	public static String encodeFileToBase64(String fullPath) throws Exception {
		File file = new File(fullPath);
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();
		return new BASE64Encoder().encode(buffer).replaceAll("\r|\n", "");

	}
    /**
     * 将Base64位编码的文件进行解码，并保存到指定目录
     * 
     * @param base64
     * @return
     */
    public static void decodeBase64ToFile(String base64, String fullPath) {
        BASE64Decoder decoder = new BASE64Decoder();
        FileOutputStream write = null;
        try {
            write = new FileOutputStream(new File(fullPath));
            byte[] decoderBytes = decoder.decodeBuffer(base64);
            write.write(decoderBytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
        	try {
				write.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
}
