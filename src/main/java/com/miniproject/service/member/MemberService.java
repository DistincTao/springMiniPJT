package com.miniproject.service.member;

import java.util.List;

import com.miniproject.domain.MemberDto;
import com.miniproject.domain.MemberVo;
import com.miniproject.domain.PagingInfoVo;
import com.miniproject.domain.PointlogVo;
import com.miniproject.domain.UploadedFileDto;

public interface MemberService {

	MemberVo getLoginUserInfo(String userId, String userPwd) throws Exception;

	List<PointlogVo> getMemberPoint(String userId) throws Exception;

	PagingInfoVo getPagingInfo() throws Exception;

	void saveNewMember(MemberDto dto, UploadedFileDto ufDto) throws Exception;
		
}
