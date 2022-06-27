<%@page import="board.BoardDTO"%>
<%@page import="java.util.List"%>
<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 검색어(keyword) 가져오기
String keyword = request.getParameter("keyword");
String searchField = request.getParameter("searchField");

// BoardDAO 객체의 selectBoardList() 메서드를 호출하여 게시물 조회
// => 단, 메서드 오버로딩을 통해 동일한 이름으로 검색어를 포함하는 메서드 호출 
// => 파라미터 : 검색어, 현재 페이지 번호, 페이지 당 게시물 수
// => 리턴타입 : List(ArrayList 객체)
BoardDAO dao = new BoardDAO();

int listCount = dao.selectListCount(searchField, keyword);
out.println("게시물 수  : " + listCount);

int pageNum = 1; // 현재 페이지 번호(기본값 1 페이지로 설정)
int listLimit = 10; // 한 페이지 당 표시할 게시물 수
int pageLimit = 10; // 한 페이지 당 표시할 페이지 목록 수

//단, URL 파라미터로 현재 페이지번호(pageNum) 가 전달됐을 경우 가져와서 변수에 저장
if(request.getParameter("pageNum") != null) {
	pageNum = Integer.parseInt(request.getParameter("pageNum")); // String -> int 변환
}

int maxPage = (int)Math.ceil((double)listCount / listLimit);

int startPage = ((int)((double)pageNum / pageLimit + 0.9) - 1) * pageLimit + 1;

int endPage = startPage + pageLimit - 1;

if(endPage > maxPage) {
	endPage = maxPage;
}

List boardList = dao.selectBoardList(searchField, keyword, pageNum, listLimit);
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>center/notice.jsp</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="wrap">
		<!-- 헤더 들어가는곳 -->
		<jsp:include page="../inc/top.jsp" />
		<!-- 헤더 들어가는곳 -->

		<!-- 본문들어가는 곳 -->
		<!-- 본문 메인 이미지 -->
		<div id="sub_img_center"></div>
		<!-- 왼쪽 메뉴 -->
		<nav id="sub_menu">
			<ul>
				<li><a href="./notice.jsp">Notice</a></li>
				<li><a href="#">Public News</a></li>
				<li><a href="./driver.jsp">Driver Download</a></li>
				<li><a href="#">Service Policy</a></li>
			</ul>
		</nav>
		<!-- 본문 내용 -->
		<article>
			<h1>Notice</h1>
			<table id="notice">
				<tr>
					<th class="tno">No.</th>
					<th class="ttitle">Title</th>
					<th class="twrite">Writer</th>
					<th class="tdate">Date</th>
					<th class="tread">Read</th>
				</tr>
				<%-- 실제 게시물 목록이 표시될 위치 --%>
				<%
// 				for(int i = 0; i < boardList.size(); i++) {
// 					Object o = boardList.get(i);
					
// 				}
				
				// 향상된 for문 사용 시
				for(Object o : boardList) {
// 					o.getIdx(); // 오류 발생! 업캐스팅 된 객체는 슈퍼클래스의 멤버만 접근 됨
					// => 따라서, BoardDTO 타입(서브클래스)으로 다운캐스팅 후에 사용해야한다!
					BoardDTO board = (BoardDTO)o; // Object -> BoardDTO 다운캐스팅
					%>
					<tr onclick="location.href='notice_content.jsp?idx=<%=board.getIdx()%>&pageNum=<%=pageNum%>'">
						<td><%=board.getIdx() %></td>
						<td class="left"><%=board.getSubject() %></td>
						<td><%=board.getName() %></td>
						<td><%=board.getDate() %></td>
						<td><%=board.getReadcount() %></td>
					</tr>
					<%
				}
				%>
			</table>
			<div id="table_search">
				<input type="button" value="글쓰기" class="btn" 
						onclick="location.href='./notice_write.jsp'">
			</div>
			<div id="table_search">
				<form action="./notice_search.jsp" method="get">
					<select name="searchField">
						<option disabled="disabled" selected="selected">검색 항목을 선택하세요.</option>
						<option value="subject">제목</option>
						<option value="name">작성자</option>
						<option value="content">내용</option>
					</select>
					<input type="text" name="keyword" class="input_box">
					<input type="submit" value="Search" class="btn">
				</form>
			</div>

			<div class="clear"></div>
			<div id="page_control">
				<%if(pageNum > 1) { %>
					<a href="notice_search.jsp?pageNum=<%=pageNum - 1 %>">Prev</a>
				<%} else { %>
					<a onclick="">Prev</a>
				<%} %>
				
				<!-- 페이지 목록은 시작페이지(startPage) 부터 끝페이지(endPage) 까지 표시 -->
				<%for(int i = startPage; i <= endPage; i++) { %>
					<!-- 단, 현재 페이지 번호는 하이퍼링크 없이 표시(현재페이지번호와 i가 같을 경우) -->
					<%if(pageNum == i) { %>
						<a onclick=""><%=i %></a>
					<%} else { %>
						<a href="notice_search.jsp?pageNum=<%=i %>"><%=i %></a>
					<%} %>
				<%} %>
				
				<%if(pageNum < endPage) { %>
					<a href="notice_search.jsp?pageNum=<%=pageNum + 1%>">Next</a>
				<%} else { %>
					<a onclick="">Next</a>
				<%} %>
			</div>
		</article>

		<div class="clear"></div>
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp" />
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>




