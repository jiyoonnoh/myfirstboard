package board.vo;

public class PageVO {
//	�������� ������ ��� �� : 10
	private int pageList = 10;
//	������ ������ �������� �� : 10
	private int blockPage = 10;
	
	private int totList;  //�� ��ϼ�: DB���� ��ȸ�ؿ´�.
	private int totPage;  //�� ��������
	private int totBlock; //�� ���ϼ�

	private int curPage; //������������ȣ
	private int curBlock; //���������ȣ
	
//	�� �������� ������ ��Ϲ�ȣ: �� ��Ϲ�ȣ, ���� ��Ϲ�ȣ
	private int endList, beginList;
	
//	�� ������ ������ ��������ȣ: �� ��������ȣ, ���� ��������ȣ
	private int endPage, beginPage;

//	�˻�����, Ű����
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
		//�� ��ϼ��� DB���� ��ȸ�ؿ��� �� ��ϼ��� ����
		//�� ��������, �� ������ �����ȴ�.
		
//		�� �������� : �Ѹ�ϼ� / ���������� ��������ϰ��� -> �� ... ������
//					 �������� ������ ���������� +1 
		totPage = totList / pageList;
		if( totList % pageList >0 ) ++totPage;
		
//		�� ���ϼ� :  ���������� / ������ ������ ���������� -> �� ... ������
//					 �������� ������ �������� +1
		totBlock = totPage / blockPage;
		if( totPage % blockPage >0 ) ++totBlock;
		
//		�� �������� ������ ��Ϲ�ȣ: �� ��Ϲ�ȣ ���� ��Ϲ�ȣ
//		�� �������� �� ��Ϲ�ȣ : �� ��ϼ� - (��������ȣ-1) * �������� ������ ��ϼ�
		endList = totList - (curPage-1) * pageList;
//		�� �������� ���� ��Ϲ�ȣ : �� ��Ϲ�ȣ - (�������� ������ ��ϼ�-1)
		beginList = endList - (pageList-1);
		
//		���Ϲ�ȣ : ��������ȣ / ���ϴ� ������ ��������
//				   �������� ������ ������ȣ+1	
		curBlock = curPage / blockPage;
		if( curPage % blockPage>0) ++curBlock;
//		�� ������ �� ��������ȣ : ���Ϲ�ȣ * ���ϴ� ������ ��������
		endPage = curBlock * blockPage;
//		�� ������ ���� ��������ȣ : �� ��������ȣ - (������ ������ ��������-1)
		beginPage = endPage - (blockPage-1);
		
//		�� ��������ȣ�� �� ������������ Ŭ �� �����Ƿ�
//		�� ���������� �� ��������ȣ�� �Ѵ�.
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






