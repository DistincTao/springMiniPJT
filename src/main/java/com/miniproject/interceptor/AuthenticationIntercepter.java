package com.miniproject.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthenticationIntercepter extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean result = false;
		
		System.out.println("AuthenticationIntercepter - preHandle() 로그인 여부를 검사하러 왔습니다! ");
		HttpSession sess = request.getSession();
		
		if (sess.getAttribute("login") != null) {
			System.out.println("로그인 확인 완료!!! 갈길 가시오~~!");
			result = true;
		} else { // 로그인 안했음
			System.out.println("로그인 하고 오시오!! 로그인 페이지로 가시오~~!");
			sess.setAttribute("prev_url", request.getServletPath());
			response.sendRedirect("/member/login");
		}
		
		return result;
	}

	
	
	
}
