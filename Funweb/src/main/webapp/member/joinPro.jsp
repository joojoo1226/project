<%@page import="member.MemberDAO"%>
<%@page import="member.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");

// 폼 파라미터 데이터 가져와서 MemberDTO 객체에 저장 => 확인
MemberDTO member = new MemberDTO();
member.setId(request.getParameter("id"));
member.setPass(request.getParameter("pass"));
member.setName(request.getParameter("name"));
member.setEmail(request.getParameter("email"));
member.setAddress(request.getParameter("address"));
member.setPhone(request.getParameter("phone"));
member.setMobile(request.getParameter("mobile"));

// MemberDAO 객체의 insert() 메서드를 호출하여 회원가입 작업 수행
// => 파라미터 : MemberDTO 객체, 리턴타입 : int(insertCount)
MemberDAO dao = new MemberDAO();
int insertCount = dao.insert(member);

// 회원가입 결과 판별
// => 실패 시(insertCount 가 0) 자바스크립트 사용하여 "회원가입 실패" 출력 후 이전페이지로 돌아가기
// => 성공 시 joinSuccess.jsp 페이지로 포워딩
if(insertCount == 0) {
	%>
	<script>
		alert("회원가입 실패!");
		history.back();
	</script>
	<%
} else {
	response.sendRedirect("joinSuccess.jsp");
}
%>









