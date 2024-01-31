package com.miniproject.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TestInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("TestInterceptor preHandle() 호출 완료");
		
		HandlerMethod method = (HandlerMethod)handler;
		Method methodObj = method.getMethod();
		
		System.out.println("Bean : " + method.getBean());
		System.out.println("Method : " + methodObj);
		return true; // true : 원래의 컨트롤러 단을 호출 || false : 현재 위치에서 중지! 호출하지 않음
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
				
		System.out.println("TestInterceptor postHandle() 호출 완료");

		Object result = modelAndView.getModel().get("result");
		System.out.println("result : " + result);
		if (result != null) {
			request.getSession().setAttribute("result", result);
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		System.out.println("TestInterceptor afterCompletion() 호출 완료");

	}
	
	
	
}
