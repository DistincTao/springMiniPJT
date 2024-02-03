package com.miniproject.interceptor;

import java.sql.Timestamp;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.miniproject.domain.MemberVo;
import com.miniproject.domain.SessionDto;
import com.miniproject.service.member.MemberService;
import com.miniproject.util.SessionCheck;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Inject
	private MemberService mService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("LoginInterceptor - preHandle() : 로그인 처리 준비");
		System.out.println("redirectUrl : " + request.getParameter("redirectUrl"));
		System.out.println("boardNo : " + request.getParameter("boardNo"));
		
		// 댓글 작성 로그인 (이전 경로저장) 처리
		if (request.getMethod().equals("GET") && request.getParameter("redirectUrl") != null) {
			if (!request.getParameter("redirectUrl").equals("")) {
				if (request.getParameter("redirectUrl").contains("viewBoard")) {
					//  댓글 달다가 로그인으로 넘어온 경우
					String uri = "/board/viewBoard";
					String queryStr = "?boardNo=" + request.getParameter("boardNo") + "&pageNo=" + request.getParameter("pageNo");
					
					request.getSession().setAttribute("returnPath", uri + queryStr);
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("LoginInterceptor - postHandle() : 로그인 처리하러 DB 다녀와서 이제는 마무리 중");
		HttpSession sess = request.getSession();
		ModelMap modelMap = modelAndView.getModelMap();
		MemberVo loginMember = (MemberVo) modelMap.get("login");
		String returnPath = "";
		if (loginMember != null) {
			System.out.println("현재 로그인한 맴버 : " + loginMember.toString());
			sess.setAttribute("login", loginMember);

			// 중복 로그인 체크
			SessionCheck.replaceSessionKey(sess, loginMember.getUserId());
			
			// 자동 로그인 처리
//			자동 로그인을 체크한 유저이면 ("on"이 전달이 되고, null 이 아닌 경우)
			if (request.getParameter("remember") != null) {
				System.out.println("자동 로그인 유저입니다!!");
//			⇒ 쿠키 생성 (loginCookie = sessionId, 만료일 : 1주일)
				String sessionValue = sess.getId();
				Timestamp sessLimit = new Timestamp(System.currentTimeMillis() + (7 * 24 * 3600 * 1000));
				// 쿠키 객체 생성
				Cookie loginCookie = new Cookie("sessionId", sessionValue);
				loginCookie.setMaxAge(7 * 24 * 60 * 60); // 초단위로 기간 설정
				loginCookie.setPath("/");
				
//			⇒ DB의 member table에 session_limit, session_key를 저장
				SessionDto dto = new SessionDto(loginMember.getUserId(), sessLimit, sessionValue);
				if (mService.remember(dto)) {
					response.addCookie(loginCookie); // 쿠키 저장
				}
			}

			if (sess.getAttribute("prev_uri") != null) {
				returnPath = (String)sess.getAttribute("prev_uri");
//				response.sendRedirect((String)sess.getAttribute("prev_uri"));
			} 

//			response.sendRedirect("/");
			response.sendRedirect(!returnPath.equals("")? returnPath : "/");
		} 
	}
}


