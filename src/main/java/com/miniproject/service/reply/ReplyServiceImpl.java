package com.miniproject.service.reply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.domain.PagingInfoVo;
import com.miniproject.domain.PointlogDto;
import com.miniproject.domain.ReplyDto;
import com.miniproject.domain.ReplyVo;
import com.miniproject.persistence.member.MemberDao;
import com.miniproject.persistence.paging.PagingDao;
import com.miniproject.persistence.pointlog.PointlogDao;
import com.miniproject.persistence.reply.ReplyDao;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Inject
	ReplyDao rDao;
	@Inject
	MemberDao mDao;
	@Inject
	PointlogDao pDao;
	@Inject
	PagingDao pageDao;
	
	@Override
//	public List<ReplyVo> getAllReplies(int pageNo, int boardNo) throws Exception {
	public Map<String, Object> getAllReplies(int pageNo, int boardNo) throws Exception {

		
		PagingInfoVo pageVo = getPagingInfo(pageNo, boardNo);				
		List<ReplyVo> list = rDao.selectAllReplies(boardNo, pageVo);
					
		Map<String, Object> replyList = new HashMap<>();
		
		replyList.put("replyList", list);
		replyList.put("pageInfo", pageVo);
		
		return replyList;
	}
	
	@Override
	public List<ReplyVo> getAllReplies(int boardNo) throws Exception {
		List<ReplyVo> list = rDao.selectAllReplies(boardNo);
		
		return list;
	}

	@Override
	@Transactional(rollbackFor = Exception.class,
	   			   isolation = Isolation.READ_COMMITTED)
	public boolean saveNewReply(ReplyDto newReply) throws Exception {
		boolean result = false;
		
		// 새 댓글 저장 (insert)
		if (rDao.insertNewReply(newReply) == 1) {
			// point 부여 (댓글 작성 1점)
			if (mDao.updateUserPoint("write_reply", newReply.getReplier()) == 1) {
				// 포인트 로그 저장
				pDao.insertPointlog(new PointlogDto(-1, null, "write_reply", 1, newReply.getReplier()));
				result = true;
			}			
		}
		
		return result;
		
	}

	@Override
	public boolean updateByNo(ReplyDto updateReply ) throws Exception {
		boolean result = false;
		System.out.println("내용 수정 업데이트 시작");
		System.out.println(updateReply.toString());
		
		if (updateReply.getReplyText() != null && !updateReply.getReplyText().equals("")) {
			if (rDao.updateReply(updateReply) == 1){
				result = true;
			}
			
		} 
		else if (updateReply.getReplyText().equals("")){ 
			if (rDao.deleteReply(updateReply.getReplyNo()) == 1) {
				result = true;
			}				
		}
		
		return result;
	}
	
	public PagingInfoVo getPagingInfo(int pageNo, int boardNo) throws Exception {
		PagingInfoVo vo = new PagingInfoVo();
		vo.setPageNo(pageNo); 
			// 전제 게시글
//		System.out.println(bDao.getTotalPostCnt());
		vo.setTotalPostCnt(pageDao.getTotalPostCnt(boardNo));
		// 총 페이지수
		vo.setTotalPageCnt(vo.getTotalPostCnt(), vo.getPagePostCnt());
		// 보이기 시작할 번호
		vo.setStartRowIndex();

		// 전체 페이징 블럭 갯수
		vo.setTotalPagingBlock();
		// 현재 페이징 블럭
		vo.setCurrentPageBlock();
		// 현재 페이징 시작 번호
		vo.setStartPageNum();
		// 현재 페이징 마지막 번호
		vo.setEndPageNum();
		
		System.out.println(vo.toString());
		
		return vo;
	}

}
