package com.miniproject.persistence.reply;

import java.util.List;

import com.miniproject.domain.PagingInfoVo;
import com.miniproject.domain.ReplyDto;
import com.miniproject.domain.ReplyVo;

public interface ReplyDao {
	
	List<ReplyVo> selectAllReplies(int boardNo, PagingInfoVo pageVo) throws Exception;
	List<ReplyVo> selectAllReplies(int boardNo) throws Exception;

	int insertNewReply(ReplyDto newReply) throws Exception;

	int updateReply(ReplyDto updateReply) throws Exception;

	int deleteReply(int replyNo) throws Exception;
	
	
}
