package com.miniproject.persistence.board;

import java.util.List;

import com.miniproject.domain.BoardDto;
import com.miniproject.domain.BoardVo;
import com.miniproject.domain.UploadedFileDto;

public interface BoardDao {
	List<BoardVo> selectAllBoard() throws Exception;
// 
	BoardVo selectBoardByNo(int boardNo) throws Exception;
// 게시글 저장
	int insertNewBoard(BoardDto dto) throws Exception;
// 업로드된 파일 저장
	int insertUploadFile(UploadedFileDto ufDto) throws Exception;
// 게시판 번호 조회
	int selectBoardNo() throws Exception;
}
