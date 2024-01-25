package com.miniproject.persistence.member;

import java.util.List;

import com.miniproject.domain.MemberDto;
import com.miniproject.domain.MemberVo;
import com.miniproject.domain.PointlogVo;
import com.miniproject.domain.UploadedFileDto;

public interface MemberDao {

	String getDate();

	String selectMemberByUserId(String string) throws Exception;

	List<MemberVo> selectAllMember() throws Exception;

	MemberVo selectLoginUser(String userId, String userPwd) throws Exception;

	List<PointlogVo> selectPointList(String userId) throws Exception;

	int getTotalPostCnt() throws Exception;
	
	// member 테이블에서 userPoint를 update
	int updateUserPoint(String pointType, String writer) throws Exception;

	int insertNewMember(MemberDto dto) throws Exception;

	int insertUploadFile(UploadedFileDto ufDto) throws Exception;

	int selectImgNo() throws Exception;
}
