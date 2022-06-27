<%@page import="member.MemberDTO"%>
<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// id 파라미터 가져오기
// MemberDAO 객체의 selectMember() 메서드를 호출하여 id 에 해당하는 레코드 조회
// => 파라미터 : 아이디, 리턴타입 : MemberDTO 객체(member)
String id = (String)session.getAttribute("sId");

MemberDAO daoM = new MemberDAO();

MemberDTO member =  daoM.selectMember(id);
%>    
<!DOCTYPE html>
<html>
<style>
	#memberListForm {
		width: 1024px;
		max-height: 600px;
		margin: auto;
	}
	
	h1 {
		text-align: center;
	}
	
	table {
		width: 90%;
		margin-left: 3%;
		margin-right: auto;
	}
	
	#tr_top {
		text-align: center;
		background: #c9cbe0;
	}
	
	table td {
		text-align: center;
	}
	
</style>
<head>
<meta charset="UTF-8">
<title>member/member_info.jsp</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/front.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%-- 조회 성공한 회원 정보(MemberDTO 객체) 출력 --%>
	<body>
	<div id="wrap">
	
	<jsp:include page="../inc/top.jsp"></jsp:include>
		
		<section id="memberListForm">
			
			<h1>내 정보</h1>
			
			<table>
				<tr id="tr_top">
					<td>ID</td>
					<td>비밀번호</td>
					<td>이름</td>
					<td>E-mail</td>
					<td>주소</td>
					<td>전화번호</td>
					<td>핸드폰</td>
					<td>날짜</td>
				</tr>
				<tr>
					<td><%=member.getId() %></td>
					<td><%=member.getPass() %></td>
					<td><%=member.getName() %></td>
					<td><%=member.getEmail() %></td>
					<td><%=member.getAddress() %></td>
					<td><%=member.getPhone() %></td>
					<td><%=member.getMobile() %></td>
					<td><%=member.getDate() %></td>
				</tr>
			</table>
			
		</section>
		
	<jsp:include page="../inc/bottom.jsp"></jsp:include>
	
	</div>
</body>
</html>