package com.miniproject.util;

import javax.inject.Inject;

import com.miniproject.domain.PagingInfoVo;
import com.miniproject.persistence.member.MemberDao;

public class PagingProcess {
	
	@Inject
	MemberDao mDao;
	
	public PagingInfoVo getPagingInfo(int pageNo, String userId) throws Exception  {
		
		PagingInfoVo vo = new PagingInfoVo();
		
		vo.setPagePostCnt(15);
		vo.setPageNo(pageNo);
		// 전제 게시글
		System.out.println(mDao.getTotalPostCnt(userId));
		vo.setTotalPostCnt(mDao.getTotalPostCnt(userId));
//			vo.setTotalPageCnt();
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
		
		vo.setUserId(userId);
		return vo;

	
	}
}
