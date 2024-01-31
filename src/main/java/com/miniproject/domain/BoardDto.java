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
