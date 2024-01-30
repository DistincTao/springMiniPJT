package com.miniproject.persistence.paging;

import com.miniproject.domain.SearchCriteriaDto;

public interface PagingDao {

	int getTotalPostCnt();

	int getTotalPostCnt(SearchCriteriaDto dto);

	int getTotalPostCnt(int boardNo);

}
