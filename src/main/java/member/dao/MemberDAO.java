package member.dao;

import java.util.HashMap;

import member.vo.MemberVO;

public interface MemberDAO {
	
	//���̵� �ߺ�Ȯ��
	boolean id_check(String userid); 
	
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
