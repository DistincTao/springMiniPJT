package com.miniproject.service.board;

import java.util.List;
import java.util.Map;

import com.miniproject.domain.BoardDto;
import com.miniproject.domain.BoardVo;
import com.miniproject.domain.LikeCountDto;
import com.miniproject.domain.LikeCountVo;
import com.miniproject.domain.PagingInfoVo;
import com.miniproject.domain.ReadcountprocessDto;
import com.miniproject.domain.SearchCriteriaDto;
import com.miniproject.domain.UploadedFileDto;

public interface BoardService {

	Map<String, Object> getEntireBoard(int pageNo, SearchCriteriaDto dto) throws Exception;

	Map<String, Object> getEntireBoard(int pageNo) throws Exception;

	List<BoardVo> getEntireBoard() throws Exception;

	Map <String, Object> getBoardByNo(ReadcountprocessDto dto) throws Exception;

	void saveNewBoard(BoardDto dto, List<UploadedFileDto> fileList) throws Exception;

	String getBoardWriterByBoardNo(String boardNo) throws Exception;

	boolean likeBoard(LikeCountDto dto) throws Exception;

	boolean dislikeBoard(LikeCountDto dto) throws Exception;

	int getLikeCntByBoardNo(int boardNo) throws Exception;

//	LikeCountVo getLikeCntLog(LikeCountDto dto) throws Exception;

	List<String> getUserListByLikeCntWithBoardNo(ReadcountprocessDto dto) throws Exception;


}
