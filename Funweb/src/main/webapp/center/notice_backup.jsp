<%@page import="board.BoardDTO"%>
<%@page import="java.util.List"%>
<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
BoardDAO dao = new BoardDAO();
//-------------------------------------------------------------------------------------
//BoardDAO 객체의 selectListCount() 메서드를 호출하여 전체 게시물 수 조회
//=> 페이지 목록 계산에 필요
int listCount = dao.selectListCount();
// out.println("게시물 수 : " + listCount);

//페이징 처리에 사용될 변수 선언
int pageNum = 1; // 현재 페이지 번호(기본값 1 페이지로 설정)
int listLimit = 10; // 한 페이지 당 표시할 게시물 수
int pageLimit = 10; // 한 페이지 당 표시할 페이지 목록 수

//단, URL 파라미터로 현재 페이지번호(pageNum) 가 전달됐을 경우 가져와서 변수에 저장
if(request.getParameter("pageNum") != null) {
	pageNum = Integer.parseInt(request.getParameter("pageNum")); // String -> int 변환
}
//-------------------------------------------------------------------------------------
//페이징 처리를 위한 계산 작업
//1. 현재 페이지에서 표시할 전체 페이지 수 계산
//=> 총 게시물 수 / 페이지 당 표시할 게시물 수
//=> 주의! 총 게시물 수 / 페이지 당 표시할 게시물 수 연산 시 int / int = int 이므로
// 나머지 값을 실수로 계산되게 하려면 double 타입 연산 필요함
// (즉, 최소 하나의 값을 double 타입으로 변환 후 나눗셈 연산 수행)
//=> 계산 결과를 소수점 첫째자리에서 올림 처리를 위해 0.9 더하기
// ex) 4.5 일 경우 1 페이지가 더 필요하므로 5.x 가 되어야 함(x.1 ~ x.9 까지 + 0.9)
//=> 올림 처리된 값을 정수화를 통해 실수 제거
//int maxPage = (int)((double)listCount / listLimit + 0.9);
//java.lang.Math 클래스의 ceil() 메서드를 사용하여 올림 처리 가능
int maxPage = (int)Math.ceil((double)listCount / listLimit);

//2. 현재 페이지에서 보여줄 시작 페이지 번호(1, 11, 21 등의 시작 번호) 계산
int startPage = ((int)((double)pageNum / pageLimit + 0.9) - 1) * pageLimit + 1;

//3. 현재 페이지에서 보여줄 끝 페이지 번호(10, 20, 30 등의 끝 번호) 계산
int endPage = startPage + pageLimit - 1;

//4. 만약, 끝 페이지(endPage)가 현재 페이지에서 표시할 총 페이지 수(maxPage)보다 클 경우
// 끝 페이지 번호를 총 페이지 수로 대체
if(endPage > maxPage) {
	endPage = maxPage;
}
//-------------------------------------------------------------------------------------
// BoardDAO 객체의 selectBoardList() 메서드 호출
// => 파라미터 : 현재 페이지 번호, 페이지 당 게시물 수    리턴타입 : java.util.List
// 조회 시 시작 게시물 번호(LIMIT 절에 사용할 시작 레코드(행) 번호) 계산
// => 공식 : (현재페이지번호 - 1) * 페이지 당 게시물 수
List boardList = dao.selectBoardList(pageNum, listLimit);
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
					<a href="notice.jsp?pageNum=<%=pageNum - 1 %>">Prev</a>
				<%} else { %>
					<a onclick="">Prev</a>
				<%} %>
				
				<!-- 페이지 목록은 시작페이지(startPage) 부터 끝페이지(endPage) 까지 표시 -->
				<%for(int i = startPage; i <= endPage; i++) { %>
					<!-- 단, 현재 페이지 번호는 하이퍼링크 없이 표시(현재페이지번호와 i가 같을 경우) -->
					<%if(pageNum == i) { %>
						<a onclick=""><%=i %></a>
					<%} else { %>
						<a href="notice.jsp?pageNum=<%=i %>"><%=i %></a>
					<%} %>
				<%} %>
				
				<%if(pageNum < endPage) { %>
					<a href="notice.jsp?pageNum=<%=pageNum + 1%>">Next</a>
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


