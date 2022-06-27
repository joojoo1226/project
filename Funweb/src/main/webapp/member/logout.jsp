<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 세션 초기화를 통해 로그아웃 기능 구현
session.invalidate();
%>
<script>
	alert("로그아웃 완료!");
	location.href = "../main/main.jsp";
</script>