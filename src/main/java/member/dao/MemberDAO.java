package member.dao;

import java.util.HashMap;

import member.vo.MemberVO;

public interface MemberDAO {
	boolean id_check(String userid); //아이디 중복확인
	boolean insert(MemberVO vo); //회원가입
	boolean update(MemberVO vo);//내정보변경
	boolean delete(String userid);//회원탈퇴
	MemberVO login(HashMap<String, String> map);//로그인
	boolean kinsert(MemberVO vo);
	public String findid(String name);
}
