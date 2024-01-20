package com.miniproject.persistence.member;

import java.util.List;

import com.miniproject.domain.MemberVo;

public interface MemeberDao {

	String getDate();

	String selectMemberByUserId(String string);

	List<MemberVo> selectAllMember();

}
