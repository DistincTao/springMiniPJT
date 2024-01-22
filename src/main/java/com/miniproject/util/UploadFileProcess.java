package com.miniproject.util;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.miniproject.domain.UploadedFileDto;

public class UploadFileProcess {

	public static void fileUpload(MultipartFile uf, String realPath) {
		try {
			String originalFilename = uf.getOriginalFilename();
			long fileSize = uf.getSize();
			String ContentType = uf.getContentType();
			byte[] bytes = uf.getBytes();

		
		
			String completePath = makeCalculatePath(realPath); // 물리적 경로 + \년\월\일
			
			UploadedFileDto ufDto = new UploadedFileDto();
			
			if (fileSize > 0) {
				ufDto.setNewFilename(getNewFileName(originalFilename, realPath, completePath));
				ufDto.setOriginalFilename(originalFilename);
				ufDto.setFileSize(fileSize);
				ufDto.setExt(originalFilename.substring(originalFilename.lastIndexOf(".")));
				
				FileCopyUtils.copy(bytes, new File(realPath + ufDto.getNewFilename())); // 원본이미지 저장
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private static String makeCalculatePath(String realPath) {
		// 현재 날짜 얻어오기
		Calendar now = Calendar.getInstance();
		// 2024 / 01 / 22
//		String year = now.get(Calendar.YEAR)  + "";
//		String month = (now.get(Calendar.MONTH) + 1) + "";
//		// 두자리 형식으로 맞춰주기!
//		new DecimalFormat("00").format(now.get(Calendar.MONTH) +1);
//		
//		String date = now.get(Calendar.DATE) + "";
//		System.out.println(year + " " + new DecimalFormat("00").format(now.get(Calendar.MONTH) +1) + " " + date);
	
		// realPath\2024\01\22
		
		String year = File.separator + (now.get(Calendar.YEAR) + ""); // \2024
		String month = year + File.separator + (new DecimalFormat("00").format(now.get(Calendar.MONTH) +1)); // \2024\01
		String date= month + File.separator + (now.get(Calendar.DATE) + ""); // \2024\01\22
		
		makeDirectory(realPath, year, month, date);
		
		return realPath + date;
	
	
	}

	private static void makeDirectory(String realPath, String...strings) { // 가변인자 방식으로 받기 (배열 방식)
		// directory 생성
		if (!new File(realPath + strings[strings.length - 1]).exists()) { // date 경로 디렉토리가 없으면
			for (String path : strings) {
				File temp = new File(realPath + path);
				if(!temp.exists()){
					temp.mkdir();			// 생성
				}
			}
		} 
	}
	
	private static String getNewFileName(String originalFilename, String realPath, String completePath) {
		String uuid = UUID.randomUUID().toString();
		String newFilename = uuid + "_" + originalFilename;
		String saveDir = completePath.substring(realPath.length()) ;
		System.out.println(saveDir + File.separator + newFilename);
		
		// 테이블에 저장될 업로드 파일이름
		
		return saveDir + File.separator + newFilename;
	}
	
}
