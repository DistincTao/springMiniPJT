package com.miniproject.controller.member;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miniproject.domain.MemberDto;
import com.miniproject.domain.MemberVo;
import com.miniproject.domain.PointlogVo;
import com.miniproject.domain.UploadedFileDto;
import com.miniproject.service.member.MemberService;
import com.miniproject.util.SessionCheck;
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
	private UploadedFileDto ufDto;
	
	
	@RequestMapping("login")
	public void logIn() {
		logger.info("login이 get 방식으로 호출됨");
		
		// member/login.jsp 가 반환
		
	}	
	
	@RequestMapping(value = "login", method=RequestMethod.POST)
	public String loginMember(MemberDto mDto, Model model,
							  RedirectAttributes rttr
			) throws Exception {
		logger.info(mDto.toString() + "로 로그인 시작!");
		MemberVo vo = mService.getLoginUserInfo(mDto);
		
		if (vo != null && vo.getIsDelete().equals("N")) {
			System.out.println(vo.getUserId() + " 회원 Login 성공");
			model.addAttribute("login", vo);
			return "index";
		} else {
			System.out.println("Login 실패");
			rttr.addAttribute("status", "fail");
//			rttr.addFlashAttribute("status", "fail");
//			return "redirect:login?status=fail";
			return "redirect:login";
		}
	}
	
	
	
//	// 일반적인 로그인 처리
//	@RequestMapping("login")
//	public void logIn() {
//		logger.info("login이 get 방식으로 호출됨");
//		
//	}
//
//	@RequestMapping(value = "login", method=RequestMethod.POST)
//	public String loginMember(@RequestParam("userId") String userId, 
//							@RequestParam("userPwd") String userPwd,
//							HttpServletRequest req) throws Exception {
//		logger.info("로그인 절차 시작");
//		MemberVo vo = mService.getLoginUserInfo(userId, userPwd);
//		
//		String result = "";
//		
//		sess = req.getSession();
//		if (vo != null && !vo.getIsDelete().equals("Y")) {
//			sess.setAttribute("login", vo);
//			result = "redirect:/";
//		} 
//		return result;
//		
//	}
	
	@RequestMapping("register")
	public void register() {
		logger.info("register이 호출됨");

	}

	@RequestMapping(value="register", method=RequestMethod.POST)
	public String register(MemberDto dto) throws Exception {
		logger.info("회원 가입 시작");
					
		mService.saveNewMember(dto, ufDto);
				
		return "redirect:/member/login";
	}	

	@RequestMapping("mypage")
	public void myPage(
//			@RequestParam("pageNo") String pageNo,
					   @RequestParam("userId") String userId,
					   Model model) throws Exception {
		logger.info("mypage이 호출됨");
		
		List<PointlogVo> pointList = mService.getMemberPoint(userId);
		model.addAttribute("pointlog", pointList);
	}
	
	@RequestMapping("logout")
	public String logOut(HttpServletRequest request) {
		logger.info("logout이 호출됨");
		sess = request.getSession();
//		if (sess.getAttribute("login") != null) { // 일반적인 로그 아웃
//			sess.removeAttribute("login");
//			sess.invalidate();
//		}
		// 로그아웃 할 때, 세션 map에 담겨진 세션 제거
		if((MemberVo)sess.getAttribute("login") != null) {
			SessionCheck.removeSessionKey(((MemberVo)sess.getAttribute("login")).getUserId());
		}
		return "redirect:/";
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
	public @ResponseBody UploadedFileDto uploadFile(MultipartFile uploadFile, HttpServletRequest req) {
		logger.info("파일을 업로드 함");
		
		System.out.println("업로드 파일의 original 이름 : " + uploadFile.getOriginalFilename());
		System.out.println("업도르 파일의 사이즈 : " + uploadFile.getSize());
		System.out.println("업도르 파일의 Content 타입 : " + uploadFile.getContentType());
		
		// 파일이 실제로 저장될 경로 realPath
		String realPath = req.getSession().getServletContext().getRealPath("resources/uploads");
		System.out.println("realPath : " + realPath);
		
		// 파일처리

		if (UploadFileProcess.fileUpload(uploadFile, realPath) != null ) {
			ufDto = UploadFileProcess.fileUpload(uploadFile, realPath);			
		}
		return ufDto;
	}
	
	@RequestMapping ("removeFile")
	public ResponseEntity<String> removeFile(@RequestParam("removeFile") String removeFile,
						   HttpServletRequest req) {
		System.out.println(removeFile + " 삭제를 시작합니다!");
		
		ResponseEntity<String> result = null;
		String realPath = req.getSession().getServletContext().getRealPath("resources/uploads");

		UploadFileProcess.deleteFile(ufDto, removeFile, realPath);
		
//		for (UploadedFileDto f : fileList) {
//			if (f.getNewFilename().equals(removeFile)) {
//				removeList.add(f);
//			}
//		}
//		for (UploadedFileDto r : removeList) {
//			if (r.getNewFilename().equals(removeFile)) {
//				fileList.remove(r);
//			}
//		}
		
		if (removeFile.equals(ufDto.getNewFilename())) {
			ufDto = null;
		}
		result = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return result;
	}
	
	@RequestMapping("removeAllFile")
	public ResponseEntity<String> removeAllFile(HttpServletRequest req){
		System.out.println("업로드 파일 초기화를 시작합니다!");

		ResponseEntity<String> result = null;
		
		String realPath = req.getSession().getServletContext().getRealPath("resources/uploads");
		UploadFileProcess.deleteAllFile(ufDto, realPath);
		
		ufDto = null;
		if (ufDto == null) {
			result = new ResponseEntity<String>("success", HttpStatus.ACCEPTED);			
		}
		return result;	
	}
	
}
