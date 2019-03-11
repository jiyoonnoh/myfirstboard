package board.service;

import java.util.HashMap;
import java.util.List;

import board.vo.BoardPage;
import board.vo.BoardVO;

public interface BoardService {

		boolean insert(BoardVO vo);
		List<BoardVO> list();
		List<BoardVO> list(HashMap<String, String> map);//�˻��� �ִ� �����ȸ
	 	BoardPage list(BoardPage page);  //������ ���� ��ȸ
		BoardVO select(int id);
		boolean update(BoardVO vo);
		void read(int id);//��ȸ������ó��
		boolean delete(int id);

}
