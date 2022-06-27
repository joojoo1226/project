<%@page import="board.BoardDAO"%>
<%@page import="board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");

// 폼 파라미터 데이터 BoardDTO 객체(board)에 저장
BoardDTO board = new BoardDTO();
board.setName(request.getParameter("name"));
board.setPass(request.getParameter("pass"));
board.setSubject(request.getParameter("subject"));
board.setContent(request.getParameter("content"));

// BoardDAO 객체의 insertBoard() 메서드를 호출하여 게시물 등록 작업 수행
// => 파라미터 : BoardDTO 객체(board), 리턴타입 : int(insertCount)
BoardDAO dao = new BoardDAO();
int insertCount = dao.insertBoard(board);

// 게시물 등록 작업 실패 시 자바스크립트 통해 "글쓰기 실패!' 출력 후 이전페이지로 돌아가기
// 아니면, notice.jsp 페이지로 포워딩
if(insertCount == 0) {
	%>
	<script>
		alert("글쓰기 실패!");
		history.back();
	</script>
	<%
} else {
	response.sendRedirect("notice.jsp");
}
%>












