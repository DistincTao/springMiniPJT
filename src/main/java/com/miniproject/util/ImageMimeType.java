package com.miniproject.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

public class ImageMimeType {

	private static Map<String, String> imgMimeType;
	
	{
		// 인스턴스 멤버 변수 초기화 블럭
	}
	
	static { // static 변수의 초기화 블럭
		imgMimeType = new HashMap<String, String>();
		imgMimeType.put("jpg", MediaType.IMAGE_JPEG.toString());
		imgMimeType.put("jpe", MediaType.IMAGE_JPEG.toString());
		imgMimeType.put("jpeg", MediaType.IMAGE_JPEG.toString());
		imgMimeType.put("gif", MediaType.IMAGE_GIF_VALUE);
		imgMimeType.put("png", MediaType.IMAGE_PNG.toString());
	} 	

	public static boolean isImageExt(String ext) {
		return imgMimeType.containsKey(ext);
	}
	
	public static boolean isImageContentType(String contentType) {
		return imgMimeType.containsValue(contentType);
	}
	
}
