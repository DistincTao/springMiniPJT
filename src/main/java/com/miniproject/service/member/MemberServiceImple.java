package com.miniproject.service.member;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.miniproject.domain.MemberPointVo;
import com.miniproject.domain.MemberVo;
import com.miniproject.domain.PagingInfoVo;
import com.miniproject.persistence.member.MemberDao;

@Service
public class MemberServiceImple implements MemberService {
	@Inject
	 MemberDao mDao;

	@Override
	public MemberVo getLoginUserInfo(String userId, String userPwd) {
		
		MemberVo vo = mDao.selectLoginUser(userId, userPwd);
		
		return vo;
	}

	@Override
	public List<MemberPointVo> getMemberPoint(String userId) {
		List<MemberPointVo> pointVo = mDao.selectPointList(userId);
		
		return pointVo;
	}

	@Override
	public PagingInfoVo getPagingInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	


	
	
}
