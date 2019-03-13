package board.dao;

import java.util.HashMap;
import java.util.List;

import board.vo.BoardPage;
import board.vo.BoardVO;

public interface BoardDAO {
	boolean insert(BoardVO vo);
	List<BoardVO> list();
	List<BoardVO> list(HashMap<String, String> map);
	BoardPage list(BoardPage page);
	BoardVO select(int id);
	boolean update(BoardVO vo);
	void read(int id);
	boolean delete(int id);
	

}
