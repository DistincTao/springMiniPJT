package com.miniproject.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberVo {
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

