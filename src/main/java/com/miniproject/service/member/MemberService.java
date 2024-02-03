package com.miniproject.service.member;

import java.util.List;

import com.miniproject.domain.MemberDto;
import com.miniproject.domain.MemberVo;
import com.miniproject.domain.PagingInfoVo;
import com.miniproject.domain.PointlogVo;
import com.miniproject.domain.UploadedFileDto;
import com.miniproject.domain.SessionDto;
import com.miniproject.domain.SessionVo;

public interface MemberService {
	
	MemberVo getLoginUserInfo(String userId, String userPwd) throws Exception;

	List<PointlogVo> getMemberPoint(String userId) throws Exception;

	PagingInfoVo getPagingInfo() throws Exception;

	void saveNewMember(MemberDto dto, UploadedFileDto ufDto) throws Exception;
	// 로그인 유저 정보 가져오기
	MemberVo getLoginUserInfo(MemberDto mDto) throws Exception;
	// 
	boolean remember(SessionDto dto) throws Exception;

	MemberVo getUserSessionInfo(String sessionKey) throws Exception;
		
}
