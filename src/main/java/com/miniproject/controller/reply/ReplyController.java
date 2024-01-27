package com.miniproject.controller.reply;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.domain.ReplyDto;
import com.miniproject.domain.ReplyVo;
import com.miniproject.service.reply.ReplyService;

@RestController
@RequestMapping ("/reply/*")
public class ReplyController {
	
	@Inject
	private ReplyService rService;
	
	@RequestMapping (value="all/{boardNo}", method=RequestMethod.GET)
	public ResponseEntity<List<ReplyVo>> getAllReplies(@PathVariable("boardNo") int boardNo) throws Exception {
		System.out.println(boardNo + "번 글의 댓글 소환!");
		ResponseEntity<List<ReplyVo>> result = null;
		
		List<ReplyVo> list = rService.getAllReplies(boardNo);
		result = new ResponseEntity<List<ReplyVo>>(list, HttpStatus.OK);
		
		System.out.println(result.toString());
		
		
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

	@RequestMapping (value="{replyNo}", method=RequestMethod.POST)
	public ResponseEntity<String> updateReply(@PathVariable("replyNo") int replyNo, @RequestParam("replyText") String replyText) throws Exception {
		System.out.println(replyNo + "번 댓글 수정 시작!");
		ResponseEntity<String> result = null;
		ReplyDto dto = new ReplyDto();

		try {
			if (replyText != null && !replyText.equals("")) {
				dto.setReplyNo(replyNo);
				dto.setReplyText(replyText);
				if (rService.updateByNo(dto)){
					result = new ResponseEntity<String>("success", HttpStatus.OK);
				}
				
			} 
			else { 
				if (rService.deleteReplyByNo(replyNo)) {
					result = new ResponseEntity<String>("success", HttpStatus.OK);
				}				
			}
			
		} catch (Exception e) {
			result = new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
			e.printStackTrace();
		}
		return result;
	}
	
//	@RequestMapping (value="{replyNo}", method=RequestMethod.PUT)
//	public ResponseEntity<String> updateReply(@PathVariable("replyNo") int replyNo) throws Exception {
//		System.out.println(replyNo + "번 댓글 삭제 시작!");
//		ResponseEntity<String> result = null;
//
//		try {
//			if (rService.deleteReplyByNo(replyNo)) {
//				result = new ResponseEntity<String>("success", HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			result = new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
//			e.printStackTrace();
//		}
//		return result;
//		
//		
//	}
	
}
