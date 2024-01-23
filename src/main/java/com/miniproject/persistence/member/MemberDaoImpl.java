package com.miniproject.persistence.member;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproject.domain.MemberDto;
import com.miniproject.domain.MemberPointVo;
import com.miniproject.domain.MemberVo;
import com.miniproject.domain.PagingInfoVo;

@Repository
public class MemberDaoImpl implements MemberDao {
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

	@Override
	public MemberVo selectLoginUser(String userId, String userPwd) {
		String q = ns + ".getLoginMember";
		MemberDto dto = new MemberDto();
		dto.setUserId(userId);
		dto.setUserPwd(userPwd);
				
		MemberVo vo = ses.selectOne(q, dto);
		
		
		return vo;
	}

	@Override
	public List<MemberPointVo> selectPointList(String userId) {
		String q = ns + ".selectUserPointLog";
		
		List<MemberPointVo> list = ses.selectList(q, userId);
		
		return list;
	}

	@Override
	public int getTotalPostCnt(String userId) {
		String q = ns + ".selectTotalPost";
		System.out.println(ses.selectOne(q, userId).toString());
		return ses.selectOne(q, userId);
	}

}
