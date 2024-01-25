package com.miniproject.persistence.readcountprocess;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproject.domain.ReadcountprocessDto;
import com.miniproject.domain.ReadcountprocessVo;

@Repository
public class readcountDaoImpl implements readcountDao {
	private static String ns = "com.miniproject.mappers.readcountMapper";

	@Inject
	private SqlSession ses; // sqlSessionTemplate 객체 주입
	
	
	@Override
	public ReadcountprocessVo selectReadcountprocess(ReadcountprocessDto dto) throws Exception {
		
		String q = ns + ".selectBoardReadCount";

		return ses.selectOne(q, dto);
	}


	@Override
	public int getHourDiffReadTime(ReadcountprocessDto dto) throws Exception {
		String q = ns + ".getHourDiffReadTime";

		
		return ses.selectOne(q, dto);
	}


	@Override
	public int updateReadCountProcess(ReadcountprocessDto dto) throws Exception {
		String q = ns + ".updateReadCount";
	
		return ses.update(q, dto);
	}


	@Override
	public int insertReadCountProcess(ReadcountprocessDto dto) throws Exception {
		
		String q = ns + ".insertReadCountProcess";
		
		return ses.insert(q, dto);
	}




}
