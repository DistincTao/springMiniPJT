package com.miniproject.service.member;

import java.sql.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.domain.PointlogVo;
import com.miniproject.domain.UploadedFileDto;
import com.miniproject.domain.MemberDto;
import com.miniproject.domain.MemberVo;
import com.miniproject.domain.PagingInfoVo;
import com.miniproject.domain.PointlogDto;
import com.miniproject.persistence.member.MemberDao;
import com.miniproject.persistence.pointlog.PointlogDao;

@Service
public class MemberServiceImple implements MemberService {
	@Inject
	 MemberDao mDao;

	@Inject
	 PointlogDao pDao;
	
	@Override
	@Transactional(rollbackFor = Exception.class) // 예외 발생 시 rollback
	public MemberVo getLoginUserInfo(String userId, String userPwd) throws Exception {
		Date lastLogin = pDao.selectLastLogin(userId);
		MemberVo vo = mDao.selectLoginUser(userId, userPwd);
		Date now = new Date(System.currentTimeMillis());
		
		if (vo != null && (now.getTime() - lastLogin.getTime()) / 1000 / 60 / 60 / 24 > 1) {
			mDao.updateUserPoint("login", userId);
			pDao.insertPointlog(new PointlogDto(-1, null, "login", 2, userId));
		}
		
		return vo;
	}

	@Override
	public List<PointlogVo> getMemberPoint(String userId) throws Exception {
		List<PointlogVo> pointVo = mDao.selectPointList(userId);
		
		return pointVo;
	}

	@Override
	public PagingInfoVo getPagingInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class) // 예외 발생 시 rollback
	public void saveNewMember(MemberDto dto, UploadedFileDto ufDto) throws Exception {
		
		if (mDao.insertUploadFile(ufDto) == 1) {
			int userImg = mDao.selectImgNo();
 			mDao.insertNewMember(dto);
		}
		
		
		mDao.updateUserPoint("signin", dto.getUserId());
		pDao.insertPointlog(new PointlogDto(-1, null, "login", 2, dto.getUserId()));
	}

	


	
	
}
