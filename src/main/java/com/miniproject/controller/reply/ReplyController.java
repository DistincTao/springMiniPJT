package com.miniproject.controller.reply;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.domain.PagingInfoVo;
import com.miniproject.domain.ReplyDto;
import com.miniproject.domain.ReplyVo;
import com.miniproject.service.reply.ReplyService;

@RestController
@RequestMapping ("/reply/*")
public class ReplyController {
	
	@Inject
	private ReplyService rService;
	
	@RequestMapping (value="all/{boardNo}/{pageNo}", method=RequestMethod.GET)
//	@RequestMapping (value="all/{boardNo}", method=RequestMethod.GET)
//	public ResponseEntity<List<ReplyVo>> getAllReplies(@PathVariable("boardNo") int boardNo,
	public ResponseEntity<Map<String, Object>> getAllReplies(@PathVariable("boardNo") int boardNo,
													   @PathVariable("pageNo") int pageNo
														) {
		System.out.println(boardNo + "번 글의 댓글 소환!  " + pageNo + "번 페이지 댓글 가져오기");
//		ResponseEntity<List<ReplyVo>> result = null;
		ResponseEntity<Map<String, Object>> result = null;
		
		PagingInfoVo pVo = new PagingInfoVo();
		
		try {
			List<ReplyVo> list = rService.getAllReplies(boardNo);
		    
//			Map<String, Object> map = new HashMap<>();
			Map<String, Object> map = rService.getAllReplies(pageNo, boardNo);
			map.put("replyList", list);
			map.put("pageInfo", pVo);
			
//		    result = new ResponseEntity<List<ReplyVo>>(list, HttpStatus.OK);
			result = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			
			System.out.println(result.toString());
		} catch (Exception e) {
			result = new ResponseEntity<Map<String, Object>>(HttpStatus.CONFLICT);
//			result = new ResponseEntity<List<ReplyVo>>(HttpStatus.CONFLICT);
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	@RequestMapping(value="", method = RequestMethod.POST)
	public ResponseEntity<String> saveReply(@RequestBody ReplyDto newReply ){
		System.out.println("댓글 저장하러 가자");
		System.out.println(newReply.toString());
		
		ResponseEntity<String> result = null;
		try {
			if (rService.saveNewReply(newReply)) {
				result = new ResponseEntity<String>("success", HttpStatus.OK);
			}
		} catch (Exception e) {
			result = new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping (value="{replyNo}", method=RequestMethod.PUT)
	public ResponseEntity<String> updateReply(@RequestBody ReplyDto updateReply ) throws Exception {
		System.out.println(updateReply.getReplyNo() + "번 댓글 수정 시작!");
		ResponseEntity<String> result = null;
		System.out.println(updateReply.toString());
		System.out.println("결과 : " + rService.updateByNo(updateReply));

		
		try {
			if (rService.updateByNo(updateReply)){
				result = new ResponseEntity<String>("success", HttpStatus.OK);
			}
			
		} catch (Exception e) {
			result = new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
			e.printStackTrace();
		}
		return result;
	}

	
}
