package com.miniproject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class LikeCountDto {

	private int boardNo;
	private String userId;
	private String behavior;
	private int n = 0;

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}
	public void setN(String behavior) {
		if (behavior.equals("like")){
			this.n = 1;	
		} else if (behavior.equals("dislike")) {
			this.n = -1;				
		}
	}

}

