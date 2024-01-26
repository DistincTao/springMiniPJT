package com.miniproject.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReplyVo {
	private int replyNo;
	private int boardNo;
	private String replyText;
	private String replier;
	private Timestamp postDate;
	private String isDelete;
	
}
