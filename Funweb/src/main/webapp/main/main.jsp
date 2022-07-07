<%@page import="board.FileBoardDTO"%>
<%@page import="board.FileBoardDAO"%>
<%@page import="board.BoardDTO"%>
<%@page import="java.util.List"%>
<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main/main.jsp</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/front.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="wrap">
		<!-- 헤더 들어가는곳 -->
		<%-- jsp:include 액션태그를 사용하여 page 속성에 현재 페이지에 삽입할 페이지 지정 --%>
		<jsp:include page="../inc/top.jsp"></jsp:include>
		<!-- 헤더 들어가는곳 -->
		  
		<div class="clear"></div>   
		<!-- 본문들어가는 곳 -->
		<!-- 메인 이미지 -->
		<div id="main_img"><img src="../images/main_img.jpg" width="100%" height="100%"></div>
		<!-- 본문 내용 -->
		<article id="front">
		  	<div id="solution">
		  		<div id="hosting">
		  			<h3>Web Hosting Solution</h3>
					<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
		  		</div>
		  		<div id="security">
		  		  	<h3>Web Security Solution</h3>
					<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
		  		</div>
		  		<div id="payment">
		  			<h3>Web Payment Solution</h3>
					<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
		  		</div>
		  	</div>
		  	<div class="clear"></div>
			<div id="sec_news">
				<%
				FileBoardDAO daoF = new FileBoardDAO();
				List recentDriverList = daoF.selectRecentDriverList();
				%>
				<h3><span class="orange">Driver</span> News</h3>
				<table>
					<%
					for(Object d : recentDriverList) {
						FileBoardDTO fileBoard = (FileBoardDTO)d;
					%>
					<tr>
						<td class="notice_title">
							<a href="../center/driver_content.jsp?idx=<%=fileBoard.getIdx()%>&pageNum=1"><%=fileBoard.getSubject() %></a>
						</td>
						<td width="100">
							<%=fileBoard.getDate() %>
						</td>
					</tr>
					<%
					}
					%>
				</table>
			</div>
		
			<div id="news_notice">
				<%
				// BoardDAO 객체의 selectRecentNoticeList() 메서드를 호출하여 최근 게시물 5개 조회
				// => 파라미터 : 없음, 리턴타입 : List(recentNoticeList)
				BoardDAO dao = new BoardDAO();
				List recentNoticeList = dao.selectRecentNoticeList();
				%>
		  		<h3 class="brown">News &amp; Notice</h3>
		  		<%-- recentBoardList 크기만큼 반복하면서 최근 게시물 5개 목록 출력 --%>
				<table>
					<%
					for(Object o : recentNoticeList) {
						BoardDTO board = (BoardDTO)o;
					%>
						<tr>
							<td class="notice_title">
								<a href="../center/notice_content.jsp?idx=<%=board.getIdx()%>&pageNum=1"><%=board.getSubject() %></a>
							</td>
							<td width="100">
								<%=board.getDate() %>
							</td>
						</tr>
					<%
					}
					%>
				</table>
		  	</div>
	  	</article>
		  
		<div class="clear"></div>  
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp"></jsp:include>
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>














