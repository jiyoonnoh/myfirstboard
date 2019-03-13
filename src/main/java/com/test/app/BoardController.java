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
	
	//첨부파일 다운로드처리
	@ResponseBody @RequestMapping("/download.bo")
	public File download(@RequestParam int id,
					HttpSession session, HttpServletResponse res) {
		//다운로드할 첨부된 파일의 정보를 DB에서 조회해와
		BoardVO vo = service.select(id);

		//사용자의 로컬시스템에 서버의 파일을 다운로드한다.
		return common.download(vo.getFilepath(), vo.getFilename(),
							session, res);
	}
	
	
	//삭제처리
	@RequestMapping("/delete.bo")
	public String delete(@RequestParam int id, HttpSession session) {
		//첨부파일 서버에서 삭제하기
		File file
		= new File( session.getServletContext().getRealPath("resources")
				+ ((BoardVO)service.select(id)).getFilepath() );
		if( file.exists() ) file.delete();
		
		//db에서 삭제
		service.delete(id);
		return "redirect:list.bo";
	}
	
	// 수정 처리
	@RequestMapping("/update.bo") 
	  public String update(BoardVO vo, 
			  		@RequestParam String attach, HttpSession session, 
			  		@RequestParam MultipartFile  file) { 
		  
		  //변경해서 첨부한 파일 - 서버에 업로드 
		  vo.setFilename(""); 
		  vo.setFilepath("");
	  
	  BoardVO original = service.select(vo.getId()); 
	  String uuid = session.getServletContext().getRealPath("resources") 
			  		+ original.getFilepath();
	  
	  if( file.getSize()>0 ) {
		  vo.setFilepath(
				  common.upload("board", file, session) );
		  vo.setFilename(file.getOriginalFilename());
		  
		  //변경 전 파일 서버에서 삭제
		  File f = new File(uuid);
		  if( f.exists() ) f.delete();
	  }else {
		  //첨부된 파일이 있었는데 삭제하는 경우
		  if(attach.equals("n") ) {
			  File f = new File(uuid);
			  if( f.exists() ) f.delete();
		  }else {
		  //원래 파일 유지
		  if(original.getFilename() != null )
			  vo.setFilename( original.getFilename() );
		  if(original.getFilepath() != null )
			  vo.setFilepath( original.getFilepath() );
	  }
  }
	// 화면에서 변경한 정보를 DB에 저장 후 상세화면 리턴
	service.update(vo);
	return "redirect:detail.bo?flag=0&id="+vo.getId();
}
		
	//수정화면 요청
	@RequestMapping("/modify.bo")
	public String modify(Model model, @RequestParam int id) {
		//db에서 가져오기
		model.addAttribute("vo", service.select(id) );
		
		//화면에출력
		return "board/modify";
	}
	
	//상세정보화면 요청
		@RequestMapping("/detail.bo")
		public String detail(Model model,@RequestParam int flag, @RequestParam int id) {
			//선택한 글에 대한 조회수증가처리
			if (flag==1) service.read(id);
			
			//선택한 글의 정보를 DB에서 조회해와
			model.addAttribute("vo", service.select(id) );
			model.addAttribute("crlf", "\r\n");
			
			//상세화면에서 목록으로 연결시 페이지관련정보를 알고 있어야만 함
			model.addAttribute("page", page);
			
			//상세화면에 출력한다.
			return "board/detail";
		}
	
	@Autowired private CommonService common;
	
	//신규저장처리 요청
	@RequestMapping("/insert.bo")
	public String insert(BoardVO vo, HttpSession session,
							@RequestParam MultipartFile file) {
		vo.setWriter( 
			((MemberVO)session.getAttribute("login_info")).getUserid() ); //아이디
		vo.setFilename("");
		vo.setFilepath("");
		//첨부파일이 있는 경우 업로드처리
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
		//신규공지글 작성화면으로 연결한다
		return "board/new";
	}
	
	@Autowired private BoardPage page;
	
	//공지글목록화면 요청
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

		//목록화면에 출력한다.
		return "board/list";
	}
}
