package com.miniproject.persistence.member;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproject.domain.MemberVo;

@Repository
public class MemberDaoImpl implements MemeberDao {
	private static String ns = "com.miniproject.mappers.memberMapper";

	@Inject
	private SqlSession ses; 
	
	@Override
	public String getDate() {
		String q = ns + ".currDate";
		return ses.selectOne(q);
	}

	@Override
	public String selectMemberByUserId(String userId) {
		String q = ns + ".viewMemberByUserId";
		return ses.selectOne(q, userId);
	}

	@Override
	public List<MemberVo> selectAllMember() {
		String q = ns + ".getAllMembers";
		
		return ses.selectList(q);
	}

}
