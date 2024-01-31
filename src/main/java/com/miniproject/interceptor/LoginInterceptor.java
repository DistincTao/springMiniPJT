package com.miniproject.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.miniproject.domain.MemberVo;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("LoginInterceptor - preHandle() : 로그인 처리 준비");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("LoginInterceptor - postHandle() : 로그인 처리하러 DB 다녀와서 이제는 마무리 중");
		HttpSession sess = request.getSession();
		ModelMap modelMap = modelAndView.getModelMap();
		MemberVo loginMember = (MemberVo) modelMap.get("login");
		
		if (loginMember != null) {
			System.out.println("현재 로그인한 맴버 : " + loginMember.toString());
			sess.setAttribute("login", loginMember);
			
			if (sess.getAttribute("prev_url") != null) {
				response.sendRedirect((String)sess.getAttribute("prev_url"));
			}
			response.sendRedirect("/");
		} 
		
	}

	
	
}
