package com.miniproject.domain;

import lombok.Data;

@Data
public class UploadedFileVo {
	private String originalFilename;
	private String ext;
	private String newFilename;
	private long fileSize;
	private int boardNo;
	private String base64String;
	private String thumbFilename;
}
