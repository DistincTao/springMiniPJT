package com.miniproject.persistence.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproject.domain.BoardDto;
import com.miniproject.domain.BoardVo;
import com.miniproject.domain.PagingInfoVo;
import com.miniproject.domain.ReadcountprocessDto;
import com.miniproject.domain.SearchCriteriaDto;
import com.miniproject.domain.UploadedFileDto;
import com.miniproject.domain.UploadedFileVo;

@Repository
public class BoardDaoImpl implements BoardDao {
	String ns = "com.miniproject.mappers.boardMapper";

	@Inject
	private SqlSession ses; // sqlSessionTemplate 객체 주입
	
	@Override
	public List<BoardVo> selectAllBoard(PagingInfoVo vo, SearchCriteriaDto dto) throws Exception {
		String query = ns + ".getAllBoardByPagingWithSearch";
		
		Map <String, Object> param = new HashMap<>();
		
		param.put("startRowIndex", vo.getStartRowIndex());
		param.put("pagePostCnt", vo.getPagePostCnt());
		param.put("searchType", dto.getSearchType());
		param.put("searchWord", "'%" + dto.getSearchWord() + "%'");
		
		
		return ses.selectList(query, param);
	}

	@Override
	public List<BoardVo> selectAllBoard(PagingInfoVo vo) throws Exception {
		String query = ns + ".getAllBoardByPaging";
		
		return ses.selectList(query, vo);
	}
	
	@Override
	public List<BoardVo> selectAllBoard() throws Exception {
		String query = ns + ".getAllBoard";
		
		return ses.selectList(query);
	}
	

	@Override
	public BoardVo selectBoardByNo(ReadcountprocessDto dto) throws Exception {
		String query = ns + ".getBoardByNo";
		
		return ses.selectOne(query, dto);
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

	@Override
	public int updateReadCount(ReadcountprocessDto dto) throws Exception {
		String query = ns + ".updateReadcount";
		
		return ses.update(query, dto);
	}

	@Override
	public List<UploadedFileVo> selectUploadedFile(ReadcountprocessDto dto) throws Exception {
		String query = ns + ".getUploadedFiles";
		
		return ses.selectList(query, dto);
	}

	@Override
	public int getTotalPostCnt(SearchCriteriaDto dto) throws Exception {
		String query = ns + ".selectTotalPost";
		return ses.selectOne(query, dto);
	}

	@Override
	public int getTotalPostCnt() throws Exception {
		String query = ns + ".selectTotalPost";

		return ses.selectOne(query);
	}


	
	

}
