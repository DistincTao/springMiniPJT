package com.miniproject.persistence.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.miniproject.domain.MemberDto;
import com.miniproject.domain.PointlogVo;
import com.miniproject.domain.UploadedFileDto;
import com.miniproject.domain.MemberVo;

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
	public String selectMemberByUserId(String userId) throws Exception {
		String q = ns + ".viewMemberByUserId";
		return ses.selectOne(q, userId);
	}

	@Override
	public List<MemberVo> selectAllMember() throws Exception {
		String q = ns + ".getAllMembers";
		
		return ses.selectList(q);
	}

	@Override
	public MemberVo selectLoginUser(String userId, String userPwd) throws Exception {
		String q = ns + ".getLoginMember";
		MemberDto dto = new MemberDto();
		dto.setUserId(userId);
		dto.setUserPwd(userPwd);
				
		MemberVo vo = ses.selectOne(q, dto);
		
		
		return vo;
	}

	@Override
	public List<PointlogVo> selectPointList(String userId) throws Exception {
		String q = ns + ".selectUserPointLog";
		
		List<PointlogVo> list = ses.selectList(q, userId);
		
		return list;
	}

	@Override
	public int getTotalPostCnt() throws Exception {
		String q = ns + ".selectTotalPost";

		return ses.selectOne(q);
	}

	@Override
	public int updateUserPoint(String pointType, String writer) throws Exception {
		String q = ns + ".updateUserPoint";
		Map <String, Object> param = new HashMap<>();
		
		param.put("pointType", pointType);
		param.put("userId", writer);
		
		return ses.update(q, param);
		
	}

	@Override
	public int insertNewMember(MemberDto dto) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertUploadFile(UploadedFileDto ufDto) throws Exception {
		String query = ns + ".insertUploadFile";
		
		return ses.insert(query, ufDto);
	}

	@Override
	public int selectImgNo() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
