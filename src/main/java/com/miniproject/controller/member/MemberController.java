package com.miniproject.controller.member;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.miniproject.domain.MemberPointVo;
import com.miniproject.domain.MemberVo;
import com.miniproject.domain.PagingInfoVo;
import com.miniproject.service.member.MemberService;
import com.miniproject.util.PagingProcess;
import com.miniproject.util.UploadFileProcess;

/**
 * @packageName : com.miniproject.controller.board
 * @fileName : BoardController.java
 * @author : DistincTao
 * @date : 2024. 1. 22.
 * @description : 
 */
@Controller
@RequestMapping ("/member/*")
public class MemberController {
	
	@Inject
	MemberService mService; 

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private HttpSession sess;

	
	@RequestMapping("register")
	public void register() {
		logger.info("register이 호출됨");
		
	}
	
	@RequestMapping("login")
	public void logIn() {
		logger.info("login이 호출됨");
		
	}

	@RequestMapping("logout")
	public void logOut(HttpServletResponse resp) {
		logger.info("logouy이 호출됨");
		try {
			sess.removeAttribute("login");
			sess.invalidate();
			resp.sendRedirect("/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@RequestMapping("mypage")
	public void myPage(
//			@RequestParam("pageNo") String pageNo,
					   @RequestParam("userId") String userId,
					   Model model) {
		logger.info("mypage이 호출됨");
		
		
		List<MemberPointVo> pointList = mService.getMemberPoint(userId);
		model.addAttribute("pointlog", pointList);
		
	}
	
	@RequestMapping(value = "loginMember", method=RequestMethod.POST)
	public void loginMember(@RequestParam("userId") String userId, 
							@RequestParam("userPwd") String userPwd,
							HttpServletRequest req,
							HttpServletResponse resp) {
		logger.info("로그인 절차 시작");
		
		MemberVo vo = mService.getLoginUserInfo(userId, userPwd);


		
		sess = req.getSession();
		try {
			if (vo != null && !vo.getIsDelete().equals("Y")) {
				sess.setAttribute("login", vo);
				resp.sendRedirect("/");
			} else {
				resp.sendRedirect("register");
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	@RequestMapping("writeBoard")
	public void showWriteBoard () {
		logger.info("writeBoard가 호출됨");
		
	}
	
	
	/**
	 * @MethodName : uploadFile
	 * @author : DistincTao
	 * @date : 2024. 1. 22.
	 * @description : 
	 * @param uploadFile
	 * @param req
	 */
	@RequestMapping (value="uploadFile", method=RequestMethod.POST)
	public void uploadFile(MultipartFile uploadFile, HttpServletRequest req) {
		logger.info("파일을 업로드 함");
		
		System.out.println("업로드 파일의 original 이름 : " + uploadFile.getOriginalFilename());
		System.out.println("업도르 파일의 사이즈 : " + uploadFile.getSize());
		System.out.println("업도르 파일의 Content 타입 : " + uploadFile.getContentType());
		
		
		// 파일이 실제로 저장될 경로 realPath
		String realPath = req.getSession().getServletContext().getRealPath("resources/uploads");
		System.out.println("realPath : " + realPath);
		
		// 파일처리
		UploadFileProcess.fileUpload(uploadFile, realPath);
		
	}
	

	
}
