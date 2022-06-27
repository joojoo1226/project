<%@page import="board.BoardDAO"%>
<%@page import="board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 폼파라미터 가져오기(확인)
request.setCharacterEncoding("UTF-8");

BoardDTO board = new BoardDTO();
board.setIdx(Integer.parseInt(request.getParameter("idx")));
board.setName(request.getParameter("name"));
board.setPass(request.getParameter("pass"));
board.setSubject(request.getParameter("subject"));
board.setContent(request.getParameter("content"));

// BoardDAO 객체의 checkPass() 메서드를 호출하여 작성자가 입력한 패스워드 확인
// => board 테이블의 idx 가 일치하는 게시물의 패스워드를 비교
// => 파라미터 : BoardDTO 객체, 리턴타입 : boolean(isCorrectPass)
BoardDAO dao = new BoardDAO();
boolean isCorrectPass = dao.checkPass(board);

// 만약, 패스워드가 일치하지 않으면 자바스크립트를 통해 "수정 권한이 없습니다" 출력 후 이전페이지
// 아니면, 글 수정 작업 수행
if(!isCorrectPass) {
	%>
	<script>
		alert("수정 권한이 없습니다!");
		history.back();
	</script>
	<%
} else {
	// BoardDAO 객체의 updateBoard() 메서드를 호출하여 글 수정 작업 수행
	// => 파라미터 : BoardDTO 객체, 리턴타입 : int(updateCount)
	int updateCount = dao.updateBoard(board);
	
	// 글 수정 실패 시 자바스크립트를 통해 "수정 실패!" 출력 후 이전페이지
	// 아니면, notice_content.jsp 페이지 포워딩(글번호와 페이지번호 전달)
	if(updateCount == 0) {
		%>
		<script>
			alert("수정 실패!");
			history.back();
		</script>
		<%
	} else {
		response.sendRedirect(
				"notice_content.jsp?idx=" + board.getIdx() + "&pageNum=" + request.getParameter("pageNum"));
	}
}
%>











