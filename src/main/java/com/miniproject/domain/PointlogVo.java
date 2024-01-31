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
public class PointlogVo {
	private int pointlogNo;
	private Date actionDate;
	private String pointType;
	private int changePoint;
	private String UserId;	
}
