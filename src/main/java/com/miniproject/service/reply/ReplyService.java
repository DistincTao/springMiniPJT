package com.miniproject.service.reply;

import java.util.List;

import com.miniproject.domain.ReplyDto;
import com.miniproject.domain.ReplyVo;

public interface ReplyService {

	// 모든 댓글 가져오기
	List<ReplyVo> getAllReplies(int boardNo) throws Exception;

	boolean saveNewReply(ReplyDto newReply) throws Exception;

	boolean updateByNo(ReplyDto updateReply) throws Exception;
	
}
