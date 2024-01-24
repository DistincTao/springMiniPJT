package com.miniproject.service.board;

import java.util.List;

import com.miniproject.domain.BoardDto;
import com.miniproject.domain.BoardVo;
import com.miniproject.domain.UploadedFileDto;

public interface BoardService {

	List<BoardVo> getEntireBoard() throws Exception;

	BoardVo getBoardByNo(int boardNo) throws Exception;

	void saveNewBoard(BoardDto dto, List<UploadedFileDto> fileList) throws Exception;
	
}
