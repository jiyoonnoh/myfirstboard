package board.service;


import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import board.dao.BoardDAO;
import board.vo.BoardPage;
import board.vo.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired private BoardDAO dao;

	@Override
	public boolean insert(BoardVO vo) {
		return dao.insert(vo);
	}

	@Override
	public BoardVO select(int id) {
		return dao.select(id);
	}

	@Override
	public boolean update(BoardVO vo) {
		return dao.update(vo);
	}
	
	@Override
	public void read(int id) {
		dao.read(id);
	}

	@Override
	public boolean delete(int id) {
		return dao.delete(id);
	}
	
	@Override
	public BoardPage list(BoardPage page) {
		return dao.list(page);
	}

	@Override
	public List<BoardVO> list() {
		return dao.list();
	}

	@Override
	public List<BoardVO> list(HashMap<String, String> map) {
		return dao.list(map);
	}


}
