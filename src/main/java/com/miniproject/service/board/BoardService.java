package com.miniproject.service.board;

import java.util.List;
import java.util.Map;

import com.miniproject.domain.BoardDto;
import com.miniproject.domain.BoardVo;
import com.miniproject.domain.PagingInfoVo;
import com.miniproject.domain.ReadcountprocessDto;
import com.miniproject.domain.SearchCriteriaDto;
import com.miniproject.domain.UploadedFileDto;

public interface BoardService {

	List<BoardVo> getEntireBoard(PagingInfoVo vo, SearchCriteriaDto dto) throws Exception;

	List<BoardVo> getEntireBoard() throws Exception;

	Map <String, Object> getBoardByNo(ReadcountprocessDto dto) throws Exception;

	void saveNewBoard(BoardDto dto, List<UploadedFileDto> fileList) throws Exception;
	
}
