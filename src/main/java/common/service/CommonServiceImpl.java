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
		}
		
		return file;
	}

	@Override
	public void emailSend(String name, String email, HttpSession session) {
//		simpleEmail(name, email);
//		attachEmail(name, email, session);
		htmlEmail(name, email, session);
	}
	
	//html 형태의 이메일전송처리
	private void htmlEmail(String name, String email, HttpSession session) {
		HtmlEmail mail = new HtmlEmail();
		mail.setCharset("utf-8");
		mail.setDebug(true);
		
		//메일서버지정해서 로그인
		mail.setHostName("smtp.naver.com");
		mail.setAuthentication("ojink2", "");
		mail.setSSLOnConnect(true);
		
		try {
			//보내는이, 받는이 정보
			mail.setFrom("ojink2@naver.com", "한울관리자");
			mail.addTo(email, name);
			
			mail.setSubject("Html태그이메일전송하기");
			
			StringBuilder sb = new StringBuilder();
			sb.append("<html>");
			sb.append("<body>");
			sb.append("<h3>한울 응용SW엔지니어링과정</h3>");
			sb.append("회원가입을 축하합니다<br>");
			sb.append("과정안내는 <a href='http://hanuledu.co.kr'>한울홈페이지</a>를 참고하세요<br>");
			sb.append("<img style='width:200px; height:100px;' src='http://hanuledu.co.kr/data/banner/2hedU2HEfy9zSgGWphVP_1500684652.png'/><br>");
			sb.append("<input type='button' value='메시지' onclick='alert(\"축하^^\")' />");
			sb.append("</body>");
			sb.append("</html>");
			mail.setHtmlMsg(sb.toString());

			EmailAttachment attach = new EmailAttachment();
			attach.setPath(session.getServletContext().getRealPath("resources")
					+ File.separator + "css" + File.separator + "default.css");
			mail.attach(attach);
			
			mail.send();
			
		}catch(Exception e) {
		}
		
	}
	
	//파일첨부이메일전송처리
	private void attachEmail(String name, String email, HttpSession session) {
		MultiPartEmail mail = new MultiPartEmail();
		mail.setCharset("utf-8");
		mail.setDebug(true);
		
		//메일보낼서버지정, 인증받기
		mail.setHostName("smtp.naver.com");
		mail.setAuthentication("ojink2", "");
		mail.setSSLOnConnect(true);
		
		try {
			// 보내는이 정보
			mail.setFrom("ojink2@naver.com", "한울관리자");
			// 받는이 정보
			mail.addTo(email, name);
			mail.setSubject("파일첨부한 이메일전송");
			mail.setMsg("회원가입을 축하합니다! \n첨부된 파일을 확인하세요~");

			// 파일첨부하기
			EmailAttachment attach = new EmailAttachment();
			// 물리적인 경로
			attach.setPath("D:\\Oracle\\훈련생명단.txt");
			mail.attach(attach);

			// 현재 프로젝트 내의 물리적파일경로
			attach = new EmailAttachment();
			attach.setPath(session.getServletContext().getRealPath("resources") + File.separator + "img"
					+ File.separator + "hanul.png");
			mail.attach(attach);

			// URL을 통해 파일첨부하기
			attach = new EmailAttachment();
			attach.setURL(
					new URL("https://www.google.co.kr/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png"));
			mail.attach(attach);

			mail.send();
		} catch (Exception e) {
		}
	}
	
	//기본이메일전송처리
	private void simpleEmail(String name, String email) {
		SimpleEmail mail = new SimpleEmail();
		mail.setCharset("utf-8");
		mail.setDebug(true); //이메일송신과정에 대한 로그
		
		//이메일을 보내는 이(관리자)의 정보
		mail.setHostName("smtp.naver.com");
		mail.setAuthentication("ojink2", "");
		mail.setSSLOnConnect(true);
		
		try {
			//보내는 이의 정보
			mail.setFrom("아이디@naver.com");
			//받는 이의 정보
			mail.addTo(email, name);
			
			//제목
			mail.setSubject("한울 응용SW엔지니어링과정");
			//내용
			mail.setMsg("과정에 입학하심을 축하합니다!!");
			
			mail.send();
		}catch(Exception e) {
		}
	}
}










