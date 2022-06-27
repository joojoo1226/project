<%@page import="member.MemberDTO"%>
<%@page import="member.MemberDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
MemberDAO daoM = new MemberDAO();

ArrayList list = daoM.memberList();
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
<title>member/member_management.jsp</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/front.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="wrap">
	
	<jsp:include page="../inc/top.jsp"></jsp:include>
		
		<section id="memberListForm">
			
			<h1>회원 목록</h1>
			
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
				<%
					if(list == null || list.size() == 0) {
					%>
						<h3>회원이 없습니다.</h3>
					<%	
					} else {
						
						for(int i=0; i<list.size(); i++) {
							MemberDTO member = (MemberDTO)list.get(i);
						%>
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
						<%
						}
					}
					%>
			</table>
			
		</section>
		
	<jsp:include page="../inc/bottom.jsp"></jsp:include>
	
	</div>
	
</body>
</html>
