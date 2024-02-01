package com.miniproject.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.miniproject.domain.MemberVo;
import com.miniproject.util.DestinationPath;

public class AuthenticationIntercepter extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean result = false;
		
		System.out.println("AuthenticationIntercepter - preHandle() 로그인 여부를 검사하러 왔습니다! ");
		HttpSession sess = request.getSession();
		System.out.println( request.getServletPath());
		System.out.println( request.getRequestURI());
		DestinationPath.savePrevPath(request);
		
		if (sess.getAttribute("login") != null) {
			System.out.println("로그인 확인 완료!!! 갈길 가시오~~!");
			
			String uri = request.getRequestURI();
			// 수정 버튼에 writer 정보를 쿼리 스트링으로 추가하여 처리하는 경우
			
			//Witer 찾기
			String writer = request.getParameter("writer");
			MemberVo loginMember = (MemberVo) sess.getAttribute("login");
			System.out.println("수정하러 갈 작성자 : " + writer);
			System.out.println("로그인한 유저 : " + ((MemberVo)sess.getAttribute("login")).getUserId());
						
			if (uri.indexOf("/updateBoard") != -1 || uri.indexOf("/deleteBoard") != -1) {
				// 수정/삭제의 경우 로그인 usderId == 작성자 writer 조건을 만족해야함
				System.out.println("로그인 상태에서 수정하러 도착");
				if (!loginMember.getUserId().equals(writer)) {
					response.sendRedirect("viewBoard?boardNo=" + request.getParameter("boardNo")
										  + "&writer=" + writer + "&status=noPermission");
					result = false;
				}
			}
			
			result = true;
		} else { // 로그인 안했음
			System.out.println("로그인 하고 오시오!! 로그인 페이지로 가시오~~!");
//			sess.setAttribute("prev_url", request.getRequestURI());
			response.sendRedirect("/member/login");
		}
		
		return result;
	}

	
	
	
}
