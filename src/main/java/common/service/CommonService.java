package common.service;

import java.io.File;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

public interface CommonService {
	//공지글, 방명록, QnA, .... : 카테고리별로 업로드파일을 관리한다.
	String upload(String board, MultipartFile file, 
				HttpSession session);
	
	//첨부된 파일 다운로드처리
	File download(String filepath, String filename,
				HttpSession session, HttpServletResponse res);
	
	//이메일전송처리
	void emailSend(String name, String email, HttpSession session);
}
