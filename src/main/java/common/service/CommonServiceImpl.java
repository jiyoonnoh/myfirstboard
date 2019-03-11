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

	//���Ͼ��ε��� ���� �����
	private String makeFolder(String category, String upload) {
		//1.upload ������ ���ٸ� �����
		//2.notice/board/qna .. ���� ī�װ��� ������ ���ٸ� �����
		//3.�⵵ ������ ���ٸ� �����
		//4.�� ������ ���ٸ� �����
		//5.�� ������ ���ٸ� �����
		//�� ������ �� �ִ� ������ 5��
		String[] folders = new String[5];
		folders[0] = upload; //Study_Spring/.../resources/upload
		folders[1] = upload + File.separator + category;
		//Study_Spring/.../resources/upload/notice
//		����� ������ ����ǹǷ�
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
		//������ ���ε��� ��������ġ�� �����Ѵ�.
		//Study_Spring/metadata/..../server.core/..wtpwebapps/app/
		//Study_Spring/metadata/..../server.core/..wtpwebapps/app/resources
		String path = session.getServletContext().getRealPath("resources");
		
		//������ ���� ���ε��� ����: upload
		//Study_Spring/metadata/..../app/resources/upload
		String upload = path + File.separator + "upload";
		
		//ī�װ������� �з��ϱ� ���� ����: notice, board, qna, ..
		//�Ϻ��� �����ϱ� ���� ����: 2018 > 10 > 12 
		String uploadFolder = makeFolder(category, upload);
		
		//������ ������ ������������ ����ó��
		//���ôٹ������� ÷���ϴٺ��� 
		//���ϸ��� ���� ���� �ٸ� ������ ÷���� �� �� �ִ�
		//-> ���ε��� ���Ͽ� ������ id�� �ο��ϵ��� �Ѵ�.
		String uuid = UUID.randomUUID().toString() 
			+ "-" + file.getOriginalFilename();
		
		try {
			file.transferTo( new File(uploadFolder, uuid) );
		
		}catch(Exception e) {
		}
		
		//DB�� ������ ����: 
		//upload/notice/2018/10/12/123Fdf-dasf-abc.txt
		return uploadFolder.replace(path, "")
					+ File.separator + uuid;
	}

	@Override
	public File download(String filepath, String filename, HttpSession session, HttpServletResponse res) {
		//���ε�� ������ ��������ġ���� ������ �о�鿩�� �Ѵ�.
		//Study_Spring/.../resources
		//filepath: /upload/notice/2018/10/12/abc.txt
		String path 
		= session.getServletContext().getRealPath("resources");
		
		//���� �о�� �� ���ϰ�ü ����
		File file = new File( path + filepath );
		//������ MimeType
		String mime = session.getServletContext().getMimeType(filename);
		if( mime == null ) mime = "application/octet-stream";
			
		//���ýý��ۿ� �ٿ�ε�ó���Ѵ�.
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
	
	//html ������ �̸�������ó��
	private void htmlEmail(String name, String email, HttpSession session) {
		HtmlEmail mail = new HtmlEmail();
		mail.setCharset("utf-8");
		mail.setDebug(true);
		
		//���ϼ��������ؼ� �α���
		mail.setHostName("smtp.naver.com");
		mail.setAuthentication("ojink2", "");
		mail.setSSLOnConnect(true);
		
		try {
			//��������, �޴��� ����
			mail.setFrom("ojink2@naver.com", "�ѿ������");
			mail.addTo(email, name);
			
			mail.setSubject("Html�±��̸��������ϱ�");
			
			StringBuilder sb = new StringBuilder();
			sb.append("<html>");
			sb.append("<body>");
			sb.append("<h3>�ѿ� ����SW�����Ͼ����</h3>");
			sb.append("ȸ�������� �����մϴ�<br>");
			sb.append("�����ȳ��� <a href='http://hanuledu.co.kr'>�ѿ�Ȩ������</a>�� �����ϼ���<br>");
			sb.append("<img style='width:200px; height:100px;' src='http://hanuledu.co.kr/data/banner/2hedU2HEfy9zSgGWphVP_1500684652.png'/><br>");
			sb.append("<input type='button' value='�޽���' onclick='alert(\"����^^\")' />");
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
	
	//����÷���̸�������ó��
	private void attachEmail(String name, String email, HttpSession session) {
		MultiPartEmail mail = new MultiPartEmail();
		mail.setCharset("utf-8");
		mail.setDebug(true);
		
		//���Ϻ�����������, �����ޱ�
		mail.setHostName("smtp.naver.com");
		mail.setAuthentication("ojink2", "");
		mail.setSSLOnConnect(true);
		
		try {
			// �������� ����
			mail.setFrom("ojink2@naver.com", "�ѿ������");
			// �޴��� ����
			mail.addTo(email, name);
			mail.setSubject("����÷���� �̸�������");
			mail.setMsg("ȸ�������� �����մϴ�! \n÷�ε� ������ Ȯ���ϼ���~");

			// ����÷���ϱ�
			EmailAttachment attach = new EmailAttachment();
			// �������� ���
			attach.setPath("D:\\Oracle\\�Ʒû�����.txt");
			mail.attach(attach);

			// ���� ������Ʈ ���� ���������ϰ��
			attach = new EmailAttachment();
			attach.setPath(session.getServletContext().getRealPath("resources") + File.separator + "img"
					+ File.separator + "hanul.png");
			mail.attach(attach);

			// URL�� ���� ����÷���ϱ�
			attach = new EmailAttachment();
			attach.setURL(
					new URL("https://www.google.co.kr/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png"));
			mail.attach(attach);

			mail.send();
		} catch (Exception e) {
		}
	}
	
	//�⺻�̸�������ó��
	private void simpleEmail(String name, String email) {
		SimpleEmail mail = new SimpleEmail();
		mail.setCharset("utf-8");
		mail.setDebug(true); //�̸��ϼ۽Ű����� ���� �α�
		
		//�̸����� ������ ��(������)�� ����
		mail.setHostName("smtp.naver.com");
		mail.setAuthentication("ojink2", "");
		mail.setSSLOnConnect(true);
		
		try {
			//������ ���� ����
			mail.setFrom("���̵�@naver.com");
			//�޴� ���� ����
			mail.addTo(email, name);
			
			//����
			mail.setSubject("�ѿ� ����SW�����Ͼ����");
			//����
			mail.setMsg("������ �����Ͻ��� �����մϴ�!!");
			
			mail.send();
		}catch(Exception e) {
		}
	}
}









