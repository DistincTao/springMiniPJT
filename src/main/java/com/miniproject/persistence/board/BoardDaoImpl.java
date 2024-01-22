package com.miniproject.persistence.board;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproject.domain.BoardVo;

@Repository
public class BoardDaoImpl implements BoardDao {
	String ns = "com.miniproject.mappers.boardMapper";

	@Inject
	private SqlSession ses; // sqlSessionTemplate 객체 주입
	
	@Override
	public List<BoardVo> selectAllBoard() {
		String query = ns + ".getAllBoard";
		
		return ses.selectList(query);
	}

}
