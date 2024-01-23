package com.miniproject.controller.board;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

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

import com.miniproject.domain.BoardVo;
import com.miniproject.domain.UploadedFileDto;
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
	private List<UploadedFileDto> fileList = new ArrayList<>();
	
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
	 * @description : upload된 파일에 대한 정보를 JSON 파일 식의 LIST로 반환
	 * @param uploadFile
	 * @param req
	 */
	@RequestMapping (value="uploadFile", method=RequestMethod.POST)
	public @ResponseBody List<UploadedFileDto> uploadFile(MultipartFile uploadFile, HttpServletRequest req) {
		logger.info("파일을 업로드 함");
		
		System.out.println("업로드 파일의 original 이름 : " + uploadFile.getOriginalFilename());
		System.out.println("업도르 파일의 사이즈 : " + uploadFile.getSize());
		System.out.println("업도르 파일의 Content 타입 : " + uploadFile.getContentType());
		
		
		// 파일이 실제로 저장될 경로 realPath
		String realPath = req.getSession().getServletContext().getRealPath("resources/uploads");
		System.out.println("realPath : " + realPath);
		
		// 파일처리
		UploadedFileDto ufDto = null;
		ufDto = UploadFileProcess.fileUpload(uploadFile, realPath);
		if (ufDto != null ) {
			fileList.add(ufDto);
		}
		
		for (UploadedFileDto f : fileList) {
			System.out.println("현재 업로드된 파일 리스트 : " + f.toString());
		}
		return fileList;
	}
	
	@RequestMapping (value="viewBoard", method=RequestMethod.GET)
	public void viewBoardByNo(@RequestParam(name = "boardNo") int boardNo,
							  Model model) {
		logger.info("게시판 불러오기 시작");
		BoardVo vo = bService.getBoardByNo(boardNo);
		model.addAttribute("board", vo);
	}
	
	@RequestMapping ("removeFile")
	public ResponseEntity<String> removeFile(@RequestParam("removeFile") String removeFile,
						   HttpServletRequest req) {
		System.out.println(removeFile + " 삭제를 시작합니다!");
		
		ResponseEntity<String> result = null;
		String realPath = req.getSession().getServletContext().getRealPath("resources/uploads");

		
		UploadFileProcess.deleteFile(fileList, removeFile, realPath);
		
		
//		List<UploadedFileDto> removeList = new ArrayList<>();
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
		
		int index = 0;
		for (UploadedFileDto dto : fileList) {
			if (!removeFile.equals(dto.getNewFilename())) {
				index++;
			} else {
				break;
			}
			
		}
		
		fileList.remove(index);
		System.out.println("fileList 현황 : " + fileList.toString());
		
		result = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return result;
		
	}
	
	@RequestMapping("removeAllFile")
	public ResponseEntity<String> removeAllFile(HttpServletRequest req){
		System.out.println("업로드 파일 초기화를 시작합니다!");

		ResponseEntity<String> result = null;
		
		String realPath = req.getSession().getServletContext().getRealPath("resources/uploads");
		UploadFileProcess.deleteAllFile(fileList, realPath);
		
		fileList.clear();
		if (fileList.isEmpty()) {
			result = new ResponseEntity<String>("success", HttpStatus.ACCEPTED);			
		}

		return result;
		
		
	}
	
	
	
}
