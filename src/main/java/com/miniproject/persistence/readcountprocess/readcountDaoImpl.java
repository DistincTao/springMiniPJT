package com.miniproject.persistence.pointlog;

import java.sql.Date;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproject.domain.PointlogDto;



@Repository
public class PointlogDaoImpl implements PointlogDao {
	private static String ns = "com.miniproject.mappers.pointlogMapper";

	@Inject
	private SqlSession ses;

	@Override
	public int insertPointlog(PointlogDto pointDto) throws Exception {
		String q = ns + ".insertPointlog";
		
		return ses.insert(q, pointDto);
		
	}

	@Override
	public Date selectLastLogin(String userId) throws Exception {
		String q = ns + ".selectLastLogin";

		return ses.selectOne(q, userId);
	} 
	


}
