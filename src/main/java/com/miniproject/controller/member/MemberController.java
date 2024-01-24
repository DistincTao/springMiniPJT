package com.miniproject.controller.member;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

import com.miniproject.domain.MemberDto;
import com.miniproject.domain.MemberVo;
import com.miniproject.domain.PointlogVo;
import com.miniproject.domain.UploadedFileDto;
import com.miniproject.service.member.MemberService;
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
//	private List<UploadedFileDto> fileList = new ArrayList<>();
	private UploadedFileDto ufDto;
	
	
	@RequestMapping("login")
	public void logIn() {
		logger.info("login이 호출됨");
		
	}

	@RequestMapping(value = "login", method=RequestMethod.POST)
	public String loginMember(@RequestParam("userId") String userId, 
							@RequestParam("userPwd") String userPwd,
							HttpServletRequest req) throws Exception {
		logger.info("로그인 절차 시작");
		MemberVo vo = mService.getLoginUserInfo(userId, userPwd);
		
		String result = "";
		
		sess = req.getSession();
		if (vo != null && !vo.getIsDelete().equals("Y")) {
			sess.setAttribute("login", vo);
			result = "redirect:/";
		} else {
			result = "redirect:/member/login?status=success";
		}
		return result;
		
	}
	
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
	public String logOut() {
		logger.info("logouy이 호출됨");

		sess.removeAttribute("login");
		sess.invalidate();

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
