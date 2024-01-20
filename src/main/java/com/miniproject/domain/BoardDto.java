package com.miniproject.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BoardDto {
	private int boardNo;
	private String writer;
	private String title;
	private Timestamp postDate;
	private String content;
	private int ref;
	private int step;
	private int refOrder;
	
}
