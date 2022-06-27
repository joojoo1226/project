<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 아이디 값은 미리 가져와서 저장하기
String id = request.getParameter("id");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function printId() {
		// 현재 창이 join.jsp 에 의해 열린 자식창이므로 부모창의 요소에 접근하려면
		// window.opener 속성을 통해 접근해야함
		window.opener.document.fr.id.value = "<%=id%>";
		window.close(); // 현재창(자식창) 닫기
	}
</script>
</head>
<body>
	<h1>ID 중복확인</h1>
	<form action="check_idPro.jsp" method="get">
		<input type="text" name="id" <%if(id != null) {%>value="<%=id%>" <%} %>>
		<input type="submit" value="중복확인">
	</form>
	<%-- URL 파라미터에 id 파라미터가 전달된 경우에만 id 와 isDuplicate 파라미터 가져오기 --%>
	<%
	if(id != null) {
		// id 파라미터가 있을 경우 isDuplicate 파라미터 가져와서 변수에 저장
		boolean isDuplicate = Boolean.parseBoolean(request.getParameter("isDuplicate"));
		// boolean 타입 isDuplicate 는 String 타입 변수로 그대로 저장 후 문자열 비교도 사용 가능
		
		if(isDuplicate) { // 아이디 중복일 경우
			%><h3><%=id %> : 사용 불가능</h3><%
		} else { // 아이디 중복이 아닐 경우
			%>
			<h3>
				<%=id %> : 사용 가능<br>
				<input type="button" value="아이디 사용" onclick="printId()">
			</h3>
			<%
		}
	}
	%>
</body>
</html>











