package com.koreait.matzip;

import java.io.File;

import javax.servlet.http.Part;

public class FileUtils {
	public static void makeFolder(String path) { //없을때 만드는 것
		File dir = new File(path);
		if(!dir.exists()) {
			dir.mkdirs(); //폴더 만듬
		}
	}
	
	public static String getExt(String fileNm) {
		return fileNm.substring(fileNm.lastIndexOf("."));
	}

}
