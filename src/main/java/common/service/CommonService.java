package common.service;

import java.io.File;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

public interface CommonService {
	//������, �����, QnA, .... : ī�װ������� ���ε������� �����Ѵ�.
	String upload(String board, MultipartFile file, 
				HttpSession session);
	
	//÷�ε� ���� �ٿ�ε�ó��
	File download(String filepath, String filename,
				HttpSession session, HttpServletResponse res);
	
	//�̸�������ó��
	void emailSend(String name, String email, HttpSession session);
}