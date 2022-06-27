<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/join.jsp</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function openDupCheckWindow() {
		// 새 창 열기
		window.open("check_id.jsp", "", "width=400,height=300");
	}
	
	function checkPass(retypePass) { // Retype Password 항목에서 커서가 빠져나갈 때 호출되는 메서드
		// 전달받은 확인용 패스워드와 입력되어 있는 패스워드를 비교하여 
		// 결과를 span 영역(id="checkPassResult") 에 출력
		let pass = document.fr.pass.value;
		
		if(pass == retypePass) {
			document.getElementById("checkPassResult").innerHTML = "<b>패스워드 일치</b>";
			document.getElementById("checkPassResult").style.color = "GREEN";
		} else {
			document.getElementById("checkPassResult").innerHTML = "<b>패스워드 불일치</b>";
			document.getElementById("checkPassResult").style.color = "RED";
		}
	}
	
	function checkEmail(retypeEmail) { // Retype Email 항목에서 커서가 빠져나갈 때 호출되는 메서드
		// 전달받은 확인용 이메일과 입력되어 있는 이메일을 비교하여 
		// 결과를 span 영역(id="checkEmailResult") 에 출력
		alert(retypeEmail);
	}
</script>
</head>
<body>
	<div id="wrap">
		<!-- 헤더 들어가는곳 -->
		<jsp:include page="../inc/top.jsp"></jsp:include>
		<!-- 헤더 들어가는곳 -->
		  
		<!-- 본문들어가는 곳 -->
		  <!-- 본문 메인 이미지 -->
		  <div id="sub_img_member"></div>
		  <!-- 왼쪽 메뉴 -->
		  <nav id="sub_menu">
		  	<ul>
		  		<li><a href="#">Join us</a></li>
		  		<li><a href="#">Privacy policy</a></li>
		  	</ul>
		  </nav>
		  <!-- 본문 내용 -->
		  <article>
		  	<h1>Join Us</h1>
		  	<form action="joinPro.jsp" method="post" id="join" name="fr">
		  		<fieldset>
		  			<legend>Basic Info</legend>
		  			<label>User Id</label>
		  			<%-- 아이디 입력창은 입력 잠금 후 중복체크 버튼을 통해서만 입력되도록 하기 --%>
		  			<input type="text" name="id" class="id" id="id" required="required" readonly="readonly" placeholder="중복체크 버튼 클릭">
		  			<input type="button" value="dup. check" class="dup" id="btn" onclick="openDupCheckWindow()"><br>
		  			
		  			<label>Password</label>
		  			<input type="password" name="pass" id="pass" required="required"><br> 			
		  			
		  			<label>Retype Password</label>
		  			<input type="password" name="pass2" onblur="checkPass(this.value)">
		  			<span id="checkPassResult"><!-- 패스워드 확인 결과 출력 위치 --></span><br>
		  			
		  			<label>Name</label>
		  			<input type="text" name="name" id="name" required="required"><br>
		  			
		  			<label>E-Mail</label>
		  			<input type="email" name="email" id="email" required="required"><br>
		  			
		  			<label>Retype E-Mail</label>
		  			<input type="email" name="email2" onblur="checkEmail(this.value)">
		  			<span id="checkEmailResult"><!-- E-Mail 확인 결과 출력 위치 --></span><br>
		  		</fieldset>
		  		
		  		<fieldset>
		  			<legend>Optional</legend>
		  			<label>Address</label>
		  			<input type="text" name="address" ><br>
		  			<label>Phone Number</label>
		  			<input type="text" name="phone" ><br>
		  			<label>Mobile Phone Number</label>
		  			<input type="text" name="mobile" ><br>
		  		</fieldset>
		  		<div class="clear"></div>
		  		<div id="buttons">
		  			<input type="submit" value="Submit" class="submit">
		  			<input type="reset" value="Cancel" class="cancel">
		  		</div>
		  	</form>
		  </article>
		  
		  
		<div class="clear"></div>  
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp"></jsp:include>
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>


