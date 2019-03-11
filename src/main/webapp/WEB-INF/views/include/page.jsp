<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
.page_on, .page_off{
display: inline-block; width: 30px; line-height: 28px; cursor: pointer;
}
.page_on { border: 1px solid #98D3FF; font-weight:bold; 
color:#fff; background:#98D3FF;  }

.page_off:hover { text-decoration: none; }

.page_next, .page_last, .page_prev, .page_first { 
	display: inline-block;	width: 30px;	
	line-height: 28px;	border:1px solid #98D3FF;
	text-indent: -99999px; 
}
.page_next { 
background: url("img/page_next.jpg") center no-repeat;
}
.page_last {
background: url("img/page_last.jpg") center no-repeat;
}
.page_prev {
background: url("img/page_prev.jpg") center no-repeat;
}
.page_first {
background: url("img/page_first.jpg") center no-repeat;
}
</style>
<script type="text/javascript">
	function go_page(no){
		$('[name=curPage]').val(no);
		$('#list').submit();
	}
</script>

<!-- 첫블럭이 아니면 이전블럭처리 -->
<c:if test="${page.curBlock gt 1 }">
<a class="page_first" onclick="go_page(1)">처음</a><a 
class="page_prev" 
onclick="go_page(${page.beginPage-page.blockPage})">이전</a>
<!-- 이전블럭의 마지막페이지  -->
<%-- <a class="page_prev" onclick="go_page(${page.beginPage-1})">이전</a> --%>
<!-- 이전블럭의 첫페이지 -->
</c:if>

<c:forEach var="i" begin="${page.beginPage }" end="${page.endPage }">
<!-- 현재페이지는 그대로 보여지게 -->
<c:if test="${i eq page.curPage }">
	<span class="page_on">${i }</span>
</c:if>
<!-- 다른페이지는 선택할 수 있는 형태로 보여지게 -->
<c:if test="${i ne page.curPage }">
	<a class="page_off" onclick="go_page(${i})">${i }</a>
</c:if>
</c:forEach>


<!-- 마지막블럭이 아니면 다음블럭에 대한 처리 -->
<c:if test="${page.curBlock lt page.totBlock}">
<a class="page_next" onclick="go_page(${page.endPage+1})"
>다음</a><a class="page_last" onclick="go_page(${page.totPage})">마지막</a>

</c:if>


