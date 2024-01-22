package com.miniproject.persistence.board;

import java.util.List;

import com.miniproject.domain.BoardVo;

public interface BoardDao {
	List<BoardVo> selectAllBoard();

	BoardVo selectBoardByNo(int boardNo);
}
