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
	
	//ȸ������ ���� ó��
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
	
	//ȸ������ ����  ȭ��
	 @RequestMapping("/modify.me")
	 public String modify(@RequestParam String userid, 
			 			  Model model, 
			 			  HttpSession session) {
		 MemberVO vo = service.select(userid);
		 model.addAttribute("login_info", vo);
		 
		 return "member/modify";
	 }
	
	//Ż��
	@RequestMapping("/delete.me")
		public String delete(MemberVO vo, SessionStatus session) {
			service.delete(vo);
			session.setComplete();
			
			return "redirect:index";
	}
	
	//���������� �̵�
	@RequestMapping("mypage")
	public String mypage() {
		return "member/mypage";
	}
	
	
	// ��й�ȣ ã�� ȭ�� �̵�
		@RequestMapping("findpwd")
		public String findpwd() {
			return "member/findpwd";
		}
	
	//���̵� ã�� ��û

	// A��
	@ResponseBody
	@RequestMapping(value="/id_find", produces="application/text; charset=utf-8")
	public String id_find(MemberVO vo) {

		// 1. �Ķ���� �� Ȯ��
		
		String findId = service.findid(vo.getName());
		
		System.out.println("findId ::: "+findId);
		System.out.println("vo.getName() ::: "+vo.getName());

		// 2. ���� ���� 
		
		return findId;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/pwd_find", produces="application/json; charset=utf-8")
	public Map<String, Object> pwd_find(MemberVO vo) {
		
		Map<String, Object> map = new HashMap<String, Object>();

		MemberVO bean = service.findpwd(vo);
		
		System.out.println("bean ::: "+bean);
//		System.out.println("vo.getName() ::: "+vo.getName());
		
		map.put("bean", bean); //===> "findId ȣ�� :: 14"
		
		return map;
		
	}
	
	
	
	// B��
	@ResponseBody
	@RequestMapping(value="/id_find2", produces="application/json; charset=utf-8")
	public Map<String, Object> id_find2(MemberVO vo) {
		
		Map<String, Object> map = new HashMap<String, Object>();

		MemberVO bean = service.findid2(vo);
		
		System.out.println("bean ::: "+bean);
//		System.out.println("vo.getName() ::: "+vo.getName());
		
		map.put("bean", bean); //===> "findId ȣ�� :: 14"
		
		return map;
		
	}
	
	// B��
	@ResponseBody
	@RequestMapping(value="/id_find3", produces="application/text; charset=utf-8")
	public Map<String, Object> id_find3(MemberVO vo) {
		
		Map<String, Object> map = new HashMap<String, Object>();

		String msg = "";
		
		// 1. �����й�ȣ �´��� ����
		
		service.kinsert(vo);
		
		// 1-1. ����� �б�ó��
		if(service.kinsert(vo)) {
			map.put("msg","�����й�ȣ�� �ٸ��ϴ�.");		
		} else {
			// 2. ��й�ȣ ����
			//service.update(vo);
			
			// ����� �б�ó��
			// map.put("msg","����");		
			
			// map.put("msg","����");		
		}
		
		return map;
	}
	
	
	//���̵�ã�� ȭ�� �̵�
	@RequestMapping("findid")
	public String findid() {
		return "member/findid";
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
	
	@Autowired private CommonService common;

	// ȸ������ó�� ��û
	@ResponseBody
	@RequestMapping(value = "/join", produces = "text/html; charset=utf-8")
	public String join(MemberVO vo, HttpSession session) {
		// ȭ�鿡�� �Է��� ȸ�������� DB�� ������ ��
		String msg = "<script type='text/javascript'>";
		if (service.insert(vo)) {
			common.emailSend(vo.getName(), vo.getUserid(), session);
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
