package board.vo;

public class PageVO {
//	페이지당 보여질 목록 수 : 10
	private int pageList = 10;
//	블럭당 보여질 페이지의 수 : 10
	private int blockPage = 10;
	
	private int totList;  //총 목록수: DB에서 조회해온다.
	private int totPage;  //총 페이지수
	private int totBlock; //총 블록수

	private int curPage; //현재페이지번호
	private int curBlock; //현재블럭번호
	
//	각 페이지에 보여질 목록번호: 끝 목록번호, 시작 목록번호
	private int endList, beginList;
	
//	각 블럭에 보여질 페이지번호: 끝 페이지번호, 시작 페이지번호
	private int endPage, beginPage;

//	검색조건, 키워드
	private String search, keyword;
	
	
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getPageList() {
		return pageList;
	}

	public void setPageList(int pageList) {
		this.pageList = pageList;
	}

	public int getBlockPage() {
		return blockPage;
	}

	public void setBlockPage(int blockPage) {
		this.blockPage = blockPage;
	}

	public int getTotList() {
		return totList;
	}

	public void setTotList(int totList) {
		this.totList = totList;
		//총 목록수를 DB에서 조회해오면 총 목록수에 따라
		//총 페이지수, 총 블럭수 결정된다.
		
//		총 페이지수 : 총목록수 / 한페이지당 보여질목록갯수 -> 몫 ... 나머지
//					 나머지가 있으면 페이지갯수 +1 
		totPage = totList / pageList;
		if( totList % pageList >0 ) ++totPage;
		
//		총 블록수 :  총페이지수 / 블럭당 보여질 페이지갯수 -> 몫 ... 나머지
//					 나머지가 있으면 블럭갯수 +1
		totBlock = totPage / blockPage;
		if( totPage % blockPage >0 ) ++totBlock;
		
//		각 페이지에 보여질 목록번호: 끝 목록번호 시작 목록번호
//		각 페이지의 끝 목록번호 : 총 목록수 - (페이지번호-1) * 페이지당 보여질 목록수
		endList = totList - (curPage-1) * pageList;
//		각 페이지의 시작 목록번호 : 끝 목록번호 - (페이지당 보여질 목록수-1)
		beginList = endList - (pageList-1);
		
//		블록번호 : 페이지번호 / 블록당 보여질 페이지수
//				   나머지가 있으면 블럭번호+1	
		curBlock = curPage / blockPage;
		if( curPage % blockPage>0) ++curBlock;
//		각 블럭의 끝 페이지번호 : 블록번호 * 블록당 보여질 페이지수
		endPage = curBlock * blockPage;
//		각 블럭의 시작 페이지번호 : 끝 페이지번호 - (블럭당 보여질 페이지수-1)
		beginPage = endPage - (blockPage-1);
		
//		끝 페이지번호가 총 페이지수보다 클 수 없으므로
//		총 페이지수를 끝 페이지번호로 한다.
		if( endPage>totPage ) endPage = totPage;
	}

	public int getTotPage() {
		return totPage;
	}

	public void setTotPage(int totPage) {
		this.totPage = totPage;
	}

	public int getTotBlock() {
		return totBlock;
	}

	public void setTotBlock(int totBlock) {
		this.totBlock = totBlock;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getCurBlock() {
		return curBlock;
	}

	public void setCurBlock(int curBlock) {
		this.curBlock = curBlock;
	}

	public int getEndList() {
		return endList;
	}

	public void setEndList(int endList) {
		this.endList = endList;
	}

	public int getBeginList() {
		return beginList;
	}

	public void setBeginList(int beginList) {
		this.beginList = beginList;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getBeginPage() {
		return beginPage;
	}

	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}
	
}







