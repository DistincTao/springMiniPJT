package com.miniproject.service.reply;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.domain.PointlogDto;
import com.miniproject.domain.ReplyDto;
import com.miniproject.domain.ReplyVo;
import com.miniproject.persistence.member.MemberDao;
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
		if (rDao.updateReply(updateReply) == 1) {
			System.out.println("내용 수정 업데이트 시작");
			System.out.println(rDao.updateReply(updateReply));
			
				result = true;
		}
		
		return result;
	}
	
	@Override
	public boolean deleteReplyByNo(int replyNo) throws Exception {
		boolean result = false;
		if (rDao.deleteReply(replyNo) == 1) {
				result = true;
		}
		
		return result;
	}

}
