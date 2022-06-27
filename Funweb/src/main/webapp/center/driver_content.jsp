<%@page import="board.FileBoardDTO"%>
<%@page import="board.FileBoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
// idx, pageNum 가져오기
int idx = Integer.parseInt(request.getParameter("idx"));
String pageNum = request.getParameter("pageNum");

// if(pageNum == null) {
// 	pageNum = "1";
// }

FileBoardDAO daoF = new FileBoardDAO();

// BoardDAO 객체의 updateReadcount() 메서드를 호출하여 게시물 조회수 증가
// => 파라미터 : 글번호(idx), 리턴타입 : void
// => readcount = readcount + 1
daoF.updateReadcount(idx);

// BoardDAO 객체의 selectBoard() 메서드를 호출하여 게시물 1개 정보 조회
// => 파라미터 : 글번호(idx), 리턴타입 : BoardDTO(board)
FileBoardDTO fileBoard = daoF.selectBoard(idx);
%>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>center/driver_content.jsp</title>
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
			<h1>Driver Content</h1>
			<table id="notice">
				<tr>
					<td>글번호</td>
					<td><%=fileBoard.getIdx() %></td>
					<td>글쓴이</td>
					<td><%=fileBoard.getName() %></td>
				</tr>
				<tr>
					<td>작성일</td>
					<td><%=fileBoard.getDate() %></td>
					<td>조회수</td>
					<td><%=fileBoard.getReadcount() %></td>
				</tr>
				<tr>
					<td>제목</td>
					<td colspan="3"><%=fileBoard.getSubject() %></td>
				</tr>
				<tr>
					<td>파일</td>
					<td colspan="3">
						<a href="../upload/<%=fileBoard.getRealFile() %>" download="<%=fileBoard.getOriginalFile() %>"><%=fileBoard.getOriginalFile() %></a>
					</td>
				</tr>
				<tr>
					<td>내용</td>
					<td colspan="3"><%=fileBoard.getContent() %></td>
				</tr>
			</table>
			<div id="table_search">
				<input type="button" value="글수정" class="btn" onclick="location.href='driver_update.jsp?idx=<%=idx%>&pageNum=<%=pageNum%>'"> 
				<input type="button" value="글삭제" class="btn" onclick="location.href='driver_delete.jsp?idx=<%=idx%>&pageNum=<%=pageNum%>'"> 
				<input type="button" value="글목록" class="btn" onclick="location.href='driver.jsp?pageNum=<%=pageNum%>'">
			</div>

			<div class="clear"></div>
		</article>

		<div class="clear"></div>
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp" />
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>


