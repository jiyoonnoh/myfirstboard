package com.test.app;

import java.util.HashMap;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import member.service.MemberService;
import member.vo.MemberVO;

@Controller
@SessionAttributes("login_info")
public class MemberController {
	@Autowired
	private MemberService service;
	
	//아이디찾기
	@ResponseBody 
	@RequestMapping(value = "/findid", produces = "text/html; charset=utf-8")
	public String findid() {
		return "/member/findid";
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

	// 회원가입처리 요청
	@ResponseBody
	@RequestMapping(value = "/join", produces = "text/html; charset=utf-8")
	public String join(MemberVO vo, HttpSession session) {
		// 화면에서 입력한 회원정보를 DB에 저장한 후
		String msg = "<script type='text/javascript'>";
		if (service.insert(vo)) {
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
