package com.miniproject.persistence.board;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproject.domain.BoardDto;
import com.miniproject.domain.BoardVo;
import com.miniproject.domain.UploadedFileDto;

@Repository
public class BoardDaoImpl implements BoardDao {
	String ns = "com.miniproject.mappers.boardMapper";

	@Inject
	private SqlSession ses; // sqlSessionTemplate 객체 주입
	
	@Override
	public List<BoardVo> selectAllBoard() throws Exception {
		String query = ns + ".getAllBoard";
		
		return ses.selectList(query);
	}

	@Override
	public BoardVo selectBoardByNo(int boardNo) throws Exception {
		String query = ns + ".getBoardByNo";
		
		return ses.selectOne(query, boardNo);
	}

	@Override
	public int insertNewBoard(BoardDto dto) throws Exception {
		String query = ns + ".insertNewBoard";
		
		return ses.insert(query, dto);
	}
	
	@Override
	public int insertUploadFile(UploadedFileDto ufDto) throws Exception {
		String query = ns + ".insertUploadFile";
		
		return ses.insert(query, ufDto);
	}

	@Override
	public int selectBoardNo() throws Exception {
		String query = ns + ".getNo";
		
		return ses.selectOne(query);
	}
	
	

}
