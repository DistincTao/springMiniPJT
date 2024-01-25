package com.miniproject.persistence.board;

import java.util.List;

import com.miniproject.domain.BoardDto;
import com.miniproject.domain.BoardVo;
import com.miniproject.domain.PagingInfoVo;
import com.miniproject.domain.ReadcountprocessDto;
import com.miniproject.domain.SearchCriteriaDto;
import com.miniproject.domain.UploadedFileDto;
import com.miniproject.domain.UploadedFileVo;

public interface BoardDao {
	List<BoardVo> selectAllBoard(PagingInfoVo vo, SearchCriteriaDto dto) throws Exception;
	
//	List<BoardVo> selectAllBoard(PagingInfoVo vo) throws Exception;
	
	List<BoardVo> selectAllBoard() throws Exception;

	BoardVo selectBoardByNo(ReadcountprocessDto dto) throws Exception;
// 게시글 저장
	int insertNewBoard(BoardDto dto) throws Exception;
// 업로드된 파일 저장
	int insertUploadFile(UploadedFileDto ufDto) throws Exception;
// 게시판 번호 조회
	int selectBoardNo() throws Exception;
// 조회수 증가	
	int updateReadCount(ReadcountprocessDto dto) throws Exception;
// 업로드된 파일 정보 가져오기	
	List<UploadedFileVo> selectUploadedFile(ReadcountprocessDto dto) throws Exception;
// 총 게시물 수 확	
	int getTotalPostCnt(SearchCriteriaDto dto) throws Exception;
// 총 게시물 수 확	
	int getTotalPostCnt() throws Exception;
}
