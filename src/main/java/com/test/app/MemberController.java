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
	
	//���̵�ã��
	@ResponseBody 
	@RequestMapping(value = "/findid", produces = "text/html; charset=utf-8")
	public String findid() {
		return "/member/findid";
	}
	
	// �α׾ƿ�ó�� ��û
	@ResponseBody
	@RequestMapping("/logout")
	public void logout(SessionStatus session) {
		// ������ ������ �����Ѵ�.
		session.setComplete();
	}

	 //ī�� �α��� ȸ������ó�� ��û
	@ResponseBody @RequestMapping(value="/klogin", produces="application/text; charset=utf-8")
	public String kjoin(@RequestParam String userid, 
						@RequestParam String name, 
						Model model) {

		MemberVO vo = new MemberVO(userid, "social", name, "", "");
		model.addAttribute("login_info", vo);

		// �ߺ��˻�
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

	// �α���ó�� ��û
	@ResponseBody
	@RequestMapping("/login")
	public String login(@RequestParam String userid, Model model, @RequestParam String userpwd) {
		// ȭ�鿡�� �Է��� ���̵�� ��й�ȣ�� ��ġ�ϴ� �����������
		// DB���� ��ȸ�Ͽ�
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("userpwd", userpwd);
		MemberVO vo = service.login(map);

		// �α��ε� ȸ�������� ���ǿ� �����ϵ��� �Ѵ�.
		model.addAttribute("login_info", vo);
		return vo == null ? "false" : "true";
		
		
	}

	// ȸ������ó�� ��û
	@ResponseBody
	@RequestMapping(value = "/join", produces = "text/html; charset=utf-8")
	public String join(MemberVO vo, HttpSession session) {
		// ȭ�鿡�� �Է��� ȸ�������� DB�� ������ ��
		String msg = "<script type='text/javascript'>";
		if (service.insert(vo)) {
			msg += "alert('ȸ�������� �����մϴ�^^'); location='index'";
		} else {
			msg += "alert('ȸ������ ���ФФ�'); history.go(-1)";
		}
		msg += "</script>";

		// Ȩȭ������ �����Ѵ�.
		return msg;
	}

	// ���̵��ߺ�Ȯ�� ��û
	@ResponseBody
	@RequestMapping("/id_check")
	public String id_check(@RequestParam String userid) {
		// �Է��� ���̵� DB�� �����ϴ��� ���θ� �Ǵ��� ��
		// ��밡��: true, ���Ұ�: false
		// ȸ������ȭ���� ��û�� ajax �Լ��� ���ư���
		return String.valueOf(service.id_check(userid));

	}

	// ȸ������ȭ�� ��û
	@RequestMapping("/member")
	public String member() {
		/* model.addAttribute("category", ""); */
		return "member/join";
	}

}
