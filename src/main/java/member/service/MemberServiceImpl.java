package member.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import member.dao.MemberDAO;
import member.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired private MemberDAO dao;
	
	@Override
	public boolean id_check(String userid) {
		return dao.id_check(userid);
	}

	@Override
	public boolean insert(MemberVO vo) {
		return dao.insert(vo);
	}

	@Override
	public boolean update(MemberVO vo) {
		return false;
	}

	@Override
	public boolean delete(String userid) {
		return false;
	}

	@Override
	public MemberVO login(HashMap<String, String> map) {
		return dao.login(map);
	}

	@Override
	public boolean kinsert(MemberVO vo) {
		return dao.insert(vo);
	}

	@Override
	public String findid(String name) {
		return dao.findid(name);
	}

}
