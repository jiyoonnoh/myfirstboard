package board.service;

import java.util.HashMap;
import java.util.List;

import board.vo.BoardPage;
import board.vo.BoardVO;

public interface BoardService {

		boolean insert(BoardVO vo);
		List<BoardVO> list();
		List<BoardVO> list(HashMap<String, String> map);//검색어 있는 목록조회
	 	BoardPage list(BoardPage page);  //페이지 정보 조회
		BoardVO select(int id);
		boolean update(BoardVO vo);
		void read(int id);//조회수증가처리
		boolean delete(int id);

}
