package com.miniproject.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miniproject.domain.MemberVo;
import com.miniproject.service.member.MemberService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Inject
	private MemberService mService;
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws Exception 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest request) throws Exception {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		HttpSession sess = request.getSession();

		if (request.getCookies() != null) {	
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals("sessionId")) {
					System.out.println("자동로그인 체크 유저 확인");
					MemberVo vo = mService.getUserSessionInfo(cookie.getValue());
					if (vo != null && vo.getSessionLimit().getTime() > System.currentTimeMillis()) {
						System.out.println("자동로그인 선택 시간 : " + vo.getSessionLimit().getTime());
						System.out.println("현재 시간 : " + System.currentTimeMillis());
						System.out.println(vo.getSessionLimit().getTime() > System.currentTimeMillis());
						System.out.println("로그인 내용을 저장할 세션 ID : " + request.getSession().getId());
						sess.setAttribute("login", vo);
					}
				} 
				
				} 
			}
		
		
		return "index";
	}
	
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String autoLogin(HttpServletRequest request,
//							Model model,
//							RedirectAttributes rttr) throws Exception {
//		System.out.println("자동 로그인 체크 여부 확인 진행");
//		MemberVo autoLoginMember = null;
//		// 쿠키 정보 읽어오기
//		for (Cookie cookie : request.getCookies()) {
////			System.out.println(cookie.getName());
//			if (cookie.getName().equals("sessionId")) {
//				System.out.println("자동로그인 체크 유저 확인");
//				String sessionKey = (String)request.getSession().getAttribute("sessionId");
//				System.out.println(sessionKey);
//				autoLoginMember = mService.getUserSessionInfo(sessionKey);
//				if (autoLoginMember != null && autoLoginMember.getIsDelete().equals("N")) {
//					System.out.println(autoLoginMember.getUserId() + " 회원 Login 성공");
//					model.addAttribute("login", autoLoginMember);
//					return "index";
//				} else {
//					System.out.println("Login 실패");
//					rttr.addAttribute("status", "fail");
////						rttr.addFlashAttribute("status", "fail");
////						return "redirect:login?status=fail";
//					return "redirect:login";
//				}
//			}
//				
//				
//				
//		}
//		
//		return "index";
//	}
	
	@RequestMapping("/doInterceptorA")
	public void doInterceptorA (Model model) {
		logger.info("doInterceptorA () 컨트롤러 호출 완료");
		model.addAttribute("result", "doInterceptorA-result");
		
	}
	
}
