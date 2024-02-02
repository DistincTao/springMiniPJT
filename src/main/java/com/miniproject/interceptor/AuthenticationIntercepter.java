package com.miniproject.interceptor;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.miniproject.domain.MemberVo;
import com.miniproject.service.board.BoardService;
import com.miniproject.util.DestinationPath;

public class AuthenticationIntercepter extends HandlerInterceptorAdapter {

	@Inject
	private BoardService bService;
	
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

			// writer정보를 쿼리스트링이 아닌, service => dao를 거쳐 writer정보를 가져오는 방법
			String boardNo = request.getParameter("boardNo");
			String writer = bService.getBoardWriterByBoardNo(boardNo);
//			String loginMember= (String)((MemberVo)sess.getAttribute("login")).getUserId();
			
			// 수정 버튼에 writer 정보를 쿼리 스트링으로 추가하여 처리하는 경우
			//Witer 찾기
//			String writer = request.getParameter("writer");
			MemberVo loginMember = (MemberVo) sess.getAttribute("login");
			System.out.println("수정하러 갈 작성자 : " + writer);
			System.out.println("로그인한 유저 : " + ((MemberVo)sess.getAttribute("login")).getUserId());
//			System.out.println("로그인한 유저 : " + loginMember);
						
//			if (uri.indexOf("/updateBoard") != -1 || uri.indexOf("/deleteBoard") != -1) {
			if (uri.contains("/updateBoard") || uri.contains("/deleteBoard")) {
				// 수정/삭제의 경우 로그인 usderId == 작성자 writer 조건을 만족해야함
				System.out.println("로그인 상태에서 수정하러 도착");
//				if (!loginMember.equals(writer)) {
//					response.sendRedirect("viewBoard?boardNo=" + boardNo + "&status=noPermission");
//					result = false;
//				}
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
