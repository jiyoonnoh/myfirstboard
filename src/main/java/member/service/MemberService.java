package member.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import member.vo.MemberVO;

public interface MemberService {
	boolean id_check(String userid); //���̵� �ߺ�Ȯ��
	boolean insert(MemberVO vo); //ȸ������
	boolean update(MemberVO vo);//����������
	boolean delete(MemberVO vo);//ȸ��Ż��
	MemberVO login(HashMap<String, String> map);//�α���
	boolean kinsert(MemberVO vo);
	public String findid(String name);
	
	MemberVO select(String userid);
	
	public MemberVO findid2(MemberVO vo);
	public MemberVO findpwd(MemberVO vo);
}
