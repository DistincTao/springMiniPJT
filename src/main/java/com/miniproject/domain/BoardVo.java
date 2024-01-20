package com.miniproject.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BoardVo {
	private int boardNo;
	private String writer;
	private String title;
	private Timestamp postDate;
	private String content;
	private int readCount;
	private int likeCount;
	private int ref;
	private int step;
	private int refOrder;
	private String isDelete;
	private String newFilename;
	
}
