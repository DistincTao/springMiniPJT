package com.miniproject.controller.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.miniproject.domain.BoardDto;
import com.miniproject.domain.BoardVo;
import com.miniproject.domain.PagingInfoVo;
import com.miniproject.domain.ReadcountprocessDto;
import com.miniproject.domain.SearchCriteriaDto;
import com.miniproject.domain.UploadedFileDto;
import com.miniproject.service.board.BoardService;
import com.miniproject.util.GetUserIPAddr;
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
	HttpSession sess;
	
//	@RequestMapping(value="listAll", method=RequestMethod.POST)
	@RequestMapping(value="listAll", method=RequestMethod.GET)
	public void listAll(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
					    @RequestParam(name = "searchType", defaultValue = "") String searchType,
					    @RequestParam(name = "searchWord", defaultValue = "") String searchWord,
						Model model) throws Exception {
		logger.info(pageNo + "페이지의 listAll이 호출됨");
		logger.info(searchWord + " " + searchType);

		Map<String, Object> map = null;
		SearchCriteriaDto searchDto = new SearchCriteriaDto(searchWord, searchType);			

		map = bService.getEntireBoard(pageNo, searchDto);			

		model.addAttribute("boardList", (List<BoardVo>)map.get("boardList"));
		model.addAttribute("pageInfo", (PagingInfoVo)map.get("pageInfo"));
	}

	
	@RequestMapping("writeBoard")
	public void showWriteBoard (HttpSession sess) {
		logger.info("writeBoard가 호출됨");
		
		String uudi = UUID.randomUUID().toString();
		sess.setAttribute("csrfToken", uudi); // Session에 바인딩
	}
	
	@RequestMapping(value="writeBoard", method=RequestMethod.POST)
	public String writeBoard(BoardDto dto,
						   @RequestParam(name = "csrfToken") String inputcsrf,
						   HttpSession sess) {
		
		logger.info("게시판 글작성 : " + dto.toString());
		logger.info("csrf : " + inputcsrf);
		
		String redirectPage = "";
		if (((String)sess.getAttribute("csrfToken")).equals(inputcsrf)) {
			//csrfToken이 같은 경우에만 게시들을 저장
			try {
				bService.saveNewBoard(dto, fileList);
				redirectPage = "listAll";
			} catch (Exception e) {
				e.printStackTrace();
				redirectPage = "listAll?status=fail";
			}
		}
		return "redirect:" + redirectPage;
	}
	
	
//	@RequestMapping ("viewBoard")
//	public void viewBoard(@RequestParam("boardNo") int boardNo) {
//		logger.info(boardNo + "번 글 상세 페이지");
//		
//		bService.getBoardByNo(boardNo, userId);
//
//	}
	
	@RequestMapping ("viewBoard")
	public void viewBoardByNo(@RequestParam(name = "boardNo") int boardNo,
							  Model model,
							  HttpServletRequest req) throws Exception {
		logger.info("게시판 불러오기 시작");
		String ipAddr = GetUserIPAddr.getIp(req);
		ReadcountprocessDto dto = new ReadcountprocessDto();
		dto.setBoardNo(boardNo);
		dto.setIpAddr(ipAddr);
		Map <String, Object> result = bService.getBoardByNo(dto);
		
		model.addAttribute("board", result.get("board"));
		model.addAttribute("fileList", result.get("fileList"));
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
	
//	@RequestMapping(value="updateBoard",  method=RequestMethod.POST)
	@RequestMapping("updateBoard")
	public void updateBoard(@RequestParam("boardNo") int boardNo,
							@RequestParam("writer") String writer
//							,
//							@RequestParam("title") String title,
//							@RequestParam("content") String content
							) throws Exception {
	
		System.out.println(boardNo + "번 글 업데이트를 시작합니다!");
	}

	@RequestMapping("deleteBoard")
	public void deleteBoard(@RequestParam("boardNo") int boardNo,
			@RequestParam("writer") String writer
//							,
//							@RequestParam("title") String title,
//							@RequestParam("content") String content
			) throws Exception {
		
		System.out.println(boardNo + "번 글 삭제를 시작합니다!");
	}
	
	
}
