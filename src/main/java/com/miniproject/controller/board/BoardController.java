package com.miniproject.controller.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.miniproject.domain.BoardVo;
import com.miniproject.service.board.BoardService;
import com.miniproject.util.UploadFileProcess;

/**
 * @packageName : com.miniproject.controller.board
 * @fileName : BoardController.java
 * @author : DistincTao
 * @date : 2024. 1. 22.
 * @description : 
 */
@Controller
@RequestMapping ("/board/*")
public class BoardController {
	
	@Inject
	BoardService bService; 

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@RequestMapping("listAll")
	public void listAll(Model model) {
		logger.info("listAll이 호출됨");
		
		List<BoardVo> list = bService.getEntireBoard();
		model.addAttribute("boardList", list);
		
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
