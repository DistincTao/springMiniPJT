package com.miniproject.service.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.domain.BoardDto;
import com.miniproject.domain.BoardVo;
import com.miniproject.domain.PagingInfoVo;
import com.miniproject.domain.PointlogDto;
import com.miniproject.domain.ReadcountprocessDto;
import com.miniproject.domain.SearchCriteriaDto;
import com.miniproject.domain.UploadedFileDto;
import com.miniproject.domain.UploadedFileVo;
import com.miniproject.persistence.board.BoardDao;
import com.miniproject.persistence.member.MemberDao;
import com.miniproject.persistence.pointlog.PointlogDao;
import com.miniproject.persistence.readcountprocess.readcountDao;

@Service
public class BoardServiceImple implements BoardService {
	@Inject
	BoardDao bDao;
	@Inject
	MemberDao mDao;
	@Inject
	PointlogDao pDao;
	@Inject
	readcountDao rDao;
	
	@Override
	public List<BoardVo> getEntireBoard(PagingInfoVo vo, SearchCriteriaDto dto) throws Exception {
		
		List<BoardVo> list = bDao.selectAllBoard(vo, dto);

		return list;
	}

	@Override
	public List<BoardVo> getEntireBoard() throws Exception {
		
		List<BoardVo> list = bDao.selectAllBoard();
		
		return list;
	}

	
	@Override
	@Transactional(rollbackFor = Exception.class,
				   isolation = Isolation.READ_COMMITTED) // 예외 발생 시 rollback
	public Map <String, Object> getBoardByNo(ReadcountprocessDto dto) throws Exception {
//		해당 아이피 주소와 글번호가 같은 것이 없으면 (-> 해당 Ip 주소가 해당글을 최초 조회)
//		-> ip 주소와 글번호와 읽은 시간을 readcountprocess 테이블에 insert
//		-> 해당 글 번호의 read_count 증가 (update)
//		-> 해당 글 가져오기
//		-- 24시간이 지나지 않은 경우
//		-> 해당 글 가져오기
		Map <String, Object> result = new HashMap<>();
		List<UploadedFileVo> upFileList;
		int readCntResult = -1;
		if (rDao.selectReadcountprocess(dto) != null) {
			System.out.println(rDao.getHourDiffReadTime(dto));
			if (rDao.getHourDiffReadTime(dto) > 23) { // 24시간이 지난경우
				// 아이피 주소와 글번호와 읽은 시간을 readcountprocess 테이블에서 update
				if (rDao.updateReadCountProcess(dto) == 1) {
					// 해당 글번호 조회수를 1 증가
					readCntResult = bDao.updateReadCount(dto);
				}
			} else { // 24시간이 지나지 않은 경우
				readCntResult = 1;
			}
		} else { // 최초 조회
			if ((rDao.insertReadCountProcess(dto) == 1)) {
				readCntResult = bDao.updateReadCount(dto);
			}
		}
		if (readCntResult == 1) {
			BoardVo vo = bDao.selectBoardByNo(dto);
			
		    upFileList = bDao.selectUploadedFile(dto);
		    
		    result.put("board", vo);
		    result.put("fileList", upFileList);
		    
		}
			
		return result;
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
