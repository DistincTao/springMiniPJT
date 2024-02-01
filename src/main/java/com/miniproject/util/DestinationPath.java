package com.miniproject.util;

import javax.servlet.http.HttpServletRequest;

public class DestinationPath {
	/**
	 * @MethodName : savePrevPath
	 * @author : DistincTao
	 * @date : 2024. 2. 1.
	 * @description : 강제로 로그인 하러온 경우, 검사하는 곳에서 이전에 있던 경로를 남기기 위한 메소드
	 * @param request
	 */
	public static void savePrevPath(HttpServletRequest request) {
		// 강제로 로그인 하러온 경우, 검사하는 곳에서 이전에 있던 경로를 남기기 위한 메소드
		String uri = request.getRequestURI();
		// 쿼리 스트링이 있는 경우~!!
		System.out.println("쿼리스트링이 있나요? : " + request.getQueryString());
		String queryStr = "";
		if (request.getQueryString() != null) {
			queryStr = request.getQueryString();
		}
		
		if (!queryStr.equals("")) {
			queryStr = "?" + queryStr;
		}
				
		if (request.getMethod().equals("GET")) {
			System.out.println("destination : " + uri + queryStr);
			
			request.getSession().setAttribute("prev_uri", uri + queryStr);
		}
	}
}
