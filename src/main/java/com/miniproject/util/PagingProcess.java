package com.miniproject.util;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.miniproject.domain.PagingInfoVo;
import com.miniproject.domain.SearchCriteriaDto;
import com.miniproject.persistence.board.BoardDao;

public class PagingProcess {
	
	public PagingInfoVo pagingProcess(int pageNo, SearchCriteriaDto dto) throws Exception {
		PagingInfoVo vo = new PagingInfoVo();
		vo.setPageNo(pageNo);
		
		if (dto.getSearchWord().equals("")) { // 검색어가 없으면
			// 전제 게시글
//			vo.setTotalPostCnt(bDao.getTotalPostCnt());
		} else if (!dto.getSearchWord().equals("") && !dto.getSearchType().equals("")) { // 검색어가 있으면
			// 전제 게시글
//			vo.setTotalPostCnt(bDao.getTotalPostCnt(dto));
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

	public PagingInfoVo pagingProcess(int pageNo) throws Exception {
		PagingInfoVo vo = new PagingInfoVo();
		vo.setPageNo(pageNo);
			// 전제 게시글
//		System.out.println(bDao.getTotalPostCnt());
//		vo.setTotalPostCnt(bDao.getTotalPostCnt());
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

}
