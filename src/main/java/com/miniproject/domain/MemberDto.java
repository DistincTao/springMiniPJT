package com.miniproject.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class MemberDto {
	private String userId;
	private String userPwd;
	private String userEmail;
	private Date regdate;
	private int userImg;
	private int userPoint;
	private String memberImg;
	private String isAdmin;
	private String isDelete;
	

	
}
