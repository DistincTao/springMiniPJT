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
import com.miniproject.domain.LikeCountDto;
import com.miniproject.domain.PagingInfoVo;
import com.miniproject.domain.PointlogDto;
import com.miniproject.domain.ReadcountprocessDto;
import com.miniproject.domain.SearchCriteriaDto;
import com.miniproject.domain.UploadedFileDto;
import com.miniproject.domain.UploadedFileVo;
import com.miniproject.persistence.board.BoardDao;
import com.miniproject.persistence.member.MemberDao;
import com.miniproject.persistence.paging.PagingDao;
import com.miniproject.persistence.pointlog.PointlogDao;
import com.miniproject.persistence.readcountprocess.ReadcountDao;

@Service
public class BoardServiceImple implements BoardService {
	@Inject
	BoardDao bDao;
	@Inject
	MemberDao mDao;
	@Inject
	PointlogDao pDao;
	@Inject
	ReadcountDao rDao;
	@Inject
	PagingDao pageDao;
	
	@Override
	public Map<String, Object> getEntireBoard(int pageNo, SearchCriteriaDto dto) throws Exception {
		PagingInfoVo pageVo = getPagingInfo(pageNo, dto);
		System.out.println(pageVo.toString());
		List<BoardVo> list = null;
		
		if (!dto.getSearchWord().equals("") && !dto.getSearchType().equals("") ) {
			list = bDao.selectAllBoard(pageVo, dto);
		} else if (dto.getSearchType().equals("")){
			list = bDao.selectAllBoard(pageVo);		
		} else {
			list = bDao.selectAllBoard(pageVo);
		}
		
		Map<String, Object> boardMap = new HashMap<>();
		
		boardMap.put("boardList", list);
		boardMap.put("pageInfo", pageVo);

		return boardMap;
	}
	
	@Override
	public Map<String, Object> getEntireBoard(int pageNo) throws Exception {
		PagingInfoVo pageVo = getPagingInfo(pageNo);
		System.out.println(pageVo.toString());
		
		List<BoardVo> list = bDao.selectAllBoard(pageVo);	
		
		Map<String, Object> boardMap = new HashMap<>();
		
		boardMap.put("boardList", list);
		boardMap.put("pageInfo", pageVo);
		
		return boardMap;
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
			List<String> likeUserList = bDao.getUserListByLikeCntWithBoardNo(dto);
			System.out.println(likeUserList.toString());
		    
		    result.put("board", vo);
		    result.put("fileList", upFileList);
		    result.put("likeUsers", likeUserList);
		    
		}
			
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class) // 예외 발생 시 rollback
	public void saveNewBoard(BoardDto dto, List<UploadedFileDto> fileList) throws Exception {
		// board 테이블에 글내용 저장 (insert)
		if (bDao.insertNewBoard(dto) == 1) {
			int boardNo = bDao.selectBoardNo();
			System.out.println("저장된 게시글 번호 : " +boardNo);
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

	public PagingInfoVo getPagingInfo(int pageNo, SearchCriteriaDto dto) throws Exception {
		PagingInfoVo vo = new PagingInfoVo();
		vo.setPageNo(pageNo);
		
		if (dto.getSearchWord().equals("") || dto.getSearchType().equals("") ) { // 검색어가 없으면
			// 전제 게시글
			vo.setTotalPostCnt(pageDao.getTotalPostCnt());
		} else if (!dto.getSearchWord().equals("") && !dto.getSearchType().equals("")) { // 검색어가 있으면
			// 전제 게시글
			vo.setTotalPostCnt(pageDao.getTotalPostCnt(dto));
		}
		// 총 페이지수
		vo.setTotalPageCnt(vo.getTotalPostCnt(), vo.getPagePostCnt());
		// 보이기 시작할 번호
		vo.setStartRowIndex();
		
		// 전체 페이징 블럭 갯수
		vo.setTotalPagingBlock();
		// 현재 페이징 블럭
		vo.setCurrentPageBlock();
		// 현재 페이징 시작 번호
		vo.setStartPageNum();
		// 현재 페이징 마지막 번호
		vo.setEndPageNum();
		
		return vo;
	}

	public PagingInfoVo getPagingInfo(int pageNo) throws Exception {
		PagingInfoVo vo = new PagingInfoVo();
		vo.setPageNo(pageNo); 
			// 전제 게시글
//		System.out.println(bDao.getTotalPostCnt());
		vo.setTotalPostCnt(pageDao.getTotalPostCnt());
		// 총 페이지수
		vo.setTotalPageCnt(vo.getTotalPostCnt(), vo.getPagePostCnt());
		// 보이기 시작할 번호
		vo.setStartRowIndex();

		// 전체 페이징 블럭 갯수
		vo.setTotalPagingBlock();
		// 현재 페이징 블럭
		vo.setCurrentPageBlock();
		// 현재 페이징 시작 번호
		vo.setStartPageNum();
		// 현재 페이징 마지막 번호
		vo.setEndPageNum();
		
		return vo;
	}

	@Override
	public String getBoardWriterByBoardNo(String boardNo) throws Exception {
		String writer = bDao.selectWriterByBoarNo(boardNo);
		
			return writer;
		
	}

	@Override
	@Transactional (rollbackFor = Exception.class)
	public boolean likeBoard(LikeCountDto dto) throws Exception {
		boolean result = false;
		System.out.println("좋아요 늘리러 왔어요");

		if (bDao.likeBoard(dto) == 1){
			if (bDao.updateLikeCount(dto) == 1);
			result = true;
		};
		return result;
		
	}

	@Override
	@Transactional (rollbackFor = Exception.class)
	public boolean dislikeBoard(LikeCountDto dto) throws Exception {
		boolean result = false;
		System.out.println("좋아요 줄이러 왔어요");
		if (bDao.dislikeBoard(dto) == 1){
			
			if (bDao.updateLikeCount(dto) == 1);
			result = true;
		};
		return result;
		
	}

	@Override
	public int getLikeCntByBoardNo(int boardNo) throws Exception {
		int result = -1;
		
		if (bDao.selectLikeCnt(boardNo) != 0) {
			result = bDao.selectLikeCnt(boardNo);
		}
		
		return result;
	}

//	@Override
//	public LikeCountVo getLikeCntLog(LikeCountDto dto) throws Exception {
//		LikeCountVo vo = null;
//		if (bDao.selectLikeLogByBoard(dto) != null) {
//			vo = bDao.selectLikeLogByBoard(dto);
//		}
//		return vo;
//	}

	@Override
	public List<String> getUserListByLikeCntWithBoardNo(ReadcountprocessDto dto) throws Exception {

		return bDao.getUserListByLikeCntWithBoardNo(dto);

	}

	
	
}
