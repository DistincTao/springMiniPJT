package com.miniproject.interceptor;

import javax.inject.Inject;
import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.miniproject.domain.MemberVo;
import com.miniproject.service.member.MemberService;
import com.miniproject.util.DestinationPath;


//import com.miniproject.service.member.MemberService;

public class AutoLoginInterceptor extends HandlerInterceptorAdapter {
	@Inject
	private MemberService mService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("자동 로그인 체크 여부 확인 진행");
		// 쿠키 정보 읽어오기
		// 쿠키가 있는지 없는지 검사
//		// 일반적으로 reqeust에서 쿠키목록을 받아오는 방법
//		HttpSession sess = request.getSession();
//		if (request.getCookies() != null) {	
//			for (Cookie cookie : request.getCookies()) {
//				if (cookie.getName().equals("sessionId")) {
//					System.out.println("자동로그인 체크 유저 확인");
//					MemberVo vo = mService.getUserSessionInfo(cookie.getValue());
//					if (vo != null && vo.getSessionLimit().getTime() > System.currentTimeMillis()) {
//						System.out.println("자동로그인 선택 시간 : " + vo.getSessionLimit().getTime());
//						System.out.println("현재 시간 : " + System.currentTimeMillis());
//						System.out.println(vo.getSessionLimit().getTime() > System.currentTimeMillis());
//						System.out.println("로그인 내용을 저장할 세션 ID : " + request.getSession().getId());
//						sess.setAttribute("login", vo);
//					}
// 
//				} 
//				
//			} 
//		}
		
		// WebUtils 활용한 방법
		Cookie loginCookie = WebUtils.getCookie(request, "sessionId");
		System.out.println("자동 로그인 쿠키 : " + loginCookie);
		if (loginCookie != null) {
			MemberVo vo = mService.getUserSessionInfo(loginCookie.getValue());
			if (vo != null) {
				System.out.println("자동로그인 선택 시간 : " + vo.getSessionLimit().getTime());
				System.out.println("현재 시간 : " + System.currentTimeMillis());
				System.out.println(vo.getSessionLimit().getTime() > System.currentTimeMillis());
				System.out.println("로그인 내용을 저장할 세션 ID : " + request.getSession().getId());

				WebUtils.setSessionAttribute(request, "login", vo);
			}

		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

//		DestinationPath.savePrevPath(request);
//		if (WebUtils.getSessionAttribute(request, "prev_uri") != null) {
//			response.sendRedirect((String)WebUtils.getSessionAttribute(request, "prev_uri"));
//		} 
		
	}

}
