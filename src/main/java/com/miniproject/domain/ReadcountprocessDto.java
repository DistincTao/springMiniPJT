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
public class ReadcountprocessDto {
	private int readNo;
	private String ipAddr;
	private int boardNo;
	private Timestamp readTime;
}
