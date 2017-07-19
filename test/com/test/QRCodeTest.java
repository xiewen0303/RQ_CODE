package com.test;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class QRCodeTest {
	
	public static void main(String[] args) {
		testEncode();
	}
	
	  public static void testEncode() {  
	        try {
				String filePath = "E://";  
				String fileName = "zxing.png";  
//				JSONObject json = new JSONObject();  
//				json.put(  
//				        "zxing",  
//				        "https://github.com/zxing/zxing/tree/zxing-3.0.0/javase/src/main/java/com/google/zxing");  
//				json.put("author", "shihy");  
//				String content = json.toJSONString();// 内容  
				
				String content = "http://raider-demo.gametrees.com/";// 内容  
				int width = 200; // 图像宽度
				int height = 200; // 图像高度
				String format = "png";// 图像类型
				Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
				hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
				BitMatrix bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵  
				Path path = FileSystems.getDefault().getPath(filePath, fileName);  
				MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像  
				System.out.println("输出成功.");
			} catch (Exception e) {
				e.printStackTrace();
			}  
	    }  
}
