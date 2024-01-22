package com.miniproject.service.board;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.miniproject.domain.BoardVo;
import com.miniproject.persistence.board.BoardDao;

@Service
public class BoardServiceImple implements BoardService {
	@Inject
	BoardDao bDao;
	
	@Override
	public List<BoardVo> getEntireBoard() {
		
		List<BoardVo> list = bDao.selectAllBoard();
		
		return list;
	}

	
	
}
