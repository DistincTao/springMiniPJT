package com.miniproject.service.member;

import java.util.List;

import com.miniproject.domain.BoardVo;
import com.miniproject.domain.MemberPointVo;
import com.miniproject.domain.MemberVo;
import com.miniproject.domain.PagingInfoVo;

public interface MemberService {

	MemberVo getLoginUserInfo(String userId, String userPwd);

	List<MemberPointVo> getMemberPoint(String userId);

	PagingInfoVo getPagingInfo();
	
}
