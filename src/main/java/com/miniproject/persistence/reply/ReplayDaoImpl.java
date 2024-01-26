package com.miniproject.persistence.reply;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproject.domain.ReplyDto;
import com.miniproject.domain.ReplyVo;

@Repository
public class ReplayDaoImpl implements ReplyDao {
	String ns = "com.miniproject.mappers.replyMapper";
	
	@Inject
	private SqlSession ses; // sqlSessionTemplate 객체 주입
	
	@Override
	public List<ReplyVo> selectAllReplies(int boardNo) throws Exception {
		String q = ns + ".selectAllReplies";
		
		return ses.selectList(q, boardNo);
	}

	@Override
	public int insertNewReply(ReplyDto newReply) throws Exception {
		String q = ns + ".insertNewReply";
		
		return ses.insert(q, newReply);	
	}

	@Override
	public int updateReply(ReplyDto updateReply) throws Exception {
		String q = ns + ".updateReply";
		System.out.println(ses.update(q, updateReply));
		return ses.update(q, updateReply);
	}

	@Override
	public int deleteReply(int replyNo) throws Exception {
		String q = ns + ".deleteReply";
		
		return ses.update(q, replyNo);
	}

}
