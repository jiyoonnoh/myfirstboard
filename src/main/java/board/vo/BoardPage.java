package board.vo;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class BoardPage extends PageVO{
	//¸ñ·Ï
	private List<BoardVO> list;

	public List<BoardVO> getList() {
		return list;
	}

	public void setList(List<BoardVO> list) {
		this.list = list;
	}
	
}
