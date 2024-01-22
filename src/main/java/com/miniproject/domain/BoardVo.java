package com.miniproject.domain;

import java.sql.Timestamp;

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
