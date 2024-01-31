package com.miniproject.domain;

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
public class UploadedFileVo {
	private String originalFilename;
	private String ext;
	private String newFilename;
	private long fileSize;
	private int boardNo;
	private String base64String;
	private String thumbFilename;
}
