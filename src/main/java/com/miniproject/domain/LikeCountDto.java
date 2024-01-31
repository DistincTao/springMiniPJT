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
public class LikeCountDto {
	private int likeNo;
	private int boardNo;
	private String userId;
	private Date likeDate;
}
