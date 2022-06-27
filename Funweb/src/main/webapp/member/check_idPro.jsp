<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 입력받은 아이디 파라미터값 가져와서 변수에 저장
String id = request.getParameter("id");

// MemberDAO 객체의 checkUser() 메서드를 호출하여 아이디 존재 여부 판별
// => 파라미터 : 아이디(id), 리턴타입 : boolean(isDuplicate)
MemberDAO dao = new MemberDAO();
boolean isDuplicate = dao.checkUser(id);

// 판별 결과값을 check_id.jsp 페이지로 포워딩할 때 함께 전송
// => 판별에 사용된 아이디도 함께 전송
response.sendRedirect("check_id.jsp?id=" + id + "&isDuplicate=" + isDuplicate);
%>









