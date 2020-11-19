<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String ctxPath = request.getContextPath();
    //    /TeamMVC
%>
<jsp:include page="../header.jsp" />
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%= ctxPath%>/css/style.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<script type="text/javascript">
	
	$(document).ready(function(){
		
		// 찾기
		$("button#btnFind").click(function(){
			var useridVal = $("input#userid").val().trim();
			var emailVal = $("input#email").val().trim();
			
			// 아이디 및 이메일에 대한 정규표현식을 사용한 유효성 검사는 생략하겠습니다.
			if( useridVal != "" && emailVal != "" ) {
				var frm = document.pwdFindFrm;
				frm.action = "<%= ctxPath%>/login/pwdFind.neige";
				frm.method = "POST";
				frm.submit();
			}
			else {
				alert("아이디와 이메일을 입력하세요!!");
				return;
			}
		});// end of $("button#btnFind").click(function(){})--------------------
		
		
		var method = "${method}";
		
		if(method == "POST") {
			$("input#userid").val("${userid}");
			$("input#email").val("${email}");
			$("div#div_findResult").show();
			
			if(${sendMailSuccess == true}) {
				$("div#div_btnFind").hide();
			}
		}
		else {
			$("div#div_findResult").hide();
		}	
		
		// 인증하기 
		$("button#btnConfirmCode").click(function(){
			
			var frm = document.verifyCertificationFrm;
			
			frm.userid.value = $("input#userid").val();
			frm.userCertificationCode.value = $("input#input_confirmCode").val();
			
			frm.action = "<%= ctxPath%>/login/verifyCertification.neige";
			frm.method = "POST";
			frm.submit();
		});
		
	});// end of $(document).ready(function(){})---------------
	
</script>
<style>

div.titleArea {
	margin: 50px 0 50px;
	color: #526B8E;
	width: 220px;
	padding: 15px font-size: 17pt;
	margin: 30px;
	height: 30px;
	border-bottom: solid 3px #e3e3e3;
	letter-spacing: 4px;
}

fieldset {
	width: 55%;
	margin: 40px;
	padding: 0 80px;
}

div.item {
	margin: 8px 0 0;
	color: #353535;
	line-height: 20px;
}

#div_userid {
	width: 50%;
	height: 15%;
	margin-bottom: 2%;
	margin-left: 10%;
	position: relative;
}

#div_email {
	width: 50%;
	height: 15%;
	margin-bottom: 5%;
	margin-left: 10%;
	position: relative;
}

#div_findResult {
	width: 70%;
	height: 15%;
	margin-bottom: 5%;
	margin-left: 10%;
	position: relative;
}

 
div#input{
   		width: 40%;
   		 
   }
   
  
   
   label#id{
   		float: left;
   		width: 20px;
   }
    
   label#Email{
   		float: left;
   		width: 20px;
   
   }


div#div_btnFind{
	width: 40%;
	height: 15%;
	margin-bottom: 5%;
	margin-left: 10%;
	position: relative;
	margin:20px;

}


</style>



<form name="pwdFindFrm">
	<fieldset>
		<div class="titleArea">PassWord Search</div>
		<div class="item">
			<div class="category">

				<div id="div_userid" align="center">
					<label id="id" for="id">ID</label>
					<input placeholder="ID" size="15" type="text" id="userid" name="userid"  autocomplete="off" required /> 
				</div>

				<div id="div_email" align="center"> 
					<label id="Email" for="Email">Email</label>
					<input type="text" name="email" id="email" size="15" placeholder="hong@gmail.com" autocomplete="off" required />
					 
				</div>





				<div id="div_findResult" align="center">

					<c:if test="${isUserExist == true && sendMailSuccess == true}">
						<span style="font-size: 10pt;">인증코드가 ${email}로 발송되었습니다.</span>
						<br>
						<span style="font-size: 10pt;">인증코드를 입력해주세요.</span>
						<br>
						<input type="text" name="input_confirmCode" id="input_confirmCode"
							required />
						<br>
						<br>
						<button type="button" class="btn btn-info" id="btnConfirmCode">인증하기</button>
					</c:if>

					<c:if test="${isUserExist == true && sendMailSuccess == false}">
						<span style="color: red;">메일발송이 실패했습니다.</span>
					</c:if>

					<c:if test="${isUserExist == false}">
						<span style="color: red;">사용자 정보가 없습니다.</span>
					</c:if>

				</div>

				<div id="div_btnFind" align="center">
					<button type="button" class="btn btn-success" id="btnFind"
					style="width: 80%; border-top: hidden; background-color: #6E6E6E; color: white; border-bottom: hidden; border-left: hidden; padding: 10px; vertical-align: middle; border: none;">
					찾기</button>
				</div>
			</div>
		</div>

	</fieldset>
</form>


<form name="verifyCertificationFrm">
	<input type="hidden" name="userid" /> <input type="hidden"
		name="userCertificationCode" />
</form>

<jsp:include page="../footer.jsp" />
