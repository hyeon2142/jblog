package com.douzone.jblog.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	private static String SAVE_PATH = "/blog-logo-imeges";
	private static String URL_BASE = "/logoImgs";
	
    public String restoreImg(MultipartFile multipartFile) {
    	String url = null;
    	
    	/** 디렉토리없으면 생성 **/
		File uploadDirectory = new File(SAVE_PATH);
		if (!uploadDirectory.exists()) {
			uploadDirectory.mkdir();
		}
    	
    	if(multipartFile.isEmpty()) {
    		return url;
    	}	
    	
    	String originFilename =  multipartFile.getOriginalFilename();
    	String extName = originFilename.substring( originFilename.lastIndexOf('.')+1); //확장자 가져오기
    	String saveFilename = generateSaveFilename(extName);
    	long fileSize = multipartFile.getSize();
    	
    	System.out.println("#############   " + originFilename);
		System.out.println("#############   " + fileSize);
		System.out.println("#############   " + saveFilename);
		
		try {
			byte[] data = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFilename);
			os.write(data);
			os.close();
			
			url = URL_BASE + "/" + saveFilename;
		} catch (IOException e) {
			throw new RuntimeException("file upload error:" + e);
		}

			
    	return url;
    }

	private String generateSaveFilename(String extName) {
		String filename = "";
		
		Calendar calendar = Calendar.getInstance();
		
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);
		
		
		return filename;
	}
}
