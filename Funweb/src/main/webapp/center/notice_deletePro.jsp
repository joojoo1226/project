<%@page import="board.BoardDAO"%>
<%@page import="board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 글번호, 페이지번호, 패스워드 가져오기
// => 글번호와 패스워드는 BoardDTO 객체에 저장
BoardDTO board = new BoardDTO();
board.setIdx(Integer.parseInt(request.getParameter("idx")));
board.setPass(request.getParameter("pass"));

// BoardDAO 객체의 checkPass() 메서드를 재사용하여 삭제 권한 판별
// => 파라미터 : BoardDTO 객체, 리턴타입 : boolean(isCorrectPass)
BoardDAO dao = new BoardDAO();
boolean isCorrectPass = dao.checkPass(board);

// 삭제 권한이 없을 경우 자바스크립트를 통해 "삭제 권한이 없습니다!" 출력 후 이전페이지로 돌아가기
// 아니면, BoardDAO 객체의 deleteBoard() 메서드를 호출하여 삭제
// => 파라미터 : BoardDTO 객체, 리턴타입 : int(deleteCount)
if(!isCorrectPass) {
	%>
	<script>
		alert("수정 권한이 없습니다!");
		history.back();
	</script>
	<%
} else {
	int deleteCount = dao.deleteBoard(board);
	
	// 삭제 실패 시 자바스크립트를 통해 "삭제 실패!" 출력 후 이전페이지로 돌아가기
	// 아니면, 글목록(notice.jsp) 페이지로 포워딩(페이지번호 전달)
	if(deleteCount == 0) {
		%>
		<script>
			alert("삭제 실패!");
			history.back();
		</script>
		<%
	} else {
		response.sendRedirect("notice.jsp?pageNum=" + request.getParameter("pageNum"));
	}
}
%>










