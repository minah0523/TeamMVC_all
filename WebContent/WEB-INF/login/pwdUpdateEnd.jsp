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
		
		$("button#btnUpdate").click(function(){
			
		    var pwd = $("input#pwd").val();
		    var pwd2 = $("input#pwd2").val();
		    
		 // var regExp = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g;
		 // 또는
			var regExp = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g);
			// 숫자/문자/특수문자/ 포함 형태의 8~15자리 이내의 암호 정규표현식 객체 생성
			
			var bool = regExp.test(pwd);
			
			if(!bool) {
				alert("암호는 8글자 이상 15글자 이하에 영문자,숫자,특수기호가 혼합되어야만 합니다.");
				$("input#pwd").val("");
				$("input#pwd2").val("");
				return; // 종료
			}
			else if(bool && pwd != pwd2) {
				alert("암호가 일치하지 않습니다.");
				$("input#pwd").val("");
				$("input#pwd2").val("");
				return; // 종료
			}
			else {
				var frm = document.pwdUpdateEndFrm;
				frm.action = "<%= ctxPath%>/login/pwdUpdateEnd.neige";
				frm.method = "POST";
				frm.submit();
			}
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




#div_pwd {
	width: 70%;
	height: 15%;
	margin-bottom: 5%;
	margin-left: 10%;
	position: relative;
}

#div_pwd2 {
	width: 70%;
	height: 15%;
	margin-bottom: 5%;
	margin-left: 10%;
	position: relative;
}

#div_updateResult {
	width: 50%;
	height: 15%;
	margin-bottom: 5%;
	margin-left: 10%;
	position: relative;
}

#div_btnUpdate {
	width: 50%;
	height: 15%;
	margin-bottom: 5%;
	margin-left: 10%;
	position: relative;
}
	div#box{
	
	}

   label#newpwd{
   		float: left;
   		width: 60px;
   }
    
   label#newpwd2{
   		float: left;
   		width: 60px;
   
   }
   
    
   
   #div_div_pwd {
	width: 50%;
	height: 15%;
	margin-bottom: 2%;
	margin-left: 10%;
	position: relative;
	}

	#div_div_pwd2 {
	width: 50%;
	height: 15%;
	margin-bottom: 5%;
	margin-left: 10%;
	position: relative;
	}
   
</style>

<form name="pwdUpdateEndFrm">
	<fieldset>
		<div class="titleArea">PassWord Search</div>
		<div class="item">
			<div class="category">
			
			<div id="box">
				<div id="div_pwd" align="center">
					<label id="newpwd" for="">새암호</label><br /> 
					<input type="password" name="pwd" id="pwd" size="15" placeholder="PASSWORD" required />
				</div>
	
				<div id="div_pwd2" align="center">
					<label id="newpwd2" for="newpwd2">새암호확인</label><br />
					 <input type="password" id="pwd2" size="15" placeholder="PASSWORD" required />
				</div>
			</div>
				
				
			</div>
		</div>
	
		<input type="hidden" name="userid" value="${userid}" />
	
		<c:if test="${method == 'POST' && n == 1}">
			<div id="div_updateResult" align="center">
				<SPAN style="color:#D2B48C">${userid}</SPAN> 님의 암호가 새로이 변경되었습니다.<br>
			</div>
		</c:if>
	
		<c:if test="${method == 'GET'}">
			<div id="div_btnUpdate" align="center">
				<button type="button" class="btn btn-success" id="btnUpdate"
					style="width: 30%; border-top: hidden; background-color: #6E6E6E; color: white; border-bottom: hidden; border-left: hidden; padding: 10px; vertical-align: middle; border: none;">
					변경</button>
			</div>
		</c:if>
	</fieldset>
</form>
<jsp:include page="../footer.jsp" />
