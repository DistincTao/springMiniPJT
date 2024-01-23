package com.miniproject.persistence.member;

import java.util.List;

import com.miniproject.domain.MemberPointVo;
import com.miniproject.domain.MemberVo;

public interface MemberDao {

	String getDate();

	String selectMemberByUserId(String string);

	List<MemberVo> selectAllMember();

	MemberVo selectLoginUser(String userId, String userPwd);

	List<MemberPointVo> selectPointList(String userId);

	int getTotalPostCnt(String userId);

}
