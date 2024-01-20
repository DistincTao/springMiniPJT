package com.miniproject.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class LikeCountDto {
	private int likeNo;
	private int boardNo;
	private String userId;
	private Date likeDate;
}
