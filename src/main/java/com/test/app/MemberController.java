package com.test.app;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import common.service.CommonService;
import member.service.MemberService;
import member.vo.MemberVO;

@Controller
@SessionAttributes("login_info")
public class MemberController {
	@Autowired private MemberService service;
	
	//회원정보 수정 처리
	@RequestMapping("/update.me")
	public String update(
			@RequestParam String userid,
			@RequestParam String userpwd,
			@RequestParam String name,
			@RequestParam String phone_number,
			@RequestParam String admin,
			HttpSession session, Model model
			) {
		
		MemberVO vo = new MemberVO(userid, userpwd, name, phone_number, admin);
		service.update(vo);
		
		session.setAttribute("member", service.select(userid));
		model.addAttribute("login_info", vo);

			return "redirect:index";
	}
	
	//회원정보 수정  화면
	 @RequestMapping("/modify.me")
	 public String modify(@RequestParam String userid, 
			 			  Model model, 
			 			  HttpSession session) {
		 MemberVO vo = service.select(userid);
		 model.addAttribute("login_info", vo);
		 
		 return "member/modify";
	 }
	
	//탈퇴
	@RequestMapping("/delete.me")
		public String delete(MemberVO vo, SessionStatus session) {
			service.delete(vo);
			session.setComplete();
			
			return "redirect:index";
	}
	
	//마이페이지 이동
	@RequestMapping("mypage")
	public String mypage() {
		return "member/mypage";
	}
	
	
	// 비밀번호 찾기 화면 이동
		@RequestMapping("findpwd")
		public String findpwd() {
			return "member/findpwd";
		}
	
	//아이디 찾기 요청

	// A안
	@ResponseBody
	@RequestMapping(value="/id_find", produces="application/text; charset=utf-8")
	public String id_find(MemberVO vo) {

		// 1. 파라미터 값 확인
		
		String findId = service.findid(vo.getName());
		
		System.out.println("findId ::: "+findId);
		System.out.println("vo.getName() ::: "+vo.getName());

		// 2. 서비스 구현 
		
		return findId;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/pwd_find", produces="application/json; charset=utf-8")
	public Map<String, Object> pwd_find(MemberVO vo) {
		
		Map<String, Object> map = new HashMap<String, Object>();

		MemberVO bean = service.findpwd(vo);
		
		System.out.println("bean ::: "+bean);
//		System.out.println("vo.getName() ::: "+vo.getName());
		
		map.put("bean", bean); //===> "findId 호출 :: 14"
		
		return map;
		
	}
	
	
	
	// B안
	@ResponseBody
	@RequestMapping(value="/id_find2", produces="application/json; charset=utf-8")
	public Map<String, Object> id_find2(MemberVO vo) {
		
		Map<String, Object> map = new HashMap<String, Object>();

		MemberVO bean = service.findid2(vo);
		
		System.out.println("bean ::: "+bean);
//		System.out.println("vo.getName() ::: "+vo.getName());
		
		map.put("bean", bean); //===> "findId 호출 :: 14"
		
		return map;
		
	}
	
	// B안
	@ResponseBody
	@RequestMapping(value="/id_find3", produces="application/text; charset=utf-8")
	public Map<String, Object> id_find3(MemberVO vo) {
		
		Map<String, Object> map = new HashMap<String, Object>();

		String msg = "";
		
		// 1. 현재비밀번호 맞는지 검증
		
		service.kinsert(vo);
		
		// 1-1. 결과로 분기처리
		if(service.kinsert(vo)) {
			map.put("msg","현재비밀번호가 다릅니다.");		
		} else {
			// 2. 비밀번호 변경
			//service.update(vo);
			
			// 결과로 분기처리
			// map.put("msg","성공");		
			
			// map.put("msg","실패");		
		}
		
		return map;
	}
	
	
	//아이디찾기 화면 이동
	@RequestMapping("findid")
	public String findid() {
		return "member/findid";
	}
	
	// 로그아웃처리 요청
	@ResponseBody
	@RequestMapping("/logout")
	public void logout(SessionStatus session) {
		// 세션의 정보를 삭제한다.
		session.setComplete();
	}

	 //카톡 로그인 회원가입처리 요청
	@ResponseBody @RequestMapping(value="/klogin", produces="application/text; charset=utf-8")
	public String kjoin(@RequestParam String userid, 
						@RequestParam String name, 
						Model model) {

		MemberVO vo = new MemberVO(userid, "social", name, "", "");
		model.addAttribute("login_info", vo);

		// 중복검사
		if (!service.id_check(userid)) {
			return "true";
		} else {
			if (service.kinsert(vo)) {
				return "true";
			} else {
				return "false";
			}
		}
	}

	// 로그인처리 요청
	@ResponseBody
	@RequestMapping("/login")
	public String login(@RequestParam String userid, Model model, @RequestParam String userpwd) {
		// 화면에서 입력한 아이디와 비밀번호가 일치하는 사용자정보를
		// DB에서 조회하여
		HashMap<String, String> map = new HashMap<String, String>();
			map.put("userid", userid);
			map.put("userpwd", userpwd);
		MemberVO vo = service.login(map);

		// 로그인된 회원정보는 세션에 관리하도록 한다.
		model.addAttribute("login_info", vo);
		return vo == null ? "false" : "true";
		
		
	}
	
	@Autowired private CommonService common;

	// 회원가입처리 요청
	@ResponseBody
	@RequestMapping(value = "/join", produces = "text/html; charset=utf-8")
	public String join(MemberVO vo, HttpSession session) {
		// 화면에서 입력한 회원정보를 DB에 저장한 후
		String msg = "<script type='text/javascript'>";
		if (service.insert(vo)) {
			common.emailSend(vo.getName(), vo.getUserid(), session);
			msg += "alert('회원가입을 축하합니다^^'); location='index'";
		} else {
			msg += "alert('회원가입 실패ㅠㅠ'); history.go(-1)";
		}
		msg += "</script>";

		// 홈화면으로 연결한다.
		return msg;
	}

	// 아이디중복확인 요청
	@ResponseBody
	@RequestMapping("/id_check")
	public String id_check(@RequestParam String userid) {
		// 입력한 아이디가 DB에 존재하는지 여부를 판단한 후
		// 사용가능: true, 사용불가: false
		// 회원가입화면의 요청한 ajax 함수로 돌아간다
		return String.valueOf(service.id_check(userid));

	}

	// 회원가입화면 요청
	@RequestMapping("/member")
	public String member() {
		/* model.addAttribute("category", ""); */
		return "member/join";
	}

}
