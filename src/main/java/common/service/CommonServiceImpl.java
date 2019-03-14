package common.service;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CommonServiceImpl implements CommonService {

	//파일업로드할 폴더 만들기
	private String makeFolder(String category, String upload) {
		//1.upload 폴더가 없다면 만들고
		//2.notice/board/qna .. 등의 카테고리 폴더가 없다면 만들고
		//3.년도 폴더가 없다면 만들고
		//4.월 폴더가 없다면 만들고
		//5.일 폴더가 없다면 만들고
		//총 만들어야 할 최대 폴더는 5개
		String[] folders = new String[5];
		folders[0] = upload; //Study_Spring/.../resources/upload
		folders[1] = upload + File.separator + category;
		//Study_Spring/.../resources/upload/notice
//		년월일 정보는 변경되므로
		Date today = new Date();
		
		//Study_Spring/.../resources/upload/notice/2018
		folders[2] = folders[1] + File.separator 
				+ new SimpleDateFormat("yyyy").format(today);
		//Study_Spring/.../resources/upload/notice/2018/10
		folders[3] = folders[2] + File.separator 
				+ new SimpleDateFormat("MM").format(today);
		//Study_Spring/.../resources/upload/notice/2018/10/12
		folders[4] = folders[3] + File.separator 
				+ new SimpleDateFormat("dd").format(today);
		
		File folder = new File(folders[4]);
		if( !folder.exists() ) folder.mkdirs();
		return folders[4];
	}
	
	@Override
	public String upload(String category, MultipartFile file, HttpSession session) {
		//파일을 업로드할 물리적위치를 지정한다.
		//Study_Spring/metadata/..../server.core/..wtpwebapps/app/
		//Study_Spring/metadata/..../server.core/..wtpwebapps/app/resources
		String path = session.getServletContext().getRealPath("resources");
		
		//파일을 실제 업로드할 폴더: upload
		//Study_Spring/metadata/..../app/resources/upload
		String upload = path + File.separator + "upload";
		
		//카테고리별로 분류하기 위한 폴더: notice, board, qna, ..
		//일별로 관리하기 위한 폴더: 2018 > 10 > 12 
		String uploadFolder = makeFolder(category, upload);
		
		//파일을 서버의 지정한폴더에 저장처리
		//동시다발적으로 첨부하다보니 
		//파일명이 같은 서로 다른 파일을 첨부할 수 도 있다
		//-> 업로드할 파일에 고유한 id를 부여하도록 한다.
		String uuid = UUID.randomUUID().toString() 
			+ "-" + file.getOriginalFilename();
		
		try {
			file.transferTo( new File(uploadFolder, uuid) );
		
		}catch(Exception e) {
		e.printStackTrace();
		}
		
		
		//DB에 관리할 정보: 
		//upload/notice/2018/10/12/123Fdf-dasf-abc.txt
		return uploadFolder.replace(path, "")
					+ File.separator + uuid;
	}

	@Override
	public File download(String filepath, String filename, HttpSession session, HttpServletResponse res) {
		//업로드된 서버의 물리적위치에서 파일을 읽어들여야 한다.
		//Study_Spring/.../resources
		//filepath: /upload/notice/2018/10/12/abc.txt
		String path 
		= session.getServletContext().getRealPath("resources");
		
		//실제 읽어야 할 파일객체 생성
		File file = new File( path + filepath );
		//파일의 MimeType
		String mime = session.getServletContext().getMimeType(filename);
		if( mime == null ) mime = "application/octet-stream";
			
		//로컬시스템에 다운로드처리한다.
		res.setContentType( mime );
		
		try {
			filename = URLEncoder.encode(filename, "utf-8")
							.replaceAll("\\+", "%20")
							.replaceAll("%28", "(")
							.replaceAll("%29", ")");
			res.setHeader("content-disposition", 
							"attachment; filename=" + filename);
			ServletOutputStream out = res.getOutputStream();
			FileCopyUtils.copy( new FileInputStream(file), out );
			out.flush();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return file;
	}

	@Override
	public void emailSend(String name, String email, HttpSession session) {
		// TODO Auto-generated method stub
		
	}

	

}










