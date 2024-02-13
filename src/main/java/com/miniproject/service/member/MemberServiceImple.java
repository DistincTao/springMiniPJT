package com.miniproject.service.member;

import java.sql.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.domain.MemberDto;
import com.miniproject.domain.MemberVo;
import com.miniproject.domain.PagingInfoVo;
import com.miniproject.domain.PointlogDto;
import com.miniproject.domain.PointlogVo;
import com.miniproject.domain.SessionDto;
import com.miniproject.domain.UploadedFileDto;
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
		System.out.println("MemberService에서 Login 처리 진행 중");
		// 1) member table에서 userId userPwd 일치 여부 확인
		MemberVo vo = mDao.selectLoginUser(userId, userPwd);
		// 2) 로그인이 성공하면 마지막 로그인 시간을 확인
		Date lastLogin = pDao.selectLastLogin(userId);
		// 하루가 지나면 포인트로그 업데이트
		Date now = new Date(System.currentTimeMillis());
		
		if (vo != null && (now.getTime() - lastLogin.getTime()) / 1000 / 60 / 60 / 24 > 1) {
			if (mDao.updateUserPoint("login", userId) == 1) {
				// 3) 포인트 로그에 포인트 기록
				pDao.insertPointlog(new PointlogDto(-1, null, "login", 2, userId));
			}
		}
		return vo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class) // 예외 발생 시 rollback
	public MemberVo getLoginUserInfo(MemberDto mDto) throws Exception {
		// > AOP 끼어드는 시점
		System.out.println("MemberService에서 Login 처리 진행 중");
		// 1) member table에서 userId userPwd 일치 여부 확인
		MemberVo vo = mDao.selectLoginUser(mDto);
		// 2) 로그인이 성공하면 마지막 로그인 시간을 확인
		Date lastLogin = pDao.selectLastLogin(mDto.getUserId());
		// 하루가 지나면 포인트로그 업데이트
		Date now = new Date(System.currentTimeMillis());
		
		if (vo != null && (now.getTime() - lastLogin.getTime()) / 1000 / 60 / 60 / 24 > 1) {
		System.out.println("로그인한 맴버 : " + vo.toString());
			
			if (mDao.updateUserPoint("login", mDto.getUserId()) == 1) {
				// 3) 포인트 로그에 포인트 기록
				pDao.insertPointlog(new PointlogDto(-1, null, "login", 2, mDto.getUserId()));				
			}
		}
		return vo;
		// < AOP 마무리되는 시점
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
			dto.setUserImg(userImg);
 			mDao.insertNewMember(dto);
		}
				
		if (mDao.updateUserPoint("signin", dto.getUserId()) == 1) {
			pDao.insertPointlog(new PointlogDto(-1, null, "signin", 100, dto.getUserId()));
		}
	}

	@Override
	public boolean remember(SessionDto dto) throws Exception {
		boolean result = false;
		if (mDao.updateUserSessionInfo(dto) == 1) {
			result = true;
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class) // 예외 발생 시 rollback
	public MemberVo getUserSessionInfo(String sessionKey) throws Exception {
		MemberVo vo = mDao.selectSessionInfo(sessionKey);
//		System.out.println(vo.toString());

		return vo;
	}
	
}
