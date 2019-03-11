package board.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import board.vo.BoardPage;
import board.vo.BoardVO;

@Repository
public class BoardDAOlmpl implements BoardDAO {
	@Autowired private SqlSession sql;
	
	@Override
	public boolean insert(BoardVO vo) {
		return sql.insert("board.mapper.insert", vo)>0 ? true : false;
	}

	@Override
	public BoardPage list(BoardPage page) {
		//총목록수를 알아야 나머지 정보가 계산된다
		page.setTotList(
				(Integer)sql.selectOne("board.mapper.total", page));
		List<BoardVO> list = sql.selectList("board.mapper.list", page );
		page.setList(list);
		return page;
	}

	@Override
	public BoardVO select(int id) {
		return sql.selectOne("board.mapper.select", id);
	}

	@Override
	public boolean update(BoardVO vo) {
		return sql.update("board.mapper.update", vo)>0 ? true : false;
	}
	
	@Override
	public void read(int id) {
		sql.update("board.mapper.read", id);

	}

	@Override
	public boolean delete(int id) {
		return sql.delete("board.mapper.delete", id)> 0 ? true : false;
	}

	@Override
	public List<BoardVO> list() {
		return sql.selectList("board.mapper.list");
	}

	@Override
	public List<BoardVO> list(HashMap<String, String> map) {
		return sql.selectList("board.mapper.list", map);
	}


}
