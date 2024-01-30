package com.miniproject.persistence.paging;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproject.domain.SearchCriteriaDto;

@Repository
public class PagingDaoImpl implements PagingDao {
	private static String ns = "com.miniproject.mappers.pagingMapper";

	@Inject
	private SqlSession ses;

	@Override
	public int getTotalPostCnt() {
		String q = ns + ".selectTotalPost";
		return ses.selectOne(q);
	}

	@Override
	public int getTotalPostCnt(SearchCriteriaDto dto) {
		String q = ns + ".selectTotalPostBySearch";
		
		Map <String, Object> param = new HashMap<>();

		param.put("searchType", dto.getSearchType());
		param.put("searchWord", "'%" + dto.getSearchWord() + "%'");
		
		return ses.selectOne(q, param);
	}

	@Override
	public int getTotalPostCnt(int boardNo) {
		String q = ns + ".selectReplyCntByBoardNo";
		
		return ses.selectOne(q, boardNo);
		
	}


	


}
