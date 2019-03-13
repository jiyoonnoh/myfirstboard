package com.test.app;

import java.io.File;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import board.service.BoardService;
import board.vo.BoardPage;
import board.vo.BoardVO;
import common.service.CommonService;
import member.vo.MemberVO;

@Controller @SessionAttributes("category")
public class BoardController {
	@Autowired private BoardService service;
	
	//÷������ �ٿ�ε�ó��
	@ResponseBody @RequestMapping("/download.bo")
	public File download(@RequestParam int id,
					HttpSession session, HttpServletResponse res) {
		//�ٿ�ε��� ÷�ε� ������ ������ DB���� ��ȸ�ؿ�
		BoardVO vo = service.select(id);

		//������� ���ýý��ۿ� ������ ������ �ٿ�ε��Ѵ�.
		return common.download(vo.getFilepath(), vo.getFilename(),
							session, res);
	}
	
	
	//����ó��
	@RequestMapping("/delete.bo")
	public String delete(@RequestParam int id, HttpSession session) {
		//÷������ �������� �����ϱ�
		File file
		= new File( session.getServletContext().getRealPath("resources")
				+ ((BoardVO)service.select(id)).getFilepath() );
		if( file.exists() ) file.delete();
		
		//db���� ����
		service.delete(id);
		return "redirect:list.bo";
	}
	
	// ���� ó��
	@RequestMapping("/update.bo") 
	  public String update(BoardVO vo, 
			  		@RequestParam String attach, HttpSession session, 
			  		@RequestParam MultipartFile  file) { 
		  
		  //�����ؼ� ÷���� ���� - ������ ���ε� 
		  vo.setFilename(""); 
		  vo.setFilepath("");
	  
	  BoardVO original = service.select(vo.getId()); 
	  String uuid = session.getServletContext().getRealPath("resources") 
			  		+ original.getFilepath();
	  
	  if( file.getSize()>0 ) {
		  vo.setFilepath(
				  common.upload("board", file, session) );
		  vo.setFilename(file.getOriginalFilename());
		  
		  //���� �� ���� �������� ����
		  File f = new File(uuid);
		  if( f.exists() ) f.delete();
	  }else {
		  //÷�ε� ������ �־��µ� �����ϴ� ���
		  if(attach.equals("n") ) {
			  File f = new File(uuid);
			  if( f.exists() ) f.delete();
		  }else {
		  //���� ���� ����
		  if(original.getFilename() != null )
			  vo.setFilename( original.getFilename() );
		  if(original.getFilepath() != null )
			  vo.setFilepath( original.getFilepath() );
	  }
  }
	// ȭ�鿡�� ������ ������ DB�� ���� �� ��ȭ�� ����
	service.update(vo);
	return "redirect:detail.bo?flag=0&id="+vo.getId();
}
		
	//����ȭ�� ��û
	@RequestMapping("/modify.bo")
	public String modify(Model model, @RequestParam int id) {
		//db���� ��������
		model.addAttribute("vo", service.select(id) );
		
		//ȭ�鿡���
		return "board/modify";
	}
	
	//������ȭ�� ��û
		@RequestMapping("/detail.bo")
		public String detail(Model model,@RequestParam int flag, @RequestParam int id) {
			//������ �ۿ� ���� ��ȸ������ó��
			if (flag==1) service.read(id);
			
			//������ ���� ������ DB���� ��ȸ�ؿ�
			model.addAttribute("vo", service.select(id) );
			model.addAttribute("crlf", "\r\n");
			
			//��ȭ�鿡�� ������� ����� ���������������� �˰� �־�߸� ��
			model.addAttribute("page", page);
			
			//��ȭ�鿡 ����Ѵ�.
			return "board/detail";
		}
	
	@Autowired private CommonService common;
	
	//�ű�����ó�� ��û
	@RequestMapping("/insert.bo")
	public String insert(BoardVO vo, HttpSession session,
							@RequestParam MultipartFile file) {
		vo.setWriter( 
			((MemberVO)session.getAttribute("login_info")).getUserid() ); //���̵�
		vo.setFilename("");
		vo.setFilepath("");
		//÷�������� �ִ� ��� ���ε�ó��
		if( file.getSize() >0 ) {
			vo.setFilepath(
					common.upload("board", file, session) );
			vo.setFilename(file.getOriginalFilename());
		}
		
		service.insert(vo);
		return "redirect:list.bo";
	}
	
	@RequestMapping("/new.bo")
	public String board() {
		//�ű԰����� �ۼ�ȭ������ �����Ѵ�
		return "board/new";
	}
	
	@Autowired private BoardPage page;
	
	//�����۸��ȭ�� ��û
	@RequestMapping(value="/list.bo")
	public String list(Model model, 
				@RequestParam(defaultValue="1") int curPage,
				@RequestParam(required=false) String search, 
				@RequestParam(defaultValue="") String keyword) {
		
		model.addAttribute("category", "bo");
		page.setCurPage(curPage);
		page.setSearch(keyword.isEmpty() ? "" : search);
		page.setKeyword(keyword);
		
		model.addAttribute("page", service.list(page) );

		//���ȭ�鿡 ����Ѵ�.
		return "board/list";
	}
}
