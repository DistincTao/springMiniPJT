package com.miniproject.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.miniproject.domain.UploadedFileDto;

/**
 * @packageName : com.miniproject.util
 * @fileName : UploadFileProcess.java
 * @author : DistincTao
 * @date : 2024. 1. 23.
 * @description : 
 */
public class UploadFileProcess {

	public static UploadedFileDto fileUpload(MultipartFile uf, String realPath) {
		UploadedFileDto ufDto = new UploadedFileDto();
		
		try {
			String originalFilename = uf.getOriginalFilename();
			long fileSize = uf.getSize();
			String ContentType = uf.getContentType();
			byte[] bytes = uf.getBytes();

			String completePath = makeCalculatePath(realPath); // 물리적 경로 + \년\월\일
			
			if (fileSize > 0) {
				ufDto.setNewFilename(getNewFileName(originalFilename, realPath, completePath));
				ufDto.setOriginalFilename(originalFilename);
				ufDto.setFileSize(fileSize);
				ufDto.setExt(originalFilename.substring(originalFilename.lastIndexOf(".")));
				
				FileCopyUtils.copy(bytes, new File(realPath + ufDto.getNewFilename())); // 원본이미지 저장
			
				if (ImageMimeType.isImageContentType(ContentType)) { // 파일이 이미지인 경우
					// 썸네일 생성(=> scale down) & 저장 
					System.out.println("이미지 입니다!!!");
					
					makeThumbNailImage(ufDto, completePath, realPath);
					
				} else {
					System.out.println("이미지가 아닙니다!!");
				}
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ufDto;
	}




	/**
	 * @MethodName : makeCalculatePath
	 * @author : DistincTao
	 * @date : 2024. 1. 23.
	 * @description : 
	 * @param realPath
	 * @return : \년\월\일\(세부 폴더) + 새파일 이름
	 */
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
		String date= month + File.separator + (new DecimalFormat("00").format(now.get(Calendar.DATE)) + ""); // \2024\01\22
		
		makeDirectory(realPath, year, month, date);
		
		return realPath + date;
	
	
	}

	/**
	 * @MethodName : makeDirectory
	 * @author : DistincTao
	 * @date : 2024. 1. 23.
	 * @description : \년\월\일에 대한 폴더가 없는 경우 해당 폴더 생성
	 * @param realPath
	 * @param strings
	 */
	private static void makeDirectory(String realPath, String...strings) { // 가변인자 방식으로 받기 (배열 방식)
		// directory 생성
		if (!new File(realPath + strings[strings.length - 1]).exists()) { // date 경로 디렉토리가 없으면
			for (String path : strings) {
				File temp = new File(realPath + path);
				if(!temp.exists()){
					temp.mkdirs();			// 생성
				}
			}
		} 
	}
	
	/**
	 * @MethodName : getNewFileName
	 * @author : DistincTao
	 * @date : 2024. 1. 23.
	 * @description : 새로운 파일 이름을 생성
	 * @param originalFilename
	 * @param realPath
	 * @param completePath
	 * @return : \년\월\일\(세부 폴더) + UUID + 원본 파일 이름
	 */
	private static String getNewFileName(String originalFilename, String realPath, String completePath) {
		String uuid = UUID.randomUUID().toString();
		String newFilename = uuid + "_" + originalFilename;
		String saveDir = completePath.substring(realPath.length()) ;
		System.out.println(saveDir + File.separator + newFilename);
		
		// 테이블에 저장될 업로드 파일이름
		
		return saveDir + File.separator + newFilename;
	}
	
	/**
	 * @MethodName : makeThumbNailImage
	 * @author : DistincTao
	 * @date : 2024. 1. 23.
	 * @description : 저장된 원본파일을 읽은 후 스케일 다운하여 썸네일을 만듦
	 * @param ufDto
	 * @param completePath
	 * @param realPath
	 * @throws IOException
	 */
	private static void makeThumbNailImage(UploadedFileDto ufDto, String completePath, String realPath) throws IOException {
		
		System.out.println("thumbNail 이미지 만들기 : " + realPath + ufDto.getNewFilename());
		
		BufferedImage originImg = ImageIO.read(new File(realPath + ufDto.getNewFilename())); //  원본파일 읽기
		
//		Scalr.resize(originImg, Scalr.Method.AUTOMATIC, Mode.FIT_TO_HEIGHT, 50);
		BufferedImage thumbNailImg = Scalr.resize(originImg, Mode.FIT_TO_HEIGHT, 50);

		 // 썸네일을 저장
		String thumbFilename = "thumb_" + ufDto.getNewFilename().substring(ufDto.getNewFilename().lastIndexOf("\\") + 1);
		File saveTarget = new File(completePath + File.separator + thumbFilename);
		String saveDir = completePath.substring(realPath.length()) ;
		String ext = ufDto.getOriginalFilename().substring(ufDto.getOriginalFilename().lastIndexOf(".") + 1);
		
		if (ImageIO.write(thumbNailImg, ext, saveTarget )) { // 썸네일 저장
			ufDto.setThumbFilename(saveDir + File.separator + thumbFilename);
			System.out.println("썸네일 이미지 저장 완료");
		} else {
			System.out.println("썸네일 이미지 저장 실패");
		}
		
	}




	/**
	 * @MethodName : deleteFile
	 * @author : DistincTao
	 * @date : 2024. 1. 23.
	 * @description : Filelist 내에 removeFile과 Thumbnaile 파일을 삭제
	 * @param fileList
	 * @param removeFile
	 * @param realPath
	 */
	public static void deleteFile(List<UploadedFileDto> fileList, String removeFile, String realPath) {
		// (x)를 클릭 => 삭제
		for (UploadedFileDto dto : fileList) {
			if (dto.getNewFilename().equals(removeFile)) {
				
				File deleteFile = new File(realPath + dto.getNewFilename());
				if (deleteFile.exists()) {
					deleteFile.delete();					
				}
		
				if (dto.getThumbFilename() != null) {
					File thumbFile = new File(realPath + dto.getThumbFilename());
					
					if (thumbFile.exists()) {
						thumbFile.delete();
					}
				}
			}
		}
	}

	/**
	 * @MethodName : deleteAllFile
	 * @author : DistincTao
	 * @date : 2024. 1. 23.
	 * @description : 취소 버튼을 클릭하면 모든 removeFile과 Thumbnaile 파일 삭제
	 * @param fileList
	 * @param realPath
	 */
	public static void deleteAllFile(List<UploadedFileDto> fileList, String realPath) {
		// 취소 버튼을 클릭 => 모드 삭제
		for (UploadedFileDto dto : fileList) {
			File deleteFile = new File(realPath + dto.getNewFilename());
			if (deleteFile.exists()) {
				deleteFile.delete();					
			}
			
			if (dto.getThumbFilename() != null) {
				File thumbFile = new File(realPath + dto.getThumbFilename());
				
				if (thumbFile.exists()) {
					thumbFile.delete();
				}
			}
		}
	}
}


