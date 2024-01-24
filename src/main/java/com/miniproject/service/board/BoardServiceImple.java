package com.miniproject.service.board;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.domain.BoardDto;
import com.miniproject.domain.BoardVo;
import com.miniproject.domain.PointlogDto;
import com.miniproject.domain.UploadedFileDto;
import com.miniproject.persistence.board.BoardDao;
import com.miniproject.persistence.member.MemberDao;
import com.miniproject.persistence.pointlog.PointlogDao;

@Service
public class BoardServiceImple implements BoardService {
	@Inject
	BoardDao bDao;
	@Inject
	MemberDao mDao;
	@Inject
	PointlogDao pDao;
	
	@Override
	public List<BoardVo> getEntireBoard() throws Exception {
		
		List<BoardVo> list = bDao.selectAllBoard();
		
		return list;
	}

	@Override
	public BoardVo getBoardByNo(int boardNo) throws Exception {

		BoardVo vo = bDao.selectBoardByNo(boardNo);
		
		return vo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class) // 예외 발생 시 rollback
	public void saveNewBoard(BoardDto dto, List<UploadedFileDto> fileList) throws Exception {
		// board 테이블에 글내용 저장 (insert)
		if (bDao.insertNewBoard(dto) == 1) {
			int boardNo = bDao.selectBoardNo();
			System.out.println(boardNo);
			if (fileList.size() > 0) { // 업로드한 파일이 있음	
				for (UploadedFileDto ufDto : fileList) {
					ufDto.setBoardNo(boardNo);
					System.out.println("테이블에 저장될 file들 : " + fileList.toString());
					bDao.insertUploadFile(ufDto);
				}
			}
			
			// member 테이블에 userPoint를 update
			mDao.updateUserPoint("write_board", dto.getWriter());
			// pointlog 테이블에 insert
			pDao.insertPointlog(new PointlogDto(-1, null, "write_board", 2, dto.getWriter()));
			
		}
		
	}

	
	
}
