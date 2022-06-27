<%@page import="member.MemberDAO"%>
<%@page import="member.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 폼파라미터(아이디, 패스워드) 가져와서 변수에 저장하거나 MemberDTO 객체에 저장
// String id = request.getParameter("id");
// String pass = request.getParameter("pass");

MemberDTO member = new MemberDTO();
member.setId(request.getParameter("id"));
member.setPass(request.getParameter("pass"));
// out.println("아이디 : " + member.getId() + ", 패스워드 : " + member.getPass());

// MemberDAO 객체의 login() 메서드 호출하여 로그인 작업 수행
// => 파라미터 : 아이디,패스워드변수 또는 MemberDTO 객체, 리턴타입 : boolean(isLoginSuccess)
MemberDAO dao = new MemberDAO();
boolean isLoginSuccess = dao.login(member);

// 로그인 결과 판별
// => 실패 시 자바스크립트 사용하여 "로그인 실패!" 출력 후 이전페이지로 돌아가기
// => 성공 시 세션 객체에 "sId" 라는 속성명으로 로그인 성공한 아이디값 저장 후 메인페이지로 포워딩
if(!isLoginSuccess) { // 실패 시
	%>
	<script>
		alert("로그인 실패!");
		history.back();
	</script>
	<%
} else { // 성공 시
	// 세션 객체에 아이디 저장
	session.setAttribute("sId", member.getId()); // sId 라는 속성명으로 로그인 성공한 아이디 저장
	response.sendRedirect("../main/main.jsp");
}
%>
