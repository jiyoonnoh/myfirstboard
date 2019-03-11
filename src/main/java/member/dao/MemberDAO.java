package member.dao;

import java.util.HashMap;

import member.vo.MemberVO;

public interface MemberDAO {
	boolean id_check(String userid); //���̵� �ߺ�Ȯ��
	boolean insert(MemberVO vo); //ȸ������
	boolean update(MemberVO vo);//����������
	boolean delete(String userid);//ȸ��Ż��
	MemberVO login(HashMap<String, String> map);//�α���
	boolean kinsert(MemberVO vo);
	public String findid(String name);
}
