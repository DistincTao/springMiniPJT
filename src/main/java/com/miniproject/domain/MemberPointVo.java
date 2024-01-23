package com.miniproject.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class MemberPointVo {
	private int pointlogNo;
	private Date actionDate;
	private String pointType;
	private int changePoint;
	private String UserId;	
}
