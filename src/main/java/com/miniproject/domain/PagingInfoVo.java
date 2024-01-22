package com.miniproject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PagingInfoVo {
	private int totalPostCnt; // 전체 게시판 글의 갯수
	private int pagePostCnt = 10; // 한페이지에 보여줄 글의 갯수
	private int totalPageCnt; // 총페이지 수
	private int startRowIndex; // 보여주기 시작할 글의 row index 번호
	private int pageNo; // 유저가 클릭한 페이지의 번호
	
	private int pageBlockCnt = 5; // 한개 블럭에 보여줄 페이지 갯수
	private int totalPagingBlock; // 전체 페이징 블럭 객수
	private int currentPageBlock; // 현재 페이지가 속한 페이징 블럭 번호
	private int startPageNum; // 현재 블럭의 시작 페이지 번호
	private int endPageNum; // 현재 블럭의 마지막 페이지 번호
	
	


}
