package com.miniproject.domain;

import lombok.Data;

@Data
public class UploadedFileDto {
	private String originalFileName;
	private String ext;
	private String newFileName;
	private long fileSize;
	private int boardNo;
	private String base64String;
}
