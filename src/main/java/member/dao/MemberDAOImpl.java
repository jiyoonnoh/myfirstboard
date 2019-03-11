package member.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import member.vo.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {
	@Autowired private SqlSession sql;
	
	@Override
	public boolean id_check(String userid) {
		return (Integer)sql.selectOne("member.mapper.id_check", userid) 
					== 0 ? true : false;
	}

	@Override
	public boolean insert(MemberVO vo) {
		return sql.insert("member.mapper.insert", vo) > 0 ? true : false;
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
		return sql.selectOne("member.mapper.login", map);
	}

	@Override
	public boolean kinsert(MemberVO vo) {
		return sql.insert("member.mapper.kinsert", vo)>0? true:false;
	}

	@Override
	public String findid(String name) {
		return sql.selectOne("member.mapper.findid", name);
	}

}
